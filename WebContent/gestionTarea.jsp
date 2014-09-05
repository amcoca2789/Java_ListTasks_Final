<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%@page import="bo.listtasks.dao.usuario.UsuarioDao"%>
<%@ page import="bo.listtasks.constantes.ConstanteGral"%>
<%@ page import="bo.listtasks.constantes.ConstantesRutasServlet"%>
<%@ page import="bo.listtasks.dto.usuario.Usuario"%>
<%@ page import="bo.listtasks.dto.tarea.Tarea"%>
<%@ page import="bo.listtasks.dao.tarea.TareaDao"%>
<%@ page import="bo.listtasks.util.UtilBO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	HttpSession sesionUsuario = request.getSession();
	UtilBO utilBo = new UtilBO();
	 TareaDao tareaDao = new TareaDao();
	Calendar fechaActual = Calendar.getInstance(); 
	List<Calendar> diasDeLaSemana = utilBo.obtenerDiasDeLaSemana(fechaActual);
   
   Usuario uConSesion = null;
   int idUsuario = -1;
   String usuarioStr = ConstanteGral.SIN_DATOS;
   String idSesionUsuario = ConstanteGral.SIN_DATOS;
   boolean isCorrectaSesion = false;
	String msg = "Lista vacia";
	int nroTarea = 1;
   
   if (sesionUsuario != null) {
   	isCorrectaSesion = this.inicioSesion(sesionUsuario);
   	uConSesion = (Usuario) sesionUsuario
   	.getAttribute(ConstanteGral.SESION_OBJETO_USUARIO);
   	idSesionUsuario = String.valueOf(session
   	.getAttribute(ConstanteGral.ID_SESION_USUARIO));
   }
   
   List<Tarea> listaTareas = null;
   
   if (isCorrectaSesion) {
   	if (uConSesion != null) {
  
   idUsuario = uConSesion.getIdUsuario();
   usuarioStr = uConSesion.getCodigoUsuario();
   listaTareas = tareaDao.obtenerTareasUsuario(uConSesion);
   
   
   
   	} else {
   System.out.println("El usuario es nulo");
   	}
   
   } else {
   	System.out.println("No inicio sesion");
   	String url_destino = ConstantesRutasServlet.RUTA_LOGIN;
   	RequestDispatcher dispatcher = request
   	.getRequestDispatcher(url_destino);
   	dispatcher.forward(request, response);
   }
%>
<%!public boolean inicioSesion(HttpSession session) {
		Usuario u = (Usuario) session
				.getAttribute(ConstanteGral.SESION_OBJETO_USUARIO);

		String idUsuario = session.getId();
		String idUsuarioAtt = String.valueOf(session
				.getAttribute(ConstanteGral.ID_SESION_USUARIO));

		boolean isIguales = idUsuario != null && idUsuario.equals(idUsuario)
				? true
				: false;

		if (u != null && isIguales) {
			return true;
		}

		return false;
	}%>
<!-- HTML -->
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=1, initial-scale=1.0">
<title>Bienvenido a ListTasks</title>
<link href='http://fonts.googleapis.com/css?family=Exo+2'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/vendor/jquery-ui/all.css">
<link rel="stylesheet" href="css/vendor/jquery-ui/theme.css">
<link rel="stylesheet"
	href="css/vendor/jquery-ui-datetimepicker/jquery.datetimepicker.css">
