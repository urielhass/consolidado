/* -----------------------------------------------------------------------------------
* ------------------------------Controller index-------------------------------------
* -----------------------------------------------------------------------------------*/

project.controller('IndexCtrl', ['$scope', '$route', '$routeParams', '$location', '$mdSidenav', '$localStorage', 'UserService', 'UnityService', 'ArrayService', 'ModalService', 'ToastService', 'AvatarService',
	function ($scope, $route, $routeParams, $location, $mdSidenav, $localStorage, UserService, UnityService, ArrayService, ModalService, ToastService, AvatarService) {

		$scope.$route = $route;
		$scope.$location = $location;
		$scope.$routeParams = $routeParams;
		$scope.$localStorage = $localStorage;
		$scope.Avatar = '';


		$scope.menuClick = function (link) {
			$location.path(link);
			$mdSidenav('left').close();
		};

		$scope.close = function () {
			$mdSidenav('left').close();
		};

		$scope.toggleLeft = function () {
			$mdSidenav('left').toggle();
		};

		($scope.getPermission = function () {

			if ($localStorage.currentUser) {

				$scope.items = [];

				$scope.items.push({ name: 'DASHBOARD', icon: 'settings', link: '/dashboard' });

				if ($localStorage.currentUser.permission == 10) {
					$scope.items.push({ name: 'REQUISIÇÃO', icon: 'label_outline', link: '/request' });
				}

				if ($localStorage.currentUser.permission == 20 || $localStorage.currentUser.permission == 30) {
					$scope.items.push({ name: 'ESTOQUE', icon: 'view_column', link: '/stock' });
					$scope.items.push({ name: 'REQ. AGUARDANDO ENVIO', icon: 'directions_car', link: '/request/sending' });
				}

				if ($localStorage.currentUser.permission == 30) {
					$scope.items.push({ name: 'REQUISIÇÃO', icon: 'label_outline', link: '/request' });
					$scope.items.push({ name: 'REQ. PENDENTE', icon: 'report_problem', link: '/request/pending' });
				}
				$scope.items.push({ name: 'TODAS REQUISIÇÕES', icon: 'chrome_reader_mode', link: '/request/all' });
				if ($localStorage.currentUser.permission == 30) {
					$scope.items.push({ name: 'CATEGORIAS', icon: 'reorder', link: '/category' });
					$scope.items.push({ name: 'UNIDADE', icon: 'place', link: "/unity" });
					$scope.items.push({ name: 'USUÁRIO', icon: 'face', link: "/users" });
					$scope.items.push({ name: 'HISTÓRICO TOTAL MÊS', icon: 'book', link: "/monthTotal" });
					$scope.items.push({ name: 'HISTÓRICO', icon: 'book', link: '/historic' });
				}
				if ($localStorage.currentUser.permission == 20 || $localStorage.currentUser.permission == 30) {
					$scope.items.push({ name: 'LOCALIZAÇÃO', icon: 'place', link: '/location' });
				}
			}
		})();

		$scope.editActualUser = function () {
			openModalActual($scope.actualUser);
		};

		($scope.getActualUser = function () {
			if (($scope.actualUser == null || $scope.actualUser == undefined) && $localStorage.currentUser) {
				UserService.editActualUser(function (response) {
					$scope.actualUser = response.data;

					$scope.Avatar = AvatarService.getAvatar($scope.actualUser.name);
				});
			}
		})();

		UnityService.getList(function (response) {
			$scope.units = response.data;
		});


		function openModalActual(user) {
			UnityService.getList(function (response) {
				$scope.units = response.data;
			});
			var listUnits = angular.copy($scope.units);
			if ($localStorage.currentUser.permission == 30) {
				if (user != null || user != undefined) {
					if (listUnits) {
						for (var i = 0; i < listUnits.length; i++) {
							if (user.units) {
								for (var c = 0; c < user.units.length; c++) {
									if (listUnits[i].id == user.units[c].id) {
										listUnits = ArrayService.remove(i, listUnits);
									};
								};
							};
						};
					}
				};
			}

			var data = {
				user: user,
				units: listUnits
			};

			ModalService.tpl('userModalActual.html', data, function ($scope, $mdDialog, data, ArrayService, $localStorage) {

				$scope.units = angular.copy(data.units);

				$scope.permission = $localStorage.currentUser.permission;

				$scope.title = "";

				$scope.user = {
					name: null,
					phone: null,
					email: null,
					password: null,
					status: false,
					adm: false
				};

				if (data.user != null || data.user != undefined) {
					$scope.title = "Editar " + data.user.name;
					$scope.user = angular.copy(data.user);
					$scope.CompareEmail = $scope.user.email;
					$scope.user.password = null;
				} else {
					$scope.title = "Novo UsuÃ¡rio";
				};

				$scope.addUnityToUser = function (unit) {

					for (var i = 0; i < $scope.units.length; i++) {
						if ($scope.units[i].id == unit.id) {
							$scope.units = ArrayService.remove(i, $scope.units);
							break;
						};
					};
					$scope.user.units = ArrayService.add($scope.user.units, unit);
				};

				$scope.deleteUnity = function (index, unit) {
					$scope.user.units = ArrayService.remove(index, $scope.user.units);
					$scope.units = ArrayService.add($scope.units, unit);
				};
				$scope.cancel = function () {
					$mdDialog.cancel();
				};
				$scope.save = function () {
					var data = {
						user: $scope.user,
						index: $scope.index
					};
					UserService.putUser(data.user, function (response) {
						ToastService.alert('UsuÃ¡rio editado com sucesso!', undefined, 'bottom left', 3000);
					})
					$mdDialog.hide(data);
				};
			});
		};
	}]);

project.controller('LoginCtrl', ['$location', '$localStorage', '$http', '$scope', '$route', '$routeParams', '$mdSidenav', 'LoginService', 'ToastService', 'UserService', 'UnityService', 'AvatarService',
	function ($location, $localStorage, $http, $scope, $route, $routeParams, $mdSidenav, LoginService, ToastService, UserService, UnityService, AvatarService) {

		$scope.login = function (event, User) {

			if (User.password) {
				User.password = btoa(User.password);
			}
			LoginService.auth(User, function (response) {
				if (response.data) {
					$localStorage.currentUser = { email: User.email, token: response.data.token, permission: response.data.permission, adm: response.data.adm };
					$scope.$parent.$parent.Avatar = AvatarService.getAvatar(response.data.name);
					$scope.getData();

				}
			}, function (err) {
				User.password = "";
				ToastService.alert(err.data.message);
			})
		};

		$scope.toDashBoard = function () {
			$location.path('/');
		};

		$scope.logout = function (event) {
			$scope.$parent.close();
			$localStorage.$reset();
			$location.path('/login');
		};

		$scope.getData = function () {

			($scope.getActualUser = function () {
				UserService.editActualUser(function (res) {
					$scope.$parent.$parent.actualUser = res.data;
					$scope.getAllUnits();
				});
			})();

			($scope.getPermission = function () {
				$scope.$parent.$parent.items = [];

				$scope.$parent.$parent.items.push({ name: 'DASHBOARD', icon: 'settings', link: '/dashboard' });

				if ($localStorage.currentUser.permission == 10) {
					$scope.$parent.$parent.items.push({ name: 'REQUISIÃ‡ÃƒO', icon: 'label_outline', link: '/request' });
				}

				if ($localStorage.currentUser.permission == 20 || $localStorage.currentUser.permission == 30) {
					$scope.$parent.$parent.items.push({ name: 'ESTOQUE', icon: 'view_column', link: '/stock' });
					$scope.$parent.$parent.items.push({ name: 'REQ. AGUARDANDO ENVIO', icon: 'directions_car', link: '/request/sending' });
				}

				if ($localStorage.currentUser.permission == 30) {
					$scope.$parent.$parent.items.push({ name: 'REQUISIÇÃO', icon: 'label_outline', link: '/request' });
					$scope.$parent.$parent.items.push({ name: 'REQ. PENDENTE', icon: 'report_problem', link: '/request/pending' });
				}
				$scope.$parent.$parent.items.push({ name: 'TODAS REQUISIÇÕES', icon: 'chrome_reader_mode', link: '/request/all' });
				if ($localStorage.currentUser.permission == 30) {
					$scope.$parent.$parent.items.push({ name: 'CATEGORIAS', icon: 'reorder', link: '/category' });
					$scope.$parent.$parent.items.push({ name: 'UNIDADE', icon: 'place', link: "/unity" });
					$scope.$parent.$parent.items.push({ name: 'USUÁRIO', icon: 'face', link: "/users" });
					$scope.$parent.$parent.items.push({ name: 'HISTÓRICO TOTAL MÊS', icon: 'book', link: "/monthTotal" });
					$scope.$parent.$parent.items.push({ name: 'HISTÓRICO', icon: 'book', link: '/historic' });
				}
				if ($localStorage.currentUser.permission == 20 || $localStorage.currentUser.permission == 30) {
					$scope.$parent.$parent.items.push({ name: 'LOCALIZAÇÃO', icon: 'place', link: '/location' });
				}
			})();
			$scope.getAllUnits = function () {
				if ($scope.$parent.$parent.units == null || $scope.$parent.$parent.units == undefined) {
					UnityService.getList(function (response) {
						$scope.$parent.$parent.units = response.data;
					});
				};
				$scope.toDashBoard();
			};
		};


	}]);

