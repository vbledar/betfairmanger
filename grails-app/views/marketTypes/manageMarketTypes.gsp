<%--
  Created by IntelliJ IDEA.
  User: vbledar
  Date: 2/11/15
  Time: 19:57
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="market.types.management.title"/>
    </title>
</head>

<body>

<div class="row">
    <section class="col-xs-12">
        <div id="marketTypesBoxContainer" class="box box-success">

            <div class="box-header">
                <i class="fa fa-paw"></i>

                <h3 class="box-title">
                    <g:message code="market.types.management.title"/>
                </h3>

                <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
                    <div class="btn-group" data-toggle="btn-toggle">
                        <g:link elementId="retrieveMarketTypesFromBetFair"
                                controller="marketTypes"
                                action="retrieveBetfairMarketTypes"
                                div-to-update="persistedMarketTypes"
                                div-loading-state="marketTypesBoxContainer"
                                class="btn btn-info">
                            <span class="glyphicon glyphicon-download"></span> <g:message
                                code="market.types.management.button.retrieve.market.types.betfair"/>
                        </g:link>
                    </div>
                </div>
            </div>

            <div class="box-body">
                <div id="persistedMarketTypes" class="peristed-market-types">
                    <g:render template="persistedMarketTypes" model="[marketTypes: marketTypes]"/>
                </div>
            </div>
        </div>
    </section>
</div>

<g:javascript>

    function updateCheckboxesLayout() {
        console.log("I'm running!");
        $('.peristed-market-types').iCheck();
    }

    $('#retrieveMarketTypesFromBetFair').off('click').on('click', function (event) {
        event.preventDefault();

        var url = $(this).attr('href');
        var divToUpdate = $(this).attr('div-to-update');
        var divToLoading = $(this).attr('div-loading-state');

        ajaxCallToServer(url, divToUpdate, divToLoading);
    });

</g:javascript>

</body>
</html>