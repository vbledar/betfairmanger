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
                    <div class="checkbox no-margin-on-checkbox">
                        <label style="padding-left: 0px">
                            <g:if test="${competition?.automaticRetrieval}">
                                <input name="automaticRetrieval" type="checkbox" checked style="position: absolute; opacity: 0;">
                            </g:if>
                            <g:else>
                                <input name="automaticRetrieval" type="checkbox" style="position: absolute; opacity: 0;">
                            </g:else>
                            <ins class="iCheck-helper" competition-id="${competition?.competitionId}" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins>
                        </label>
                    </div>
                </td>
            </tr>
        </g:each>
    </table>
</div>

<script type="application/javascript">

    $('.iCheck-helper').off('click').on('click', function(event) {
        event.preventDefault();

        addLoadingStateInElement('competitionsBoxContainer');

        var competitionId = $(this).attr('competition-id');
        var parameters = {}
        parameters.competitionId = competitionId;

        var url = "${createLink(controller: 'competitions', action: 'setCompetitionAutomaticRetrieval')}";
        $.post(url, parameters, function (data) {
            console.log("POST executed");
        }).done(function (data) {
            removeLoadingStateFromElement('competitionsBoxContainer');

            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            showSuccessMessage(data.message);
        }).fail(function (data) {
            removeLoadingStateFromElement('competitionsBoxContainer');
            showErrorMessage(data);
        });
    });

</script>