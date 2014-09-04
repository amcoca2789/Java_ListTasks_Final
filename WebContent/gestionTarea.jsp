<%@page import="bo.listtasks.dao.usuario.UsuarioDao"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%@ page import="bo.listtasks.constantes.ConstanteGral"%>
<%@ page import="bo.listtasks.constantes.ConstantesRutasServlet"%>
<%@ page import="bo.listtasks.dto.usuario.Usuario"%>
<%@ page import="bo.listtasks.dto.tarea.Tarea"%>
<%@ page import="bo.listtasks.dao.tarea.TareaDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%
   HttpSession sesionUsuario = request.getSession(false);
   
   Usuario uConSesion = null;
   int idUsuario = -1;
   String usuarioStr = ConstanteGral.SIN_DATOS;
   String idSesionUsuario = ConstanteGral.SIN_DATOS;
   boolean isCorrectaSesion = false;
   
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
   TareaDao tareaDao = new TareaDao();
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
        <section class="calendario">
            <div class="calendario-dia" id="dia1">
                <div class="calendario-dia-nombre">
                    <span class="calendario-dia-nombre-span u-span">Lunes</span>
                    <ul class="calendario-dia-nombre-lista">
                        <li class="calendario-dia-nombre-lista-item">
                            <a class="calendario-dia-nombre-lista-item-enlace u-enlace u-boton-newtarea" id="btn_new_tarea_dia1" href="#"></a>
                        </li>
                    </ul>
                </div>
                <form class="calendario-dia-formulario" id="form-newtarea-dia1" action="ServiceNewTarea" method="post">
                    <h3 class="calendario-dia-formulario-titulo3 u-titulo-3">Nueva Tarea</h3>
                    <label class="calendario-dia-formulario-label u-label" for="form-newtarea">Descripcion:</label>
                    <input class="calendario-dia-formulario-input u-input" name="caja_descripcion_creacion_dia1_tarea1" id="caja_descripcion_creacion_dia1_tarea1" type="text" />
                    <label class="calendario-dia-formulario-label u-label" for="form-newtarea">Fecha Realizacion:</label>
                    <input class="calendario-dia-formulario-input u-input u-fecha" name="caja_fecharealizacion_creacion_dia1_tarea1" id="caja_fecharealizacion_creacion_dia1_tarea1" type="text" />

                    <ul class="calendario-dia-formulario-lista">
                        <li class="calendario-dia-formulario-lista-item">
                            <button class="calendario-dia-formulario-lista-item-boton u-boton" id="btn-guardar-dia1-tarea1" type="submit">Guardar</button>
                        </li>
                        <li class="calendario-dia-formulario-lista-item">
                            <button class="calendario-dia-formulario-lista-item-boton u-boton" id="btn-limpiar-dia1-tarea1" type="reset">Limpiar</button>
                        </li>
                        <li class="calendario-dia-formulario-lista-item">
                            <button class="calendario-dia-formulario-lista-item-boton u-boton" id="btn-cancelar-dia1-tarea1" type="button">Cancelar</button>
                        </li>
                    </ul>
                </form>
                <div class="calendario-dia-botonera" id="dia1-tarea1">
                    <ul class="calendario-dia-botonera-lista">
                        <li class="calendario-dia-botonera-lista-item">
                            <a href="ServicioConclusionTarea" class="calendario-dia-botonera-lista-item-enlace u-enlace u-boton-concluida" id="btn-concluida-dia1-tarea1"></a>
                        </li>
                        <li class="calendario-dia-botonera-lista-item">
                            <a href="ServicioArchivarTarea" class="calendario-dia-botonera-lista-item-enlace u-enlace u-boton-archivada" id="btn-archivada-dia1-tarea1"></a>
                        </li>
                    </ul>
                </div>
                <div class="calendario-dia-tarea">
                    <span class="calendario-dia-tarea-descripcion u-span">
                    Reuniones o comunicaciones online con amig@s o familiares
                    <a class="calendario-dia-tarea-descripcion-enlace u-boton-ediciontarea" id="btn-edicion-dia1-tarea1" href="#"></a>
                    </span>
                </div>
                <form class="calendario-dia-formulario" id="form-edicion-dia1-tarea1" action="ServicioEdicionTarea" method="post">
                    <h3 class="calendario-dia-formulario-titulo3 u-titulo-3">Edicion Tarea</h3>
                    <label class="calendario-dia-formulario-label u-label" for="form-edicion-dia1-tarea1">Descripcion:</label>
                    <input class="calendario-dia-formulario-input u-input" name="caja_descripcion_edicion_dia1_tarea1" id="caja_descripcion_edicion_dia1_tarea1" type="text" />
                    <label class="calendario-dia-formulario-label u-label" for="form-edicion-dia1-tarea1">Fecha Realizacion:</label>
                    <input class="calendario-dia-formulario-input u-input u-fecha" name="caja_fecharealizacion_edicion_dia1_tarea1" id="caja_fecharealizacion_edicion_dia1_tarea1" type="text" />
                    <ul class="calendario-dia-formulario-lista">
                        <li class="calendario-dia-formulario-lista-item">
                            <button class="calendario-dia-formulario-lista-item-boton u-boton" id="btn-guardar-dia1-tarea1" type="submit">Guardar</button>
                        </li>
                        <li class="calendario-dia-formulario-lista-item">
                            <button class="calendario-dia-formulario-lista-item-boton u-boton" id="btn-limpiar-dia1-tarea1" type="reset">Limpiar</button>
                        </li>
                        <li class="calendario-dia-formulario-lista-item">
                            <button class="calendario-dia-formulario-lista-item-boton u-boton" id="btn-cancelar-dia1-tarea1" type="button">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
        </section>
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
        <script src="js/general.min.js">;</script>
    </body>
</html>