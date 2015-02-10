<%--
  Created by IntelliJ IDEA.
  User: vbledar
  Date: 2/10/15
  Time: 23:26
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        <g:message code="errors.management.title"/>
    </title>
</head>

<body>

<div class="table-responsive">

    <table class="table">
        <tr>
            <th>
                <g:message code="form.field.record.id"/>
            </th>
            <th>
                <g:message code="form.field.error.code"/>
            </th>
            <th>
                <g:message code="form.field.error.message"/>
            </th>
            <th>
                <g:message code="form.field.exception.error.code"/>
            </th>
            <th>
                <g:message code="form.field.exception.error.details"/>
            </th>
            <th>
                <g:message code="form.field.exception.request.uuid"/>
            </th>
            <th>
                <g:message code="form.field.exception.error.name"/>
            </th>
        </tr>

        <g:each in="${errors}" var="error">
            <tr>
                <td>
                    ${error?.id}
                </td>
                <td>
                    ${error?.code}
                </td>
                <td>
                    ${error?.message}
                </td>
                <td>
                    ${error?.betfairException?.errorCode}
                </td>
                <td>
                    ${error?.betfairException?.errorDetails}
                </td>
                <td>
                    ${error?.betfairException?.requestUUID}
                </td>
                <td>
                    ${error?.betfairException?.exceptionName}
                </td>
            </tr>
        </g:each>
    </table>
</div>

</body>
</html>