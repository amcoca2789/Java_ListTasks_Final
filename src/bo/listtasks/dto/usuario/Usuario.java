package bo.listtasks.dto.usuario;

import bo.listtasks.constantes.ConstanteGral;

public class Usuario {
	private int idUsuario;
	private String codigoUsuario;
	private String passwordUsuario;
	private String email;

	/**
	 * @return the idUsuario
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the codigoUsuario
	 */
	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	/**
	 * @param codigoUsuario
	 *            the codigoUsuario to set
	 */
	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	/**
	 * @return the passwordUsuario
	 */
	public String getPasswordUsuario() {
		return passwordUsuario;
	}

	/**
	 * @param passwordUsuario
	 *            the passwordUsuario to set
	 */
	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [idUsuario=");
		builder.append(idUsuario);
		builder.append(", codigoUsuario=");
		builder.append(codigoUsuario);
		builder.append(", passwordUsuario=");
		builder.append(passwordUsuario);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}

	public boolean isVacio() {
		if (this.codigoUsuario != null && this.passwordUsuario != null
				&& this.email != null
				&& !ConstanteGral.CADENA_VACIA.equals(this.codigoUsuario)
				&& !ConstanteGral.CADENA_VACIA.equals(this.passwordUsuario)
				&& !ConstanteGral.CADENA_VACIA.equals(this.email)) {
			return false;
		}
		return true;
	}

}
