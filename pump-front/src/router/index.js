import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

import Layout from '../pages/Layout.vue'
import Login from 'gravitee-vue-support/components/Login.vue'

import AppList from '../pages/AppList.vue'
import AppDetail from '../pages/AppDetail.vue'

import GitLab from '../pages/development/GitLab.vue'
import CI from '../pages/development/CI.vue'
import CodeCheck from '../pages/development/CodeCheck.vue'

import Domain from '../pages/resource/Domain.vue'
import Machine from '../pages/resource/Machine.vue'
import Db from '../pages/resource/Db.vue'
import Apollo from '../pages/resource/Apollo.vue'
import ApolloConfig from '../pages/resource/ApolloConfig.vue'
import Mq from '../pages/resource/Mq.vue'
import Cache from '../pages/resource/Cache.vue'
import Job from '../pages/resource/Job.vue'

import Docker from '../pages/release/Docker.vue'
import ClusterList from '../pages/release/ClusterList.vue'
import ReleaseGroupList from '../pages/release/ReleaseGroupList.vue'
import Vm from '../pages/release/Vm.vue'
import H5 from '../pages/release/H5.vue'
import VmStructureHistory from '../pages/release/VmStructureHistory.vue'
import JenkinsConfig from '../pages/release/JenkinsConfig.vue'

import Log from '../pages/monitor/Log.vue'
import Metrics from '../pages/monitor/Metrics.vue'
import Cat from '../pages/monitor/Cat.vue'
import Gateway from '../pages/monitor/Gateway.vue'

import ApplyList from '../pages/ApplyList.vue'
import AuditLog from '../pages/AuditLog.vue'

export default new Router({
    mode: 'hash', // mode option: 1. hash (default), 2. history
    routes: [{
        path: '',
        name: 'base',
        component: Layout,
        children: [{
            path: 'apps',
            name: 'apps',
            component: AppList,
        },{
            path: 'appDetail',
            name: 'appDetail',
            component: AppDetail,
        },{
            path: 'development/gitlab',
            name: 'gitlab',
            component: GitLab
        },{
            path: 'development/ci',
            name: 'ci',
            component: CI
        },{
            path: 'development/codecheck',
            name: 'codecheck',
            component: CodeCheck
        },{
            path: 'resource/domain',
            name: 'domain',
            component: Domain
        },{
            path: 'resource/machine',
            name: 'machine',
            component: Machine
        },{
            path: 'resource/db',
            name: 'db',
            component: Db
        },{
            path: 'resource/apollo',
            name: 'apollo',
            component: Apollo
        },{
            path: 'resource/apollo/apolloConfig',
            name: 'apolloConfig',
            component: ApolloConfig
        },{
            path: 'resource/mq',
            name: 'mq',
            component: Mq
        },{
            path: 'resource/cache',
            name: 'cache',
            component: Cache
        },{
            path: 'resource/job',
            name: 'job',
            component: Job
        },{
            path: 'release/docker',
            name: 'docker',
            component: Docker
        },{
            path: 'release/docker/clusterList',
            name: 'clusterList',
            component: ClusterList
        },{
            path: 'release/docker/clusterList/releaseGroupList',
            name: 'releaseGroupList',
            component: ReleaseGroupList
        }
        ,{
            path: 'release/vm',
            name: 'vm',
            component: Vm
        },
        {
            path: 'release/vm/vmStructureHistory',
            name: 'vmStructureHistory',
            component: VmStructureHistory
        },{
            path: 'release/vm/jenkinsConfig',
            name: 'jenkinsConfig',
            component: JenkinsConfig
        },{
              path: 'release/h5',
              name: 'h5',
              component: H5
        },{
            path: 'monitor/log',
            name: 'log',
            component: Log
        },{
            path: 'monitor/metrics',
            name: 'metrics',
            component: Metrics
        },{
            path: 'monitor/cat',
            name: 'cat',
            component: Cat
        },{
            path: 'monitor/gateway',
            name: 'gateway',
            component: Gateway
        },{
            path: 'applylist',
            name: 'applylist',
            component: ApplyList,
        },{
            path: 'auditlogs',
            name: 'auditlogs',
            component: AuditLog,
        }]
    }, {
        path: '/login/callback',
        name: 'Login',
        component: Login,
    }],
    linkActiveClass: 'active'
})
