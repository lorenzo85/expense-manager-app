var app = angular.module('navigationControllers', []);

app.run(['$rootScope', '$location', function($rootScope, $location) {
    $rootScope.$on("$locationChangeStart", function(event, next) {
        if (next.templateUrl != "partials/login.html" && !$rootScope.loggedIn) {
            $location.path("/login");
        }
    });
}]);

app.controller('NavigationController', function($rootScope, $scope, $location) {

    $scope.$watch(function() {
        return $rootScope.loggedIn;
    }, function() {
        $scope.title = $rootScope.loggedIn;
    }, true);

    $scope.logout = function() {
        $rootScope.loggedIn = null;
        $location.path("/");
    }
});

app.controller('LoginController', function($scope, $location, $rootScope) {

    $rootScope.title = "Login";

    $scope.login = function() {
        $rootScope.loggedIn = $scope.username;
        $location.path("/yards");
    };
});