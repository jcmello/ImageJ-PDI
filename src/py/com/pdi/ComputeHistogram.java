package py.com.pdi;
import java.util.Arrays;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class ComputeHistogram implements PlugInFilter{
	
	ImagePlus imp;
	
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		int[] h = new int[256];
		int M = ip.getWidth();
		int N = ip.getHeight();
		
		for(int v = 0; v < N; v++){
			for(int u = 0; u < M; u++){
				int i = ip.getPixel(u, v);
				h[i] = h[i] + 1;
			}
		}
		//
		System.out.println(Arrays.toString(h));
		
		// ... histogram h can now be used

		// create the histogram image:
		ImageProcessor hip = new ByteProcessor(256, 100);
		hip.setValue(255); // white = 255
		hip.fill();

		// draw the histogram values as black bars in hip here,
		// for example, using hip.putpixel(u, v, 0)
		// ...
		
		//Obtener la frecuencia mas alta del vector h
		int ma = maximo(h);
		for(int x = 0; x < 256; x++){
			int esc = (h[x]*100)/ma;
			for(int y = 0; y<=esc; y++){
				hip.putPixel(x, 100-y, 0);
			}
		}
		
		// compose a nice title:
		String imTitle = imp.getShortTitle();
		String histTitle = "Histogra de " + imTitle;

		// display the histogram image:
		ImagePlus him = new ImagePlus(histTitle, hip);
		him.show();
	}
	
	private int maximo(int[] H){
		int maxi = H[0];
		int ta = H.length; //Tamanho del vector o arreglo unidimensional
		for(int t = 0; t < ta; t++){  //Recorrer mi vector
			if(H[t]>maxi){
				maxi = H[t];
			}
		}
		return maxi;
	}
}
