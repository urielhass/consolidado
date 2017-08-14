project.factory('SubCategoryService', function ($resource) {
    return $resource(pathRest + '/subCategory', {}, {
        getList: { method: 'GET', params: {}, isArray: true },
        getSubCategory: { method: 'GET', params: {id: 'id'}, isArray: false },
        postSubCategory: { method: 'POST', params: {data: 'data'}, isArray: false},
        putSubCategory: { method: 'PUT', params: {data: 'data'}, isArray: false},
        deleteSubCategory: { method: 'DELETE', params: {id: 'id'}, isArray: false}
    })
});
