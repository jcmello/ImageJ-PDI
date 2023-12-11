package py.com.pdi.operaciones;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Contraste_Brillo implements PlugInFilter{

	public void run(ImageProcessor ip) {
		int w = ip.getWidth(); //Ancho
		int h = ip.getHeight(); //Alto
		
		for(int v = 0; v < h; v++) {
			for(int u=0; u<w; u++) {
				int a = ip.getPixel(u, v); //Obtener el valor de pixel actual en la posicion (u,v)
				int b = (int)(a * 1.5 + 10);
				if(b>255) {
					b = 255;
				}
				//Actualizar el valor del pixel modificado
				ip.putPixel(u, v, b);
				
			}
		}
		
	}

	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}
