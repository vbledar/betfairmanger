<%--
  Created by IntelliJ IDEA.
  User: Bledar
  Date: 2/9/2015
  Time: 10:02 PM
--%>

<html>
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="countries.management.title"/>
    </title>
</head>

<body>

<div class="row">
    <section class="col-xs-12">
        <div id="countriesBoxContainer" class="box box-success">
            <div id="persistedCompetitionsOverlay"></div>
            <div id="persistedCompetitionsLoading"></div>

            <div class="box-header">
                <i class="fa fa-paw"></i>

                <h3 class="box-title">
                    <g:message code="countries.management.title"/>
                </h3>

                <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
                    <div class="btn-group" data-toggle="btn-toggle">
                        <g:link elementId="retrieveBetfairCountries"
                                controller="countries"
                                action="retrieveBetfairCountries"
                                div-to-update="persistedAccounts"
                                class="btn btn-info">
                            <span class="glyphicon glyphicon-download"></span> <g:message
                                code="countries.management.button.synchronize.countries.betfair"/>
                        </g:link>
                    </div>
                </div>
            </div>

            <div class="box-body">
                <div id="persistedAccounts">
                    <g:render template="country/persistedCountries" model="[countries: countries]"/>
                </div>
            </div>
        </div>
    </section>
</div>

<g:javascript>

    $('#retrieveBetfairCountries').off('click').on('click', function (event) {
        event.preventDefault();
        var divToUpdate = $(this).attr('div-to-update');

        addLoadingStateInElement('countriesBoxContainer');

        var url = $(this).attr('href')
        $.post(url, function (data) {
            console.log("POST executed");
        }).done(function (data) {
            removeLoadingStateFromElement('countriesBoxContainer');

            if (data.success === false) {

                showErrorMessage(data.message);
                return;
            }

            showSuccessMessage("Action completed successfully.");
            $('#persistedCountriesList').html(data);
        }).fail(function (data) {
            removeLoadingStateFromElement('countriesBoxContainer');
            showErrorMessage(data);
        });
    });

</g:javascript>
</body>
</html>