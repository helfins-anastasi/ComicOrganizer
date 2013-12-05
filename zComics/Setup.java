/** Created by Zachary Helfinstein
 *  There is some sort of copyright on this material, I haven't decided what yet.
 *  So basically just don't use it at the moment, all right?
 *  
 *  Last edited: 10/19/13
 */

package zComics;

import java.io.IOException;
import java.util.logging.*;
import java.util.ArrayList;

//Servlet imports
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

//AppEngine imports
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
public class Setup {

    private Logger log = Logger.getLogger(ComicChooser.class.getName());

}