<%@ page import="com.tipster.betfair.event.Competition" %>
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

<div class="row">
    <div class="col-xs-6">

    </div>

    <div class="col-xs-6 text-right">
        <div class="dataTables_paginate paging_bootstrap">
            <util:remotePaginate controller="competitions" action="filteredCompetitions" total="${competitions.getTotalCount()}"
                                 update="persistedCompetitions"
                                 onLoading="addLoadingStateInElement('competitionsBoxContainer')"
                                 onComplete="removeLoadingStateFromElement('competitionsBoxContainer')"
                                 max="15"/>
        </div>
    </div>
</div>
