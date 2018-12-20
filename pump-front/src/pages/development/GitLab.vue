<template>
    <div class="content-panel">
        <div v-if="!isCreated">
            <!--<el-row>
                <el-col :span="4">
                    属性方式：
                    <el-select v-model="value" placeholder="请选择NameSpace">
                        <el-option v-for="item in namespaces" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-col>
                <el-col :span="6" :offset="1">
                    <el-input placeholder="请输入Project Name" style="width: 217px"></el-input>
                </el-col>
                <el-col :span="4">
                    <el-select v-model="value" placeholder="Visibility Level">
                        <el-option v-for="item in levels" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-col>
                <el-button type="primary" style="float: right" @click="createNewProject">New Project</el-button>
            </el-row>-->
            请选择NameSpace：
            <el-select v-model="value" placeholder="请选择NameSpace">
                <el-option v-for="item in namespaces" :key="item" :value="item"
                           :label="item"></el-option>
            </el-select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            请输入Project Name:
            <el-input placeholder="请输入Project Name" style="width: 217px"></el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            请选择Visibility Level:
            <el-select v-model="value2" placeholder="Visibility Level">
                <el-option v-for="item in levels" :key="item" :value="item"
                           :label="item"></el-option>
            </el-select>
            <el-button type="primary" style="float: right" @click="createNewProject">New Project</el-button>
        </div>

        <div v-if="isCreated">
            <el-row class="query-form">
                <!--<el-col :span="2">
                    <el-select v-model="value" placeholder="请选择" @change="t()">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col :span="6" :offset="1">
                    <el-input :value="123" readonly  style="width: 217px"></el-input>
                </el-col>-->
                <el-col :span="7" :offset="0">
                    <el-input placeholder="请输入内容" value="Http://git.test.com/infrastructure/demo.git">
                        <template slot="prepend">Http</template>
                    </el-input>
                </el-col>
                <el-col :span="7" :offset="1">
                    <el-input placeholder="请输入内容" value="git@git.test.com:infrastructure/demo.git">
                        <template slot="prepend">SSH</template>
                    </el-input>
                </el-col>
                <el-button type="primary" style="float: right" >Add Members</el-button>
            </el-row>
            <br>
            <div>
                <el-tabs type="border-card">
                    <el-tab-pane label="Commits(3)">
                        <el-select v-model="value" placeholder="请选择分支">
                            <el-option
                                    v-for="item in branches"
                                    :key="item"
                                    :label="item"
                                    :value="item">
                            </el-option>
                        </el-select>
                        <br/>
                        <br/>
                        <el-table
                                :data="commitsTableData"
                                style="width: 100%" border fit>
                            <el-table-column
                                    prop="commitId"
                                    label="commitId">
                            </el-table-column>
                            <el-table-column
                                    prop="commitMessage"
                                    label="commitMessage" width="500px">
                            </el-table-column>
                            <el-table-column
                                    prop="committer"
                                    label="Committer">
                            </el-table-column>
                            <el-table-column
                                    prop="commitTime"
                                    label="CommitTime">
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="Branches">
                        <el-row>
                            <el-button type="primary" style="float: right" @click="handleCreateNewBranch()">New branch</el-button>
                        </el-row>
                        <br>
                        <el-table
                                :data="branchesTableData"
                                style="width: 100%" border fit>
                            <el-table-column
                                    prop="branchName"
                                    label="BranchName">
                            </el-table-column>
                            <el-table-column
                                    prop="property"
                                    label="Property">
                                <template slot-scope="props">
                                    <el-tag v-if="props.row.property == '0'" type="primary">default</el-tag>
                                    <el-tag v-if="props.row.property == '0'" type="success">protected</el-tag>
                                    <el-tag v-if="props.row.property == '1'" type="primary">merged</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="latestCommit"
                                    label="LatestCommit" width="700px">
                            </el-table-column>
                            <el-table-column
                                    prop="commitTime"
                                    label="CommitTime">
                            </el-table-column>
                            <el-table-column label="操作" align="center" width="350">
                                <template scope="props">
                                    <el-button plain size="small" v-if="props.row.branchName != 'master'" @click="createMergeRequest(props.row.branchName)">Merge request</el-button>
                                    <el-button type="danger" size="small">delete</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="Tags"><el-row>
                        <el-button type="primary" style="float: right" @click="handleCreateNewTag()">New Tag</el-button>
                    </el-row>
                        <br>
                        <el-table
                                :data="tagsTableData"
                                style="width: 100%" border fit>
                            <el-table-column
                                    prop="tagName"
                                    label="TagName">
                            </el-table-column>
                            <el-table-column
                                    prop="message"
                                    label="Message">
                            </el-table-column>
                            <el-table-column
                                    prop="createFrom"
                                    label="Create From">
                            </el-table-column>
                            <el-table-column
                                    prop="latestCommit"
                                    label="LatestCommit" width="700px">
                            </el-table-column>
                            <el-table-column
                                    prop="createTime"
                                    label="CreateTime" width="250">
                            </el-table-column>
                            <el-table-column label="操作" align="center" width="300">
                                <template scope="props">
                                    <el-button size="small" @click="handleEditNotes">Edit</el-button>
                                    <el-button type="danger" size="small">delete</el-button>
                                </template>
                            </el-table-column>
                        </el-table></el-tab-pane>
                </el-tabs>
            </div>
        </div>

        <el-dialog title="Create a New Branch" :visible.sync="openCreateNewBranchDialog" width="400px">
            <el-form label-width="80px" label-position="left" :model="newBranchModel">
                <el-form-item label="Name" prop="branchName">
                    <el-input placeholder="Branch name" v-model="newBranchModel.newBranchName"></el-input>
                </el-form-item>
                <el-form-item label="From" prop="createFrom">
                    <el-select v-model="newBranchModel.createFrom" placeholder="Create from" filterable style="width: 100%">
                        <el-option v-for="item in branches" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeCreateNewBranchDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="createBranch" style="float:right;margin:0 10px 0 0;">提交</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="Create a Merge Request" :visible.sync="openCreateNewMergeRequestDialog" width="500px">
            <el-form label-width="80px" label-position="left" :model="mergeRequestModel">
                <el-form-item label="From" prop="branchFrom">
                    <el-input placeholder="Branch From" v-model="mergeRequestModel.branchFrom" readonly></el-input>
                </el-form-item>
                <el-form-item label="Target" prop="branchTarget">
                    <el-input placeholder="Branch Target" v-model="mergeRequestModel.branchTarget" readonly></el-input>
                </el-form-item>
                <el-form-item label="Description" prop="description">
                    <el-input type="textarea" :rows="6" placeholder="Description" v-model="mergeRequestModel.description"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeCreateNewMergeRequestDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="createNewMergeRequest" style="float:right;margin:0 10px 0 0;">提交</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="Create a Tag" :visible.sync="openCreateNewTagDialog" width="500px">
            <el-form label-width="80px" label-position="left" :model="tagModel">
                <el-form-item label="Name" prop="tagName">
                    <el-input placeholder="Tag name" v-model="tagModel.tagName"></el-input>
                </el-form-item>
                <el-form-item label="from" prop="createFrom">
                    <el-select v-model="tagModel.createFrom" placeholder="Create from" filterable style="width: 100%">
                        <el-option v-for="item in branches" :key="item" :value="item"
                                   :label="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Message" prop="message">
                    <el-input type="textarea" :rows="6" placeholder="Optionally, add a message to the tag." v-model="tagModel.message"></el-input>
                </el-form-item>
                <el-form-item label="Notes" prop="releaseNotes">
                    <el-input type="textarea" :rows="8" placeholder="Optionally, Release notes." v-model="tagModel.releaseNotes"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeCreateNewTagDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="createNewTag" style="float:right;margin:0 10px 0 0;">提交</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="Edit Release Notes" :visible.sync="openEditNotesDialog" width="500px">
            <el-form label-width="80px" label-position="left" :model="tagModel">
                <el-form-item label="Notes" prop="releaseNotes">
                    <el-input type="textarea" :rows="8" placeholder="Optionally, Release notes." v-model="tagModel.releaseNotes"></el-input>
                </el-form-item>
                <el-form-item style="margin-bottom: 0">
                    <el-button @click="closeEditNotesDialog" style="float: right">关闭</el-button>
                    <el-button type="primary" @click="editNotes" style="float:right;margin:0 10px 0 0;">提交</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                isCreated:false,
                namespaces:['infrastructure','foundation-framework'],
                levels:['Private','Internal','Public'],
                options: [{
                    value: 'SSH',
                    label: 'SSH'
                }, {
                    value: 'HTTP',
                    label: 'HTTP'
                }],
                value: '',
                value2:'',
                openCreateNewBranchDialog:false,
                newBranchModel:{newBranchName:'',createFrom:''},
                branches:['master','dev','demo-aliyun','zhang'],
                commitsTableData : [{
                    commitId:'baa6097b',
                    commitMessage:'[add]zuul springboot项目',
                    committer:'zoufeng02',
                    commitTime:'2018-11-6'
                },{
                    commitId:'f81d1875',
                    commitMessage:'[update]#关闭限流熔断测试',
                    committer:'zoufeng02',
                    commitTime:'2018-10-24'
                },{
                    commitId:'043466bd',
                    commitMessage:'[update]#重写app数据拉取流程，优化对象数量和存储空间',
                    committer:'zoufeng02',
                    commitTime:'2018-10-16'
                }],
                branchesTableData : [{
                    branchName : 'master',
                    property : '0',
                    latestCommit : 'baa6097b · [add]zuul springboot项目',
                    commitTime : '2018-11-6'
                },{
                    branchName : 'dev',
                    property : '',
                    latestCommit : 'ef4c0c0c · [fix]#post请求体传入querystring bug',
                    commitTime : '2018-10-24'
                },{
                    branchName : 'demo-aliyun',
                    property : '',
                    latestCommit : '1fe30781 · Merge remote-tracking branch \'origin/wormhole-aliyun\' into wormhole-aliyun',
                    commitTime : '2018-12-3'
                },{
                    branchName : 'zhang',
                    property : '1',
                    latestCommit : '5879a81a · 整合Hystrix  · 2 months ago',
                    commitTime : '26 minutes ago'
                }],
                mergeRequestModel:{branchFrom:'', branchTarget:'master', description:''},
                openCreateNewMergeRequestDialog:false,
                tagsTableData:[{
                    tagName:'Tag1',
                    message:'tag1',
                    createFrom:'master',
                    latestCommit : '5879a81a · 整合Hystrix  · 2 months ago',
                    releaseNotes:'xxxxxx',
                    createTime:'2018-12-3'
                }],
                openCreateNewTagDialog:false,
                tagModel:{tagName:'', createFrom:'', message:'', releaseNotes:''},
                openEditNotesDialog : false,
                sshUrl : 'git@git.test.com:infrastructure/demo.git',
                httpUrl : 'http://git.test.com/infrastructure/demo.git'
            }
        },
        methods: {
            createNewProject(){
                this.isCreated = true;
            },
            t(){
                alert(123);
            },
            handleCreateNewBranch(){
                this.openCreateNewBranchDialog = true;
            },
            createBranch(){
                let newBranch = {
                    branchName : this.newBranchModel.newBranchName,
                    property : '',
                    latestCommit : '',
                    commitTime : 'now'
                };
                this.branchesTableData.push(newBranch);
                this.openCreateNewBranchDialog = false;
            },
            closeCreateNewBranchDialog(){
                this.openCreateNewBranchDialog = false;
            },
            createMergeRequest(from){
                this.mergeRequestModel.branchFrom = from;
                this.openCreateNewMergeRequestDialog = true;
            },
            createNewMergeRequest(){

            },
            closeCreateNewMergeRequestDialog(){
                this.openCreateNewMergeRequestDialog = false;
            },
            handleCreateNewTag(){
                this.openCreateNewTagDialog = true;
            },
            closeCreateNewTagDialog(){
                this.openCreateNewTagDialog = false;
            },
            createNewTag(){
                let tag = {
                    tagName:'Tag1',
                    message:'tag1',
                    createFrom:'master',
                    latestCommit : '5879a81a · 整合Hystrix  · 2 months ago',
                    releaseNotes:'xxxxxx',
                    createTime:'2018-12-3'
                };
                this.tagsTableData.push(tag);
                this.openCreateNewTagDialog = false;
            },
            handleEditNotes(){
                this.openEditNotesDialog = true;
            },
            closeEditNotesDialog(){
                this.openEditNotesDialog = false;
            },
            editNotes(){
                this.openEditNotesDialog = false;
            }

        }
    }
</script>

<style>

</style>