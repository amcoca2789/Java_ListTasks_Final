package bo.listtasks.util;

import java.io.BufferedReader;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bo.listtasks.constantes.ConstanteGral;
import bo.listtasks.constantes.ConstanteQueriesDB;

public class UtilBO {

	public static String getJsonFromJS(HttpServletRequest request) {
		String jsonString = new String(); // this is your data sent from client
		try {
			String line = "";
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jsonString += line;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	public static String cambioValores(String cadena, String[] datos) {

		if (datos != null && datos.length > 0) {
			MessageFormat mf = new MessageFormat(cadena);
			final String resultado = mf.format(datos);
			return resultado;
		}

		return ConstanteGral.CADENA_VACIA;

	}

	public static String convertirArrayToStringSeparadoCaracter(
			String[] columnas, String caracterSeparacion) {
		String nombresColumnas = ConstanteGral.CADENA_VACIA;

		for (int i = 0; i < columnas.length; i++) {
			nombresColumnas += columnas[i];
			if (i != columnas.length - 1) {
				nombresColumnas += caracterSeparacion;
			}
		}

		return nombresColumnas;
	}

	public static String convertCalendarToDateOracle(Calendar fechaHoraInicio) {

		String[] datosFecha = UtilBO
				.convertCalendarFechaToArreglo(fechaHoraInicio);

		String[] datosTiempo = UtilBO
				.convertCalendarTiempoToArreglo(fechaHoraInicio);

		String fecha = UtilBO.cambioValores(ConstanteGral.ESTRUCTURA_FECHA_1,
				datosFecha);

		String tiempo = UtilBO.cambioValores(ConstanteGral.ESTRUCTURA_TIEMPO_1,
				datosTiempo);

		String fechaTiempo = "'" + fecha + ConstanteGral.ESPACIO_EN_BLANCO
				+ tiempo + "'";

		String[] datos = { fechaTiempo, ConstanteQueriesDB.FORMATO_DATE_ORACLE };
		String cadenaOracle = ConstanteQueriesDB.ESTRUCTURA_DATE_ORACLE;

		return UtilBO.cambioValores(cadenaOracle, datos);
	}

	public static String[] convertCalendarFechaToArreglo(
			Calendar fechaHoraInicio) {
		String[] salida = null;

		if (fechaHoraInicio != null) {
			salida = new String[3];

			int year = fechaHoraInicio.get(Calendar.YEAR);
			int month = fechaHoraInicio.get(Calendar.MONTH) + 1; // Jan = 0, dec
			int dayOfMonth = fechaHoraInicio.get(Calendar.DAY_OF_MONTH);

			salida[0] = Integer.toString(year);
			salida[1] = Integer.toString(month);
			salida[2] = Integer.toString(dayOfMonth);
		}

		return salida;
	}

	public static String[] convertCalendarTiempoToArreglo(
			Calendar fechaHoraInicio) {
		String[] salida = null;

		if (fechaHoraInicio != null) {
			salida = new String[3];

			int hourOfDay = fechaHoraInicio.get(Calendar.HOUR_OF_DAY); // 24
			int minute = fechaHoraInicio.get(Calendar.MINUTE);
			int second = fechaHoraInicio.get(Calendar.SECOND);

			String hourOfDayStr = Integer.toString(hourOfDay); // 24
			String minuteStr = Integer.toString(minute);
			String secondStr = Integer.toString(second);

			if (ConstanteGral.CADENA_NUMERO_CERO.equals(hourOfDayStr)) {
				hourOfDayStr += ConstanteGral.CADENA_NUMERO_CERO;
			}
			if (ConstanteGral.CADENA_NUMERO_CERO.equals(minuteStr)) {
				minuteStr += ConstanteGral.CADENA_NUMERO_CERO;
			}
			if (ConstanteGral.CADENA_NUMERO_CERO.equals(secondStr)) {
				secondStr += ConstanteGral.CADENA_NUMERO_CERO;
			}

			salida[0] = hourOfDayStr;
			salida[1] = minuteStr;
			salida[2] = secondStr;
		}

		return salida;
	}

	public String[] eliminarElementoArreglo(String elemento, String[] arreglo) {

		List<String> lista = new ArrayList<String>();

		if (arreglo != null && elemento != null) {

			for (String cadena : arreglo) {
				if (!elemento.equals(cadena)) {
					lista.add(cadena);
				}
			}
		}

		String[] salida = new String[lista.size()];

		for (int i = 0; i < lista.size(); i++) {
			salida[i] = lista.get(i);
		}

		return salida;

	}

	public String[] eliminarElementosArreglo(String[] arregloDatos,
			String[] arregloElementosAEliminar) {

		if (arregloDatos != null) {
			if (arregloElementosAEliminar != null) {

				int nroColumnas = arregloDatos.length;
				int nroElementosAEliminar = arregloElementosAEliminar.length;

				if (nroColumnas >= nroElementosAEliminar) {

					for (String elemento : arregloElementosAEliminar) {
						arregloDatos = this.eliminarElementoArreglo(elemento,
								arregloDatos);
					}

				}
			}
		}
		return arregloDatos;
	}

	public Date convertStringToDate(String fecha) {

		SimpleDateFormat sdf = new SimpleDateFormat(
				ConstanteGral.FORMATO_FECHA_Y_TIEMPO_1);

		Date fechaParseada = null;

		if (fecha != null) {
			try {
				fechaParseada = sdf.parse(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("La fecha es nula");
		}

		return fechaParseada;
	}

	public String[] convertirDatosADatosSQL(String[] tipoDatos, String[] datos) {

		String[] datosConvertidos = null;

		if (datos != null && datos.length > 0 && tipoDatos != null
				&& tipoDatos.length > 0 && datos.length == tipoDatos.length) {

			datosConvertidos = new String[datos.length];

			for (int i = 0; i < tipoDatos.length; i++) {
				if (ConstanteGral.TIPO_VARCHAR2.equals(tipoDatos[i])) {
					datosConvertidos[i] = "'" + datos[i] + "'";
				} else if (ConstanteGral.TIPO_DATE.equals(tipoDatos[i])) {
					Date fechaAux = this.convertStringToDate(datos[i]);
					Calendar fechaCalend = Calendar.getInstance();
					fechaCalend.setTime(fechaAux);
					datosConvertidos[i] = UtilBO
							.convertCalendarToDateOracle(fechaCalend);

				} else if (ConstanteGral.TIPO_NUMBER.equals(tipoDatos[i])) {
					datosConvertidos[i] = datos[i];
				}
			}
		} else {
			System.out.println("No se puedo realizar ninguna conversion");
		}

		return datosConvertidos;
	}

	public List<Calendar> obtenerDiasDeLaSemana(Calendar fecha) {

		List<Calendar> listaFechasSemana = null;

		if (fecha != null) {

			listaFechasSemana = new ArrayList<Calendar>();

			Calendar lunes = (Calendar) fecha.clone();
			lunes.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

			listaFechasSemana.add(lunes);

			int nroDias = 1;
			while (nroDias <= 6) {
				Calendar fechaX = (Calendar) lunes.clone();
				fechaX.add(Calendar.DATE, nroDias);
				nroDias++;
				listaFechasSemana.add(fechaX);
			}
		} else {
			System.out.println("Fecha es nulo");
		}

		return listaFechasSemana;
	}

	public String obtenerNombreDia(Calendar fecha) {

		String nombreDia = ConstanteGral.SIN_DATOS;

		if (fecha != null) {
			int nroDia = fecha.get(Calendar.DAY_OF_WEEK);

			switch (nroDia) {
			case 1:
				nombreDia = "Domingo";
				break;
			case 2:
				nombreDia = "Lunes";
				break;
			case 3:
				nombreDia = "Martes";
				break;
			case 4:
				nombreDia = "Miercoles";
				break;
			case 5:
				nombreDia = "Jueves";
				break;
			case 6:
				nombreDia = "Viernes";
				break;
			case 7:
				nombreDia = "Sabado";
				break;
			}

		} else {
			System.out.println("Fecha Nula");
		}

		return nombreDia;
	}

	public String convertirCalendarToStringFechaYTiempo(Calendar fecha) {

		String salida = ConstanteGral.SIN_DATOS;

		if (fecha != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					ConstanteGral.FORMATO_FECHA_Y_TIEMPO_1);

			salida = sdf.format(fecha.getTime());

		} else {
			System.out.println("Fecha nula");
		}

		return salida;
	}

	public Calendar convertStringToCalendar(String fechaRealizacionStr) {

		Date fecha = this.convertStringToDate(fechaRealizacionStr);
		Calendar calendario = null;
		if (fecha != null) {
			calendario = Calendar.getInstance();
			calendario.setTime(fecha);
		} else {
			System.out.println("fecha nula");
		}

		return calendario;
	}

	public String convertirCalendarToStringFecha(Calendar fecha, String formato) {
		String salida = ConstanteGral.SIN_DATOS;

		if (fecha != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);

			salida = sdf.format(fecha.getTime());

		} else {
			System.out.println("Fecha nula");
		}

		return salida;
	}

	public static void main(String[] args) {
		UtilBO uBo = new UtilBO();
		String fecha = "2014-12-30 00:00:01";
		System.out.println(uBo.convertStringToDate(fecha));
		Date fechaRealizacion = uBo.convertStringToDate(fecha);
		Calendar c = Calendar.getInstance();
		c.setTime(fechaRealizacion);
		System.out.println(uBo.convertirCalendarToStringFecha(c,
				ConstanteGral.FORMATO_FECHA_2));
	}
}
