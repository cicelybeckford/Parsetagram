package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import codepath.com.parsetagram.model.Post;

public class PostActivity extends AppCompatActivity {
    private final ParseUser user = ParseUser.getCurrentUser();
    private ParseFile parseFile;
    private String description;
    private ImageView ivPost;
    private TextView inputDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        inputDescription = findViewById(R.id.inputDescription);
        ivPost = findViewById(R.id.ivPost);

        parseFile = getIntent().getParcelableExtra("parseFile");
        String imageURL = parseFile.getUrl();
        Glide.with(getApplicationContext()).load(imageURL).into(ivPost);
    }

    public void createPost(View view) {
        final Post newPost = new Post();
        description = inputDescription.getText().toString();
        newPost.setDescription(description);
        newPost.setImage(parseFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success!");
                }
                else {
                    e.printStackTrace();
                }
            }
        });
        finish();
    }
}