/* -----------------------------------------------------------------------------------
* ------------------------------Controller User-------------------------------------
* -----------------------------------------------------------------------------------*/

project.controller('UserCtrl', ['$scope', '$localStorage', '$mdDialog', '$mdToast', 'UserService', 'ModalService', 'UnityService', 'ArrayService', 'ToastService',
	function ($scope, $localStorage, $mdDialog, $mdToast, UserService, ModalService, UnityService, ArrayService, ToastService) {
		$scope.localStorage = $localStorage;
		$scope.adm = { isAdm: $scope.localStorage.currentUser.adm };
		//Adiciona usuÃ¡rio
		$scope.addUser = function (data) {
			UserService.postUser(data.user, function (response) {
				ToastService.alert('UsuÃ¡rio adicionado com sucesso!', undefined, 'bottom left', 3000);
				$scope.users = ArrayService.add($scope.users, response.data);
			}),
				function (error) {

				};
		};

		//Busca usuÃ¡rios
		$scope.getUsers = function () {
			UserService.getList(function (response) {
				$scope.users = response.data;
			});

		};

		//Deleta usuÃ¡rios
		$scope.deleteUser = function (index, id) {
			ModalService.confirm('Deseja excluir o usuÃ¡rio?').then(function () {
				UserService.deleteUser({ id: id }, function (response) {
					ToastService.alert('Usuário deletado com sucesso!', undefined, 'bottom left', 3000);
					$scope.users = ArrayService.remove(index, $scope.users);
				});
			});
		};

		//Edita usuÃ¡rio
		$scope.editUser = function (data) {
			UserService.putUser(data.user, function (response) {
				ToastService.alert('Usuário editado com sucesso!', undefined, 'bottom left', 3000);
				$scope.users = ArrayService.edit(data.index, data.user, $scope.users);
			}),
				function (error) {

				};
		};

		//Modal usuÃ¡rios
		$scope.userModal = function (index, user) {

			$scope.adm = { isAdm: $scope.localStorage.currentUser.adm };

			if ($scope.units == null || $scope.units == undefined) {
				UnityService.getList(function (response) {
					$scope.units = response.data;
					openModal(index, user);
				});
			} else {
				openModal(index, user);
			};

			function openModal(index, user) {
				var listUnits = angular.copy($scope.units);
				if (user != null || user != undefined) {
					if (listUnits && user.units)
						for (var i = 0; i < listUnits.length; i++) {
							for (var c = 0; c < user.units.length; c++) {
								if (listUnits[i].id == user.units[c].id) {
									listUnits = ArrayService.remove(i, listUnits);
								};
							};
						};
				};

				var data = {
					index: index,
					user: user,
					units: listUnits
				};

				ModalService.tpl('userModal.html', data, function ($scope, $mdDialog, data, ArrayService) {

					$scope.units = angular.copy(data.units);

					$scope.title = "";

					$scope.user = {
						name: null,
						phone: null,
						email: null,
						password: null,
						status: false,
						adm: false
					};

					$scope.adm = { isAdm: $localStorage.currentUser.adm };

					if (data.user != null || data.user != undefined) {

						$scope.title = "Editar " + data.user.name;

						$scope.index = data.index;

						$scope.user = angular.copy(data.user);

						$scope.CompareEmail = $scope.user.email;

						$scope.user.password = null;

					} else {
						$scope.title = "Novo Usuário";
					};

					$scope.addUnityToUser = function (unit) {

						for (var i = 0; i < $scope.units.length; i++) {
							if ($scope.units[i].id == unit.id) {
								$scope.units = ArrayService.remove(i, $scope.units);
								break;
							};
						};
						$scope.user.units = ArrayService.add($scope.user.units, unit);
					};

					$scope.deleteUnity = function (index, unit) {
						$scope.user.units = ArrayService.remove(index, $scope.user.units);
						$scope.units = ArrayService.add($scope.units, unit);
					};

					$scope.validPassword = function () {
						console.log($scope.index);
						if ($scope.index == null || $scope.index == undefined) {
							console.log($scope.user.password);
							if (!$scope.user.password) {
								return true;
							} else {
								return false;
							}
						};
						return false;
					};

					$scope.validPasswordMessage = function () {
						if ($scope.validPassword()) {
							$scope.showError = true;
						} else {
							$scope.showError = false;
						}
					};

					$scope.cancel = function () {
						$mdDialog.cancel();
					};

					$scope.save = function () {

						var data = {
							user: $scope.user,
							index: $scope.index
						};

						$mdDialog.hide(data);
					};

				}).then(function (data) {
					console.log($scope.adm.isAdm);
					console.log("birl 4");
					if (data.index != null || data.index != undefined) {
						$scope.editUser(data);
					} else {
						$scope.addUser(data);
					};
				},
					function (error) {

					});
			};
		};
		//Carga inicial
		$scope.getUsers();
	}]);
/* ----------------------------------------------------------------------------------
* ----------------------------History of requisitions------------------------------
-------------------------------------------------------------------------------------*/
project.controller('HistoricCtrl', ['$scope', 'RequestService', '$mdDialog', 'ToastService', 'StockService',
	function ($scope, RequestService, $mdDialog, ToastService, StockService) {

		$scope.showFilterForm = false;
		$scope.selectForm = function () {
			$scope.showFilterForm = !$scope.showFilterForm;
		};

		$scope.imprimir = function () {

			var htmltable = document.getElementById('content').innerHTML;
			var html = htmltable.outerHTML;
			window.open('data:application/vnd.ms-excel,' + encodeURIComponent(htmltable));
		};

		$scope.search = function (filter) {

			var momentDateStart = moment(filter.start).format('YYYY-MM-DDT00:00:00');
			momentDateStart = momentDateStart + 'Z';

			var momentDateEnd = moment(filter.end).format('YYYY-MM-DDT00:00:00');
			momentDateEnd = momentDateEnd + 'Z';

			filter.start = momentDateStart;
			filter.end = momentDateEnd;

			StockService.listHistoric(filter, function (res) {
				$scope.listFinished = res.data;
			}, function () {
				console.error("Erro ao buscar dados!");
			});
		};

		$scope.openModal = function (ev) {

			$mdDialog.show({
				controller: periodModalCtrl,
				templateUrl: 'periodModal.html',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true
			}).then(function (filter) {
				$scope.search(filter);
			}, function () {
			});

			function periodModalCtrl($scope, $mdDialog) {
				$scope.modal = {};

				$scope.modal.filtrar = function () {
					$mdDialog.hide($scope.filter);
				};

				$scope.modal.cancel = function () {
					$mdDialog.cancel();
				};
			};
		};


	}]);


