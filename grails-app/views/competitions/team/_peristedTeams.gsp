<div class="row">
    <div class="col-sm-12">
        <div id="persistedTeamsContainer">
            <g:if test="${!competition?.teams || competition?.teams?.size() == 0}">
                <div class="alert alert-info">
                    <p>
                        <g:message code="teams.management.no.persisted.teams" args="[competition?.competitionName]"/>
                    </p>
                </div>
            </g:if>
            <g:else>
                <g:render template="team/teamsList" model="[competition: competition]"/>
            </g:else>
        </div>
    </div>
</div>