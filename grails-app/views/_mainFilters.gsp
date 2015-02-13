<%@ page import="com.tipster.betfair.Country" %>
<div class="panel-body bg-info" style="margin-bottom: 0px !important;">
    <h4>
        <g:message code="main.filters.header"/>
    </h4>

    <div class="clearfix"></div>

    <div class="col-xs-12 col-sm-3">
        <blockquote style="margin-bottom: 0px !important;">
            <h5>UK Exchange</h5>
            <h6>
                <footer>
                    Betfair Exchange
                </footer>
            </h6>
        </blockquote>
    </div>

    <div class="col-xs-12 col-sm-3">
        <blockquote style="margin-bottom: 0px !important;">
            <h5>Soccer</h5>
            <h6>
                <footer>
                    Event Type
                </footer>
            </h6>
        </blockquote>
    </div>

    <div class="col-xs-12 col-sm-3">

    </div>

    <div class="col-xs-12 col-sm-3">
        <blockquote style="margin-bottom: 0px !important;">
            <g:select id="countrySelection"
                      class="form-control input-sm"
                      name="country"
                      from="${countries}"
                      optionKey="countryCode"
                      optionValue="countryName"
                      noSelection="['': 'Please select']"
                      div-to-update="mainInformationPanel"/>
            <h6>
                <footer>
                    Country
                </footer>
            </h6>
        </blockquote>
    </div>
</div>

<g:javascript>

    $(function() {

        $('#countrySelection').change(function() {
            var divToUpdate = $(this).attr('div-to-update');
            var selectedOption = $('#countrySelection option:selected').val();
            var parameters = {};
            parameters.country = selectedOption;

            var url = "${createLink(controller: 'landing', action: 'ajaxGetCompetitionsByCountry')}";
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



