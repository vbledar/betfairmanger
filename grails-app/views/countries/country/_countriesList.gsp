<div class="box-body table-responsive no-padding">
    <table class="table table-bordered table-hover dataTable table-cell-horizontal-center table-cell-vertical-middle">
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
        <th>
            <g:message code="form.field.competitions.counted"/>
        </th>
        <th>
            <g:message code="form.field.retrieve.automatically"/>
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
                <td>
                    <label class="label label-info">
                        ${country?.getCompetitionsCounted()}
                    </label>
                </td>
                <td class="">
                    <div class="checkbox no-margin-on-checkbox">
                        <label style="padding-left: 0px">
                            <g:if test="${country?.automaticRetrieval}">
                                <input id="autoOnCountry${country?.countryCode}" name="automaticRetrieval" type="checkbox" checked style="position: absolute; opacity: 0;">
                            </g:if>
                            <g:else>
                                <input id="autoOnCountry${country?.countryCode}" name="automaticRetrieval" type="checkbox" style="position: absolute; opacity: 0;">
                            </g:else>
                            <ins class="iCheck-helper" country-id="${country?.countryCode}" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins>
                        </label>
                    </div>
                </td>
                <td class="text-right">
                    <div class="btn-group">
                        <g:link controller="competitions" action="manageCompetitions" params="[countryCode: country?.countryCode]" class="btn btn-sm btn-flat btn-primary">
                            <span class="fa fa-paw"></span>
                        </g:link>
                        <g:link controller="countries" action="deleteCountry" id="${country?.countryCode}"
                                class="btn btn-sm btn-flat btn-danger">
                            <span class="glyphicon glyphicon-remove"></span>
                        </g:link>
                    </div>
                </td>
            </tr>
        </g:each>
    </table>
</div>

<script type="application/javascript">

    console.log("I'm running!");
    $('.iCheck-helper').off('click').on('click', function(event) {
        event.preventDefault();

        var countryCode = $(this).attr('country-id');
        var parameters = {}
        parameters.countryCode = countryCode;

        var url = "${createLink(controller: 'countries', action: 'setCountryAutomaticRetrieval')}";
        $.post(url, parameters, function (data) {
            console.log("POST executed");
        }).done(function (data) {
            removeLoadingStateFromElement('countriesBoxContainer');

            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            showSuccessMessage(data.message);
        }).fail(function (data) {
            removeLoadingStateFromElement('countriesBoxContainer');
            showErrorMessage(data);
        });
    });

</script>