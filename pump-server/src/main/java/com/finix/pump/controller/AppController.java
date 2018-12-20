package com.finix.pump.controller;

import com.finix.pump.controller.response.MessageType;
import com.finix.pump.controller.response.Response;
import com.finix.pump.vo.*;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/apps")
public class AppController  {

    private final AtomicLong i = new AtomicLong(100000L);

    private AtomicLong cacheIdstart = new AtomicLong(10173L);

    private PageVO<AppVO> apps = new PageVO<>();

    private Map<Long, AppVO> approvedApps = new HashMap<>(16);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Map<String, Map<Long, DomainNameVo>> domainNames = new HashMap<>(16);

    private Map<String, Map<Long, DBVo>> dbs = new HashMap<>(16);

    private Map<String, Map<Long, ApolloVo>> appollos = new HashMap<>(16);

    private Map<String,Map<Long, List<CacheVo>>> caches = new HashMap<>(16);

    private Map<String,Map<Long, List<CustomerGroupVo>>> customerGroups = new HashMap<>(16);

    private Map<String,Map<Long, List<TopicVo>>> topics = new HashMap<>(16);

    private Map<String,Map<Long, StargateVo>> stargates = new HashMap<>(16);

    /**
     * {env,{clusterName,ReleaseGroup}}
     */
    private Map<String,Map<String, List<ReleaseGroupVo>>> releaseGroups = new HashMap<>(16);

    /**
     * {env,{releaseGroupName,Container}}
     */
    private Map<String,Map<String, List<ContainerVo>>> containers = new HashMap<>(16);

    @RequestMapping(method = RequestMethod.GET)
    public Response<PageVO<AppVO>> findApps() {
        return Response.mark(MessageType.SUCCESS, apps);
    }


