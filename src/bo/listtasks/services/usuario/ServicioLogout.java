package bo.listtasks.services.usuario;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstantesRutasServlet;

/**
 * Servlet implementation class ServicioLogout
 */
public class ServicioLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicioLogout() {
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
		HttpSession sess = request.getSession(false);
		if (sess != null) {
			String idSesionUsuario = String.valueOf(sess
					.getAttribute(ConstanteGral.ID_SESION_USUARIO));
			sess.removeAttribute(ConstanteGral.ID_SESION_USUARIO);
			sess.removeAttribute(ConstanteGral.SESION_OBJETO_USUARIO);
			sess.invalidate();

			System.out.println("#Sesion CERRADA:" + idSesionUsuario);

			String url_destino = ConstantesRutasServlet.RUTA_LOGIN;
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(url_destino);
			dispatcher.forward(request, response);
		}
	}
}
