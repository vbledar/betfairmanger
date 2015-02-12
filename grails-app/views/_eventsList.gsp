<div class="panel panel-default table-responsive">
    <table class="table table-cell-horizontal-center table-cell-vertical-middle">

        <tr>
            <th>
                <g:message code="form.field.event.id"/>
            </th>
            <th>
                <g:message code="form.field.event.name"/>
            </th>
            <th>
                <g:message code="form.field.country.name"/>
            </th>
            <th>
                <g:message code="form.field.competition.name"/>
            </th>
            <th>
                <g:message code="form.field.event.timezone"/>
            </th>
            <th>
                <g:message code="form.field.event.open.date"/>
            </th>
        </tr>

        <g:each in="${eventsList.sort {it.openDate}}" var="eventInformation">
            <tr>
                <td>
                    ${eventInformation?.id}
                </td>
                <td>
                    ${eventInformation?.name}
                </td>
                <td>
                    ${eventInformation?.country?.getCountryName()}
                </td>
                <td>
                    ${eventInformation?.competition?.competitionName}
                </td>
                <td>
                    ${eventInformation?.timezone}
                </td>
                <td>
                    <g:formatDate date="${eventInformation?.openDate}" format="dd/MM/yyyy HH:mm:ss z"/>
                </td>
            </tr>
        </g:each>
    </table>
</div>