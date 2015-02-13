<div class="col-xs-12 col-sm-12 col-md-12 bg-info" style="margin-left: 0px !important; padding-left: 0px !important;">

    <g:render template="/specializedFilters" model="[competitions: competitions]"/>

    <div id="mainEventsInformationPanel">
        <div class="panel-body bg-info">
            <h4>
                Events management
            </h4>

            %{--<div class="col-xs-12 col-sm-8 col-md-9">--}%
                <div class="alert alert-warning">
                    <g:message code="user.message.please.select.competition"/>
                </div>
            %{--</div>--}%
        </div>
    </div>

</div>