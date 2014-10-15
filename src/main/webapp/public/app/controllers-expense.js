var app = angular.module('expenseControllers', []);

app.controller('ExpensesController', function ($rootScope, $scope, $routeParams, Expense) {
    $scope.expenses = Expense.query({ yardId: $routeParams.id });

    $scope.getYardId = function () {
        return $routeParams.id;
    };

    $scope.deleteExpense = function(expense) {
        expense.$delete(function() {
            $scope.expenses = Expense.query({ yardId: $routeParams.id })
        });
    }


}).controller('ExpenseViewController', function ($rootScope, $scope, $routeParams, Expense) {
    $rootScope.title = "Expense details";
    $scope.expense = Expense.get({ yardId: $routeParams.yardId, id: $routeParams.id })


}).controller('ExpenseCreateController', function ($rootScope, $scope, $routeParams, $location, Expense) {
    $scope.states = Expense.allPaymentStatuses();
    $scope.categories = Expense.allExpenseCategories();

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


}).controller('ExpenseEditController', function ($rootScope, $scope, $routeParams, $location, Expense) {
    $scope.states = Expense.allPaymentStatuses();
    $scope.categories = Expense.allExpenseCategories();

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