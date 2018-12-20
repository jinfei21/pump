<template>
    <div class="layout-wrapper">
        <vframe></vframe>
        <div class="main-content" :style="{left: isCollapse ? '65px' : '220px'}">
            <transition name="move" mode="out-in">
                <router-view></router-view>
            </transition>
        </div>
    </div>
</template>

<script>
    import vframe from '../components/Frame.vue'
    import {mapGetters} from 'vuex'

    export default {
        components: {
            vframe
        },
        computed: {
            ...mapGetters({
                isCollapse: 'getCollapseState',
                promptMessage: 'getPromptMessage'
            })
        },
        watch: {
            promptMessage: function (newMessage) {
                if (newMessage.code != null) {
                    if (newMessage.code >= 0) {
                        this.$message.success(newMessage.details);
                    } else {
                        this.$message({
                            type: "error",
                            message: newMessage.details,
                            showClose: true,
                            duration: 5000
                        });
                    }
                }
            }
        }
    }

</script>

<style>

    @import '../assets/app.css';

    .main-content {
        background: none repeat scroll 0 0 #fff;
        position: absolute;
        right: 0;
        top: 60px;
        bottom: 0;
        width: auto;
        box-sizing: border-box;
        overflow-y: scroll;
    }

</style>