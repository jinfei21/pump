
<template>
    <div class="content-panel">
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/release/docker' }">站点管理</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
        </div>
        <div>
            <el-row>
                <el-button type="primary" style="float: right" v-if="!isPassed" @click="openApplyNewWebsiteDialog">申请站点</el-button>
            </el-row>
            <br/>
            <el-row>站点详情</el-row>
            <el-row>
                <el-table
                        :data="tableData"
                        style="width: 100%">
                    <el-table-column
                            prop="id"
                            label="序号">
                    </el-table-column>
                    <el-table-column
                            prop="appId"
                            label="APPID">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="站点名称">
                    </el-table-column>
                    <el-table-column
                            prop="clusterQutantiy"
                            label="集群数">
                    </el-table-column>
                    <el-table-column
                            prop="applicant"
                            label="申请人">
                    </el-table-column>
                    <el-table-column label="审核状态">
                        <template slot-scope="props">
                            <el-tag v-if="isPassed" type="success">审核通过</el-tag>
                            <el-tag v-else type="warning">等待审核</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="applyTime"
                            label="申请时间">
                    </el-table-column>
                    <el-table-column
                            label="操作"
                            width="250" align="center">
                        <template slot-scope="scope">
                            <el-button type="primary" size="small" @click="entryWebsite" plain v-if="isPassed">进入站点<i class="el-icon-d-arrow-right el-icon--right"></i></el-button>
                            <el-button type="primary" size="small" @click="opneOAFlow">查看流程<i class="el-icon-search el-icon--right"></i></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-row>

            <br/>
            <el-row>
                <el-button type="primary" plain @click="imitateOA()" v-if="!isPassed">模拟审批</el-button>
            </el-row>

            <!-- 这里吧website定义出来，然后再多来几个默认input -->
            <el-dialog title="申请站点" :visible.sync="openApplyWebsiteDialog" width="400px">
                <el-form label-width="120px" label-position="left" :model="website">
                    <el-form-item label="域名">
                        <el-input placeholder="请输入域名" v-model="website.domainName"></el-input>
                    </el-form-item>
                    <el-form-item label="测试人员" prop="testers">
                        <el-input placeholder="请输入测试人员" v-model="website.testers"></el-input>
                    </el-form-item>
                    <el-form-item style="margin-bottom: 0">
                        <el-button type="primary" @click="onSubmitApplyWebsite()" style="float:right;">申请
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
    </div>
</template>

<script>
    export default {
        name: "Docker",
        data(){
            return {
                isPassed: false,
                openApplyWebsiteDialog: false,
                openOAFlowDialog: false,
                openOAFlowDownDialog: false,
                tableData: [],
                website:{appId:'',domainName:'',testers:''}
            }
        },
        created() {
            this.isPassed = false;
        },
        methods: {
            openApplyNewWebsiteDialog(){
                this.openApplyWebsiteDialog = true;
            },
            onSubmitApplyWebsite(){
                this.tableData = [{
                    id: '1',
                    appId:'100050',
                    name:'test',
                    clusterQutantiy:0,
                    applicant:'xxxx',
                    flowNode:'xxx',
                    applyTime:'2018-12-13'}];
                this.openApplyWebsiteDialog = false;
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
            },
            entryWebsite(){
                this.$router.push({name: 'clusterList', query: {}});
            }
        }
    }
</script>

<style scoped>

</style>