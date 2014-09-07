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
 * Servlet implementation class ServicioConclusionTarea
 */
public class ServicioEdicionTarea extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicioEdicionTarea() {
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
		System.out.println("Estamos editando la tarea");

		HttpSession sess = request.getSession(false);
		if (sess != null) {

			String idUsuario = String.valueOf(sess
					.getAttribute(ConstanteUsuario.IDUSUARIO));

			String nroDiaStr = request.getParameter("caja-nro-dia");
			String nroTareaStr = request.getParameter("caja-nro-tarea");

			int idTarea = -1;
			String descripcionTarea = null;
			String fechaRealizacionStr = null;
			String idTareaStr = "-1";
			if (nroDiaStr != null && nroTareaStr != null) {
				int nroDia = Integer.parseInt(nroDiaStr);
				int nroTarea = Integer.parseInt(nroTareaStr);
				String diaX = "dia" + nroDia;
				String tareaY = "tarea" + nroTarea;
				String diaXtareaY = diaX + "-" + tareaY;
				String idCajaEdicionIdTarea = "caja-nro-idtarea-edicion-"
						+ diaXtareaY;
				String idCajaEdicionDescripcionTarea = "caja_descripcion_edicion-"
						+ diaXtareaY;
				String idCajaEdicionFechRealTarea = "caja_fecharealizacion_edicion-"
						+ diaXtareaY;

				idTareaStr = request.getParameter(idCajaEdicionIdTarea);
				descripcionTarea = request
						.getParameter(idCajaEdicionDescripcionTarea);
				fechaRealizacionStr = request
						.getParameter(idCajaEdicionFechRealTarea);

				UtilBO utilBo = new UtilBO();

				try {
					Calendar fechaRealizacionTarea = utilBo
							.convertStringToCalendar(fechaRealizacionStr);

					if (idTareaStr != null && !idTareaStr.isEmpty()) {
						idTarea = Integer.parseInt(idTareaStr);

						Tarea t = new Tarea();
						t.setIdTarea(idTarea);
						t.setDescripcionTarea(descripcionTarea);
						t.setFechaRealizacionTarea(fechaRealizacionTarea);
						t.setIdUsuario(Integer.parseInt(idUsuario));
						t.setEstadoTarea(ConstanteGral.ESTADO_EN_DESARROLLO);

						System.out.println("TAREA:" + t);

						TareaDao tDao = new TareaDao();
						boolean isEditado = tDao.edicionTarea(t);

						if (!isEditado) {
							System.out.println("No se edito la tarea");
						}
					}else{
						System.out.println("IdTarea nulo o vacio");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					String url_destino = ConstantesRutasServlet.RUTA_GESTION_TAREA;
					RequestDispatcher dispatcher = request
							.getRequestDispatcher(url_destino);
					dispatcher.forward(request, response);
				}
			} else {
				System.out.println("nroDia o nroTarea es nula o vacia");
			}

		} else {
			System.out.println("Sesion nula");
		}

	}
}
