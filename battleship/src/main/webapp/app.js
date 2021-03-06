var app = angular.module('Battleship', ['ngRoute', 'pascalprecht.translate'])
    .config(function ($routeProvider, $translateProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'login.page/login.html',
                controller: 'LoginCtrl'
            })
            .when('/register', {
                templateUrl: 'registration.page/registration.html',
                controller: 'RegistrationCtrl'
            })
            .when('/lobby', {
                templateUrl: 'secure/lobby.page/lobby.html',
                controller: 'LobbyCtrl'
            })
            .when('/game', {
                templateUrl: 'secure/game.page/game.html',
                controller: 'GameCtrl'
            })
            .when('/room', {
                templateUrl: 'secure/room.page/room.html',
                controller: 'RoomCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
        $translateProvider
            .translations('hu', {
                WELCOMETO: 'Üdvözlünk a Torpedó játékban',
                LOBBYTITLE: 'Várólista',
                ROOMTITLE: ' szobája',
                CREATEROOM: 'Új szoba',
                LOGOUT: 'Kijelentkezés',
                LOGIN: 'Bejelentkezés',
                USERNAME: 'Felhasználónév',
                WRONGCREDENTIALS: 'Hibás adatok',
                PASSWORD: 'Jelszó',
                REGISTER: 'Regisztráció',
                UNAMEALREADY: 'A felhasznélónév már létezik',
                EMAIL: 'E-mail cím',
                EALREADY: 'Az e-maillel már regisztráltak',
                PCONF: 'Jelszó megerősítése',
                PDONTMATCH: 'A jelszavak nem egyeznek',
                BACK: 'Vissza',
                WELCOME: 'Üdvözlünk ',
                OWNERTITLE: ' szobájában',
                Ready: 'Készen áll',
                NotReady: 'Nem áll készen',
                TOLOBBY: 'Vissza a várólistába',
                READY: 'Kész',
                PLAYERS_PLACING: 'Lerakási fázis',
                PLAYER_ONE_SHOOTING: '{{username}} Lő',
                PLAYER_TWO_SHOOTING: '{{username}} Lő',
                PLAYER_ONE_WON: '{{username}} Nyert',
                PLAYER_TWO_WON: '{{username}} Nyert',
                HORIZONTAL: 'Horizontális',
                REMAININGSHIPS: ' hátralevő hajói',
                UNPLACEDSHIPS: ' lerakatlan hajói',
                Forfeit: 'Feladás',
                BATTLESHIP: 'Csatahajó (4)',
                DESTROYER: 'Romboló (2)',
                SUBMARINE: 'Tengeralattjáró (3)',
                CARRIER: 'Anyahajó (5)',
                CRUISER: 'Cirkáló (3)',
                BattleShip: 'Csatahajó (4)',
                Destroyer: 'Romboló (2)',
                Submarine: 'Tengeralattjáró (3)',
                Carrier: 'Anyahajó (5)',
                Cruiser: 'Cirkáló (3)',
                JOIN: 'Belépés'

            })
            .translations('en', {
                WELCOMETO: 'Welcome to Battle Ship',
                LOBBYTITLE: 'Lobby',
                ROOMTITLE: ' \'s room',
                CREATEROOM: 'Create room',
                LOGOUT: 'Log out',
                LOGIN: 'Log in',
                USERNAME: 'Username',
                WRONGCREDENTIALS: 'Wrong Credentials',
                PASSWORD: 'Password',
                REGISTER: 'Registration',
                UNAMEALREADY: 'Username already in use',
                EMAIL: 'E-mail',
                EALREADY: 'E-mail already in use',
                PCONF: 'Confirm password',
                PDONTMATCH: 'Passwords does not match',
                BACK: 'Back',
                WELCOME: 'Welcome ',
                OWNERTITLE: ' \'s room',
                Ready: 'Ready',
                NotReady: 'Not ready',
                TOLOBBY: 'Back to lobby',
                READY: 'Ready',
                PLAYERS_PLACING: 'Placing state',
                PLAYER_ONE_SHOOTING: '{{username}} is shooting',
                PLAYER_TWO_SHOOTING: '{{username}} is shooting',
                PLAYER_ONE_WON: '{{username}} Won',
                PLAYER_TWO_WON: '{{username}} Won',
                HORIZONTAL: 'Horizontal',
                REMAININGSHIPS: '\'s reamining ships',
                UNPLACEDSHIPS: '\'s reamining ships',
                Forfeit: 'Forfeit',
                BATTLESHIP: 'Battleship (4)',
                DESTROYER: 'Destroyer (2)',
                SUBMARINE: 'Submarine (3)',
                CARRIER: 'Carrier (5)',
                CRUISER: 'Cruiser (3)',
                BattleShip: 'Battleship (4)',
                Destroyer: 'Destroyer (2)',
                Submarine: 'Submarine (3)',
                Carrier: 'Carrier (5)',
                Cruiser: 'Cruiser (3)',
                JOIN: 'Join'
            });
        $translateProvider.preferredLanguage('hu');
    })
    .directive('langSelector', function () {
        return {
            restrict: 'E',
            templateUrl: 'langselector.html'
        };
    })
    .controller('langCtrl', function ($scope, $translate) {
        $scope.changeLanguage = function (key) {
            $translate.use(key);
            console.log("asd")
        };
    });