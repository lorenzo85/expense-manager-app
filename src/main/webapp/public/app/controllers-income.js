var app = angular.module('incomeControllers', []);

app.controller('IncomesController', function($rootScope, $scope, $routeParams, Income) {

    $scope.incomes = Income.query({ yardId: $routeParams.id });

    $scope.getYardId = function() {
        return $routeParams.id;
    };

    $scope.deleteIncome = function(income) {
        income.$delete(function() {
            $scope.incomes = Income.query({ yardId: $routeParams.id })
        })
    };


}).controller('IncomeCreateController', function($rootScope, $scope, $routeParams, $location, Income, Expense) {
    $rootScope.title = "New income";

    $scope.states = Expense.allPaymentStatuses();
    $scope.income = new Income();

    $scope.reset = function() {
        $scope.income = new Income();
    };

    $scope.addIncome = function() {
        $scope.income.$save({ yardId: $routeParams.yardId }, function(){
           $location.path('/yards/' + $routeParams.yardId + '/view');
        });
    };


}).controller('IncomeEditController', function($rootScope, $scope, $routeParams, $location, Income, Expense) {
   $rootScope.title = "Edit income";

    $scope.states = Expense.allPaymentStatuses();
    $scope.income = Income.get({ yardId: $routeParams.yardId, id: $routeParams.id });

    $scope.reset = function() {
        $scope.income = Income.get({ yardId: $routeParams.yardId, id: $routeParams.id })
    };

    $scope.updateIncome = function() {
        $scope.income.$update({ yardId: $routeParams.yardId }, function(){
           $location.path('/yards/' + $routeParams.yardId + '/view')
        });
    };
});