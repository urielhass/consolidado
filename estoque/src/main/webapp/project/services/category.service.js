angular.module('myApp.services', []).factory('CategoryService', ['$resource', function ($resource) {
  return $resource(project.pathPrivate + '/category/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getCategory: { method: 'GET', params: {id: 'id'}, isArray: false },
    postCategory: { method: 'POST', params: {}, isArray: false},
    putCategory: { method: 'PUT', params: {}, isArray: false},
    deleteCategory: { method: 'DELETE', params: {id: 'id'}, isArray: false}
  })
}]);
