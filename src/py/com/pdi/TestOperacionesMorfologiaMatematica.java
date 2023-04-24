package py.com.pdi;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;

public class TestOperacionesMorfologiaMatematica {

	public static void main(String[] args) {
		String ruta = "C:\\Proyectos_PDI\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta); // carga la imagen
		im.show(); // Muestra la imagen original
		ImagePlus im2 = im.duplicate(); // duplicar la imagen
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor(); // Para procesar la imagen

		//Erosion
		int r = 1;
		Strel B = DiskStrel.fromRadius(r);
		ImageProcessor eRosion = Morphology.erosion(ip, B);
		ImagePlus newImage = new ImagePlus("Erosion", eRosion);
		
		
		// Imagenes resultantes
		newImage.show();
	}
}
