var app = angular.module('navigationControllers', []);

app.run(['$rootScope', '$location', function ($rootScope, $location) {
    /**
    $rootScope.$on("$locationChangeStart", function (event, next) {
        if (next.templateUrl != "partials/login.html" && !$rootScope.authenticated) {
            $location.path("/");
        }
    });*/
}]);

app.controller('NavigationController', function ($rootScope, $scope, $location) {

    $scope.$watch(function () {
        return $rootScope.authenticated;
    }, function () {
        $scope.title = $rootScope.authenticated;
    }, true);

    $scope.logout = function() {
        $rootScope.authenticated = false;
        $location.path("/login");
    }

}).controller('LoginController', function ($scope, $location, $rootScope, $http, config) {

    $rootScope.title = "Login";

    $scope.credentials = {};

    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/yards");
                $scope.error = false;
            } else {
                $location.path("/login");
                $scope.error = true;
            }
        });
    };


    var authenticate = function (credentials, callback) {
        var headers = credentials ?
        { authorization: "Basic " + btoa(credentials.username + ":" + credentials.password) } : {};

        $http.get(config.url + '/user', {headers: headers}).success(function (data) {
            $rootScope.authenticated = !!data.name;
            callback && callback();
        }).error(function () {
            $rootScope.authenticated = false;
            callback && callback();
        });
    };

   // authenticate();
});