/* -----------------------------------------------------------------------------------
* ------------------------------Controller Request Approved---------------------------
* -----------------------------------------------------------------------------------*/

//project.controller('RequestApprovedCtrl', ['$scope', 'RequestService', 'ModalService', 'ToastService',
//function($scope, RequestService, ModalService, ToastService){
//
//	$scope.getAllRequestApproved = function(){
//		$scope.listRequest = [];
//		RequestService.listRequestByStatus({id: 20},function(res){
//			$scope.listRequest = res.data;
//		});
//	};
//
//	$scope.confirm = function(id){
//		ModalService.confirm('Deseja movimentar esta requisiÃ§Ã£o?').then(function(){
//			RequestService.moveStatus({id: id}, function(res){
//				ToastService.alert(res.message);
//				$scope.getAllRequestApproved();
//			});
//		});
//	};
//
//	$scope.getAllRequestApproved();
//}]);

/* -----------------------------------------------------------------------------------
* ------------------------------Controller Request Pending---------------------------
* -----------------------------------------------------------------------------------*/

project.controller('RequestPendingCtrl', ['$scope', '$mdDialog', 'RequestService', 'ModalService', 'ToastService', 'ArrayService',
	function ($scope, $mdDialog, RequestService, ModalService, ToastService, ArrayService) {

		$scope.listRequest = [];
		RequestService.listRequestByStatus({ id: 10 }, function (res) {
			$scope.listRequest = res.data;
		});

		$scope.confirmReject = function (req, index) {

			ModalService.confirmInput('Deseja Reprovar essa requisiÃ§Ã£o?', null, 'Motivo').then(function (reason) {
				var request = {};
				request.id = req.id;
				request.reason = reason;

				RequestService.disapproved(request, function (res) {
					ToastService.alert(res.message);
					$scope.listRequest = ArrayService.remove(index, $scope.listRequest);
				});
			});
		};

		$scope.viewStock = function (id) {
			RequestService.getRequest({ id: id }, function (res) {
				var request = res.data;

				$mdDialog.show({
					controller: InfoCtrl,
					controllerAs: 'ctrl',
					templateUrl: 'requestViewModal.html',
					parent: angular.element(document.body),
					data: request,
					clickOutsideToClose: true
				});

				function InfoCtrl($mdDialog, data) {
					var self = this;

					self.listStock = data.listInfo;

					self.total = 0;

					for (i in self.listStock) {
						self.total = self.total + self.listStock[i].quantity;
						self.totalValue = self.totalValue + (self.listStock[i].stock.unitaryValue * self.listStock[i].quantity);
					}

					self.cancel = function ($event) {
						$mdDialog.cancel();
					};
				}
			});
		};

		$scope.confirmRequest = function (req, index) {
			//ModalService.confirmInput('Aprova a requisiÃ§Ã£o ao livro: ' + req.stock.title + '?', null, 'Quantidade', req.quantity).then(function (quantity) {
			//				var total = 0;
			//				for(i in req.listInfo){
			//					total = total + req.listInfo[i].quantity;
			//				}
			//				req.quantity = total;

			RequestService.approved(req, function (res) {
				ToastService.alert(res.message);
				$scope.listRequest = ArrayService.remove(index, $scope.listRequest);
			});
			//});
		};
	}]);

/* -----------------------------------------------------------------------------------
* ------------------------------Controller Request-----------------------------------
* -----------------------------------------------------------------------------------*/
project.controller('RequestAllCtrl', ['$scope', 'ModalService', '$mdDialog', 'RequestService', 'ToastService', 'ArrayService', '$localStorage',
	function ($scope, ModalService, $mdDialog, RequestService, ToastService, ArrayService, $localStorage) {

		$scope.emailUser = $localStorage.currentUser.email;

		$scope.getAllRequest = function () {
			$scope.listRequest = [];
			RequestService.listRequestAll(function (res) {
				$scope.listRequest = res.data;
			});
		};

		$scope.viewStock = function (id) {
			RequestService.getRequest({ id: id }, function (res) {
				var request = res.data;

				$mdDialog.show({
					controller: InfoCtrl,
					controllerAs: 'ctrl',
					templateUrl: 'requestViewModal.html',
					parent: angular.element(document.body),
					data: request,
					clickOutsideToClose: true
				});

				function InfoCtrl($mdDialog, data) {
					var self = this;

					self.listStock = data.listInfo;

					self.total = 0;

					for (i in self.listStock) {
						self.total = self.total + self.listStock[i].quantity;
						self.totalValue = self.totalValue + (self.listStock[i].stock.unitaryValue * self.listStock[i].quantity);
					}

					self.cancel = function ($event) {
						$mdDialog.cancel();
					};
				}
			});
		};

		$scope.remove = function (data, index) {
			ModalService.confirm('Deseja deletar estÃ¡ requisiÃ§Ã£o?').then(function () {
				RequestService.deleteRequest({ id: data.id }, function (response) {
					$scope.listRequest = ArrayService.remove(index, $scope.listRequest);
					ToastService.alert(response.message);
				}),
					function (error) {

					};
			});
		};

		$scope.confirm = function (id) {
			ModalService.confirm('Deseja movimentar esta requisiÃ§Ã£o?').then(function () {
				RequestService.moveStatus({ id: id }, function (res) {
					ToastService.alert(res.message);
					$scope.getAllRequest();
				});
			});
		};

		$scope.getAllRequest();
	}])

project.controller('RequestSendingCtrl', ['$scope', 'ModalService', '$mdDialog', 'RequestService', 'ToastService', 'ArrayService',
	function ($scope, ModalService, $mdDialog, RequestService, ToastService, ArrayService) {
		$scope.atualizar = function () {
			$scope.listRequest = [];
			RequestService.listRequestByStatus({ id: 40 }, function (res) {
				console.log(res.data);
				$scope.listRequest = res.data;
			});
		};

		$scope.viewStock = function (id) {
			RequestService.getRequest({ id: id }, function (res) {
				var request = res.data;

				$mdDialog.show({
					controller: InfoCtrl,
					controllerAs: 'ctrl',
					templateUrl: 'requestViewModal.html',
					parent: angular.element(document.body),
					data: request,
					clickOutsideToClose: true
				});

				function InfoCtrl($mdDialog, data) {
					var self = this;

					self.listStock = data.listInfo;

					self.total = 0;

					for (i in self.listStock) {
						self.total = self.total + self.listStock[i].quantity;
						self.totalValue = self.totalValue + (self.listStock[i].stock.unitaryValue * self.listStock[i].quantity);
					}

					self.cancel = function ($event) {
						$mdDialog.cancel();
					};
				}
			});
		};

		$scope.confirm = function (id) {
			ModalService.confirm('Deseja movimentar esta requisiÃ§Ã£o?').then(function () {
				RequestService.moveStatus({ id: id }, function (res) {
					ToastService.alert(res.message);
					$scope.atualizar();
				});
			});
		};

		$scope.atualizar();
	}])

