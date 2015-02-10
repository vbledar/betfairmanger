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

<div class="page-header">
    <h4>
        <g:message code="accounts.management.title"/>
    </h4>
</div>

<div id="retrievedAccounts">
    <g:render template="betfairAccounts"/>
</div>

<div id="persistedAccounts">
    <g:render template="persistedAccounts" model="[developerApps: developerApps]"/>
</div>

</body>
</html>