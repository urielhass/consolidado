project.factory('RequestService', function ($resource) {
    return $resource(pathRest + '/request', {}, {
        getList: { method: 'GET', params: {}, isArray: true },
        getRequest: { method: 'GET', params: {id: 'id'}, isArray: false },
        postRequest: { method: 'POST', params: {data: 'data'}, isArray: false},
        putRequest: { method: 'PUT', params: {data: 'data'}, isArray: false},
        deleteRequest: { method: 'DELETE', params: {id: 'id'}, isArray: false}
    })
});
