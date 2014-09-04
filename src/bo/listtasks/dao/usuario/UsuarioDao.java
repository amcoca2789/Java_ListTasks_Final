package bo.listtasks.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteNombreTabla;
import bo.listtasks.constantes.ConstanteQueriesDB;
import bo.listtasks.constantes.ConstanteUsuario;
import bo.listtasks.dao.util.ConexionBD;
import bo.listtasks.dao.util.ConfiguracionDB;
import bo.listtasks.dto.usuario.Usuario;
import bo.listtasks.util.UtilBO;

public class UsuarioDao {

	private ConfiguracionDB configDB;

	public UsuarioDao() {
		configDB = new ConfiguracionDB();
		configDB.establecerDatosConexion();
	}

	public boolean adicionUsuario(Usuario u) throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {

			if (u != null) {
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
							System.out.println("CONEXION CERRADA");
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
