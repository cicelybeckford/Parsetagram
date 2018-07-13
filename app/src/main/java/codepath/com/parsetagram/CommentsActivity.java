package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import codepath.com.parsetagram.model.Comment;
import codepath.com.parsetagram.model.Post;
import codepath.com.parsetagram.model.User;

public class CommentsActivity extends AppCompatActivity {
    private final Comment.Query commentsQuery = new Comment.Query();
    private ParseUser parseUser;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> comments;
    private RecyclerView rvComments;
    private Post post;
    private User user;
    private ImageView ivProfileImgDetails;
    private ImageView ivUserProfileImg;
    private EditText etNewComment;
    private Button btnPostComment;
    private TextView tvUserDetails;
    private TextView tvCaptionDetails;
    private TextView tvTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ivProfileImgDetails = findViewById(R.id.ivProfileImgDetails);
        ivUserProfileImg = findViewById(R.id.ivUserProfileImg);
        etNewComment = findViewById(R.id.etNewComment);
        btnPostComment = findViewById(R.id.btnPostComment);
        tvUserDetails = findViewById(R.id.tvUserDetails);
        tvCaptionDetails = findViewById(R.id.tvCaptionDetails);
        rvComments = findViewById(R.id.rvComments);
        tvTime = findViewById(R.id.tvTime);
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments);
        parseUser = getIntent().getParcelableExtra("user");
        post= getIntent().getParcelableExtra("post");
        if (post.getUser() == null) post.setUser(parseUser);
        user = (User) parseUser;

        tvUserDetails.setText(parseUser.getUsername());
        tvCaptionDetails.setText(post.getDescription());
        tvTime.setText(post.getCreatedAt().toString());
        //Glide.with(getApplicationContext()).load(user.getImage().getUrl()).into(ivProfileImgDetails); TODO
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(commentAdapter);
        loadComments();

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("button", "works");
                final Comment newComment = new Comment();
                String message = etNewComment.getText().toString();
                newComment.setMessage(message);
                newComment.setUser(parseUser);
                newComment.setPost(post);
                newComment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        commentAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void loadComments() {
        commentsQuery.findComment(post);
        commentsQuery.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                for (int i = 0; i < objects.size(); i++) {
                    Comment comment = objects.get(i);
                    comments.add(comment);
                    commentAdapter.notifyItemChanged(comments.size() - 1);
                }
            }
        });
    }
}
