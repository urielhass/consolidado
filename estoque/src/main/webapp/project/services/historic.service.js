project.factory('HistoricService', function ($resource) {
    return $resource(pathRest + '/historic', {}, {
        getList: { method: 'GET', params: {}, isArray: true },
        getHistoric: { method: 'GET', params: {id: 'id'}, isArray: false },
        postHistoric: { method: 'POST', params: {data: 'data'}, isArray: false},
        putHistoric: { method: 'PUT', params: {data: 'data'}, isArray: false},
        deleteHistoric: { method: 'DELETE', params: {id: 'id'}, isArray: false}
    })
});
