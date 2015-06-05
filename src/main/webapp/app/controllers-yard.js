var app = angular.module('yardControllers', []);

app.controller('YardsController', function($rootScope, $scope, $location, Yard) {
    $rootScope.title = "Yards";

    $scope.yards = Yard.query();

    $scope.deleteYard = function(yard) {
        yard.$delete(function() {
            $scope.yards = Yard.query();
        });
    }

}).controller('YardsViewController', function($rootScope, $scope, $routeParams, Yard) {

    $rootScope.$on('data::updated', function(){
        $scope.yard = Yard.getYardDetails({ id: $routeParams.id });
    });

    $scope.yard = Yard.getYardDetails({ id: $routeParams.id }, function(){
        $rootScope.title = $scope.yard.name;
    });

}).controller('YardsCreateController', function($rootScope, $scope, $location, Yard) {
    $rootScope.title = "New yard";

    $scope.yard = new Yard();

    $scope.reset = function() {
        $scope.yard = new Yard();
    };

    $scope.addYard = function() {
        $scope.yard.$save(function() {
            $location.path('/yards');
        });
    }

}).controller('YardsEditController', function($rootScope, $scope, $location, $routeParams, Yard) {
    $rootScope.title = "Edit yard";

    $scope.yard = Yard.get({ id: $routeParams.id });

    $scope.reset = function() {
        $scope.yard = Yard.get({ id: $routeParams.id })
    };

    $scope.updateYard = function() {
        $scope.yard.$update(function() {
            $location.path('/yards');
        });
    };
});