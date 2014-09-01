package bo.listtasks.dto.usuario;

import java.util.Calendar;

import bo.listtasks.constantes.ConstanteGral;

public class Persona {
	private String primerNombre;
	private String segundoNombre;
	private String paterno;
	private String materno;
	private int telefono;
	private int idCiudad;
	private int idPais;
	private Calendar fechaNacimiento;
	private String sexo;

	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * @param primerNombre
	 *            the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * @param segundoNombre
	 *            the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	/**
	 * @return the paterno
	 */
	public String getPaterno() {
		return paterno;
	}

	/**
	 * @param paterno
	 *            the paterno to set
	 */
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	/**
	 * @return the materno
	 */
	public String getMaterno() {
		return materno;
	}

	/**
	 * @param materno
	 *            the materno to set
	 */
	public void setMaterno(String materno) {
		this.materno = materno;
	}

	/**
	 * @return the telefono
	 */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the idCiudad
	 */
	public int getIdCiudad() {
		return idCiudad;
	}

	/**
	 * @param idCiudad
	 *            the idCiudad to set
	 */
	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	/**
	 * @return the idPais
	 */
	public int getIdPais() {
		return idPais;
	}

	/**
	 * @param idPais
	 *            the idPais to set
	 */
	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Calendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento
	 *            the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Calendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNombrePerfilCompleto() {
		StringBuffer nombrePerfil = new StringBuffer();

		nombrePerfil.append(this.primerNombre)
				.append(ConstanteGral.ESPACIO_EN_BLANCO)
				.append(this.segundoNombre);

		return nombrePerfil.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonaDto [primerNombre=");
		builder.append(primerNombre);
		builder.append(", segundoNombre=");
		builder.append(segundoNombre);
		builder.append(", paterno=");
		builder.append(paterno);
		builder.append(", materno=");
		builder.append(materno);
		builder.append(", telefono=");
		builder.append(telefono);
		builder.append(", idCiudad=");
		builder.append(idCiudad);
		builder.append(", idPais=");
		builder.append(idPais);
		builder.append(", fechaNacimiento=");
		builder.append(fechaNacimiento);
		builder.append(", sexo=");
		builder.append(sexo);
		builder.append("]");
		return builder.toString();
	}

}
