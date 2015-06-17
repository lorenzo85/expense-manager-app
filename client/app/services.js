var app = angular.module('services', []);

app.factory('authService', function ($http, $q, $window) {

    var userInfo;

    function login(username, password) {
        var deferred = $q.defer();

        $http.post("/api/login", {
            userName: userName,
            password: password
        }).then(function (result) {
            userInfo = {
                accessToken: result.data.accessToken,
                userName: result.data.userName
            };

            $window.sessionStorage["userInfo"] = JSON.stringify(userInfo);
            deferred.resolve(userInfo);
        }, function(error) {
            deferred.reject(error);
        });

        return deferred.promise();
    }

    return {
        login: login
    };

});