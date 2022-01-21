package com.example.polly.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polly.R;
import com.example.polly.activities.polldetails;

import java.util.ArrayList;

public class pollomAdapter  extends RecyclerView.Adapter<pollomAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList _id, _title, _location;

    public pollomAdapter(Activity activity, Context context, ArrayList _idd, ArrayList _titlee, ArrayList _locationn){
        this.activity = activity;
        this.context = context;
        this._id = _idd;
        this._title = _titlee;
        this._location = _locationn;

    }
    @NonNull
    @Override
    public pollomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_poll_row_view, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.book_id_txt.setText(String.valueOf(_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(_location.get(position)));
        //holder.resultTextView.setText("Result: "+"");
        holder.resultTextView.setVisibility(View.GONE);
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, polldetails.class);
                intent.putExtra("id", String.valueOf(_id.get(position)));
                intent.putExtra("title", String.valueOf(_title.get(position)));
                intent.putExtra("loca", String.valueOf(_location.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }
    @Override
    public int getItemCount() {
        return _id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt, book_author_txt, resultTextView;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.id_txt);
            book_title_txt = itemView.findViewById(R.id.title_txt);
            book_author_txt = itemView.findViewById(R.id.location_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            resultTextView = itemView.findViewById(R.id.resultTv);
            //Animate Recyclerview
            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
           // mainLayout.setAnimation(translate_anim);
        }

    }
}
