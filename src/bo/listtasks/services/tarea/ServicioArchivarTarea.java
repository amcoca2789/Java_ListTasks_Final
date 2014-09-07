package bo.listtasks.services.tarea;

import java.io.IOException;
import java.sql.SQLException;

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

/**
 * Servlet implementation class ServicioConclusionTarea
 */
public class ServicioArchivarTarea extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServicioArchivarTarea() {
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
		System.out.println("Estamos archivando la tarea");

		HttpSession sess = request.getSession(false);
		if (sess != null) {

			String idUsuario = String.valueOf(sess
					.getAttribute(ConstanteUsuario.IDUSUARIO));

			String nroDiaStr = request.getParameter("caja-nro-dia");
			String nroTareaStr = request.getParameter("caja-nro-tarea");

			int idTarea = -1;
			String idTareaStr = "-1";
			if (nroDiaStr != null && nroTareaStr != null) {
				int nroDia = Integer.parseInt(nroDiaStr);
				int nroTarea = Integer.parseInt(nroTareaStr);
				String diaX = "dia" + nroDia;
				String tareaY = "tarea" + nroTarea;
				String diaXtareaY = diaX + "-" + tareaY;
				String idCajaEdicionIdTarea = "caja-nro-idtarea-archivar-"
						+ diaXtareaY;

				idTareaStr = request.getParameter(idCajaEdicionIdTarea);
			}

			try {

				System.out.println("idTareaStr:" + idTareaStr);
				if (idTareaStr != null && !idTareaStr.isEmpty()) {
					idTarea = Integer.parseInt(idTareaStr);
				} else {
					System.out
							.println("IDTAREA es nulo o vacio->" + idTareaStr);
				}

				boolean isEditado = false;
				if (idTarea != -1) {
					TareaDao tDao = new TareaDao();
					isEditado = tDao.cambioEstado(idUsuario, idTarea,
							ConstanteGral.ESTADO_ARCHIVADA);
				} else {
					System.out.println("IDTAREA invalido->" + idTarea);
				}

				if (!isEditado) {
					System.out.println("No se edito la tarea");
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
