(function() {
    'use strict';
    angular
        .module('appNameApp')
        .factory('ExtraCourse', ExtraCourse);

    ExtraCourse.$inject = ['$resource'];

    function ExtraCourse ($resource) {
        var resourceUrl =  'api/extra-courses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
