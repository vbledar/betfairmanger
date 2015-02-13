<div class="panel panel-default table-responsive">
    <table class="table table-cell-horizontal-center table-cell-vertical-middle">

        <tr>
            <th>
                <g:message code="form.field.market.type.name"/>
            </th>
            <th>
                <g:message code="form.field.market.type.active"/>
            </th>
        </tr>

        <g:each in="${marketTypes}" var="marketType">
            <tr>
                <td>
                    ${marketType?.name}
                </td>
                <td>

                </td>
            </tr>
        </g:each>
    </table>
</div>