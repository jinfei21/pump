<template>
    <div class="content-panel">
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/release/docker' }">站点管理</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/clusterList' }">集群列表</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
            <br/>
            <el-row>
                <el-button type="primary" @click="handleCreateCluster()">新建集群</el-button>
            </el-row>

            <br/>

            <el-table :data="tableData" style="width: 100%" border fit>
                <el-table-column label="应用名称" prop="appName" align="center"></el-table-column>
                <el-table-column label="环境" prop="env" align="center"></el-table-column>
                <el-table-column label="组织" prop="orgName" align="center"></el-table-column>
                <el-table-column label="集群名称" prop="currentCluster" align="center"></el-table-column>
                <el-table-column label="域名" prop="domainName" align="center"></el-table-column>
                <el-table-column label="负责人" prop="personInCharge" align="center"></el-table-column>
                <el-table-column label="操作" align="center" width="300">
                    <template scope="props">
                        <el-button @click="handleToReleaseGroupList()" size="small" type="success">发布组<i class="el-icon-d-arrow-right el-icon--right"></i></el-button>
                        <el-button @click="handleToHistory()" size="small" type="primary" plain>历史<i class="el-icon-search el-icon--right"></i></el-button>
                        <el-button @click="handleDeleteCluster()" size="small" type="danger">删除<i class="el-icon-delete el-icon--right"></i></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-dialog title="创建集群" :visible.sync="openCreateClusterDialog" width="400px">
                <el-form label-width="120px" label-position="left" :model="cluster">
                    <el-form-item label="集群名称" prop="currentCluster">
                        <el-input placeholder="请输入集群名称" v-model="cluster.name"></el-input>
                    </el-form-item>
                    <el-form-item style="margin-bottom: 0">
                        <el-button type="primary" @click="onSubmitCreateCluster()" style="float:right;">提交
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <el-dialog title="发布历史" :visible.sync="openHistoryDialog" width="1200px">
                <el-row class="query-form">
                    <el-date-picker
                            v-model="date"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            value-format="timestamp"
                            :default-time="['00:00:00', '23:59:59']"
                            :picker-options="pickerOptions"
                            class="query-box"
                            @change="">
                    </el-date-picker>

                    <el-button type="primary" @click="">查询</el-button>
                    <el-button @click="">重置</el-button>
                </el-row>

                <br/>

                <el-table :data="historyList" style="width: 100%" border fit>
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
                            <el-button @click="" size="small" type="primary" plain>日志<i class="el-icon-search el-icon--right"></i></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ClusterList",
        data(){
            return {
                isPassed: false,
                openCreateClusterDialog:false,
                openHistoryDialog:false,
                cluster:{name:''},
                tableData:[],
                historyList:[],
                date:''
            }
        },
        created() {
            this.isPassed = false;
        },
        methods: {
            handleCreateCluster(){
                this.openCreateClusterDialog = true;
            },
            onSubmitCreateCluster(){
                this.tableData = [{
                    appName:'test',
                    env:'fat',
                    orgName:'基础框架',
                    currentCluster:this.cluster.name,
                    domainName:'test.test.com',
                    personInCharge:'xxx'}];
                this.openCreateClusterDialog = false;
            },
            handleToReleaseGroupList(){
                this.$router.push({name: 'releaseGroupList', query: {}});
            },
            handleToHistory(){
                this.historyList = [{
                    releaseGroupName:'s244.g43',
                    taskType:'创建发布组',
                    taskMessage:'{"newSize":3,"token":"e',
                    taskStaus:'SUCCESS',
                    operator:'xxx',
                    operatorTime:'2018-12-14'
                },{
                    releaseGroupName:'s244.g42',
                    taskType:'扩容/缩容',
                    taskMessage:'{"newSize":2,"token"',
                    taskStaus:'FAIL',
                    operator:'yyy',
                    operatorTime:'2018-12-15'
                },{
                    releaseGroupName:'s244.g41',
                    taskType:'调节流量',
                    taskMessage:'{"newSize":1,"token',
                    taskStaus:'EXPIRED',
                    operator:'zzz',
                    operatorTime:'2018-12-16'
                },{
                    releaseGroupName:'s244.g42',
                    taskType:'扩容/缩容',
                    taskMessage:'{"newSize":1,"token"',
                    taskStaus:'RUNNING',
                    operator:'xxx',
                    operatorTime:'2018-12-17'
                }];
                this.openHistoryDialog = true;
            },
            handleDeleteCluster(){

            }
        }
    }
</script>

<style scoped>

</style>