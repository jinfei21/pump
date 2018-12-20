<template>
    <div class="content-panel">
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>我的应用</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
        </div>
        <div>
            <el-row class="query-form">
                <el-col :span="3">
                    <el-input v-model="query.appId" placeholder="输入应用Id"></el-input>
                </el-col>
                <el-col :span="3" :offset="1">
                    <el-input v-model="query.appName" placeholder="输入应用名称"></el-input>
                </el-col>
                <el-col :span="6" :offset="1">
                    <el-button type="primary" @click="onSearch">查询</el-button>
                    <el-button @click="resetQueryForm">重置</el-button>
                </el-col>
                <el-button type="primary" style="float: right" @click="openApplyNewApp">申请应用</el-button>
            </el-row>

            <el-table :data="tableData" style="width: 100%" border fit>
                <el-table-column label="AppID" prop="appIp" align="center" width="160"></el-table-column>
                <el-table-column label="应用名称" prop="appName" align="center"></el-table-column>
                <el-table-column label="组织名称" prop="orgName" align="center"></el-table-column>
                <el-table-column label="应用级别" prop="grade" align="center"></el-table-column>
                <el-table-column label="应用类型" prop="appType" align="center"></el-table-column>
                <el-table-column label="服务类型" prop="serviceType" align="center"></el-table-column>
                <el-table-column label="审核状态" prop="applyStatus" align="center">
                    <template slot-scope="props">
                        <el-tag v-if="isPassed" type="success">审核通过</el-tag>
                        <el-tag v-else type="warning">等待审核</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="350">
                    <template scope="props">
                        <!--<el-button @click="handleDetail(props.row.appId)" size="small" v-if="isPassed">详情</el-button>-->
                        <el-button @click="handleMembers(props.row.appId)" size="small" v-if="isPassed">成员</el-button>
                        <el-button @click="handleDelete()" type="danger" size="small" v-if="isPassed">删除</el-button>
                        <el-button @click="opneOAFlow()" size="small">查看流程</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <br>
            <el-row>
                <el-button type="primary" plain @click="imitateOA()">模拟审批</el-button>
            </el-row>
        </div>

        <el-dialog title="申请应用" :visible.sync="applyNewAppDialogVisible" width="500px" :before-close="onCloseApplyNewApp">
            <el-form label-width="80px" label-position="left" :model="applyNewApp" ref="inApplyNewAppForm" :rules="inApplyNewAppRules">
                <el-form-item label="应用名称" prop="appName"required>
                    <el-input placeholder="请输入应用名称" v-model="applyNewApp.appName"></el-input>
                </el-form-item>
                <el-form-item label="组织名称" prop="orgName" required>
                    <el-select v-model="applyNewApp.orgName" placeholder="请选择组织名称" filterable style="width: 100%">
                        <el-option v-for="item in orgNames" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="应用级别" prop="grade" required>
                    <el-select v-model="applyNewApp.grade" placeholder="请选择应用级别" filterable style="width: 100%">
                        <el-option v-for="item in grades" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="应用类型" prop="appType" required>
                    <el-select v-model="applyNewApp.appType" placeholder="请选择应用类型" filterable style="width: 100%">
                        <el-option v-for="item in appTypes" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="服务类型" prop="serviceType" required>
                    <el-select v-model="applyNewApp.serviceType" placeholder="请选择服务类型" filterable style="width: 100%">
                        <el-option v-for="item in serviceTypes" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item style="margin-bottom: 0">
                    <el-button @click="onCloseApplyNewApp" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="onSubmitApplyNewApp" style="float:right;margin:0 10px 0 0;">申请</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="选择环境" :visible.sync="selectEnvDialogVisible" width="280px" >
            <el-form label-position="top">
                <el-button type="primary" @click="enterOnEnv('fat')">FAT</el-button>
                <el-button type="primary" @click="enterOnEnv('uat')">UAT</el-button>
                <el-button type="primary" @click="enterOnEnv('pro')">PRO</el-button>
            </el-form>
        </el-dialog>

        <el-dialog title="成员管理" :visible.sync="memberDialogVisible" width="650px">
            <el-form label-width="80px" label-position="right">
                <el-form-item label="添加成员">
                    <el-input v-model="addMember" placeholder="输入域账号" style="width: 200px"></el-input>
                    <el-button @click="onAddMember" type="primary" plain>添加</el-button>
                </el-form-item>
                <el-form-item label="已有成员">
                    <el-tag v-for="item in members"
                            :key="item" closable type="primary"
                            @close="onRemoveMember(item)">
                        {{item}}
                    </el-tag>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="OA流程图" :visible.sync="openOAFlowDialog" width="500px">
            <el-steps :space="200" :active="1" finish-status="success">
                <el-step title="申请人"></el-step>
                <el-step title="直接上级"></el-step>
                <el-step title="部门领导"></el-step>
                <el-step title="运维"></el-step>
            </el-steps>
        </el-dialog>

        <el-dialog title="OA流程图" :visible.sync="openOAFlowDownDialog" width="500px">
            <el-steps :space="200" :active="4" finish-status="success">
                <el-step title="申请人"></el-step>
                <el-step title="直接上级"></el-step>
                <el-step title="部门领导"></el-step>
                <el-step title="运维"></el-step>
            </el-steps>
        </el-dialog>
    </div>
</template>