/*project.controller('RequestPendingCtrl', ['$scope', 'ModalService', '$mdDialog', 'RequestService', 'ToastService', 'ArrayService',
function($scope, ModalService, $mdDialog, RequestService, ToastService, ArrayService){
$scope.atualizar = function(){
$scope.listRequest = [];
RequestService.listRequestByStatus({id: 10}, function(res){
$scope.listRequest = res.data;
});
};

$scope.confirm = function(id){
ModalService.confirm('Deseja movimentar esta requisiÃ§Ã£o?').then(function(){
RequestService.moveStatus({id: id}, function(res){
ToastService.alert(res.message);
$scope.atualizar();
});
});
};

$scope.atualizar();
}])*/
project.controller('RequestCtrl', ['$scope', 'ModalService', '$mdDialog', 'RequestService', 'ToastService', 'ArrayService', '$localStorage', 'UserService', 'SubCategoryService', 'CategoryService',
	function ($scope, ModalService, $mdDialog, RequestService, ToastService, ArrayService, $localStorage, UserService, SubCategoryService, CategoryService) {

		$scope.isOpen = false;

		$scope.emailUser = $localStorage.currentUser.email;

		$scope.listRequest = [];
		RequestService.getListByUser(function (res) {
			$scope.listRequest = res.data;
		});

		$scope.demo = {
			isOpen: false,
			count: 0,
			selectedDirection: 'left'
		};

		$scope.viewStock = function (id) {
			RequestService.getRequest({ id: id }, function (res) {
				var request = res.data;

				$mdDialog.show({
					controller: InfoCtrl,
					controllerAs: 'ctrl',
					templateUrl: 'requestViewModal.html',
					parent: angular.element(document.body),
					data: request,
					clickOutsideToClose: true
				});

				function InfoCtrl($mdDialog, data) {
					var self = this;

					self.listStock = data.listInfo;

					self.total = 0;

					for (i in self.listStock) {
						self.total = self.total + self.listStock[i].quantity;
						self.totalValue = self.totalValue + (self.listStock[i].stock.unitaryValue * self.listStock[i].quantity);
					}

					self.cancel = function ($event) {
						$mdDialog.cancel();
					};
				}
			});
		};

		$scope.confirm = function (id, index) {
			ModalService.confirm('Confirma o encerramento dessa requisiÃ§Ã£o?').then(function () {
				RequestService.moveStatus({ id: id }, function (res) {
					ToastService.alert(res.message);
					$scope.listRequest = ArrayService.remove(index, $scope.listRequest);
				});
			});
		};

		$scope.remove = function (request, index) {
			ModalService.confirm('Deseja excluir a requisiÃ§Ã£o?').then(function () {
				RequestService.deleteRequest({ id: request.id }, function (res) {
					ToastService.alert(res.message);
					$scope.listRequest.splice(index, 1);
				}, function (res) {
					ToastService.alert(res.message);
				});
			});
		};

		$scope.toRequest = function (ev, index) {
			$mdDialog.show({
				controller: DialogCtrl,
				controllerAs: 'ctrl',
				templateUrl: 'requestModal.html',
				data: "",
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true
			}).then(function (data) {
				//console.log(data);
				if (index) {
					$scope.listRequest.splice(index, 1, data);
				} else {
					var cont = 0;

					//for (cont in data) {
					if (($scope.listRequest != null) && ($scope.listRequest.length > 0)) {
						$scope.listRequest.push(data);
					} else {
						$scope.listRequest = [data];
					}
					//}
				}
			});

			function DialogCtrl($timeout, $q, $scope, $mdDialog, RequestService, ToastService, StockService, $http) {
				var self = this;

				$scope.listUnitsAvaible = [];
				($scope.getActualUser = function () {
					UserService.editActualUser(function (res) {
						$scope.listUnitsAvaible = res.data.units;
					});
				})();
				
				CategoryService.getList(function (response) {
					$scope.categorys = response.data;
				});

				$scope.requestsProduct = [];

				$scope.deleteProduct = function (index) {
					$scope.requestsProduct.splice(index, 1);
				};

				StockService.getList(function (res) {
					self.states = res.data;
					self.listStock = res.data;
				});
				self.listStock = [];
				self.querySearch = querySearch;
				//ANYELSE THISS
				self.listStock = [];
				
				self.cancel = function ($event) {
					$mdDialog.cancel();
				};
				
				self.filterRequestStock = function(query, category, subCategory){
					console.log('teste');
					/*self.listStock = [];
					self.states.forEach(function (data) {
						if(data.subCategory && data.subCategory.category){
							if(query || category || subCategory){
								if(query && category && subCategory){
									if(data.title == query && data.subCategory.category.name == category && data.subCategory.name == subCategory){
										self.listStock.push(data);
									}
								}else if(query && category){
									if(data.title == query && data.subCategory.category.name == category){
										self.listStock.push(data);
									}
								}else if(query){
									if(data.title == query){
										self.listStock.push(data);
									}
								}else if(category && subCategory ){
									if(data.subCategory.category.name == category && data.subCategory.name == subCategory){
										self.listStock.push(data);
									}
								}else if(category){
									if(data.subCategory.category.name == category){
										self.listStock.push(data);
									}
								}
							}else{
								self.listStock.push(data);
							}							
						}else{
							if(query != ""){
								if(data.title == query){
									self.listStock.push(data);
								}
							}else if(!subCategory && !category){
								self.listStock.push(data);
							}
						}
					})*/
				}
				
				$scope.getSubCategorys = function(data, query, category){
					SubCategoryService.filterCategory({ id: data.id }, function (response) {
						$scope.subCategorys = response.data;
					});

					self.filterRequestStock(query, category);
				}
				
				self.finish = function ($event, listRequest, scheduleDate, costCenter, project) {
					/*for (i in listRequest) {

						var momentDate = moment(listRequest[i].scheduleDate).format('YYYY-MM-DDT00:00:00');
						momentDate = momentDate + 'Z';
						listRequest[i].scheduleDate = momentDate;
					}*/
					var momentDate = moment(scheduleDate).format('YYYY-MM-DDT00:00:00');
					momentDate = momentDate + 'Z';
					scheduleDate = momentDate;

					RequestService.postRequest({ listInfo: listRequest, scheduleDate: scheduleDate, costCenter: costCenter, project: project }, function (res) {

						ToastService.alert(res.message);
						$mdDialog.hide(res.data);
					}, function (res) {
						ToastService.alert(res.data.message);
					});
				};

				self.add = function (data) {
					if (data != null || data != undefined) {
						$scope.requestsProduct.push({ stock: data });
					}
				};
				
				function querySearch(query, category, subcategory) {
					// TODO query ? self.states.filter(createFilterFor(query, category ? category : "", subCategory ? subCategory : "")) : self.states; 
					// TODO return self.states.filter(createFilterFor(query, category ? category : "", subCategory ? subCategory : ""));
					//return self.states.filter(createFilterFor(query, category ? category : "", subCategory ? subCategory : ""));
					
					var id_category = 0;
					if(category){
						id_category = category.id;
					}
					
					var id_subcategory = 0;
					if(subcategory){
						id_subcategory = subcategory.id;
					}
					
					return $http.get('../privateRest/stock/listStockByFilter?id_category='+ 
							id_category +'&id_subcategory='+ id_subcategory +'&search='+ query)
								.then(function(res){
						//return res.data.data;
						if(res.data.data){
							return res.data.data;
						}else{
							return false;
						}
					});
				};

				function loadAll() {

					return [{
						stock: { id: 1, name: "Livro" }
					}];
				};

				function createFilterFor(query, category, subCategory) {

					var lowercaseQuery = angular.lowercase(query);
					
					return function filterFn(state) {
						if(category != "" && subCategory != "" && query != ""){
							console.log("onde eu to 1 -> 1 ");
							return (state.title.indexOf(query) === 0 && state.subCategory.name.indexOf(subCategory) === 0 && state.subCategory.category.name.indexOf(category) === 0);
						}
						if(category != "" && query != ""){
							console.log("onde eu to 2 -> 2 ");
							return (state.title.indexOf(query) === 0 && state.subCategory.category.name.indexOf(category) === 0);
						}
						if(query != "" && query != undefined && query != null && query != false){
							console.log("onde eu to 3 -> 3 ");
							return (state.title.indexOf(query) === 0);							
						}
						if((category != "" && subCategory != "" && !query) || (category != "" && subCategory != "" && !query)){
							console.log("onde eu to 4 -> 4 ");
							//TODO return (state.subCategory.name.indexOf(subCategory) === 0 && state.subCategory.category.name.indexOf(category) === 0);
							//console.log(state.subCategory.name + " ONDE EU TO NO 4 HEIN");
							if((state.subCategory && state.subCategory.name == subCategory) && (state.subCategory && state.subCategory.category.name == category)){
								return (state);
							}
							if((state.subCategory && state.subCategory.category.name == category)){
								return (state);
							}
							//return (state.subCategory.name.indexOf(subCategory) === 0 && state.subCategory.category.name.indexOf(category) === 0);
						}
						
						if(query == "" || query == undefined || query == null || query == false){
							console.log("onde eu to 5 -> 5 ");
							return (state);
						}
					};

					self.cancel = function ($event) {
						$mdDialog.cancel();
					};
					self.finish = function ($event, obj) {
						$mdDialog.hide();
					};

					self.add = function (data) {
						if (data != null || data != undefined) {
							$scope.requestsProduct.push(data);
						}
					};
				}

			};

		}
	}]);


