package com.example.chessclock;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PreferrencesAdapter extends RecyclerView.Adapter<PreferrencesAdapter.ViewHolder> {


    public interface OnItemClickListener
    {
        void onItemClick(View v,int position);
    }

    private OnItemClickListener mListener;
    private MyDBHelper dbHelper;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    private ArrayList<CustomTimerData> arrayList;
    private final Context context;


    public PreferrencesAdapter(ArrayList<CustomTimerData> arrayList, Context context){

        this.arrayList = arrayList;
        this.context = context;
        dbHelper=new MyDBHelper(context);
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
        ImageView delete;

        public ViewHolder(View itemView ,final OnItemClickListener listener) {
            super(itemView);
            textView=itemView.findViewById(R.id.title);
            delete=itemView.findViewById(R.id.delete);

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

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog(getAdapterPosition());
                }
            });

        }
        public void confirmDialog(int position){
            Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Delete Timer "+arrayList.get(position).getTitle()+" ?");
            builder.setMessage("Are you sure you want to delete "+arrayList.get(position).getTitle()+" ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHelper.deleteTimer(arrayList.get(position).getId()+"");

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }

    }
}
