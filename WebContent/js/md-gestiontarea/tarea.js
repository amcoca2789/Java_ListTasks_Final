var name_btn = "btn";
var name_btn_edicion = "btn-edicion";
var name_btn_new_tarea = "btn_new_tarea_";
var name_form_edicion = "form-edittarea";
var name_form_newtarea = "form-newtarea";

$(document).ready(function() {
	$(".u-boton-newtarea").on('click', mostrarFormularioNewTarea);
    $(".u-boton-ediciontarea").on('click', mostrarFormularioEdicionTarea);
    $( ".u-fecha" ).datetimepicker(
    { 
    	format:	'Y-m-d H:i:s',
    	formatTime:'H:i:s',
		formatDate:'Y/m/d'
    });
});

function mostrarFormularioNewTarea (evnt) {
	evnt.preventDefault();

	$("#"+name_form_newtarea).slideToggle(100);
}

function mostrarFormularioEdicionTarea (evnt) {
	evnt.preventDefault();

	var id = $(this).attr('id');
	console.log("id:"+id);

	var idFormulario = id.replace(name_btn_edicion, name_form_edicion);


	console.log("idFormulario:"+idFormulario);
	$("#"+idFormulario).slideToggle(100);
}