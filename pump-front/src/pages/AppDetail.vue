<template>
    <div class="content-panel">
        <el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/apps' }">我的应用</el-breadcrumb-item>
                    <el-breadcrumb-item>应用详情</el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
        </el-row>

        <br/>

        <el-row>
            <el-col :span="12">
                <el-form label-width="100px" label-position="right">
                    <el-form-item label="当前环境">
                        <el-select v-model="env" placeholder="选择环境" @change="onChangeEnv" style="border-color: #409eff;">
                            <el-option v-for="item in envs" :key="item" :label="item" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="应用ID">
                        <el-input :value="app.appId" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="应用名称">
                        <el-input :value="app.appName" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="组织名称">
                        <el-input :value="app.orgName" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="应用级别">
                        <el-input :value="app.grade" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="应用类型">
                        <el-input :value="app.appType" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="服务类型">
                        <el-input :value="app.serviceType" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="申请日期">
                        <el-input :value="app.applyDate" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="审核日期">
                        <el-input :value="app.approveDate" readonly style="width: 217px"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="12">
                <el-form label-width="100px" label-position="right">
                    <el-form-item label="域名">
                        <div v-if="app.domainName==null">
                            <el-button type="primary" @click="handleApplyDomainNameDialog()">申请域名
                            </el-button>
                        </div>
                        <div v-else-if="app.domainName.status=='审核拒绝'">
                            <el-button type="primary" @click="handleApplyDomainNameDialog()">申请域名
                            </el-button>
                            <el-tag  type="danger" v-if="this.closeDomainNameTabFlag == false && app.domainName.approveMessage != null" closable @close="handleCloseDomainNameTag()">{{app.domainName.approveMessage}}</el-tag>
                        </div>
                        <el-tag  v-else-if="app.domainName.status == '审核中'" type="warning">审核中</el-tag>
                        <div v-else-if="app.domainName.status == '审核通过'">
                            <el-tag type="primary" plain>{{app.domainName.domainName}}</el-tag>
                            <el-tag type="success" v-if="this.closeDomainNameTabFlag == false && app.domainName.approveMessage != null" closable @close="handleCloseDomainNameTag()">{{app.domainName.approveMessage}}</el-tag>
                        </div>
                    </el-form-item>
                    <el-form-item label="数据库">
                        <div v-if="app.db==null">
                            <el-button type="primary" @click="handleApplyDBDialog()">申请数据库
                            </el-button>
                        </div>
                        <div v-else-if="app.db.status=='审核拒绝'">
                            <el-button type="primary" @click="handleApplyDBDialog()">申请数据库
                            </el-button>
                            <el-tag type="danger" v-if="this.closeDBTabFlag == false && app.db.approveMessage != null" closable @close="handleCloseDBTag()">{{app.db.approveMessage}}</el-tag>
                        </div>
                        <el-tag  v-else-if="app.db.status == '审核中'" type="warning">申请中</el-tag>
                        <div v-else-if="app.db.status == '审核通过'">
                            <el-button type="primary" plain @click="handleDBDetailDialog()">查看数据库信息
                            </el-button>
                            <el-tag  type="success" v-if="this.closeDBTabFlag == false && app.db.approveMessage != null" closable @close="handleCloseDBTag()">{{app.db.approveMessage}}</el-tag>
                        </div>
                    </el-form-item>
                    <el-form-item label="Apollo">
                        <div v-if="app.apollo==null">
                            <el-button type="primary" @click="handleConfiguredOnApollo()">创建项目
                            </el-button>
                        </div>
                        <div v-else-if="app.apollo.status=='审核拒绝'">
                            <el-button type="primary" @click="handleConfiguredOnApollo()">创建项目
                            </el-button>
                            <el-tag  type="danger" v-if="this.closeApolloTabFlag == false && app.apollo.approveMessage != null" closable @close="handleCloseApolloTag()">{{app.apollo.approveMessage}}</el-tag>
                        </div>
                        <el-tag  v-else-if="app.apollo.status == '审核中'" type="warning">申请中</el-tag>
                        <div v-else-if="app.apollo.status == '审核通过'">
                            <el-button type="primary" plain @click="handleManageApollo()">管理配置
                            </el-button>
                            <el-tag  type="success" v-if="this.closeApolloTabFlag == false && app.apollo.approveMessage != null" closable @close="handleCloseApolloTag()">{{app.apollo.approveMessage}}</el-tag>
                        </div>
                    </el-form-item>
                    <el-form-item label="消费者组">
                        <el-button type="primary" @click="handleConsumerGroupList()">消费者组列表
                        </el-button>
                    </el-form-item>
                    <el-form-item label="Topic">
                        <el-button type="primary" @click="handleTopicList()">Topic列表
                        </el-button>
                    </el-form-item>
                    <el-form-item label="Cache">
                        <el-button type="primary" @click="handleCacheList()">Cache列表
                        </el-button>
                    </el-form-item>
                    <el-form-item label="容器云" v-if="app.domainName!=null && app.domainName.status!='审核中'">
                        <div v-if="app.stargate == null">
                            <el-button type="primary"  @click="handlenContainerCloud()">创建应用</el-button>
                        </div>
                        <div v-else-if="app.stargate.status == '审核拒绝'">
                            <el-button type="primary"  @click="handlenContainerCloud()">创建应用</el-button>
                            <el-tag  type="danger" v-if="this.closeStargateTabFlag == false && app.stargate.approveMessage != null" closable @close="handleCloseStaragateTag()">{{app.stargate.approveMessage}}</el-tag>
                        </div>
                        <el-tag  v-else-if="app.stargate.status == '审核中'" type="warning">审核中</el-tag>
                        <div v-else-if="app.stargate.status == '审核通过'">
                            <el-button type="primary"  @click="handlenStagateList()">集群列表</el-button>
                            <el-tag  type="success" v-if="this.closeStargateTabFlag == false && app.stargate.approveMessage != null" closable @close="handleCloseStaragateTag()">{{app.stargate.approveMessage}}</el-tag>
                        </div>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>

        <el-dialog title="申请域名" :visible.sync="openApplyDomainNameDialog" width="400px"
                   :before-close="closeApplyDomainNameDialog">
            <el-form label-width="60px" label-position="left" :model="domainNameModel" ref="domainNameModelForm"
                     :rules="domainNameRules">
                <el-form-item label="域名" prop="domainName">
                    <el-input placeholder="请输入域名" v-model="domainNameModel.domainName"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeApplyDomainNameDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="onApplyDomainName" style="float:right;margin:0 10px 0 0;">申请
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="申请数据库" :visible.sync="openApplyDBDialog" width="400px" :before-close="closeApplyDBDialog">
            <el-form label-width="80px" label-position="left" :model="dbModel" ref="dbModelForm" :rules="dbRules">
                <el-form-item label="DB类型" prop="dbType" required>
                    <el-select v-model="dbModel.dbType" placeholder="请选择数据库类型" filterable style="width: 100%">
                        <el-option v-for="item in dbTypes" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="DB名称" prop="dbName" required>
                    <el-input placeholder="请输入数据库名称" v-model="dbModel.dbName"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeApplyDBDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="onApplyDB" style="float:right;margin:0 10px 0 0;">申请</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="数据库明细" :visible.sync="openApplyDBDetailDialog" width="500px">
            <el-form label-width="80px" label-position="left" :model="dbModel">
                fat-2.mysql.test.com（10.tst.8.31)<br/>
                port:3405<br/>
                db:ppdai_pump<br/>
                user:user_app_pump<br/>
                pwd:7V*xM0si5Jgy<br/>
                privs: select,insert,update,delete,execute
            </el-form>
        </el-dialog>

        <el-dialog title="管理Apollo配置" :visible.sync="openManageApolloDialog" width="1400px">
            <el-row>
                <el-button type="primary">发布</el-button>
            </el-row>

            <br/>

            <el-table
                    :data="applloTableData"
                    stripe border
                    style="width: 100%" align="center">
                <el-table-column
                        prop="publishStatus"
                        label="发布状态"
                        width="100" align="center">
                </el-table-column>
                <el-table-column
                        prop="key"
                        label="Key"
                        width="250" align="center">
                </el-table-column>
                <el-table-column
                        prop="value"
                        label="Value"
                        width="300" align="center">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        label="备注"
                        width="200" align="center">
                </el-table-column>
                <el-table-column
                        prop="updateBy"
                        label="最后修改人"
                        width="150" align="center">
                </el-table-column>
                <el-table-column
                        prop="updateTime"
                        label="最后修改时间"
                        width="200" align="center">
                </el-table-column>
                <el-table-column
                        label="操作"
                        width="150" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" size="medium"><i class="el-icon-edit" title="编辑"></i></el-button>
                        <el-button type="text" size="medium"><i class="el-icon-delete" title="删除"></i></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="Cache列表" :visible.sync="openCacheListDialog" width="900px">
            <el-row>
                <el-button type="primary" @click="handlenApplyCache()">申请Cache</el-button>
            </el-row>

            <br/>

            <el-table :data="cacheList" style="width: 100%" border fit>
                <el-table-column label="ID" prop="id" align="center"></el-table-column>
                <el-table-column label="CacheID" prop="cacheId" align="center">
                    <template scope="props">
                        <a href="http://fat-cache.test.com/admin/app/list" target="_blank" style="color: inherit">{{props.row.cacheId}}</a>
                    </template>
                </el-table-column>
                <el-table-column label="应用类型" prop="storeType" align="center"></el-table-column>
                <el-table-column label="内存详情" prop="usedMemory" align="center"></el-table-column>
                <el-table-column label="命中率" prop="hitRate" align="center"></el-table-column>
                <el-table-column label="已运行时间" prop="runtime" align="center"></el-table-column>
                <el-table-column label="状态" prop="status" align="center"></el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="申请Cache" :visible.sync="openApplyCacheDialog" width="500px" top="0">
            <el-form label-width="130px" label-position="right" :model="applyNewCache" ref="applyNewCacheForm"
                     :rules="applyNewCacheRules">
                <el-form-item label="应用描述" prop="appDescription" required>
                    <el-input placeholder="请输入应用描述" v-model="applyNewCache.appDescription"></el-input>
                </el-form-item>
                <el-form-item label="应用种类" prop="storeType" required>
                    <el-select v-model="applyNewCache.storeType" placeholder="请选择存储类型" filterable style="width: 100%">
                        <el-option v-for="item in storeTypes" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="内存总量" prop="memory" required>
                    <el-input placeholder="请输入内存总量" v-model="applyNewCache.memory"></el-input>
                </el-form-item>
                <el-form-item label="项目负责人" prop="personInCharge" required>
                    <el-input placeholder="请输入项目负责人" v-model="applyNewCache.personInCharge"></el-input>
                </el-form-item>
                <el-form-item label="测试" prop="test">
                    <el-select v-model="applyNewCache.test" placeholder="是否测试" filterable style="width: 100%">
                        <el-option v-for="item in whether" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否有数据源" prop="dataSource">
                    <el-select v-model="applyNewCache.dataSource" placeholder="是否有数据源" filterable style="width: 100%">
                        <el-option v-for="item in whether" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否持久化" prop="persistent">
                    <el-select v-model="applyNewCache.persistent" placeholder="是否需要持久化" filterable style="width: 100%">
                        <el-option v-for="item in whether" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否热备" prop="persistent">
                    <el-select v-model="applyNewCache.hotBackup" placeholder="是否需要热备" filterable style="width: 100%">
                        <el-option v-for="item in whether" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="预估QPS" prop="qps" required>
                    <el-input placeholder="请输入预估QPS" v-model="applyNewCache.qps"></el-input>
                </el-form-item>
                <el-form-item label="预估条目数" prop="termQuantity" required>
                    <el-input placeholder="请输入预估条目数" v-model="applyNewCache.termQuantity"></el-input>
                </el-form-item>
                <el-form-item label="客户端机房" prop="generatorRoom" required>
                    <el-input placeholder="请输入客户端机房" v-model="applyNewCache.generatorRoom"></el-input>
                </el-form-item>
                <el-form-item label="内存报警阈值" prop="memoryUsedWarningThreshold" required>
                    <el-input placeholder="请输入内存报警阈值" v-model="applyNewCache.memoryUsedWarningThreshold"></el-input>
                </el-form-item>
                <el-form-item label="连接数报警阀值" prop="connectionQuantityWarningThreshold" required>
                    <el-input placeholder="请输入客户端连接数报警阀值"
                              v-model="applyNewCache.connectionQuantityWarningThreshold"></el-input>
                </el-form-item>

                <el-form-item style="margin-bottom: 0">
                    <el-button type="primary" @click="onSubmitapplyNewCache" style="float:right;">申请
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="消费者组列表" :visible.sync="openConsumerGroupListDialog" width="900px">
            <el-row>
                <el-button type="primary" @click="handlenApplyConsumerGroup()">创建消费者组</el-button>
            </el-row>

            <br/>

            <el-table :data="consumerGroupList" style="width: 100%" border fit>
                <el-table-column label="ID" prop="id" align="center"></el-table-column>
                <el-table-column label="名称" prop="consumerGroupName" align="center"></el-table-column>
                <el-table-column label="数量" prop="quantity" align="center"></el-table-column>
                <el-table-column label="消费的Topic" prop="topicName" align="center"></el-table-column>
                <el-table-column label="申请人" prop="applicant" align="center"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center"></el-table-column>
                <el-table-column label="状态" prop="status" align="center"></el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="创建消费者组" :visible.sync="openApplyConsumerGroupDialog" width="500px">
            <el-form label-width="120px" label-position="left" :model="applyNewConsumerGroup"
                     ref="applyConsumerGroupForm" :rules="applyNewConsumerGroupRules">
                <el-form-item label="消费者组名称" prop="consumerGroupName">
                    <el-input placeholder="请输入消费者组名称" v-model="applyNewConsumerGroup.consumerGroupName"></el-input>
                </el-form-item>
                <el-form-item label="消费者数量" prop="quantity">
                    <el-input placeholder="请输入消费者数量" v-model="applyNewConsumerGroup.quantity"></el-input>
                </el-form-item>
                <el-form-item label="消费的Topic" prop="topicName">
                    <el-input placeholder="消费的Topic" v-model="applyNewConsumerGroup.topicName"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button type="primary" @click="onSubmitApplyNewConsumerGroup"
                               style="float:right;">申请
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="Topic列表" :visible.sync="openTopicListDialog" width="900px">
            <el-row>
                <el-button type="primary" @click="handlenApplyTopic()">创建Topic</el-button>
            </el-row>

            <br/>

            <el-table :data="topicList" style="width: 100%" border fit>
                <el-table-column label="ID" prop="id" align="center"></el-table-column>
                <el-table-column label="名称" prop="topicName" align="center"></el-table-column>
                <el-table-column label="部门" prop="department" align="center"></el-table-column>
                <el-table-column label="延时告警" prop="delayedThreshold" align="center"></el-table-column>
                <el-table-column label="保存天数" prop="retentionDays" align="center"></el-table-column>
                <el-table-column label="负责人" prop="personInCharge" align="center"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" align="center"></el-table-column>
                <el-table-column label="状态" prop="status" align="center"></el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="创建Topic" :visible.sync="openApplyTopicDialog" width="500px">
            <el-form label-width="120px" label-position="left" :model="applyNewTopic" ref="applyTopicForm"
                     :rules="applyNewTopicRules">
                <el-form-item label="Topic名称" prop="topicName">
                    <el-input placeholder="请输入Topic名称" v-model="applyNewTopic.topicName"></el-input>
                </el-form-item>
                <el-form-item label="延时告警阈值" prop="delayedThreshold">
                    <el-input placeholder="请输入延时告警阈值" v-model="applyNewTopic.delayedThreshold"></el-input>
                </el-form-item>
                <el-form-item label="保存天数" prop="retentionDays">
                    <el-input placeholder="请输入保存天数" v-model="applyNewTopic.retentionDays"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button type="primary" @click="onSubmitApplyNewTopic" style="float:right;">申请
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="创建容器云应用" :visible.sync="openApplyContainerCloud" width="400px">
            <el-form label-width="120px" label-position="left" :model="stargate" ref="applyContainerCloudForm"
                     :rules="applyContainerCloudRules">
                <!--<el-form-item label="实例个数" prop="instanceQuantity" required="true">
                    <el-input placeholder="请输入实例个数" v-model="stargate.instanceQuantity"></el-input>
                </el-form-item>-->
                <el-form-item label="测试人员" prop="testers">
                    <el-input placeholder="请输入测试人员" v-model="stargate.testers"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button type="primary" @click="onSubmitApplyContainerCloud()" style="float:right;">申请
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="集群列表" :visible.sync="openStageListDialog" width="1200px">
            <el-row>
                <el-button type="primary" @click="handleCreateCluster()">新建集群</el-button>
            </el-row>

            <br/>

            <el-table :data="stargateList" style="width: 100%" border fit>
                <el-table-column label="应用名称" prop="appName" align="center"></el-table-column>
                <el-table-column label="环境" prop="env" align="center"></el-table-column>
                <el-table-column label="组织" prop="orgName" align="center"></el-table-column>
                <el-table-column label="集群名称" prop="currentCluster" align="center"></el-table-column>
                <el-table-column label="域名" prop="domainName" align="center"></el-table-column>
                <el-table-column label="负责人" prop="personInCharge" align="center"></el-table-column>
                <el-table-column label="操作" align="center" width="300">
                    <template scope="props">
                        <el-button @click="handleToReleaseGroupList(props.row.currentCluster)" size="small" type="success">发布组<i class="el-icon-d-arrow-right el-icon--right"></i></el-button>
                        <el-button @click="handleToHistory(props.row.currentCluster)" size="small" type="primary" plain>历史<i class="el-icon-search el-icon--right"></i></el-button>
                        <el-button @click="handleDeleteCluster(props.row.currentCluster)" size="small" type="danger">删除<i class="el-icon-delete el-icon--right"></i></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="创建集群" :visible.sync="openCreateClusterDialog" width="400px">
            <el-form label-width="120px" label-position="left" :model="stargate" ref="createClusterForm"
                     :rules="createClusterRules">
                <el-form-item label="集群名称" prop="currentCluster">
                    <el-input placeholder="请输入集群名称" v-model="stargate.currentCluster"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button type="primary" @click="onSubmitCreateCluster()" style="float:right;">提交
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="新建发布组" :visible.sync="openCreateReleaseGroupDialog" width="400px">
            <el-form label-width="120px" label-position="left" :model="releaseGroup" ref="createReleaseGroupForm"
                     :rules="createReleaseGroupRules">
                <el-form-item label="应用镜像" prop="image">
                    <el-select v-model="releaseGroup.image" placeholder="请选择应用镜像" filterable style="width: 100%">
                        <el-option v-for="item in imageList" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="发布规格" prop="releaseSpecification">
                    <el-select v-model="releaseGroup.releaseSpecification" placeholder="请选择发布规格" filterable style="width: 100%">
                        <el-option v-for="item in releaseSpecificationList" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="实例个数" prop="containerQuantity">
                    <el-input placeholder="请输入实例个数" v-model="releaseGroup.containerQuantity"></el-input>
                </el-form-item>
                <el-form-item label="开放端口" prop="port">
                    <el-input placeholder="请输入开放端口" v-model="releaseGroup.port"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button type="primary" @click="onSubmitReleaseGroup()" style="float:right;">提交
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="发布历史" :visible.sync="openReleaseHistoryDialog" width="1200px">
            <el-row class="query-form">
                <el-date-picker
                        v-model="queryForm.date"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="timestamp"
                        :default-time="['00:00:00', '23:59:59']"
                        :picker-options="pickerOptions"
                        class="query-box"
                        @change="onSearch">
                </el-date-picker>

                <el-button type="primary" @click="onSearch">查询</el-button>
                <el-button @click="resetQueryForm">重置</el-button>
            </el-row>

            <br/>

            <el-table :data="releaseHistoryList" style="width: 100%" border fit>
                <el-table-column label="发布组名" prop="releaseGroupName" align="center" width="150"></el-table-column>
                <el-table-column label="任务类型" prop="taskType" align="center" width="150"></el-table-column>
                <el-table-column label="任务信息" prop="taskMessage" align="center"></el-table-column>
                <el-table-column label="任务状态" align="center" width="120">
                    <template slot-scope="props">
                        <el-tag v-if="props.row.taskStaus == 'SUCCESS'" type="success">{{props.row.taskStaus}}</el-tag>
                        <el-tag v-else-if="props.row.taskStaus == 'FAIL'" type="danger">{{props.row.taskStaus}}</el-tag>
                        <el-tag v-else-if="props.row.taskStaus == 'EXPIRED'" type="warning">{{props.row.taskStaus}}</el-tag>
                        <el-tag v-else-if="props.row.taskStaus == 'RUNNING'">{{props.row.taskStaus}}</el-tag>
                        <el-tag v-else type="info">{{props.row.taskStaus}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作者" prop="operator" align="center" width="170"></el-table-column>
                <el-table-column label="操作时间" prop="operatorTime" align="center" width="180"></el-table-column>
                <el-table-column label="操作" align="center" width="120">
                    <template scope="props">
                        <el-button @click="displayReleaseLog(props.row.releaseGroupId,props.row.clusterName,props.row.id)" size="small" type="primary" plain>日志<i class="el-icon-search el-icon--right"></i></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="发布组列表" :visible.sync="releaseGroupListDialog" width="841px">
            <el-row>
                <el-button type="primary" @click="handleCreateRelieaseGroup()">新建发布组</el-button>
            </el-row>
            <br/>
            <el-table :data="releaseGroupList" style="width: 100%" border fit>
                <el-table-column label="发布组名" prop="releaseGroupName" align="center" width="100"></el-table-column>
                <el-table-column label="配额" prop="releaseSpecification" align="center" width="100"></el-table-column>
                <el-table-column label="容器数量" prop="containerQuantity" align="center" width="100"></el-table-column>
                <el-table-column label="当前流量" prop="runningContainerQuantity" align="center" width="100"></el-table-column>
                <el-table-column label="操作" align="center" width="400">
                    <template scope="props">
                        <el-button @click="handleAdjustContainers(props.row.releaseGroupName,props.row.containerQuantity)" size="small" type="primary">调整容器</el-button>
                        <el-button @click="handleAdjustFlux(props.row.releaseGroupName,props.row.runningContainerQuantity)" size="small" type="primary">调整流量</el-button>
                        <el-button @click="handleContainerList()" size="small" type="primary">容器列表</el-button>
                        <el-button @click="deleteReleaseGroup(props.row.id)" size="small" type="danger" plain>删除发布组</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <el-dialog title="调整容器数" :visible.sync="handleAdjustContainersDialog" width="400px">
            <el-form label-width="120px" label-position="left" :model="releaseGroup">
                <el-row :gutter="10">
                    <el-col :span="18" style="position: relative">
                        <span>当前容器数</span>
                        <el-input-number size="small" v-model="releaseGroup.containerQuantity" :min="1"></el-input-number>
                    </el-col>
                    <el-col :span="6">
                        <el-button @click="submitAdjustContainers()" type="primary">容量调整</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>

        <el-dialog title="调整流量" :visible.sync="handleAdjustFluxDialog" width="400px">
            <el-form label-width="120px" label-position="left" :model="releaseGroup">
                <el-row :gutter="10">
                    <el-col :span="18" style="position: relative">
                        <span>当前流量</span>
                        <el-input-number size="small" v-model="releaseGroup.runningContainerQuantity" :min="0"></el-input-number>
                    </el-col>
                    <el-col :span="6">
                        <el-button @click="submitAdjustFlux()" type="primary">流量调整</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-dialog>

        <el-dialog title="容器列表" :visible.sync="openContainerListDialog" width="1334px">
            <el-table :data="containerList" style="width: 100%" border fit>
                <el-table-column label="序号" prop="id" align="center" width="50">
                    <template slot-scope="props">
                        <el-checkbox>{{props.row.id}}</el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column label="实例名" prop="instanceName" align="center" width="300"></el-table-column>
                <el-table-column label="IP" prop="ip" align="center" width="150"></el-table-column>
                <el-table-column label="端口" prop="port" align="center" width="100"></el-table-column>
                <el-table-column label="接入流量" prop="runtimeStatus" align="center" width="100">
                    <template slot-scope="props">
                        <el-tag v-if="props.row.runtimeStatus == 'UP'" type="success">{{props.row.runtimeStatus}}</el-tag>
                        <el-tag v-else-if="props.row.runtimeStatus == 'DOWN'" type="danger">{{props.row.runtimeStatus}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="容器Id" prop="containerId" align="center" width="150"></el-table-column>
                <el-table-column label="容器状态" prop="containerStatus" align="center" width="100">
                    <template slot-scope="props">
                        <el-tag v-if="props.row.containerStatus == 'Running'" type="success">{{props.row.containerStatus}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="物理机" prop="hostIp" align="center" width="100"></el-table-column>
                <el-table-column label="操作" align="center" width="200">
                    <template scope="props">
                        <el-button @click="" size="small" type="primary">拉入/拉出</el-button>
                        <el-button @click="" size="small" type="danger" plain>删除实例</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <br>
            <el-row>
                <el-button type="primary" @click="">批量拉入</el-button>
                <el-button type="primary" @click="">批量拉出</el-button>
                <el-button type="danger" plain  @click="">批量删除</el-button>
            </el-row>
            <br/>
        </el-dialog>
    </div>
</template>

<script>

    import {mapActions, mapGetters} from "vuex";

    export default {
        name: "appDetails",
        data() {
            return {
                env: '',
                envs: ['fat', 'uat', 'pro'],
                currentPage: 1,
                pageSize: 10,
                queryForm: {
                    appId: ''
                },
                openApplyDomainNameDialog: false,
                openApplyDBDialog: false,
                openApplyDBDetailDialog: false,
                openManageApolloDialog: false,
                openApplyCacheDialog: false,
                openCacheListDialog: false,
                openConsumerGroupListDialog: false,
                openApplyConsumerGroupDialog: false,
                openApplyTopicDialog: false,
                openTopicListDialog: false,
                openApplyContainerCloud: false,
                openStageListDialog:false,
                openCreateClusterDialog:false,
                openCreateReleaseGroupDialog:false,
                openReleaseHistoryDialog:false,
                releaseGroupListDialog:false,
                handleAdjustContainersDialog:false,
                handleAdjustFluxDialog:false,
                openContainerListDialog:false,
                domainNameModel: {domainName: ''},
                domainNameRules: {
                    domainName: [
                        {required: true, message: '请输入域名', trigger: 'blur'}
                    ]
                },
                dbModel: {dbType: '', dbName: ''},
                dbTypes: ['MySQL', 'SQL Server', 'MongoDB'],
                dbRules: {
                    dbType: [
                        {required: true, message: '请选择数据库类型', trigger: 'blur'}
                    ],
                    dbName: [
                        {required: true, message: '请输入数据库名称', trigger: 'blur'}
                    ]
                },
                storeTypes: ['Redis-cluster', 'Redis-sentinel', 'Redis-standalone'],
                whether: ['是', '否'],
                applyNewCache: {
                    id: '',
                    appId: '',
                    env: '',
                    appDescription: '',
                    storeType: '',
                    memory: '',
                    personInCharge: '',
                    test: '',
                    dataSource: '',
                    persistent: '',
                    hotBackup: '',
                    qps: '',
                    termQuantity: '',
                    generatorRoom: '',
                    memoryUsedWarningThreshold: '',
                    connectionQuantityWarningThreshold: ''
                },
                applyNewTopicRules: {
                    topicName: [
                        {required: true, message: '请输入Topic名称', trigger: 'blur'}
                    ],
                    delayedThreshold: [
                        {required: true, message: '请输入延时告警阈值', trigger: 'blur'}
                    ],
                    retentionDays: [
                        {required: true, message: '请输入保存天数', trigger: 'blur'}
                    ]
                },
                applyNewCacheRules: {
                    appDescription: [
                        {required: true, message: '请输入应用描述', trigger: 'blur'}
                    ],
                    storeType: [
                        {required: true, message: '请选择存储类型', trigger: 'blur'}
                    ],
                    memory: [
                        {required: true, message: '请输入内存总量', trigger: 'blur'}
                    ],
                    personInCharge: [
                        {required: true, message: '请输入项目负责人', trigger: 'blur'}
                    ],
                    qps: [
                        {required: true, message: '请输入预估QPS', trigger: 'blur'}
                    ],
                    termQuantity: [
                        {required: true, message: '请输入预估条目数', trigger: 'blur'}
                    ],
                    generatorRoom: [
                        {required: true, message: '请输入客户端机房', trigger: 'blur'}
                    ],
                    memoryUsedWarningThreshold: [
                        {required: true, message: '请输入内存报警阈值', trigger: 'blur'}
                    ],
                    connectionQuantityWarningThreshold: [
                        {required: true, message: '请输入客户端连接数报警阀值', trigger: 'blur'}
                    ]
                },
                applyContainerCloudRules: {
                    tester: [
                        {required: true, message: '请输入测试人员', trigger: 'blur'}
                    ]
                },
                createClusterRules:{
                    currentCluster: [
                        {required: true, message: '请输入集群名称', trigger: 'blur'}
                    ]
                },
                applyNewConsumerGroup: {id: '', appId: '', env: '', consumerGroupName: '', quantity: '', topicName: ''},
                applyNewConsumerGroupRules: {
                    consumerGroupName: [
                        {required: true, message: '请输入消费者组名称', trigger: 'blur'}
                    ],
                    quantity: [
                        {required: true, message: '请输入消费者数量', trigger: 'blur'}
                    ],
                    topicName: [
                        {required: true, message: '请输入消费的Topic', trigger: 'blur'}
                    ]
                },
                applyNewTopic: {
                    id: '',
                    appId: '',
                    env: '',
                    topicName: '',
                    department: '',
                    delayedThreshold: '',
                    retentionDays: ''
                },
                stargate: {
                    id: '',
                    appId: '',
                    env: '',
                    orgName:'',
                    domainName:'',
                    personInCharge:'',
                    appType:'',
                    serviceType:'',
                    grade:'',
                    testers:'',
                    port:'',
                    status:'',
                    currentCluster:'',
                    instanceQuantity:'',
                    image:'',
                    releaseSpecification:''
                },
                releaseGroup: {
                    id: '',
                    env: '',
                    releaseGroupName:'',
                    version:'',
                    port:'',
                    image:'',
                    clusterName:'',
                    containerQuantity:'',
                    runningContainerQuantity:'',
                    releaseSpecification:''
                },
                closeDomainNameTabFlag : false,
                closeDBTabFlag : false,
                closeApolloTabFlag : false,
                closeStargateTabFlag : false,
                applloTableData: [{
                    publishStatus: '已发布',
                    key: 'server.port',
                    value: '8080',
                    remark:'',
                    updateBy:'test',
                    updateTime:'2018-05-07 18:37:07'
                }, {
                    publishStatus: '已发布',
                    key: 'mybatis.type-aliases-package',
                    value: 'com.test.dockeryard.core.mapper',
                    remark:'',
                    updateBy:'test',
                    updateTime:'2018-05-07 18:37:07'
                }, {
                    publishStatus: '已发布',
                    key: 'mybatis.mapper-locations',
                    value: 'classpath*:mapper/*Mapper.xml',
                    remark:'',
                    updateBy:'test',
                    updateTime:'2018-05-07 18:37:07'
                }, {
                    publishStatus: '已发布',
                    key: 'spring.datasource.driverClassName',
                    value: 'com.mysql.jdbc.Driver',
                    remark:'',
                    updateBy:'test',
                    updateTime:'2018-05-07 18:37:07'
                }, {
                    publishStatus: '已发布',
                    key: 'spring.datasource.url',
                    value: 'jdbc:mysql://10.test.8.31:3405/test?useUnicode=true&characterEncoding=utf-8&useSSL=false',
                    remark:'',
                    updateBy:'test',
                    updateTime:'2018-05-07 18:37:07'
                }],
                currentClusterName:''
            }
        },
        created() {
            let query = this.$route.query;
            this.queryForm.appId = query && query.appId ? query.appId : null;
            this.env = query && query.env ? query.env : null;
            this.$store.dispatch('getApp', {appId: this.queryForm.appId, env: this.env});
        },
        computed: {
            ...mapGetters({
                app: 'getApp',
                cacheList: 'getCacheList',
                consumerGroupList: 'getConsumerGroupList',
                topicList: 'getTopicList',
                stargateList: 'getStargateList',
                imageList: 'getImageList',
                releaseSpecificationList: 'getReleaseSpecificationList',
                releaseHistoryList: 'getReleaseHistoryList',
                releaseGroupList : 'getReleaseGroupList',
                containerList : 'getContainerList'
            })
        },
        methods: {
            onChangeEnv(data) {
                this.$router.push({name: 'appDetail', query: {appId: this.queryForm.appId, env: data}});
                this.$store.dispatch('getApp', {appId: this.queryForm.appId, env: data});
            },
            handleApplyDomainNameDialog() {
                this.openApplyDomainNameDialog = true;
            },
            closeApplyDomainNameDialog () {
                this.openApplyDomainNameDialog = false;
                this.$refs["domainNameModelForm"].resetFields();
            },
            onApplyDomainName () {
                this.$refs["domainNameModelForm"].validate((valid) => {
                    if (valid) {
                        this.$store.dispatch('applyDomainName', {
                            appId: this.queryForm.appId,
                            domainName: this.domainNameModel.domainName,
                            env: this.env
                        });
                        this.closeApplyDomainNameDialog();
                    }
                });
            },
            handleApplyDBDialog() {
                this.openApplyDBDialog = true;
            },
            closeApplyDBDialog() {
                this.openApplyDBDialog = false;
                this.$refs["dbModelForm"].resetFields();
            },
            onApplyDB(){
                this.$refs["dbModelForm"].validate((valid) => {
                    if (valid) {
                        this.$store.dispatch('applyDB', {
                            appId: this.queryForm.appId,
                            dbName: this.dbModel.dbName,
                            env: this.env
                        });
                        this.closeApplyDBDialog();
                    }
                });
            },
            handleDBDetailDialog(){
                this.openApplyDBDetailDialog = true;
            },
            handleConfiguredOnApollo(){
                this.$store.dispatch('applyApollo', {
                    appId: this.queryForm.appId,
                    env: this.env
                });
            },
            handleManageApollo(){
                this.openManageApolloDialog = true;
            },
            handleCacheList(){
                this.$store.dispatch('fetchCacheList', {
                    appId: this.queryForm.appId,
                    env: this.env
                });
                this.openCacheListDialog = true;
            },
            handlenApplyCache(){
                this.openApplyCacheDialog = true;
            },
            onSubmitapplyNewCache(){
                this.$refs["applyNewCacheForm"].validate((valid) => {
                    if (valid) {
                        this.applyNewCache.appId = this.queryForm.appId;
                        this.applyNewCache.env = this.env;
                        this.$store.dispatch('applyNewCache', {
                            applyRequest: this.applyNewCache,
                            queryRequest: {
                                appId: this.queryForm.appId,
                                env: this.env
                            }
                        });
                        this.openApplyCacheDialog = false;
                    }
                });
            },
            handleConsumerGroupList(){
                this.$store.dispatch('fetchCustomerGroupList', {
                    appId: this.queryForm.appId,
                    env: this.env
                });
                this.openConsumerGroupListDialog = true;
            },
            handlenApplyConsumerGroup(){
                this.openApplyConsumerGroupDialog = true;
            },
            onSubmitApplyNewConsumerGroup(){
                this.$refs["applyConsumerGroupForm"].validate((valid) => {
                    if (valid) {
                        this.applyNewConsumerGroup.appId = this.queryForm.appId;
                        this.applyNewConsumerGroup.env = this.env;
                        this.$store.dispatch('applyNewustomerGroup', {
                            applyRequest: this.applyNewConsumerGroup,
                            queryRequest: {
                                appId: this.queryForm.appId,
                                env: this.env
                            }
                        });
                        this.openApplyConsumerGroupDialog = false;
                    }
                });
            },
            handleTopicList(){
                this.$store.dispatch('fetchTopicList', {
                    appId: this.queryForm.appId,
                    env: this.env
                });
                this.openTopicListDialog = true;
            },
            handlenApplyTopic(){
                this.openApplyTopicDialog = true;
            },
            onSubmitApplyNewTopic(){
                this.$refs["applyTopicForm"].validate((valid) => {
                    if (valid) {
                        this.applyNewTopic.appId = this.queryForm.appId;
                        this.applyNewTopic.env = this.env;
                        this.$store.dispatch('applyNewTopic', {
                            applyRequest: this.applyNewTopic,
                            queryRequest: {
                                appId: this.queryForm.appId,
                                env: this.env
                            }
                        });
                        this.openApplyTopicDialog = false;
                    }
                });
            },
            handlenContainerCloud(){
                this.openApplyContainerCloud = true;
            },
            onSubmitApplyContainerCloud(){
                this.$refs["applyContainerCloudForm"].validate((valid) => {
                    if (valid) {
                        this.stargate.appId = this.queryForm.appId;
                        this.stargate.env = this.env;
                        this.$store.dispatch('applyNewContainerCloud', {
                            applyRequest: this.stargate,
                            queryRequest: {
                                appId: this.queryForm.appId,
                                env: this.env
                            }
                        });
                        this.openApplyContainerCloud = false;
                    }
                });
            },
            handlenStagateList(){
                this.$store.dispatch('fetchStargateList', {
                    appId: this.queryForm.appId,
                    env: this.env
                });
                this.openStageListDialog = true;
            },
            handleCreateCluster(){
                this.openCreateClusterDialog = true;
            },
            onSubmitCreateCluster(){
                this.$refs["createClusterForm"].validate((valid) => {
                    if (valid) {
                        this.stargate.appId = this.queryForm.appId;
                        this.stargate.env = this.env;
                        this.$store.dispatch('createNewCluster', {
                            applyRequest: this.stargate,
                            queryRequest: {
                                appId: this.queryForm.appId,
                                env: this.env
                            }
                        });
                        this.openCreateClusterDialog = false;
                    }
                });
            },
            handleDeleteCluster(currentCluster){
                this.$confirm('此操作将删除该集群, 是否继续？', '删除站点', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    this.stargate.appId = this.queryForm.appId;
                    this.stargate.env = this.env;
                    this.stargate.currentCluster = currentCluster;
                    this.$store.dispatch('deleteCluster', {
                        applyRequest: this.stargate,
                        queryRequest: {
                            appId: this.queryForm.appId,
                            env: this.env
                        }
                    });
                }).catch(() => {
                    this.$message({
                        'type': 'info',
                        'message': '取消删除'
                    });
                });
            },
            handleToReleaseGroupList(currentCluster){
                this.$store.dispatch('toReleaseGroupList', {
                    env:this.env,
                    clusterName:currentCluster
                });
                this.currentClusterName = currentCluster;
                this.releaseGroupListDialog = true;
            },
            //新建发布组页面
            handleCreateRelieaseGroup(){
                this.stargate.appId = this.queryForm.appId;
                this.stargate.env = this.env;
                this.stargate.currentCluster = this.currentClusterName;
                this.$store.dispatch('toRelease', {
                    applyRequest: this.stargate,
                    queryRequest: {
                        appId: this.queryForm.appId,
                        env: this.env
                    }
                });
                this.openCreateReleaseGroupDialog = true;
            },
            onSubmitReleaseGroup(){
                this.releaseGroup.env = this.env;
                this.releaseGroup.clusterName = this.currentClusterName;
                this.$store.dispatch('createReleaseGroup', {
                    applyRequest: this.releaseGroup/*,
                    queryRequest: {
                        appId: this.queryForm.appId,
                        env: this.env
                    }*/
                });
                this.openCreateReleaseGroupDialog = false;
            },
            handleToHistory(currentCluster){
                this.stargate.appId = this.queryForm.appId;
                this.stargate.env = this.env;
                this.stargate.currentCluster = currentCluster;
                this.$store.dispatch('toHistory', {
                    applyRequest: this.stargate,
                    queryRequest: {
                        appId: this.queryForm.appId,
                        env: this.env
                    }
                });
                this.openReleaseHistoryDialog = true;
            },
            resetQueryForm() {
                this.queryForm.date = '';
                this.currentPage = 1;
            },
            handleCloseDomainNameTag(){
                this.$store.dispatch('removeDomainNameMessage', {
                    queryRequest: {
                        appId: this.queryForm.appId,
                        env: this.env
                    }
                });
                this.closeDomainNameTabFlag = true;
            },
            handleCloseDBTag(){
                this.$store.dispatch('removeDBMessage', {
                    queryRequest: {
                        appId: this.queryForm.appId,
                        env: this.env
                    }
                });
                this.closeDBTabFlag = true;
            },
            handleCloseApolloTag(){
                this.$store.dispatch('removeApolloMessage', {
                    queryRequest: {
                        appId: this.queryForm.appId,
                        env: this.env
                    }
                });
                this.closeApolloTabFlag = true;
            },
            handleCloseStaragateTag(){
                this.$store.dispatch('removeStargateMessage', {
                    queryRequest: {
                        appId: this.queryForm.appId,
                        env: this.env
                    }
                });
                this.closeStargateTabFlag = true;
            },
            handleAdjustContainers(releaseGroupName,containerQuantity){
                this.releaseGroup.containerQuantity = containerQuantity;
                this.releaseGroup.releaseGroupName = releaseGroupName;
                this.handleAdjustContainersDialog = true;
            },
            submitAdjustContainers(){
                this.$store.dispatch('adjustContainers', {
                    applyRequest: this.releaseGroup
                });
                this.handleAdjustContainersDialog = false;
            },
            handleAdjustFlux(releaseGroupName,runningContainerQuantity){
                this.releaseGroup.runningContainerQuantity = runningContainerQuantity;
                this.releaseGroup.releaseGroupName = releaseGroupName;
                this.handleAdjustFluxDialog = true;
            },
            submitAdjustFlux(){
                this.$store.dispatch('adjustFlux', {
                    applyRequest: this.releaseGroup
                });
                this.handleAdjustFluxDialog = false;
            },
            handleContainerList(){
                this.$store.dispatch('fetchContainerList', {
                    applyRequest: this.releaseGroup
                });
                this.openContainerListDialog = true;
            }
        }
    }
</script>

<style>
    .detail-form {
        margin: 20px 0 0;
    }

    .detail-box {
        display: inline-flex;
        font-size: 14px;
        margin-right: 40px;
    }

    .detail-box-left {
        border: 1px solid #dcdfe6;
        border-right: 0;
        border-radius: 4px;
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
        box-sizing: border-box;
        background-color: #f5f7fa;
        color: #909399;
        display: inline-block;
        height: 40px;
        line-height: 40px;
        padding: 0 10px;
    }

    .detail-box-right {
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
        box-sizing: border-box;
        color: #606266;
        display: inline-block;
        height: 40px;
        line-height: 40px;
        padding: 0 20px;
    }

</style>