/**COMMENT

This is my code, don't steal it.
*/

package zComics;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ComicKeeper implements Serializable {

    String name;
    ArrayList<String> imageURLs;
    String defaultURL;

    public ComicKeeper(String name) {
	this.name = name;
	defaultURL = "/";
	imageURLs = new ArrayList<String>();
    }
    private ComicKeeper() {}

    /*Returns "true" if import is successful, "false" otherwise*/
    public boolean importURLs(String filename) {
	Scanner in;

	try {
	    File inFile = new File(filename);
	    in = new Scanner(inFile);
	} catch(Exception ex) {
	    return false;
	}
	
	int size = Integer.parseInt(in.nextLine());
	imageURLs = new ArrayList<String>(size);
	for(int i = 0; i < size && in.hasNextLine(); i++) {
	    if(imageURLs.size() == i) {
		imageURLs.add(in.nextLine());
	    } else if(imageURLs.size() > i) {
		imageURLs.set(i, in.nextLine());
	    } else {
		return false;
	    }
	}

	defaultURL = imageURLs.get(0);

	return true;
    }

    private void setURLs(ArrayList<String> urls, String defaultURL) {
	imageURLs = urls;
	this.defaultURL = defaultURL;
    }

    public String getName() {
	return name;
    }

    public String getDefaultURL() {
	return defaultURL;
    }

    /*public void setDefaultURL(String url) {
	if(url.substring(0,7) == "http://") {
	    defaultURL = url;
	}
    }*/

    public String getURL(int index) {
	if(index < imageURLs.size()) {
	    String url = imageURLs.get(index);
	    return url;
	}
	return "/";
    }
    
    public String getNextURL(String previousURL) {
	int current = findURL(previousURL);
	if(current < 0) {
	    return "/";
	} 

	if(imageURLs.size() <= current+1) {
	    return imageURLs.get(current);
	}
	return imageURLs.get(current+1);
    }

    public String getNextURL(int previousIndex) {
	if(previousIndex < 0) {
	    return "/";
	}

	if(imageURLs.size() <= previousIndex+1) {
	    return imageURLs.get(previousIndex);
	}

	return imageURLs.get(previousIndex+1);
    }

    public int findURL(String URL) {
	for(int i = 0; i < imageURLs.size(); i++) {
	    if(imageURLs.get(i).equals(URL)) {
		return i;
	    }
	}
	return -1;
    }

    public String toString() {
	String result = "";
	result += name;
	result += "\n";
	result += defaultURL;
	result += "\n";
	result += imageURLs.size();
	result += "\n";
	for(int i  = 0; i < imageURLs.size(); i++) {
	    result += imageURLs.get(i);
	    result += "\n";
	}

	return result;
    }

    public static ComicKeeper fromString(String in) {
	Scanner scn = new Scanner(in);
	
	//Put this everywhere
	if(!scn.hasNextLine()) {
	    return null;
	}

	String name = scn.nextLine();
	String defaultURL = scn.nextLine();
	int sizeOf = Integer.parseInt(scn.nextLine());
	ArrayList<String> imageURLs  = new ArrayList<String>(sizeOf);
	for(int i = 0; i < sizeOf; i++) {
	    imageURLs.add(scn.nextLine());
	}

	ComicKeeper result = new ComicKeeper(name);
	result.setURLs(imageURLs, defaultURL);

	return result;
    }

    //For Serializable
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
	out.writeChars(name);
	out.writeInt(imageURLs.size());
	for(int i = 0; i < imageURLs.size(); i++) {
	    out.writeChars(imageURLs.get(i));
	}
    }

    //For Serializable
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
	name = in.readUTF();
	int size = in.readInt();
	imageURLs = new ArrayList<String>(size);
	for(int i = 0; i < size; i++) {
	    imageURLs.set(i, in.readUTF());
	}
    }

    //For Serializable
    private void readObjectNoData() throws ObjectStreamException {
	name = null;
	imageURLs = null;
    }

}