/* -----------------------------------------------------------------------------------
* ------------------------------Controller Dashboard-----------------------------------
* -----------------------------------------------------------------------------------*/

project.controller('DashboardCtrl', ['$scope', 'ToastService', '$mdToast', 'RequestService', 'StockService', 'ModalService', 'ArrayService',
	function ($scope, ToastService, $mdToast, RequestService, StockService, ModalService, ArrayService) {
		$scope.getRandomColor = function () {
			var letters = '0123456789ABCDEF';
			var color = '#';
			for (var i = 0; i < 6; i++) {
				color += letters[Math.floor(Math.random() * 16)];
			}
			return color;
		}
		$scope.opcoes = [1, 2, 3];
		$scope.opcaoPercent = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

		$scope.listRequest = [];
		RequestService.requestPending(function (res) {
			$scope.listRequest = res.data;
		});

		$scope.stockLow = [];
		StockService.listStockLow(function (res) {
			$scope.stockLow = res.data;
		});

		$scope.confirmRequest = function (req, index) {
			//ModalService.confirmInput('Aprova a requisiÃ§Ã£o ao livro: ' + req.stock.title + '?', null, 'Quantidade', req.quantity).then(function (quantity) {
			//req.quantity = quantity;

			RequestService.approved(req, function (res) {
				ToastService.alert(res.message);
				$scope.listRequest = ArrayService.remove(index, $scope.listRequest);
			});
			//});
		};


		$scope.loadChart = function () {
			StockService.listStockLow(function (res) {
				$scope.stockLow = res.data;
				var arrValue = [];
				var arrName = [];
				for (i = 0; i < res.data.length; i++) {
					arrValue[i] = res.data[i].quantity;
					arrName[i] = res.data[i].title;
				}
				var ctx = document.getElementById("myChart2");
				var myChart = new Chart(ctx, {
					type: 'bar',
					data: {
						labels: arrName,
						datasets: [{
							label: 'RequisiÃ§Ãµes com Estoque Baixo',
							data: arrValue,
							backgroundColor: [
								'rgba(255, 99, 132, 0.2)',
								'rgba(54, 162, 235, 0.2)',
								'rgba(255, 206, 86, 0.2)',
								'rgba(75, 192, 192, 0.2)',
								'rgba(153, 102, 255, 0.2)',
								'rgba(255, 159, 64, 0.2)',
								'rgba(255, 99, 132, 0.2)',
								'rgba(54, 162, 235, 0.2)',
								'rgba(255, 206, 86, 0.2)',
								'rgba(75, 192, 192, 0.2)',
								'rgba(153, 102, 255, 0.2)',
								'rgba(255, 159, 64, 0.2)',
								'rgba(255, 99, 132, 0.2)',
								'rgba(54, 162, 235, 0.2)',
								'rgba(255, 206, 86, 0.2)',
								'rgba(75, 192, 192, 0.2)',
								'rgba(153, 102, 255, 0.2)',
								'rgba(255, 159, 64, 0.2)'
							],
							borderColor: [
								'rgba(255,99,132,1)',
								'rgba(54, 162, 235, 1)',
								'rgba(255, 206, 86, 1)',
								'rgba(75, 192, 192, 1)',
								'rgba(153, 102, 255, 1)',
								'rgba(255, 159, 64, 1)',
								'rgba(255,99,132,1)',
								'rgba(54, 162, 235, 1)',
								'rgba(255, 206, 86, 1)',
								'rgba(75, 192, 192, 1)',
								'rgba(153, 102, 255, 1)',
								'rgba(255, 159, 64, 1)',
								'rgba(255,99,132,1)',
								'rgba(54, 162, 235, 1)',
								'rgba(255, 206, 86, 1)',
								'rgba(75, 192, 192, 1)',
								'rgba(153, 102, 255, 1)',
								'rgba(255, 159, 64, 1)'
							],
							borderWidth: 1
						}]
					},
					options: {
						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true
								}
							}]
						}
					}
				});
			});

			StockService.listHistoric(function (res) {
				var data = res.data,
					arrValue = [],
					arrName = [],
					arrColor = [];
				var newData = [];
				if (data.length >= 5) {
					newData[0] = data[data.length - 1];
					newData[1] = data[data.length - 2];
					newData[2] = data[data.length - 3];
					newData[3] = data[data.length - 4];
					newData[4] = data[data.length - 5];
					data = newData;
				}

				if (data) {
					for (i = 0; i < data.length; i++) {
						arrName[i] = data[i].stock;
						arrValue[i] = data[i].quantity;
						arrColor[i] = $scope.getRandomColor();
					}
				}

				var p = 0;
				var ctx = document.getElementById("myChart1");
				var data = {
					labels: arrName,
					datasets: [{
						data: arrValue,
						label: "Ultimos pedidos",
						backgroundColor: arrColor,
						hoverBackgroundColor: arrColor
					}]
				};

				var myDoughnutChart = new Chart(ctx, {
					type: 'doughnut',
					data: data
				})

			})
			RequestService.requestPending(function (res) {
				$scope.listRequest = res.data;

			});
		}
		$scope.loadChart();
	}]);

/*
 * -----------------------------------------------------------------------------------
 * ------------------------------Controller  Stock-----------------------------------
 * -----------------------------------------------------------------------------------
 */

