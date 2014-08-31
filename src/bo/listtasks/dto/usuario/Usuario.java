package bo.listtasks.dto.usuario;

public class Usuario extends Persona {
	private int idUsuario;
	private String codigoUsuario;
	private String passwordUsuario;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioDto [");
		if (super.toString() != null) {
			builder.append("toString()=");
			builder.append(super.toString());
			builder.append(", ");
		}
		builder.append("idUsuario=");
		builder.append(idUsuario);
		builder.append(", ");
		if (codigoUsuario != null) {
			builder.append("codigoUsuario=");
			builder.append(codigoUsuario);
			builder.append(", ");
		}
		if (passwordUsuario != null) {
			builder.append("passwordUsuario=");
			builder.append(passwordUsuario);
		}
		builder.append("]");
		return builder.toString();
	}

}
