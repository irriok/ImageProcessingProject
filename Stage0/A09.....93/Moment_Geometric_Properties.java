import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.plugin.filter.PlugInFilter;
import java.awt.Color;



public class Moment_Geometric_Properties implements PlugInFilter {

	public int setup(String args, ImagePlus im) {
		return DOES_RGB;
	}
	
	double moment(ImageProcessor I, int p, int q) {
		
		double Mpq = 0.0;
		for (int v = 0; v < I.getHeight(); v++) {
			for (int u = 0; u < I.getWidth(); u++) {
				if (I.getPixel(u, v) > 0) {
					Mpq+= Math.pow(u, p) * Math.pow(v, q);
				}
			}
		}
		return Mpq;
	}
	
	
///////////TEXTBOOK, PAGE 235
	
	 // Central moments:
	
	 double centralMoment(ImageProcessor I, int p, int q) {
	 
		 double m00 = moment(I, 0, 0); // region area
		 double xCtr = moment(I, 1, 0) / m00;
		 double yCtr = moment(I, 0, 1) / m00;
		 double cMpq = 0.0;
		 for (int v = 0; v < I.getHeight(); v++) {
			 for (int u = 0; u < I.getWidth(); u++) {
				 if (I.getPixel(u, v) > 0) {
					 cMpq+= Math.pow(u-xCtr, p) * Math.pow(v-yCtr, q);
				 }
			 }
		 }
		 return cMpq;
	 }
	
	 // Normalized central moments:
	 
	 double nCentralMoment(ImageProcessor I, int p, int q) {
		 
		 double m00 = moment(I, 0, 0);
		 double norm = Math.pow(m00, 0.5 * (p + q + 2));
		 return centralMoment(I, p, q) / norm;
	}
	 
/////////////////////
	 
	 public void run(ImageProcessor ip) {
		
		double theta;
		double ecc;
		
		double mom11 = centralMoment(ip, 1, 1);
		double mom02 = centralMoment(ip, 0, 2);
		double mom20 = centralMoment(ip, 2, 0);
		
		theta = (1/2) * Math.atan(2*mom11/(mom20 - mom02));
		String orientation = String.valueOf(theta);
		
		double sqroot = Math.sqrt(Math.pow(mom20 - mom02, 2) + 4 * Math.pow(mom11, 2));
		
		ecc = (mom20 + mom02 + sqroot)/(mom20 + mom02 - sqroot);
		String eccentricity = String.valueOf(ecc);
		
		ij.IJ.log(orientation);
		ij.IJ.log(eccentricity);
		
	}
}