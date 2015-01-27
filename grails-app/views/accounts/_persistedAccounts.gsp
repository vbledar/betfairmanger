<div class="page-header">
    <h5>
        <g:message code="accounts.management.persisted.accounts"/>
    </h5>
</div>

<g:if test="${!developerApps || developerApps?.size() == 0}">
    <div class="alert alert-info">
        <p>
            <g:message code="accounts.management.no.persisted.accounts"/>
        </p>
    </div>
</g:if>
<g:else>
    <div class="table-responsive">
        <table class="table">
            <tr>
                <th>
                    <g:message code="table.column.app.id"/>
                </th>
                <th>
                    <g:message code="table.column.app.name"/>
                </th>
                <th>
                    <g:message code="table.column.owner"/>
                </th>
                <th>
                    <g:message code="table.column.version.id"/>
                </th>
                <th>
                    <g:message code="table.column.version"/>
                </th>
                <th>
                    <g:message code="table.column.application.key"/>
                </th>
                <th>
                    <g:message code="table.column.delay.data"/>
                </th>
                <th>
                    <g:message code="table.column.subscription.required"/>
                </th>
                <th>
                    <g:message code="table.column.owner.managed"/>
                </th>
                <th>
                    <g:message code="table.column.active"/>
                </th>
            </tr>

            <g:set var="applicationId" value="0"/>
            <g:each in="${developerApps}" var="application">
                <g:each in="${application.appVersions}" var="applicationVersion">
                    <tr>
                        <td>
                            ${application?.appId}
                        </td>
                        <td>
                            ${application?.appName}
                        </td>
                        <td>
                            ${applicationVersion?.owner}
                        </td>
                        <td>
                            ${applicationVersion?.versionId}
                        </td>
                        <td>
                            ${applicationVersion?.version}
                        </td>
                        <td>
                            ${applicationVersion?.applicationKey}
                        </td>
                        <td>
                            ${applicationVersion?.delayData}
                        </td>
                        <td>
                            ${applicationVersion?.subscriptionRequired}
                        </td>
                        <td>
                            ${applicationVersion?.ownerManaged}
                        </td>
                        <td>
                            ${applicationVersion?.active}
                        </td>
                    </tr>
                </g:each>
            </g:each>
        </table>
    </div>
</g:else>
