package py.com.pdi;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class TestAutoContraste {

	public static void main(String[] args) {
		String ruta = "C:\\Proyectos_PDI\\ImageJ-PDI\\imagenes\\einstein-low-contrast.tif";
		ImagePlus im = IJ.openImage(ruta); 				// carga la imagen
		im.show(); 										// Muestra la imagen original
		ImagePlus im2 = im.duplicate(); 
		
		AutoContraste auto = new AutoContraste();
		
		ImageProcessor ip = im2.getProcessor();
		auto.run(ip);
		im2.show();

	}

}
