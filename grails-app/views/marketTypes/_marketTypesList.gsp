<div class="box-body table-responsive no-padding">
    <table class="table table-bordered table-hover dataTable table-cell-horizontal-center table-cell-vertical-middle">

        <tr>
            <th>
                <g:message code="form.field.market.type.name"/>
            </th>
            <th>
                <g:message code="form.field.retrieve.automatically"/>
            </th>
        </tr>

        <g:each in="${marketTypes}" var="marketType">
            <tr>
                <td>
                    ${marketType?.name}
                </td>
                <td>
                    <div class="checkbox no-margin-on-checkbox">
                        <label style="padding-left: 0px">
                            <g:if test="${marketType?.automaticRetrieval}">
                                <input name="automaticRetrieval" type="checkbox" checked style="position: absolute; opacity: 0;">
                            </g:if>
                            <g:else>
                                <input name="automaticRetrieval" type="checkbox" style="position: absolute; opacity: 0;">
                            </g:else>
                            <ins class="iCheck-helper" market-type-name="${marketType?.name}" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins>
                        </label>
                    </div>
                </td>
            </tr>
        </g:each>
    </table>
</div>

<script type="application/javascript">

    $('.iCheck-helper').off('click').on('click', function(event) {
        event.preventDefault();

        var marketTypeName = $(this).attr('market-type-name');
        var parameters = {}
        parameters.marketTypeName = marketTypeName;

        var url = "${createLink(controller: 'marketTypes', action: 'setMarketTypeAutomaticRetrieval')}";
        $.post(url, parameters, function (data) {
            console.log("POST executed");
        }).done(function (data) {
            removeLoadingStateFromElement('countriesBoxContainer');

            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            showSuccessMessage(data.message);
        }).fail(function (data) {
            removeLoadingStateFromElement('countriesBoxContainer');
            showErrorMessage(data);
        });
    });

</script>