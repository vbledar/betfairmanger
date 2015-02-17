<div class="row">
    <div class="col-xs-12 col-sm-6 col-md-6">

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
                    <tr id="marketRow${market?.marketId}"
                        class="selectable-market-row selectable-row"
                        market-id="${market?.marketId}"
                        div-to-update="runnersInformationPanel"
                        div-to-loading="marketsBox">
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

    <div id="runnersInformationPanel" class="col-xs-12 col-sm-6 col-md-6">

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

            var divToUpdate = $(this).attr('div-to-update');
            var divToLoading = $(this).attr('div-to-loading');

            var marketId = $(this).attr('market-id');
            loadMarketRunners(marketId, divToUpdate, divToLoading)
        })
    });

    function loadMarketRunners(marketId, divToUpdate, divToLoading) {
        var parameters = {};
        parameters.marketId = marketId;

        addLoadingStateInElement(divToLoading);

        var url = "${createLink(controller: 'events', action: 'renderMarketRunners')}";
        $.post(url, parameters, function (data) {

        }).done(function (data) {
            removeLoadingStateFromElement(divToLoading);

            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            $('#'+divToUpdate).html(data);
        }).fail(function (data) {
            removeLoadingStateFromElement(divToLoading);
            showErrorMessage(data);
        });
    }

</g:javascript>