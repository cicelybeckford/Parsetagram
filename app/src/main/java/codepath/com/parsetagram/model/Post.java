package codepath.com.parsetagram.model;

import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Locale;

@ParseClassName("Post")
public class Post extends ParseObject{
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_LIKES = "likes";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public String getRelativeTimeAgo() {
        String timeCreated = getCreatedAt().toString();
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        long dateMillis = 0;
        try {
            dateMillis = sf.parse(timeCreated).getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

        return relativeDate;
    }

    public ParseFile getImage() { return getParseFile(KEY_IMAGE); }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser (ParseUser User) {
        put(KEY_USER, User);
    }

    public Integer getLikes() { return getInt(KEY_LIKES);}

    public void updateLikes() { put(KEY_LIKES, getLikes() + 1);}

    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }

        public Query getUserPost(ParseUser parseUser) {
            whereEqualTo(KEY_USER, parseUser);
            include("user");
            return this;
        }
    }
}
