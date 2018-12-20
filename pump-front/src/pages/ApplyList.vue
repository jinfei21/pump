<template>
    <div class="content-panel">
        <el-row class="query-form">
            <el-col :span="3">
                <el-input placeholder="输入AppId"></el-input>
            </el-col>
            <el-col :span="3" :offset="1">
                <el-input placeholder="输入申请人"></el-input>
            </el-col>
            <el-button type="primary" @click="" >查询</el-button>
            <el-button @click="">重置</el-button>
        </el-row>
        <br/>
        <el-row>申请记录</el-row>
        <br/>
        <el-row>
            <el-table
                    :data="tableData"
                    style="width: 100%" border fit>
                <el-table-column
                        prop="id"
                        label="序号" align="center">
                </el-table-column>
                <el-table-column
                        prop="appId"
                        label="AppId" align="center">
                </el-table-column>
                <el-table-column
                        prop="type"
                        label="类型" align="center">
                </el-table-column>
                <el-table-column
                        prop="applicant"
                        label="申请人" align="center">
                </el-table-column>
                <el-table-column
                        prop="appTime"
                        label="申请时间" align="center">
                </el-table-column>
                <el-table-column label="审核状态" align="center">
                    <template slot-scope="props">
                        <el-tag v-if="props.row.status == true" type="success">审核通过</el-tag>
                        <el-tag v-else type="warning">等待审核</el-tag>
                    </template>
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

        <el-dialog title="OA流程图" :visible.sync="openOAFlowDialog" width="500px">
            <el-steps :space="200" :active="1" finish-status="success">
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
        data () {
            return {
                openOAFlowDialog:false,
                types:['1','2','3'],
                tableData: [{
                    id:'1',
                    appId:'100001',
                    type:'虚拟机申请',
                    applicant:'zhangxiao04',
                    appTime:'2018-12-17',
                    status:true
                },{
                    id:'2',
                    appId:'100047',
                    type:'域名申请',
                    applicant:'lizhiming',
                    appTime:'2018-12-17',
                    status:false
                },{
                    id:'3',
                    appId:'100065',
                    type:'Cache申请',
                    applicant:'zoufeng02',
                    appTime:'2018-12-12',
                    status:false
                },{
                    id:'4',
                    appId:'100029',
                    type:'容器云申请',
                    applicant:'lizhiming',
                    appTime:'2018-12-30',
                    status:true
                }]
            }
        },
        methods: {
            opneOAFlow(){
                this.openOAFlowDialog = true;
            }
        }
    }
</script>

<!--
<style>

</style>-->
