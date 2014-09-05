package bo.listtasks.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteQueriesDB;
import bo.listtasks.util.UtilBO;

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
				System.out.println("CONEXION CERRADA");
				conexion.close();
			}
		}

		return nroColumnas;

	}

	public String[] getNamesColumnsTable(String sqlSelect) throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		String[] namesColumnasTask = null;

		Statement st = null;
		ResultSet rs = null;

		try {

			if (conexion != null) {
				st = conexion.createStatement();
				rs = st.executeQuery(sqlSelect);
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
				System.out.println("CONEXION CERRADA");
				conexion.close();
			}
		}

		return namesColumnasTask;
	}

	public String construirWhere(String[] columnasCondicion,
			String[] datosCondicion, String operadorLogicoSQL) {

		System.out.println("columnasCondicion[]:"
				+ Arrays.toString(columnasCondicion));
		System.out.println("datosCondicion[]:"
				+ Arrays.toString(datosCondicion));

		StringBuffer where = new StringBuffer();

		int nroColumnas = 0;
		int nroDatos = 0;

		if (columnasCondicion != null && datosCondicion != null) {
			nroColumnas = columnasCondicion.length;
			nroDatos = datosCondicion.length;

			if (nroColumnas == nroDatos) {
				where.append(" WHERE ");
				for (int i = 0; i < datosCondicion.length; i++) {
					where.append(columnasCondicion[i] + "= "
							+ datosCondicion[i]);
					where.append(ConstanteGral.ESPACIO_EN_BLANCO);

					if (i != datosCondicion.length - 1) {
						where.append(operadorLogicoSQL);
					}
				}

			} else {
				System.out
						.println("el numero de elementos de los arreglos son DISTINTOS");
			}

		} else {
			System.out.println("Datos nulos");
		}

		return where.toString();
	}

	public String[] obtenerTiposDatosColumnaTabla(String querySelect)
			throws SQLException {
		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		String[] tipoDatoColumnas = null;

		Statement st = null;
		ResultSet rs = null;

		try {

			if (conexion != null) {
				st = conexion.createStatement();
				rs = st.executeQuery(querySelect);
				ResultSetMetaData md = rs.getMetaData();

				if (md != null) {
					int nroCol = md.getColumnCount();

					tipoDatoColumnas = new String[nroCol];

					int posicion = 0;
					for (int i = 1; i <= nroCol; i++) {
						final String col_name = md.getColumnTypeName(i);
						tipoDatoColumnas[posicion] = col_name;
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
				System.out.println("CONEXION CERRADA");
				conexion.close();
			}
		}

		return tipoDatoColumnas;
	}

	public boolean isExisteRegistro(String nombreTabla,
			String[] columnasDelSelect, String[] columnasDeCondicion,
			String[] tipoDatos, String[] datosAUtilizar,
			String operadorLogicoSQL) throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {
			String nombreEsquemaDB = cnxBD.getNameBD();

			String columnasDelSelectStr = UtilBO
					.convertirArrayToStringSeparadoCaracter(columnasDelSelect,
							ConstanteGral.COMA);

			String queryDefault = ConstanteQueriesDB.SELECT_CON_RESTRICCION;

			UtilBO utilBo = new UtilBO();
			String[] datosAUtilizarConvertidos = utilBo
					.convertirDatosADatosSQL(tipoDatos, datosAUtilizar);

			String where = this.construirWhere(columnasDeCondicion,
					datosAUtilizarConvertidos, operadorLogicoSQL);

			String[] datos = { columnasDelSelectStr, nombreEsquemaDB,
					nombreTabla, where };
			String querySelect = UtilBO.cambioValores(queryDefault, datos);

			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conexion.prepareStatement(querySelect);
				rs = ps.executeQuery();

				if (rs.next()) {
					return true;
				}
				return false;

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conexion != null) {
					System.out.println("CONEXION CERRADA");
					conexion.close();
				}
			}
		}

		return false;
	}

	public String obtenerDatoToTabla(String nombreTabla, String nombreColumna,
			String tipoDeDatoColumna, String[] columnasDeCondicion,
			String[] datosAUtilizar, String[] tipoDatos,
			String operadorLogicoSQL) throws SQLException {

		String dato = null;

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {
			String nombreEsquemaDB = cnxBD.getNameBD();

			String queryDefault = ConstanteQueriesDB.SELECT_CON_RESTRICCION;

			UtilBO utilBo = new UtilBO();
			String[] datosAUtilizarConvertidos = utilBo
					.convertirDatosADatosSQL(tipoDatos, datosAUtilizar);

			String where = this.construirWhere(columnasDeCondicion,
					datosAUtilizarConvertidos, operadorLogicoSQL);

			String[] datos = { nombreColumna, nombreEsquemaDB, nombreTabla,
					where };

			String querySelect = UtilBO.cambioValores(queryDefault, datos);

			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conexion.prepareStatement(querySelect);
				rs = ps.executeQuery();

				if (rs.next()) {
					dato = String.valueOf(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conexion != null) {
					System.out.println("CONEXION CERRADA");
					conexion.close();
				}
			}
		}

		return dato;
	}

}
