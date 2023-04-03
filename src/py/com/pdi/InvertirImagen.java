package py.com.pdi;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

public class InvertirImagen {

	public static void main(String[] args) {
		String ruta = "C:\\Proyectos_PDI\\Pruebas_ImageJ\\imagenes\\blurry-moon.tif";
		ImagePlus im = IJ.openImage(ruta); 				// carga la imagen
		ImagePlus im2 = im.duplicate();					// duplicar la imagen
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor(); 		// Para procesar la imagen
		
		int M = ip.getWidth(); 							//Ancho de la imagen
		int N = ip.getHeight(); 						//Alto de la imagen
		
		// iterar sobre todas las coordenadas de la imagen (u,v)
		for(int v = 0; v < N; v++) {
			for(int u = 0; u < M; u++) {
				int p  = ip.getPixel(u, v);
				ip.putPixelValue(u, v, 255-p);
			}
		}
		
		im.show(); // Muestra la imagen original
		im2.show(); //Muestra el negativo de la imagen
		
		//Guardar la imagen
		IJ.save(im2, "C:/Proyectos_PDI/Pruebas_ImageJ/imagenes/blurry-moon_inv.tif");

	}

}
