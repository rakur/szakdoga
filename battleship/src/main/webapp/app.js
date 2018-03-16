var app = angular.module('Battleship', ['ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/register', {
                templateUrl: 'registration.page/registration.html',
                controller: 'RegistrationCtrl'
            })
            .when('/lobby', {
                templateUrl: 'lobby.page/lobby.html',
                controller: 'LobbyCtrl'
            })
            .when('/game', {
                templateUrl: 'game.page/game.html',
                controller: 'GameCtrl'
            })
            .when('/room', {
                templateUrl: 'room.page/room.html',
                controller: 'RoomCtrl'
            })
            .otherwise({
                templateUrl: 'login.page/login.html',
                controller: 'LoginCtrl'
            })
    });