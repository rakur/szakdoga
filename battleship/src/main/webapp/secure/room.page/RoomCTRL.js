app.controller('RoomCtrl', function($rootScope, $scope, $location, $http, $interval) {
    if (!$rootScope.loggedInUser) {
        $location.path("/");
    }
    $scope.room = {};

    $scope.exit = function () {
        $http.post("/battleship/rest/room/quit", {}).then(function () {
            $location.path("/lobby");
        }, function () {
        })
    };
    $http.get("/battleship/rest/room").then(function (response) {
        if (!response.data)
            $location.path("/lobby");
        $scope.room = response.data;
        $rootScope.playerOne = response.data.ownerName;
        $rootScope.playerTwo = response.data.userName;
        if ($scope.room.roomState === "ONGOING") {
            $location.path("/game");
        }
    }, function () {
    });
    $scope.refresh = function () {
        $http.get("/battleship/rest/room").then(function (response) {
            if (!response.data)
                $location.path("/lobby");
            $scope.room = response.data;
            $rootScope.playerOne = response.data.ownerName;
            $rootScope.playerTwo = response.data.userName;
            if ($scope.room.roomState === "ONGOING") {
                $location.path("/game");
            }
        }, function () {
            $location.path("/lobby");
        });
    };
    $scope.changeReadyState = function () {
        $http.patch("/battleship/rest/room/ready").then(function () {
            $scope.refresh();
        }, function () {
        })
    };

    var promise = $interval($scope.refresh, 1000);
    $scope.$on('$destroy', function () {
        if (promise)
            $interval.cancel(promise)
    });
});