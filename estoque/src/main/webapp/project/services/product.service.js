project.factory('ProductService', function ($resource) {
    return $resource(pathRest + '/product', {}, {
        getList: { method: 'GET', params: {}, isArray: true },
        getProduct: { method: 'GET', params: {id: 'id'}, isArray: false },
        postProduct: { method: 'POST', params: {data: 'data'}, isArray: false},
        putProduct: { method: 'PUT', params: {data: 'data'}, isArray: false},
        deleteProduct: { method: 'DELETE', params: {id: 'id'}, isArray: false}
    })
});
