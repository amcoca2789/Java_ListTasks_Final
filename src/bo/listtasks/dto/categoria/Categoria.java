package bo.listtasks.dto.categoria;

public class Categoria {
	private int idCategoria;
	private String nombreCategoria;
	private String estadoRegistro; // Activado o Desactivado

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombreCategoria="
				+ nombreCategoria + ", estadoRegistro=" + estadoRegistro + "]";
	}
}
