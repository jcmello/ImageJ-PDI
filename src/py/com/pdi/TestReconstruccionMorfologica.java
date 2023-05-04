package py.com.pdi;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Reconstruction;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.LinearDiagDownStrel;

public class TestReconstruccionMorfologica {

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

		// Erosion
		ImageProcessor eRosion = Morphology.erosion(ip, B);
		ImagePlus eroImage = new ImagePlus("Erosion", eRosion);
		eroImage.show();

		// Apertura por reconstrucción
		ImageProcessor aReconst = Reconstruction.reconstructByDilation(eRosion, ip, 8);
		ImagePlus rbdImage = new ImagePlus("Apertura por reconstrucción", aReconst);
		rbdImage.show();

		// Dilatacion
		ImageProcessor dIlatacion = Morphology.dilation(ip, B);
		ImagePlus dilImage = new ImagePlus("Dilatación", dIlatacion);
		dilImage.show();

		// Cierre por reconstruccion
		ImageProcessor cReconst = Reconstruction.reconstructByErosion(dIlatacion, ip, 8);
		ImagePlus rbeImage = new ImagePlus("Cierre por reconstrucción", cReconst);
		rbeImage.show();

		// Transformada de Top-Hat por Reconstrucción
		ImageProcessor RTH = restaImagenes(ip, aReconst);
		ImagePlus rthImage = new ImagePlus("Top-Hat por apertura reconstructiva", RTH);
		rthImage.show();

		// Transformada de Bottom-Hat por Reconstrucción
		ImageProcessor RBH = restaImagenes(cReconst, ip);
		ImagePlus rbhImage = new ImagePlus("Top-Hat por cierre reconstructivo", RBH);
		rbhImage.show();

	}

	private static ImageProcessor restaImagenes(ImageProcessor img1, ImageProcessor img2) {
		int M = img1.getWidth();
		int N = img1.getHeight();

		// Defino la imagen a retornar
		ImageProcessor imgR = img1.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int dif = img1.getPixel(i, j) - img2.getPixel(i, j);
				if (dif < 0) {
					dif = 0;
				}
				imgR.putPixel(i, j, dif);
			}
		}
		return imgR;
	}

}