project.controller('StockCtrl', ['$scope', 'StockService', 'ModalService', 'ToastService', 'ArrayService', 'CategoryService', 'SubCategoryService',
	function ($scope, StockService, ModalService, ToastService, ArrayService, CategoryService, SubCategoryService) {

		// Variavel que armazena a listagem dos estoques.
		$scope.listStock = [];
		$scope.listar = function () {
			StockService.listTotal(function (res) {
				$scope.listStock = res.data;
			});
		};

		($scope.getCategorys = function () {
			CategoryService.getList(function (response) {
				$scope.category = response.data;
			});
		})();

		$scope.getSubCategory = function (name) {
			$scope.category.forEach(function (data) {
				if (data.name === name) {
					SubCategoryService.filterCategory({ id: data.id }, function (response) {
						console.log(data.id);
						$scope.subCategory = response.data;
					});
				}
			});
		};

		$scope.listar();

		$scope.show = function (id, index) {
			ModalService.tpl('localModal.html', { id: id }, ['$mdDialog', '$scope', 'LocationService', 'ArrayService', 'CategoryService', 'StockService', 'ToastService',
				function ($mdDialog, $scope, ContainerService, ArrayService, CategoryService, StockService, ToastService) {
					$scope.itemPlace = [];
					StockService.getStock({ id: id }, function (res) {
						$scope.itemPlace = res.data;
						console.log(res.data);
					})
					$scope.cancelModal = function () {
						$mdDialog.hide();
					};
				}])
		}
		$scope.removeStock = function (id, index) {
			ModalService.confirm("Deseja remover este estoque?").then(function () {
				StockService.deleteStock({ id: id }, function (res) {
					ToastService.alert(res.message);
					$scope.listStock = ArrayService.remove(index, $scope.listStock);
				}, function (res) {
					ToastService.alert(res.message);
				});
			});
		};

		$scope.showEdit = function (stock, index) {

			var id = 0;
			if (stock) {
				id = stock.id;
			}

			ModalService.tpl('openModal.html', { id: id }, ['$mdDialog', '$scope', /*'ContainerService',*/'LocationService', 'ArrayService', 'CategoryService', 'StockService', 'ToastService', 'data', 'SubCategoryService',
				function ($mdDialog, $scope, ContainerService, ArrayService, CategoryService, StockService, ToastService, data, SubCategoryService) {
					$scope.listLocation = [];

					if (data.id) {
						$scope.stock = {};//angular.copy(data.stock);
						$scope.listLocation = [];//angular.copy(data.stock.listLocation);
						StockService.getStock({ id: data.id }, function (res) {
							$scope.stock = res.data;
							$scope.listLocation = res.data.listLocation;

							$scope.stock.cpa = parseInt($scope.stock.cpa);

							$scope.getSubCategory($scope.stock.subCategory.category.id);
						});
					}

					$scope.addLocation = function () {
						$scope.listLocation = ArrayService.add($scope.listLocation, { quantity: null, position: "", container: { id: null } });
					};

					$scope.removeLocation = function (index) {
						$scope.listLocation = ArrayService.remove(index, $scope.listLocation);
					};

					$scope.listCategory = [];
					CategoryService.getList(function (res) {
						$scope.listCategory = res.data;
					});

					$scope.listSubCategory = [];

					$scope.getSubCategory = function (id) {
						SubCategoryService.filterCategory({ id: id }, function (res) {
							$scope.listSubCategory = res.data;
						});
					};

					/*if (data.id) {
						$scope.getSubCategory($scope.stock.subCategory.category.id);
					}*/

					$scope.listContainer = [];
					ContainerService.getList(function (res) {
						$scope.listContainer = res.data;
					});

					if ((!$scope.listLocation) || ($scope.listLocation.length <= 0)) {
						$scope.addLocation();
					}

					$scope.cancel = function () {
						$mdDialog.hide();
					};
					$scope.save = function (stock, listLocation) {
						if (stock.id) {
							StockService.putStock(stock, function (res) {
								ToastService.alert(res.message);
								var stockEdit = res.data;
								stockEdit.index = index;
								$mdDialog.hide(stockEdit);
							}, function (res) {
								ToastService.alert(res.data.message);
							});

						} else {

							StockService.postStock(stock, function (res) {
								ToastService.alert(res.message);
								$mdDialog.hide(res.data);
							}, function (res) {
								ToastService.alert(res.data.message);
							});
						}
						stock.listLocation = listLocation;
					};
				}]).then(function (data) {
					$scope.listar();
				});
		};
	}]);

/* -----------------------------------------------------------------------------------
* ------------------------------Controller Categorys-----------------------------------
* -----------------------------------------------------------------------------------*/

project.controller('CategoryCtrl', ['$scope', '$mdDialog', 'CategoryService', 'SubCategoryService', 'ModalService', 'ToastService', 'ArrayService',
	function ($scope, $mdDialog, CategoryService, SubCategoryService, ModalService, ToastService, ArrayService) {

		$scope.showButton = true;
		//Busca categorias
		$scope.getCategorys = function () {
			CategoryService.getList(function (response) {
				$scope.categorys = response.data;
			});
		};

		//Adiciona categoria
		$scope.addCategory = function (data) {
			CategoryService.postCategory(data.category, function (response) {
				ToastService.alert('Categoria adicionada com sucesso!', undefined, 'bottom left', 3000);
				$scope.categorys = ArrayService.add($scope.categorys, response.data);
			}),
				function (error) {

				};
		};

		//Edita categoria
		$scope.editCategory = function (data) {
			CategoryService.putCategory(data.category, function (response) {
				ToastService.alert('Categoria editada com sucesso!', undefined, 'bottom left', 3000);
				$scope.categorys = ArrayService.edit(data.index, data.category, $scope.categorys);
			}),
				function (error) {

				};
		};

		//Deleta categoria
		$scope.deleteCategory = function (index, id) {
			ModalService.confirm('Deseja excluir a Categoria').then(function () {
				CategoryService.deleteCategory({ 'id': id }, function (response) {
					ToastService.alert('Categoria deletada com sucesso!', undefined, 'bottom left', 3000);
					$scope.categorys = ArrayService.remove(index, $scope.categorys);
				}, function (res) {
					ToastService.alert(res.data.message);
				});
			})
		};

		//Busca sub-categorias
		$scope.getSubCategorys = function () {
			SubCategoryService.getList(function (response) {
				$scope.subCategorys = response.data;
			});
		};

		//Adiciona sub-categoria
		$scope.addSubCategory = function (data) {
			SubCategoryService.postSubCategory(data.subCategory, function (response) {
				ToastService.alert('Sub-Categoria adicionada com sucesso!', undefined, 'bottom left', 3000);
				$scope.subCategorys = ArrayService.add($scope.subCategorys, response.data);
			}),
				function (error) {

				};
		};

		//Edita sub-categoria
		$scope.editSubCategory = function (data) {
			SubCategoryService.putSubCategory(data.subCategory, function (response) {
				ToastService.alert('Sub-Categoria editada com sucesso!', undefined, 'bottom left', 3000);
				$scope.subCategorys = ArrayService.edit(data.index, data.subCategory, $scope.subCategorys);
			}),
				function (error) {

				};
		};
		//Deleta sub-categorias
		$scope.deleteSubCategory = function (index, id) {
			ModalService.confirm('Deseja excluir a SubCategoria').then(function () {
				SubCategoryService.deleteSubCategory({ 'id': id }, function (response) {
					ToastService.alert('Sub-Categoria deletada com sucesso!', undefined, 'bottom left', 3000);
					$scope.subCategorys = ArrayService.remove(index, $scope.subCategorys);
				}, function (res) {
					ToastService.alert(res.data.message);
				});
			})
		};

		//Exibe modal categoria
		$scope.showModalCategory = function (index, category) {

			var data = {
				category: category,
				index: index
			};

			ModalService.tpl('category.html', data, function ($scope, $mdDialog, data) {
				$scope.title = '';
				if (data.category != null || data.category != undefined) {
					$scope.category = angular.copy(data.category);
					$scope.index = data.index;
					$scope.title = 'Editar Categoria - ' + data.category.name;
				} else {
					$scope.title = 'Nova Categoria';
				};

				$scope.save = function (category) {

					var data = {
						category: category,
						index: $scope.index
					};

					$mdDialog.hide(data);
				};

				$scope.cancel = function () {
					$mdDialog.cancel();
				};

			}).then(function (data) {

				if (data.index === null || data.index === undefined) {
					$scope.addCategory(data);
				} else {
					$scope.editCategory(data);
				};

			},
				function (error) {

				});
		};

		//Exibe modal de sub-categoria
		$scope.showModalSubCategory = function (categorys, subCategory, index) {

			var data = {
				categorys: categorys,
				subCategory: subCategory,
				index: index
			};

			ModalService.tpl('subCategory.html', data, function ($scope, $mdDialog, data) {
				$scope.title = "";
				$scope.categorys = data.categorys;

				if (data.subCategory != null || data.subCategory != undefined) {
					$scope.subCategory = angular.copy(data.subCategory);
					$scope.index = data.index;
					$scope.title = 'Editar Sub-Categoria - ' + data.subCategory.name;
				} else {
					$scope.title = 'Nova Sub-Categoria'
				};

				$scope.save = function (subCategory) {

					var data = {
						subCategory: subCategory,
						index: $scope.index
					};

					$mdDialog.hide(data);

				};

				$scope.cancel = function () {
					$mdDialog.cancel();
				};

			}).then(function (data) {

				if (data.index === null || data.index === undefined) {
					$scope.addSubCategory(data);
				} else {
					$scope.editSubCategory(data);
				}
			},
				function (error) {

				});
		};

		$scope.getCategorys();
		$scope.getSubCategorys();
	}]);

