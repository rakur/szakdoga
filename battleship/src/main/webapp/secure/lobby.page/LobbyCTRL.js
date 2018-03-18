app.controller('LobbyCtrl', function($rootScope, $scope, $location, $http) {
    if (!$rootScope.loggedInUser) {
        $location.path("/");
    }

    $scope.logout = function() {
        $http.post('logout', {}).then(function() {
            $rootScope.authenticated = undefined;
            $location.path("/");
        }, function() {
            $rootScope.authenticated = undefined;
        });
    };
    $scope.username = $rootScope.loggedInUser;

});