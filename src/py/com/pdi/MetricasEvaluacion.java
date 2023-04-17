package py.com.pdi;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class MetricasEvaluacion {

	// PSNR
	public double psnr(ImagePlus imagenOriginal, ImagePlus imagenMejorada) {
		double pSNR = 10 * Math.log10((Math.pow(255, 2)) / (MSE(imagenOriginal, imagenMejorada)));
		return pSNR;
	}

	// MSE
	public double MSE(ImagePlus imagenOriginal, ImagePlus imagenMejorada) {
		int M = imagenOriginal.getWidth();
		int N = imagenOriginal.getHeight();
		ImageProcessor ipIO = imagenOriginal.getProcessor();
		ImageProcessor ipIM = imagenMejorada.getProcessor();

		double mse;
		double sumat = 0;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				double img1 = Double.parseDouble(String.valueOf(ipIO.getPixel(i, j)));
				double img2 = Double.parseDouble(String.valueOf(ipIM.getPixel(i, j)));
				double s = img1 - img2;
				sumat = sumat + Math.pow(s, 2);
			}
		}
		double den = (M * N);
		mse = (Math.pow(den, -1)) * sumat;
		return mse;
	}

	// AMBE
	public double ambe(ImagePlus imagenOriginal, ImagePlus imagenMejorada) {
		double aMBE = Math.abs(promedioImagen(imagenOriginal) - promedioImagen(imagenMejorada));
		return aMBE;
	}

	public double promedioImagen(ImagePlus imagen) {
		ImageProcessor ip = imagen.getProcessor();
		int M = ip.getWidth();
		int N = ip.getHeight();
		double sumatoria = 0;
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				sumatoria = sumatoria + ip.getPixel(i, j);
			}
		}
		double promedio = sumatoria/(M*N);
		return promedio;
	}
}
