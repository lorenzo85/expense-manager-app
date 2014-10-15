var app = angular.module('directives', []);

app.directive('widgetNewElement', function() {
    return {
        templateUrl: "/includes/widget-new-element.html",
        scope: {
            url: "@url",
            label: "@label"
        }
    };
});