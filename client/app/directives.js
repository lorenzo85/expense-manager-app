var app = angular.module('directives', []);

app.directive('widgetNewElement', function() {
    return {
        templateUrl: "includes/new-element.tpl.html",
        scope: {
            url: "@url",
            label: "@label"
        }
    };
});

app.directive('euroCurrency', function() {
    return {
        templateUrl: "includes/euro-currency.tpl.html",
        scope: {
            amount: "@amount"
        }
    };
});

app.directive('fileDownload', ['$http', 'config', function($http, config) {
   return {
       restrict: 'E',
       templateUrl: "includes/file-download.tpl.html",
       scope: true,
       link: function($scope, element, attributes) {
           $scope.text = 'Download ' + attributes.name;
           var anchor = element.children()[0];

           $scope.downloadFile = function () {
               disableButton();
               $http.get(config.url + attributes.url).then(function(response) {
                   setUpButtonForDownload(response);
                   // Overwrite the download function to do nothing.
                   // This is because after the file has been downloaded
                   // pressing the button should do nothing.
                   $scope.downloadFile =  function() { };
               });
           };

           function setUpButtonForDownload(response) {
               $(anchor).attr({
                   href: 'data:application/xls;base64, ' + response.data,
                   download: extractFileNameFromResponse(response)
               }).removeAttr('disabled')
                   .text('Save ' + extractFileNameFromResponse(response))
                   .removeClass('btn-default')
                   .addClass('btn-success');
           }

           function disableButton() {
               $(anchor).attr('disabled', 'disabled');
           }

           function extractFileNameFromResponse(response) {
               return response
                   .headers('Content-Disposition')
                   .split(';')[1]
                   .split("=")[1]
                   .replace(/\"/g, "");
           }
       }
   }
}]);