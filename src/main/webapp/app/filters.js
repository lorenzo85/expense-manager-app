var app = angular.module('filters', []);

app.filter('capitalize', function() {
   return function(input) {
       if(input == null) return;
       input = input.toLowerCase();
       return input.substring(0, 1).toUpperCase() + input.substring(1);
   }


}).filter('euroCurrency', ["$filter", function($filter) {
    return function(amount) {
        if(amount == undefined) return;

        var currencyFilter = $filter('currency');
        var formatted = currencyFilter(amount, '');
        return '\u20AC '.concat(formatted.replace('(','-').replace(')', ''));
    }

}]).filter('dateFormat', ["$filter", function($filter) {
    return function(date) {
        var dateFilter = $filter('date');
        return dateFilter(date, 'dd/MM/yyyy');
    }
}]);