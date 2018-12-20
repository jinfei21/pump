<template>
    <div class="content-panel">
        <div>
            <el-row>
                <el-col>
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/release/vm' }">虚拟机发布</el-breadcrumb-item>
                        <el-breadcrumb-item :to="{ path: '/vmStructureHistory' }">构建历史</el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row>
            <br/>
            <el-row class="query-form">
                <el-col :span="3">
                    <el-input placeholder="输入Id"></el-input>
                </el-col>
                <el-col :span="6" :offset="1">
                    <el-button type="primary" @click="">查询</el-button>
                    <el-button @click="">重置</el-button>
                </el-col>
            </el-row>
            <br/>
            <el-row>构建历史</el-row>
            <br/>
            <el-row>
                <el-table
                        :data="tableData"
                        style="width: 100%"
                        border fit>
                    <el-table-column
                            prop="id"
                            label="序号"
                            width="100" align="center">
                    </el-table-column>
                    <el-table-column
                            prop="releaseStatus"
                            label="发布状态"
                            align="center">
                        <template scope="props">
                            <el-progress :percentage="100" status="success" v-if="props.row.releaseStatus == 0"></el-progress>
                            <el-progress :percentage="70" v-if="props.row.releaseStatus == 1"></el-progress>
                            <el-progress :percentage="50" status="exception" v-if="props.row.releaseStatus == -1"></el-progress>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="releaseTime"
                            label="发布时间"
                            align="center">
                    </el-table-column>
                    <el-table-column
                            prop="operator"
                            label="操作人"
                            align="center">
                    </el-table-column>
                    <el-table-column
                            label="操作"
                            width="600" align="center">
                        <template slot-scope="scope">
                            <el-button type="primary" size="small" @click="f1">变更记录</el-button>
                            <el-button type="primary" size="small" @click="f2">Console Output</el-button>
                            <el-button type="primary" size="small" @click="f3">Environment variables</el-button>
                            <el-button type="primary" size="small" @click="f4">Git Build Data</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-row>
        </div>
        <el-dialog title="变革记录" :visible.sync="t1Dialog" width="800px">
            为k8s pull request临时增加 白名单功能 (details)<br/><br/>
            Commit 0810fed10f49e02c6336eca7c05c795bd84b7f4c by 张虓<br/><br/>
            为k8s pull request临时增加 白名单功能<br/><br/>
            The file was modified	proxy/src/main/java/com/test/dockeryard/proxy/executor/AuthorizationRequestExecutor.java<br/>
            The file was modified	proxy/src/main/java/com/test/dockeryard/proxy/Application.java<br/>
            The file was modified	proxy/src/main/java/com/test/dockeryard/proxy/executor/PullRequestExecutor.java
        </el-dialog>
        <el-dialog title="Console Output" :visible.sync="t2Dialog" width="1600px">
            [INFO] --- spring-boot-maven-plugin:2.0.1.RELEASE:repackage (default) @ proxy ---<br><br>
            [INFO] ------------------------------------------------------------------------
            [INFO] Reactor Summary:<br>
            [INFO]<br>
            [INFO] dockeryard-parent .................................. SUCCESS [ 0.952 s]<br>
            [INFO] core ............................................... SUCCESS [ 5.628 s]<br>
            [INFO] admin .............................................. SUCCESS [ 8.777 s]<br>
            [INFO] proxy .............................................. SUCCESS [ 4.488 s]
            [INFO] ------------------------------------------------------------------------<br>
            [INFO] BUILD SUCCESS<br>
            [INFO] ------------------------------------------------------------------------<br>
            [INFO] Total time: 23.685 s<br>
            [INFO] Finished at: 2018-12-18T09:33:11+08:00<br>
            [INFO] Final Memory: 40M/219M<br>
            [INFO] ------------------------------------------------------------------------<br>
            Waiting for Jenkins to finish collecting data
            [JENKINS] Archiving /data/jenkins/workspace/基础框架_预发布环境/基础框架_dockeryard.testcorp.com_pre/core/pom.xml to com.test.dockeryard/core/0.0.1-SNAPSHOT/core-0.0.1-SNAPSHOT.pom<br>
            [JENKINS] Archiving /data/jenkins/workspace/基础框架_预发布环境/基础框架_dockeryard.testcorp.com_pre/core/target/core-0.0.1-SNAPSHOT.jar to com.test.dockeryard/core/0.0.1-SNAPSHOT/core-0.0.1-SNAPSHOT.jar<br>
            [JENKINS] Archiving /data/jenkins/workspace/基础框架_预发布环境/基础框架_dockeryard.testcorp.com_pre/proxy/pom.xml to com.test.dockeryard/proxy/0.0.1-SNAPSHOT/proxy-0.0.1-SNAPSHOT.pom<br>
            [JENKINS] Archiving /data/jenkins/workspace/基础框架_预发布环境/基础框架_dockeryard.testcorp.com_pre/proxy/target/proxy-0.0.1-SNAPSHOT.jar to com.test.dockeryard/proxy/0.0.1-SNAPSHOT/proxy-0.0.1-SNAPSHOT.jar<br>
            [JENKINS] Archiving /data/jenkins/workspace/基础框架_预发布环境/基础框架_dockeryard.testcorp.com_pre/admin/pom.xml to com.test.dockeryard/admin/0.0.1-SNAPSHOT/admin-0.0.1-SNAPSHOT.pom<br>
            [JENKINS] Archiving /data/jenkins/workspace/基础框架_预发布环境/基础框架_dockeryard.testcorp.com_pre/admin/target/admin-0.0.1-SNAPSHOT.jar to com.test.dockeryard/admin/0.0.1-SNAPSHOT/admin-0.0.1-SNAPSHOT.jar<br>
            [JENKINS] Archiving /data/jenkins/workspace/基础框架_预发布环境/基础框架_dockeryard.testcorp.com_pre/pom.xml to com.test.dockeryard/dockeryard-parent/0.0.1-SNAPSHOT/dockeryard-parent-0.0.1-SNAPSHOT.pom<br>
            channel stopped<br>
            buildstep.1SSH: Connecting from host [jenkins-slave017062.ppdgdsl.com]<br>
        </el-dialog>
        <el-dialog title="Environment variables" :visible.sync="t3Dialog" width="841px">
            <el-table
                    :data="variables"
                    style="width: 100%"
                    border fit>
                <el-table-column
                        prop="key"
                        label="名称"
                        width="300" align="center">
                </el-table-column>
                <el-table-column
                        prop="value"
                        label="值"
                        width="500" align="center">
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-dialog title="Git Build Data" :visible.sync="t4Dialog" width="800px">
            Git Build Data<br/><br/>
            Revision: 0810fed10f49e02c6336eca7c05c795bd84b7f4c<br/>
            refs/remotes/origin/master<br/><br/>
            Built Branches<br/>
            refs/remotes/origin/master: Build #14 of Revision 0810fed10f49e02c6336eca7c05c795bd84b7f4c (refs/remotes/origin/master)
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "VmStructureHistory",
        data () {
            return {
                t1Dialog:false,
                t2Dialog:false,
                t3Dialog:false,
                t4Dialog:false,
                tableData : [{
                    id:"#15",
                    releaseStatus:1,
                    releaseTime:"2018-12-18 上午9:32",
                    operator:"zhangxiao04"
                },{
                    id:"#14",
                    releaseStatus:0,
                    releaseTime:"2018-12-18 上午8:38",
                    operator:"zoufeng02"
                },
                {
                    id:"#13",
                    releaseStatus:0,
                    releaseTime:"2018-12-17 下午19:25",
                    operator:"lizhiming"
                },
                {
                    id:"#12",
                    releaseStatus:-1,
                    releaseTime:"2018-12-17 下午5:40",
                    operator:"zoufeng02"
                }],
                variables : [{
                    key:"BUILD_CAUSE",
                    value:'MANUALTRIGGER'
                },{
                    key:"BUILD_CAUSE_MANUALTRIGGER",
                    value:'true'
                },
                {
                    key:"BUILD_DISPLAY_NAME",
                    value:'#15'
                },
                {
                    key:"BUILD_TAG",
                    value:'jenkins-基础框架_预发布环境-基础框架_demo.tes.com_fat-15'
                },
                {
                    key:"HUDSON_URL",
                    value:"\thttp://jenkins.tes.com/"
                },
                {
                    key:"JAVA_HOME",
                    value:"/usr/local/java"
                },
                {
                    key:"JENKINS_HOME",
                    value:"\t/data/.jenkins"
                },
                {
                    key:"JENKINS_SERVER_COOKIE",
                    value:"3a925396b0db04ad"
                }]
            }
        },
        methods: {
            f1(){
                this.t1Dialog = true;
            },
            f2(){
                this.t2Dialog = true;
            },
            f3(){
                this.t3Dialog = true;
            },
            f4(){
                this.t4Dialog = true;
            }
        }

    }
</script>

<style scoped>

</style>