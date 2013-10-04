import java.net.*;
import java.io.*;

public class Comic implements Serializable
{
	private String name;
	private URL url;
	private int currentNumber;

	public Comic(String name, String address)
	{
		this.name = name;
		InputStream in = null;
		BufferedReader br;
		String line;
		try
		{
			url = new URL(address);
			in = url.openStream();
			br = new BufferedReader(new InputStreamReader(in));
			
			int i = 1;
			while((line = br.readLine()) != null)
			{
				if(i == 57)
				{
					int start = line.indexOf("href")+7;
					System.out.println("Start # is "+start);
					String num = line.substring(start, start+4);
					System.out.println("String num is "+num);
					currentNumber = Integer.parseInt(num);
				}
				else if(i == 63)
				{
					int start = line.indexOf("http://");
					System.out.println("Start is: "+start);
					int end = line.indexOf("\" title");
					System.out.println("End is: "+end);
					String str = line.substring(start,end);
					System.out.println("str is: "+str);
					url = new URL(str);
					break;
				}
				i++;
			}
		} 
		catch(MalformedURLException mue)
		{
			System.out.println("Malformed URL.");
			mue.printStackTrace();
		}
		catch(IOException ex) 
		{
			System.out.println("It failed.");
			ex.printStackTrace();
		}
		
	}
	
	public String toString() {
    	   return new StringBuffer(" Name : ")
    	   .append(this.name)
    	   .append(" URL : ")
    	   .append(this.url).toString()
    	   .append(" Current Number : ")
    	   .append(currentNumber);
	   }
	
	public String getName()
	{
		return name;
	}
}