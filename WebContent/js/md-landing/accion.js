$(document).ready(function() {
	$("#btn-form-signup").on('click', mostrarFormularioSignUp);
    $("#btn-form-login").on('click', mostrarFormularioLogin);
});

function mostrarFormularioSignUp (evnt) {
	$("#form-signup").slideToggle(100);
	$("#form-login").css('display', 'none');
}

function mostrarFormularioLogin (evnt) {
	$("#form-login").slideToggle(100);
	$("#form-signup").css('display', 'none');
}