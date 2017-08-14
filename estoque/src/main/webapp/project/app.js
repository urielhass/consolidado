'use strict';
var project = {};

project = angular.module('myApp',
['ngRoute',
'ngMaterial',
'ngResource',
'md.data.table',
'ui.sortable',
'ngMessages',
'ngStorage',
'ui.utils.masks'
]
);

project.pathPrivate = '../privateRest';
project.pathRest = '../rest';
project.config(function($routeProvider, $mdThemingProvider, $mdDateLocaleProvider) {

	//	$routeProvider.otherwise("/");
	$mdThemingProvider.theme('default')
	.primaryPalette('grey')
	.accentPalette('green', {
		'default' : '600'
	});

	$mdDateLocaleProvider.formatDate = function(date) {
		return moment(date).format('DD/MM/YYYY');
    };

	$routeProvider
	.when('/', {
		templateUrl : 'views/dashboard.html',
		controller: 'DashboardCtrl'
	})

	.when('/dashboard', {
		templateUrl : 'views/dashboard.html',
		controller: 'DashboardCtrl'
	})

	.when('/category', {
		templateUrl: 'views/category.html',
		controller: 'CategoryCtrl'
	})

	.when('/stock', {
		templateUrl : 'views/stock.html',
		controller: 'StockCtrl'
	})

	.when('/sotck/details',{
		templateUrl : 'views/stockDetails.html'
	})
	
	.when('/users', {
		templateUrl : 'views/user.html',
		controller : 'UserCtrl'

	})

	.when('/historic', {
		templateUrl : 'views/historic.html',
		controller : 'HistoricCtrl'
	})

	.when('/login', {
		templateUrl : 'login.html',
		controller : 'LoginCtrl'
	})

	.when('/unity', {
		templateUrl : 'views/unity.html',
		controller : 'UnityCtrl'
	})

	.when('/request',{
		templateUrl: 'views/request.html',
		controller: 'RequestCtrl'
	})

	.when('/request/pending',{
		templateUrl: 'views/requestPending.html',
		controller: 'RequestPendingCtrl'
	})

//	.when('/request/approved',{
//		templateUrl: 'views/requestApproved.html',
//		controller: 'RequestApprovedCtrl'
//	})

	.when('/request/all',{
		templateUrl: 'views/requestAll.html',
		controller: 'RequestAllCtrl'
	})
	
	.when('/location',{
		templateUrl: 'views/location.html',
		controller: 'LocationCtrl'
	})
	
	.when('/monthTotal',{
		templateUrl: ' views/monthTotal.html',
		controller: 'monthTotalCtrl'
	})

	.when('/request/sending',{
		templateUrl: 'views/requestSending.html',
		controller: 'RequestSendingCtrl'
	})
});
project.run(function($rootScope, $http, $location, $localStorage) {
	// redirect to login page if not logged in and trying to access a restricted page
	$rootScope.$on('$locationChangeStart', function (event, next, current) {
		var publicPages = ['/login'];
		var restrictedPage = publicPages.indexOf($location.path()) === -1;
		if (restrictedPage && !$localStorage.currentUser) {
			$location.path('/login');
		}else if(publicPages.indexOf($location.path()) != -1 && $localStorage.currentUser){
			$location.path('/');
		}
	});
});
