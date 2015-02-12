<div class="col-xs-12 col-sm-8 col-md-9">

    <div class="page-header">
        <h4>
            <g:message code="events.management.title"/>
        </h4>

        <div class="btn-group btn-group-sm" role="group">
            <g:link elementId="retrieveCompetitionsFromBetFair" controller="competitions"
                    action="retrieveBetfairCompetitions"
                    class="btn btn-info">
                <span class="glyphicon glyphicon-download"></span> <g:message
                    code="events.management.button.retrieve.events.betfair"/>
            </g:link>
        </div>
    </div>

    <g:if test="${!eventsList || eventsList.size() == 0}">
        <div class="alert alert-info">
            <g:message code="events.management.no.persisted.events"/>
        </div>
    </g:if>
    <g:else>

    </g:else>
</div>