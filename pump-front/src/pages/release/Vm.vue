<template>
    <div class="content-panel">
        <div v-if="flag==0">
            <el-row>
                <el-button type="primary" style="float: right" @click="handleCreateNewJenkinsConfig">构建Jenkins配置</el-button>
            </el-row>
        </div>

        <div v-if="flag==1">
            <el-row>Jenkins配置</el-row>
            <el-row>
                <el-table
                        :data="tableData"
                        style="width: 100%"
                        border fit>
                    <el-table-column
                            prop="name"
                            label="名称"
                            width="400" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="latestSuccess"
                            label="上次成功" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="latestFail"
                            label="上次失败" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="latestDuration"
                            label="上次持续时间" align="center">
                    </el-table-column>
                    <el-table-column label="审核状态" align="center">
                        <template slot-scope="props">
                            <el-tag v-if="isPassed" type="success">审核通过</el-tag>
                            <el-tag v-else type="warning">等待审核</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="操作"
                            width="500" align="center">
                        <template slot-scope="scope">
                            <el-button type="primary" size="small" @click="openConfig()" v-if="isPassed">配置</el-button>
                            <el-button type="primary" size="small" @click="structure()" v-if="isPassed && !isStructured">立即构建</el-button>
                            <el-button type="primary" size="small" @click="structure()" disabled v-if="isPassed && isStructured">立即构建</el-button>
                            <el-button type="primary" size="small" @click="structureHistory()" v-if="isPassed">构建历史<i class="el-icon-d-arrow-right el-icon--right"></i></el-button>
                            <el-button type="primary" size="small" @click="opneOAFlow()">查看流程<i class="el-icon-search el-icon--right"></i></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-row>
            <br/>
            <el-row v-if="!isPassed">
                <el-button type="primary" plain @click="imitateOA()">模拟审批</el-button>
            </el-row>
        </div>

        <div v-if="flag==2">
            <iframe width="100%" height="100%" frameborder="0" style="position: absolute"
                    src="http://jenkins.tes.com/view/%E5%9F%BA%E7%A1%80%E6%A1%86%E6%9E%B6/job/%E5%9F%BA%E7%A1%80%E6%A1%86%E6%9E%B6_%E9%A2%84%E5%8F%91%E5%B8%83%E7%8E%AF%E5%A2%83/job/%E5%9F%BA%E7%A1%80%E6%A1%86%E6%9E%B6_dockeryard.test
                    test.com_pre/configure"></iframe>
        </div>

        <el-dialog title="Jenkins配置名称" :visible.sync="openCreateNewJenkinsConfigDialog" width="400px">
            <el-form label-width="80px" label-position="left" :model="jenkinsConfig">
                <el-form-item label="名称" prop="configName">
                    <el-input placeholder="请输入项目名称" v-model="jenkinsConfig.itemName"></el-input>
                </el-form-item>
                <el-form-item label="项目类型" prop="itemType">
                    <el-select v-model="jenkinsConfig.itemType" placeholder="请选择项目类型" filterable style="width: 100%">
                        <el-option v-for="item in itemTypes" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeCreateNewJenkinsConfigDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="applyJenkinsConfig" style="float:right;margin:0 10px 0 0;">提交</el-button>
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
        data () {
            return {
                flag:0,
                isPassed:false,
                openCreateNewJenkinsConfigDialog:false,
                jenkinsConfig : {itemName : "",itemType:""},
                tableData : [],
                openOAFlowDialog:false,
                openOAFlowDownDialog:false,
                isStructured:false,
                itemTypes:['构建一个Maven项目','构建一个自由风格的软件项目','流水线','External Job','创建一个多配置项目','MultiJob Project','Bitbucket Team/Project','文件夹','GitHub Organization','多分支流水线','Multibranch Pipeline with defaults']
            }
        },
        methods: {
            handleCreateNewJenkinsConfig(){
                this.openCreateNewJenkinsConfigDialog = true;
            },
            closeCreateNewJenkinsConfigDialog(){
                this.openCreateNewJenkinsConfigDialog = false;
            },
            applyJenkinsConfig(){
                this.tableData = [{
                    name:"Maven project 基础框架_" + this.jenkinsConfig.itemName+".test.com_fat",
                    latestSuccess:"",
                    latestFail:"",
                    latestDuration:""
                }];
                this.flag = 1;
                this.openCreateNewJenkinsConfigDialog = false;
            },
            openConfig(){
              this.$router.push({name: 'jenkinsConfig', query: {}});
            },
            imitateOA(){
                this.isPassed = true;
            },
            opneOAFlow(){
                if(this.isPassed){
                    this.openOAFlowDownDialog = true;
                }else {
                    this.openOAFlowDialog = true;
                }
            },
            structure(){
                this.isStructured = true;
                this.$notify.info({
                    title: '消息',
                    message: '构建中，请到构建历史中查看结构。'
                });
            },
            structureHistory(){
                this.$router.push({name: 'vmStructureHistory', query: {}});
            }
        }
        /*computed: {
            ...mapGetters({
                appId: 'getAppId',
                env: 'getEnv'
            })
        },*/
        /*watch: {
            $route: function (newRoute) {
                alert(newRoute.query.env)
            }
        }*/
    }
</script>

<style>

</style>