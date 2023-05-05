package py.com.pdi.mejoracontraste;

import java.util.ArrayList;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Reconstruction;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;

/*
 * @article{Bai_2013,
	doi = {10.1016/j.ijleo.2013.01.100},
	url = {https://doi.org/10.1016%2Fj.ijleo.2013.01.100},
	year = 2013,
	month = {oct},
	publisher = {Elsevier {BV}},
	volume = {124},
	number = {20},
	pages = {4421--4424},
	author = {Xiangzhi Bai},
	title = {Image enhancement through contrast enlargement using the image regions extracted by multiscale top-hat by reconstruction},
	journal = {Optik}
}
*/
public class TestMMALCER {

	public static void main(String[] args) {
		String ruta = "C:\\Proyectos_PDI\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta); // carga la imagen
		im.show(); // Muestra la imagen original
		ImagePlus im2 = im.duplicate(); // duplicar la imagen
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor(); // Para procesar la imagen

		int n = 7; // Numero de iteraciones
		ImageProcessor rth;
		ImageProcessor rbh;
		ImageProcessor aReconst = null;
		ImageProcessor cReconst = null;

		ArrayList<ImageProcessor> matrizSW = new ArrayList<>();
		ArrayList<ImageProcessor> matrizSB = new ArrayList<>();

		// Radio inicial igual a 1
		for (int i = 1; i <= n; i++) {
			int r = i;
			Strel B = DiskStrel.fromRadius(r);
			/*********************************************************/
			// Erosion
			ImageProcessor eRosion = Morphology.erosion(ip, B);

			// Apertura por reconstrucción
			aReconst = Reconstruction.reconstructByDilation(eRosion, ip, 8);

			// Dilatacion
			ImageProcessor dIlatacion = Morphology.dilation(ip, B);

			// Cierre por reconstruccion
			cReconst = Reconstruction.reconstructByErosion(dIlatacion, ip, 8);

			// Transformada de Top-Hat por Reconstrucción
			rth = restaImagenes(ip, aReconst);

			// Transformada de Bottom-Hat por Reconstrucción
			rbh = restaImagenes(cReconst, ip);

			/*********************************************************/
			matrizSW.add(rth);
			matrizSB.add(rbh);
		}

		// Sumatorias de Top-Hat
		ImageProcessor SW = matrizSW.get(0);
		ImageProcessor SB = matrizSB.get(0);
		for (int x = 1; x < n; x++) {
			SW = sumaImagenes(SW, matrizSW.get(x));
			SB = sumaImagenes(SB, matrizSB.get(x));
		}

		ImageProcessor A = semiSuma(aReconst, cReconst);

		ImageProcessor iE = MMALCER(A, SW, SB);
		ImagePlus IE = new ImagePlus("Imagen Mejorada con MMALCER", iE);
		IE.show();

	}

	private static ImageProcessor semiSuma(ImageProcessor ip1, ImageProcessor ip2) {
		int M = ip1.getWidth();
		int N = ip1.getHeight();

		ImageProcessor suma = ip1.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int s = (int) ((ip1.getPixel(i, j) + ip2.getPixel(i, j)) / 2.0);
				if (s > 255) {
					s = 255;
				}
				suma.putPixel(i, j, s);
			}
		}

		return suma;
	}

	private static ImageProcessor MMALCER(ImageProcessor ip, ImageProcessor sW, ImageProcessor sB) {
		int M = ip.getWidth();
		int N = ip.getHeight();

		ImageProcessor mmalce = ip.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int s = (int) (ip.getPixel(i, j) + 0.5 * sW.getPixel(i, j) - 0.5 * sB.getPixel(i, j));
				if (s > 255) {
					s = 255;
				}
				mmalce.putPixel(i, j, s);
			}
		}

		return mmalce;
	}

	private static ImageProcessor sumaImagenes(ImageProcessor ip1, ImageProcessor ip2) {
		int M = ip1.getWidth();
		int N = ip1.getHeight();

		ImageProcessor suma = ip1.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int s = ip1.getPixel(i, j) + ip2.getPixel(i, j);
				if (s > 255) {
					s = 255;
				}
				suma.putPixel(i, j, s);
			}
		}

		return suma;
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
