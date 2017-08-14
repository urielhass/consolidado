project.factory('LoginService', ['$resource', function ($resource) {
  return $resource(project.pathPrivate + '/login/auth', {}, {
    auth: { method: 'POST', params: {}, isArray: false },
  })
}]);
project.factory('UserService', ['$resource', function ($resource) {
  return $resource(project.pathPrivate + '/user/:method/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getUser: { method: 'GET', params: { id: 'id' }, isArray: false },
    postUser: { method: 'POST', params: {}, isArray: false },
    putUser: { method: 'PUT', params: { data: 'data' }, isArray: false },
    deleteUser: { method: 'DELETE', params: { id: 'id' }, isArray: false },
    editActualUser: { method: 'GET', params: { method: 'editActualUser' }, isArray: false }
  })
}]);
project.factory('CategoryService', function ($resource) {
  return $resource(project.pathPrivate + '/category/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getCategory: { method: 'GET', params: { id: 'id' }, isArray: false },
    postCategory: { method: 'POST', params: {}, isArray: false },
    putCategory: { method: 'PUT', params: {}, isArray: false },
    deleteCategory: { method: 'DELETE', params: { id: 'id' }, isArray: false }
  })
});
project.factory('SubCategoryService', function ($resource) {
  return $resource(project.pathPrivate + '/subCategory/:method/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getSubCategory: { method: 'GET', params: { id: 'id' }, isArray: false },
    postSubCategory: { method: 'POST', params: {}, isArray: false },
    putSubCategory: { method: 'PUT', params: { data: 'data' }, isArray: false },
    deleteSubCategory: { method: 'DELETE', params: { id: 'id' }, isArray: false },
    filterCategory: { method: 'GET', params: { method: 'filterCategory', id: 'id' }, isArray: false }
  })
});
project.factory('RequestService', function ($resource) {
  return $resource(project.pathPrivate + '/request/:method/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getListByUser: {method: 'GET', params: { method: 'listRequestByUser' }, isArray: false},
    getRequest: { method: 'GET', params: {id: '@id'}, isArray: false },
    postRequest: { method: 'POST', params: {}, isArray: false},
    putRequest: { method: 'PUT', params: {data: 'data'}, isArray: false},
    deleteRequest: { method: 'DELETE', params: {id: '@id'}, isArray: false},
    requestPending : {method: 'GET', params: {method: 'listRequestPending'}},
    disapproved : {method: 'PUT', params: {method: 'disapproved'}},
    approved : {method: 'PUT', params: {method: 'approved'}},
    moveStatus: {method: 'PUT', params: {method: 'moveStatus', id: '@id'}},
    listRequestApproved: {method: 'GET', params: {method: 'listRequestApproved'}},
    listRequestFinish: {method: 'GET', params: {method: 'listRequestFinish'}},
    listRequestByStatus: {method: 'GET', params: {method: 'listRequestByStatus', id: '@id'}  },
    listRequestAll: {method: 'GET' ,params:{method: 'listRequestAll'}, isArray: false},
  })
});

project.factory('StockService', function ($resource) {
  return $resource(project.pathPrivate + '/stock/:method/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getStock: { method: 'GET', params: { id: '@id' }, isArray: false },
    postStock: { method: 'POST', params: {}, isArray: false },
    putStock: { method: 'PUT', /*params: {data: 'data'},*/ isArray: false },
    deleteStock: { method: 'DELETE', params: { id: '@id' }, isArray: false },
    listStockLow: { method: 'GET', params: { method: 'listStockLow' } },
    listTotal: { method: 'GET', params: { method: 'listTotal' } },
    listHistoric: { method: 'GET', params: { method: 'listHistoric' } },
    monthWithRequest: { method: 'GET', params: { method: 'monthWithRequest' } },
    totalHistoric: { method: 'GET', params: { method: 'totalHistoric' } },
    listStockByFilter: {method: 'GET', params: {method: 'listStockByFilter'}}
  })
});

project.factory('UnityService', function ($resource) {
  return $resource(project.pathPrivate + '/unit/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getUnity: { method: 'GET', params: { id: 'id' }, isArray: false },
    postUnity: { method: 'POST', params: {}, isArray: false },
    putUnity: { method: 'PUT', params: { data: 'data' }, isArray: false },
    deleteUnity: { method: 'DELETE', params: { id: 'id' }, isArray: false }
  })
});

project.factory('CityService', function ($resource) {
  return $resource(project.pathPrivate + '/city/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getCity: { method: 'GET', params: { id: 'id' }, isArray: false },
    postCity: { method: 'POST', params: { data: 'data' }, isArray: false },
    putCity: { method: 'PUT', params: { data: 'data' }, isArray: false },
    deleteCity: { method: 'DELETE', params: { id: 'id' }, isArray: false }
  })
});

project.factory('StateService', function ($resource) {
  return $resource(project.pathPrivate + '/state', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getState: { method: 'GET', params: { id: 'id' }, isArray: false },
    postState: { method: 'POST', params: { data: 'data' }, isArray: false },
    putState: { method: 'PUT', params: { data: 'data' }, isArray: false },
    deleteState: { method: 'DELETE', params: { id: 'id' }, isArray: false }
  })
});

