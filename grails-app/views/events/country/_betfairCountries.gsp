<div class="page-header">
    <h5>
        <g:message code="countries.management.betfair.countries"/>
    </h5>
</div>

<div class="row">
    <div class="col-sm-12">
        <div class="btn-group" role="group">
            <g:link elementId="retrieveBetfairCountries" controller="events" action="retrieveBetfairCountries"
                    class="btn btn-info">
                <span class="glyphicon glyphicon-download"></span> <g:message
                    code="countries.management.button.retrieve.countries.betfair"/>
            </g:link>
            <g:link elementId="persistAllAccounts" controller="events" action="persistBetfairCountries"
                    class="btn btn-info">
                <span class="glyphicon glyphicon-cloud-upload"></span> <g:message
                    code="countries.management.button.persist.betfair.countries"/>
            </g:link>
        </div>
    </div>
</div>

<br/>

<div id="betfairCountriesContainer">

</div>


<script type="application/javascript">

    $(function () {
        $('#retrieveBetfairCountries').off('click').on('click', function (event) {
            event.preventDefault();

            var url = $(this).attr('href')
            $.post(url, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                console.log("POST success");
                if (data.success === false) {
                    console.log("POST failed");
                    console.log("Server message is: " + data.message)
                }

                console.log("POST server response successful");
                console.log(data);
                $('#betfairCountriesContainer').html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
            });
        });

        $('#persistAllAccounts').off('click').on('click', function (event) {
            event.preventDefault();

            var url = $(this).attr('href')
            $.post(url, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                console.log("POST success");
                if (data.success === false) {
                    console.log("POST failed");
                    console.log("Server message is: " + data.message)
                }

                console.log("POST server response successful");
                console.log(data);
                $('#persistedCountriesContainer').html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
            })
        });
    });

</script>