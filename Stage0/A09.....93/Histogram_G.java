import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;

public class Histogram_G implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
			return DOES_RGB;
		}
	
		public void binnedHistogram(ImageProcessor ip) {
			Color color;
		int K = 256; // number of intensity values
		int[] G = new int[K]; // histogram array
		double[] Hist = new double[K];
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				color = new Color(ip.getPixel(u, v));
				G[color.getGreen()] = G[color.getGreen()] + 1;
			}
		}
		
		 for (int i = 0; i < 256; i++) {
			 if(i == 0)
				 Hist[0] = G[0];
			 
			 else 
				 Hist[i] = Hist[i - 1] + G[i];
	           }
		 
		 for (int i = 0; i < 256; i++) {
			 ij.IJ.log(String.valueOf(Hist[i]/Hist[255]) + ", ");
	           }
		}
			
		public void run(ImageProcessor ip) {
		binnedHistogram(ip);
		}
	}
