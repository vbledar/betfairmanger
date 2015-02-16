<%--
  Created by IntelliJ IDEA.
  User: Bledar
  Date: 1/25/2015
  Time: 12:39 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="accounts.management.title"/>
    </title>
</head>

<body>

<div class="row">
    <section class="col-xs-12">
        <div class="box box-success">
            <div id="persistedCompetitionsOverlay"></div>
            <div id="persistedCompetitionsLoading"></div>

            <div class="box-header">
                <i class="fa fa-paw"></i>

                <h3 class="box-title">
                    <g:message code="accounts.management.title"/>
                </h3>

                <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
                    <div class="btn-group" data-toggle="btn-toggle">
                        <g:link elementId="retrieveBetfairAccounts"
                                controller="accounts"
                                action="retrieveBetfairAccounts"
                                div-to-update="persistedAccounts"
                                div-to-overlay="persistedAccountsOverlay"
                                div-to-loading="persistedAccountsLoading"
                                class="btn btn-flat btn-info">
                            <span class="glyphicon glyphicon-download"></span> <g:message code="accounts.management.button.retrieve.accounts.betfair"/>
                        </g:link>
                    </div>
                </div>
            </div>

            <div class="box-body">
                <div id="persistedAccounts">
                    <g:render template="persistedAccounts" model="[developerApps: developerApps]"/>
                </div>
            </div>
        </div>
    </section>
</div>

<g:javascript>
    $('#retrieveBetfairAccounts').off('click').on('click', function (event) {
        event.preventDefault();
        var divToUpdate = $(this).attr('div-to-update');
        var divToOverlay = $(this).attr('div-to-overlay');
        var divToLoading = $(this).attr('div-to-loading');

        $('#'+divToOverlay).addClass('overlay');
        $('#'+divToLoading).addClass('loading-img');

        var url = $(this).attr('href')
        $.post(url, function (data) {
            console.log("POST executed");
        }).done(function (data) {

            $('#'+divToOverlay).removeClass('overlay');
            $('#'+divToLoading).removeClass('loading-img');

            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            $('#'+divToUpdate).html(data);
            showSuccessMessage("Action completed successfully.")
        }).fail(function (data) {
            $('#'+divToOverlay).removeClass('overlay');
            $('#'+divToLoading).removeClass('loading-img');
            showErrorMessage(data);
        });
    });
</g:javascript>
</body>
</html>