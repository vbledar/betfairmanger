<div class="page-header">
    <h5>
        <g:message code="countries.management.persisted.countries"/>
    </h5>
</div>

<div class="row">
    <div class="col-sm-12">
        <div id="persistedCountriesContainer">
            <g:if test="${!countries || countries?.size() == 0}">
                <div class="alert alert-info">
                    <p>
                        <g:message code="countries.management.no.persisted.countries"/>
                    </p>
                </div>
            </g:if>
            <g:else>
                <div id="persistedCountriesList">
                    <g:render template="country/persistedCountriesFiltered" model="[countries: countries, showActions: Boolean.TRUE]"/>
                </div>
            </g:else>
        </div>
    </div>
</div>