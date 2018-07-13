package codepath.com.parsetagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import codepath.com.parsetagram.model.Post;

public class FeedActivity extends AppCompatActivity {
    private static final String imagePath = Environment.getExternalStorageDirectory().getPath();
    private final ParseUser user = ParseUser.getCurrentUser();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_READ_STORAGE = 1;
    private static final int REQUEST_WRITE_STORAGE = 1;
    private final Post.Query postsQuery = new Post.Query();
    private SwipeRefreshLayout swipeContainer;
    private BottomNavigationView bottomNavigationView;
    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        rvPosts = (RecyclerView) findViewById(R.id.rvPost);
        swipeContainer = findViewById(R.id.swipeContainer);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(postAdapter);

        populateFeed();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        // do something here
                        return true;
                    case R.id.action_post:
                        newPost();
                        return true;
                    case R.id.action_user:
                        logOut();
                        return true;
                }
                return true;
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { refreshFeed(); }
        });
        swipeContainer.setColorSchemeResources(R.color.black);
    }

    private void logOut () {
        Intent intent = new Intent(this,LogoutActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void newPost() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            File imageFile = new File(imagePath + "/parsetagram.jpg");
            FileOutputStream out;
            try {
                out = new FileOutputStream(imageFile);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            final ParseFile parseFile = new ParseFile(imageFile);
            parseFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Intent intent = new Intent(FeedActivity.this, PostActivity.class);
                    intent.putExtra("parseFile", parseFile);
                    startActivity(intent);
                }
            });
        }
    }

    private void refreshFeed() {
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                ArrayList<Post> temp_posts = new ArrayList<>();
                postAdapter.clear();
                Post post = null;
                if (e == null) {
                    for (int i = objects.size() - 1; i >= 0 ; i--) {
                        post = objects.get(i);
                        temp_posts.add(post);
                        postAdapter.notifyItemChanged(posts.size() - 1);
                    }
                }
                else {
                    e.printStackTrace();
                }
                postAdapter.addAll(temp_posts);
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void populateFeed() {
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                Post post = null;
                if (e == null) {
                    for (int i = objects.size() - 1; i >= 0; i--) {
                        post = objects.get(i);
                        posts.add(post);
                        postAdapter.notifyItemChanged(posts.size() - 1);
                    }
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }
}
