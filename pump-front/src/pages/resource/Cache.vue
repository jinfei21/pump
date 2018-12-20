<!--
<template>
    <div>
        {{msg}}
    </div>
</template>

<script>
    import {mapActions, mapGetters} from "vuex"

    export default {
        data () {
            return {
                msg: '这个是Home模板页'
            }
        },
        computed: {
            ...mapGetters({
                appId: 'getAppId',
                env: 'getEnv'
            })
        },
        watch: {
            $route: function (newRoute) {
                alert(newRoute.query.env)
            }
        }
    }
</script>

<style>

</style>-->
<template>
    <iframe width="100%" height="100%" frameborder="0" style="position: absolute"
            src="http://fat-cache.test.com/admin/app/list.do"></iframe>
</template>