package uk.ac.soton.ecs.cbb1u19.hybridimages;
import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
	private float[][] kernel;
	FImage convolved;
	
	public MyConvolution(float[][] kernel) {
		//note that like the image pixel s kernel is indexed by [row][column]
		this.kernel = kernel;
	}

	@Override
	public void processImage(FImage image) {
		
		int imageHeight = image.getHeight(); // no of columns in the image
		int imageWidth = image.getWidth(); // no of rows in the image
		
		int trow = kernel.length; // no of rows in the kernel 
		int tcol = kernel[0].length; // no of columns in the kernel
		
		int tr = trow/2; //half of the length of the kernel's rows
		int tc = tcol/2; //half of the length of the kernel's columns
		
		convolved = image.clone();
		convolved.fill(0); // makes image black
		
		//Template Convolution done assuming that the image replicates to infinity along both dimensions and
		//calculate new values by cyclic shift from the far border
		for(int j = 0; j<imageHeight; j++) {
			for(int i = 0; i<imageWidth; i++) {
				float sum = 0;
				for(int n = 0; n < tcol; n++) {
					for(int m = 0; m < trow; m++) {
						
						if (i + m - tr >= 0 && j + n - tc >= 0 && i + m - tr < imageWidth && j + n - tc < imageHeight) {
                            sum += image.getPixel(i + m - tr, j + n - tc) * kernel[trow - m - 1][tcol - n - 1];
                        }
                        else sum += 0;							
					}
					convolved.setPixel(i, j, sum);
				}
			}
		}
		image.internalAssign(convolved);
	}
}
