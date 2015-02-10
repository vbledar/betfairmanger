<div class="panel panel-default table-responsive">
    <table class="table table-cell-horizontal-center table-cell-vertical-middle">

        <tr>
            <th>
                <g:message code="form.field.competition.id"/>
            </th>
            <th>
                <g:message code="form.field.competition.name"/>
            </th>
            <th>
                <g:message code="form.field.country.name"/>
            </th>
        </tr>

        <g:each in="${competitions}" var="competition">
            <tr>
                <td>
                    ${competition?.competitionId}
                </td>
                <td>
                    ${competition?.competitionName}
                </td>
                <td>
                    ${competition?.country?.getCountryName()}
                </td>
            </tr>
        </g:each>
    </table>
</div>