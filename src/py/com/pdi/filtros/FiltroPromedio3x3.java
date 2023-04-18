package py.com.pdi.filtros;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class FiltroPromedio3x3 implements PlugInFilter {

	public void run(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight();
		ImageProcessor copy = ip.duplicate();

		for (int u = 1; u <= M - 2; u++) {
			for (int v = 1; v <= N - 2; v++) {
				// compute filter result for position (u, v):
				int sum = 0;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						int p = copy.getPixel(u + i, v + j);
						sum = sum + p;
					}
				}
				int q = (int) (sum / 9.0);
				ip.putPixel(u, v, q);
			}
		}

	}

	public int setup(String args, ImagePlus im) {
		// Para imágenes en escala de grises
		return DOES_8G;
	}

}
