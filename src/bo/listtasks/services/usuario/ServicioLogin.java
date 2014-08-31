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

			UsuarioDao udao = new UsuarioDao();
			boolean isUserValido = udao.isUsuarioValido(u);

			if (isUserValido) {
				System.out.println("Usuario valido...");
				url_destino = ConstantesRutasServlet.RUTA_GESTION_TAREA;

				HttpSession session = request.getSession(true);

				if (session != null) {

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
