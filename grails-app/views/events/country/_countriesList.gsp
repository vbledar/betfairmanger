<div class="panel panel-default table-responsive">
    <table class="table table-cell-horizontal-center table-cell-vertical-middle">
        <thead>
        <th>
            <g:message code="form.field.country.code"/>
        </th>
        <th>
            <g:message code="form.field.country.name"/>
        </th>
        <g:if test="${showActions}">
            <th>

            </th>
        </g:if>
        </thead>

        <g:each in="${countries}" var="country">
            <tr id="accountRow${country?.countryCode}">
                <td>
                    ${country?.countryCode}
                </td>
                <td>
                    ${country?.getCountryName()}
                </td>
                <g:if test="${showActions}">
                    <th>
                        <div class="btn-group">
                            <g:link controller="events" action="deleteCountry" id="${country?.countryCode}"
                                    class="btn btn-danger">
                                <span class="glyphicon glyphicon-remove"></span>
                            </g:link>
                        </div>
                    </th>
                </g:if>
            </tr>
        </g:each>
    </table>
</div>