    @RequestMapping(value = "/newApp", method = RequestMethod.POST)
    public Response<String> newApp(@RequestBody AppVO applyNewApp) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String applyDate = dateTimeFormatter.format(localDateTime);
        applyNewApp.setApplyStatus("审核中");
        applyNewApp.setApplyDate(applyDate);
        if(CollectionUtils.isEmpty(apps.getContent())){
            apps.setContent(new ArrayList<>());
        }
        long id = apps.getContent().size() + 1;
        applyNewApp.setId(id);
        apps.getContent().add(applyNewApp);
        return Response.mark(MessageType.SUCCESS, "您已成功提交申请，请耐心等待。您可以在申请历史查看申请结果。");
    }

    @RequestMapping(value = "/{appId}" , method = RequestMethod.DELETE)
    public Response<String> newApp(@PathVariable("appId") Long appId){
        AppVO app = approvedApps.remove(appId);
        apps.getContent().remove(app);
        for(Map.Entry<String, Map<Long, DomainNameVo>> entry : domainNames.entrySet()){
            entry.getValue().remove(appId);
        }

        for(Map.Entry<String, Map<Long, DBVo>> entry : dbs.entrySet()){
            entry.getValue().remove(appId);
        }

        for(Map.Entry<String, Map<Long, ApolloVo>> entry : appollos.entrySet()){
            entry.getValue().remove(appId);
        }

        for(Map.Entry<String, Map<Long, List<CacheVo>>> entry : caches.entrySet()){
            entry.getValue().remove(appId);
        }

        for(Map.Entry<String, Map<Long, List<CustomerGroupVo>>> entry : customerGroups.entrySet()){
            entry.getValue().remove(appId);
        }

        for(Map.Entry<String, Map<Long, List<TopicVo>>> entry : topics.entrySet()){
            entry.getValue().remove(appId);
        }

        for(Map.Entry<String, Map<Long, StargateVo>> entry : stargates.entrySet()){
            entry.getValue().remove(appId);
        }
        return Response.mark(MessageType.SUCCESS, "删除成功!该应用申请的所有资源将被回收！");
    }

    @RequestMapping(value = "/approve/{id}/{isPass}", method = RequestMethod.GET)
    public Response<String> approve(@PathVariable("id") Integer id, @PathVariable("isPass") Integer isPass) {
        String message = "找不到对应序号"+id+"的app";
        Long appId = null;
        if(!CollectionUtils.isEmpty(apps.getContent()) && id <= apps.getContent().size()){
            if(isPass == 0){
                appId= i.incrementAndGet();
                message = "审核通过";
            }else{
                message = "审核拒绝";
            }
            int index = id - 1;
            AppVO app = apps.getContent().get(index);
            app.setAppId(appId);
            app.setApplyStatus(message);
            LocalDateTime localDateTime = LocalDateTime.now();
            String approveDate = dateTimeFormatter.format(localDateTime);
            app.setApproveDate(approveDate);
            //保存审核通过的app
            approvedApps.put(appId,app);
        }
        return Response.mark(MessageType.SUCCESS, message);
    }

    @RequestMapping(value = "/{appId}/{env}" , method = RequestMethod.GET)
    public Response<AppVO> gateApp(@PathVariable("appId") Long appId, @PathVariable("env") String env) {
        AppVO app = approvedApps.get(appId);
        if(domainNames.get(env) != null){
            DomainNameVo domainName = domainNames.get(env).get(appId);
            app.setDomainName(domainName);
        }else{
            app.setDomainName(null);
        }
        if(dbs.get(env) != null){
            DBVo dbVo = dbs.get(env).get(appId);
            app.setDb(dbVo);
        }else{
            app.setDb(null);
        }
        if(appollos.get(env) != null){
            ApolloVo apolloVo = appollos.get(env).get(appId);
            app.setApollo(apolloVo);
        }else{
            app.setApollo(null);
        }
        if(stargates.get(env) != null){
            StargateVo stargateVo = stargates.get(env).get(appId);
            app.setStargate(stargateVo);
        }else{
            app.setStargate(null);
        }
        return Response.mark(MessageType.SUCCESS, app);
    }

    @RequestMapping(value = "/applyDomainName" , method = RequestMethod.POST)
    public Response<String> applyDomainName(@RequestBody DomainNameVo domainName){
        Long appId = domainName.getAppId();
        AppVO app = approvedApps.get(appId);
        domainName.setStatus("审核中");
        app.setDomainName(domainName);
        String env = domainName.getEnv();
        Map<Long,DomainNameVo> map = new HashMap<>(16);
        map.put(appId,domainName);
        domainNames.put(env,map);
        return Response.mark(MessageType.SUCCESS, "审核中");
    }

    @RequestMapping(value = "/approveDomainName/{appId}/{env}/{i}" , method = RequestMethod.GET)
    public Response<String> approveDomainName(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("i") Integer i){
        if(i == 0){
            DomainNameVo orgDomainName = domainNames.get(env).get(appId);
            orgDomainName.setStatus("审核通过");
            orgDomainName.setApproveMessage("审核通过");
            return Response.mark(MessageType.SUCCESS, "审核通过");
        }else{
            DomainNameVo orgDomainName = domainNames.get(env).get(appId);
            orgDomainName.setStatus("审核拒绝");
            orgDomainName.setApproveMessage("审核拒绝，请重新申请。");
            return Response.mark(MessageType.SUCCESS, "审核拒绝");
        }
    }

    @RequestMapping(value = "/applyDB" , method = RequestMethod.POST)
    public Response<String> applyDB(@RequestBody DBVo db){
        Long appId = db.getAppId();
        String env = db.getEnv();
        db.setStatus("审核中");
        AppVO app = approvedApps.get(appId);
        app.setDb(db);
        Map<Long,DBVo> map = new HashMap<>(16);
        map.put(appId,db);
        dbs.put(env,map);
        return Response.mark(MessageType.SUCCESS, "审核中");
    }

    @RequestMapping(value = "/approveDB/{appId}/{env}/{i}" , method = RequestMethod.GET)
    public Response<String> approveDB(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("i") Integer i) {
        DBVo orgDb = dbs.get(env).get(appId);
        if(i == 0){
            orgDb.setStatus("审核通过");
            orgDb.setHost("10.114.8.31");
            orgDb.setPort(3405);
            String orgDbName = orgDb.getDbName();
            orgDb.setDbName("ppd_" + orgDbName);
            orgDb.setUserName("user_app_pump");
            orgDb.setPassword("7V*xM0si5Jgy");
            orgDb.setPrivs("select,insert,update,delete,execute");
            orgDb.setApproveMessage("审核通过，请查看数据库信息");
            return Response.mark(MessageType.SUCCESS, "审核通过");
        }else{
            orgDb.setStatus("审核拒绝");
            orgDb.setApproveMessage("审核拒绝，原因：...");
            return Response.mark(MessageType.SUCCESS, "审核拒绝");
        }

    }

    /**
     * 查看数据库详情
     * @param appId
     * @param env
     * @return
     */
    @RequestMapping(value = "/getDBVo/{appId}/{env}" , method = RequestMethod.GET)
    public Response<DBVo> getDBVo(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        DBVo dbVo = dbs.get(env).get(appId);
        return Response.mark(MessageType.SUCCESS, dbVo);
    }


    /**
     * 申请Apollo项目
     * @param apollo
     * @return
     */
    @RequestMapping(value = "/applyApollo" , method = RequestMethod.POST)
    public Response<String> applyApollo(@RequestBody ApolloVo apollo){
        Long appId = apollo.getAppId();
        AppVO app = approvedApps.get(appId);
        apollo.setAppName(app.getAppName());
        apollo.setStatus("审核中");
        String env = apollo.getEnv();
        Map<Long,ApolloVo> map = new HashMap<>(16);
        map.put(appId,apollo);
        appollos.put(env,map);
        return Response.mark(MessageType.SUCCESS, "审核中");
    }

    /**
     * 审核Apollo项目
     * @return
     */
    @RequestMapping(value = "/approveApollo/{appId}/{env}/{i}" , method = RequestMethod.GET)
    public Response<String> approveApollo(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("i") Integer i){
        ApolloVo apolloVo = appollos.get(env).get(appId);
        if(i==0){
            apolloVo.setStatus("审核通过");
            apolloVo.setApproveMessage("审核通过，可以管理配置");
            return Response.mark(MessageType.SUCCESS, "审核通过");
        }else {
            apolloVo.setStatus("审核拒绝");
            apolloVo.setApproveMessage("审核拒绝，原因：...");
            return Response.mark(MessageType.SUCCESS, "审核拒绝");
        }

    }

    /**
     * 获取Cache列表
     * @return
     */
    @RequestMapping(value = "/cacheList/{appId}/{env}" , method = RequestMethod.GET)
    public Response<List<CacheVo>> getCacheList(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        Map<Long, List<CacheVo>> map = caches.get(env);
        if(map == null) {
            map = new HashMap<>();
            List<CacheVo> list = new ArrayList<>();
            map.put(appId,list);
            caches.put(env,map);
        }
        List<CacheVo> cacheVos = caches.get(env).get(appId);
        return Response.mark(MessageType.SUCCESS, cacheVos);
    }

    /**
     * 申请cache
     * @param cacheVo
     * @return
     */
    @RequestMapping(value = "/applyCache" , method = RequestMethod.POST)
    public Response<String> applyCache(@RequestBody CacheVo cacheVo){
        cacheVo.setStatus("审核中");
        Long appId = cacheVo.getAppId();
        String env = cacheVo.getEnv();
        List<CacheVo> cacheVos = caches.get(env).get(appId);
        if(cacheVos == null){
            cacheVos =new ArrayList<>();
            Map map = new HashMap<Long,List<CacheVo>>(16);
            map.put(appId,cacheVos);
            caches.put(env,map);
        }
        cacheVos.add(cacheVo);
        cacheVo.setId(cacheVos.size());
        return Response.mark(MessageType.SUCCESS, "审核中");
    }

    /**
     * 审核cache
     * @param appId
     * @param env
     * @param id
     * @return
     */
    @RequestMapping(value = "/approveCache/{appId}/{env}/{id}/{isPass}" , method = RequestMethod.GET)
    public Response<List<CacheVo>> approveCache(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("id") Integer id, @PathVariable("isPass") Integer isPass){
        List<CacheVo> cacheVos = caches.get(env).get(appId);
        CacheVo cacheVo = cacheVos.get(id - 1);
        if(isPass == 0){
            cacheVo.setCacheId(cacheIdstart.incrementAndGet());
            cacheVo.setStatus("使用中");
            cacheVo.setUsedMemory("0.02G  Used/0.15G  Total");
        }else{
            cacheVo.setStatus("审核拒绝");
        }
        return Response.mark(MessageType.SUCCESS, cacheVos);
    }

    /**
     * 消费者组列表
     * @param appId
     * @param env
     * @return
     */
    @RequestMapping(value = "/consumerGroupList/{appId}/{env}" , method = RequestMethod.GET)
    public Response<List<CustomerGroupVo>> getConsumerGroupList(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        Map<Long, List<CustomerGroupVo>> map = customerGroups.get(env);
        if(map == null) {
            map = new HashMap<>();
            List<CustomerGroupVo> list = new ArrayList<>();
            map.put(appId,list);
            customerGroups.put(env,map);
        }
        List<CustomerGroupVo> cacheVos = customerGroups.get(env).get(appId);
        return Response.mark(MessageType.SUCCESS, cacheVos);
    }

    /**
     * 申请消费者组
     * @return
     */
    @RequestMapping(value = "/applyConsumerGroup" , method = RequestMethod.POST)
    public Response<String> applyConsumerGroup(@RequestBody CustomerGroupVo customerGroupVo){
        customerGroupVo.setStatus("审核中");
        customerGroupVo.setApplicant("zhangxiao04");
        Long appId = customerGroupVo.getAppId();
        String env = customerGroupVo.getEnv();
        List<CustomerGroupVo> customerGroupVos = customerGroups.get(env).get(appId);
        if(customerGroupVos == null){
            customerGroupVos =new ArrayList<>();
            Map map = new HashMap<Long,List<CustomerGroupVo>>(16);
            map.put(appId,customerGroupVos);
            customerGroups.put(env,map);
        }
        customerGroupVos.add(customerGroupVo);
        customerGroupVo.setId(customerGroupVos.size());
        return Response.mark(MessageType.SUCCESS, "审核中");
    }

    /**
     * 审核消费者组
     * @param appId
     * @param env
     * @param id
     * @return
     */
    @RequestMapping(value = "/approveConsumerGroup/{appId}/{env}/{id}/{isPass}" , method = RequestMethod.GET)
    public Response<List<CustomerGroupVo>> approveConsumerGroup(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("id") Integer id, @PathVariable("isPass") Integer isPass){
        List<CustomerGroupVo> customerGroupVos = customerGroups.get(env).get(appId);
        CustomerGroupVo customerGroupVo = customerGroupVos.get(id - 1);
        LocalDateTime localDateTime = LocalDateTime.now();
        String applyDate = dateTimeFormatter.format(localDateTime);
        if(isPass == 0){
            customerGroupVo.setStatus("审核通过");
        }else{
            customerGroupVo.setStatus("审核拒绝");
        }
        customerGroupVo.setCreateTime(applyDate);
        return Response.mark(MessageType.SUCCESS, customerGroupVos);
    }

    /**
     * Topic列表
     * @param appId
     * @param env
     * @return
     */
    @RequestMapping(value = "/topicList/{appId}/{env}" , method = RequestMethod.GET)
    public Response<List<TopicVo>> getTopicList(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        Map<Long, List<TopicVo>> map = topics.get(env);
        if(map == null) {
            map = new HashMap<>();
            List<TopicVo> list = new ArrayList<>();
            map.put(appId,list);
            topics.put(env,map);
        }
        List<TopicVo> topicVos = topics.get(env).get(appId);
        return Response.mark(MessageType.SUCCESS, topicVos);
    }

    /**
     * 申请topic
     * @return
     */
    @RequestMapping(value = "/applyTopic" , method = RequestMethod.POST)
    public Response<String> applyTopic(@RequestBody TopicVo topicVo){
        topicVo.setStatus("审核中");
        topicVo.setPersonInCharge("zhangxiao04");
        topicVo.setDepartment("基础架构");
        Long appId = topicVo.getAppId();
        String env = topicVo.getEnv();
        List<TopicVo> topicVos = topics.get(env).get(appId);
        if(topicVos == null){
            topicVos =new ArrayList<>();
            Map map = new HashMap<Long,List<CustomerGroupVo>>(16);
            map.put(appId,topicVos);
            topics.put(env,map);
        }
        topicVos.add(topicVo);
        topicVo.setId(topicVos.size());
        return Response.mark(MessageType.SUCCESS, "审核中");
    }

    /**
     * 审核消费者组
     * @param appId
     * @param env
     * @param id
     * @return
     */
    @RequestMapping(value = "/approveTopic/{appId}/{env}/{id}/{isPass}" , method = RequestMethod.GET)
    public Response<List<TopicVo>> approveTopic(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("id") Integer id,@PathVariable("isPass") Integer isPass){
        List<TopicVo> topicVos = topics.get(env).get(appId);
        TopicVo topicVo = topicVos.get(id - 1);
        LocalDateTime localDateTime = LocalDateTime.now();
        String applyDate = dateTimeFormatter.format(localDateTime);
        if(isPass ==0){
            topicVo.setStatus("审核通过");
        }else {
            topicVo.setStatus("审核拒绝");
        }
        topicVo.setCreateTime(applyDate);
        return Response.mark(MessageType.SUCCESS, topicVos);
    }

    /**
     * 申请Stargate
     * @return
     */
    @RequestMapping(value = "/applyStargate" , method = RequestMethod.POST)
    public Response<String> applyStargate(@RequestBody StargateVo stargateVo){
        Long appId = stargateVo.getAppId();
        String env = stargateVo.getEnv();
        AppVO app = approvedApps.get(appId);
        stargateVo.setAppName(app.getAppName());
        stargateVo.setDomainName(app.getDomainName().getDomainName());
        stargateVo.setAppType(app.getAppType());
        stargateVo.setServiceType(app.getServiceType());
        stargateVo.setGrade(app.getGrade());
        stargateVo.setPersonInCharge("zhangxiao04");
        stargateVo.setOrgName(app.getOrgName());
        stargateVo.setStatus("审核中");
        app.setStargate(stargateVo);
        Map<Long,StargateVo> map = new HashMap<>(16);
        map.put(appId,stargateVo);
        stargates.put(env,map);
        return Response.mark(MessageType.SUCCESS, "审核中");
    }

    /**
     * 审核Stargate
     * @return
     */
    @RequestMapping(value = "/approveStargate/{appId}/{env}/{i}" , method = RequestMethod.GET)
    public Response<String> approveStargate(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("i") Integer i){
        StargateVo stargateVo = stargates.get(env).get(appId);
        if(i==0){
            Set<String> clusterSet = new HashSet<>();
            clusterSet.add("default");
            stargateVo.setClusterSet(clusterSet);
            stargateVo.setStatus("审核通过");
            stargateVo.setApproveMessage("审核通过,请查看集群列表。");
            return Response.mark(MessageType.SUCCESS, "审核通过");
        }else{
            stargateVo.setStatus("审核拒绝");
            stargateVo.setApproveMessage("审核拒绝，原因是...");
            return Response.mark(MessageType.SUCCESS, "审核拒绝");
        }

    }

    /**
     * 查看站点
     */
    @RequestMapping(value = "/stargateList/{appId}/{env}" , method = RequestMethod.GET)
    public Response<List<StargateVo>> getStargateList(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        StargateVo stargateVo = stargates.get(env).get(appId);
        List<StargateVo> stargateVos = new ArrayList<>();
        Set<String> clusterSet = stargateVo.getClusterSet();
        int index = 0;
        for(String cluster : clusterSet){
            StargateVo webSite = new StargateVo();
            webSite.setId(++index);
            webSite.setCurrentCluster(cluster);
            webSite.setStatus(stargateVo.getStatus());
            webSite.setPort(stargateVo.getPort());
            webSite.setServiceType(stargateVo.getServiceType());
            webSite.setGrade(stargateVo.getGrade());
            webSite.setAppType(stargateVo.getAppType());
            webSite.setDomainName(stargateVo.getDomainName());
            webSite.setAppName(stargateVo.getAppName());
            webSite.setEnv(stargateVo.getEnv());
            webSite.setOrgName(stargateVo.getOrgName());
            webSite.setPersonInCharge(stargateVo.getPersonInCharge());
            webSite.setTesters(stargateVo.getTesters());
            stargateVos.add(webSite);
        }
        return Response.mark(MessageType.SUCCESS, stargateVos);
    }

    /**
     * 创建集群
     */
    @RequestMapping(value = "/createCluster" , method = RequestMethod.POST)
    public Response<String> createCluster(@RequestBody StargateVo param){
        Long appId = param.getAppId();
        String env = param.getEnv();
        String currentCluster = param.getCurrentCluster();
        StargateVo stargateVo = stargates.get(env).get(appId);
        Set<String> clusterSet = stargateVo.getClusterSet();
        clusterSet.add(currentCluster);
        return Response.mark(MessageType.SUCCESS, "创建集群成功");
    }

    @RequestMapping(value = "/deleteCluster/{appId}/{env}/{currentCluster}" , method = RequestMethod.GET)
    public Response<String> deleteCluster(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("currentCluster") String currentCluster){
        StargateVo stargateVo = stargates.get(env).get(appId);
        stargateVo.getClusterSet().remove(currentCluster);
        return Response.mark(MessageType.SUCCESS, "删除成功");
    }

    /**
     * 发布（新建发布组）
     */
    @RequestMapping(value = "/toRelease/{appId}/{env}/{currentCluster}" , method = RequestMethod.GET)
    public Response<StargateVo> toReleasePage(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("currentCluster") String currentCluster){
        StargateVo stargateVo = stargates.get(env).get(appId);
        //这里模仿调用Stargate接口返回可发布的镜像和规格
        List<String> imageList = new ArrayList<>();
        imageList.add("framewoke/demo:1.0.0");
        imageList.add("framewoke/demo:1.0.1");
        imageList.add("framewoke/demo:1.0.2");
        imageList.add("framewoke/demo:latest");
        stargateVo.setImageList(imageList);
        List<String> releaseSpecificationList = new ArrayList<>();
        releaseSpecificationList.add("C-2C4G");
        stargateVo.setReleaseSpecificationList(releaseSpecificationList);
        return Response.mark(MessageType.SUCCESS, stargateVo);
    }


    /**
     * 提交
     */
    @RequestMapping(value = "/release" , method = RequestMethod.POST)
    public Response<String> submitStagateRelease(@RequestBody StargateVo stargateVo){
        //这里调用stagate发布接口，等待发布结构
        return Response.mark(MessageType.SUCCESS, "发布成功！");
    }

    @RequestMapping(value = "/releaseGroupLog/{appId}/{env}/{currentCluster}" , method = RequestMethod.GET)
    public Response<List<StargateLogVo>> getReleaseGroupLog(@PathVariable("appId") Long appId, @PathVariable("env") String env, @PathVariable("currentCluster") String currentCluster){
        LocalDateTime localDateTime = LocalDateTime.now();
        String applyDate = dateTimeFormatter.format(localDateTime);
        List<StargateLogVo> logs = new ArrayList<>();
        StargateLogVo log1 = new StargateLogVo();
        log1.setClusterName("currentCluster");
        log1.setReleaseGroupName("s244.g40");
        log1.setTaskType("调节流量");
        log1.setTaskMessage("{\"newSize\":0,\"token\":\"ey");
        log1.setTaskStaus("RUNNING");
        log1.setOperator("zhangxiao04");
        log1.setOperatorTime(applyDate);
        logs.add(log1);

        StargateLogVo log2 = new StargateLogVo();
        log2.setClusterName("currentCluster");
        log2.setReleaseGroupName("s244.g40");
        log2.setTaskType("调节流量");
        log2.setTaskMessage("{\"newSize\":0,\"toke");
        log2.setTaskStaus("SUCCESS");
        log2.setOperator("zhangxiao04");
        log2.setOperatorTime(applyDate);
        logs.add(log2);

        StargateLogVo log3 = new StargateLogVo();
        log3.setClusterName("currentCluster");
        log3.setReleaseGroupName("s244.g39");
        log3.setTaskType("调节流量");
        log3.setTaskMessage("{\"newSize\":1,\"token...");
        log3.setTaskStaus("FAIL");
        log3.setOperator("zhangxiao04");
        log3.setOperatorTime(applyDate);
        logs.add(log3);

        StargateLogVo log4 = new StargateLogVo();
        log4.setClusterName("currentCluster");
        log4.setReleaseGroupName("s244.g39");
        log4.setTaskType("创建发布组");
        log4.setTaskMessage("{\"newSize\":0,\"token\":\"ey...");
        log4.setTaskStaus("EXPIRED");
        log4.setOperator("zhangxiao04");
        log4.setOperatorTime(applyDate);
        logs.add(log4);

        return Response.mark(MessageType.SUCCESS, logs);
    }

    @RequestMapping(value = "/removeDomainNameMessage/{appId}/{env}", method = RequestMethod.GET)
    public Response<String> removeDomainNameMessage(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        DomainNameVo domainName = domainNames.get(env).get(appId);
        domainName.setApproveMessage(null);
        return Response.mark(MessageType.SUCCESS, "");
    }

    @RequestMapping(value = "/removeDBMessage/{appId}/{env}", method = RequestMethod.GET)
    public Response<String> removeDBMessage(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        DBVo db = dbs.get(env).get(appId);
        db.setApproveMessage(null);
        return Response.mark(MessageType.SUCCESS, "");
    }

    @RequestMapping(value = "/removeApolloMessage/{appId}/{env}", method = RequestMethod.GET)
    public Response<String> removeApolloMessage(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        ApolloVo apolloVo = appollos.get(env).get(appId);
        apolloVo.setApproveMessage(null);
        return Response.mark(MessageType.SUCCESS, "");
    }

    @RequestMapping(value = "/removeStargateMessage/{appId}/{env}", method = RequestMethod.GET)
    public Response<String> removeStargateMessage(@PathVariable("appId") Long appId, @PathVariable("env") String env){
        StargateVo stargate = stargates.get(env).get(appId);
        stargate.setApproveMessage(null);
        return Response.mark(MessageType.SUCCESS, "");
    }

    /**
     * 返回发布组列表
     * @param env
     * @param clusterName
     * @return
     */
    @RequestMapping(value = "/releaseGroupList/{env}/{clusterName}", method = RequestMethod.GET)
    public Response<List<ReleaseGroupVo>> releaseGroupList(@PathVariable("env") String env, @PathVariable("clusterName") String clusterName){
        List<ReleaseGroupVo> releaseGroupVos = null;
        if(releaseGroups.get(env) != null ) {
            releaseGroupVos = releaseGroups.get(env).get(clusterName);
        }
        return Response.mark(MessageType.SUCCESS, releaseGroupVos);
    }

    /**
     * 新建发布组
     * @param releaseGroupVo
     * @return
     */
    @RequestMapping(value = "/createReleaseGroup", method = RequestMethod.POST)
    public Response<List<ReleaseGroupVo>> createReleaseGroup(@RequestBody ReleaseGroupVo releaseGroupVo){
        String env = releaseGroupVo.getEnv();
        releaseGroupVo.setReleaseGroupName("s244.g43");
        releaseGroupVo.setRunningContainerQuantity(0);
        releaseGroupVo.setVersion("0.0.6_09");
        String clusterName = releaseGroupVo.getClusterName();
        if(releaseGroups.get(env) == null){
            Map<String,List<ReleaseGroupVo>> map = new HashMap<>(16);
            map.put(clusterName,new ArrayList<>());
            releaseGroups.put(env,map);
        }
        List<ReleaseGroupVo> releaseGroupVos = releaseGroups.get(env).get(clusterName);
        releaseGroupVos.add(releaseGroupVo);
        return Response.mark(MessageType.SUCCESS, releaseGroupVos);
    }

    @RequestMapping(value = "/updateReleaseGroup", method = RequestMethod.PUT)
    public Response<List<ReleaseGroupVo>> updateReleaseGroup(@RequestBody ReleaseGroupVo releaseGroupVo){
        String env = releaseGroupVo.getEnv();
        String clusterName = releaseGroupVo.getClusterName();
        List<ReleaseGroupVo> releaseGroupVos = releaseGroups.get(env).get(clusterName);
        for(ReleaseGroupVo temp : releaseGroupVos){
            if(temp.getReleaseGroupName().equals(releaseGroupVo.getReleaseGroupName())){
                temp.setRunningContainerQuantity(releaseGroupVo.getRunningContainerQuantity());
                temp.setContainerQuantity(releaseGroupVo.getContainerQuantity());
            }
        }
        return Response.mark(MessageType.SUCCESS, releaseGroupVos);
    }

    @RequestMapping(value = "/containerList", method = RequestMethod.GET)
    public Response<List<ContainerVo>> containerList(){
        List<ContainerVo> containerVoList = new ArrayList<>();

        ContainerVo containerVo = new ContainerVo();
        containerVo.setId(1);
        containerVo.setInstanceName("stargate.9900000000.s244.g43.1");
        containerVo.setIp("10.114.4.34");
        containerVo.setPort(6062);
        containerVo.setRuntimeStatus("UP");
        containerVo.setContainerId("616917505d6f");
        containerVo.setContainerStatus("Running");
        containerVo.setHostIp("10.114.2.30");

        ContainerVo containerVo2 = new ContainerVo();
        containerVo2.setId(2);
        containerVo2.setInstanceName("stargate.9900000000.s244.g43.2");
        containerVo2.setIp("10.114.5.227");
        containerVo2.setPort(6062);
        containerVo2.setRuntimeStatus("DOWN");
        containerVo2.setContainerId("73b848612274");
        containerVo2.setContainerStatus("Running");
        containerVo2.setHostIp("10.114.2.31");

        ContainerVo containerVo3 = new ContainerVo();
        containerVo3.setId(3);
        containerVo3.setInstanceName("stargate.9900000000.s244.g43.2");
        containerVo3.setIp("10.114.5.228");
        containerVo3.setPort(6062);
        containerVo3.setRuntimeStatus("DOWN");
        containerVo3.setContainerId("94a5e70225aa");
        containerVo3.setContainerStatus("Running");
        containerVo3.setHostIp("10.114.2.31");

        containerVoList.add(containerVo);
        containerVoList.add(containerVo2);
        containerVoList.add(containerVo3);
        return Response.mark(MessageType.SUCCESS, containerVoList);
    }

}
