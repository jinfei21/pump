<template>
    <div class="content-panel">
        <el-row class="query-form">
            <el-col :span="3">
                <el-input placeholder="输入AppId"></el-input>
            </el-col>
            <el-col :span="3" :offset="1">
                <el-input placeholder="输入操作人"></el-input>
            </el-col>
            <el-button type="primary" @click="" >查询</el-button>
            <el-button @click="">重置</el-button>
        </el-row>
        <br/>
        <el-row>审计日志</el-row>
        <br/>
        <el-row>
            <el-table
                    :data="tableData"
                    style="width: 100%" border fit>
                <el-table-column
                        prop="id"
                        label="序号" align="center" width="100">
                </el-table-column>
                <el-table-column
                        prop="appId"
                        label="AppId" align="center" width="150">
                </el-table-column>
                <el-table-column
                        prop="event"
                        label="事件" align="center">
                </el-table-column>
                <el-table-column
                        prop="eventType"
                        label="事件类型" align="center" width="150">
                </el-table-column>
                <el-table-column
                        prop="result"
                        label="执行结构" align="center" width="150">
                    <template slot-scope="props">
                        <el-tag v-if="props.row.result == 0" type="success">成功</el-tag>
                        <el-tag v-else type="warning">失败</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="operator"
                        label="操作人" align="center" width="200">
                </el-table-column>
                <el-table-column
                        prop="operateTime"
                        label="操作时间" align="center" width="300">
                </el-table-column>
            </el-table>
        </el-row>
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
                    event:'cache 10174 申请扩容',
                    eventType:'apply',
                    result:0,
                    operator:'zhangxiao04',
                    operateTime:'2018-12-17 14:52:08'
                },{
                    id:'2',
                    appId:'100047',
                    event:'删除应用',
                    result:0,
                    eventType:'delete',
                    operator:'zhangxiao04',
                    operateTime:'2018-12-17 10:03:52'
                },{
                    id:'3',
                    appId:'100065',
                    event:'删除发布组s244.g43 - 版本0.0.6_09',
                    eventType:'delete',
                    result:0,
                    operator:'zhangxiao04',
                    operateTime:'2018-12-17 15:42:00'
                },{
                    id:'4',
                    appId:'100029',
                    event:'MQ 创建topic MessageToESSub',
                    eventType:'create',
                    result:-1,
                    operator:'zhangxiao04',
                    operateTime:'2018-12-17 16:04:09'
                }]
            }
        }
    }
</script>

<!--
<style>

</style>-->
