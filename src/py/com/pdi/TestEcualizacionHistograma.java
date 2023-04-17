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

public class TestEcualizacionHistograma {

	public static void main(String[] args) {

		String sCarpAct = System.getProperty("user.dir").concat("\\imagenes");
		File carpeta = new File(sCarpAct);
		String[] listado = carpeta.list();

		Object[][] result = new Object[listado.length][4]; // Para cuantificar los resultados

		if (listado == null || listado.length == 0) {
			System.out.println("No hay elementos dentro de la carpeta actual");
			return;
		} else {
			for (int i = 0; i < listado.length; i++) {
				System.out.println(listado[i]);
				String ruta = sCarpAct.concat("\\").concat(listado[i]);
				System.out.println(ruta);
				ImagePlus im = IJ.openImage(ruta); // carga la imagen
				im.show(); // Muestra la imagen original

				ImagePlus im2 = im.duplicate();

				// Inicio del algoritmo
				long time_start = System.currentTimeMillis();

				EcualizacionHistograma eH = new EcualizacionHistograma();
				ImageProcessor ip = im2.getProcessor();
				eH.run(ip);

				// Fin del tiempo en milisegundos
				long time_end = System.currentTimeMillis();
				long time = time_end - time_start;

				im2.show();

				// Guardar los resultados
				String rGuardar = System.getProperty("user.dir").concat("\\resultados\\EcualizacionHistograma\\")
						.concat(listado[i]);
				System.out.println(rGuardar);
				IJ.saveAs(im2, "tif", rGuardar);

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

		// Guardamos los resultados en un archivo .csv
		try {
			String sResult = System.getProperty("user.dir").concat("\\resultados");
			String camino = sResult.concat("\\ecualizacion_histograma.csv");
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
			Logger.getLogger(TestEcualizacionHistograma.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
