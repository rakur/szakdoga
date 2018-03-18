var app = angular.module('Battleship', ['ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'login.page/login.html',
                controller: 'LoginCtrl'
            })
            .when('/register', {
                templateUrl: 'registration.page/registration.html',
                controller: 'RegistrationCtrl'
            })
            .when('/lobby', {
                templateUrl: 'secure/lobby.page/lobby.html',
                controller: 'LobbyCtrl'
            })
            .when('/game', {
                templateUrl: 'secure/game.page/game.html',
                controller: 'GameCtrl'
            })
            .when('/room', {
                templateUrl: 'secure/room.page/room.html',
                controller: 'RoomCtrl'
            })
            .otherwise({
                redirectTo: '/'
            })
    });