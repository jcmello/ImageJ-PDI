package py.com.pdi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class TestAutoContraste {

	public static void main(String[] args) {
		
		/*
		 * java.io.File. Esta clase nos permite obtener información sobre cualquier
		 * elemento del sistema de archivos.
		 * 
		 */
		// Obtener la carpeta actual desde la que se está ejecutando el código
		String sCarpAct = System.getProperty("user.dir").concat("\\imagenes");
		System.out.println(sCarpAct);
		File carpeta = new File(sCarpAct); // Se crea un nuevo objeto File para representar la carpeta que nos interesa.
		String[] listado = carpeta.list(); // Con el método list() obtenemos los nombres de todos sus hijos
		
		Object[][] result = new Object[listado.length][4]; // Para cuantificar los resultados
		
		// Si no devuelve nada o la longitud es 0 mostramos un mensaje de que no hay
		// elementos en la carpeta actual (también podríamos haber comprobado si es una
		// carpeta o no con isDirectory())
		if (listado == null || listado.length == 0) {
			System.out.println("No hay elementos dentro de la carpeta actual");
			return;
		} else {
			// Se realiza un recorrido mediante un bucle sencillo todos los nombres de
			// elementos hijo.
			for (int i = 0; i < listado.length; i++) {
				System.out.println(listado[i]);
				String ruta = sCarpAct.concat("\\").concat(listado[i]);
				System.out.println(ruta);
				ImagePlus im = IJ.openImage(ruta); // carga la imagen
				im.show();

				// Duplicar imagen
				ImagePlus im2 = im.duplicate();

				// Inicio del algoritmo
				long time_start = System.currentTimeMillis();
				
				AutoContraste auto = new AutoContraste();

				// Fin del tiempo en milisegundos
				long time_end = System.currentTimeMillis();
				long time = time_end - time_start;
				
				ImageProcessor ip = im2.getProcessor();
				auto.run(ip);
				im2.show();

				String sResultados = System.getProperty("user.dir").concat("\\resultados\\AutoContraste");
				String rutaGuardar = sResultados.concat("\\").concat(listado[i]);
				// Guardar la imagen con el mismo formato
				IJ.saveAs(im2, "tif", rutaGuardar);

				// Metricas de Evaluacion
				MetricasEvaluacion mE = new MetricasEvaluacion();
				double PSNR = mE.psnr(im, im2);
				double AMBE = mE.ambe(im, im2);

				// Guardamos los resultados de las metricas
				result[i][0] = listado[i];
				result[i][1] = PSNR;
				result[i][2] = AMBE;
				result[i][3] = time;
			}
		}

		//Guardamos los resultados en un archivo .csv
		try {
			String sResult = System.getProperty("user.dir").concat("\\resultados");
			String camino = sResult.concat("\\auto_contraste.csv");
			BufferedWriter br = new BufferedWriter(new FileWriter(camino));
			StringBuilder sb = new StringBuilder();
			for (int fi = 0; fi < result.length; fi++) {
				for (int co = 0; co < result[0].length; co++) {
					sb.append(String.valueOf(result[fi][co]));
					sb.append(";");
				}
				sb.append("\n");
			}
			br.write(sb.toString());
			br.close();
		} catch (IOException ex) {
			Logger.getLogger(TestAutoContraste.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
