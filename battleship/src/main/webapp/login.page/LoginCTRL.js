app.controller('LoginCtrl', function($rootScope, $scope, $location, $http) {
    $scope.user = {credentialError: ''}
    var clearErrors = function () {
        $scope.user.credentialError = '';
    };

    var authenticate = function(callback) {
        $http.get('rest/user').then(function(response) {
            $rootScope.loggedInUser = response.data.name;
            callback && callback();
        },function() {
            $rootScope.loggedInUser = undefined;
            callback && callback();
        });
    };
    authenticate();
    clearErrors();
    $scope.credentials = {};
    $scope.login = function() {
        $http.post('login', $.param($scope.credentials), {
            headers : {
                "content-type" : "application/x-www-form-urlencoded"
            }
        }).then(function() {
            authenticate(function() {
                if ($rootScope.loggedInUser) {
                    $location.path("/lobby");
                }
                else {
                    $scope.user.credentialError = 'is-invalid';
                }
            });
        })
    };
});