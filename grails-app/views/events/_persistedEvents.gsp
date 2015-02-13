<g:if test="${!eventsList || eventsList.size() == 0}">
    <div class="alert alert-info">
        <g:message code="events.management.no.persisted.events"/>
    </div>
</g:if>
<g:else>
    <g:render template="/events/eventsList" model="[eventsList: eventsList]"/>

    <div id="marketsContainer">

    </div>
</g:else>