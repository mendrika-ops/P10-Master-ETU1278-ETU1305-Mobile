package com.example.tongasoa.ui.commentaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongasoa.R;
import com.example.tongasoa.modele.Commentaire;

import java.util.ArrayList;

public class ListeCommentaire extends RecyclerView.Adapter<ListeCommentaire.viewHolder>{
    private ArrayList<Commentaire> commentaires;
    private Context ctx;

    private FragmentManager fragmentManager;

    public ListeCommentaire(ArrayList<Commentaire> commentaires, Context ctx, FragmentManager fragmentManager) {
        this.commentaires = commentaires;
        this.ctx = ctx;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ListeCommentaire.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.fragment_commentaire_card, parent, false);
        return new ListeCommentaire.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListeCommentaire.viewHolder holder, int position) {
        Commentaire commentaire = this.commentaires.get(position);
        holder.comment.setText(commentaire.getCommentaire() +" - " + commentaire.getCreateDate());
        holder.nameComment.setText(commentaire.getNote());
    }

    @Override
    public int getItemCount() {
        return this.commentaires.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView nameComment;
        TextView comment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
            nameComment = itemView.findViewById(R.id.nameComment);
        }
    }
}
