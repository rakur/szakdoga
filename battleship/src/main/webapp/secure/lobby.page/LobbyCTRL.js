app.controller('LobbyCtrl', function($rootScope, $scope, $location, $http, $interval) {
    if (!$rootScope.loggedInUser) {
        $location.path("/");
    }
    $scope.rooms = [];
    $scope.logout = function() {
        $http.post('logout', {}).then(function() {
            $rootScope.authenticated = undefined;
            $location.path("/");
        }, function() {
            $rootScope.authenticated = undefined;
        });
    };
    $scope.create = function () {
        $http.post('/battleship/rest/room', {}).then(function () {
            console.log("room created");
        }, function () {
            console.log("failed");
        })
    };

    $scope.join = function (roomId) {
        $http.post('/battleship/rest/room/'+roomId, {}).then(function () {
            $location.path("/room");
        }, function () {
            console.log("nem tud belépni");
        })
    };

    $interval(function () {
        $http.get('/battleship/rest/room/getall').then(function (response) {
            $scope.rooms = response.data;
        }, function () {
            console.log("a listázás nem okos")
        })
    },1000);
    $scope.username = $rootScope.loggedInUser;

});