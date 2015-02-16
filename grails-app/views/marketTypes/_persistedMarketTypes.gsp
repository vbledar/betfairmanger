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
                <div id="persistedMarketTypesList">
                    <g:render template="persistedMarketTypesFiltered" model="[marketTypes: marketTypes]"/>
                </div>
            </g:else>
        </div>
    </div>
</div>