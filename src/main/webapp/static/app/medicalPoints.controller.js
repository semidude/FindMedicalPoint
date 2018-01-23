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

            var url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+position.coords.latitude+","+position.coords.longitude+"&sensor=true"
            var degeocodePromise = $http.get(url);
            degeocodePromise.then(function (response)
            {
                vm.number = response.data.results[0].address_components[0].long_name;
                vm.street = response.data.results[0].address_components[1].long_name;
                vm.city = response.data.results[0].address_components[3].long_name;
            })

            findClosest("Ortopeda", position.coords.latitude, position.coords.longitude);
        }

    }
})();