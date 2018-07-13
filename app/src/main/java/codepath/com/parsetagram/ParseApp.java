package codepath.com.parsetagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import codepath.com.parsetagram.model.Comment;
import codepath.com.parsetagram.model.Post;
import codepath.com.parsetagram.model.User;

public class ParseApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(User.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("cic-parse")
                .clientKey("cic-key")
                .server("http://cicely-parsetagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
