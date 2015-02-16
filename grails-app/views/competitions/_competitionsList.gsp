<div class="box-body table-responsive no-padding">
    <table class="table table-bordered table-hover dataTable table-cell-horizontal-center table-cell-vertical-middle">
        <tr>
            <th>
                <g:message code="form.field.competition.name"/>
            </th>
            <th>
                <g:message code="form.field.country.name"/>
            </th>
            <th>
                <g:message code="form.field.events.counted"/>
            </th>
        </tr>

        <g:each in="${competitions}" var="competition">
            <tr>
                <td>
                    ${competition?.competitionName}
                </td>
                <td>
                    ${competition?.country?.getCountryName()}
                </td>
                <td>
                    <label class="label label-info">
                        ${competition?.events?.size()}
                    </label>
                </td>
            </tr>
        </g:each>
    </table>
</div>