<div class="col-xs-12 col-sm-8 col-md-9">

    <div class="page-header">
        <h4>
            <g:message code="events.management.title"/>
        </h4>

        <div class="btn-group btn-group-sm" role="group">
            <g:link elementId="retrieveEventsFromBetfair" controller="events"
                    action="synchronizeEventsFromBetfair" competition-id="${competition?.competitionId}"
                    class="btn btn-info">
                <span class="glyphicon glyphicon-download"></span> <g:message
                    code="events.management.button.retrieve.events.betfair"/>
            </g:link>
        </div>
    </div>

    <div id="persistedEvents">
        <g:render template="/events/persistedEvents" model="[eventsList: eventsList]"/>
    </div>
</div>

<script type="application/javascript">

    $(function () {
        $('#retrieveEventsFromBetfair').off('click').on('click', function (event) {
            event.preventDefault();

            var competitionId = $(this).attr('competition-id');
            var parameters = {};
            parameters.competitionId = competitionId;

            var url = $(this).attr('href')
            $.post(url, parameters, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                console.log("POST success");
                if (data.success === false) {
                    console.log("POST failed");
                    console.log("Server message is: " + data.message)
                    showErrorMessage(data.message);
                    return;
                }

                console.log("POST server response successful");
                console.log(data);
                showSuccessMessage("Events were synchronized successfully from BetFair.")
                $('#persistedEvents').html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
                showErrorMessage(data);
            });
        });
    });
</script>