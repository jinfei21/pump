<template>
    <div class="content-panel">
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/release/apollo' }">Apollo管理</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/apolloConfig' }">Apollo</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
            <br/>
            <el-row>
                <el-button type="primary" style="float: right" @click="handleRelease">发布</el-button>
            </el-row>
            <br/>
            <el-tabs type="border-card">
                <el-tab-pane label="表格">
                    <el-button @click="handleAddConfig">新增配置</el-button>
                    <br><br>
                    <el-table :data="tableData1" style="width: 100%" border fit>
                        <el-table-column label="发布状态" prop="releaseStatus" align="center" width="160">
                            <template slot-scope="props">
                                <el-tag v-if="props.row.releaseStatus == 0" type="warning">已发布</el-tag>
                                <el-tag v-else type="success">未发布</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="Key" prop="key" align="center"></el-table-column>
                        <el-table-column label="Value" prop="value" align="center"></el-table-column>
                        <el-table-column label="备注" prop="comment" align="center"></el-table-column>
                        <el-table-column label="最后修改人" prop="updateBy" align="center"></el-table-column>
                        <el-table-column label="最后修改时间" prop="updateTime" align="center"></el-table-column>
                        <el-table-column label="操作" align="center" width="200">
                            <template scope="props">
                                <el-button @click="editRow(props.row.id)" size="small" type="primary" plain><i class="el-icon-edit"></i></el-button>
                                <el-button @click="deleteRow(props.row.id)" size="small" type="primary" plain><i class="el-icon-delete"></i></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane label="文本">
                    server.port = 8080###-###false<br/>
                    mybatis.type-aliases-package = com.tset.dockeryard.core.mapper###-###false<br/>
                    mybatis.mapper-locations = classpath*:mapper/*Mapper.xml###-###false<br/>
                    # spring data source<br/>
                    spring.datasource.driverClassName = com.mysql.jdbc.Driver###-###false
                    spring.datasource.url = jdbc:mysql://10.test.8.31:3405/test?useUnicode=true&characterEncoding=utf-8&useSSL=false###-###false<br/>
                    spring.datasource.username = user_app_dockeryard###-###false<br/>
                    spring.datasource.password = F57L3up{cf^k###-###false<br/>
                </el-tab-pane>
                <el-tab-pane label="变更历史">
                    <el-table :data="tableData2" style="width: 100%" border fit>
                        <el-table-column label="Id" prop="id" align="center" width="50"></el-table-column>
                        <el-table-column label="Key" prop="key" align="center" width="250"></el-table-column>
                        <el-table-column label="Old Value" prop="oldValue" align="center" width="300"></el-table-column>
                        <el-table-column label="New Value" prop="newValue" align="center" width="300"></el-table-column>
                        <el-table-column label="Comment" prop="comment" align="center" width="300"></el-table-column>
                        <el-table-column label="操作类型" prop="operateType" align="center" width="100">
                            <template slot-scope="props">
                                <el-tag v-if="props.row.operateType == 0" type="success">新增</el-tag>
                                <el-tag v-else-if="props.row.operateType == 1" type="warning">更新</el-tag>
                                <el-tag v-else type="danger">删除</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="更新时间" prop="updateTime" align="center" width="150"></el-table-column>
                        <el-table-column label="更新人" prop="updateBy" align="center"></el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
        </div>
        <el-dialog title="添加配置项" :visible.sync="openHandleAddConfigDialog" width="600px">
            <el-form label-width="80px" label-position="left" :model="apolloConfig" ref="inApplyNewAppForm" :rules="inApplyNewAppRules">
                <el-form-item label="Key" prop="key"required>
                    <el-input placeholder="" v-model="apolloConfig.key"></el-input>
                </el-form-item>
                <el-form-item label="Value" prop="key"required>
                    <el-input placeholder="" v-model="apolloConfig.value"></el-input>
                </el-form-item>
                <el-form-item label="Comment" prop="comment">
                    <el-input placeholder="" type="textarea" :rows="6" v-model="apolloConfig.comment"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeHandleAddConfigDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="addConfig" style="float:right;margin:0 10px 0 0;">申请</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="修改配置项" :visible.sync="openHandleEditConfigDialog" width="600px">
            <el-form label-width="80px" label-position="left" :model="apolloConfig" ref="inApplyNewAppForm" :rules="inApplyNewAppRules">
                <el-form-item label="Key">
                    <el-input placeholder="" v-model="apolloConfig.key" readonly></el-input>
                </el-form-item>
                <el-form-item label="Value" prop="key"required>
                    <el-input placeholder="" v-model="apolloConfig.value"></el-input>
                </el-form-item>
                <el-form-item label="Comment" prop="comment">
                    <el-input placeholder="" type="textarea" :rows="6" v-model="apolloConfig.comment"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeHandleEditConfigDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="editConfig" style="float:right;margin:0 10px 0 0;">申请</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                openHandleAddConfigDialog:false,
                openHandleEditConfigDialog : false,
                index : 0,
                tableData1: [],
                apolloConfig:{index:'',key:'',value:'',comment:''},
                tableData2 :[{
                    id:"1",
                    key:"server.port",
                    oldValue:"8081",
                    newValue:"8080",
                    comment:"",
                    operateType:2,
                    updateTime:"2018-12-20 17:08:19",
                    updateBy:"zhangxiao04"
                },{
                    id:"2",
                    key:"server.port",
                    oldValue:"8080",
                    newValue:"8081",
                    comment:"",
                    operateType:1,
                    updateTime:"2018-12-20 11:15:42",
                    updateBy:"lizhiming"
                },{
                    id:"2",
                    key:"server.port",
                    oldValue:"8081",
                    newValue:"",
                    comment:"",
                    operateType:0,
                    updateTime:"2018-12-19 10:12:54",
                    updateBy:"zoufeng02"
                }]
            }
        },
        methods: {
            handleRelease(){
                let size = this.tableData1.length;
                for(let index = 0 ; index < size ; index++){
                    this.tableData1[index].releaseStatus = 0;
                }
                this.$notify.success({
                    title: '发布成功',
                    message: '发布成功。'
                });
            },
            handleAddConfig(){
                this.openHandleAddConfigDialog = true;
            },
            editRow(id){
                let index = id -1;
                let config = this.tableData1[index];
                this.apolloConfig.index = index;
                this.apolloConfig.key = config.key;
                this.apolloConfig.value = config.value;
                this.apolloConfig.comment = config.comment;
                this.openHandleEditConfigDialog = true;
            },
            deleteRow(id){
                this.$confirm('确定要删除该条配置项？', '删除站点', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                }).catch(() => {
                    this.$message({
                        'type': 'info',
                        'message': '取消删除'
                    });
                });
            },
            closeHandleAddConfigDialog(){
                this.openHandleAddConfigDialog = false;
            },
            addConfig(){
                let config = {
                    id : this.index+1,
                    releaseStatus : 1,
                    key : this.apolloConfig.key,
                    value : this.apolloConfig.value,
                    comment : this.apolloConfig.comment,
                    updateBy : 'zhangxiao04',
                    updateTime : '2018-12-20 17:52:39'
                }
                this.tableData1.push(config);
                this.openHandleAddConfigDialog = false;
                this.$notify.success({
                    title: '添加成功',
                    message: '如需生效请点击发布。'
                });
            },
            closeHandleEditConfigDialog(){
                this.openHandleEditConfigDialog = false;
            },
            editConfig(){
                let index = this.apolloConfig.index;
                this.tableData1[index].value = this.apolloConfig.value;
                this.tableData1[index].comment = this.apolloConfig.comment;
                this.openHandleEditConfigDialog = false;
            }
        }
    }
</script>