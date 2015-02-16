// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= plugins/morris/morris.min
//= plugins/sparkline/jquery.sparkline.min
//= plugins/jvectormap/jquery-jvectormap-1.2.2.min
//= plugins/jvectormap/jquery-jvectormap-world-mill-en
//= plugins/jqueryKnob/jquery.knob
//= plugins/daterangepicker/daterangepicker
//= plugins/datepicker/bootstrap-datepicker
//= plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min
//= plugins/iCheck/icheck.min
//= AdminLTE/app
//= require notify-combined
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

function showInfoMessage(message) {
    $.notify(message, 'info');
}

function showSuccessMessage(message) {
    $.notify(message, 'success');
}

function showWarnMessage(message) {
    $.notify(message, 'warn');
}

function showErrorMessage(message) {
    $.notify(message, 'error');
}

function ajaxCallToServer(url, divToUpdate, divToLoading) {

    addLoadingStateInElement(divToLoading);
    $.post(url, function (data) {
        console.log("POST executed");
    }).done(function (data) {
        removeLoadingStateFromElement(divToLoading);

        if (data.success === false) {
            showErrorMessage(data.message);
            return;
        }

        $('#'+divToUpdate).html(data);
    }).fail(function (data) {
        removeLoadingStateFromElement(divToLoading);
        showErrorMessage(data);
    });

}

function addLoadingStateInElement(elementId) {
    $('#'+elementId).append("<div class='overlay'></div>");
    $('#'+elementId).append("<div class='loading-img'></div>");
}

function removeLoadingStateFromElement(elementId) {
    $('#'+elementId).find('.overlay').remove();
    $('#'+elementId).find('.loading-img').remove();
}