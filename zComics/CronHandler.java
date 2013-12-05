/** Created by Zachary Helfinstein
 *  There is some sort of copyright on this material, I haven't decided what yet.
 *  So basically just don't use it at the moment, all right?
 *  
 *  Last edited: 10/19/13
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
//import com.google.appengine.api.users.User;
//import com.google.appengine.api.users.UserService;
//import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

@SuppressWarnings("serial")
public class CronHandler extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
	Queue queue = QueueFactory.getDefaultQueue();
	queue.add(withParam("Comic", ComicChooser.XKCD));
    }
}