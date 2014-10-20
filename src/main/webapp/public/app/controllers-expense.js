var app = angular.module('expenseControllers', []);

app.controller('ExpensesController', function ($rootScope, $scope, $routeParams, Expense) {
    $scope.expenses = Expense.query({ yardId: $routeParams.id });

    $scope.getYardId = function () {
        return $routeParams.id;
    };

    $scope.deleteExpense = function(expense) {
        expense.$delete(function() {
            $scope.expenses = Expense.query({ yardId: $routeParams.id });
            $rootScope.$emit('data::updated');
        });
    };


}).controller('ExpenseViewController', function ($rootScope, $scope, $routeParams, Expense) {
    $rootScope.title = "Expense details";
    $scope.expense = Expense.get({ yardId: $routeParams.yardId, id: $routeParams.id })


}).controller('BaseCommandController', function($scope, $expense) {
    $scope.states = $expense.allPaymentStatuses();
    $scope.categories = $expense.allExpenseCategories();

    $scope.datepickers = {
        expiresAt: false,
        emissionAt: false
    };

    $scope.getToday = function() {
        return new Date();
    };

    $scope.open = function ($event, which) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.datepickers[which] = true;
    };

}).controller('ExpenseCreateController', function ($rootScope, $scope, $routeParams, $location, $controller, Expense) {
    $controller('BaseCommandController', {$scope: $scope, $expense: Expense});

    $rootScope.title = "New expense";
    $scope.expense = new Expense();

    $scope.reset = function () {
        $scope.expense = new Expense();
    };

    $scope.addExpense = function () {
        $scope.expense.$save({ yardId: $routeParams.yardId }, function () {
            $location.path('/yards/' + $routeParams.yardId + '/view');
        });
    }


}).controller('ExpenseEditController', function ($rootScope, $scope, $routeParams, $location, $controller, Expense) {
    $controller('BaseCommandController', {$scope: $scope, $expense: Expense});

    $rootScope.title = "Edit expense";
    $scope.expense = Expense.get({ yardId: $routeParams.yardId, id: $routeParams.id });

    $scope.reset = function () {
        $scope.expense = Expense.get({ yardId: $routeParams.yardId, id: $routeParams.id })
    };

    $scope.updateExpense = function () {
        $scope.expense.$update({ yardId: $routeParams.yardId }, function () {
            $location.path('/yards/' + $routeParams.yardId + '/view');
        });
    }
});