package py.com.pdi;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class MostrarHistogramaImagen {

	public static void main(String[] args) {
		String ruta = "C:\\Proyectos_PDI\\Pruebas_ImageJ\\imagenes\\cameraman.tif";
		ImagePlus im = IJ.openImage(ruta); 				// carga la imagen
		im.show(); // Muestra la imagen original
		
		ComputeHistogram hist = new ComputeHistogram();
		hist.setup(null, im);
		ImageProcessor ip = im.getProcessor();
		hist.run(ip);

	}

}