project.factory('LocationService', function ($resource) {
  return $resource(project.pathPrivate + '/container/:id', {}, {
    getList: { method: 'GET', params: {}, isArray: false },
    getLocation: { method: 'GET', params: { id: 'id' }, isArray: false },
    postLocation: { method: 'POST', params: { data: 'data' }, isArray: false },
    putLocation: { method: 'PUT', params: { data: 'data' }, isArray: false },
    deleteLocation: { method: 'DELETE', params: { id: 'id' }, isArray: false },
  });
});

project.service('ToastService', function ($mdToast) {
  this.alert = function (text, action, position, delay) {
    if (!text) { text = 'Operação realizada com sucesso'; }
    if (!position) { position = 'bottom right'; }
    if (!delay) { delay = 3000; }

    var toast = $mdToast.simple()
      .textContent(text)
      .position(position)
      .hideDelay(delay);

    if (action) { toast.action(action); }

    return $mdToast.show(toast);
  };
  this.tpl = function (tpl, data, controller, position, delay) {

    if (!position) { position = 'bottom right'; }
    if (!delay) { delay = 3000; }

    var toast = {
      hideDelay: delay,
      position: position
    };
    if (tpl) { toast.tpl = tpl; }
    if (data) { toast.data = data; }
    if (controller) { toast.controller = controller; }

    this.alert = function (text, action, position, delay) {
      if (!text) { text = 'Operação realizada com sucesso'; }
      if (!position) { position = 'bottom right'; }
      if (!delay) { delay = 3000; }
      if (!action) { action = 'Fechar'; }

      var toast = $mdToast.simple()
        .textContent(text)
        .position(position)
        .hideDelay(delay)
        .action(action);

      return $mdToast.show(toast);
    };
    this.tpl = function (tpl, data, controller, position, delay) {

      if (!position) { position = 'bottom right'; }
      if (!delay) { delay = 3000; }

      var toast = {
        hideDelay: delay,
        position: position
      };
      if (tpl) { toast.tpl = tpl; }
      if (data) { toast.data = data; }
      if (controller) { toast.controller = controller; }

      return $mdToast.show(toast);
    };

    return $mdToast.show(toast);
  };
});

project.service('ArrayService', function () {

  this.add = function (array, data) {

    if (array === null || array === undefined || array.length <= 0) {
      array = [];
    };
    array.push(data);
    return array;
  };

  this.edit = function (index, data, array) {
    array[index] = data;
    return array;
  };

  this.remove = function (index, array) {

    if (index === null || index === undefined || array === null || array === undefined) {
      return null;
    } else {
      array.splice(index, 1);
      return array;
    };
  };
});

project.service('AvatarService', function () {
  this.getAvatar = function (userID) {
    var avatar = '';
    if (userID) {
      avatar = userID.toLowerCase().substr(0, 2);
    }
    else {
      avatar = 'aa';
    }
    return avatar + '.png';
  };
});

project.service('ModalService', function ($mdDialog) {

  this.alert = function (text, title, ok) {
    if (!text) { text = 'Essa operação requer cuidados.'; }
    if (!title) { title = 'Atenção'; }
    if (!ok) { ok = 'OK'; }

    $mdDialog.show(
      $mdDialog.alert()
        .clickOutsideToClose(true)
        .title(title)
        .textContent(text)
        .ariaLabel('Alert Dialog Demo')
        .ok(ok)
    );
  };
  this.confirm = function (text, title, ok, cancel) {

    if (!text) { text = 'Confirma essa operação?'; }
    if (!title) { title = 'Confirma'; }
    if (!ok) { ok = 'Confirma'; }
    if (!cancel) { cancel = 'Cancela'; }

    var confirm = $mdDialog.confirm()
      .title(title)
      .textContent(text)
      .ariaLabel('Lucky day')
      .ok(ok)
      .cancel(cancel);

    return $mdDialog.show(confirm);
  };
  this.confirmInput = function (text, title, placeholder, value, ok, cancel) {

    if (!text) { text = 'Confirma essa operação?'; }
    if (!title) { title = 'Confirma'; }
    if (!ok) { ok = 'Confirma'; }
    if (!cancel) { cancel = 'Cancela'; }

    var confirm = $mdDialog.prompt()
      .title(title)
      .textContent(text)
      .ariaLabel('confirm')
      .ok(ok)
      .cancel(cancel);

    if (placeholder) {
      confirm.placeholder(placeholder);
    }

    if (value) {
      confirm.initialValue(value);
    }

    return $mdDialog.show(confirm);
  };
  this.tpl = function (tpl, data, controller) {

    var modal = {};

    if (tpl) { modal.templateUrl = tpl; }
    if (data) { modal.data = data; }
    if (controller) { modal.controller = controller; }

    return $mdDialog.show(modal);
  };
});