project.controller('UnityCtrl', ['$scope', '$filter', '$mdDialog', 'UnityService', 'CityService', 'StateService', 'ModalService', 'ToastService', 'ArrayService',
	function ($scope, $filter, $mdDialog, UnityService, CityService, StateService, ModalService, ToastService, ArrayService) {

		//Busca unidades
		$scope.getUnits = function () {
			UnityService.getList(function (response) {
				$scope.units = response.data;
			});
		};

		//Adiciona unidade
		$scope.addUnity = function (data) {
			UnityService.postUnity(data.unity, function (response) {
				ToastService.alert('Unidade adicionada com sucesso!', undefined, 'bottom left', 3000);
				$scope.units = ArrayService.add($scope.units, response.data);
			}),
				function (error) {

				};
		};

		//Edita unidade
		$scope.editUnity = function (data) {
			UnityService.putUnity(data.unity, function (response) {
				ToastService.alert('Unidade editada com sucesso!', undefined, 'bottom left', 3000);
				$scope.units = ArrayService.edit(data.index, data.unity, $scope.units);
			}),
				function (error) {

				};
		};

		//Deleta unidade
		$scope.deleteUnity = function (index, id) {
			ModalService.confirm('Deseja excluir a Unidade').then(function () {
				UnityService.deleteUnity({ 'id': id }, function (response) {
					ToastService.alert('Unidade deletada com sucesso!', undefined, 'bottom left', 3000);
					$scope.units = ArrayService.remove(index, $scope.units);
				});
			})
		};

		//Busca cidades
		$scope.getCity = function () {
			CityService.getList(function (response) {
				$scope.cities = response.data;
			});
		};

		//Adiciona cidade
		$scope.addCity = function (data) {
			CityService.postCity(data.city, function (response) {
				ToastService.alert('Cidade adicionada com sucesso!', undefined, 'bottom left', 3000);
				$scope.cities = ArrayService.add($scope.cities, response.data);
			}),
				function (error) {

				};
		};

		//Edita cidade
		$scope.editCity = function (data) {
			CityService.putCity(data.city, function (response) {
				ToastService.alert('Cidade editada com sucesso!', undefined, 'bottom left', 3000);
				$scope.cities = ArrayService.edit(data.index, data.city, $scope.cities);
			}),
				function (error) {

				};
		};

		//Deleta cidade
		$scope.deleteCity = function (index, id) {
			ModalService.confirm('Deseja excluir a Cidade').then(function () {
				CityService.deleteCity({ 'id': id }, function (response) {
					ToastService.alert('Cidade deletada com sucesso!', undefined, 'bottom left', 3000);
					$scope.cities = ArrayService.remove(index, $scope.cities);
				});
			})
		};


		$scope.showModalUnity = function (cities, unity, index) {
			var data = {
				unity: unity,
				cities: cities,
				index: index
			};

			ModalService.tpl('unityModal.html', data, function ($scope, $mdDialog, data) {
				$scope.title = "";

				$scope.cities = data.cities;

				if (data.unity != null || data.unity != undefined) {
					$scope.unity = angular.copy(data.unity);
					$scope.index = data.index;
					$scope.title = 'Editar Unidade - ' + data.unity.name;
				} else {
					$scope.title = 'Nova Unidade'
				};

				$scope.save = function (unity) {

					var data = {
						unity: unity,
						index: $scope.index
					};

					$mdDialog.hide(data);

				};

				$scope.cancel = function () {
					$mdDialog.cancel();
				};

			}).then(function (data) {

				if (data.index === null || data.index === undefined) {
					$scope.addUnity(data);
				} else {
					$scope.editUnity(data);
				}
			},
				function (error) {

				});
		};

		$scope.showModalCity = function (city, index) {

			//Busca estados
			if ($scope.states === null || $scope.states === undefined) {
				StateService.getList(function (response) {
					$scope.states = response.data;
					openModal(city, index);
				}),
					function (error) {

					};
			} else {
				openModal(city, index);
			};

			//Abre modal cidade
			function openModal(city, index) {

				var data = {
					city: city,
					states: $scope.states,
					index: index
				};

				ModalService.tpl('cityModal.html', data, function ($scope, $mdDialog, data, StateService) {
					$scope.title = "";

					$scope.states = data.states;

					if (data.city != null || data.city != undefined) {
						$scope.city = angular.copy(data.city);
						$scope.index = data.index;
						$scope.title = 'Editar Cidade - ' + data.city.name;
					} else {
						$scope.title = 'Nova Cidade';
					};

					$scope.save = function (city) {

						city.initials = $filter('uppercase')(city.initials);

						var data = {
							city: city,
							index: $scope.index
						};

						$mdDialog.hide(data);

					};

					$scope.cancel = function () {
						$mdDialog.cancel();
					};

				}).then(function (data) {

					if (data.index === null || data.index === undefined) {
						$scope.addCity(data);
					} else {
						$scope.editCity(data);
					}
				},
					function (error) {

					});
			}
		};

		$scope.getCity();
		$scope.getUnits();

	}]);