<link rel="stylesheet" href="css/vendor/normalize/normalize.css">
<link rel="stylesheet" href="css/estilos.css">
</head>
<body class="u-body">
	<header class="cabecera">
		<figure class="cabecera-logo">
			<img class="cabecera-logo-imagen" src="css/imagenes/logos/logo-1.png"
				alt="logo-1">
			<figcaption class="cabecera-logo-figcaption">
				<h4 class="cabecera-logo-figcaption-titulo4 u-titulo-4">Crea tu
					propia lista de tareas</h4>
			</figcaption>
		</figure>
		<figure class="cabecera-menu">
			<img class="cabecera-menu-imagen" src="css/imagenes/iconos/menu.png"
				alt="menu">
		</figure>
		<nav class="cabecera-navegacion">
			<ul class="cabecera-navegacion-lista">
				<li class="cabecera-navegacion-lista-item"><a
					class="cabecera-navegacion-lista-item-enlace u-enlace"
					href="ServicioPerfil">amcoca2789</a></li>
				<li class="cabecera-navegacion-lista-item"><a
					class="cabecera-navegacion-lista-item-enlace u-enlace"
					href="ServiceLogout">Cerrar Sesion</a></li>
				<li class="cabecera-navegacion-lista-item"><a
					class="cabecera-navegacion-lista-item-enlace u-enlace"
					href="ServiceSoporteTecnico">Soporte Tecnico</a></li>
			</ul>
		</nav>
	</header>
	<section class="newtarea">
		<a class="newtarea-enlace u-enlace u-boton-newtarea"
			id="btn_new_tarea" href="#"></a>
		<form class="newtarea-formulario" id="form-newtarea"
			action="ServiceNewTarea" method="post">
			<h3 class="newtarea-formulario-titulo3 u-titulo-3">Nueva Tarea</h3>
			<label class="newtarea-formulario-label u-label" for="form-newtarea">Descripcion:</label>
			<input class="newtarea-formulario-input u-input"
				name="caja_descripcion_creacion" id="caja_descripcion_creacion"
				type="text" /> <label class="newtarea-formulario-label u-label"
				for="form-newtarea">Fecha Realizacion:</label> <input
				class="newtarea-formulario-input u-input u-fecha"
				name="caja_fecharealizacion_creacion"
				id="caja_fecharealizacion_creacion"
				placeholder="YYYY-mm-dd HH:mm:ss" type="text" />
			<ul class="newtarea-formulario-lista">
				<li class="newtarea-formulario-lista-item">
					<button class="newtarea-formulario-lista-item-boton u-boton"
						id="btn-guardar-newtarea" type="submit">Guardar</button>
				</li>
				<li class="newtarea-formulario-lista-item">
					<button class="newtarea-formulario-lista-item-boton u-boton"
						id="btn-limpiar-newtarea" type="reset">Limpiar</button>
				</li>
				<li class="newtarea-formulario-lista-item">
					<button class="newtarea-formulario-lista-item-boton u-boton"
						id="btn-cancelar-newtarea" type="button">Cancelar</button>
				</li>
			</ul>
		</form>
	</section>
	<!-- ************************* [INICIO] Calendario *********************** -->
	<section class="calendario">
		<!-- ************************* [INICIO] Plantilla DIA *********************** -->
		<%
			if(diasDeLaSemana!=null){
				for(int nroDia=0; nroDia<diasDeLaSemana.size(); nroDia++){
					Calendar fechaFiltro = diasDeLaSemana.get(nroDia);
					String nombreDia = "";
					String fechaDia = "";
					
					if(fechaFiltro!=null){
						nombreDia = utilBo.obtenerNombreDia(fechaFiltro);
						fechaDia = utilBo.convertirCalendarToStringFechaYTiempo(fechaFiltro);
					}
		%>

		<div class="calendario-dia" id="dia1">
			<div class="calendario-dia-detalle">
				<span class="calendario-dia-detalle-nombre u-span"><%=nombreDia%></span>
				<span class="calendario-dia-detalle-fecha u-span"><%=fechaDia%></span>
			</div>
			<%
				if(listaTareas!=null){
								List<Tarea> listaTareasFiltradas = tareaDao.filtrarTareasPorFecha(listaTareas, fechaFiltro);
								System.out.println("listaTareasFiltradas.size(): "+listaTareasFiltradas.size());
														for(Tarea tarea: listaTareasFiltradas){
			%>
			<!-- ************************* [INICIO] Plantilla Tarea *********************** -->
			<%
				int idDiaXTareaY=tarea.getIdTarea();
													String descripcionTarea = tarea.getDescripcionTarea();
											                Calendar fechaRealizacionTarea=tarea.getFechaRealizacionTarea();
											                String fechaRealizacionTareaStr = utilBo.convertirCalendarToStringFechaYTiempo(fechaRealizacionTarea);
											                
											                String diaX = "dia"+nroDia;
											                String tareaY = "tarea"+nroTarea;
													String diaXtareaY = diaX + "-" + tareaY;
			%>
			<div class="calendario-dia-tarea" id="<%=diaXtareaY%>">
				<ul class="calendario-dia-tarea-lista">
					<li class="calendario-dia-tarea-lista-item"><a
						class="calendario-dia-tarea-lista-item-enlace u-enlace u-boton-concluida"
						id="btn-concluida-dia1-tarea1" href="ServicioConclusionTarea"></a>
					</li>
					<li class="calendario-dia-tarea-lista-item"><a
						class="calendario-dia-tarea-lista-item-enlace u-enlace u-boton-archivada"
						id="btn-archivada-dia1-tarea1" href="ServicioArchivarTarea"></a></li>
				</ul>
				<span class="calendario-dia-tarea-descripcion u-span"> <%=descripcionTarea%>
					<a
					class="calendario-dia-tarea-descripcion-enlace u-enlace u-boton-ediciontarea"
					id="btn-edicion-<%=diaXtareaY%>" href="#"></a>
				</span>
				<form class="calendario-dia-tarea-formulario"
					id="form-edittarea-<%=diaXtareaY%>" action="ServicioEdicionTarea"
					method="post">
					<h3 class="calendario-dia-tarea-formulario-titulo3 u-titulo-3">Edicion
						Tarea</h3>
					<input name="caja_nro_dia" id="caja_nro_dia" value="<%=nroDia%>"
						type="hidden" /> <input name="caja_nro_tarea" id="caja_nro_tarea"
						value="<%=nroTarea%>" type="hidden" /> <input
						name="caja_idtarea_edicion-<%=diaXtareaY%>"
						id="caja_idtarea_edicion-<%=diaXtareaY%>"
						value="<%=idDiaXTareaY%>" type="hidden" /> <label
						class="calendario-dia-tarea-formulario-label u-label"
						for="form-edittarea-dia1-tarea1">Descripcion:</label> <input
						class="calendario-dia-tarea-formulario-input u-input"
						name="caja_descripcion_edicion-<%=diaXtareaY%>"
						id="caja_descripcion_edicion-<%=diaXtareaY%>"
						value="<%=descripcionTarea%>" type="text" /> <label
						class="calendario-dia-tarea-formulario-label u-label"
						for="form-edittarea-dia1-tarea1">Fecha Realizacion:</label> <input
						class="calendario-dia-tarea-formulario-input u-input u-fecha"
						name="caja_fecharealizacion_edicion-<%=diaXtareaY%>"
						id="caja_fecharealizacion_edicion-<%=diaXtareaY%>"
						value="<%=fechaRealizacionTareaStr%>"
						placeholder="YYYY-mm-dd HH:mm:ss" type="text" />
					<ul class="calendario-dia-tarea-formulario-lista">
						<li class="calendario-dia-tarea-formulario-lista-item">
							<button
								class="calendario-dia-tarea-formulario-lista-item-boton u-boton"
								id="btn-guardar-edittarea" type="submit">Guardar</button>
						</li>
						<li class="calendario-dia-tarea-formulario-lista-item">
							<button
								class="calendario-dia-tarea-formulario-lista-item-boton u-boton"
								id="btn-limpiar-edittarea" type="reset">Limpiar</button>
						</li>
						<li class="calendario-dia-tarea-formulario-lista-item">
							<button
								class="calendario-dia-tarea-formulario-lista-item-boton u-boton"
								id="btn-cancelar-edittarea" type="button">Cancelar</button>
						</li>
					</ul>
				</form>
			</div>
			<!-- ************************* [FIN] Plantilla Tarea *********************** -->
			<%
				nroTarea++;
														}
													}
											else{
			%>
			<span>No existe tareas en este dia</span>
			<%
				}
			%>
		</div>
		<%
			}
				}else{
		%>
		<span>No hay dias en este semana</span>
		<%
			}
		%>
		<!-- ************************* [FIN] Plantilla DIA *********************** -->
	</section>
	<!-- ************************* [FIN] Calendario *********************** -->
	<footer class="contacto">
		<h2 class="contacto-titulo2 u-titulo-2">Contacto</h2>
		<h3 class="contacto-titulo3 u-titulo-3">AMROSOFT 2014 ®</h3>
	</footer>
	<!-- JavaScript -->
	<script src="js/vendor/jquery/jquery.js">
		;
	</script>
	<script src="js/vendor/jquery-ui/jquery-ui.js">
		;
	</script>
	<script
		src="js/vendor/jquery-ui-datetimepicker/jquery.datetimepicker.js">
		;
	</script>
	<script src="js/general.min.js">
		;
	</script>
</body>
</html>