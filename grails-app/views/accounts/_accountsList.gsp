<div class="panel panel-default table-responsive">
    <table class="table table-cell-horizontal-center table-cell-vertical-middle">
        <thead>
        <th>
            <g:message code="form.field.developer.app.id"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.name"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.owner"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.owner.managed"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.version"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.version.id"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.application.key"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.subscription.required"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.delay.data"/>
        </th>
        <th>
            <g:message code="form.field.developer.app.version.active"/>
        </th>
        <g:if test="${showActions}">
            <th>

            </th>
        </g:if>
        </thead>

        <g:each in="${developerApps}" var="developerApp">
            <g:each in="${developerApp?.appVersions}" var="appVersion">
                <tr id="accountRow${appVersion?.id}" class="${appVersion?.active ? 'success' : ''}">
                    <td>
                        ${developerApp?.appId}
                    </td>
                    <td>
                        ${developerApp?.appName}
                    </td>
                    <td>
                        ${appVersion?.owner}
                    </td>
                    <td>
                        ${appVersion?.ownerManaged}
                    </td>
                    <td>
                        ${appVersion?.version}
                    </td>
                    <td>
                        ${appVersion?.versionId}
                    </td>
                    <td>
                        ${appVersion?.applicationKey}
                    </td>
                    <td>
                        ${appVersion?.subscriptionRequired}
                    </td>
                    <td>
                        ${appVersion?.delayData}
                    </td>
                    <td>
                        ${appVersion?.active}
                    </td>
                    <g:if test="${showActions}">
                        <th>
                            <div class="btn-group">
                                <g:link controller="accounts" action="deleteAccount" id="${appVersion?.id}" class="btn btn-danger">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </g:link>
                            </div>
                        </th>
                    </g:if>
                </tr>
            </g:each>
        </g:each>
    </table>
</div>