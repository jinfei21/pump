<template>
    <div class="content-panel">
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/release/vm' }">虚拟机发布</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/jenkinsConfig' }">Jenkins配置</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
            <br/>
            <el-tabs type="border-card">
                <el-tab-pane label="源码管理">
                    <el-form label-width="80px" label-position="left">
                        <!--<el-form-item label="DB类型" prop="dbType">
                            <el-select v-model="dbModel.dbType" placeholder="请选择数据库类型" filterable style="width: 100%">
                                <el-option v-for="item in dbTypes" :key="item" :value="item"
                                           :label="item"></el-option>
                            </el-select>
                        </el-form-item>-->
                        <el-form-item label="项目描述" prop="">
                            <el-input type="textarea" :rows="8" placeholder="描述"></el-input>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
                <el-tab-pane label="构建触发器">
                    <template>
                        <el-radio v-model="radio" label="1">None</el-radio>
                        <el-radio v-model="radio" label="2">Git</el-radio>
                        <el-radio v-model="radio" label="3">Multiple SCMs</el-radio>
                        <el-radio v-model="radio" label="4">Subversion</el-radio>
                        <el-form label-width="80px" label-position="left" v-if="radio == 2">
                            <br>
                            <el-form-item label="Repository" prop="">
                                <el-input placeholder="描述" value="http://git.test.com/infrastructure/docker-yard.git"></el-input>
                            </el-form-item>
                            <el-form-item label="Credentials" prop="">
                                <el-select v-model="value" placeholder="请选择">
                                    <el-option
                                            v-for="item in options"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="Branch" prop="">
                                <el-input placeholder="" value="*/master"></el-input>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-tab-pane>
                <el-tab-pane label="Build">
                    <el-form label-width="80px" label-position="left">
                        <el-form-item label="RootPOM" prop="">
                            <el-input placeholder="" value="pom.xml"></el-input>
                        </el-form-item>
                        <el-form-item label="options" prop="">
                            <el-input placeholder="" value="clean package -DskipTests"></el-input>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
                <el-tab-pane label="构建后操作">
                    <el-form label-width="180px" label-position="left">
                        <el-form-item label="Project Recipient List" prop="">
                            <el-input placeholder="" type="textarea" :rows="6" value="$DEFAULT_RECIPIENTS"></el-input>
                        </el-form-item>
                        <el-form-item label="Project Reply-To List" prop="">
                            <el-input placeholder="" type="textarea" :rows="6" value="$DEFAULT_REPLYTO"></el-input>
                        </el-form-item>
                        <el-form-item label="Content Type" prop="">
                            <el-select v-model="value" placeholder="请选择">
                                <el-option
                                        v-for="item in contentTypes"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="Default Subject" prop="">
                            <el-input placeholder="" value="$DEFAULT_SUBJECT"></el-input>
                        </el-form-item>
                        <el-form-item label="Default Content" prop="">
                            <el-input placeholder="" type="textarea" :rows="6" value="$DEFAULT_CONTENT"></el-input>
                        </el-form-item>
                        <el-form-item label="Attachments" prop="">
                            <el-input placeholder="" value="$DEFAULT_CONTENT"></el-input>
                        </el-form-item>
                        <el-form-item label="Attach Build Log" prop="">
                            <el-select v-model="value" placeholder="请选择">
                                <el-option
                                        v-for="item in Logs"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    SSH Server<br/><br/>
                    <el-form label-width="180px" label-position="left">
                        <el-form-item label="Server Name" prop="">
                            <el-input placeholder="" value="10.1.63.59"></el-input>
                        </el-form-item>
                        <el-form-item label="Source files" prop="">
                            <el-input placeholder="" value="proxy/target/*.jar,proxy/deploy/*.sh"></el-input>
                        </el-form-item>
                        <el-form-item label="Remove prefix" prop="">
                            <el-input placeholder="" value=""></el-input>
                        </el-form-item>
                        <el-form-item label="Remote directory" prop="">
                            <el-input placeholder="" value="/www/dockeryard.test.com"></el-input>
                        </el-form-item>
                        <el-form-item label="Exec command" prop="">
                            <el-input placeholder="" type="textarea" :rows="30" value=""></el-input>
                        </el-form-item>
                    </el-form>
                    <el-button type="primary" style="float: right" @click="">保存配置</el-button>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    export default {
        name: "JenkinsConfig",
        data () {
            return {
                value:'',
                radio: '2',
                credentials:['grafana/*****','ops_ops@test.com/******','root'],
                options: [{
                    value: '选项1',
                    label: 'grafana/*****'
                }, {
                    value: '选项2',
                    label: 'ops_ops@test.com/******'
                }, {
                    value: '选项3',
                    label: 'root'
                }],
                contentTypes:[{
                    value: '选项1',
                    label: 'Default Content Type'
                }, {
                    value: '选项2',
                    label: 'Plain Text(text/plain)'
                }, {
                    value: '选项3',
                    label: 'HTML(text/html)'
                }, {
                    value: '选项4',
                    label: 'Both HTML and Plain Text'
                }],
                Logs:[{
                    value: '选项1',
                    label: 'Do Not Attach Build Log'
                }, {
                    value: '选项2',
                    label: 'Attach Build Log'
                }, {
                    value: '选项3',
                    label: 'Compress And Attach Build Log'
                }
                ]
            };
        }
    }
</script>

<style scoped>

</style>