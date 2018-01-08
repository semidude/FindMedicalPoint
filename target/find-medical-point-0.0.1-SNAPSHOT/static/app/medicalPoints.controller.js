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

            function getJSONP(url, success) {

                var ud = '_' + +new Date,
                    script = document.createElement('script'),
                    head = document.getElementsByTagName('head')[0]
                        || document.documentElement;

                window[ud] = function(data) {
                    head.removeChild(script);
                    success && success(data);
                };

                script.src = url.replace('callback=?', 'callback=' + ud);
                head.appendChild(script);

            }

            function findClosest(specialization, lat, lon)
            {
                var url = "/medicalPoints/findClosest?specialization="+specialization+"&lat="+lat+"&lon="+lon;
                var findmpPromise = $http.get(url);
                findmpPromise.then(function(response)
                {
                    vm.medicalPoints = response.data;
                });

                var googleUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&sensor=true"
                // var a = $http.get(googleUrl);
                // a.then(function (response)
                // {
                //     alert(response.data);
                // })

                getJSONP(googleUrl, function(data) {
                    alert(data);
                })
            }

            function findClosestByAddress(specialization, city, street, number)
            {
                var url = "/medicalPoints/findClosestByAddress?specialization="+specialization+"&address="+city+";"+street+";"+number;
                var findmpPromise = $http.get(url);
                findmpPromise.then(function(response)
                {
                    vm.medicalPoints = response.
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
                findClosest("Ortopeda", position.coords.latitude, position.coords.longitude);
                // post('/showloc', {latitude: position.coords.latitude, longitude: position.coords.longitude});
            }

        }
})();

