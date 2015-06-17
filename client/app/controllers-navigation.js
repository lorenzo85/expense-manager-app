var app = angular.module('navigationControllers', []);

app.factory('TokenStorage', function () {
    var storageKey = 'auth_token';
    return {
        store: function (token) {
            return localStorage.setItem(storageKey, token);
        },
        retrieve: function () {
            return localStorage.getItem(storageKey);
        },
        clear: function () {
            return localStorage.removeItem(storageKey);
        }
    }
});

app.factory('TokenAuthInterceptor', function ($q, TokenStorage) {
    return {
        request: function (config) {
            var authToken = TokenStorage.retrieve();
            if (authToken) {
                config.headers['X-AUTH-TOKEN'] = authToken;
            }
            return config;
        },
        responseError: function (error) {
            if (error.status === 401 || error.status === 403) {
                TokenStorage.clear();
            }
            return $q.reject(error);
        }
    }
});

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('TokenAuthInterceptor');
});

app.controller('NavigationController', function ($rootScope, $scope, $location, $http, config, TokenStorage) {

    $scope.init = function () {
        $http.get(config.url + '/auth/user').success(function (user) {
            if (user.username !== 'anonymousUser') {
                $rootScope.authenticated = true;
                $rootScope.username = user.username;
                $location.path("/yards");
            } else {
                $location.path("/login");
            }
        })
    };

    $scope.logout = function () {
        TokenStorage.clear();
        $rootScope.authenticated = false;
        $location.path("/login");
    };

    $scope.init();

}).controller('LoginController', function ($scope, $location, $rootScope, $http, config, TokenStorage) {

    $rootScope.authenticated = false;

    $scope.login = function() {
        $http.post(config.url + '/login', {username: $scope.username, password: $scope.password})
            .success(function (data, status, headers) {
                $rootScope.authenticated = true;
                TokenStorage.store(headers('X-AUTH-TOKEN'));
                $location.path("/yards");
            }).error(function () {
                $scope.error = "Could not login!";
            });
    };


});