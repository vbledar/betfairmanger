<%--
  Created by IntelliJ IDEA.
  User: vbledar
  Date: 2/11/15
  Time: 0:44
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="competitions.management.title"/>
    </title>
</head>

<body>

<div class="row">
    <section class="col-xs-12 col-sm-3 col-md-2">
        <div id="countriesFiltersContainer" class="box box-success">
            <div class="box-header">
                <i class="fa fa-flag"></i>

                <h3 class="box-title">
                    <g:message code="countries.management.filter.by.country"/>
                </h3>
            </div>

            <div class="box-body hidden-xs">
                <ul class="list-group" style="max-height: 500px; overflow-y: scroll">
                    <g:each in="${session["countries"]}" var="country">
                        <li div-to-update="competitionsBoxContainer"
                            div-to-loading="competitionsBox"
                            class="list-group-item selectable-row competition-filter-by-company ${params.countryCode && params.countryCode?.equalsIgnoreCase(country?.countryCode) ? 'active' : ''}" country-code="${country?.countryCode}">
                            <span class="badge">
                                ${country?.competitionsCounter ? country?.competitionsCounter : 0}
                            </span>
                            ${country?.getCountryName()}
                        </li>
                    </g:each>
                </ul>
            </div>
            <div class="box-body visible-xs">
                <g:select id="countrySelection"
                          class="form-control input-sm"
                          name="country"
                          from="${session["countries"]}"
                          optionKey="countryCode"
                          optionValue="countryName"
                          noSelection="['': 'Please select']"
                          value="${selectedCountry?.countryCode}"
                          div-to-update="competitionsBoxContainer"
                          div-to-loading="competitionsBoxContainer"
                />
            </div>
        </div>
    </section>
    <section class="col-xs-12 col-sm-9 col-md-10">
        <div id="competitionsBoxContainer">
            <g:render template="competitionsBoxContainer" model="[competitions: competitions]"/>
        </div>
    </section>
</div>


<script type="application/javascript">

    $(function () {
        $('#countrySelection').change(function() {
            var divToUpdate = $(this).attr('div-to-update');
            var divToLoading = $(this).attr('div-to-loading');

            var countryCode = $('#countrySelection option:selected').val();

            filterCompetitionByCountry(countryCode, divToUpdate, divToLoading);
        });

        $('.competition-filter-by-company').off('click').on('click', function(event) {
            event.preventDefault();

            $('.competition-filter-by-company').removeClass('active');
            $(this).addClass('active');

            var divToUpdate = $(this).attr('div-to-update');
            var divToLoading = $(this).attr('div-to-loading');

            var countryCode = $(this).attr('country-code');
            filterCompetitionByCountry(countryCode, divToUpdate, divToLoading);
        })

        function filterCompetitionByCountry(countryCode, divToUpdate, divToLoading) {
            var parameters = {};
            parameters.countryCode = countryCode;

            addLoadingStateInElement(divToLoading);

            var url = "${createLink(controller: 'competitions', action: 'manageCompetitionsBoxContainer')}";
            $.post(url, parameters, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                removeLoadingStateFromElement(divToLoading);

                if (data.success === false) {
                    showErrorMessage(data.message);
                    return;
                }

                $('#'+divToUpdate).html(data);
            }).fail(function (data) {
                removeLoadingStateFromElement(divToLoading);
                showErrorMessage(data);
            });
        }
    });
</script>
</body>
</html>