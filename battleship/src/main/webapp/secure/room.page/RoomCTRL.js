app.controller('RoomCtrl', function($rootScope, $scope, $location, $http, $interval) {
    if (!$rootScope.loggedInUser) {
        $location.path("/");
    }
    $scope.room = {};

    $scope.exit = function () {
        $http.post("/battleship/rest/room/quit", {}).then(function () {
            $location.path("/lobby");
        }, function () {
            console.log("nem sikerült kilépni");
        })
    };
    $scope.refresh = function () {
        $http.get("/battleship/rest/room").then(function (response) {
            $scope.room = response.data;
            if (!response.data)
                $location.path("/lobby");
        }, function () {
            console.log("nem jó a szoba");
        });
    };
    $scope.changeReadyState = function () {
        $http.patch("/battleship/rest/room/ready").then(function () {
            console.log("ready/unready")
            $scope.refresh();
        }, function () {
            console.log("nem tud readyzni")
        })
    };

    var promise = $interval($scope.refresh, 1000);
    $scope.$on('$destroy', function () {
        if (promise)
            $interval.cancel(promise)
    });
});