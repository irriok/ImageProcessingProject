import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;

public class Histogram_HSB implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
			return DOES_RGB;
		}
	
		public void binnedHistogram(ImageProcessor ip) {
			Color color;

int K = 256; // number of intensity values
float[] convert = new float[3];
int[] H = new int[K]; // histogram array		
int[] S = new int[K]; // histogram array
int[] B = new int[K]; // histogram array
int red, green, blue;
double[] HistH = new double[K];
double[] HistS = new double[K];
double[] HistB = new double[K];
int w = ip.getWidth();
int h = ip.getHeight();

for (int v = 0; v < h; v++) {
	for (int u = 0; u < w; u++) {
		color = new Color(ip.getPixel(u, v));
		red = color.getRed();
		blue = color.getBlue();
		green = color.getGreen();
	
        Color.RGBtoHSB(red, green, blue, convert);
        double[] hsb = new double[3];
        hsb[0] = (int) convert[0];
        hsb[1] = (int) convert[1];
        hsb[2] = (int) convert[2];
        
        H[(int) hsb[0]] = H[(int) hsb[0]] + 1;
		S[(int) hsb[1]] = S[(int) hsb[1]] + 1;
		B[(int) hsb[2]] = B[(int) hsb[2]] + 1;
	}
}

 for (int i = 0; i < 256; i++) {
	 if(i == 0) {
		 HistH[0] = H[0];
		 HistS[0] = S[0];
		 HistB[0] = B[0];
	}
	 else {
		 HistH[i] = HistH[i - 1] + H[i];
		 HistS[i] = HistS[i - 1] + S[i];
		 HistB[i] = HistB[i - 1] + B[i];
	 	}
    }
 
 for (int i = 0; i < 256; i++) {
	 HistH[i] = HistH[i]/HistH[255];
	 HistS[i] = HistS[i]/HistS[255];
	 HistB[i] = HistB[i]/HistB[255];
    }
 
 
 for (int i = 0; i < 256; i++) {
	 int hue = (int) HistH[i];
	 int saturation = (int) HistS[i];
	 int brightness = (int) HistB[i];
	
	 Color.HSBtoRGB(hue, saturation, brightness);
    }
		}

		public void run(ImageProcessor ip) {
		binnedHistogram(ip);
		}
	}
