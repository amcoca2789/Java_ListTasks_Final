<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%@ page import="bo.listtasks.dao.usuario.UsuarioDao"%>
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
   
	String inicioPeriodoStr = ConstanteGral.SIN_DATOS;
	String finPeriodoStr = ConstanteGral.SIN_DATOS;
	if(diasDeLaSemana!=null && diasDeLaSemana.size()>1 ){
		Calendar inicioFecha = diasDeLaSemana.get(0);
		Calendar finFecha = diasDeLaSemana.get(diasDeLaSemana.size()-1);
		
		inicioPeriodoStr = utilBo.convertirCalendarToStringFecha(inicioFecha, ConstanteGral.FORMATO_FECHA_1);
		finPeriodoStr = utilBo.convertirCalendarToStringFecha(finFecha, ConstanteGral.FORMATO_FECHA_1);
	}
	
   Usuario uConSesion = null;
   int idUsuario = -1;
   String usuarioStr = ConstanteGral.SIN_DATOS;
   String idSesionUsuario = ConstanteGral.SIN_DATOS;
   boolean isCorrectaSesion = false;
	String msg = "Lista vacia";
   
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
        <meta name="viewport" content="width=device-width, user-scalable=1, initial-scale=1.0">
        <title>Bienvenido a ListTasks</title>
        <link href='http://fonts.googleapis.com/css?family=Exo+2' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="css/vendor/jquery-ui/all.css">
        <link rel="stylesheet" href="css/vendor/jquery-ui/theme.css">
        <link rel="stylesheet" href="css/vendor/jquery-ui-datetimepicker/jquery.datetimepicker.css">
        <link rel="stylesheet" href="css/vendor/normalize/normalize.css">
        <link rel="stylesheet" href="css/estilos.css">
    </head>
    <body class="u-body">
        <header class="cabecera">
            <figure class="cabecera-logo">
                <img class="cabecera-logo-imagen" src="css/imagenes/logos/logo-1.png" alt="logo-1">
                <figcaption class="cabecera-logo-figcaption">
                    <h4 class="cabecera-logo-figcaption-titulo4 u-titulo-4">Crea tu propia lista de tareas</h4>
                </figcaption>
            </figure>
            <figure class="cabecera-menu">
                <img class="cabecera-menu-imagen" src="css/imagenes/iconos/menu.png" alt="menu" >
            </figure>
            <nav class="cabecera-navegacion">
                <ul class="cabecera-navegacion-lista">
                    <li class="cabecera-navegacion-lista-item">
                        <a class="cabecera-navegacion-lista-item-enlace u-enlace" href="ServicioPerfil">amcoca2789</a>
                    </li>
                    <li class="cabecera-navegacion-lista-item">
                        <a class="cabecera-navegacion-lista-item-enlace u-enlace" href="ServiceLogout">Cerrar Sesion</a>
                    </li>
                    <li class="cabecera-navegacion-lista-item">
                        <a class="cabecera-navegacion-lista-item-enlace u-enlace" href="ServiceSoporteTecnico">Soporte Tecnico</a>
                    </li>
                </ul>
            </nav>
        </header>
        <!-- ************************* [INICIO] calendarioactivas *********************** -->
        <section class="calendario">
            <div class="calendario-encabezado">
                <h3 class="calendario-encabezado-titulo3 u-titulo-3">Calendario de Tareas [EN DESARROLLO]</h3>
                <h4 class="calendario-encabezado-titulo4 u-titulo-4">[01 septiembre 2014 - 07 septiembre 2014]</h4>
            </div>
            <div class="calendario-newtarea">
                <a class="calendario-newtarea-enlace u-enlace u-boton-newtarea" id="btn_new_tarea" href="#"></a>
                <form class="calendario-newtarea-formulario" id="form-newtarea" action="ServiceNewTarea" method="post">
                    <h3 class="calendario-newtarea-formulario-titulo3 u-titulo-3">
                        Nueva Tarea
                    </h3>
                    <label class="calendario-newtarea-formulario-label u-label" for="form-newtarea">Descripcion:</label>
                    <input class="calendario-newtarea-formulario-input u-input" name="caja_descripcion_creacion" id="caja_descripcion_creacion" type="text" />
                    <label class="calendario-newtarea-formulario-label u-label" for="form-newtarea">Fecha Realizacion:</label>
                    <input class="calendario-newtarea-formulario-input u-input u-fecha" name="caja_fecharealizacion_creacion" id="caja_fecharealizacion_creacion" placeholder="YYYY-mm-dd HH:mm:ss" type="text" />
                    <ul class="calendario-newtarea-formulario-lista">
                        <li class="calendario-newtarea-formulario-lista-item">
                            <button class="calendario-newtarea-formulario-lista-item-boton u-boton" id="btn-guardar-newtarea" type="submit">Guardar</button>
                        </li>
                        <li class="calendario-newtarea-formulario-lista-item">
                            <button class="calendario-newtarea-formulario-lista-item-boton u-boton" id="btn-limpiar-newtarea" type="reset">Limpiar</button>
                        </li>
                        <li class="calendario-newtarea-formulario-lista-item">
                            <button class="calendario-newtarea-formulario-lista-item-boton u-boton" id="btn-cancelar-newtarea" type="button">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
            <!-- ************************* [INICIO] Plantilla DIA *********************** -->
            <%
				if(diasDeLaSemana!=null){
					for(int nroDia=0; nroDia<diasDeLaSemana.size(); nroDia++){
						Calendar fechaFiltro = diasDeLaSemana.get(nroDia);
						String nombreDia = "";
						String fechaDia = "";
								
						if(fechaFiltro!=null){
							nombreDia = utilBo.obtenerNombreDia(fechaFiltro);
							fechaDia = utilBo.convertirCalendarToStringFecha(fechaFiltro, ConstanteGral.FORMATO_FECHA_2);
						}
			%>
            <div class="calendario-dia" id="<%=nroDia%>">
                <div class="calendario-dia-detalle">
                    <span class="calendario-dia-detalle-nombredia u-span"><%=nombreDia %></span>
                    <p class="calendario-dia-detalle-fecha u-parrafo">[<%=fechaDia %>]</p>
                </div>
                <%
					List<Tarea> listaTareasFiltradas = tareaDao.filtrarTareasPorFecha(listaTareas, fechaFiltro);
					
					if(listaTareasFiltradas==null || listaTareasFiltradas.isEmpty()){
				%>
                <div class="calendario-dia-resultado">
                    <span class="calendario-dia-resultado-mensaje u-span">NO hay tareas para este dia</span>
                </div>
                <%	
					}else{
				%>
				<%
					int nroTarea = 1;
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
                    <div class="calendario-dia-tarea-contenedor">
                        <form class="calendario-dia-tarea-contenedor-conclusion" id="formulario-concluida-<%=diaXtareaY%>" action="ServicioConclusionTarea" method="post">
                            <input name="caja-nro-dia" id="caja-nro-dia" value="<%=nroDia %>" type="hidden" />
                            <input name="caja-nro-tarea" id="caja-nro-tarea" value="<%=nroTarea %>" type="hidden" />
                            <input name="caja-nro-idtarea-conclusion-<%=diaXtareaY%>" id="caja-nro-idtarea-conclusion-<%=diaXtareaY%>" value="<%=idDiaXTareaY %>" type="hidden" />
                            <button class="calendario-dia-tarea-contenedor-conclusion-boton u-boton u-boton-concluida" id="btn-concluida" type="submit"></button>
                        </form>
                        <form class="calendario-dia-tarea-contenedor-archivar" id="formulario-archivada-<%=diaXtareaY%>" action="ServicioArchivarTarea" method="post">
                            <input name="caja-nro-dia" id="caja-nro-dia" value="<%=nroDia %>" type="hidden" />
                            <input name="caja-nro-tarea" id="caja-nro-tarea" value="<%=nroTarea %>" type="hidden" />
                            <input name="caja-nro-idtarea-archivar-<%=diaXtareaY%>" id="caja-nro-idtarea-archivar-<%=diaXtareaY%>" value="<%=idDiaXTareaY %>" type="hidden" />
                            <button class="calendario-dia-tarea-contenedor-archivar-boton u-boton u-boton-archivada" id="btn-archivada" type="submit"></button>
                        </form>
                    </div>
                    <span class="calendario-dia-tarea-descripcion u-span">
                    <%=descripcionTarea %>
                    <a class="calendario-dia-tarea-descripcion-enlace u-enlace u-boton-ediciontarea" id="btn-edicion-<%=diaXtareaY%>" href="#"></a>
                    </span>
                    <form class="calendario-dia-tarea-edicion" id="formulario-editada-<%=diaXtareaY%>" action="ServicioEdicionTarea" method="post">
                        <h3 class="calendario-dia-tarea-edicion-titulo3 u-titulo-3">Edicion Tarea</h3>
                        <input name="caja-nro-dia" id="caja-nro-dia" value="<%=nroDia %>" type="hidden" />
                        <input name="caja-nro-tarea" id="caja-nro-tarea" value="<%=nroTarea %>" type="hidden" />
                        <input name="caja-nro-idtarea-edicion-<%=diaXtareaY%>" id="caja-nro-idtarea-edicion-<%=diaXtareaY%>" value="<%=idDiaXTareaY %>" type="hidden" />
                        <label class="calendario-dia-tarea-edicion-label u-label" for="formulario-editada-<%=diaXtareaY%>">Descripcion:</label>
                        <input class="calendario-dia-tarea-edicion-input u-input" name="caja_descripcion_edicion-<%=diaXtareaY%>" id="caja_descripcion_edicion-<%=diaXtareaY%>" value="<%=descripcionTarea %>" type="text" />
                        <label class="calendario-dia-tarea-edicion-label u-label" for="formulario-editada-<%=diaXtareaY%>">Fecha Realizacion:</label>
                        <input class="calendario-dia-tarea-edicion-input u-input u-fecha" name="caja_fecharealizacion_edicion-<%=diaXtareaY%>" id="caja_fecharealizacion_edicion-<%=diaXtareaY%>" value="<%=fechaRealizacionTareaStr %>" placeholder="YYYY-mm-dd HH:mm:ss" type="text" />
                        <ul class="calendario-dia-tarea-edicion-lista">
                            <li class="calendario-dia-tarea-edicion-lista-item">
                                <button class="calendario-dia-tarea-edicion-lista-item-boton u-boton" id="btn-guardar-<%=diaXtareaY%>" type="submit">Guardar</button>
                            </li>
                            <li class="calendario-dia-tarea-edicion-lista-item">
                                <button class="calendario-dia-tarea-edicion-lista-item-boton u-boton" id="btn-limpiar-<%=diaXtareaY%>" type="reset">Limpiar</button>
                            </li>
                            <li class="calendario-dia-tarea-edicion-lista-item">
                                <button class="calendario-dia-tarea-edicion-lista-item-boton u-boton" id="btn-cancelar-<%=diaXtareaY%>" type="button">Cancelar</button>
                            </li>
                        </ul>
                    </form>
                </div>
                <!-- ************************* [FIN] Plantilla Tarea *********************** -->
                <%
                			nroTarea++;
							}//fin for tareafiltradas
						}//fin else tareafiltradas
                %>
            </div>
            <!-- ************************* [FIN] Plantilla DIA *********************** -->
            <%
				}//fin for dia
			}//fin if dia
            %>
        </section>
        <!-- ************************* [FIN] calendarioactivas *********************** -->
        <footer class="contacto">
            <h2 class="contacto-titulo2 u-titulo-2">
                Contacto
            </h2>
            <h3 class="contacto-titulo3 u-titulo-3">
                AMROSOFT 2014 ®
            </h3>
        </footer>
        <!-- JavaScript -->
        <script src="js/vendor/jquery/jquery.js">;</script>
        <script src="js/vendor/jquery-ui/jquery-ui.js">;</script>
        <script src="js/vendor/jquery-ui-datetimepicker/jquery.datetimepicker.js">;</script>
        <script src="js/general.min.js">;</script>
    </body>
</html>