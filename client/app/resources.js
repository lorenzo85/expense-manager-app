var app = angular.module('resources', []);

app.factory('Yard', function($resource, config) {
   return $resource(config.url + '/yards/:id', { id: '@id' },{
       update: {
           url: config.url + '/yards',
           method: 'PUT'
       },
       getYardDetails: {
           url: config.url + '/yards/:id/details',
           method: 'GET'
       }
   });

}).factory('Expense', function($resource, config) {
    return $resource(config.url + '/yards/:yardId/expenses/:id', { id: '@id', yardId: '@yardId' }, {
        update: {
            url: config.url + '/yards/:yardId/expenses',
            method: 'PUT'
        },
        allPaymentStatuses: {
            url: config.url + '/yards/expenses/allPaymentStatuses',
            method: 'GET',
            isArray: true
        },
        allExpenseCategories: {
            url: config.url + '/yards/expenses/allExpenseCategories',
            method: 'GET',
            isArray: true
        }
    });

}).factory('Income', function($resource, config) {
   return $resource(config.url + '/yards/:yardId/incomes/:id', { id: '@id', yardId: '@yardId' },{
       update: {
           url: config.url + '/yards/:yardId/incomes',
           method: 'PUT'
       }
   });

}).factory('Deadline', function($resource, config) {
   return $resource(config.url + '/yards/expenses/deadlines', {id: '@id', yardId: '@yardId'}, {
       markAsPaid: {
           url: config.url + '/yards/:yardId/expenses/:id/markAsPaid',
           method: 'PUT'
       }
   });
});