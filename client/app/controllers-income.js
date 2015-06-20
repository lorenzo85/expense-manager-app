var app = angular.module('incomeControllers', []);

app.controller('IncomesController', function($rootScope, $scope, $stateParams, Income) {
    $scope.incomes = Income.query({ yardId: $stateParams.id });

    $scope.getYardId = function() {
        return $stateParams.id;
    };

    $scope.deleteIncome = function(income) {
        console.log("Delete income");
        income.$delete(function() {
            $scope.incomes = Income.query({ yardId: $stateParams.id });
            $rootScope.$emit('data::updated');
        })
    };


}).controller('IncomeCreateController', function($rootScope, $scope, $stateParams, $location, Income, Expense) {
    $rootScope.title = "New income";

    $scope.states = Expense.allPaymentStatuses();
    $scope.income = new Income();

    $scope.reset = function() {
        $scope.income = new Income();
    };

    $scope.addIncome = function() {
        $scope.income.$save({ yardId: $stateParams.yardId }, function(){
           $location.path('/yards/' + $stateParams.yardId + '/view');
        });
    };


}).controller('IncomeEditController', function($rootScope, $scope, $stateParams, $location, Income, Expense) {
   $rootScope.title = "Edit income";

    $scope.states = Expense.allPaymentStatuses();
    $scope.income = Income.get({ yardId: $stateParams.yardId, id: $stateParams.id });

    $scope.reset = function() {
        $scope.income = Income.get({ yardId: $stateParams.yardId, id: $stateParams.id })
    };

    $scope.updateIncome = function() {
        $scope.income.$update({ yardId: $stateParams.yardId }, function(){
           $location.path('/yards/' + $stateParams.yardId + '/view')
        });
    };
});