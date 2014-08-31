var name_btn_edicion = "btn-edicion";
var name_form_edicion = "form-edicion";
var name_form_newtarea = "form-newtarea";

$(document).ready(function() {
	$("#btn_new_tarea_dia1").on('click', mostrarFormularioNewTarea);
    $("#btn-edicion-dia1-tarea1").on('click', mostrarFormularioEdicionTarea);
});

function mostrarFormularioNewTarea () {
	$("#form-newtarea-dia1").slideToggle(300);
}

function mostrarFormularioEdicionTarea (evnt) {
	evnt.preventDefault();

	var id = $(this).attr('id');

	var idFormulario = id.replace(name_btn_edicion, name_form_edicion);

	$("#"+idFormulario).slideToggle(100);
}