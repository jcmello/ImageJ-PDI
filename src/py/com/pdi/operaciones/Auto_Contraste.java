package py.com.pdi.operaciones;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Auto_Contraste implements PlugInFilter{

	public void run(ImageProcessor ip) {
		// Definir el valor del ancho y el alto
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		//
		int amax = 255;
		int amin = 0;
		
		//Calcular los pixeles maximos y minimos de la imagen
		int alo=255; //valor minimo de la imagen original
		int ahi=0;   //valor maximo de la imagen original
		for(int v = 0; v<h; v++) {
			for(int u=0; u<w; u++) {
				int a = ip.getPixel(u, v);
				if(a<alo) {
					alo = a;
				}
				if(a>ahi) {
					ahi = a;
				}
			}
		}
		//Calcular el Auto-Contraste
		for(int v = 0; v<h; v++) {
			for(int u=0; u<w; u++) {
				int a = ip.getPixel(u, v);
				int b = (int)(amin + (a-alo)*((amax-amin)/(ahi-alo)));
				//Agregar limites de sujecion
				if(b>255) {
					b=255;
				}
				if(b<0) {
					b=0;
				}
				//Modificar el valor del pixel
				ip.putPixel(u, v, b);
			}
		}
	}

	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}
