package bo.listtasks.dto.tarea;

import java.util.Calendar;

import bo.listtasks.util.UtilBO;

public class Tarea {
	public int idTarea;
	private String descripcionTarea;
	private Calendar fechaRealizacionTarea;
	private String etapaTarea;
	private String estadoRegistro; // Activado o Desactivado

	public int getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(int idTarea) {
		this.idTarea = idTarea;
	}

	public String getDescripcionTarea() {
		return descripcionTarea;
	}

	public void setDescripcionTarea(String descripcionTarea) {
		this.descripcionTarea = descripcionTarea;
	}

	public Calendar getFechaRealizacionTarea() {
		return fechaRealizacionTarea;
	}

	public void setFechaRealizacionTarea(Calendar fechaRealizacionTarea) {
		this.fechaRealizacionTarea = fechaRealizacionTarea;
	}

	public String getEtapaTarea() {
		return etapaTarea;
	}

	public void setEtapaTarea(String etapaTarea) {
		this.etapaTarea = etapaTarea;
	}

	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public String toStringTareaQuery() {

		String fechaRealizacionSQL = UtilBO
				.convertCalendarToDateOracle(this.fechaRealizacionTarea);

		return "Tarea [idTarea=" + idTarea + ", descripcionTarea="
				+ descripcionTarea + ", fechaRealizacionTarea="
				+ fechaRealizacionSQL + ", etapaTarea=" + etapaTarea
				+ ", estadoRegistro=" + estadoRegistro + "]";
	}

	public String obtenerCadenaWhereTareaBasico() {
		return "descripcion = '" + descripcionTarea + "'";
	}

}
