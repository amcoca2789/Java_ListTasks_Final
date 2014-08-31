<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- HTML -->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=1, initial-scale=1.0">
        <title>ListTasks | Crea tu propia lista de tareas</title>
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
                        <a class="cabecera-navegacion-lista-item-enlace u-enlace" href="#">Comience a Disfrutar</a>
                    </li>
                    <li class="cabecera-navegacion-lista-item">
                        <a class="cabecera-navegacion-lista-item-enlace u-enlace" href="#">Caracteristicas</a>
                    </li>
                    <li class="cabecera-navegacion-lista-item">
                        <a class="cabecera-navegacion-lista-item-enlace u-enlace" href="#">Contacto</a>
                    </li>
                </ul>
            </nav>
        </header>
        <section class="accion">
            <div class="accion-producto">
                <h2 class="accion-producto-titulo2 u-titulo-2">ListTasks</h2>
                <h3 class="accion-producto-titulo3 u-titulo-3">Crea tu propia lista de tareas</h3>
            </div>
            <img class="accion-fondo u-imagen" src="css/imagenes/fondos/fondo-1.jpg" alt="fondo-1" />
            <div class="accion-call">
                <ul class="accion-call-lista">
                    <li class="accion-call-lista-item">
                        <a class="accion-call-lista-item-enlace u-enlace" id="btn-form-signup" href="#">Sign Up</a>
                    </li>
                    <li class="accion-call-lista-item">
                        <a class="accion-call-lista-item-enlace u-enlace" href="#" id="btn-form-login">Login</a>
                    </li>
                </ul>
                <form class="accion-call-formulario" id="form-signup" action="ServiceSignUp" method="post">
                    <h3 class="accion-call-formulario-titulo2 u-titulo-2">Registro</h3>
                    <label class="accion-call-formulario-label u-label" for="form-signup">Usuario:</label>
                    <input class="accion-call-formulario-input u-input" id="caja-usuario-signup" name="caja-usuario-signup" type="text"/>
                    <label class="accion-call-formulario-label u-label" for="form-signup">Password:</label>
                    <input class="accion-call-formulario-input u-input" id="caja-password-signup" name="caja-password-signup" type="password"/>
                    <label class="accion-call-formulario-label u-label" for="form-signup">Email:</label>
                    <input class="accion-call-formulario-input u-input" id="caja-email-signup" name="caja-email-signup" type="text"/>
                    <ul class="accion-call-formulario-lista">
                        <li class="accion-call-formulario-lista-item">
                            <button class="accion-call-formulario-lista-item-boton u-boton" type="submit">Registrar</button>
                        </li>
                        <li class="accion-call-formulario-lista-item">
                            <button class="accion-call-formulario-lista-item-boton u-boton" type="reset">Limpiar</button>
                        </li>
                    </ul>
                </form>
                <form class="accion-call-formulario" id="form-login" action="ServiceLogin" method="post">
                    <h3 class="accion-call-formulario-titulo2 u-titulo-2">Login</h3>
                    <label class="accion-call-formulario-label u-label" for="form-login">Usuario:</label>
                    <input class="accion-call-formulario-input u-input" id="caja-usuario-login" name="caja-usuario-login" type="text"/>
                    <label class="accion-call-formulario-label u-label" for="form-login">Password:</label>
                    <input class="accion-call-formulario-input u-input" id="caja-password-login" name="caja-password-login" type="password"/>

                    <ul class="accion-call-formulario-lista">
                        <li class="accion-call-formulario-lista-item">
                            <button class="accion-call-formulario-lista-item-boton u-boton" type="submit">Ingresar</button>
                        </li>
                        <li class="accion-call-formulario-lista-item">
                            <button class="accion-call-formulario-lista-item-boton u-boton" type="reset">Limpiar</button>
                        </li>
                    </ul>
                </form>
            </div>
        </section>

        <section class="caracteristica">
            <figure class="caracteristica-figura">
                <img src="css/imagenes/fondos/fondo-2.jpg" alt="fondo-2" class="caracteristica-figura-imagen u-imagen">
                <figcaption class="caracteristica-figura-figcaption">
                    <h2 class="caracteristica-figura-figcaption-titulo2 u-titulo-2">Agilidad:</h2>
                    <h3 class="caracteristica-figura-figcaption-titulo3 u-titulo-3">Crea y guarda tus tareas facilmente</h3>
                </figcaption>
            </figure>
        </section>
        <section class="caracteristica">
            <figure class="caracteristica-figura">
                <img src="css/imagenes/fondos/fondo-3.jpg" alt="fondo-3" class="caracteristica-figura-imagen u-imagen">
                <figcaption class="caracteristica-figura-figcaption">
                    <h2 class="caracteristica-figura-figcaption-titulo2 u-titulo-2">Recordatorios:</h2>
                    <h3 class="caracteristica-figura-figcaption-titulo3 u-titulo-3">Nunca olvides tus tareas</h3>
                </figcaption>
            </figure>
        </section>
        <section class="caracteristica">
            <figure class="caracteristica-figura">
                <img src="css/imagenes/fondos/fondo-4.jpg" alt="fondo-4" class="caracteristica-figura-imagen u-imagen">
                <figcaption class="caracteristica-figura-figcaption">
                    <h2 class="caracteristica-figura-figcaption-titulo2 u-titulo-2">App Movil:</h2>
                    <h3 class="caracteristica-figura-figcaption-titulo3 u-titulo-3">Utilizalo en cualquier lugar</h3>
                </figcaption>
            </figure>
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