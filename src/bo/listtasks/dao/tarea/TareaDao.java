package bo.listtasks.dao.tarea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
				if (!t.isVacio()) {

					String nombreEsquemaDB = cnxBD.getNameBD();
					String nombreTabla = ConstanteNombreTabla.TAREA;

					String descripcionTarea = t.getDescripcionTarea();
					Calendar fechaRealizacion = t.getFechaRealizacionTarea();
					String estadoTarea = t.getEstadoTarea();
					int idUsuario = t.getIdUsuario();

					UtilBO utilBo = new UtilBO();
					String fechaRealizacionStr = utilBo
							.convertirCalendarToStringFechaYTiempo(fechaRealizacion);

					String idUsuarioStr = String.valueOf(idUsuario);

					String[] tipoDatos = { ConstanteGral.TIPO_VARCHAR2,
							ConstanteGral.TIPO_DATE, ConstanteGral.TIPO_NUMBER,
							ConstanteGral.TIPO_VARCHAR2 };
					String[] datosARegistrar = { descripcionTarea,
							fechaRealizacionStr, idUsuarioStr, estadoTarea };
					String[] columnasInsertar = { ConstanteTarea.DESCRIPCION,
							ConstanteTarea.FECHAREALIZ,
							ConstanteTarea.IDUSUARIO,
							ConstanteTarea.ESTADOTAREA };

					String[] datosAUtilizarConvertidos = utilBo
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
				tareasUsuario = new ArrayList<Tarea>();

				int idUsuario = usuario.getIdUsuario();

				String nombreEsquemaDB = cnxBD.getNameBD();
				String nombreTabla = ConstanteNombreTabla.TAREA;

				String queryDefault = ConstanteQueriesDB.SELECT_CON_RESTRICCION;

				UtilDao utilDao = new UtilDao();

				String[] datosCondicion = { String.valueOf(idUsuario),
						ConstanteGral.ESTADO_EN_DESARROLLO };

				String[] tipoColumnasCondicion = { ConstanteGral.TIPO_NUMBER,
						ConstanteGral.TIPO_VARCHAR2 };

				String[] nombresColumnas = { ConstanteTarea.IDUSUARIO,
						ConstanteTarea.ESTADOTAREA };

				UtilBO utilBo = new UtilBO();

				String[] datosCondicionConvertidos = utilBo
						.convertirDatosADatosSQL(tipoColumnasCondicion,
								datosCondicion);
				String where = utilDao.construirWhere(nombresColumnas,
						datosCondicionConvertidos, ConstanteGral.OPERADOR_AND);

				String[] datosSQL = { ConstanteGral.ALL_CAMPOS,
						nombreEsquemaDB, nombreTabla, where };
				String querySelect = UtilBO.cambioValores(queryDefault,
						datosSQL);

				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					ps = conexion.prepareStatement(querySelect);
					rs = ps.executeQuery();

					while (rs.next()) {
						int idTarea = rs.getInt(1);
						String descripcionTarea = rs.getString(2);

						String fechaRealizacionTareaStr = rs.getString(3);

						Date fechaRealizacionTareaDate = utilBo
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
				UtilBO uBo = new UtilBO();
				String fechaFiltroStr = uBo.convertirCalendarToStringFecha(
						fechaFiltro, ConstanteGral.FORMATO_FECHA_1);
				tareasFiltradas = new ArrayList<Tarea>();
				for (Tarea tarea : tareas) {
					Calendar fechaRealizacion = tarea
							.getFechaRealizacionTarea();
					String fechaRealizacionStr = uBo
							.convertirCalendarToStringFecha(fechaRealizacion,
									ConstanteGral.FORMATO_FECHA_1);

					if (fechaFiltroStr.equals(fechaRealizacionStr)) {
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

	public boolean edicionTarea(Tarea t) throws SQLException {
		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {

			if (t != null) {
				if (!t.isVacio()) {

					String nombreEsquemaDB = cnxBD.getNameBD();
					String nombreTabla = ConstanteNombreTabla.TAREA;

					int idTarea = t.getIdTarea();
					String descripcionTarea = t.getDescripcionTarea();
					Calendar fechaRealizacion = t.getFechaRealizacionTarea();
					String idTareaStr = String.valueOf(idTarea);

					UtilBO utilBo = new UtilBO();
					UtilDao utilDao = new UtilDao();
					String fechaRealizacionStr = utilBo
							.convertirCalendarToStringFechaYTiempo(fechaRealizacion);

					String queryDefault = ConstanteQueriesDB.UPDATE_CON_RESTRICCION;
					/** Where */
					String[] nombresColumnasCondicion = { ConstanteTarea.IDTAREA };
					String[] datosCondicion = { idTareaStr };
					String[] tipoDatosWhere = { ConstanteGral.TIPO_NUMBER };
					String[] datosCondicionConvertidos = utilBo
							.convertirDatosADatosSQL(tipoDatosWhere,
									datosCondicion);
					/** setUpdate */
					String[] nombreColumnasActualizar = {
							ConstanteTarea.DESCRIPCION,
							ConstanteTarea.FECHAREALIZ };
					String[] tiposColumnasActualizar = {
							ConstanteGral.TIPO_VARCHAR2,
							ConstanteGral.TIPO_DATE };
					String[] datosToActualizar = { descripcionTarea,
							fechaRealizacionStr };
					String[] datosToActualizarConvertidos = utilBo
							.convertirDatosADatosSQL(tiposColumnasActualizar,
									datosToActualizar);
					/** Where */
					System.out.println("datosCondicionConvertidos:"
							+ Arrays.toString(datosCondicionConvertidos));
					String whereUpdate = utilDao.construirWhere(
							nombresColumnasCondicion,
							datosCondicionConvertidos,
							ConstanteGral.OPERADOR_AND);
					System.out.println("whereUpdate:" + whereUpdate);
					/** setUpdate */
					System.out.println("datosToActualizarConvertidos:"
							+ Arrays.toString(datosToActualizarConvertidos));
					String setUpdate = utilDao.construirSetUpdate(
							nombreColumnasActualizar,
							datosToActualizarConvertidos);
					System.out.println("setUpdate:" + setUpdate);
					
					/** datosUpdateTarea */
					String[] datosUpdateTarea = { nombreEsquemaDB, nombreTabla,
							setUpdate, whereUpdate };

					String queryUpdate = UtilBO.cambioValores(queryDefault,
							datosUpdateTarea);

					System.out.println("queryUpdate:" + queryUpdate);

					PreparedStatement ps = null;

					try {
						ps = conexion.prepareStatement(queryUpdate);
						int resultado = ps.executeUpdate();

						if (resultado == 1) {
							System.out
									.println("Se modifico el registro correctamente...");
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

	public boolean cambioEstado(String idUsuario, int idTarea, String estado)
			throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {

			if (idTarea != -1) {
				UtilDao utilDao = new UtilDao();
				UtilBO utilBo = new UtilBO();

				String nombreEsquemaDB = cnxBD.getNameBD();
				String nombreTabla = ConstanteNombreTabla.TAREA;

				String queryDefault = ConstanteQueriesDB.UPDATE_CON_RESTRICCION;

				// "********SET************"
				System.out.println("********SET************");
				String[] nombreColumnasSet = { ConstanteTarea.ESTADOTAREA };
				String[] datosSet = { estado };
				String[] tipoDatosSet = { ConstanteGral.TIPO_VARCHAR2 };
				String[] datosSetConvertidos = utilBo.convertirDatosADatosSQL(
						tipoDatosSet, datosSet);
				System.out.println("datosSetConvertidos:"
						+ Arrays.toString(datosSetConvertidos));
				// "********WHERE************"
				System.out.println("********WHERE************");
				String[] nombresColumnasWhere = { ConstanteTarea.IDUSUARIO,
						ConstanteTarea.IDTAREA };
				String[] datosCondicionWhere = { idUsuario,
						String.valueOf(idTarea) };
				String[] tipoDatosWhere = { ConstanteGral.TIPO_NUMBER,
						ConstanteGral.TIPO_NUMBER };
				String[] datosCondicionWhereConvertidos = utilBo
						.convertirDatosADatosSQL(tipoDatosWhere,
								datosCondicionWhere);
				System.out.println("datosCondicionWhereConvertidos:"
						+ Arrays.toString(datosCondicionWhereConvertidos));

				// "********SET************"
				String setUpdate = utilDao.construirSetUpdate(
						nombreColumnasSet, datosSetConvertidos);
				System.out.println("setUpdate:[" + setUpdate + "]");
				// "********WHERE************"
				String whereUpdate = utilDao.construirWhere(
						nombresColumnasWhere, datosCondicionWhereConvertidos,
						ConstanteGral.OPERADOR_AND);
				System.out.println("whereUpdate:[" + whereUpdate + "]");

				// *** QUERY UPDATE
				String[] datos = { nombreEsquemaDB, nombreTabla, setUpdate,
						whereUpdate };
				String queryUpdate = UtilBO.cambioValores(queryDefault, datos);

				System.out.println("queryUpdate:" + queryUpdate);

				PreparedStatement ps = null;

				try {
					ps = conexion.prepareStatement(queryUpdate);
					int resultado = ps.executeUpdate();

					if (resultado == 1) {
						System.out
								.println("Se modifico el registro correctamente...");
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
				System.out.println("El IDTAREA no es valido:" + idTarea);
			}
		} else {
			System.out.println("No se pudo obtener conexion");
		}

		return false;

	}
}
