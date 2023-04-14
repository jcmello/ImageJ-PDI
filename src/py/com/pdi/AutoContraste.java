package py.com.pdi;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class AutoContraste implements PlugInFilter {

	public int setup(String arg0, ImagePlus arg1) {
		return DOES_8G;
	}

	public void run(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight();

		int aMin = 0;
		int aMax = 255;

		int aHi = 0;
		int aLo = 255;
		for (int v = 0; v < N; v++) {
			for (int u = 0; u < M; u++) {
				if (ip.getPixel(u, v) > aHi) {
					aHi = ip.getPixel(u, v);
				}
				if (ip.getPixel(u, v) < aLo) {
					aLo = ip.getPixel(u, v);
				}
			}
		}

		IJ.log("aHi: " + aHi);
		IJ.log("aLo: " + aLo);

		for (int v = 0; v < N; v++) {
			for (int u = 0; u < M; u++) {
				int a = ip.getPixel(u, v);
				int fac = aMin + (a - aLo) * ((aMax - aMin) / (aHi - aLo));
				if (fac < 0) {
					fac = 0;
				}
				if (fac > 255) {
					fac = 255;
				}
				ip.putPixel(u, v, fac);
			}
		}
	}
}
