package com.example.polly.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polly.R;
import com.example.polly.activities.VoteActivity;
import com.example.polly.activities.polldetails;
import com.example.polly.models.Poll;
import com.example.polly.support.Global;

import java.util.ArrayList;

public class NearPollsAdapter extends RecyclerView.Adapter<NearPollsAdapter.MyViewHolder> {
    private Activity context;
    private ArrayList<Poll> polls = new ArrayList<>();

    public NearPollsAdapter(Activity context){
        this.context = context;
    }

    public void setData(ArrayList<Poll> polls){
        this.polls = polls;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NearPollsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_near_poll_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Poll poll = polls.get(position);

        holder.book_id_txt.setText(poll.getIdString());
        holder.book_title_txt.setText(poll.getTitle());

        //Recyclerview onClickListener
        holder.voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.currentSelectedPoll = poll;
                Intent intent = new Intent(context, VoteActivity.class);
                context.startActivity(intent);
            }
        });

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.currentSelectedPoll = poll;
                Intent intent = new Intent(context, polldetails.class);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return polls.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt;
        Button voteBtn;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.id_txt);
            book_title_txt = itemView.findViewById(R.id.title_txt);
            voteBtn = itemView.findViewById(R.id.voteBtn);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
