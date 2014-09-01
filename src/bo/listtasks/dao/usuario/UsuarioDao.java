package bo.listtasks.dao.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteNombreTabla;
import bo.listtasks.constantes.ConstanteQueriesDB;
import bo.listtasks.constantes.ConstanteUsuario;
import bo.listtasks.dao.util.ConexionBD;
import bo.listtasks.dao.util.ConfiguracionDB;
import bo.listtasks.dao.util.UtilDao;
import bo.listtasks.dto.ubicacion.Ciudad;
import bo.listtasks.dto.usuario.Usuario;
import bo.listtasks.util.UtilBO;

public class UsuarioDao {

	private ConfiguracionDB configDB;

	public UsuarioDao() {
		configDB = new ConfiguracionDB();
		configDB.establecerDatosConexion();
	}

	public boolean isUsuarioValido(Usuario u) throws SQLException {

		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {
			String nombreTabla = ConstanteNombreTabla.USUARIO;
			String nombreEsquemaDB = cnxBD.getNameBD();

			UtilDao utilDao = new UtilDao();
			String sqlSelectUsuario = ConstanteQueriesDB.SELECT;

			String codigo = ConstanteGral.CADENA_VACIA;
			String password = ConstanteGral.CADENA_VACIA;
			if (u != null) {
				codigo = "'" + u.getCodigoUsuario() + "'";
				password = "'" + u.getPasswordUsuario() + "'";
			}

			String[] datosSelectUsuario = { nombreEsquemaDB, nombreTabla };
			String querySelect = UtilBO.cambioValores(sqlSelectUsuario,
					datosSelectUsuario);
			String[] datosSelect = { codigo, password };

			String[] columnasSelectUsuario = utilDao
					.getNamesColumnsTable(querySelect);

			// Eliminando algunos campos
			String[] columnasSelectUsuarioNuevo = null;
			if (columnasSelectUsuario != null
					&& columnasSelectUsuario.length > 0) {

				UtilBO utilBo = new UtilBO();
				String[] elementosAEliminar = { ConstanteUsuario.IDUSUARIO,
						ConstanteUsuario.EMAIL };
				columnasSelectUsuarioNuevo = utilBo.eliminarElementosArreglo(
						columnasSelectUsuario, elementosAEliminar);
			}

			String whereUsuario = utilDao.construirWhere(
					columnasSelectUsuarioNuevo, datosSelect);

			System.out.println("whereUsuario:" + whereUsuario);

			String[] datos = { nombreEsquemaDB, nombreTabla, whereUsuario };

			String querySelectRest = UtilBO.cambioValores(
					ConstanteQueriesDB.SELECT_RESTRINGIDO, datos);

			System.out.println("QUERY: " + querySelectRest);

			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conexion.prepareStatement(querySelectRest);
				rs = ps.executeQuery();

				if (!rs.next()) {
					return false;
				}
				return true;

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

	public Usuario obtenerDatosUsuario(String codigoUsuario,
			String passwordUsuario) throws SQLException {

		Usuario u = new Usuario();

		if (codigoUsuario != null && passwordUsuario != null
				&& !ConstanteGral.CADENA_VACIA.equals(codigoUsuario)
				&& !ConstanteGral.CADENA_VACIA.equals(passwordUsuario)) {

			ConexionBD cnxBD = configDB.obtenerConexionConectada();
			Connection conexion = cnxBD.getConexion();

			if (conexion != null) {
				String nombreTabla = ConstanteNombreTabla.USUARIO;
				String nombreEsquemaDB = cnxBD.getNameBD();

				String[] datos = { nombreEsquemaDB, nombreTabla,
						"'" + codigoUsuario + "'", "'" + passwordUsuario + "'" };

				String query = UtilBO.cambioValores(
						ConstanteQueriesDB.USER_VALIDO, datos);

				System.out.println("QUERY: " + query);

				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					ps = conexion.prepareStatement(query);
					rs = ps.executeQuery();

					while (rs.next()) {

						Ciudad ciudad = new Ciudad();
						Calendar fechaNacimiento = Calendar.getInstance();

						int idUsuario = rs.getInt(1);
						String primerNombre = rs.getString(4);
						String segundoNombre = rs.getString(5);
						String paterno = rs.getString(6);
						String materno = rs.getString(7);
						String email = rs.getString(8);
						int telefono = rs.getInt(9);
						String idCiudad = rs.getString(10);
						Date fechNac = rs.getDate(11);
						String sexo = rs.getString(12);

						Date dateSQL = new Date(fechNac.getTime());
						ciudad.setIdCiudad(idCiudad);
						fechaNacimiento.setTime(dateSQL);

						u.setIdUsuario(idUsuario);
						u.setCodigoUsuario(codigoUsuario);
						u.setPasswordUsuario(passwordUsuario);
						u.setPrimerNombre(primerNombre);
						u.setSegundoNombre(segundoNombre);
						u.setPaterno(paterno);
						u.setMaterno(materno);
						u.setEmail(email);
						u.setTelefono(telefono);
						u.setIdCiudad(Integer.parseInt(idCiudad));
						u.setFechaNacimiento(fechaNacimiento);

						u.setSexo(sexo);
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
						System.out.println("Cerrando Conexion...");
						conexion.close();
					}
				}
			}
		}

		return u;
	}

	public boolean isExisteUsuario(Usuario u) throws SQLException {
		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {
			String nombreTabla = ConstanteNombreTabla.USUARIO;
			String nombreEsquemaDB = cnxBD.getNameBD();
			String codigo = ConstanteGral.CADENA_VACIA;
			String password = ConstanteGral.CADENA_VACIA;
			String email = ConstanteGral.CADENA_VACIA;
			if (u != null) {
				codigo = "'" + u.getCodigoUsuario() + "'";
				password = "'" + u.getPasswordUsuario() + "'";
				email = "'" + u.getEmail() + "'";
			}

			String sqlSelectUsuario = ConstanteQueriesDB.SELECT;
			String[] datosSelectUsuario = { nombreEsquemaDB, nombreTabla };
			String querySelect = UtilBO.cambioValores(sqlSelectUsuario,
					datosSelectUsuario);
			String[] datosSelect = { codigo, password, email };

			UtilDao utilDao = new UtilDao();
			String[] columnasSelectUsuario = utilDao
					.getNamesColumnsTable(querySelect);

			// Eliminando algunos campos
			String[] columnasSelectUsuarioNuevo = null;
			if (columnasSelectUsuario != null
					&& columnasSelectUsuario.length > 0) {

				UtilBO utilBo = new UtilBO();
				String[] elementosAEliminar = { ConstanteUsuario.IDUSUARIO,
						ConstanteUsuario.EMAIL };
				columnasSelectUsuarioNuevo = utilBo.eliminarElementosArreglo(
						columnasSelectUsuario, elementosAEliminar);
			}

			String whereUsuario = utilDao.construirWhere(
					columnasSelectUsuarioNuevo, datosSelect);

			System.out.println("whereUsuario:" + whereUsuario);

			String[] datos = { nombreEsquemaDB, nombreTabla, whereUsuario };

			String querySelectRest = UtilBO.cambioValores(
					ConstanteQueriesDB.SELECT_RESTRINGIDO, datos);

			System.out.println("QUERY: " + querySelectRest);

			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conexion.prepareStatement(querySelectRest);
				rs = ps.executeQuery();

				if (!rs.next()) {
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

		return true;
	}

	public boolean nuevoUsuario(Usuario u) throws SQLException {
		ConexionBD cnxBD = configDB.obtenerConexionConectada();
		Connection conexion = cnxBD.getConexion();

		if (conexion != null) {
			UtilDao utilDao = new UtilDao();
			String nombreTabla = ConstanteNombreTabla.USUARIO;
			String nombreEsquemaDB = cnxBD.getNameBD();
			String codigo = ConstanteGral.CADENA_VACIA;
			String password = ConstanteGral.CADENA_VACIA;
			String email = ConstanteGral.CADENA_VACIA;

			String[] datosSelect = { nombreEsquemaDB, nombreTabla };

			String querySelect = UtilBO.cambioValores(
					ConstanteQueriesDB.SELECT, datosSelect);

			String[] nombresColumnasArr = utilDao
					.getNamesColumnsTable(querySelect);

			String nombresColumnas = UtilBO
					.convertStringToArrayComas(nombresColumnasArr);

			String[] valoresUsuarioArr = new String[3];

			if (u != null) {
				codigo = "'" + u.getCodigoUsuario() + "'";
				password = "'" + u.getPasswordUsuario() + "'";
				email = "'" + u.getEmail() + "'";
			}

			valoresUsuarioArr[0] = codigo;
			valoresUsuarioArr[1] = password;
			valoresUsuarioArr[2] = email;

			String valoresUsuario = UtilBO
					.convertStringToArrayComas(valoresUsuarioArr);

			String[] datos = { nombreEsquemaDB, nombreTabla, nombresColumnas,
					valoresUsuario };

			String queryInsert = UtilBO.cambioValores(
					ConstanteQueriesDB.INSERT, datos);

			System.out.println("QUERY: " + queryInsert);

			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				ps = conexion.prepareStatement(queryInsert);
				rs = ps.executeQuery();

				if (!rs.next()) {
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

		return true;
	}
}
