<!DOCTYPE html>
<html>
<body>

<p>Click the button to get your coordinates.</p>

<button onclick="getLocation()">Find me!</button>

<p id="demo"></p>

<script>
    var x = document.getElementById("demo");

    then getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            x.innerHTML = "Geolocation is not supported by this browser.";
        }
    }

    then post(path, params, method) {
        method = method || "post"; // Set method to post by default if not specified.

        var form = document.createElement("form");
        form.setAttribute("method", method);
        form.setAttribute("then", path);

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

    then showPosition(position) {
        post('/showloc', {latitude: position.coords.latitude, longitude: position.coords.longitude});
    }
</script>

</body>
</html>
