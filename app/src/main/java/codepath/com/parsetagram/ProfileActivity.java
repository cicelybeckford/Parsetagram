package codepath.com.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import codepath.com.parsetagram.model.Post;
import codepath.com.parsetagram.model.User;

public class ProfileActivity extends AppCompatActivity {

    private final Post.Query postsQuery = new Post.Query();

    private ParseUser parseUser;
    private ProfileAdapter profileAdapter;
    private ArrayList<Post> posts;
    private RecyclerView rvProfile;
    private ImageView ivImageProfile;
    private TextView tvName;
    private TextView tvUsernameProfile;
    private User user;
    private BottomNavigationView bottomNavigationView;
    private Button btnOfficialLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ParseUser user = ParseUser.getCurrentUser();

        bottomNavigationView = findViewById(R.id.bottom_navigation_logout);
        btnOfficialLogout = findViewById(R.id.btnLogout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        goHome();
                        return true;
                    case R.id.action_post:
                        //newPost();
                        return true;
                    case R.id.action_user:
                        //logOut();
                        return true;
                }
                return true;
            }
        });


         ivImageProfile = findViewById(R.id.ivImageProfile);
         tvUsernameProfile = findViewById(R.id.tvUsernameProfile);
         rvProfile = findViewById(R.id.rvProfile);
         posts = new ArrayList<>();
         profileAdapter = new ProfileAdapter(posts);
         parseUser = getIntent().getParcelableExtra("user");
         user = (User) parseUser;

         tvUsernameProfile.setText(parseUser.getUsername());
         GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5, GridLayoutManager.HORIZONTAL, false);
         rvProfile.setLayoutManager(gridLayoutManager);
         rvProfile.setAdapter(profileAdapter);
         loadPosts();

        btnOfficialLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseUser.logOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void loadPosts() {
        postsQuery.getUserPost(ParseUser.getCurrentUser());
        postsQuery.orderByAscending("createdAt").findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                for (int i = 0; i < objects.size(); i++) {
                   Post comment = objects.get(i);
                    posts.add(comment);
                    profileAdapter.notifyItemChanged(posts.size() - 1);
                }
            }
        });
    }

    private void goHome() {
        Intent intent = new Intent(this,FeedActivity.class);
        startActivity(intent);
        finish();
    }
}
