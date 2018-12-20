import {api} from '../../api'
import demoService from "~/api/service/demoService";

const state = {
    appList: [{appId: '10001', appName: 'demo'}],
    appCount: 0,
    userList: [],
    promptMessage: {
        code: null,
        details: null
    },
    app: {},
    cacheList: null,
    consumerGroupList : null,
    topicList : null,
    stargateList : null,
    imageList : null,
    releaseSpecificationList : null,
    releaseHistoryList : null,
    releaseGroupList : null,
    containerList : null
};

const getters = {
    getUserList: state => state.userList,
    getPromptMessage: state => state.promptMessage,
    getAppList: state => state.appList,
    getAppCount: state => state.appCount,
    getApp: state => state.app,
    getCacheList : state => state.cacheList,
    getConsumerGroupList : state => state.consumerGroupList,
    getTopicList : state => state.topicList,
    getStargateList : state => state.stargateList,
    getImageList : state => state.imageList,
    getReleaseSpecificationList : state => state.releaseSpecificationList,
    getReleaseHistoryList : state => state.releaseHistoryList,
    getReleaseGroupList : state => state.releaseGroupList,
    getContainerList : state => state.containerList
};

const actions = {
    /**
     * 发送请求到后端服务，获取app列表
     * @param commit     store state更新提交者
     * @param dispatch  store action分发者
     */
    fetchAppList ({commit, dispatch}) {
        api.demoService.getAppList().then(function (resp) {
            commit('refreshAppList', resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyNewApp({commit, dispatch}, data) {
        api.demoService.applyNewApp(data).then(function (resp) {
            dispatch('fetchAppList');
            dispatch("displayPromptByResponseMsg", resp);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    deleteApp({commit, dispatch}, data){
        api.demoService.deleteApp(data).then(function (resp) {
            commit('refreshAppList', resp.data.details);
            dispatch("displayPromptByResponseMsg", resp);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    getApp ({commit, dispatch}, data) {
        api.demoService.getApp(data.appId, data.env).then(function (resp) {
            commit('saveApp', resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyDomainName ({commit, dispatch}, data) {
        api.demoService.applyDomainName(data).then(function (res) {
            dispatch("getApp", data);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyDB ({commit, dispatch}, data) {
        api.demoService.applyDB(data).then(function (res) {
            dispatch("getApp", data);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyApollo({commit, dispatch}, data) {
        api.demoService.applyApollo(data).then(function (res) {
            dispatch("getApp", data);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    fetchCacheList({commit, dispatch}, data) {
        api.demoService.getCacheList(data.appId, data.env).then(function (res) {
            commit('refreshCache', res.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyNewCache({commit, dispatch}, data){
        api.demoService.applyNewCache(data.applyRequest).then(function (res) {
            dispatch("fetchCacheList", data.queryRequest);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    fetchCustomerGroupList({commit, dispatch}, data) {
        api.demoService.getCustomerGroupList(data.appId, data.env).then(function (res) {
            commit('refreshConsumerGroup', res.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyNewustomerGroup({commit, dispatch}, data){
        api.demoService.applyNewCustomerGroup(data.applyRequest).then(function (res) {
            dispatch("fetchCustomerGroupList", data.queryRequest);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    fetchTopicList({commit, dispatch}, data) {
        api.demoService.getTopicList(data.appId, data.env).then(function (res) {
            commit('refreshTopic', res.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyNewTopic({commit, dispatch}, data){
        api.demoService.applyNewTopic(data.applyRequest).then(function (res) {
            dispatch("fetchTopicList", data.queryRequest);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    applyNewContainerCloud({commit, dispatch}, data){
        api.demoService.applyNewContainerCloud(data.applyRequest).then(function (res) {
            dispatch("getApp", data.queryRequest);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    fetchStargateList({commit, dispatch}, data){
        api.demoService.getStargateList(data.appId, data.env).then(function (res) {
            commit('refreshStagateList', res.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    createNewCluster({commit, dispatch}, data){
        api.demoService.createNewCluster(data.applyRequest).then(function (res) {
            dispatch("fetchStargateList", data.queryRequest);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    deleteCluster({commit, dispatch}, data){
        api.demoService.deleteCluster(data.applyRequest.appId, data.applyRequest.env, data.applyRequest.currentCluster).then(function (res) {
            dispatch("fetchStargateList", data.queryRequest);
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    toRelease({commit, dispatch}, data){
        api.demoService.toRelease(data.applyRequest.appId, data.applyRequest.env, data.applyRequest.currentCluster).then(function (res) {
            commit('refreshReleaseElement', res.data.details)
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    toHistory({commit, dispatch}, data){
        api.demoService.toHistory(data.applyRequest.appId, data.applyRequest.env, data.applyRequest.currentCluster).then(function (res) {
            commit('refreshHistoryList', res.data.details)
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    removeDomainNameMessage({commit, dispatch}, data){
        api.demoService.removeDomainNameMessage(data.queryRequest.appId, data.queryRequest.env);
    },
    removeDBMessage({commit, dispatch}, data){
        api.demoService.removeDBMessage(data.queryRequest.appId, data.queryRequest.env);
    },
    removeApolloMessage({commit, dispatch}, data){
        api.demoService.removeApolloMessage(data.queryRequest.appId, data.queryRequest.env);
    },
    removeStargateMessage({commit, dispatch}, data){
        api.demoService.removeStargateMessage(data.queryRequest.appId, data.queryRequest.env);
    },
    toReleaseGroupList({commit, dispatch}, data){
        api.demoService.toReleaseGroupList(data.env,data.clusterName).then(function (res) {
            commit('refreshReleaseGroupList', res.data.details)
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    createReleaseGroup({commit, dispatch}, data){
        api.demoService.createReleaseGroup(data.applyRequest).then(function (res) {
            commit('refreshReleaseGroupList', res.data.details)
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    adjustContainers({commit, dispatch}, data){
        api.demoService.adjustContainers(data.applyRequest).then(function (res) {
            commit('refreshReleaseGroupList', res.data.details)
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    adjustFlux({commit, dispatch}, data){
        api.demoService.adjustContainers(data.applyRequest).then(function (res) {
            commit('refreshReleaseGroupList', res.data.details)
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    fetchContainerList({commit, dispatch}, data){
        api.demoService.getContainerList().then(function (res) {
            commit('refreshContainerGroupList', res.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveApp({commit, dispatch}, data){
        api.demoService.approveApp(data.applyRequest.id,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveDomainName({commit, dispatch}, data){
        api.demoService.approveDomainName(data.applyRequest.appId,data.applyRequest.env,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveDB({commit, dispatch}, data){
        api.demoService.approveDB(data.applyRequest.appId,data.applyRequest.env,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveApollo({commit, dispatch}, data){
        api.demoService.approveApollo(data.applyRequest.appId,data.applyRequest.env,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveConsumerGroup({commit, dispatch}, data){
        api.demoService.approveConsumerGroup(data.applyRequest.appId,data.applyRequest.env,data.applyRequest.id,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveTopic({commit, dispatch}, data){
        api.demoService.approveTopic(data.applyRequest.appId,data.applyRequest.env,data.applyRequest.id,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveCache({commit, dispatch}, data){
        api.demoService.approveCache(data.applyRequest.appId,data.applyRequest.env,data.applyRequest.id,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    approveStargate({commit, dispatch}, data){
        api.demoService.approveStargate(data.applyRequest.appId,data.applyRequest.env,data.applyRequest.isPass).then(function (resp) {
            alert(resp.data.details);
            //dispatch("displayPromptByResponseMsg", resp.data.details);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    addNewUser({commit, dispatch}, data) {
        api.userService.createUser(data).then(function (res) {
            dispatch('fetchUserList');
            dispatch("displayPromptByResponseMsg", res);
        }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    displayPromptByResponseMsg({commit}, response){
        if (response != null && response.status != null && response.status == 200) {
            let data = response.data;
            commit("refreshPromptMessage", {code: data.code, details: data.details});
        } else {
            let errorMsg = "请求失败，";
            if (response == null) {
                errorMsg += "访问后端服务返回异常。";
            } else if (response.status != null && response.data != null) {
                // 发生后端处理过的错误
                errorMsg += "返回码：" + response.status + "，返回消息：" + response.data;
            } else if (response.status != null && response.status >= 400 && response.status < 500) {
                // 发生4XX错误
                errorMsg += "返回码：" + response.status + "，返回消息：" + response.statusText;
            } else if (response.status != null && response.status >= 500 && response.status < 600) {
                // 发生5XX错误
                errorMsg += "请检查后端服务是否工作正常。";
                errorMsg += "返回码：" + response.status + "，返回消息：" + response.statusText;
            } else if (response.status != null) {
                errorMsg += "返回码：" + response.status + "，返回消息：" + response.statusText;
            } else {
                errorMsg += "请检查后端服务是否工作正常。";
                errorMsg += "消息：" + response;
            }
            commit("refreshPromptMessage", {code: -1, details: errorMsg});
        }
    }
};

const mutations = {
    refreshAppList(state, data) {
        state.appList = data.content;
        state.appCount = data.totalElements;
    },
    refreshUserList(state, data) {
        state.userList = data;
    },
    refreshPromptMessage(state, data) {
        state.promptMessage = data;
    },
    saveApp(state,data) {
        state.app = data;
    },
    refreshCache(state, data) {
        state.cacheList = data;
    },
    refreshConsumerGroup(state, data) {
        state.consumerGroupList = data;
    },
    refreshTopic(state, data) {
        state.topicList = data;
    },
    refreshStagateList(state, data) {
        state.stargateList = data;
    },
    refreshReleaseElement(state, data) {
        state.imageList = data.imageList;
        state.releaseSpecificationList = data.releaseSpecificationList;
    },
    refreshHistoryList(state, data) {
        state.releaseHistoryList = data;
    },
    refreshReleaseGroupList(state, data) {
        state.releaseGroupList = data;
    },
    refreshContainerGroupList(state, data) {
        state.containerList = data;
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}