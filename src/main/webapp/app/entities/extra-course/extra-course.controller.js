(function() {
    'use strict';

    angular
        .module('appNameApp')
        .controller('ExtraCourseController', ExtraCourseController);

    ExtraCourseController.$inject = ['ExtraCourse'];

    function ExtraCourseController(ExtraCourse) {

        var vm = this;

        vm.extraCourses = [];

        loadAll();

        function loadAll() {
            ExtraCourse.query(function(result) {
                vm.extraCourses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
