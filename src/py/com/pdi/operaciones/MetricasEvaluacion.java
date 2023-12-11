package py.com.pdi.operaciones;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class MetricasEvaluacion {

	public static double entropy(ImagePlus img) {
		ImageProcessor ip = img.getProcessor();
		
		double E = 0;
		for(int k=0; k<256; k++) {
			double prob =  probabilidad(ip,k);			
			E = E + prob * (Math.log10(prob)/Math.log10(2.0));
			System.out.println("E-->"+E);
		}
		E = E * (-1);
		return E;
	}
	
	public static double probabilidad(ImageProcessor ip, int a) {
		int c = 0;    //Cantidad de apariciones del pixel a
		// Definir el valor del ancho y el alto
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		for(int v = 0; v<h; v++) {
			for(int u=0; u<w; u++) {
				if(ip.getPixel(u, v)==a) {
					c = c + 1;
				}
			}
		}
		double prob = ((double)(c)/(double)(w*h)) + 0.0000000000000001; //Se le suma un valor para que el valor no retorne cero
		return prob;
	}
}
