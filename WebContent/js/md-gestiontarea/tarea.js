var name_btn_edicion = "btn-edicion";
var name_btn_new_tarea = "btn_new_tarea_";
var name_form_edicion = "form-edicion";
var name_form_newtarea = "form-newtarea-";

$(document).ready(function() {
	$(".u-boton-newtarea").on('click', mostrarFormularioNewTarea);
    $(".u-boton-ediciontarea").on('click', mostrarFormularioEdicionTarea);
});

function mostrarFormularioNewTarea (evnt) {
	evnt.preventDefault();

	var id = $(this).attr('id');
	console.log("id:"+id);

	var idFormulario = id.replace(name_btn_new_tarea, name_form_newtarea);

	console.log("idFormulario:"+idFormulario);
	$("#"+idFormulario).slideToggle(100);
}

function mostrarFormularioEdicionTarea (evnt) {
	evnt.preventDefault();

	var id = $(this).attr('id');
	console.log("id:"+id);

	var idFormulario = id.replace(name_btn_edicion, name_form_edicion);


	console.log("idFormulario:"+idFormulario);
	$("#"+idFormulario).slideToggle(100);
}