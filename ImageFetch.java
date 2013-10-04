import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class ImageFetch
{
	
	/**This method takes the URL of an image and returns the image as as BufferedImage. It
	checks and will automatically include the "http://" if it does not already include it
	*/
	public static BufferedImage getImage(String theURL)
	{
		BufferedImage image = null;
		try
		{
			URL url = new URL(theURL);
			image = ImageIO.read(url);
		}
		catch(IOException e)
		{
			System.err.println("Something went wrong getting image from "+theURL);
			if(theURL.charAt(0) != 'h')
			{
				image = getImage("http://"+theURL);
				return image;
			}
		}
		if(image==null)
		{
			System.err.println("WAHT. NO. NO NO NO NO NO");
		}
		return image;
	}

	/**This method saves the given BufferedImage to the disk, with name outputFileName and
	type fileType*/
	public static void saveImage(BufferedImage img, String outputFileName, String fileType)
	{
		try
		{
			//BufferedImage img = getImage("http://imgs.xkcd.com/comics/10_day_forecast.png");
			File outputFile = new File(outputFileName);
			if(ImageIO.write(img, fileType, outputFile))
			{
				System.out.println("It worked! Look for the file");
			}
			else
			{
				System.err.println("apparently something went wrong writing the image to "+outputFileName);
			}
		}
		catch(IOException e)
		{
			System.err.println("Something ELSE went wrong");
			System.exit(1);
		}
	}
}