package codepath.com.parsetagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import codepath.com.parsetagram.model.Post;

public class ParseApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("cic-parse")
                .clientKey("cic-key")
                .server("http://cicely-parsetagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
