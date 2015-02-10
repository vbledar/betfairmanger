<div class="page-header">
    <h5>
        <g:message code="accounts.management.persisted.accounts"/>
    </h5>
</div>

<div class="row">
    <div class="col-sm-12">
        <div id="persistedAccountsContainer">
            <g:if test="${!developerApps || developerApps?.size() == 0}">
                <div class="alert alert-info">
                    <p>
                        <g:message code="accounts.management.no.persisted.accounts"/>
                    </p>
                </div>
            </g:if>
            <g:else>
                <g:render template="accountsList"
                          model="[developerApps: developerApps, showActions: Boolean.TRUE]"/>
            </g:else>
        </div>
    </div>
</div>