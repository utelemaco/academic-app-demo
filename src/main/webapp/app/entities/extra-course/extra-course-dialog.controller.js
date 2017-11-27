(function() {
    'use strict';

    angular
        .module('appNameApp')
        .controller('ExtraCourseDialogController', ExtraCourseDialogController);

    ExtraCourseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExtraCourse', 'Student'];

    function ExtraCourseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExtraCourse, Student) {
        var vm = this;

        vm.extraCourse = entity;
        vm.clear = clear;
        vm.save = save;
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.extraCourse.id !== null) {
                ExtraCourse.update(vm.extraCourse, onSaveSuccess, onSaveError);
            } else {
                ExtraCourse.save(vm.extraCourse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appNameApp:extraCourseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
