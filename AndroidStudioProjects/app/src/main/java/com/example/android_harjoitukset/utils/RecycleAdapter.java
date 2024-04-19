package com.example.android_harjoitukset.utils;

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
    protected ArrayList<Item> dataSetFiltered;

    protected ArrayList<Item> FullList;

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        /**
         * @param v The view that was clicked.
         */
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
        dataSet = itemList;
        FullList = itemList;
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
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                dataSetFiltered = new ArrayList<>();
                if (charString.isEmpty()) {
                    dataSetFiltered = FullList;
                } else {
                    String filterPattern = charString.toLowerCase().trim();
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
                dataSet = (ArrayList<Item>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
