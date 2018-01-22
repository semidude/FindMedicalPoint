(function (){
    'use strict';

    angular.module('app').controller('MedicalPointsController', MedicalPointsController);

        //$scope.hello = "Goodbye world!";

        MedicalPointsController.$inject = ['$http'];
        function MedicalPointsController($http) {
            var vm =this;

            vm.city;
            vm.street;
            vm.number;
            vm.medicalPoints = [];
            vm.getAll = getAll;
            vm.deleteMedicalPoint = deleteMedicalPoint;
            vm.addMedicalPoint = addMedicalPoint;
            vm.findClosest = findClosest;
            vm.findClosestByAddress = findClosestByAddress;
            vm.getLocation = getLocation;
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

            function addMedicalPoint(name, specialization, city, street, number)
            {
                var url = "/medicalPoints/addmp?name="+name+"&specialization="+specialization+"&adress="+city+";"+street+";"+number;
                $http.post(url).then(function(response)
                {
                    vm.medicalPoints = response.data;
                });
            }

            function findClosest(specialization, lat, lon)
            {
                var url = "/medicalPoints/findClosest?specialization="+specialization+"&lat="+lat+"&lon="+lon;
                var findmpPromise = $http.get(url);
                findmpPromise.then(function(response)
                {
                    vm.medicalPoints = response.data;
                });
            }

            function findClosestByAddress(specialization, city, street, number)
            {
                var url = "/medicalPoints/findClosestByAddress?specialization="+specialization+"&address="+city+";"+street+";"+number;
                var findmpPromise = $http.get(url);
                findmpPromise.then(function(response)
                {
                    vm.medicalPoints = response.data;
                });
            }

            var x = document.getElementById("demo");

            function getLocation() {
                vm.city = "Warszawa";
                vm.street = "Nowowiejska";
                vm.number = "15";
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(showPosition);
                } else {
                    x.innerHTML = "Geolocation is not supported by this browser.";
                }
            }

            function post(path, params, method) {
                method = method || "post"; // Set method to post by default if not specified.

                var form = document.createElement("form");
                form.setAttribute("method", method);
                form.setAttribute("action", path);

                for(var key in params) {
                    if(params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", key);
                        hiddenField.setAttribute("value", params[key]);

                        form.appendChild(hiddenField);
                    }
                }

                document.body.appendChild(form);
                form.submit();
            }

            function showPosition(position) {
                //===================================================================================
                // to też musisz zmienić, bo jest z palca wpisane 'Ortopeda'
                // ~Radek
                //===================================================================================
                findClosest("Ortopeda", position.coords.latitude, position.coords.longitude);


                //===================================================================================
                // Demonstracja pobierania adresu na podstawie lokalizacji
                // Olaf, odkomentuj to, żeby zobaczyć jak działa
                // ~Radek
                //===================================================================================
                // var url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+position.coords.latitude+","+position.coords.longitude+"&sensor=true"
                // var degeocodePromise = $http.get(url);
                // degeocodePromise.then(function (response)
                // {
                //     alert(
                //     response.data.results[0].address_components[0].long_name + '\n' +
                //     response.data.results[0].address_components[1].long_name + '\n' +
                //     response.data.results[0].address_components[2].long_name + '\n' +
                //     response.data.results[0].address_components[3].long_name + '\n' +
                //     response.data.results[0].address_components[4].long_name + '\n' +
                //     response.data.results[0].address_components[5].long_name + '\n' +
                //     response.data.results[0].address_components[6].long_name);
                // })
            }

        }
})();

