var app = angular.module('directives', []);

app.directive('widgetNewElement', function() {
    return {
        templateUrl: "/includes/widget-new-element.html",
        scope: {
            url: "@url",
            label: "@label"
        }
    };


}).directive('euroCurrency', function() {
    return {
        templateUrl: "/includes/template-euro-currency.html",
        scope: {
            amount: "@amount"
        }
    };
});