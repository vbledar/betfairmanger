<div class="page-header">
    <h5>
        <g:message code="competitions.management.persisted.competitions"/>
    </h5>
</div>

<div class="row">
    <div class="col-sm-12">
        <div id="persistedCompetitionsContainer">
            <g:if test="${!competitions || competitions?.size() == 0}">
                <div class="alert alert-info">
                    <p>
                        <g:message code="competitions.management.no.persisted.competitions"/>
                    </p>
                </div>
            </g:if>
            <g:else>
                <g:render template="competitionsList" model="[competitions: competitions]"/>
            </g:else>
        </div>
    </div>
</div>