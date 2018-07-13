package codepath.com.parsetagram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codepath.com.parsetagram.model.Post;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;

    public ProfileAdapter(List<Post> posts) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_profile, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPosts.get(position);

        holder.tvUser.setText(post.getUser().getUsername());
        holder.tvUser2.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getDescription());
        holder.tvCreatedAt.setText(post.getCreatedAt().toString());
        //holder.tvComments.setText("View all" + post.getComments().size() + "comments"); TODO
        holder.tvLikes.setText(post.getLikes() + " likes");

        //Glide.with(context).load(post.getImage().getUrl()).into(holder.);
    }

    @Override
    public int getItemCount() { return mPosts.size();}


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfilePost;
        public TextView tvUser;
        public TextView tvUser2;
        public TextView tvCaption;
        public TextView tvCreatedAt;
        public TextView tvLikes;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfilePost = itemView.findViewById(R.id.ivProfileImg);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

