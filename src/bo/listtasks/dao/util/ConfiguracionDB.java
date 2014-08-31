package bo.listtasks.dao.util;

import bo.listtasks.constantes.ConstanteDriverDB;
import bo.listtasks.constantes.ConstanteUrlJDBC;
import bo.listtasks.constantes.DatoConfigBD;

public class ConfiguracionDB {
	private ConexionBD conexion;

	public void establecerDatosConexion() {

		this.conexion = new ConexionBD();

		this.conexion.setUsuarioBD(DatoConfigBD.USER_BD);
		this.conexion.setPasswordBD(DatoConfigBD.PASS_BD);
		this.conexion.setPuertoBD(DatoConfigBD.PORT_BD);
		this.conexion
				.setCadenaConexionBD(ConstanteUrlJDBC.JDBC_URL_ORACLE_THIN_SID);
		this.conexion.setDriver(ConstanteDriverDB.DRIVER_ORACLE);
		this.conexion.setNameBD(DatoConfigBD.NAME_BD);

		String[] datosUrlJDBC = { DatoConfigBD.HOST,
				String.valueOf(DatoConfigBD.PORT_BD), DatoConfigBD.NAME_SID };

		this.conexion.setDatosUrlJDBC(datosUrlJDBC);

	}

	public ConexionBD obtenerConexionConectada() {
		if (this.conexion != null) {
			this.conexion.abrirConexion();
		}

		return this.conexion;
	}
}
