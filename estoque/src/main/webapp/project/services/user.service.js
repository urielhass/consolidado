project.factory('UserService', function ($resource) {
    return $resource(pathRest + '/users', {}, {
        getList: { method: 'GET', params: {}, isArray: true },
        getUser: { method: 'GET', params: {id: 'id'}, isArray: false },
        postUser: { method: 'POST', params: {data: 'data'}, isArray: false},
        putUser: { method: 'PUT', params: {data: 'data'}, isArray: false},
        deleteUser: { method: 'DELETE', params: {id: 'id'}, isArray: false}
    })
});
