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
import bo.listtasks.dao.util.UtilDao;
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

			String[] columnasDelSelect = { ConstanteUsuario.CODIGO };
			String[] columnasDeCondicion = { ConstanteUsuario.CODIGO,
					ConstanteUsuario.PASSWORD };
			String[] datosAUtilizar = { codigoUsuario, passwordUsuario };
			String[] tipoDatos = { ConstanteGral.TIPO_VARCHAR2,
					ConstanteGral.TIPO_VARCHAR2 };

			String nombreTabla = ConstanteNombreTabla.USUARIO;

			String operadorLogicoSQL = ConstanteGral.OPERADOR_AND;

			UtilDao utilDao = new UtilDao();

			boolean isExisteUsuario = utilDao.isExisteRegistro(nombreTabla,
					columnasDelSelect, columnasDeCondicion, tipoDatos,
					datosAUtilizar, operadorLogicoSQL);

			String nombreColumna = ConstanteUsuario.IDUSUARIO;
			String tipoDeDatoColumna = ConstanteGral.TIPO_NUMBER;
			String idUsuario = utilDao.obtenerDatoToTabla(nombreTabla,
					nombreColumna, tipoDeDatoColumna, columnasDeCondicion,
					datosAUtilizar, tipoDatos, operadorLogicoSQL);

			if (isExisteUsuario && idUsuario != null) {
				System.out.println("Usuario valido...");

				u.setIdUsuario(Integer.parseInt(idUsuario));

				HttpSession session = request.getSession(true);

				if (session != null) {
					url_destino = ConstantesRutasServlet.RUTA_GESTION_TAREA;
					System.out.println("Inicio Sesion...");
					final String idSesionUser = session.getId();

					session.setAttribute(ConstanteUsuario.IDUSUARIO, idUsuario);
					session.setAttribute(ConstanteGral.ID_SESION_USUARIO,
							idSesionUser);
					session.setAttribute(ConstanteGral.SESION_OBJETO_USUARIO, u);
					session.setMaxInactiveInterval(30 * 60); // 30min

				} else {
					System.out.println("No se pudo iniciar sesion...");
				}

			} else {
				System.out.println("Usuario invalido...");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(url_destino);
			dispatcher.forward(request, response);
		}
	}
}
