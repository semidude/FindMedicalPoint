(function (){
    'use strict';

    angular.module('app').controller('MedicalPointsController', MedicalPointsController);

        //$scope.hello = "Goodbye world!";

        MedicalPointsController.$inject = ['$http'];
        function MedicalPointsController($http) {
            var vm =this;

            vm.medicalPoints = [];
            vm.getAll = getAll;
            vm.deleteMedicalPoint = deleteMedicalPoint;
            vm.addMedicalPoint = addMedicalPoint;

            init();

            function init()
            {
                getAll();
            }
            function getAll()
            {
                var url = "/medicalPoints/findmp";
                var findmpPromise = $http.get(url);
                findmpPromise.then(function(response)
                {
                    vm.medicalPoints = response.data;
                });
            }

            function deleteMedicalPoint(id)
            {
                var url = "/medicalPoints/removemp?ID=" + id;
                $http.post(url).then(function(response)
                {
                    vm.medicalPoints = response.data;
                });
            }

            function addMedicalPoint(name, type, city, street, number)
            {
                var url = "/medicalPoints/addmp?name="+name+"&type="+type+"&adress="+city+";"+street+";"+number
                $http.post(url).then(function(response)
                {
                    vm.medicalPoints = response.data;
                });
            }


        }
})();

