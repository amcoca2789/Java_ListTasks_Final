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
import bo.listtasks.dao.util.UtilDao;
import bo.listtasks.dto.usuario.Usuario;

/**
 * Servlet implementation class ServicioSignUp
 */
public class ServicioSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicioSignUp() {
		super();
		// TODO Auto-generated constructor stub
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

		System.out.println("--------------ServicioSignup:doPost--------------");

		String url_destino = ConstantesRutasServlet.RUTA_LOGIN;

		try {

			String codigoUsuario = request.getParameter("caja-usuario-signup");
			String passwordUsuario = request
					.getParameter("caja-password-signup");
			String emailUsuario = request.getParameter("caja-email-signup");

			Usuario u = new Usuario();
			u.setCodigoUsuario(codigoUsuario);
			u.setPasswordUsuario(passwordUsuario);
			u.setEmail(emailUsuario);

			UtilDao uDao = new UtilDao();

			String nombreTabla = ConstanteNombreTabla.USUARIO;
			/** Datos para consultar por CODIGO USUARIO */
			String[] columnasDelSelectCodigoUsuario = { ConstanteUsuario.CODIGO };
			String[] columnasDeCondicionCodigoUsuario = { ConstanteUsuario.CODIGO };
			String[] tipoDatosCodigoUsuario = { ConstanteGral.TIPO_VARCHAR2 };
			String[] datosAUtilizarCodigoUsuario = { codigoUsuario };

			boolean isExisteForCodigoUsuario = uDao.isExisteRegistro(
					nombreTabla, columnasDelSelectCodigoUsuario,
					columnasDeCondicionCodigoUsuario, tipoDatosCodigoUsuario,
					datosAUtilizarCodigoUsuario, ConstanteGral.OPERADOR_AND);

			/** Datos para consultar por EMAIL */
			String[] columnasDelSelectEmail = { ConstanteUsuario.EMAIL };
			String[] columnasDeCondicionEmail = { ConstanteUsuario.EMAIL };
			String[] tipoDatosEmail = { ConstanteGral.TIPO_VARCHAR2 };
			String[] datosAUtilizarEmail = { emailUsuario };

			boolean isExisteForEmailUsuario = uDao.isExisteRegistro(
					nombreTabla, columnasDelSelectEmail,
					columnasDeCondicionEmail, tipoDatosEmail,
					datosAUtilizarEmail, ConstanteGral.OPERADOR_AND);

			if (!isExisteForCodigoUsuario) {
				if (!isExisteForEmailUsuario) {

					UsuarioDao userDao = new UsuarioDao();
					boolean isAdicionado = userDao.adicionUsuario(u);

					HttpSession session = request.getSession(true);

					if (isAdicionado) {
						if (session != null) {
							url_destino = ConstantesRutasServlet.RUTA_GESTION_TAREA;
							System.out.println("Inicio Sesion...");
							final String idSesionUser = session.getId();

							session.setAttribute(
									ConstanteGral.ID_SESION_USUARIO,
									idSesionUser);
							session.setAttribute(
									ConstanteGral.SESION_OBJETO_USUARIO, u);
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
						System.out.println("No se pudo adicionar el usuario..");
					}

				} else {
					System.out.println("EMAIL REPETIDO:" + emailUsuario);
				}
			} else {
				System.out.println("CODIGO USUARIO REPETIDO:" + codigoUsuario);
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
