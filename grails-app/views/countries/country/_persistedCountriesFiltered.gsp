<%@ page import="com.tipster.betfair.Country" %>
<%@ page import="com.tipster.betfair.CountryInformation" %>


<div class="text-center">
    <util:remotePaginate controller="countries" action="manageCountriesFiltered" total="${Country.count()}"
                         update="persistedCountriesList"
                         max="20"/>

    <div class="btn-group pull-right" role="group" style="margin: 20px 0px !important;">
        <g:link elementId="retrieveBetfairCountries" controller="countries" action="retrieveBetfairCountries"
                class="btn btn-info">
            <span class="glyphicon glyphicon-download"></span> <g:message
                code="countries.management.button.synchronize.countries.betfair"/>
        </g:link>
    </div>

</div>

<g:render template="country/countriesList" model="[countries: countries, showActions: Boolean.TRUE]"/>

<div class="text-center">
    <util:remotePaginate controller="countries" action="manageCountriesFiltered" total="${Country.count()}"
                         update="persistedCountriesList"
                         max="20"/>
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
                    console.log("Server message is: " + data.message);
                    showErrorMessage(data.message);
                    return;
                }

                console.log("POST server response successful");
                showSuccessMessage("Action completed successfully.");
                console.log(data);
                $('#persistedCountriesList').html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
                showErrorMessage(data);
            });
        });
    });
</script>