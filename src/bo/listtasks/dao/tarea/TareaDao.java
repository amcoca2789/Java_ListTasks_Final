package bo.listtasks.dao.tarea;

import bo.listtasks.dao.util.ConfiguracionDB;

public class TareaDao {
	private ConfiguracionDB configDB;

	public TareaDao() {
		configDB = new ConfiguracionDB();
		configDB.establecerDatosConexion();
	}
}
