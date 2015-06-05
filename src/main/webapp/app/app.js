var app = angular.module('manager', [
    'ngRoute',
    'ngResource',
    'ui.bootstrap',
    'yardControllers',
    'incomeControllers',
    'expenseControllers',
    'deadlineControllers',
    'navigationControllers',
    'directives',
    'resources',
    'filters']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
            .when('/yards', {
                templateUrl: 'partials/yards.html',
                controller: 'YardsController'

            }).when('/deadlines', {
                templateUrl: 'partials/deadlines.html',
                controller: 'DeadlinesController'

            }).when('/yards/:id/view', {
                templateUrl: 'partials/yard/yard-view.html'

            }).when('/yards/new', {
                templateUrl: 'partials/yard/yard-add.html',
                controller: 'YardsCreateController'

            }).when('/yards/:id/edit', {
                templateUrl: 'partials/yard/yard-edit.html',
                controller: 'YardsEditController'

            }).when('/yards/:yardId/expenses/:id/view', {
                templateUrl: 'partials/expense/expense-view.html',
                controller: 'ExpenseViewController'

            }).when('/yards/:yardId/expenses/new', {
                templateUrl: 'partials/expense/expense-add.html',
                controller: 'ExpenseCreateController'

            }).when('/yards/:yardId/expenses/:id/edit', {
                templateUrl: 'partials/expense/expense-edit.html',
                controller: 'ExpenseEditController'

            }).when('/yards/:yardId/incomes/:id/view', {
                templateUrl: 'partials/income/income-view.html',
                controller: 'IncomeViewController'

            }).when('/yards/:yardId/incomes/new', {
                templateUrl: 'partials/income/income-add.html',
                controller: 'IncomeCreateController'

            }).when('/yards/:yardId/incomes/:id/edit', {
                templateUrl: 'partials/income/income-edit.html',
                controller: 'IncomeEditController'

            }).when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'LoginController'

            }).otherwise({
                redirectTo: '/'
            })
    }]);