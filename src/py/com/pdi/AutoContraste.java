package py.com.pdi;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class AutoContraste implements PlugInFilter{

	public int setup(String args, ImagePlus im) {
		return DOES_8G;
	}
	
	public void run(ImageProcessor ip) {
		
	}

	

}