//CTRL LOCATION
project.controller('LocationCtrl', ['$scope', '$filter', 'LocationService', '$mdDialog', 'ModalService', 'ToastService', 'ArrayService',
	function ($scope, $filter, LocationService, $mdDialog, ModalService, ToastService, ArrayService) {
		$scope.addLocation = function (data) {
			LocationService.postLocation(data.location, function (response) {
				ToastService.alert('LocalizaÃ§Ã£o adicionada com sucesso!', undefined, 'bottom left', 3000);
				$scope.locations = ArrayService.add($scope.locations, response.data);
			}),
				function (error) { };
		};
		$scope.editLocation = function (data) {
			LocationService.putLocation(data.location, function (response) {
				ToastService.alert('LocalizaÃ§Ã£o editada com sucesso!', undefined, 'bottom left', 3000);
				$scope.locations = ArrayService.edit(data.index, data.location, $scope.locations);
			}),
				function (error) { };
		};
		$scope.deleteLocation = function (index, id) {
			ModalService.confirm('Deseja excluir a LocalizaÃ§Ã£o?').then(function () {
				LocationService.deleteLocation({ 'id': id }, function (response) {
					ToastService.alert('LocalizaÃ§Ã£o deltada com sucesso!', undefined, 'bottom left', 3000);
					$scope.locations = ArrayService.remove(index, $scope.locations);
				})
			}), function (error) { };
		};
		$scope.getLocations = function () {
			LocationService.getList(function (response) {
				$scope.locations = response.data;
			});
		};
		$scope.showModalLocation = function (location, index) {
			var data = {
				location: location,
				index: index
			};
			ModalService.tpl('locationModal.html', data, function ($scope, $mdDialog, data) {
				$scope.title = "";
				$scope.locations = data.location;
				if (data.location != null || data.location != undefined) {
					$scope.location = angular.copy(data.location);
					$scope.index = data.index;
					$scope.title = 'Editar Unidade - ' + data.location.name;
				} else {
					$scope.title = 'Nova Unidade'
				};
				$scope.save = function (location) {
					var data = {
						location: location,
						index: $scope.index
					};
					$mdDialog.hide(data);
				};
				$scope.cancel = function () {
					$mdDialog.cancel();
				};
			}).then(function (data) {
				if (data.index === null || data.index === undefined) {
					$scope.addLocation(data);
				} else {
					$scope.editLocation(data);
				}
			}, function (error) { });
		};
		$scope.getLocations();
	}]);
project.controller('monthTotalCtrl', ['$scope', 'StockService', 'ModalService', 'ToastService', 'ArrayService', '$mdDialog',
	function ($scope, StockService, ModalService, ToastService, ArrayService, $mdDialog) {
		$scope.search = function (filter) {

			if (!filter) filter = {};
			if (!filter.start) {
				var d = new Date();
				d.setDate(01);
				filter.start = d;
			}

			if (!filter.end) {
				filter.end = new Date();
			}

			var momentDateStart = moment(filter.start).format('YYYY-MM-DDT00:00:00');
			momentDateStart = momentDateStart + 'Z';

			var momentDateEnd = moment(filter.end).format('YYYY-MM-DDT00:00:00');
			momentDateEnd = momentDateEnd + 'Z';

			filter.start = momentDateStart;
			filter.end = momentDateEnd;
			$scope.totalMonth = 0;
			StockService.listHistoric(filter, function (res) {
				$scope.listFinished = res.data;
				var totalMonth = 0;
				if (res.data) {
					for (var i = 0; i < res.data.length; i++) {
						totalMonth += res.data[i].unitaryValue;
					}
					$scope.totalMonth = totalMonth;
				}
			}, function () {
				console.error("Erro ao buscar dados!");
			});
		};
		$scope.imprimir = function () {
			if($scope.listFinished && $scope.listFinished.length > 0){
				for (var i = 0; i < $scope.listFinished.length; i++) {
					$scope.listFinished[i].showInput = false;
					$scope.listFinished[i].showInput2 = false;
				}
			}
			
			var time = setInterval(function(){
				var htmltable = document.getElementById('content').innerHTML;
				var html = htmltable.outerHTML;
				window.open('data:application/vnd.ms-excel,' + encodeURIComponent(htmltable));
				
				clearInterval(time);
			}, 2);
		};
		
		$scope.changeAccount = function(ev){
			$mdDialog.show({
				controller: contaModalCtrl,
				templateUrl: 'alteraContabil.html',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true
			}).then(function(param){
				if($scope.listFinished && $scope.listFinished.length > 0){
					for (var i = 0; i < $scope.listFinished.length; i++) {
						$scope.listFinished[i].cont = param.account;
						$scope.listFinished[i].cont2 = param.account2;
					}
				}
			});
			
			function contaModalCtrl($scope, $mdDialog) {
				$scope.change = function(param){
					$mdDialog.hide(param);
				};
				
				$scope.cancel = function(){
					$mdDialog.cancel();
				};
			}
		};
		
		$scope.valueTotalStock = 0;
		$scope.totalValue = function () {
			StockService.totalHistoric(function (res) {
				$scope.valueTotalStock = res.data;
			}, function () {
				console.error("Erro ao buscar dados!");
			});
		}

		$scope.openModal = function (ev) {

			$mdDialog.show({
				controller: periodModalCtrl,
				templateUrl: 'periodModal.html',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true
			}).then(function (res) {
				//$scope.search(filter);
				//console.log('oi');
				//console.log(filter);
				//console.log('oi');
				$scope.listFinished = res;
				var totalMonth = 0;
				if (res) {
					for (var i = 0; i < res.length; i++) {
						totalMonth += res[i].unitaryValue;
						$scope.listFinished[i].cont = 3109;
						$scope.listFinished[i].cont2 = 832;
						$scope.listFinished[i].showInput = false;
						$scope.listFinished[i].showInput2 = false;
					}
					$scope.totalMonth = totalMonth;
				} else {
					$scope.totalMonth = 0;
				}
			}, function () {
			});

			function periodModalCtrl($scope, $mdDialog) {
				$scope.modal = {};

				$scope.getMonthWithRequest = function () {
					StockService.monthWithRequest(function (res) {
						$scope.monthWithRequest = res.data;
					}, function () {
						console.error("Erro ao buscar dados!");
					});
				}

				$scope.getMonthWithRequest();

				$scope.modal.filtrar = function (filter) {
					if (filter) {
						var date = filter.split("-");
						filter = { start: "", end: "" };
						filter.start = new Date(date[0], date[1] - 1, 01);
						filter.end = new Date(date[0], date[1], 0);
					}

					var momentDateStart = moment(filter.start).format('YYYY-MM-DDT00:00:00');
					momentDateStart = momentDateStart + 'Z';

					var momentDateEnd = moment(filter.end).format('YYYY-MM-DDT00:00:00');
					momentDateEnd = momentDateEnd + 'Z';

					filter.start = momentDateStart;
					filter.end = momentDateEnd;
					$scope.totalMonth = 0;
					StockService.listHistoric(filter, function (res) {
						/*console.log("saida");
						console.log(res.data);
						$scope.listFinished = res.data;
						var totalMonth = 0;
						if(res.data){
							for (var i = 0; i < res.data.length; i++) {
								totalMonth += res.data[i].unitaryValue;
							}
							$scope.totalMonth = totalMonth;						
						}*/
						$mdDialog.hide(res.data);
					}, function () {
						console.error("Erro ao buscar dados!");
					});
					//$mdDialog.hide($scope.filter);
				};

				$scope.modal.cancel = function () {
					$mdDialog.cancel();
				};
			};
		};


		$scope.totalValue();
		$scope.search();
	}]);

project.controller('ButtomFlyCtrl', ['$scope', '$mdDialog',
	function ($scope, $mdDialog) {
		var self = this;

		self.hidden = false;
		self.isOpen = false;
		self.hover = false;

		self.items = [
			{ name: "RequisiÃ§Ã£o", icon: "done", direction: "left", href: "/request" },
			{ name: "RequisiÃ§Ãµes Pendentes", icon: "assignment_late", direction: "left", href: "/request/pending" },
			{ name: "Estoque", icon: "view_list", direction: "left", href: "/stock" }
		];

	}]);