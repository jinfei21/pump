import restApi from '../restApi'

export default {

    getAppList(request = {}) {
        let url = '/api/apps';
        return restApi.doGetRequest(url);
    },

    applyNewApp(request = {}){
        let url = 'api/apps/newApp';
        return restApi.doPostRequest(url, request);
    },

    deleteApp(appId){
        let url = '/api/apps/' + appId;
        return restApi.doDeleteRequest(url)
    },

    createUser(request = {}) {
        let url = '/api/users';
        return restApi.doPostRequest(url, request);
    },

    getApp(appId,env) {
        let url = '/api/apps/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    applyDomainName(request = {}) {
        let url = 'api/apps/applyDomainName';
        return restApi.doPostRequest(url, request);
    },

    applyDB(request = {}) {
        let url = 'api/apps/applyDB';
        return restApi.doPostRequest(url, request);
    },

    applyApollo(request = {})  {
        let url = 'api/apps/applyApollo';
        return restApi.doPostRequest(url, request);
    },

    getCacheList(appId,env){
        let url = '/api/apps/cacheList/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    applyNewCache(request = {}){
        let url = 'api/apps/applyCache';
        return restApi.doPostRequest(url, request);
    },

    getCustomerGroupList(appId,env){
        let url = '/api/apps/consumerGroupList/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    applyNewCustomerGroup(request = {}){
        let url = 'api/apps/applyConsumerGroup';
        return restApi.doPostRequest(url, request);
    },

    getTopicList(appId,env){
        let url = '/api/apps/topicList/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    applyNewTopic(request = {}){
        let url = 'api/apps/applyTopic';
        return restApi.doPostRequest(url, request);
    },

    applyNewContainerCloud(request = {}){
        let url = 'api/apps/applyStargate';
        return restApi.doPostRequest(url, request);
    },

    getStargateList(appId,env){
        let url = '/api/apps/stargateList/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    createNewCluster(request = {}){
        let url = 'api/apps/createCluster';
        return restApi.doPostRequest(url, request);
    },

    deleteCluster(appId,env,currentCluster){
        let url = '/api/apps/deleteCluster/' + appId + '/' + env + '/' + currentCluster;
        return restApi.doGetRequest(url);
    },

    toRelease(appId,env,currentCluster){
        let url = '/api/apps/toRelease/' + appId + '/' + env + '/' + currentCluster;
        return restApi.doGetRequest(url);
    },

    toHistory(appId,env,currentCluster){
        let url = '/api/apps/releaseGroupLog/' + appId + '/' + env + '/' + currentCluster;
        return restApi.doGetRequest(url);
    },

    removeDomainNameMessage(appId,env){
        let url = '/api/apps/removeDomainNameMessage/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    removeDBMessage(appId,env){
        let url = '/api/apps/removeDBMessage/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    removeApolloMessage(appId,env){
        let url = '/api/apps/removeApolloMessage/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },

    removeStargateMessage(appId,env){
        let url = '/api/apps/removeStargateMessage/' + appId + '/' + env;
        return restApi.doGetRequest(url);
    },
    toReleaseGroupList(env,currentCluster){
        let url = '/api/apps/releaseGroupList/' + env + '/' + currentCluster;
        return restApi.doGetRequest(url);
    },
    createReleaseGroup(request = {}){
        let url = '/api/apps/createReleaseGroup';
        return restApi.doPostRequest(url, request);
    },
    adjustContainers(request = {}){
        let url = '/api/apps/updateReleaseGroup';
        return restApi.doPutRequest(url, request);
    },
    getContainerList(request = {}){
        let url = '/api/apps/containerList';
        return restApi.doGetRequest(url);
    },
    approveApp(id,isPass){
        let url = '/api/apps/approve/' + id + '/' + isPass;
        return restApi.doGetRequest(url);
    },
    approveDomainName(appId,env,isPass){
        let url = '/api/apps/approveDomainName/' + appId + '/' + env+ '/' + isPass;
        return restApi.doGetRequest(url);
    },
    approveDB(appId,env,isPass){
        let url = '/api/apps/approveDB/' + appId + '/' + env+ '/' + isPass;
        return restApi.doGetRequest(url);
    },
    approveApollo(appId,env,isPass){
        let url = '/api/apps/approveApollo/' + appId + '/' + env+ '/' + isPass;
        return restApi.doGetRequest(url);
    },
    approveConsumerGroup(appId,env,id,isPass){
        let url = '/api/apps/approveConsumerGroup/' + appId + '/' + env+ '/'+ id+ '/' + isPass;
        return restApi.doGetRequest(url);
    },
    approveTopic(appId,env,id,isPass){
        let url = '/api/apps/approveTopic/' + appId + '/' + env+ '/'+ id+ '/' + isPass;
        return restApi.doGetRequest(url);
    },
    approveCache(appId,env,id,isPass){
        let url = '/api/apps/approveCache/' + appId + '/' + env+ '/'+ id+ '/' + isPass;
        return restApi.doGetRequest(url);
    },
    approveStargate(appId,env,isPass){
        let url = '/api/apps/approveStargate/' + appId + '/' + env+ '/' + isPass;
        return restApi.doGetRequest(url);
    }

}
