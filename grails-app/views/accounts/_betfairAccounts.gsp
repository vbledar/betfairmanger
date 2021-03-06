<div class="page-header">
    <h5>
        <g:message code="accounts.management.betfair.accounts"/>
    </h5>
</div>

        <div class="btn-group" role="group">
            <g:link elementId="retrieveBetfairAccounts" controller="accounts" action="retrieveBetfairAccounts"
                    class="btn btn-info">
                <span class="glyphicon glyphicon-download"></span> <g:message
                    code="accounts.management.button.retrieve.accounts.betfair"/>
            </g:link>
            <g:link elementId="persistAllAccounts" controller="accounts" action="persistBetfairAccounts"
                    class="btn btn-info">
                <span class="glyphicon glyphicon-cloud-upload"></span> <g:message
                    code="accounts.management.button.persist.betfair.accounts"/>
            </g:link>
        </div>

<br/>

<div id="betfairAccountsContainer">

</div>


<g:javascript>


        console.log("JQuery has been found.");
        $('#retrieveBetfairAccounts').off('click').on('click', function (event) {
            event.preventDefault();
            var divToUpdate = $(this).attr('div-to-update');
            var divToOverlay = $(this).attr('div-to-overlay');
            var divToLoading = $(this).attr('div-to-loading');

            var url = $(this).attr('href')
            $.post(url, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                console.log("POST success");
                if (data.success === false) {
                    console.log("POST failed");
                    console.log("Server message is: " + data.message)
                    showErrorMessage(data.message);
                    return;
                }

                console.log("POST server response successful");
                console.log(data);
                $('#betfairAccountsContainer').html(data);
                showSuccessMessage("Action completed successfully.")
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
                showErrorMessage(data);
            });
        });

        $('#persistAllAccounts').off('click').on('click', function (event) {
            event.preventDefault();

            var url = $(this).attr('href')
            $.post(url, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                console.log("POST success");
                if (data.success === false) {
                    console.log("POST failed");
                    console.log("Server message is: " + data.message)
                    showErrorMessage(data.message);
                    return;
                }

                console.log("POST server response successful");
                console.log(data);
                $('#persistedAccountsContainer').html(data);
                showSuccessMessage("Accounts were persisted successfully.");
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
                showErrorMessage(data);
            })
        });
//    });

</g:javascript>