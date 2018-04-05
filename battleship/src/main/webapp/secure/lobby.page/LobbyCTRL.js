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
            $location.path("/room");
        }, function () {
        })
    };

    $scope.join = function (roomId) {
        $http.post('/battleship/rest/room/'+roomId, {}).then(function () {
            $location.path("/room");
        }, function () {
        })
    };
    $http.get('/battleship/rest/room/').then(function (response) {
        if (response.data) {
            $location.path("/room");
        }
    });
    $scope.refresh = function () {
        $http.get('/battleship/rest/room/getall').then(function (response) {
        if (!response) {
            $location.path("/");
        }
        $scope.rooms = response.data;
        }, function () {
            $location.path("/");
        });
    };
    var promise = $interval($scope.refresh, 1000);
    $scope.$on('$destroy', function () {
        if (promise)
            $interval.cancel(promise)
    });
    $scope.username = $rootScope.loggedInUser;
});