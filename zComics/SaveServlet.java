/** Created by Zachary Helfinstein
 *  There is some sort of copyright on this material, I haven't decided what yet.
 *  So basically just don't use it at the moment, all right?
 */

package zComics;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class SaveServlet extends HttpServlet {

    private Logger log = Logger.getLogger(SaveServlet.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	UserService uServe = UserServiceFactory.getUserService();
	User user = uServe.getCurrentUser();

	DatastoreService dServe = DatastoreServiceFactory.getDatastoreService();
	MemcacheService mServe = MemcacheServiceFactory.getMemcacheService();
	
	Entity userPrefs = ComicChooser.getUserPrefs(user, dServe, mServe);
	String currentComic = (String)userPrefs.getProperty(ComicChooser.COMIC_NAME);

	String newURL = (String)req.getParameter("new_url");
	
      	if(urlIsValid(newURL)) {
	    userPrefs.setProperty(ComicChooser.makeURLKey(currentComic), newURL);
	} else {
	    log.warning("The url provided is not valid. Please try again");
	}
	
	dServe.put(userPrefs);
	mServe.put(ComicChooser.USER_KEY+user.getUserId(), userPrefs);
	resp.sendRedirect("/");
   }

    private boolean urlIsValid(String url) {
	if(url == null) {
	    return false;
	}
	if(url.equals("")) {
	    return false;
	} if(url.charAt(0) != 'h') {
	    return false;
	}
	return true;
    }
}