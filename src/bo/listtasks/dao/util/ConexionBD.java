package bo.listtasks.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.util.UtilBO;

public class ConexionBD {
	private Connection conexion;

	/** Datos de la BD */
	private String usuarioBD;
	private String passwordBD;
	private String nameBD;
	private int puertoBD;

	/** Gestor de la BD */
	private String cadenaConexionBD;
	private String driver;

	/** Datos URL JDBC */
	private String[] datosUrlJDBC;

	/**
	 * @return the usuarioBD
	 */
	public String getUsuarioBD() {
		return usuarioBD;
	}

	/**
	 * @param usuarioBD
	 *            the usuarioBD to set
	 */
	public void setUsuarioBD(String usuarioBD) {
		this.usuarioBD = usuarioBD;
	}

	/**
	 * @return the passwordBD
	 */
	public String getPasswordBD() {
		return passwordBD;
	}

	/**
	 * @param passwordBD
	 *            the passwordBD to set
	 */
	public void setPasswordBD(String passwordBD) {
		this.passwordBD = passwordBD;
	}

	/**
	 * @return the nameBD
	 */
	public String getNameBD() {
		return nameBD;
	}

	/**
	 * @param nameBD
	 *            the nameBD to set
	 */
	public void setNameBD(String nameBD) {
		this.nameBD = nameBD;
	}

	/**
	 * @return the puertoBD
	 */
	public int getPuertoBD() {
		return puertoBD;
	}

	/**
	 * @param puertoBD
	 *            the puertoBD to set
	 */
	public void setPuertoBD(int puertoBD) {
		this.puertoBD = puertoBD;
	}

	/**
	 * @return the cadenaConexionBD
	 */
	public String getCadenaConexionBD() {
		return cadenaConexionBD;
	}

	/**
	 * @param cadenaConexionBD
	 *            the cadenaConexionBD to set
	 */
	public void setCadenaConexionBD(String cadenaConexionBD) {
		this.cadenaConexionBD = cadenaConexionBD;
	}

	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return the datosUrlJDBC
	 */
	public String[] getDatosUrlJDBC() {
		return datosUrlJDBC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConexionBD [");
		if (usuarioBD != null) {
			builder.append("usuarioBD=");
			builder.append(usuarioBD);
			builder.append(", ");
		}
		if (passwordBD != null) {
			builder.append("passwordBD=");
			builder.append(passwordBD);
			builder.append(", ");
		}
		if (nameBD != null) {
			builder.append("nameBD=");
			builder.append(nameBD);
			builder.append(", ");
		}
		builder.append("puertoBD=");
		builder.append(puertoBD);
		builder.append(", ");
		if (cadenaConexionBD != null) {
			builder.append("cadenaConexionBD=");
			builder.append(cadenaConexionBD);
			builder.append(", ");
		}
		if (driver != null) {
			builder.append("driver=");
			builder.append(driver);
			builder.append(", ");
		}
		if (datosUrlJDBC != null) {
			builder.append("datosUrlJDBC=");
			builder.append(Arrays.toString(datosUrlJDBC));
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @param datosUrlJDBC
	 *            the datosUrlJDBC to set
	 */
	public void setDatosUrlJDBC(String[] datosUrlJDBC) {
		this.datosUrlJDBC = datosUrlJDBC;
	}

	/**
	 * @return the conexion
	 */
	public Connection getConexion() {
		return conexion;
	}

	public boolean isTodosLosCamposInformados() {
		boolean isNulos = this.usuarioBD == null || this.passwordBD == null
				|| this.nameBD == null || this.datosUrlJDBC == null;
		boolean isVacios = ConstanteGral.CADENA_VACIA.equals(this.usuarioBD)
				|| ConstanteGral.CADENA_VACIA.equals(this.passwordBD)
				|| ConstanteGral.CADENA_VACIA.equals(this.nameBD);
		boolean isArregloVacio = this.datosUrlJDBC == null ? true
				: this.datosUrlJDBC.length <= 0;

		if (!isNulos && !isVacios && !isArregloVacio) {
			return true;
		}
		return false;
	}

	public void abrirConexion() {
		if (this.isTodosLosCamposInformados()) {

			try {
				Class.forName(this.driver);

				String url = UtilBO.cambioValores(this.cadenaConexionBD,
						this.datosUrlJDBC);

				this.setCadenaConexionBD(url);

				conexion = DriverManager.getConnection(url, this.usuarioBD,
						this.passwordBD);
				System.out.println("Exito... conexion");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			System.out
					.println("Error. Faltan campos para obtener una conexion");
		}
	}

	public void cerrarConexion() {
		try {
			if (this.conexion != null && !this.conexion.isClosed()) {
				this.conexion.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
