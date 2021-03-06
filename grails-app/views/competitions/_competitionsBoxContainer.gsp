<div id="competitionsBox" class="box box-success">
    <div class="box-header">
        <i class="fa fa-paw"></i>

        <h3 class="box-title">
            <g:message code="competitions.management.title"/>
        </h3>

        <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
            <div class="btn-group" data-toggle="btn-toggle">
                <g:link elementId="retrieveCompetitionsFromBetFair" controller="competitions"
                        action="retrieveBetfairCompetitions"
                        params="[countryCode: params.countryCode]"
                        div-to-update="persistedCompetitions"
                        div-to-loading="competitionsBox"
                        class="btn btn-info">
                    <span class="glyphicon glyphicon-download"></span> <g:message
                        code="competitions.management.button.retrieve.competitions.betfair"/>
                </g:link>
            </div>
        </div>
    </div>

    <div class="box-body">
        <div id="persistedCompetitions">
            <g:render template="persistedCompetitions" model="[competitions: competitions]"/>
        </div>
    </div>
</div>

<div id="eventsBoxContainer" class="box box-success">

</div>

<g:javascript>

    $(function () {
        $('#retrieveCompetitionsFromBetFair').off('click').on('click', function (event) {
            event.preventDefault();
            var divToUpdate = $(this).attr('div-to-update');
            var divToLoading = $(this).attr('div-to-loading');

            addLoadingStateInElement(divToLoading);

            var url = $(this).attr('href')
            $.post(url, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                removeLoadingStateFromElement(divToLoading);

                if (data.success === false) {
                    showErrorMessage(data.message);
                    return;
                }

                showSuccessMessage("Competitions were retrieved successfully from BetFair.")
                $('#' + divToUpdate).html(data);
            }).fail(function (data) {
                removeLoadingStateFromElement(divToLoading);
                showErrorMessage(data);
            });
        });
    });

</g:javascript>