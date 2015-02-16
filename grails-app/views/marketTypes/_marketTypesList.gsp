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
                    <label class="label selectable-row ${marketType?.automaticRetrieval ? 'label-success' : 'label-danger'} update-market-type-auto-state"
                           market-type-name="${marketType?.name}"
                           div-to-loading="marketTypesBoxContainer">
                        ${marketType?.automaticRetrieval ? 'Enabled' : 'Disabled'}
                    </label>
                </td>
            </tr>
        </g:each>
    </table>
</div>

<script type="application/javascript">

    $('.update-market-type-auto-state').off('click').on('click', function(event) {
        event.preventDefault();

        var element = $(this);
        var divToLoading = $(this).attr('div-to-loading');
        var wasEnabled = $(this).hasClass('label-success');

        addLoadingStateInElement(divToLoading);

        var marketTypeName = $(this).attr('market-type-name');
        var parameters = {}
        parameters.marketTypeName = marketTypeName;

        var url = "${createLink(controller: 'marketTypes', action: 'setMarketTypeAutomaticRetrieval')}";
        $.post(url, parameters, function (data) {
            console.log("POST executed");
        }).done(function (data) {
            removeLoadingStateFromElement(divToLoading);

            if (data.success === false) {
                showErrorMessage(data.message);
                return;
            }

            if (wasEnabled) {
                $(element).removeClass('label-success').addClass('label-danger');
                $(element).text('Disabled');
            }
            else {
                $(element).removeClass('label-danger').addClass('label-success');
                $(element).text('Enabled');
            }

            showSuccessMessage(data.message);
        }).fail(function (data) {
            removeLoadingStateFromElement(divToLoading);
            showErrorMessage(data);
        });
    });

</script>