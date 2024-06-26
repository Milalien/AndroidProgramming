package com.example.android_harjoitukset.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_harjoitukset.R;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> implements Filterable {

    public static final String TAG = "RecycleAdapter";

    protected ArrayList<Item> dataSet;


    protected ArrayList<Item> FullList;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvName;
        protected TextView tvId;
        protected TextView tvCompanyForm;
        protected TextView tvRegistrarionDate;
        protected LinearLayout linearLayout;

        public MyViewHolder(View v) {

            super(v);
            tvName = v.findViewById(R.id.compName);
            tvId = v.findViewById(R.id.compIdVal);
            tvCompanyForm = v.findViewById(R.id.compFormVal);
            tvRegistrarionDate = v.findViewById(R.id.regDateVal);
            linearLayout = v.findViewById(R.id.cardContent);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (linearLayout.getVisibility() == View.GONE) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        }
    }

    public RecycleAdapter(ArrayList<Item> itemList) {
        this.dataSet = itemList;
        FullList = new ArrayList<>(itemList);
    }


    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
        Item currentItem = dataSet.get(position);
        holder.tvName.setText(currentItem.name);
        holder.tvId.setText(currentItem.id);
        holder.tvCompanyForm.setText(currentItem.companyForm);
        holder.tvRegistrarionDate.setText(currentItem.registrationDate);

    }

    @Override
    public int getItemCount() {
        return (dataSet == null) ? 0 : dataSet.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                ArrayList<Item> dataSetFiltered = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    dataSetFiltered.addAll(FullList);
                   
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Item item : FullList) {
                        if (item.name.toLowerCase().contains(filterPattern)) {
                            dataSetFiltered.add(item);
                        }
                    }

                }
                FilterResults results = new FilterResults();
                results.values = dataSetFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataSet.clear();

                dataSet.addAll((ArrayList<Item>) results.values);
                notifyDataSetChanged();
            }
        };
    }


}
