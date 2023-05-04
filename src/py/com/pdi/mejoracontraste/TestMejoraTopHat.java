package py.com.pdi.mejoracontraste;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;
import inra.ijpb.morphology.strel.LinearDiagDownStrel;

public class TestMejoraTopHat {

	public static void main(String[] args) {
		String ruta = "C:\\Proyectos_PDI\\ImageJ-PDI\\imagenes\\157032.jpg";
		ImagePlus im = IJ.openImage(ruta); // carga la imagen
		im.show(); // Muestra la imagen original
		ImagePlus im2 = im.duplicate(); // duplicar la imagen
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor(); // Para procesar la imagen

		int r = 7;
		Strel B = LinearDiagDownStrel.fromDiameter(r);
		// TH
		ImageProcessor th = Morphology.whiteTopHat(ip, B);

		// BH
		ImageProcessor bh = Morphology.blackTopHat(th, B);

		// Mejora de contraste (IE = I + th - bh)
		ImageProcessor IE = imageEnhancement(ip,th,bh);
		ImagePlus newImage = new ImagePlus("Imagen Mejorada", IE);
		newImage.show();
	}

	public static ImageProcessor imageEnhancement(ImageProcessor I, ImageProcessor TH, ImageProcessor BH) {

		int M = I.getWidth();
		int N = I.getHeight();

		ImageProcessor iE = I.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int pix = I.getPixel(i, j) + TH.getPixel(i, j) - BH.getPixel(i, j);
				if (pix < 0) {
					pix = 0;
				}
				if (pix > 255) {
					pix = 255;
				}
				iE.putPixel(i, j, pix);
			}
		}
		return iE;
	}
}
