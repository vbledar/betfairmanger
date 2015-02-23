<div id="teamsBoxContainer" class="box box-success">
    <div class="box-header">
        <i class="fa fa-users"></i>

        <h3 class="box-title">
            <g:message code="teams.management.title" args="[competition?.competitionName]"/>
        </h3>

        <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
            <div class="btn-group" data-toggle="btn-toggle">
                <g:link elementId="retrieveTeamsFromWikipedia"
                        controller="competitions"
                        action="retrieveTeamsFromBetfair"
                        id="${competition?.competitionId}"
                        div-to-update="persistedTeams"
                        div-to-loading="eventsBoxContainer"
                        class="btn btn-info">
                    <span class="glyphicon glyphicon-download"></span> <g:message
                        code="teams.management.button.retrieve.teams.wikipedia"/>
                </g:link>
            </div>
        </div>
    </div>

    <div class="box-body">
        <div id="persistedTeams">
            <g:render template="team/peristedTeams" model="[competition: competition]"/>
        </div>
    </div>
</div>

<script type="application/javascript">

    $(function () {
        $('#retrieveTeamsFromWikipedia').off('click').on('click', function (event) {
            event.preventDefault();

            var divToUpdate = $(this).attr('div-to-update');
            var divToLoading = $(this).attr('div-to-loading');

            addLoadingStateInElement(divToLoading);

            var url = $(this).attr('href');
            $.post(url, function (data) {
                console.log("POST executed");
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
        });
    });
</script>