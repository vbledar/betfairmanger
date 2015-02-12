<%--
  Created by IntelliJ IDEA.
  User: Bledar
  Date: 2/9/2015
  Time: 10:02 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="countries.management.title"/>
    </title>
</head>

<body>

<div class="page-header">
    <h4>
        <g:message code="accounts.management.title"/>
    </h4>
</div>


<div id="persistedAccounts">
    <g:render template="country/persistedCountries" model="[countries: countries]"/>
</div>

</body>
</html>