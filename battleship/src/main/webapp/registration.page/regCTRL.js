app.controller('RegistrationCtrl', function($scope, $location, $http) {

    $scope.user = {userName: '', email : '', password : '', passwordConf : '', userNameError:'', passwordError : '', emailError: ''};
    var valid;

    $scope.submit = function () {
        clearErrors();
        valid = true;
        validatePassword();
        if (valid) {
            var content = {
                'userName': $scope.user.userName,
                'email': $scope.user.email,
                'password': $scope.user.password
            };
            $http.post('/battleship/rest/register',content).then(function () {
                $location.path("/login")
            }, function (response) {
                if (response.data.message === "Email")
                    $scope.user.emailError = 'is-invalid';
                else
                    $scope.user.userNameError = 'is-invalid';
            })
        }
    };

    var validatePassword = function() {
        if ($scope.user.password !== $scope.user.passwordConf) {
            $scope.user.passwordError = 'is-invalid';
            valid = false;
        }
    };

    var clearErrors = function() {
        $scope.user.passwordError = '';
        $scope.user.emailError = '';
        $scope.user.userNameError = '';
    };

});