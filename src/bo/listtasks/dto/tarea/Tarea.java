package bo.listtasks.dto.tarea;

import java.util.Calendar;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.util.UtilBO;

public class Tarea {
	public int idTarea;
	private String descripcionTarea;
	private Calendar fechaRealizacionTarea;
	private int idUsuario;
	private String estadoTarea;

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

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEstadoTarea() {
		return estadoTarea;
	}

	public void setEstadoTarea(String estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

	@Override
	public String toString() {
		UtilBO uBo = new UtilBO();

		String fechaRealizacionTareaStr = uBo
				.convertirCalendarToStringFechaYTiempo(fechaRealizacionTarea);
		StringBuilder builder = new StringBuilder();
		builder.append("Tarea [idTarea=");
		builder.append(idTarea);
		builder.append(", descripcionTarea=");
		builder.append(descripcionTarea);
		builder.append(", fechaRealizacionTarea=");
		builder.append(fechaRealizacionTareaStr);
		builder.append(", idUsuario=");
		builder.append(idUsuario);
		builder.append(", estadoTarea=");
		builder.append(estadoTarea);
		builder.append("]");
		return builder.toString();
	}

	public boolean isVacio() {

		if (descripcionTarea != null && fechaRealizacionTarea != null
				&& !ConstanteGral.CADENA_VACIA.equals(descripcionTarea)) {
			return false;
		}

		return true;
	}
}
