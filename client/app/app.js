var app = angular.module('manager', [
    'ngResource',
    'ui.router',
    'ui.bootstrap',
    'yardControllers',
    'incomeControllers',
    'expenseControllers',
    'deadlineControllers',
    'navigationControllers',
    'directives',
    'resources',
    'filters']);

app.constant("config", {
    "url": "http://localhost:8080"
});

app.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/login');

    $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'login.html',
            controller: 'LoginController'

        }).state('navigation', {
            templateUrl: 'navigation.html',
            controller: 'NavigationController'
        })
        .state('yards', {
            url: '/yards',
            parent: 'navigation',
            templateUrl: 'partials/yards.html',
            controller: 'YardsController'
        })
        .state('yard-view', {
            url: '/yards/:id/view',
            parent: 'navigation',
            templateUrl: 'partials/yard/yard-view.html'
        })
        .state('yard-new', {
            url: '/yards/new',
            parent: 'navigation',
            templateUrl: 'partials/yard/yard-add.html',
            controller: 'YardsCreateController'
        })
        .state('yard-edit', {
            url: '/yards/:id/edit',
            parent: 'navigation',
            templateUrl: 'partials/yard/yard-edit.html',
            controller: 'YardsEditController'
        })
        .state('expense-view', {
            url: '/yards/:yardId/expenses/:id/view',
            parent: 'navigation',
            templateUrl: 'partials/expense/expense-view.html',
            controller: 'ExpenseViewController'
        })
        .state('expense-new', {
            url: '/yards/:yardId/expenses/new',
            parent: 'navigation',
            templateUrl: 'partials/expense/expense-add.html',
            controller: 'ExpenseCreateController'
        })
        .state('expense-edit', {
            url: '/yards/:yardId/expenses/:id/edit',
            parent: 'navigation',
            templateUrl: 'partials/expense/expense-edit.html',
            controller: 'ExpenseEditController'
        })
        .state('income-view', {
            url: '/yards/:yardId/incomes/:id/view',
            parent: 'navigation',
            templateUrl: 'partials/income/income-view.html',
            controller: 'IncomeViewController'
        })
        .state('income-new', {
            url: '/yards/:yardId/incomes/new',
            parent: 'navigation',
            templateUrl: 'partials/income/income-add.html',
            controller: 'IncomeCreateController'
        })
        .state('income-edit', {
            url: '/yards/:yardId/incomes/:id/edit',
            parent: 'navigation',
            templateUrl: 'partials/income/income-edit.html',
            controller: 'IncomeEditController'
        })
        .state('deadlines', {
            url: '/deadlines',
            parent: 'navigation',
            templateUrl: 'partials/deadlines.html',
            controller: 'DeadlinesController'
        });
});