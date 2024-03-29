package bo.listtasks.constantes;

public class ConstanteQueriesDB {
	public static final String USER_VALIDO = "SELECT * FROM {0}.{1} WHERE CODIGOUSUARIO={2} AND PASSWORDUSUARIO={3}";
	public static final String ALL_DATOS_USER = "SELECT {0} FROM {1}.{2} WHERE CODIGOUSUARIO={3} AND PASSWORDUSUARIO={4}";
	public static final String SELECT_SIN_RESTRICCION = "SELECT {0} FROM {1}.{2}";
	public static final String SELECT_CON_RESTRICCION = "SELECT {0} FROM {1}.{2} {3}";
	public static final String INSERT = "INSERT INTO {0}.{1} ({2}) VALUES ({3})";
	public static final String FORMATO_DATE_ORACLE = "'YYYY-MM-DD HH24:MI:SS'";
	public static final String ESTRUCTURA_DATE_ORACLE = "TO_DATE({0}, {1})";
	public static final String UPDATE_CON_RESTRICCION = "UPDATE {0}.{1} SET {2} {3}";
	public static final String UPDATE_SIN_RESTRICCION = "UPDATE {0}.{1} SET {2}";
}
