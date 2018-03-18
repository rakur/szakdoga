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
                'email' : $scope.user.email,
                'password' : $scope.user.password
            };
            $http.post('http://localhost:8080/battleship/rest/register',content).then(function () {
                $location.path("/login")
            }, function () {
                $scope.user.emailError='is-invalid';
            })
        }
    };

    validatePassword = function() {
        if ($scope.user.password !== $scope.user.passwordConf) {
            $scope.user.passwordError ='is-invalid';
            valid = false;
        }
    };

    clearErrors = function() {
        $scope.user.passwordError = '';
        $scope.user.emailError='';
    };

});