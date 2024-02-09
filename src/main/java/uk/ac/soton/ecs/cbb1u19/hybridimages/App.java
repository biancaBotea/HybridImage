package uk.ac.soton.ecs.cbb1u19.hybridimages;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

public class App {

    
	public static void main( String[] args ) throws IOException {
    	
    	
    	MBFImage highImage = ImageUtilities.readMBF(new File("data/cat with perl.png"));
    	MBFImage lowImage = ImageUtilities.readMBF(new File("data/perl.png"));
        
    	MBFImage newImage = MyHybridImages.makeHybrid(lowImage,8, highImage, 8).clone();
    	System.out.println(Arrays.toString(newImage.getPixel(0, 0)));
    	DisplayUtilities.display(newImage);
    }
}
