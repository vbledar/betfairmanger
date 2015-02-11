<div class="page-header">
    <h5>
        <g:message code="market.types.management.persisted.market.types"/>
    </h5>
</div>

<div class="row">
    <div class="col-sm-12">
        <div id="persistedMarketTypesContainer">
            <g:if test="${!marketTypes || marketTypes?.size() == 0}">
                <div class="alert alert-info">
                    <p>
                        <g:message code="market.types.management.no.persisted.market.types"/>
                    </p>
                </div>
            </g:if>
            <g:else>
                <g:render template="marketTypesList" model="[marketTypes: marketTypes]"/>
            </g:else>
        </div>
    </div>
</div>