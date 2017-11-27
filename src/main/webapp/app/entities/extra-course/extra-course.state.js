(function() {
    'use strict';

    angular
        .module('appNameApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('extra-course', {
            parent: 'entity',
            url: '/extra-course',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appNameApp.extraCourse.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extra-course/extra-courses.html',
                    controller: 'ExtraCourseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('extraCourse');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('extra-course-detail', {
            parent: 'extra-course',
            url: '/extra-course/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appNameApp.extraCourse.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extra-course/extra-course-detail.html',
                    controller: 'ExtraCourseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('extraCourse');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ExtraCourse', function($stateParams, ExtraCourse) {
                    return ExtraCourse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'extra-course',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('extra-course-detail.edit', {
            parent: 'extra-course-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-course/extra-course-dialog.html',
                    controller: 'ExtraCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtraCourse', function(ExtraCourse) {
                            return ExtraCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extra-course.new', {
            parent: 'extra-course',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-course/extra-course-dialog.html',
                    controller: 'ExtraCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                grade: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('extra-course', null, { reload: 'extra-course' });
                }, function() {
                    $state.go('extra-course');
                });
            }]
        })
        .state('extra-course.edit', {
            parent: 'extra-course',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-course/extra-course-dialog.html',
                    controller: 'ExtraCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtraCourse', function(ExtraCourse) {
                            return ExtraCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extra-course', null, { reload: 'extra-course' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extra-course.delete', {
            parent: 'extra-course',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-course/extra-course-delete-dialog.html',
                    controller: 'ExtraCourseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExtraCourse', function(ExtraCourse) {
                            return ExtraCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extra-course', null, { reload: 'extra-course' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
