<template>
    <div>
        <div class="header">
            <div class="collapse-button"><i :class="collapseButtonClass" @click="onCollapse"></i></div>
            <div class="title"><span @click="returnHome">应用管理平台</span></div>
            <el-select v-model="appId" placeholder="选择应用" @change="selectApp" class="select-app" v-if="showSelect">
                <el-option v-for="item in apps"
                        :key="item.appId"
                        :label="item.appName"
                        :value="item.appId">
                </el-option>
            </el-select>
            <el-select v-model="env" placeholder="选择环境" @change="selectEnv" class="select-env" v-if="showSelect">
                <el-option v-for="item in envs"
                           :key="item"
                           :label="item"
                           :value="item">
                </el-option>
            </el-select>
            <el-menu class="header-menu" mode="horizontal" @select="selectHeaderMenu"
                     :default-active="headerMenuItem" router
                     background-color="#434a50"
                     text-color="#fff"
                     active-text-color="#f98f41">
                <el-menu-item v-for="item in developmentMenu" :index="item.index" v-if="sidebarMenuItem == 'development'"
                              :route="{name: item.index, query: routeQuery}">
                    {{item.label}}
                </el-menu-item>
                <el-menu-item v-for="item in resourceMenu" :index="item.index" v-if="sidebarMenuItem == 'resource'"
                              :route="{name: item.index, query: routeQuery}">
                    {{item.label}}
                </el-menu-item>
                <el-menu-item v-for="item in releaseMenu" :index="item.index" v-if="sidebarMenuItem == 'release'"
                              :route="{name: item.index, query: routeQuery}">
                    {{item.label}}
                </el-menu-item>
                <el-menu-item v-for="item in monitorMenu" :index="item.index" v-if="sidebarMenuItem == 'monitor'"
                              :route="{name: item.index, query: routeQuery}">
                    {{item.label}}
                </el-menu-item>
            </el-menu>
            <div class="user-info">
                <el-dropdown @command="handleCommand">
                    <div class="el-dropdown-link">
                        <img v-if="username != null" :src="imgUrl" class="user-logo">
                        {{isLogin ? username : "您好，请登录"}}
                    </div>
                    <el-dropdown-menu slot="dropdown" class="dropdown-menu">
                        <el-dropdown-item command="logout" v-if='isLogin'>退出</el-dropdown-item>
                        <el-dropdown-item command="login" v-else>登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>

        <div class="sidebar">
            <el-menu class="sidebar-menu" :collapse="isCollapse" :collapse-transition="false"
                     @select="selectSidebarMenu" :default-active="sidebarMenuItem" router
                     background-color="#545c64"
                     text-color="#fff"
                     active-text-color="#f98f41">
                <el-menu-item index="app" :route="{name: 'apps'}">
                    <i class="el-icon-star-on"></i>
                    <span slot="title">应用列表</span>
                </el-menu-item>
                <el-menu-item index="development" :route="{name: 'gitlab', query: routeQuery}">
                    <i class="el-icon-edit-outline"></i>
                    <span slot="title">应用开发</span>
                </el-menu-item>
                <el-menu-item index="resource" :route="{name: 'machine', query: routeQuery}">
                    <i class="el-icon-goods"></i>
                    <span slot="title">应用资源</span>
                </el-menu-item>
                <el-menu-item index="release" :route="{name: 'docker', query: routeQuery}">
                    <i class="el-icon-upload"></i>
                    <span slot="title">应用发布</span>
                </el-menu-item>
                <el-menu-item index="monitor" :route="{name: 'log', query: routeQuery}">
                    <i class="el-icon-view"></i>
                    <span slot="title">应用监控</span>
                </el-menu-item>
                <el-menu-item index="applylist" :route="{name: 'applylist'}">
                    <i class="el-icon-message"></i>
                    <span slot="title">申请记录</span>
                </el-menu-item>
                <el-menu-item index="auditlogs" :route="{name: 'auditlogs'}">
                    <i class="el-icon-tickets"></i>
                    <span slot="title">审计日志</span>
                </el-menu-item>
            </el-menu>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'
    import crypto from 'crypto'
    import Identicon from 'identicon.js'

    export default {
        data: function () {
            return {
                appId: '',
                env: '',
                isCollapse: false,
                headerMenuItem: '',
                sidebarMenuItem: '',
                apps: [{appId: '10001', appName: 'demo'}],
                envs: ['dev', 'fat', 'uat', 'pro'],
                developmentMenu: [
                    {label: '代码仓库', index: 'gitlab'},
                    {label: 'CI', index: 'ci'},
                    {label: '代码检查', index: 'codecheck'}
                ],
                resourceMenu: [
                    {label: '域名', index: 'domain'},
                    {label: '机器资源', index: 'machine'},
                    {label: '数据库资源', index: 'db'},
                    {label: 'Apollo', index: 'apollo'},
                    {label: '消息队列', index: 'mq'},
                    {label: 'Cache', index: 'cache'},
                    {label: 'Job', index: 'job'}
                ],
                releaseMenu: [
                    {label: '容器云发布', index: 'docker'},
                    {label: '虚拟机发布', index: 'vm'},
                    {label: 'API网关', index: 'h5'}
                ],
                monitorMenu: [
                    {label: '日志', index: 'log'},
                    {label: 'Metrics', index: 'metrics'},
                    {label: '调用链', index: 'cat'}/*,
                    {label: '网关', index: 'gateway'}*/
                ]
            }
        },
        computed: {
            ...mapGetters({
                isLogin: 'getLoginState',
                username: 'getUserName',
                promptMsg: 'getPromptMsg'
            }),
            routeQuery() {
                let query = null;
                if (this.appId != '' && this.env == '') {
                    query = {appid: this.appId};
                } else if (this.appId == '' && this.env != '') {
                    query = {env: this.env};
                } else if (this.appId != '' && this.env != '') {
                    query = {appid: this.appId, env: this.env};
                }
                return query;
            },
            imgUrl: function () {
                let hash = crypto.createHash('md5');
                hash.update(this.username);
                let imgData = new Identicon(hash.digest('hex'), 40).toString();
                let imgUrl = 'data:image/png;base64,' + imgData;
                return imgUrl;
            },
            collapseButtonClass() {
                return this.isCollapse ? 'el-icon-more' : 'el-icon-menu';
            },
            showSelect() {
                let arr = ['development', 'resource', 'release', 'monitor'];
                return arr.includes(this.sidebarMenuItem);
            }
        },
        methods: {
            onCollapse() {
                this.isCollapse = !this.isCollapse;
                this.$store.commit('refreshCollapseState', this.isCollapse);
            },
            handleCommand(command) {
                if (command == 'logout') {
                    this.logout();
                } else if (command == 'login') {
                    this.login();
                }
            },
            returnHome() {
                this.$router.push('/');
            },
            logout(){
                this.$store.dispatch('saveLastVisit', this.$route.fullPath);
                this.$store.dispatch('logout');
            },
            login(){
                this.$store.dispatch('saveLastVisit', this.$route.fullPath);
                this.$store.dispatch('login');
            },
            selectApp(data) {
                this.appId = data;
                this.$store.commit('refreshAppId', data);
            },
            selectEnv(data) {
                this.env = data;
                this.$store.commit('refreshEnv', data);
            },
            selectHeaderMenu(data) {
                this.headerMenuItem = data;
            },
            selectSidebarMenu(data) {
                this.sidebarMenuItem = data;
                if (data == 'development') {
                    this.headerMenuItem = 'gitlab';
                } else if (data == 'resource') {
                    this.headerMenuItem = 'machine';
                } else if (data == 'release') {
                    this.headerMenuItem = 'docker';
                } else if (data == 'monitor') {
                    this.headerMenuItem = 'log';
                }
            }
        },
        watch: {
            appId: function (newAppId) {
                this.$router.replace({name: this.$route.name, query: {appid: newAppId, env: this.$route.query.env}});
            },
            env: function (newEnv) {
                this.$router.replace({name: this.$route.name, query: {appid: this.$route.query.appid, env: newEnv}});
            },
            $route: function (newRoute) {

            },
            promptMsg: function (newMessage) {
                if (newMessage.code != null) {
                    if (newMessage.code >= 0) {
                        this.$message.success(newMessage.details);
                    } else {
                        this.$message.error(newMessage.details);
                    }
                }
            }
        },
        created () {
            this.$store.dispatch('readLoginInfo');
            let query = this.$route.query;
            if (query && query.appid) {
                this.appId = query.appid;
                this.$store.commit('refreshAppId', query.appid);
            }
            if (query && query.env) {
                this.env = query.env;
                this.$store.commit('refreshEnv', query.env);
            }
            let name = this.$route.name;
            if (name == 'apps' || name == 'applylist' || name == 'auditlogs') {
                this.sidebarMenuItem = name;
            } else {
                let path = this.$route.path;
                let pathArr = path.split("/");
                this.sidebarMenuItem = pathArr[1];
                this.headerMenuItem = pathArr[2];
            }
        },
        beforeMount() {
            if (!this.isLogin) {
                this.$message.warning("您好，请登录");
            }
        }
    };
