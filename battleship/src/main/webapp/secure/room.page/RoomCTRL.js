app.controller('RoomCtrl', function($rootScope, $scope, $location, $http, $interval) {
    if (!$rootScope.loggedInUser) {
        $location.path("/");
    }
    $scope.room = {};

    $scope.exit = function () {

    };

    $interval(function () {
        $http.get("/battleship/rest/room").then(function (response) {
            $scope.room = response.data;
        }, function () {
            console.log("nem jó a szoba");
        })
    }, 1000);
});