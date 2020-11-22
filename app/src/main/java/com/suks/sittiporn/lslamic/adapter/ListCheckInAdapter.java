package com.suks.sittiporn.lslamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.DetailCheckInActivity;
import com.suks.sittiporn.lslamic.model.reponse.LocationReponseModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.suks.sittiporn.lslamic.manager.Retrofit2.BASE_URL;

public class ListCheckInAdapter extends RecyclerView.Adapter implements Filterable {
    private Context context;
    private List<LocationReponseModel> mDataset;
    private List<LocationReponseModel> contactListFiltered;

    public ListCheckInAdapter(Context applicationContext, List<LocationReponseModel> categoery) {
        context = applicationContext;
        mDataset = categoery;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list_check_in, parent, false);
        this.contactListFiltered = mDataset;
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_person;
        private TextView tv_room;
        private TextView tv_time;
        private TextView tv_head_name;
        private CircleImageView img_room;
        private CardView cardView;
        //
        public ListViewHolder(View itemView) {
            super(itemView);
            tv_person = (TextView) itemView.findViewById(R.id.tv_person);
            tv_room = (TextView) itemView.findViewById(R.id.tv_room);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_head_name = (TextView) itemView.findViewById(R.id.tv_head_name);
            img_room = (CircleImageView) itemView.findViewById(R.id.img_room);
            cardView = (CardView) itemView.findViewById(R.id.root_container);


            itemView.setOnClickListener(this);
        }

        public void bindView(final int position) {
//
            tv_time.setText(mDataset.get(position).getTimeStart().split(":")[0] +":"+
                    mDataset.get(position).getTimeStart().split(":")[1] +"-"+
                    mDataset.get(position).getTimeEnd().split(":")[0] +":"+
                    mDataset.get(position).getTimeEnd().split(":")[1] +" น.");
            tv_room.setText(mDataset.get(position).getRoomnumber());
            tv_person.setText(mDataset.get(position).getNumberfull());
            tv_head_name.setText(mDataset.get(position).getName());



            String path = "";
            String url = "";
            try {
                path = mDataset.get(position).getImage_url().split("/")[0];
            }catch (Throwable e){
            }


            if (path.equals("img")){
                url = BASE_URL + mDataset.get(position).getImage_url();
            }else {
                url = "https://lslamicplace.com/locationmuslim/" + mDataset.get(position).getImage_url();
            }

            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.camera200)
                            .error(R.drawable.camera200)
                            .fitCenter())
//                .placeholder(R.drawable.ic_photo)
//                .fitCenter()
//                .animate(android.R.anim.fade_in)
                    .into(img_room);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailCheckInActivity.class);
                    intent.putExtra("locationId", mDataset.get(position).getId());
                    intent.putExtra("time", tv_time.getText().toString());
                    intent.putExtra("room", mDataset.get(position).getRoomnumber());
                    intent.putExtra("person", mDataset.get(position).getNumberfull());
                    intent.putExtra("name", mDataset.get(position).getName());
                    intent.putExtra("lat", mDataset.get(position).getLatitude());
                    intent.putExtra("lng", mDataset.get(position).getLongitude());
                    intent.putExtra("location", mDataset.get(position).getLocation());
                    intent.putExtra("desc", mDataset.get(position).getAddress());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataset = contactListFiltered;
                } else {
                    List<LocationReponseModel> filteredList = new ArrayList<>();
                    for (LocationReponseModel row : contactListFiltered) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getTimeStart().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getTimeEnd().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getNumberfull().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCounty().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getId().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getZone().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getStatus().toLowerCase().contains(charString.toLowerCase())

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