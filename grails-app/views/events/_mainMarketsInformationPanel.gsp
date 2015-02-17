<div id="marketsBox" class="box box-success">
    <div class="box-header">
        <i class="fa fa-soccer-ball-o"></i>

        <h3 class="box-title">
            <g:message code="markets.management.title"/>
        </h3>

        <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
            <div class="btn-group" data-toggle="btn-toggle">
                <g:link elementId="synchronizeMarketsFromBetfair"
                        controller="events"
                        action="synchronizeMarketsFromBetfair"
                        event-id="${event?.id}"
                        class="btn btn-info"
                        div-to-update="persistedMarkets"
                        div-to-loading="marketsBox">
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

            var divToUpdate = $(this).attr('div-to-update');
            var divToLoading = $(this).attr('div-to-loading');

            addLoadingStateInElement(divToLoading);

            var eventId = $(this).attr('event-id');
            var parameters = {};
            parameters.eventId = eventId;

            var url = $(this).attr('href')
            $.post(url, parameters, function (data) {
            }).done(function (data) {
                removeLoadingStateFromElement(divToLoading);
                if (data.success === false) {
                    showErrorMessage(data.message);
                    return;
                }

                $('#persistedMarkets').html(data);
            }).fail(function (data) {
                removeLoadingStateFromElement(divToLoading);
                showErrorMessage(data);
            });
        });
    });
</script>