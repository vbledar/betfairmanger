<div id="marketsBox" class="box box-success">
    <div class="box-header">
        <i class="fa fa-soccer-ball-o"></i>

        <h3 class="box-title">
            <g:message code="markets.management.title"/>
        </h3>

        <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
            <div class="btn-group" data-toggle="btn-toggle">
                <g:link elementId="synchronizeMarketsFromBetfair" controller="events"
                        action="synchronizeMarketsFromBetfair" event-id="${event?.id}"
                        class="btn btn-info">
                    <span class="glyphicon glyphicon-download"></span> <g:message
                        code="markets.management.button.retrieve.markets.betfair"/>
                </g:link>
            </div>
        </div>
    </div>

    <div class="box-body">
        <div id="persistedMarkets">
            <g:render template="persistedMarkets" model="[markets: markets]"/>
        </div>
    </div>
</div>

%{--</div>--}%

<script type="application/javascript">

    $(function () {
        $('#synchronizeMarketsFromBetfair').off('click').on('click', function (event) {
            event.preventDefault();

            var eventId = $(this).attr('event-id');
            var parameters = {};
            parameters.eventId = eventId;

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
                showSuccessMessage("Markets were synchronized successfully from BetFair.")
                $('#persistedMarkets').html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
                showErrorMessage(data);
            });
        });
    });
</script>