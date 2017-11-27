(function() {
    'use strict';

    angular
        .module('appNameApp')
        .controller('ExtraCourseDeleteController',ExtraCourseDeleteController);

    ExtraCourseDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExtraCourse'];

    function ExtraCourseDeleteController($uibModalInstance, entity, ExtraCourse) {
        var vm = this;

        vm.extraCourse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExtraCourse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
