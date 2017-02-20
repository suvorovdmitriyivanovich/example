package com.example.examle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.examle.objects.Employee;
import com.example.examle.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView city;
        TextView birthday;

        View item;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            city = (TextView) itemView.findViewById(R.id.city);
            birthday = (TextView) itemView.findViewById(R.id.birthday);
            item = itemView;
        }
    }

    private Context mContext;
    private ArrayList<Employee> data;

    public CustomAdapter(Context mContext) {
        this.mContext = mContext;
        this.data = new ArrayList<>();
    }

    public void setData(ArrayList<Employee> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<Employee> list, String search){
        data.clear();
        if(search.isEmpty()) {
            data.addAll(list);
        } else {
            for (Employee employee: list ) {
                if(employee.getFullName().toLowerCase().contains(search.toLowerCase()))
                {
                    data.add(employee);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_employee, parent, false);
        return new CustomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.ViewHolder viewHolder, int position) {

        final Employee employee = (Employee) data.get(position);

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClickEmployee) mContext).onClickEmployee(employee);
            }
        });

        viewHolder.name.setText(employee.getFullName());
        viewHolder.birthday.setText(employee.getAge());
        viewHolder.city.setText(employee.getCity());
    }

    @Override
    public int getItemCount() {
        if(data != null)
            return data.size();
        else
            return 0;
    }

    public Employee getEmployee(int position) {
        return data.get(position);
    }
}
