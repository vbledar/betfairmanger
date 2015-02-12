<div class="panel panel-default table-responsive">
    <table class="table table-cell-horizontal-center table-cell-vertical-middle">
        <thead>
        <th>
            <g:message code="form.field.country.code"/>
        </th>
        <th>
            <g:message code="form.field.country.iso.three.letter"/>
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
                    ${country?.countryInformation?.iso3LetterCode}
                </td>
                <td>
                    ${country?.getCountryName()}
                </td>
                <td class="text-right">
                    <div class="btn-group">
                        <g:link controller="competitions" action="manageCompetitions" id="${country?.countryCode}" class="btn btn-primary">
                            <span class="glyphicon glyphicon-eye-open"></span>
                        </g:link>
                        <g:link controller="countries" action="deleteCountry" id="${country?.countryCode}"
                                class="btn btn-danger">
                            <span class="glyphicon glyphicon-remove"></span>
                        </g:link>
                    </div>
                </td>
            </tr>
        </g:each>
    </table>
</div>