/** Created by Zachary Helfinstein
 *  There is some sort of copyright on this material, I haven't decided what yet.
 *  So basically just don't use it at the moment, all right?
 *  
 *  Last edited: 10/19/13
 */

package zComics;

import java.io.IOException;
import java.util.logging.*;

//Import HTTP stuff
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

//Import App Engine stuff
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class NextServlet extends HttpServlet {

    //Lets me log stuff with log.warning()
    private Logger log = Logger.getLogger(NextServlet.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	//Gets the user, datastore, memcache, and user preferences
	UserService uServe = UserServiceFactory.getUserService();
	User user = uServe.getCurrentUser();
	DatastoreService dServe = DatastoreServiceFactory.getDatastoreService();
	MemcacheService mServe = MemcacheServiceFactory.getMemcacheService();
	Entity userPrefs = ComicChooser.getUserPrefs(user, dServe, mServe);

	//If the user preferences are not null, get the comic name, comic URL, and numerical index of the comic.
	if(userPrefs != null) {
	    Comic comic = (Comic)userPrefs.getProperty(ComicChooser.CURRENT_COMIC);
	    if(comic == null) {
		return;
	    }
	    comic.next();
	}
	resp.sendRedirect("/");
    }

    //============================================================================================
    //                                       DEPRECATED
    //============================================================================================
    /*    private String getURLXKCD(String url, DatastoreService dServe, MemcacheService mServe) {
	String myInt = "";
	int index = -1, currentIndex = -1;
	
	if(url.length() > 16) {
	    myInt = url.substring(16, url.length());
	} else {
	    index = -1;
	}	    
	if(myInt == "" || myInt == "/") {
	    index = -1;
	} else {
	    
	    index = Integer.parseInt(myInt) + 1;
	    
	    ComicKeeper comicSettings = ComicChooser.getComicSettings(ComicChooser.XKCD, dServe, mServe);
	    if(comicSettings == null) {
		log.warning("I could not find the comic settings. This comic could be nonexistent");
	    } else {
		String currentIndexString = (String)comicSettings.getProperty(ComicChooser.CURRENT_COMIC);
		
		if(currentIndexString == null || currentIndexString == "") {
		    log.warning("I could not find the current index. This comic could be nonexistent");
		    currentIndex = -1;
		} else {
		    
		    currentIndex = Integer.parseInt(currentIndexString);
		}
	    }
	}
	if(currentIndex > 0 && index > currentIndex) {
	    index = currentIndex;
	} else if(index < 0) {
	    index = currentIndex;
	}
	
	String newComicURL = ComicChooser.NOT_SUPPORTED;
	if(index > 0) {
	    newComicURL = url.substring(0, 16) + index; 
	    url = newComicURL;
	}
	return url;
	}*/

    //============================================================================================
    //                                       DEPRECATED
    //============================================================================================    
    /*private String getURLSMBC(String url) {
	return "/";
    }*/
}