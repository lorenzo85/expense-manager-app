var app = angular.module('yardControllers', []);

app.controller('YardsController', function($rootScope, $scope, $location, Yard) {
    $rootScope.title = 'Yards';

    $scope.yards = Yard.query();

    $scope.deleteYard = function(yard) {
        yard.$delete(function() {
            $scope.yards = Yard.query();
        });
    }

}).controller('YardsViewController', function($rootScope, $scope, $stateParams, Yard) {
    $rootScope.$on('data::updated', function(){
        $scope.yard = Yard.getYardDetails({ id: $stateParams.id });
    });

    $scope.yard = Yard.getYardDetails({ id: $stateParams.id }, function(){
        $rootScope.title = $scope.yard.name;
    });

}).controller('YardsCreateController', function($rootScope, $scope, $location, Yard) {
    $rootScope.title = 'New Yard';
    $scope.yard = new Yard();

    $scope.reset = function() {
        $scope.yard = new Yard();
    };

    $scope.addYard = function() {
        $scope.yard.$save(function() {
            $location.path('/yards');
        });
    }

}).controller('YardsEditController', function($rootScope, $scope, $location, $stateParams, Yard) {
    $scope.yard = Yard.get({ id: $stateParams.id }, function(yard) {
        $rootScope.title = yard.name;
    });

    $scope.reset = function() {
        $scope.yard = Yard.get({ id: $stateParams.id })
    };

    $scope.updateYard = function() {
        $scope.yard.$update(function() {
            $location.path('/yards');
        });
    };
});