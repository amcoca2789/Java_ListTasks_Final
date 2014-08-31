$(document).ready(function() {
    $(".cabecera-menu-imagen").on('click', mostrarMenuOpciones);
});

function mostrarMenuOpciones () {
	$(".cabecera-navegacion").slideToggle(300);
}