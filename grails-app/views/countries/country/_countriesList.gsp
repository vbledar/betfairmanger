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
                <td>
                    <label class="label selectable-row ${country?.automaticRetrieval ? 'label-success' : 'label-danger'} update-country-auto-state"
                           country-code-name="${country?.countryCode}"
                           div-to-loading="countriesBoxContainer">
                        ${country?.automaticRetrieval ? 'Enabled' : 'Disabled'}
                    </label>
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
    $('.update-country-auto-state').off('click').on('click', function(event) {
        event.preventDefault();

        var element = $(this);
        var divToLoading = $(this).attr('div-to-loading');
        var wasEnabled = $(this).hasClass('label-success');

        addLoadingStateInElement(divToLoading);

        var countryCode = $(this).attr('country-code-name');
        var parameters = {}
        parameters.countryCode = countryCode;

        var url = "${createLink(controller: 'countries', action: 'setCountryAutomaticRetrieval')}";
        $.post(url, parameters, function (data) {
            console.log("POST executed");
        }).done(function (data) {
            removeLoadingStateFromElement(divToLoading);

            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            if (wasEnabled) {
                $(element).removeClass('label-success').addClass('label-danger');
                $(element).text('Disabled');
            }
            else {
                $(element).removeClass('label-danger').addClass('label-success');
                $(element).text('Enabled');
            }

            showSuccessMessage(data.message);
        }).fail(function (data) {
            removeLoadingStateFromElement(divToLoading);
            showErrorMessage(data);
        });
    });

</script>