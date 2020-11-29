package com.example.chessclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PreferrencesAdapter extends RecyclerView.Adapter<PreferrencesAdapter.ViewHolder> {

    public interface OnItemClickListener
    {
        void onItemClick(View v,int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    private ArrayList<CustomTimerData> arrayList;
    private Context context;


    public PreferrencesAdapter(ArrayList<CustomTimerData> arrayList, Context context){

        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PreferrencesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.preferences_layout,parent,false);
        return new ViewHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(arrayList.get(position).getTitle()+"");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View itemView ,final OnItemClickListener listener) {
            super(itemView);
            textView=itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(v,position);
                        }
                    }
                }
            });
        }
    }
}
