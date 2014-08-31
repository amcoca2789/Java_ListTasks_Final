package bo.listtasks.dao.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilDao extends ConfiguracionDB {

	private ConfiguracionDB configDB;

	public UtilDao() {
		configDB = new ConfiguracionDB();
		configDB.establecerDatosConexion();
	}

	public int obtenerNumeroColumnasTabla(String[] datosSelectTask,
			String sqlSelectTask) throws SQLException {

		int nroColumnas = 0;

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		Statement st = null;
		ResultSet rs = null;

		try {

			if (conexion != null) {
				st = conexion.createStatement();
				rs = st.executeQuery(sqlSelectTask);
				ResultSetMetaData md = rs.getMetaData();

				if (md != null) {
					nroColumnas = md.getColumnCount();
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

		return nroColumnas;

	}

	public String[] getNamesColumnsTable(String[] datosSelectTask,
			String sqlSelectTask) throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		String[] namesColumnasTask = null;

		Statement st = null;
		ResultSet rs = null;

		try {

			if (conexion != null) {
				st = conexion.createStatement();
				rs = st.executeQuery(sqlSelectTask);
				ResultSetMetaData md = rs.getMetaData();

				if (md != null) {
					int nroCol = md.getColumnCount();

					namesColumnasTask = new String[nroCol];

					int posicion = 0;
					for (int i = 1; i <= nroCol; i++) {
						final String col_name = md.getColumnName(i);
						namesColumnasTask[posicion] = col_name;
						posicion++;
					}
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

		return namesColumnasTask;
	}

}
