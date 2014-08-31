package bo.listtasks.services.tarea;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.dao.tarea.TareaDao;
import bo.listtasks.dto.tarea.Tarea;

/**
 * Servlet implementation class ServicioTask
 */
public class ServicioTarea extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicioTarea() {
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

		String url_destino = "dashboard.jsp";

		try {
			String descripcionTarea = null;
			Calendar fechaRealizacionTarea = Calendar.getInstance();

			Tarea t = new Tarea();

			t.setDescripcionTarea(descripcionTarea);
			t.setFechaRealizacionTarea(fechaRealizacionTarea);
			t.setEtapaTarea(ConstanteGral.SIN_DESARROLLAR);
			t.setEstadoRegistro(ConstanteGral.ACTIVADO);

			System.out.println("Service:T=" + t);

			TareaDao tDao = new TareaDao();
			tDao.insertTask(t);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(url_destino);
			dispatcher.forward(request, response);
		}
	}
}
