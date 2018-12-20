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
        <!--<div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/virtualMachine' }">虚拟机管理</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
        </div>-->
        <div>
            <el-row class="query-form">
                <el-col :span="3">
                    <el-input placeholder="输入应用IP"></el-input>
                </el-col>
                <el-col :span="6" :offset="1">
                    <el-button type="primary">查询</el-button>
                    <el-button>重置</el-button>
                </el-col>
                <el-button type="primary" style="float: right" @click="openApplyNewMachineDialog">申请虚拟机</el-button>
            </el-row>
            <br/>
            <el-row>机器列表</el-row>
            <el-row>
                <el-table
                        :data="tableData"
                        style="width: 100%" border fit>
                    <el-table-column
                            prop="id"
                            label="序号" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="ip"
                            label="ip" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="user"
                            label="用户" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="password"
                            label="密码" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="os"
                            label="操作系统" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="specification"
                            label="规格" align="center">
                    </el-table-column>
                    <el-table-column label="审核状态" align="center">
                        <template slot-scope="props">
                            <el-tag v-if="isPassed" type="success">审核通过</el-tag>
                            <el-tag v-else type="warning">等待审核</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="flowNode"
                            label="当前节点" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="operateTime"
                            label="操作时间" align="center">
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

            <el-dialog title="申请虚拟机" :visible.sync="openApplyVirtualMachineDialog" width="300px">
                <el-form label-width="80px" label-position="left" :model="virtualMachine">
                    <el-form-item label="操作系统" prop="machineType">
                        <el-select v-model="virtualMachine.os" placeholder="请选择操作系统" filterable style="width: 100%">
                            <el-option v-for="item in osSet" :key="item" :value="item"
                                       :label="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="配置" prop="specification">
                        <el-select v-model="virtualMachine.specification" placeholder="请选择虚拟机配置" filterable style="width: 100%">
                            <el-option v-for="item in specifications" :key="item" :value="item"
                                       :label="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="数量" prop="quantity">
                        <el-input placeholder="请输入申请数量" v-model="virtualMachine.quantity"></el-input>
                    </el-form-item>
                    <el-form-item style="margin-bottom: 0">
                        <el-button @click="closeApplyVirtualMachineDialog" style="float: right">关闭</el-button>
                        <el-button type="primary" @click="onApplyVirtualMachine" style="float:right;margin:0 10px 0 0;">申请</el-button>
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
    </div>
</template>

<script>
    export default {
        name: "Machine",
        data(){
            return {
                isPassed: false,
                openApplyVirtualMachineDialog: false,
                openOAFlowDialog:false,
                openOAFlowDownDialog : false,
                osSet: ['CentOS 6.7', 'CentOS 7.2', 'Windows2008 R2'],
                specifications: ['2核4G', '4核8G'],
                tableData: [],
                virtualMachine: {ip:'',user: '',password:'',os:'',specification:'',passWord:'',quantity:''}
            }
        },
        created() {
            this.isPassed = false;
        },
        methods: {
            openApplyNewMachineDialog(){
                this.openApplyVirtualMachineDialog = true;
            },
            closeApplyVirtualMachineDialog(){
                this.openApplyVirtualMachineDialog = false;
            },
            onApplyVirtualMachine(){
                this.tableData = [{
                    id: '1',
                    os:this.virtualMachine.os,
                    specification:this.virtualMachine.specification,
                    flowNode:'xxx',
                    operateTime:'2018-12-13'}];
                this.openApplyVirtualMachineDialog = false;
            },
            opneOAFlow(){
                if(this.isPassed){
                    this.openOAFlowDownDialog = true;
                }else {
                    this.openOAFlowDialog = true;
                }
            },
            imitateOA(){
                this.tableData[0].ip = '10.114.27.80';
                this.tableData[0].user = 'arch_zhangyicong';
                this.tableData[0].password = '7V*xM0si5Jgy';
                this.isPassed = true;
            }
        }
    }
</script>

<style scoped>

</style>