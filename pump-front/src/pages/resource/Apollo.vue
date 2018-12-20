<template>

    <div class="content-panel" v-if="!isEntered">
        <el-button type="primary" style="float: right" @click="createNewApollo" v-if="!isCreated">创建Apollo项目</el-button>
        <div v-if="isCreated">
            申请记录：<br/><br/>
            <el-table
                    :data="tableData"
                    style="width: 100%" border fit>
                <el-table-column
                        prop="appId"
                        label="AppId" align="center">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="应用名称" align="center">
                </el-table-column>
                <el-table-column
                        prop="department"
                        label="部门" align="center">
                </el-table-column>
                <el-table-column
                        prop="applicant"
                        label="申请人" align="center">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="创建时间" align="center">
                </el-table-column>
                <el-table-column label="当前状态" align="center">
                    <template slot-scope="props">
                        <el-tag v-if="isPassed" type="success">审核通过</el-tag>
                        <el-tag v-else type="warning">等待审核</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        label="操作"
                        width="350" align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" size="small" v-if="isPassed" @click="enterApollo">进入项目<i class="el-icon-d-arrow-right el-icon--right"></i></el-button>
                        <el-button type="primary" size="small" @click="opneOAFlow">查看流程<i class="el-icon-search el-icon--right"></i></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <br>
            <el-row>
                <el-button type="primary" plain @click="imitateOA()" v-if="!isPassed">模拟审批</el-button>
            </el-row>

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
        data() {
            return {
                isEntered:false,
                isCreated:false,
                isPassed:false,
                tableData:[],
                openOAFlowDialog:false,
                openOAFlowDownDialog : false
            }
        },
        methods: {
            createNewApollo(){
                this.isCreated = true;
                this.$notify.success({
                    title: '消息',
                    message: '创建成功，等待审核。'
                });
                this.tableData = [{
                    appId:'100010',
                    name:'demo',
                    department:'基础框架',
                    applicant:'zhangxiao04',
                    createTime:'2018-12-20 14:22:07'
                }]
            },
            enterApollo(){
                this.$router.push({name: 'apolloConfig', query: {}});
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
            }
        }
    }
</script>