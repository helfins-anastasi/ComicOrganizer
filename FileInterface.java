/*
Created by Zachary Helfinstein
All rights reserved.

Version 2.0.0 (Last edited 6/12/12; Tested 6/10/12)

Previous versions under the name "AddTxt"
*/
import java.io.*;
import java.util.Scanner;

public class FileInterface
{
	PrintWriter output;
	String fileName;
	Scanner fileReader;
	File memory;
	boolean forReading;
	
	public FileInterface(String fileIsCalled, boolean isReader)
	{
		fileName = fileIsCalled;
		forReading = isReader;
		
		Writer w = null;
		try
		{
			if(!isReader)
			{
				w = new FileWriter(fileName, true);
			}
			memory = new File(fileName);
		}
		catch(IOException ex)
		{
			System.out.println("*** Cannot create/open "+fileName+" ***");
			System.exit(1);
		}
		if(!isReader)
		{
			output = new PrintWriter(w);
		} 
		else 
		{
			openScanner();
		}

	}
		
	public void println(String textToAdd)
	{
		if (forReading) 
		{
			System.out.println("Illegal operation Ð FileInterface formatted for reading cannot print text");
			return;
		}
		output.println(textToAdd);
	}

	public void print(String textToAdd)
	{
		if (forReading) 
		{
			System.out.println("Illegal operation Ð FileInterface formatted for reading cannot print text");
			return;
		}
		output.print(textToAdd);
	}

	public void printf(String textToAdd)
	{
		if (forReading) 
		{
			System.out.println("Illegal operation Ð FileInterface formatted for reading cannot print text");
			return;
		}
		output.printf(textToAdd);
	}

	public void close()
	{
		if (forReading) 
		{
			System.out.println("Illegal operation Ð FileInterface formatted for reading cannot be closed");
			return;
		}
		output.close();
	}

	public String read()
	{
		if (!forReading) 
		{
			System.out.println("Illegal operation - FileInterface formatted for writing cannot read files");
		}
		openScanner();
		String sum = "";
		String theLine = "";
		while(fileReader.hasNextLine())
		{
			theLine = fileReader.nextLine();
			sum += theLine + '\n';
		}
		return sum;
	}
	
	public String readLines(int upTo)
	{
		if (!forReading) 
		{
			System.out.println("Illegal operation - FileInterface formatted for writing cannot read files");
		}
		openScanner();
		String sum = "";
		String theLine = "";
		for(int i = 0; i < upTo && fileReader.hasNextLine(); i++)
		{
			theLine = fileReader.nextLine();
			sum += theLine + '\n';
		}
		return sum;
	}

    //line numbering is the very first line of the file is numbered line 1
	public void readLines(String[] target, int startAt)
	{
		if (!forReading) 
		{
			System.out.println("Illegal operation - FileInterface formatted for writing cannot read files");
		}
		openScanner();
		for (int i = 1; i < startAt && fileReader.hasNextLine(); i++) 
		{
			fileReader.nextLine();
		}
		for(int i = 0; i < target.length && fileReader.hasNextLine(); i++)
		{
			target[i] = fileReader.nextLine();
		}
	}
	
	public void readLines(String[] target)
	{
		readLines(target, 0);
	}
	
	public String readLine(int lineNumber)
	{
		if (!forReading) 
		{
			System.out.println("Illegal operation - FileInterface formatted for writing cannot read files");
		}
		openScanner();
		for(int i = 1; i < lineNumber && fileReader.hasNextLine(); i++)
		{
			fileReader.nextLine();
		}
		String target = "";
		if (fileReader.hasNextLine())
		{
			target = fileReader.nextLine();
		}
		return target;
	}

	public String toString()
	{
		return "File Interface ("+fileName+")";
	}
	
	//---------------PRIVATE METHODS---------------
	
	private void openScanner()
	{
		try
		{
			fileReader = new Scanner(memory);
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("********No Such File********** "+ex);
			System.exit(1);
		}
	}
}