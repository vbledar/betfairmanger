<div class="row">
    <div class="col-xs-6 col-sm-6 col-md-6">
        <div class="panel panel-default table-responsive">
            <table id="marketsTable" class="table table-cell-horizontal-center table-cell-vertical-middle">

                <tr>
                    <th class="column-to-hide">
                        <g:message code="form.field.market.id"/>
                    </th>
                    <th>
                        <g:message code="form.field.market.name"/>
                    </th>
                </tr>

                <g:each in="${markets}" var="market">
                    <tr id="marketRow${market?.marketId}" class="selectable-market-row selectable-row"
                        market-id="${market?.marketId}">
                        <td class="column-to-hide">
                            ${market?.marketId}
                        </td>
                        <td>
                            ${market?.marketName}
                        </td>
                    </tr>
                </g:each>
            </table>
        </div>
    </div>

    <div id="runnersInformationPanel" class="col-xs-6 col-sm-6 col-md-6">

        <div class="alert alert-warning">
            <g:message code="user.message.please.select.market"/>
        </div>

    </div>
</div>

<g:javascript>

    var marketSelected = false;

    $(function() {
        $('.selectable-market-row').off('click').on('click', function(event) {
            $('.selectable-market-row').removeClass('warning');
            $(this).addClass('warning');

            var marketId = $(this).attr('market-id');
            $('#runnersInformationPanel').fadeOut();
            loadMarketRunners(marketId)
        })
    });

    function loadMarketRunners(marketId) {
        var parameters = {};
        parameters.marketId = marketId;

        var url = "${createLink(controller: 'events', action: 'renderMarketRunners')}";
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
            showSuccessMessage("Action completed successfully.");
            $('#runnersInformationPanel').html(data);
            $('#runnersInformationPanel').fadeIn();
        }).fail(function (data) {
            console.log("POST failed.");
            console.log(data);
            showErrorMessage(data);
        });
    }

</g:javascript>