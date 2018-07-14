package codepath.com.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import codepath.com.parsetagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public void addAll (ArrayList<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        holder.tvUser.setText(post.getUser().getUsername());
        holder.tvUser2.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getDescription());
        holder.tvCreatedAt.setText(post.getRelativeTimeAgo());
        holder.tvComments.setText("View all comments");
        holder.tvLikes.setText(post.getLikes() + " likes");

        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPostImg);
    }

    @Override
    public int getItemCount() { return mPosts.size();}


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImg;
        public ImageView ivPostImg;
        public TextView tvUser;
        public TextView tvUser2;
        public TextView tvCaption;
        public TextView tvLikes;
        public TextView tvComments;
        public TextView tvCreatedAt;
        public ImageButton btnLike;
        public ImageButton btnComment;
        public ImageButton btnDm;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImg = itemView.findViewById(R.id.ivProfileImg);
            ivPostImg = itemView.findViewById(R.id.ivPostImg);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvUser2 = itemView.findViewById(R.id.tvUser2);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvComments = itemView.findViewById(R.id.tvComments);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnComment= itemView.findViewById(R.id.btnComment);

            tvComments.setOnClickListener(this);
            tvCaption.setOnClickListener(this);
            btnComment.setOnClickListener(this);
            btnLike.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (view.getId() == tvComments.getId() || view.getId() == tvCaption.getId() || view.getId() == btnComment.getId()) {
                    Post post = mPosts.get(position);
                    ParseUser parseUser = post.getUser();
                    Log.d("PostAdapter", "Clicked Post");
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("post", post);
                    intent.putExtra("user", parseUser
                    );
                    context.startActivity(intent);
                } else if (view.getId() == btnLike.getId()){
                    Post post = mPosts.get(position);
                    post.updateLikes();
                    post.saveInBackground();
                    notifyDataSetChanged();
                }
            }
        }
    }
}
