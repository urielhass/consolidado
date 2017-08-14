project.factory('UnityService', function ($resource) {
    return $resource(pathRest + '/unity', {}, {
        getList: { method: 'GET', params: {}, isArray: true },
        getUnity: { method: 'GET', params: {id: 'id'}, isArray: false },
        postUnity: { method: 'POST', params: {data: 'data'}, isArray: false},
        putUnity: { method: 'PUT', params: {data: 'data'}, isArray: false},
        deleteUnity: { method: 'DELETE', params: {id: 'id'}, isArray: false}
    })
});
