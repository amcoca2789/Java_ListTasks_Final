package bo.listtasks.services.tarea;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteUsuario;
import bo.listtasks.constantes.ConstantesRutasServlet;
import bo.listtasks.dao.tarea.TareaDao;
import bo.listtasks.dto.tarea.Tarea;
import bo.listtasks.util.UtilBO;

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

			String descripcionTarea = request
					.getParameter("caja_descripcion_creacion");
			String fechaRealizacionStr = request
					.getParameter("caja_fecharealizacion_creacion");

			UtilBO utilBo = new UtilBO();

			try {
				System.out
						.println("fechaRealizacionStr:" + fechaRealizacionStr);
				Calendar fechaRealizacionTarea = utilBo
						.convertStringToCalendar(fechaRealizacionStr);

				Tarea t = new Tarea();
				t.setDescripcionTarea(descripcionTarea);
				t.setFechaRealizacionTarea(fechaRealizacionTarea);
				t.setIdUsuario(Integer.parseInt(idUsuario));
				t.setEstadoTarea(ConstanteGral.ESTADO_EN_DESARROLLO);

				System.out.println("TAREA:" + t);

				TareaDao tDao = new TareaDao();
				boolean isInsertado = tDao.adicionTarea(t);

				if (!isInsertado) {
					System.out.println("No se inserto la tarea");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				String url_destino = ConstantesRutasServlet.RUTA_GESTION_TAREA;
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(url_destino);
				dispatcher.forward(request, response);
			}

		}

	}
}
