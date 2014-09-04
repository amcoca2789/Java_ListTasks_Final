package bo.listtasks.dao.tarea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteNombreTabla;
import bo.listtasks.constantes.ConstanteQueriesDB;
import bo.listtasks.constantes.ConstanteTarea;
import bo.listtasks.dao.util.ConexionBD;
import bo.listtasks.dao.util.ConfiguracionDB;
import bo.listtasks.dao.util.UtilDao;
import bo.listtasks.dto.tarea.Tarea;
import bo.listtasks.dto.usuario.Usuario;
import bo.listtasks.util.UtilBO;

public class TareaDao {
	private ConfiguracionDB configDB;

	public TareaDao() {
		configDB = new ConfiguracionDB();
		configDB.establecerDatosConexion();
	}

	public boolean adicionTarea(Tarea t) throws SQLException {
		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {

			if (t != null) {
				System.out.println("TAREA:" + t);
				if (!t.isVacio()) {

					String nombreEsquemaDB = cnxBD.getNameBD();
					String nombreTabla = ConstanteNombreTabla.TAREA;

					String descripcionTarea = t.getDescripcionTarea();
					Calendar fechaRealizacion = t.getFechaRealizacionTarea();
					int idUsuario = t.getIdUsuario();

					String fechaRealizacionStr = UtilBO
							.convertCalendarToDateOracle(fechaRealizacion);
					String idUsuarioStr = String.valueOf(idUsuario);

					String[] tipoDatos = { ConstanteGral.TIPO_VARCHAR2,
							ConstanteGral.TIPO_DATE, ConstanteGral.TIPO_NUMBER };
					String[] datosARegistrar = { descripcionTarea,
							fechaRealizacionStr, idUsuarioStr };
					String[] columnasInsertar = { ConstanteTarea.DESCRIPCION,
							ConstanteTarea.FECHAREALIZ,
							ConstanteTarea.IDUSUARIO };

					String[] datosAUtilizarConvertidos = UtilBO
							.convertirDatosADatosSQL(tipoDatos, datosARegistrar);

					String datosARegistrarStr = UtilBO
							.convertirArrayToStringSeparadoCaracter(
									datosAUtilizarConvertidos,
									ConstanteGral.COMA);
					String columnasInsertarStr = UtilBO
							.convertirArrayToStringSeparadoCaracter(
									columnasInsertar, ConstanteGral.COMA);

					String queryDefault = ConstanteQueriesDB.INSERT;

					String[] datos = { nombreEsquemaDB, nombreTabla,
							columnasInsertarStr, datosARegistrarStr };
					String queryInsert = UtilBO.cambioValores(queryDefault,
							datos);

					System.out.println("queryInsert:" + queryInsert);

					PreparedStatement ps = null;

					try {
						ps = conexion.prepareStatement(queryInsert);
						int resultado = ps.executeUpdate();

						if (resultado == 1) {
							System.out
									.println("Se adiciono el registro correctamente...");
							return true;
						}
						return false;

					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						if (ps != null) {
							ps.close();
						}
						if (conexion != null) {
							System.out.println("CONEXION CERRADA");
							conexion.close();
						}
					}
				} else {
					System.out.println("OBJETO VACIO");
				}

			} else {
				System.out.println("El OBJETO es nulo");
			}
		} else {
			System.out.println("No se obtuvo conexion");
		}

		return false;
	}

	public List<Tarea> obtenerTareasUsuario(Usuario usuario)
			throws SQLException {

		List<Tarea> tareasUsuario = null;

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {

			if (usuario != null) {
				System.out.println("obtenerTareasUsuario->usuario->" + usuario);
				tareasUsuario = new ArrayList<Tarea>();

				int idUsuario = usuario.getIdUsuario();

				String nombreEsquemaDB = cnxBD.getNameBD();
				String nombreTabla = ConstanteNombreTabla.TAREA;

				String queryDefault = ConstanteQueriesDB.SELECT_CON_RESTRICCION;

				UtilDao utilDao = new UtilDao();

				String[] datosCondicion = { String.valueOf(idUsuario) };

				String[] tipoColumnasCondicion = { ConstanteGral.TIPO_NUMBER };

				String[] nombresColumnas = { ConstanteTarea.IDUSUARIO };

				String[] datosCondicionConvertidos = UtilBO
						.convertirDatosADatosSQL(tipoColumnasCondicion,
								datosCondicion);
				String where = utilDao.construirWhere(nombresColumnas,
						datosCondicionConvertidos, ConstanteGral.OPERADOR_AND);

				String[] datosSQL = { ConstanteGral.ALL_CAMPOS,
						nombreEsquemaDB, nombreTabla, where };
				String querySelect = UtilBO.cambioValores(queryDefault,
						datosSQL);

				System.out.println("queryInsert:" + querySelect);

				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					ps = conexion.prepareStatement(querySelect);
					rs = ps.executeQuery();

					while (rs.next()) {
						int idTarea = rs.getInt(1);
						String descripcionTarea = rs.getString(2);

						String fechaRealizacionTareaStr = rs.getString(3);
						Date fechaRealizacionTareaDate = UtilBO
								.convertStringToDate(fechaRealizacionTareaStr);
						Calendar fechaRealizacionTarea = Calendar.getInstance();
						fechaRealizacionTarea
								.setTime(fechaRealizacionTareaDate);

						int idUsuarioTarea = rs.getInt(4);

						Tarea t = new Tarea();

						t.setIdTarea(idTarea);
						t.setDescripcionTarea(descripcionTarea);
						t.setFechaRealizacionTarea(fechaRealizacionTarea);
						t.setIdUsuario(idUsuarioTarea);

						tareasUsuario.add(t);

					}

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (ps != null) {
						ps.close();
					}
					if (conexion != null) {
						System.out.println("CONEXION CERRADA");
						conexion.close();
					}
				}

			} else {
				System.out.println("El usuario es nulo");
			}
		} else {
			System.out.println("No se obtuvo conexion");
		}

		return tareasUsuario;
	}

	public List<Tarea> filtrarTareasPorFecha(List<Tarea> tareas,
			Calendar fechaFiltro) {
		List<Tarea> tareasFiltradas = null;

		if (tareas != null) {
			if (fechaFiltro != null) {
				tareasFiltradas = new ArrayList<Tarea>();
				for (Tarea tarea : tareas) {
					if (fechaFiltro.equals(tarea.getFechaRealizacionTarea())) {
						tareasFiltradas.add(tarea);
					}
				}
			} else {
				System.out.println("La fecha Filtro es nula");
				return tareas;
			}

		} else {
			System.out.println("La lista de tareas es nulo");
		}

		return tareasFiltradas;
	}
}
