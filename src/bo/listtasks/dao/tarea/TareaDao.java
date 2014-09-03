package bo.listtasks.dao.tarea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteNombreTabla;
import bo.listtasks.constantes.ConstanteQueriesDB;
import bo.listtasks.constantes.ConstanteTarea;
import bo.listtasks.dao.util.ConexionBD;
import bo.listtasks.dao.util.ConfiguracionDB;
import bo.listtasks.dto.tarea.Tarea;
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
							System.out.println("Cerrando Conexion...");
							conexion.close();
						}
					}
				} else {
					System.out.println("OBJETO VACIO");
				}

			} else {
				System.out.println("El OBJETO es nulo");
			}
		}

		return false;
	}

}
