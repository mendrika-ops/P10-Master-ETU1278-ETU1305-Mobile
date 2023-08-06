package com.example.tongasoa.ui.commentaire;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tongasoa.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentaireCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentaireCard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextView comment;
    private TextView nameComment;

    public CommentaireCard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommentaireCard.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentaireCard newInstance(String param1, String param2) {
        CommentaireCard fragment = new CommentaireCard();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commentaire_card, container, false);
        comment = view.findViewById(R.id.comment);
        nameComment = view.findViewById(R.id.nameComment);
        comment.setText("Tonga eto samoela");
        nameComment.setText("Tatiana");
        return view;
    }
}