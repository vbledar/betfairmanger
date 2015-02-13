<div class="panel panel-default table-responsive">
    <div class="panel-heading">
        <div class="panel-title">
            <g:message code="markets.management.runners.list.title"/>
        </div>
    </div>
    <table id="runnersTable" class="table table-cell-horizontal-center table-cell-vertical-middle">

        <tr>
            <th class="column-to-hide">
                <g:message code="form.field.runner.name"/>
            </th>
            <th>
                <g:message code="form.field.runner.odd"/>
            </th>
        </tr>

        <g:each in="${runners}" var="runner">
            <tr>
                <td>
                    ${runner?.runnerName}
                </td>
                <td>
                    ${1.6}
                </td>
            </tr>
        </g:each>
    </table>
</div>