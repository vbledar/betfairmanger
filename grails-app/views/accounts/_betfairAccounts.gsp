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
</div>

<div id="betfairAccountsContainer">

</div>

<script type="application/javascript">

    $(function () {
        $('#retrieveBetfairAccounts').off('click').on('click', function (event) {
            event.preventDefault();

            var url = $(this).attr('href')
            $.post(url, function (data) {
                console.log("POST executed");
            }).done(function (data) {
                console.log("POST success");
                if (data.success === false) {
                    console.log("POST failed");
                    console.log("Server message is: " + data.message)
                }

                console.log("POST server response successful");
                console.log(data);
                $('#betfairAccountsContainer').html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
            })
        });
    });

</script>