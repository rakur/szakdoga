app.controller('GameCtrl', function($rootScope, $scope, $location, $http, $interval) {
    if (!$rootScope.loggedInUser) {
        $location.path("/");
    }
    $scope.index = [0,1,2,3,4,5,6,7,8,9];
    $scope.game = {};
    $scope.isHorizontal = {};

    $http.get("/battleship/rest/game").then(function (response) {
        if (!response.data) {
            $location.path("/lobby");
        }
        $scope.game = response.data;
        $scope.current = { username: response.data.gameState === 'PLAYER_ONE_SHOOTING' ? $rootScope.playerOne : response.data.gameState === 'PLAYER_ONE_WON' ? $rootScope.playerOne : response.data.gameState === 'PLAYER_TWO_SHOOTING' ? $rootScope.playerTwo : response.data.gameState === 'PLAYER_TWO_WON' ? $rootScope.playerTwo : ''};
    }, function () {
        $location.path("/lobby");
    });

    $scope.refresh = function () {
        $http.get("/battleship/rest/game").then(function (response) {
            if (!response.data) {
                $location.path("/lobby");
            }
            $scope.game = response.data;
            $scope.current = { username: response.data.gameState === 'PLAYER_ONE_SHOOTING' ? $rootScope.playerOne : response.data.gameState === 'PLAYER_ONE_WON' ? $rootScope.playerOne : response.data.gameState === 'PLAYER_TWO_SHOOTING' ? $rootScope.playerTwo : response.data.gameState === 'PLAYER_TWO_WON' ? $rootScope.playerTwo : ''};
        }, function () {
            $location.path("/lobby");
        })
    };

    var promise = $interval($scope.refresh, 1000);
    $scope.$on('$destroy', function () {
        if (promise)
            $interval.cancel(promise)
    });

    $scope.clickEvent = function (i, j) {
        var place = [i,j,1];
        if ($scope.isHorizontal.horizontal)
            place[2]=0;
        var shoot = [i,j];

        switch($scope.game.gameState) {
            case 'PLAYERS_PLACING':
                $http.post("/battleship/rest/game/place", place).then(function () {
                   $scope.refresh();
                });
                break;
            case 'PLAYER_ONE_SHOOTING':
                $http.post("/battleship/rest/game/shoot", shoot).then(function () {
                    $scope.refresh();
                });
                break;
            case 'PLAYER_TWO_SHOOTING':
                $http.post("/battleship/rest/game/shoot", shoot).then(function () {
                    $scope.refresh();
                });
                break;
            default:
                console.log("szar");
                break;
        }
    };

    $scope.initiateEnd = function () {
            $http.post("/battleship/rest/game/finish").then(function () {
                $scope.refresh();
            })
    };
})

    .directive('gameField', function () {
        return {
            restrict: 'E',
            templateUrl: 'secure/game.page/gamefield.html'
        };
    });