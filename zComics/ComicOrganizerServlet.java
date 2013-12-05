/** Created by Zachary Helfinstein
 *  This file has some sort of copyright protection, but I haven't decided what yet.
 *  So for the time being, I'm going to say it's all rights reserved, but I'll fix this
 *  soon, I swear.
 *  Last edited: 10/19/13
 */

package zComics;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

import java.util.logging.*;

@SuppressWarnings("serial")
public class ComicOrganizerServlet extends HttpServlet {

    //Allows me to log warnings
    private Logger log = Logger.getLogger(ComicOrganizerServlet.class.getName());
    
    //Makes names, etc easy to change and hard to misspell (compiler catches)
    public static final String GLOBAL = "Global", INIT = "Initialized", TRUE = "True", 
	COMICS = "List of all comics";

    //***The method that is called when this webpage is requested
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {

	//	Queue queue = QueueFactory.getDefaultQueue();
	//	queue.add();

	//Gets the user, login/logout URLs, and sets the default for comicUrl and comicName
	UserService uServe = UserServiceFactory.getUserService();
	User user = uServe.getCurrentUser();
	String loginUrl = uServe.createLoginURL("/");
	String logoutUrl = uServe.createLogoutURL("/");
	String comicUrl = ComicChooser.XKCD_URL;
	String comicName = ComicChooser.XKCD;

	//Gets the Datastore (persistent memory) and memcache (temporary memory)
	DatastoreService dServe = DatastoreServiceFactory.getDatastoreService();
	MemcacheService mServe = MemcacheServiceFactory.getMemcacheService();
	
	//calls the initializer and stores the list of comic names in comicNames
	String stuff = getStrProperty(getGlobal(dServe, mServe), COMICS, "undefined");
	String[] comicNames = ComicChooser.makeStringArray(stuff);
	
	//Gets the user preferences
	Entity userPrefs = ComicChooser.getUserPrefs(user, dServe, mServe);

	//Checks if the preferences are null. If not, get the name of the user's current comic and its URL.
	//If it is null, sets a default comic.
	if(userPrefs != null) {
	    //Get the comic name and url from userPrefs.
	    comicName = getStrProperty(userPrefs, ComicChooser.COMIC_NAME, ComicChooser.XKCD);
	    comicUrl = getStrProperty(userPrefs, ComicChooser.makeURLKey(comicName), 
				      getURL(comicName, dServe, mServe));
	} else {
	    //Default values
	    comicName = ComicChooser.XKCD;
	    comicUrl = ComicChooser.XKCD_URL;
	}
	
	//Set the attriubtes for the jsp to use
	req.setAttribute("ComicURL", comicUrl);
	req.setAttribute("ComicName", comicName);
	req.setAttribute("listOfComicNames", comicNames);
	req.setAttribute("user", user);
	req.setAttribute("loginUrl", loginUrl);
	req.setAttribute("logoutUrl", logoutUrl);
	
	resp.setContentType("text/html");

       	RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/home.jsp");
	jsp.forward(req, resp);
    }

    //---------PRIVATE METHODS------------   

    //Method to check initialization
    public static Entity getGlobal(DatastoreService dServe, MemcacheService mServe) {
	String cacheKey = GLOBAL;
	Entity global = (Entity)mServe.get(cacheKey);
	
	//Checks if global is null. If not, check if it's initialized.
	if(global != null) {
	    String init = getStrProperty(global, INIT, null);

	    // If it's initialized, just get the array and leave
	    if(init != null && init.equals(TRUE)) {
		return global;
	    }
        }

	//If there is no global, try to get the global from the datastore
	if(global == null) {
	    Key key = KeyFactory.createKey(GLOBAL, GLOBAL);
	    try {
		global = dServe.get(key);
		String init = getStrProperty(global, INIT, "");
		if(init != null && init.equals(TRUE)){
		    return global;
		}
	    } catch(EntityNotFoundException e) {
		//If it's not in the datastore, make a new one.
		global = new Entity(key);
	    }
	}
	 
	//create list of comics (hopefully figure out a better way to find these comics instead of hard-coding 
	//them)
	String[] comics = {ComicChooser.XKCD, ComicChooser.SMBC};
	String comicsString = ComicChooser.makeString(comics);

	//set the list of comics as an entity in global
	global.setProperty(COMICS, comicsString);
	String[] urlFiles = {"xkcd.txt", "smbc.txt"};

	//Make each comic entity of it doesn't exist
	for(int i = 0; i < comics.length; i++) {
	    ComicKeeper ck = (ComicKeeper)global.getProperty(comics[i]);    
	    if(ck == null) {
		ck = new ComicKeeper(comics[i]);
		ck.importURLs(urlFiles[i]);
		global.setProperty(comics[i], ck);
	    }
	}
	global.setProperty(INIT, TRUE);
	String result = (String)global.getProperty(COMICS);
	return global;
    }

    //Parameter: entity is an entity you wish to access a String property in
    //Parameter: key is the string key that accesses the property
    //Parameter: alt is the alternate value you want to get back if nothing is found.
    //Returns: entity.getProperty(key) if it's not null or empty; alt otherwise
    public static String getStrProperty(Entity entity, String key, String alt) {
	String property = (String)entity.getProperty(key);
	if(property == null || property.equals("")) {
	    property = alt;
	}
	return property;
    }

    //Gets the default URL of the comic (there's an edge case where this could fail)
    public static String getURL(String comicName, DatastoreService dServe, MemcacheService mServe) {
	ComicKeeper comicSettings = ComicChooser.getComicSettings(comicName, dServe, mServe);
	return comicSettings.getDefaultURL();
    }
}
