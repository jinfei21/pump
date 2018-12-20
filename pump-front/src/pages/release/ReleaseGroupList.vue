<template>
    <div class="content-panel">
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/release/docker' }">站点管理</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/clusterList' }">集群列表</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/releaseGroupList' }">发布组</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
            <br/>
            <el-row>
                <el-button type="primary" @click="handleCreateReleaseGroup()">新建发布组</el-button>
            </el-row>
            <br/>
            <el-table :data="tableData" style="width: 100%" border fit>
                <el-table-column label="发布组名称" prop="name" align="center"></el-table-column>
                <el-table-column label="版本号" prop="version" align="center"></el-table-column>
                <el-table-column label="实例数" prop="instanceQuantity" align="center" width="200"></el-table-column>
                <el-table-column label="流量" prop="flow" align="center">
                    <template scope="props">
                        <el-input-number size="small" v-model="props.row.newFlow" :min="0" :max="Number(props.row.instanceQuantity)" class="group-size"></el-input-number>
                        <el-button @click="changeFlow(props.row.id,props.row.newFlow,Number(props.row.instanceQuantity))" size="small">调整流量</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="流量状态" prop="flowStatus" align="center">
                    <template scope="props">
                        <el-progress :text-inside="true" :stroke-width="18" :percentage=props.row.flowStatus></el-progress>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="400">
                    <template scope="props">
                        <el-button @click="handleToContainerList(props.row.port)" size="small" type="success">实例列表<i class="el-icon-d-arrow-right el-icon--right"></i></el-button>
                        <el-button @click="handleToReleaseHistory()" size="small" type="primary" plain>发布历史<i class="el-icon-search el-icon--right"></i></el-button>
                        <el-button @click="handleDeleteReleaseGroup()" size="small" type="danger">删除<i class="el-icon-delete el-icon--right"></i></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-dialog title="新建发布组" :visible.sync="openCreateReleaseGroupDialog" width="400px">
                <el-form label-width="120px" label-position="left" :model="releaseGroup">
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

            <el-dialog title="容器列表" :visible.sync="openContainerListDialog" width="1391px">
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
                    <el-table-column label="操作" align="center" width="300">
                        <template scope="props">
                            <el-button @click="containerDown(props.row.id)" size="small" type="primary" v-if="props.row.runtimeStatus == 'UP'">拉出</el-button>
                            <el-button @click="containerUp(props.row.id)" size="small" type="primary" plain v-else>拉入</el-button>
                            <!--<el-button @click="containerLog" size="small" plain>容器日志</el-button>-->
                            <el-button @click="" size="small" type="danger" plain>删除实例</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <br>
                <el-row>
                    <el-button type="primary" @click="batchOn">批量拉入</el-button>
                    <el-button type="primary" @click="batchDown">批量拉出</el-button>
                    <el-button type="danger" plain  @click="batchDelete">批量删除</el-button>
                </el-row>
                <br/>
            </el-dialog>

            <el-dialog title="发布历史" :visible.sync="openReleaseHistoryDialog" width="1200px">
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
                            <el-button @click="" size="small" type="primary" plain>日志<i class="el-icon-search el-icon--right"></i></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>

            <el-dialog title="容器日志" :visible.sync="containerLogVisible" width="70%">
                <i @click="" class="el-icon-refresh refresh-log-button"></i>
                <div class="release-log">{{this.containerLogUrl}}</div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ReleaseGroupList",
        data(){
            return {
                groupId:0,
                isPassed: false,
                openCreateReleaseGroupDialog : false,
                openContainerListDialog: false,
                openReleaseHistoryDialog: false,
                releaseGroup : {id:'', image : '', releaseSpecification: '', containerQuantity:'' , port:'', newFlow:''},
                imageList:['framework/demo:1.0.0','framework/demo:1.0.1','framework/demo:1.0.2','framework/demo:latest'],
                releaseSpecificationList:['2C4G','4C8G'],
                tableData:[],
                containerList:[],
                releaseHistoryList:[],
                containerLogVisible: false,
                containerLogUrl: 'https://test:8443/#!/shell/default-framework/stargate.9900000000.s244.g46.1/?namespace=default-framework'
            }
        },
        created() {
            this.isPassed = false;
        },
        methods: {
            handleCreateReleaseGroup(){
                this.openCreateReleaseGroupDialog = true;
            },
            onSubmitReleaseGroup(){
                this.groupId = this.groupId +1;
                let tempReleaseGroup = {
                    id:this.groupId,
                    name:'s244.g43',
                    version:'0.0.6_09',
                    port: this.releaseGroup.port,
                    instanceQuantity:this.releaseGroup.containerQuantity,
                    flow:'0',
                    newFlow:'0',
                    flowStatus:null
                };
                tempReleaseGroup.flowStatus = tempReleaseGroup.flow/this.releaseGroup.containerQuantity*100;
                this.tableData.push(tempReleaseGroup);
                this.openCreateReleaseGroupDialog = false;
            },
            handleToContainerList(port){
                this.containerList = [{
                    id:1,
                    instanceName:'stargate.9900000000.s244.g43.1',
                    ip:'10.tes.34',
                    port:port,
                    runtimeStatus:'UP',
                    containerId:'616917505d6f',
                    containerStatus:'Running',
                    hostIp:'10.114.2.30'
                },{
                    id:2,
                    instanceName:'stargate.9900000000.s244.g43.2',
                    ip:'10.tes.5.227',
                    port:port,
                    runtimeStatus:'DOWN',
                    containerId:'73b848612274',
                    containerStatus:'Running',
                    hostIp:'10.tes.2.31'
                },{
                    id:3,
                    instanceName:'stargate.9900000000.s244.g43.3',
                    ip:'10.tes.5.228',
                    port:port,
                    runtimeStatus:'DOWN',
                    containerId:'94a5e70225aa',
                    containerStatus:'Running',
                    hostIp:'10.tes.2.31'
                }];
                this.openContainerListDialog = true;
            },
            handleToReleaseHistory(){
                this.releaseHistoryList = [{
                    releaseGroupName:'s244.g43',
                    taskType:'创建发布组',
                    taskMessage:'{"newSize":3,"token":"e',
                    taskStaus:'SUCCESS',
                    operator:'xxx',
                    operatorTime:'2018-12-14'
                },{
                    releaseGroupName:'s244.g43',
                    taskType:'扩容/缩容',
                    taskMessage:'{"newSize":2,"token"',
                    taskStaus:'SUCCESS',
                    operator:'yyy',
                    operatorTime:'2018-12-15'
                },{
                    releaseGroupName:'s244.g43',
                    taskType:'调节流量',
                    taskMessage:'{"newSize":1,"token',
                    taskStaus:'SUCCESS',
                    operator:'zzz',
                    operatorTime:'2018-12-16'
                },{
                    releaseGroupName:'s244.g43',
                    taskType:'扩容/缩容',
                    taskMessage:'{"newSize":1,"token"',
                    taskStaus:'SUCCESS',
                    operator:'xxx',
                    operatorTime:'2018-12-17'
                }];
                this.openReleaseHistoryDialog = true;
            },
            handleDeleteReleaseGroup(){

            },
            resetQueryForm(){
                this.date = '';
            },
            batchOn(){
                let size = this.containerList.length;
                for(let index = 0; index < size ; index ++){
                    this.containerList[index].runtimeStatus = 'UP';
                }
                this.$notify.success({
                    title: '',
                    message: '批量拉入成功。'
                });
            },
            batchDown(){
                let size = this.containerList.length;
                for(let index = 0; index < size ; index ++){
                    this.containerList[index].runtimeStatus = 'DOWN';
                }
                this.$notify.success({
                    title: '',
                    message: '批量拉出成功。'
                });
            },
            batchDelete(){

            },
            containerDown(id){
                let index = id - 1;
                this.containerList[index].runtimeStatus = 'DOWN';
                this.$notify.success({
                    title: '',
                    message: '拉出成功。'
                });
            },
            containerUp(id){
                let index = id - 1;
                this.containerList[index].runtimeStatus = 'UP';
                this.$notify.success({
                    title: '',
                    message: '拉入成功。'
                });
            },
            changeFlow(id,newFlow,instanceQuantity){
                let index = id - 1;
                this.tableData[index].flowStatus = newFlow/instanceQuantity*100;
                this.$notify.success({
                    title: '',
                    message: '调整流量成功。'
                });
            },
            containerLog(){
                this.containerLogVisible = true;
            }
        }
    }
</script>

<style scoped>

</style>