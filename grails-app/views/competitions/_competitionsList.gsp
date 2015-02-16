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
            <th>
                <g:message code="form.field.retrieve.automatically"/>
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
                        ${competition?.eventsCounter ? competition?.eventsCounter : 0}
                    </label>
                </td>
                <td>
                    <label class="label selectable-row ${competition?.automaticRetrieval ? 'label-success' : 'label-danger'} update-competition-auto-state"
                           competition-id="${competition?.competitionId}"
                           div-to-loading="competitionsBoxContainer">
                        ${competition?.automaticRetrieval ? 'Enabled' : 'Disabled'}
                    </label>
                </td>
            </tr>
        </g:each>
    </table>
</div>

<script type="application/javascript">

    $('.update-competition-auto-state').off('click').on('click', function(event) {
        event.preventDefault();

        var element = $(this);
        var divToLoading = $(this).attr('div-to-loading');
        var wasEnabled = $(this).hasClass('label-success');

        addLoadingStateInElement(divToLoading);

        var competitionId = $(this).attr('competition-id');
        var parameters = {}
        parameters.competitionId = competitionId;

        var url = "${createLink(controller: 'competitions', action: 'setCompetitionAutomaticRetrieval')}";
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