<g:if test="${!markets || markets.size() == 0}">
    <div class="alert alert-info">
        <g:message code="markets.management.no.persisted.markets"/>
    </div>
</g:if>
<g:else>
    <g:render template="marketsList" model="[markets: markets]"/>
</g:else>