<script>

    import {mapActions, mapGetters} from "vuex";

    export default {
        data() {
            return {
                isPassed:false,
                appId:'',
                currentPage: 1,
                pageSize: 10,
                query: {
                    appId: '',
                    appName: ''
                },
                selectEnvDialogVisible:false,
                applyNewAppDialogVisible: false,
                memberDialogVisible: false,
                openOAFlowDialog:false,
                openOAFlowDownDialog:false,
                members: [],
                addMember: '',
                orgNames:['实时数据平台', '基础架构', '借入研发'],
                serviceTypes: ['服务提供', '服务消费', 'Job', '其他'],
                appTypes: ['Tomcat', 'Django', 'SpringBoot', '其他'],
                grades: ['一般业务', '重要业务', '核心业务'],
                applyNewApp: {appId:'', appName:'', orgName:'', grade:'', appType:'', serviceType:''},
                inApplyNewAppRules: {
                    appName: [
                        {required: true, message: '请输入应用名称', trigger: 'blur'}
                    ],
                    orgName: [
                        {required: true, message: '请选择组织名称', trigger: 'change'},
                    ],
                    grade: [
                        {required: true, message: '请选择应用级别', trigger: 'change'}
                    ],
                    appType: [
                        {required: true, message: '请选择应用类型', trigger: 'change'},
                    ],
                    serviceType: [
                        {required: true, message: '请选择服务类型', trigger: 'change'},
                    ]
                },
                tableData: []
            }
        },
        created() {
            //this.refreshAppList();
            this.isPassed = false;
            this.members.push(this.userName);
        },
        computed: {
            ...mapGetters({
                apps: 'getAppList',
                total: 'getAppCount',
                userName: 'getUserName'
            })
        },
        methods: {
            handleDetail(appId) {
                this.appId = appId;
                this.selectEnvDialogVisible = true;
            },
            handleMembers(appId) {
                this.memberDialogVisible = true;
            },
            onAddMember() {
                if (this.addMember != '') {
                    let isMemberExist = false;
                    for (let member of this.members) {
                        if (member == this.addMember) {
                            isMemberExist = true;
                        }
                    }
                    if (!isMemberExist) {
                        this.members.push(this.addMember);
                    } else {
                        this.$message.warning('成员已存在')
                    }
                    this.addMember = '';
                }
            },
            onRemoveMember(member) {
                this.members.splice(this.members.indexOf(member), 1);
            },
            handleDelete(appId) {
                this.$confirm('此操作将删除该站点申请的所有资源, 是否继续？', '删除站点', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    this.tableData = [];
                }).catch(() => {
                    this.$message({
                        'type': 'info',
                        'message': '取消删除'
                    });
                });
            },
            enterOnEnv(env){
                this.$router.push({name: 'appDetail', query: {appId: this.appId, env: env}});
            },
            openApplyNewApp() {
                this.applyNewAppDialogVisible = true;
            },
            onCloseApplyNewApp() {
                this.applyNewAppDialogVisible = false;
                this.$refs["inApplyNewAppForm"].resetFields();
            },
            onSubmitApplyNewApp() {
                /*this.$refs["inApplyNewAppForm"].validate((valid) => {
                    if (valid) {
                        this.$store.dispatch('applyNewApp', {
                            appName: this.applyNewApp.appName,
                            orgName: this.applyNewApp.orgName,
                            grade: this.applyNewApp.grade,
                            appType: this.applyNewApp.appType,
                            serviceType: this.applyNewApp.serviceType
                        });
                        this.onCloseApplyNewApp();
                    }
                });*/
                this.isPassed = false;
                this.tableData = [{
                    appIp : '',
                    appName : this.applyNewApp.appName,
                    orgName : this.applyNewApp.orgName,
                    grade:this.applyNewApp.grade,
                    appType:this.applyNewApp.appType,
                    serviceType:this.applyNewApp.serviceType
                }];
                this.onCloseApplyNewApp();
            },
            onSearch() {
                this.currentPage = 1;
                alert(123);
                //this.refreshSiteList();

                /*if (this.queryForm.appId != '') {
                    localStroageUtil.saveLastQueryAppId(this.queryForm.appId);
                }*/
            },

            handleSizeChange(data) {
                this.pageSize = data;
                this.refreshUsers();
            },
            handleCurrentChange(data) {
                this.currentPage = data;
                this.refreshUsers();
            },
            refreshAppList() {
                this.$store.dispatch('fetchAppList');
            },
            refreshUsers() {
                let data = {
                    page: this.currentPage - 1,
                    size: this.pageSize,
                    realName: this.query.realName,
                    name: this.query.name,
                    shortCode: this.query.shortCode
                };
                this.$store.dispatch('fetchUsersByPage', data);
            },
            resetQueryForm() {
                this.query.realName = '';
                this.query.name = '';
                this.currentPage = 1;
                this.query.orgId = '';
                this.refreshUsers();
            },
            syncUserList() {
                this.$store.dispatch('syncUserList');
            },
            imitateOA(){
                this.tableData[0].appIp='100001';
                this.isPassed = true;
            },
            opneOAFlow(){
                if(this.isPassed){
                    this.openOAFlowDownDialog = true;
                }else {
                    this.openOAFlowDialog = true;
                }
            }
        }
    }

</script>

<style>
    .content-panel {
        padding: 20px;
    }
    .query-form {
        margin-bottom: 10px;
    }
    .el-tag + .el-tag {
        margin-left: 5px;
    }
</style>