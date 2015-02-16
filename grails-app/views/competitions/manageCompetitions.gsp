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
    <section class="col-xs-12">
        <div id="competitionsBoxContainer" class="box box-success">
            <div id="persistedCompetitionsOverlay"></div>
            <div id="persistedCompetitionsLoading"></div>

            <div class="box-header">
                <i class="fa fa-paw"></i>

                <h3 class="box-title">
                    <g:message code="competitions.management.title"/>
                </h3>

                <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
                    <div class="btn-group" data-toggle="btn-toggle">
                        <g:link elementId="retrieveCompetitionsFromBetFair" controller="competitions"
                                action="retrieveBetfairCompetitions"
                                div-to-update="persistedCompetitions"
                                div-to-overlay="persistedCompetitionsOverlay"
                                div-to-loading="persistedCompetitionsLoading"
                                class="btn btn-info">
                            <span class="glyphicon glyphicon-download"></span> <g:message
                                code="competitions.management.button.retrieve.competitions.betfair"/>
                        </g:link>
                    </div>
                </div>
            </div>

            <div class="box-body">
                <div id="persistedCompetitions">
                    <g:render template="persistedCompetitions" model="[competitions: competitions]"/>
                </div>
            </div>
        </div>
    </section>
</div>


<script type="application/javascript">

    $(function () {
        $('#retrieveCompetitionsFromBetFair').off('click').on('click', function (event) {
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

                showSuccessMessage("Competitions were retrieved successfully from BetFair.")
                $('#'+divToUpdate).html(data);
            }).fail(function (data) {
                $('#'+divToOverlay).removeClass('overlay');
                $('#'+divToLoading).removeClass('loading-img');
                showErrorMessage(data);
            });
        });
    });
</script>
</body>
</html>