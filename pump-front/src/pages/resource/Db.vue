<!--
<template>
    <div>
        {{msg}}
    </div>
</template>

<script>
    import {mapActions, mapGetters} from "vuex"

    export default {
        data () {
            return {
                msg: '这个是Home模板页'
            }
        },
        computed: {
            ...mapGetters({
                appId: 'getAppId',
                env: 'getEnv'
            })
        },
        watch: {
            $route: function (newRoute) {
                alert(newRoute.query.env)
            }
        }
    }
</script>

<style>

</style>-->
<template>
    <div class="content-panel">
        <!--<el-row>
            <el-col>
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/db' }">数据库管理</el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
        </el-row>-->
        <br/>
        <el-row><el-button type="primary" style="float: right" @click="handleApplyDBDialog" v-if="!isPassed">申请数据库</el-button></el-row>
        <el-row>
            <el-col :span="8">
                <el-form label-width="100px" label-position="right">
                    <el-form-item label="地址">
                        <el-input :value="dbModel.dbHost" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="用户">
                        <el-input :value="dbModel.user" readonly style="width: 217px"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="8">
                <el-form label-width="100px" label-position="right">
                    <el-form-item label="名称">
                        <el-input :value="dbModel.dbName" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input :value="dbModel.passWord" readonly style="width: 217px"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="8">
                <el-form label-width="100px" label-position="right">
                    <el-form-item label="端口">
                        <el-input :value="dbModel.dbPort" readonly style="width: 217px"></el-input>
                    </el-form-item>
                    <el-form-item label="权限">
                        <el-input :value="dbModel.privs" readonly style="width: 300px"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <br/>
        <el-row>申请记录</el-row>
        <el-row>
            <el-table
                    :data="tableData"
                    style="width: 100%" border fit>
                <el-table-column
                        prop="id"
                        label="序号">
                </el-table-column>
                <el-table-column
                        prop="dbApplyName"
                        label="数据库名称">
                </el-table-column>
                <el-table-column
                        prop="dbType"
                        label="数据库类型">
                </el-table-column>
                <el-table-column label="审核状态">
                    <template slot-scope="props">
                        <el-tag v-if="isPassed" type="success">审核通过</el-tag>
                        <el-tag v-else type="warning">等待审核</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="flowNode"
                        label="当前节点">
                </el-table-column>
                <el-table-column
                        prop="operateTime"
                        label="操作时间">
                </el-table-column>
                <el-table-column
                        label="操作"
                        width="150" align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" size="small" @click="opneOAFlow()">查看流程<i class="el-icon-search el-icon--right"></i></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <br/>
        <el-row>
            <el-button type="primary" plain @click="imitateOA()" v-if="!isPassed">模拟审批</el-button>
        </el-row>

        <el-dialog title="申请数据库" :visible.sync="openApplyDBDialog" width="400px" :before-close="closeApplyDBDialog">
            <el-form label-width="80px" label-position="left" :model="dbModel">
                <el-form-item label="DB类型" prop="dbType">
                    <el-select v-model="dbModel.dbType" placeholder="请选择数据库类型" filterable style="width: 100%">
                        <el-option v-for="item in dbTypes" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="DB名称" prop="dbName">
                    <el-input placeholder="请输入数据库名称" v-model="dbModel.dbApplyName"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeApplyDBDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="onApplyDB" style="float:right;margin:0 10px 0 0;">申请</el-button>
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
    export default {
        name: "Db",
        data(){
            return {
                isPassed: false,
                openApplyDBDialog: false,
                openOAFlowDialog:false,
                openOAFlowDownDialog:false,
                dbTypes: ['MySQL', 'SQL Server', 'MongoDB'],
                tableData: [],
                dbModel: {dbType: '', dbApplyName:'',dbName: '',dbHost:'',dbPort:'',user:'',passWord:'',privs:''}
            }
        },
        created() {
            this.isPassed = false;
        },
        methods: {
            handleApplyDBDialog(){
                this.openApplyDBDialog = true;
            },
            closeApplyDBDialog(){
                this.openApplyDBDialog = false;
            },
            onApplyDB(){
                this.tableData = [{
                    id: '1',
                    dbApplyName: this.dbModel.dbApplyName,
                    dbType: this.dbModel.dbType,
                    flowNode:'xxx',
                    operateTime:'2018-12-13'}];
                this.openApplyDBDialog = false;
            },
            opneOAFlow(){
                if(this.isPassed){
                    this.openOAFlowDownDialog = true;
                }else {
                    this.openOAFlowDialog = true;
                }
            },
            imitateOA(){
                this.isPassed = true;
                this.dbModel.dbHost = 'fat-2.mysql.test.com（10.res.8.31)';
                this.dbModel.dbName = this.dbModel.dbApplyName;
                this.dbModel.dbPort = 3405;
                this.dbModel.user = 'user_app_'+ this.dbModel.dbApplyName;
                this.dbModel.passWord = '7V*xM0si5Jgy';
                this.dbModel.privs = 'select,insert,update,delete,execute';
            }
        }
    }
</script>

<style scoped>

</style>