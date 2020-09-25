package com.ka8eem.savedatawithroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ka8eem.savedatawithroom.R;
import com.ka8eem.savedatawithroom.models.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<UserModel> list;
    private Context context;

    public UserAdapter() {
    }

    public void setList(List<UserModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel model = list.get(position);
        holder.textName.setText(model.getUserName());
        holder.textAge.setText(model.getAge());
        holder.textGender.setText(model.getGender());
        holder.textJob.setText(model.getJob());
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textAge, textJob, textGender;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_user_name);
            textAge = itemView.findViewById(R.id.text_age);
            textJob = itemView.findViewById(R.id.text_job);
            textGender = itemView.findViewById(R.id.text_gender);
        }
    }
}
