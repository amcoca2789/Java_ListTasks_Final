package bo.listtasks.services.tarea;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.listtasks.constantes.ConstanteUsuario;

/**
 * Servlet implementation class ServicioCreacionTarea
 */
public class ServicioCreacionTarea extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicioCreacionTarea() {
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

		System.out
				.println("--------------ServicioCreacionTarea:doPost--------------");

		HttpSession sess = request.getSession(false);
		if (sess != null) {

			String idUsuario = String.valueOf(sess
					.getAttribute(ConstanteUsuario.IDUSUARIO));

		}

	}
}
