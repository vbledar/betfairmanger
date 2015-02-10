<%--
  Created by IntelliJ IDEA.
  User: vbledar
  Date: 2/11/15
  Time: 0:44
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="competitions.management.title"/>
    </title>
</head>

<body>

<div class="page-header">
    <h4>
        <g:message code="competitions.management.title"/>
    </h4>
</div>

<div class="btn-group" role="group">
    <g:link elementId="retrieveCompetitionsFromBetFair" controller="competitions" action="retrieveBetfairCompetitions"
            class="btn btn-info">
        <span class="glyphicon glyphicon-download"></span> <g:message
            code="competitions.management.button.retrieve.competitions.betfair"/>
    </g:link>
</div>

<br />

<div id="persistedCompetitions">
    <g:render template="persistedCompetitions" model="[competitions: competitions]"/>
</div>

<script type="application/javascript">

    $(function () {
        $('#retrieveCompetitionsFromBetFair').off('click').on('click', function (event) {
            event.preventDefault();

            var url = $(this).attr('href')
            $.post(url, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                console.log("POST success");
                if (data.success === false) {
                    console.log("POST failed");
                    console.log("Server message is: " + data.message)
                    showErrorMessage(data.message);
                    return;
                }

                console.log("POST server response successful");
                console.log(data);
                showSuccessMessage("Competitions were retrieved successfully from BetFair.")
                $('#persistedCompetitions').html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
                showErrorMessage(data);
            });
        });
    });
</script>
</body>
</html>