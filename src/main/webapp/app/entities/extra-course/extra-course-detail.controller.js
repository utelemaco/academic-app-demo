(function() {
    'use strict';

    angular
        .module('appNameApp')
        .controller('ExtraCourseDetailController', ExtraCourseDetailController);

    ExtraCourseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExtraCourse', 'Student'];

    function ExtraCourseDetailController($scope, $rootScope, $stateParams, previousState, entity, ExtraCourse, Student) {
        var vm = this;

        vm.extraCourse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appNameApp:extraCourseUpdate', function(event, result) {
            vm.extraCourse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
