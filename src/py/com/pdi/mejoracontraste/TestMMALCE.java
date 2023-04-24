package py.com.pdi.mejoracontraste;

import java.util.ArrayList;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel;
import inra.ijpb.morphology.strel.DiskStrel;

/*
 * @article{Mukhopadhyay_2000,
	doi = {10.1016/s0165-1684(99)00161-9},
	url = {https://doi.org/10.1016%2Fs0165-1684%2899%2900161-9},
	year = 2000,
	month = {apr},
	publisher = {Elsevier {BV}},
	volume = {80},
	number = {4},
	pages = {685--696},
	author = {Susanta Mukhopadhyay and Bhabatosh Chanda},
	title = {A multiscale morphological approach to local contrast enhancement},
	journal = {Signal Processing}
}
 */
public class TestMMALCE {

	public static void main(String[] args) {
		String ruta = "C:\\Proyectos_PDI\\ImageJ-PDI\\imagenes\\1.jpg";
		ImagePlus im = IJ.openImage(ruta); // carga la imagen
		im.show(); // Muestra la imagen original
		ImagePlus im2 = im.duplicate(); // duplicar la imagen
		ImageConverter ic = new ImageConverter(im2);
		ic.convertToGray8();
		ImageProcessor ip = im2.getProcessor(); // Para procesar la imagen

		int n = 7; // Numero de iteraciones
		// Primer top-hat y botton hat
		ImageProcessor th;
		ImageProcessor bh;
		ArrayList<ImageProcessor> matrizSW = new ArrayList<>();
		ArrayList<ImageProcessor> matrizSB = new ArrayList<>();

		for (int i = 1; i <= n; i++) {
			int r = i;
			Strel B = DiskStrel.fromRadius(r);
			th = Morphology.whiteTopHat(ip, B);
			bh = Morphology.blackTopHat(th, B);
			matrizSW.add(th);
			matrizSB.add(bh);
		}
		
		ImageProcessor SW = matrizSW.get(0);
		ImageProcessor SB = matrizSB.get(0);
		for(int x = 1; x < n; x++) {
			SW = sumaImagenes(SW,  matrizSW.get(x));
			SB = sumaImagenes(SB, matrizSB.get(x));
		}
		
		ImageProcessor iE = MMALCE(ip,SW,SB);
		ImagePlus IE = new ImagePlus("Imagen Mejorada", iE);
		IE.show();

	}

	private static ImageProcessor MMALCE(ImageProcessor ip, ImageProcessor sW, ImageProcessor sB) {
		int M = ip.getWidth();
		int N = ip.getHeight();

		ImageProcessor mmalce = ip.createProcessor(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				int s = (int) (ip.getPixel(i, j) + 0.5 * sW.getPixel(i, j) -  0.5 * sB.getPixel(i, j));
				if (s > 255) {
					s = 255;
				}
				mmalce.putPixel(i, j, s);
			}
		}

		return mmalce;
	}

	public static ImageProcessor sumaImagenes(ImageProcessor ip1, ImageProcessor ip2) {
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

}
