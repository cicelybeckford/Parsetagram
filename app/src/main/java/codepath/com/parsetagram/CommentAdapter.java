package codepath.com.parsetagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import codepath.com.parsetagram.model.Comment;
import codepath.com.parsetagram.model.User;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> mComments;
    Context context;

    public CommentAdapter(List<Comment> comments) {
        mComments = comments;
    }

    public void clear() {
        mComments.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Comment> list) {
        mComments.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View commentView = inflater.inflate(R.layout.item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(commentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        ParseUser parseUser = comment.getUser();
        User user = (User) parseUser;
        Log.d("BindView", "Comment loADS");

        holder.tvCommentUser.setText(parseUser.getUsername());
        holder.tvMessage.setText(comment.getMessage());
        holder.tvlikesCount.setText(comment.getLikes().toString() + " likes");
        holder.tvTimestamp.setText(comment.getCreatedAt().toString());
        //Glide.with(context).load(user.getImage().getUrl()).into(holder.ivProfilePic);
    }

    @Override
    public int getItemCount() { return mComments.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfilePic;
        public TextView tvCommentUser;
        public TextView tvMessage;
        public TextView tvlikesCount;
        public TextView tvTimestamp;
        public ImageButton btnLikeComment;

        public ViewHolder (View itemView) {
            super(itemView);

            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvCommentUser = itemView.findViewById(R.id.tvCommentUser);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvlikesCount = itemView.findViewById(R.id.tvlikesCount);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            btnLikeComment = itemView.findViewById(R.id.btnLikeComment);

            itemView.setOnClickListener(this);
            btnLikeComment.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (view.getId() == btnLikeComment.getId()) {
                    Comment comment = mComments.get(position);
                    comment.updateLikes();
                }
                else {
                    Log.d("CommentAdapter", "Loaded");
                }
            }
        }
    }
}
