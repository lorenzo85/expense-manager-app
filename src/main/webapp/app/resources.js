var app = angular.module('resources', []);

app.factory('Yard', function($resource) {
   return $resource('/yards/:id', { id: '@id' },{
       update: {
           url: '/yards',
           method: 'PUT'
       },
       getYardDetails: {
           url: '/yards/:id/details',
           method: 'GET'
       }
   });


}).factory('Expense', function($resource) {
    return $resource('/yards/:yardId/expenses/:id', { id: '@id', yardId: '@yardId' },{
        update: {
            url: '/yards/:yardId/expenses',
            method: 'PUT'
        },
        allPaymentStatuses: {
            url: '/yards/expenses/allPaymentStatuses',
            method: 'GET',
            isArray: true
        },
        allExpenseCategories: {
            url: '/yards/expenses/allExpenseCategories',
            method: 'GET',
            isArray: true
        }
    });


}).factory('Income', function($resource) {
   return $resource('/yards/:yardId/incomes/:id', { id: '@id', yardId: '@yardId' },{
       update: {
           url: '/yards/:yardId/incomes',
           method: 'PUT'
       }
   });


}).factory('Deadline', function($resource) {
   return $resource('yards/expenses/deadlines', {id: '@id', yardId: '@yardId'}, {
       markAsPaid: {
           url: '/yards/:yardId/expenses/:id/markAsPaid',
           method: 'PUT'
       }
   });
});