package bo.listtasks.services.usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteNombreTabla;
import bo.listtasks.constantes.ConstanteUsuario;
import bo.listtasks.constantes.ConstantesRutasServlet;
import bo.listtasks.dao.usuario.UsuarioDao;
import bo.listtasks.dto.usuario.Usuario;

/**
 * Servlet implementation class ServicioLogin
 */
public class ServicioLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicioLogin() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("--------------ServicioLogin:doPost--------------");

		String url_destino = ConstantesRutasServlet.RUTA_LOGIN;

		try {

			String codigoUsuario = request.getParameter("caja-usuario-login");
			String passwordUsuario = request
					.getParameter("caja-password-login");

			Usuario u = new Usuario();
			u.setCodigoUsuario(codigoUsuario);
			u.setPasswordUsuario(passwordUsuario);

			System.out.println("USUARIO:" + u);

			String[] columnasAConsultar = { ConstanteUsuario.CODIGO,
					ConstanteUsuario.PASSWORD };
			String[] datosUsuario = { codigoUsuario, passwordUsuario };
			String[] tipoDatos = { ConstanteGral.TIPO_VARCHAR2,
					ConstanteGral.TIPO_VARCHAR2 };

			String nombreTabla = ConstanteNombreTabla.USUARIO;

			UsuarioDao udao = new UsuarioDao();
			String operadorLogicoSQL = ConstanteGral.OPERADOR_AND;
			boolean isExisteUsuario = udao.isExisteRegistro(nombreTabla,
					columnasAConsultar, tipoDatos, datosUsuario,
					operadorLogicoSQL);

			if (isExisteUsuario) {
				System.out.println("Usuario valido...");

				HttpSession session = request.getSession(true);

				if (session != null) {
					url_destino = ConstantesRutasServlet.RUTA_GESTION_TAREA;
					System.out.println("Inicio Sesion...");
					final String idSesionUser = session.getId();

					session.setAttribute(ConstanteGral.ID_SESION_USUARIO,
							idSesionUser);
					session.setAttribute(ConstanteGral.SESION_USUARIO, u);
					session.setMaxInactiveInterval(30 * 60); // 30min

					System.out.println("Creada: "
							+ new Date(session.getCreationTime()));
					System.out.println("Eliminada/Cerrada: "
							+ new Date(session.getLastAccessedTime()));
					System.out.println("Id Sesion: " + session.getId());
				} else {
					System.out.println("No se pudo iniciar sesion...");
				}

			} else {
				System.out.println("Usuario invalido...");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Redireccionando a -> " + url_destino);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(url_destino);
			dispatcher.forward(request, response);
		}
	}
}
