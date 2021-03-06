package codepath.com.parsetagram.model;

import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Locale;

@ParseClassName("Comment")
public class Comment extends ParseObject{
    private static final String KEY_USER = "user";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_LIKES = "likes";
    private static final String KEY_ID= "objectId";
    private static final String KEY_POST="post";
    private static final String KEY_TIME="createdAt";

    public void setUser (ParseUser User) {
        put(KEY_USER, User);
    }

    public String getObjectId() { return getString(KEY_ID);}

    public ParseUser getUser() { return getParseUser(KEY_USER); }

    public void setMessage(String description) {
        put(KEY_MESSAGE, description);
    }

    public String getMessage() {
        return getString(KEY_MESSAGE);
    }

    public Integer getLikes() { return getInt(KEY_LIKES);}

    public void updateLikes() { put(KEY_LIKES, getLikes()+ 1);}

    public ParseObject getPost() {return getParseObject(KEY_POST); }

    public void setPost(Post post) {put(KEY_POST, post);}

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

    public static class Query extends ParseQuery<Comment> {
        public Query() {
            super(Comment.class);
        }

        public Comment.Query getTop() {
            setLimit(20);
            return this;
        }

        public Comment.Query withUser() {
            include("user");
            return this;
        }

        public Comment.Query findComment(Post post) {
            whereEqualTo(KEY_POST, post);
            include("user");
            include("message");
            return this;
        }
    }
}
