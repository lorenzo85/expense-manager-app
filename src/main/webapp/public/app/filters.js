var app = angular.module('filters', []);

app.filter('capitalize', function() {
   return function(input) {
       if(input == null) return;

       input = input.toLowerCase();
       return input.substring(0, 1).toUpperCase() + input.substring(1);
   }
});