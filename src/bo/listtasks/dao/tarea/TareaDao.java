package bo.listtasks.dao.tarea;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import bo.listtasks.constantes.ConstanteNombreTabla;
import bo.listtasks.constantes.ConstanteQueriesDB;
import bo.listtasks.dao.util.ConexionBD;
import bo.listtasks.dao.util.ConfiguracionDB;
import bo.listtasks.dao.util.UtilDao;
import bo.listtasks.dto.tarea.Tarea;
import bo.listtasks.util.UtilBO;

public class TareaDao {
	private ConfiguracionDB configDB;
	private UtilDao utilDao;

	public TareaDao() {
		configDB = new ConfiguracionDB();
		configDB.establecerDatosConexion();

		utilDao = new UtilDao();
	}

	public void insertTask(Tarea t) throws SQLException {

		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			ConexionBD cnxBD = configDB.obtenerConexionConectada();
			conexion = cnxBD.getConexion();

			String nombreTablaTask = ConstanteNombreTabla.TAREA;
			String nombreEsquemaDB = cnxBD.getNameBD();

			String datosWhere = t.obtenerCadenaWhereTareaBasico();
			String[] datosSelectTask = { nombreEsquemaDB, nombreTablaTask,
					datosWhere };
			String sqlSelectTask = UtilBO.cambioValores(
					ConstanteQueriesDB.SELECT_RESTRINGIDO, datosSelectTask);

			String[] namesColumnasTask = utilDao.getNamesColumnsTable(
					datosSelectTask, sqlSelectTask);

			/*
			 * Debido a que la primera posicion es su identificador, se debe
			 * eliminar el identificador, es decir, el primer elemento o primera
			 * columna
			 */

			String nombreColumnaAEliminar = ConstanteNombreTabla.ID_TABLA_TAREA;
			namesColumnasTask = UtilBO.eliminarElementoArreglo(
					nombreColumnaAEliminar, namesColumnasTask);
			String auxNamesColumnas = Arrays.toString(namesColumnasTask);
			String namesColumnas = auxNamesColumnas.replace("[", "(");
			namesColumnas = namesColumnas.replace("]", ")");

			if (t != null) {
				String valoresTareaQuery = t.toStringTareaQuery();
				String[] datosInsertTask = { nombreEsquemaDB, nombreTablaTask,
						namesColumnas, valoresTareaQuery };
				String sqlInsertTask = UtilBO.cambioValores(
						ConstanteQueriesDB.INSERT, datosInsertTask);

				System.out.println("sqlInsertTask:" + sqlInsertTask);

				st = conexion.createStatement();
				int resultado = st.executeUpdate(sqlInsertTask);

				if (resultado == 1) {
					System.out.println("Registro TAREA --> satisfactorio");
				} else {
					System.out.println("Registro TAREA --> erroneo");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (conexion != null) {
				System.out.println("Cerrando Conexion...");
				conexion.close();
			}
		}

	}
}
