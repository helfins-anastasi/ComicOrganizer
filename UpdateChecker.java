/**Hi, I'm Zach. I wrote this. Don't steal it.*/

import java.io.*;

public class UpdateChecker
{
	
	private Comic[] comics;
	
	
	public UpdateChecker(String myManifest)
	{

		FileInterface fi = new FileInterface(myManifest, true);
		int numFiles = Integer.parseInt(fi.readLine(1));
		String[] comicFileNames = new String[numFiles];
		fi.readLines(comicFileNames);
		comics = new Comic[numFiles];
		try
		{
			for(int i = 0; i < comicFileNames.length; i++)
			{
				FileInputStream fis = new FileInputStream(comicFileNames[i]);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object o = ois.readObject();
				comics[i] = (Comic)o;
			}
		}
		catch(Exception e)
		{
			System.err.println("IO snaffoo");
		}
	}
	
	public void saveComics()
	{
		try
		{
			for(int i = 0; i < comics.length; i++)
			{
				FileOutputStream fos = new FileOutputStream(comics[i].getName()+".zco");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(comics[i]);
				oos.close();
				fos.close();
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Some sort of FileNotFoundError");
			e.printStackTrace();
		}
		catch(IOException ex)
		{
			System.err.println("Some sort of IO exception");
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Comic c = new Comic("xkcd", "http://xkcd.com");
		UpdateChecker uc = new UpdateChecker("manifest");
		uc.saveComics();
		System.out.println("If you got here, I think this worked");
	}
}