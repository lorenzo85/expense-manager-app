var app = angular.module('deadlineControllers', []);

app.controller('DeadlinesController', function($rootScope, $scope, Deadline) {
    $rootScope.title = "Deadlines";

    $scope.deadlines = Deadline.query();

    $scope.markAsPaid = function(yardId, id) {
        Deadline.markAsPaid({ yardId: yardId, id: id }, function(){
            $scope.deadlines = Deadline.query();
        });
    }
});