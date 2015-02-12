<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        <g:message code="application.title"/>
    </title>

</head>

<body>

<g:render template="/mainFilters" model="[countries: countries]"/>

<br />

<div class="clearfix"></div>

<div id="mainInformationPanel">
    <div class="alert alert-info">
        <g:message code="user.message.please.select.country"/>
    </div>
</div>

</body>
</html>
