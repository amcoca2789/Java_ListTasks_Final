package bo.listtasks.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteNombreTabla;
import bo.listtasks.constantes.ConstanteQueriesDB;
import bo.listtasks.constantes.ConstanteUsuario;
import bo.listtasks.dao.util.ConexionBD;
import bo.listtasks.dao.util.ConfiguracionDB;
import bo.listtasks.dao.util.UtilDao;
import bo.listtasks.dto.usuario.Usuario;
import bo.listtasks.util.UtilBO;

public class UsuarioDao {

	private ConfiguracionDB configDB;

	public UsuarioDao() {
		configDB = new ConfiguracionDB();
		configDB.establecerDatosConexion();
	}

	public boolean isExisteRegistro(String nombreTabla,
			String[] columnasAConsultar, String[] tipoDatos,
			String[] datosAUtilizar, String operadorLogicoSQL)
			throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {
			String nombreEsquemaDB = cnxBD.getNameBD();

			String querySelect = ConstanteGral.CADENA_VACIA;
			String queryDefault = ConstanteQueriesDB.SELECT;

			String where = ConstanteGral.CADENA_VACIA;

			String[] datosQuery = new String[3];
			datosQuery[0] = nombreEsquemaDB;
			datosQuery[1] = nombreTabla;
			datosQuery[2] = where;

			querySelect = UtilBO.cambioValores(queryDefault, datosQuery);

			UtilDao utilDao = new UtilDao();
			String[] datosAUtilizarConvertidos = UtilBO
					.convertirDatosADatosSQL(tipoDatos, datosAUtilizar);

			queryDefault = ConstanteQueriesDB.SELECT_RESTRINGIDO;

			// Construccion del where
			where = utilDao.construirWhere(columnasAConsultar,
					datosAUtilizarConvertidos, operadorLogicoSQL);

			datosQuery[0] = nombreEsquemaDB;
			datosQuery[1] = nombreTabla;
			datosQuery[2] = where;
			querySelect = UtilBO.cambioValores(queryDefault, datosQuery);

			System.out.println("querySelect:" + querySelect);

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
					System.out.println("Cerrando Conexion...");
					conexion.close();
				}
			}
		}

		return false;
	}

	public boolean adicionUsuario(Usuario u) throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {

			if (u != null) {
				System.out.println("USUARIO:" + u);
				if (!u.isVacio()) {

					String nombreEsquemaDB = cnxBD.getNameBD();
					String nombreTabla = ConstanteNombreTabla.USUARIO;

					String codigoUsuario = u.getCodigoUsuario();
					String passwordUsuario = u.getPasswordUsuario();
					String email = u.getEmail();

					String[] tipoDatos = { ConstanteGral.TIPO_VARCHAR2,
							ConstanteGral.TIPO_VARCHAR2,
							ConstanteGral.TIPO_VARCHAR2 };
					String[] datosARegistrar = { codigoUsuario,
							passwordUsuario, email };
					String[] columnasInsertar = { ConstanteUsuario.CODIGO,
							ConstanteUsuario.PASSWORD, ConstanteUsuario.EMAIL };

					String[] datosAUtilizarConvertidos = UtilBO
							.convertirDatosADatosSQL(tipoDatos, datosARegistrar);

					String datosARegistrarStr = UtilBO
							.convertStringToArrayComas(datosAUtilizarConvertidos);
					String columnasInsertarStr = UtilBO
							.convertStringToArrayComas(columnasInsertar);

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
									.println("Se registro correctamente el USUARIO...");
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
					System.out.println("USUARIO VACIO");
				}

			} else {
				System.out.println("El usuario es nulo");
			}

		}

		return false;
	}
}
