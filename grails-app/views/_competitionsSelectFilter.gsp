<blockquote style="margin-bottom: 0px !important;">
    <g:select id="competitionSelection"
              class="form-control input-sm"
              name="competition"
              from="${competitions}"
              optionKey="competitionId"
              optionValue="competitionName"
              noSelection="['': 'Please select']"
              div-to-update="mainEventsInformationPanel"/>
    <h6>
        <footer>
            Competition
        </footer>
    </h6>
</blockquote>


<g:javascript>

    $(function() {

        $('#competitionSelection').change(function() {
            var divToUpdate = $(this).attr('div-to-update');
            var selectedOption = $('#competitionSelection option:selected').val();
            var parameters = {};
            parameters.competitionId = selectedOption;

            var url = "${createLink(controller: 'landing', action: 'ajaxGetEventByCompetition')}";
            $.post(url, parameters, function (data) {
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
                showSuccessMessage("Action completed successfully.");
                $('#'+divToUpdate).html(data);
            }).fail(function (data) {
                console.log("POST failed.");
                console.log(data);
                showErrorMessage(data);
            });
        });
    });
</g:javascript>