</script>

<style>

    .header {
        background-color: #434a50;
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 60px;
        font-size: 22px;
        line-height: 60px;
        color: #fff;
    }

    .header .title {
        margin-left: 20px;
        margin-right: 30px;
        float: left;
        font-size: 24px;
    }

    .header .title span {
        cursor: pointer;
    }

    .header .title span:hover {
        color: #f98f41;
    }

    .header .collapse-button {
        margin-left: 20px;
        font-size: 24px;
        float: left;
    }

    .header .collapse-button i {
        cursor: pointer;
    }

    .header .collapse-button i:hover {
        color: #f98f41;
    }

    .header .select-app {
        float: left;
        width: 200px;
    }

    .header .select-env {
        float: left;
        margin: 0 20px 0 10px;
        width: 110px;
    }

    .header .el-select .el-input.is-focus .el-input__inner {
        border-color: transparent;
    }

    .header .el-select .el-input__inner:focus {
        border-color: transparent;
    }

    .header .header-menu {
        float: left;
    }

    .user-info {
        float: right;
        margin-right: 35px;
        font-size: 16px;
        color: #fff;
    }

    .user-info .el-dropdown {
        display: block;
    }

    .user-info .el-dropdown-link {
        position: relative;
        padding-left: 50px;
        color: #fff;
        cursor: pointer;
    }

    .user-info .user-logo {
        position: absolute;
        left: 0;
        top: 10px;
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .el-dropdown-menu__item {
        text-align: center;
    }

    .dropdown-menu {
        min-width: 100px;
    }

    .sidebar {
        position: absolute;
        width: 220px;
        left: 0;
        top: 60px;
        bottom: 0;
    }

    .sidebar > ul {
        height: 100%;
    }

    .header .el-menu-item.is-disabled, .sidebar .el-menu-item.is-disabled {
        opacity: 1;
    }

</style>