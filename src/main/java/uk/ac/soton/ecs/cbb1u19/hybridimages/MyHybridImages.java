package uk.ac.soton.ecs.cbb1u19.hybridimages;

import java.lang.reflect.Array;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;

public class MyHybridImages {
	/**
	 * Compute a hybrid image combining low-pass and high-pass filtered images
	 *
	 * @param lowImage
	 *            the image to which apply the low pass filter
	 * @param lowSigma
	 *            the standard deviation of the low-pass filter
	 * @param highImage
	 *            the image to which apply the high pass filter
	 * @param highSigma
	 *            the standard deviation of the low-pass component of computing the
	 *            high-pass filtered image
	 * @return the computed hybrid image
	 */
	public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
		
		//making the low-pass filtered version of the first image
		int sizeL = (int) (8.0f * lowSigma + 1.0f); // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
		if (sizeL % 2 == 0) sizeL++; // size must be odd
		MyConvolution myConvoLow = new MyConvolution(Gaussian2D.createKernelImage(sizeL, lowSigma).pixels);
		MBFImage lowConvoImage = lowImage.process(myConvoLow);
		
		//making the low-pass filtered version of the secind image
		int sizeH = (int) (8.0f * highSigma + 1.0f); // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
		if (sizeH % 2 == 0) sizeH++; // size must be odd
		MyConvolution myConvoHigh = new MyConvolution(Gaussian2D.createKernelImage(sizeH, highSigma).pixels);
		MBFImage highConvoImage = highImage.clone().process(myConvoHigh);
		
		//create the hibrid picture
		for(int i = 0; i < lowImage.getHeight(); i++) {
			for(int j = 0; j < lowImage.getWidth(); j++) {
				for(int k = 0; k < lowImage.numBands(); k++) {
					//the second picture's low-pass version is subtracted from the original second picture to get the high-pass filtered version.
					lowConvoImage.getBand(k).pixels[i][j] += highImage.getBand(k).pixels[i][j] - highConvoImage.getBand(k).pixels[i][j];
				}
			}
		}
		return lowConvoImage;
	}
}