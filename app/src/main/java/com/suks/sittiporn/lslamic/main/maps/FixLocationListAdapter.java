package com.suks.sittiporn.lslamic.main.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.model.reponse.LocationReponseModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FixLocationListAdapter extends RecyclerView.Adapter implements Filterable {
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private Context context;

    private Listener listener;

    private List<LocationReponseModel> mDataset = new ArrayList<>();
    private List<LocationReponseModel> locationList = new ArrayList<>();

    public interface Listener {

        void location(LocationReponseModel location);
    }


    public FixLocationListAdapter(Context context, List<LocationReponseModel> locationList, Listener listener) {
        this.context = context;
        this.mDataset = locationList;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location_list, parent, false);
        this.locationList = mDataset;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        TimeStampCriteriaEntity model = locationList.get(position);
        ((ViewHolder) holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation;
        TextView tvDistance;
        CardView items;

        public ViewHolder(View itemView) {
            super(itemView);
        }


        public void bindView(final int position) {

            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            items = (CardView) itemView.findViewById(R.id.items);
            String dis = String.format("%.2f", Double.valueOf(mDataset.get(position).getDistance()));
            tvDistance.setText(dis + " กิโลเมตร");
            tvLocation.setText(mDataset.get(position).getName());
            items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.location(mDataset.get(position));
                }
            });

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataset = locationList;
                } else {
                    List<LocationReponseModel> filteredList = new ArrayList<>();
                    for (LocationReponseModel row : locationList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (
                                row.getLatitude().toLowerCase().contains(charString.toLowerCase()) ||
                                        row.getLongitude().toLowerCase().contains(charString.toLowerCase()) ||
                                        row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                        row.getNumberfull().toLowerCase().contains(charString.toLowerCase()) ||
                                        row.getRoomnumber().toLowerCase().contains(charString.toLowerCase()) ||
                                        row.getDatetime().toLowerCase().contains(charString.toLowerCase()) ||
                                        row.getTimeStart().toLowerCase().contains(charString.toLowerCase()) ||
                                        row.getTimeEnd().toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row);
                        }
                    }
                    mDataset = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataset;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataset = (ArrayList<LocationReponseModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
