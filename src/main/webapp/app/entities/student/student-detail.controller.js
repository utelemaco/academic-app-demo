(function() {
    'use strict';

    angular
        .module('appNameApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student', 'ExtraCourse'];

    function StudentDetailController($scope, $rootScope, $stateParams, previousState, entity, Student, ExtraCourse) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appNameApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
