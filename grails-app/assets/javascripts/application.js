// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
//= require bootstrap
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