package py.com.pdi.operaciones;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class TestAutoContraste {

	public static void main(String[] args) {
		//Leer la ruta de una imagen
		String ruta = "C:\\Proyectos_PDI\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta);
		im.show();
		ImagePlus im2 = im.duplicate();
		
		ImageProcessor ip = im2.getProcessor();
		
		//
		Auto_Contraste autoContraste = new Auto_Contraste();
		autoContraste.run(ip);
		im2.show();
		
		IJ.save(im2, "C:\\Proyectos_PDI\\ImageJ-PDI\\imagenes\\1-auto.jpg");

		//Metricas de evaluacion
		MetricasEvaluacion metrica = new MetricasEvaluacion();
		double eAC = metrica.entropy(im2);
		System.out.println("Entropy-->"+eAC);
;
	}

}
