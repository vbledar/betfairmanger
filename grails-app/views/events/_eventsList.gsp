<div class="panel panel-default table-responsive">
    <table class="table table-cell-horizontal-center table-cell-vertical-middle">

        <tr>
            <th>
                <g:message code="form.field.event.id"/>
            </th>
            <th>
                <g:message code="form.field.event.name"/>
            </th>
            <th>
                <g:message code="form.field.country.name"/>
            </th>
            <th>
                <g:message code="form.field.competition.name"/>
            </th>
            <th>
                <g:message code="form.field.event.timezone"/>
            </th>
            <th>
                <g:message code="form.field.event.open.date"/>
            </th>
        </tr>

        <g:each in="${eventsList.sort {it.openDate}}" var="eventInformation">
            <tr id="eventRow${eventInformation?.id}" class="selectable-row show-markets-for-event" event-id="${eventInformation?.id}">
                <td>
                    ${eventInformation?.id}
                </td>
                <td>
                    ${eventInformation?.name}
                </td>
                <td>
                    ${eventInformation?.country?.getCountryName()}
                </td>
                <td>
                    ${eventInformation?.competition?.competitionName}
                </td>
                <td>
                    ${eventInformation?.timezone}
                </td>
                <td>
                    <g:formatDate date="${eventInformation?.openDate}" format="dd/MM/yyyy HH:mm:ss z"/>
                </td>
            </tr>
        </g:each>
    </table>
</div>

<g:javascript>

    var competitionSelected = false;
    $(function () {

        $('.show-markets-for-event').off('click').on('click', function (event) {
            event.preventDefault();

            var selectedRowId = $(this).attr('id');
            var eventId = $(this).attr('event-id');
            if (competitionSelected === false) {
                competitionSelected = true;

                $('.show-markets-for-event').removeClass("warning");
                $('.show-markets-for-event').fadeToggle("slow", "linear");
                $('#' + selectedRowId).fadeToggle();
                $('#' + selectedRowId).addClass("warning");

                $('#marketsContainer').fadeOut();
                $('#marketsContainer').html("");

                loadEventMarkets(eventId);
            } else {
                competitionSelected = false;
                $('.show-markets-for-event').removeClass("warning");
                $('.show-markets-for-event').each(function () {
                    var currentRowId = $(this).attr('id')
                    if (currentRowId != selectedRowId) {
                        $(this).fadeToggle();
                    }
                });
                $('#marketsContainer').fadeOut();
                $('#marketsContainer').html("");
            }

        });
    });

    function loadEventMarkets(eventId) {
        var parameters = {};
        parameters.eventId = eventId;

        var url = "${createLink(controller: 'events', action: 'manageEventMarkets')}";
        $.post(url, parameters, function (data) {
        }).done(function (data) {
            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            $('#marketsContainer').html(data);
            $('#marketsContainer').fadeIn();
        }).fail(function (data) {
            showErrorMessage(data);
        });
    }

</g:javascript>