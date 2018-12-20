<template>
    <div class="content-panel">
        <br/>
        <el-row>
            <div>当前域名:</div><el-tag v-if="isPassed">{{this.domainNameModel.domainName}}</el-tag>
            <el-button type="primary" style="float: right" v-if="!isPassed" @click="handleApplyDomainNameDialog()">申请域名
            </el-button>
        </el-row>
        <el-row>
            <el-col>申请记录：</el-col>
            <el-table
                    :data="tableData"
                    style="width: 100%" border fit>
                <el-table-column
                        prop="id"
                        label="序号">
                </el-table-column>
                <el-table-column
                        prop="domianName"
                        label="域名">
                </el-table-column>
                <el-table-column label="当前状态">
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
        <br>
        <el-row>
            <el-button type="primary" plain @click="imitateOA()" v-if="!isPassed">模拟审批</el-button>
        </el-row>
        <el-dialog title="申请域名" :visible.sync="openApplyDomainNameDialog" width="400px">
            <el-form label-width="60px" label-position="left" :model="domainNameModel">
                <el-form-item label="域名" prop="domainName">
                    <el-input placeholder="请输入域名" v-model="domainNameModel.domainName"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeApplyDomainNameDialog()" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="onApplyDomainName()" style="float:right;margin:0 10px 0 0;">申请
                    </el-button>
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
        name: "Domain",
        data() {
            return {
                isPassed:false,
                tableData: [],
                openApplyDomainNameDialog:false,
                openOAFlowDialog:false,
                domainNameModel: {domainName: ''},
                openOAFlowDownDialog : false,
            }
        },
        created() {
            this.isPassed = false;
        },
        methods: {
            handleApplyDomainNameDialog(){
                this.openApplyDomainNameDialog = true;
            },
            closeApplyDomainNameDialog(){
                this.openApplyDomainNameDialog = false;
            },
            onApplyDomainName(){
                this.tableData = [{
                    id: '1',
                    domianName: this.domainNameModel.domainName,
                    flowNode:'xxx',
                    operateTime:'2018-12-13'}];
                this.openApplyDomainNameDialog = false;
            },
            opneOAFlow(){
                this.openOAFlowDialog = true;
            },
            imitateOA(){
                if(this.isPassed){
                    this.openOAFlowDownDialog = true;
                }else {
                    this.openOAFlowDialog = true;
                }
            }
        }
    }
</script>

<style scoped>

</style>