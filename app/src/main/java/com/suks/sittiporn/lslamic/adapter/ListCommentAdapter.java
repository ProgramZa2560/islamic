package com.suks.sittiporn.lslamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.DetailCheckInActivity;
import com.suks.sittiporn.lslamic.model.reponse.CommentReponseModel;
import com.suks.sittiporn.lslamic.model.reponse.LocationReponseModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.suks.sittiporn.lslamic.manager.Retrofit2.BASE_URL;

public class ListCommentAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CommentReponseModel> mDataset;
    private List<CommentReponseModel> contactListFiltered;

    public ListCommentAdapter(Context applicationContext, List<CommentReponseModel> categoery) {
        context = applicationContext;
        mDataset = categoery;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list_comment, parent, false);
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

        private TextView tv_comment;
        private TextView tv_name;
        private TextView tv_time;
        private CircleImageView img_room;
        private CardView cardView;
        //
        public ListViewHolder(View itemView) {
            super(itemView);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            img_room = (CircleImageView) itemView.findViewById(R.id.img_room);
            cardView = (CardView) itemView.findViewById(R.id.root_container);


            itemView.setOnClickListener(this);
        }

        public void bindView(final int position) {
//
            tv_time.setText(mDataset.get(position).getDate() +" : "+ mDataset.get(position).getTime());
            tv_name.setText(mDataset.get(position).getUser_id());
            tv_comment.setText(mDataset.get(position).getComment());
//            tv_head_name.setText(mDataset.get(position).getName());
//            String url = BASE_URL + mDataset.get(position).getImage_url();
//            Glide.with(context)
//                    .load(url)
//                    .fitCenter()
//                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                    .placeholder(R.mipmap.ic_launcher)
//                    .into(img_room);

//            Glide.with(context)
//                    .load(url)
//                    .apply(new RequestOptions()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .placeholder(R.drawable.bg_home)
//                            .error(R.drawable.bg_home)
//                            .fitCenter())
//                .placeholder(R.drawable.ic_photo)
//                .fitCenter()
//                .animate(android.R.anim.fade_in)
//                    .into(img_room);
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, DetailCheckInActivity.class);
//                    intent.putExtra("locationId", mDataset.get(position).getId());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    v.getContext().startActivity(intent);

//                }
//            });
        }

        @Override
        public void onClick(View v) {

        }
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    mDataset = contactListFiltered;
//                } else {
//                    List<LocationReponseModel> filteredList = new ArrayList<>();
//                    for (LocationReponseModel row : contactListFiltered) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getTimeStart().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getTimeEnd().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getNumberfull().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getCounty().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getId().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getZone().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getStatus().toLowerCase().contains(charString.toLowerCase())
//
//                        ) {
//                            filteredList.add(row);
//                        }
//                    }
//                    mDataset = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mDataset;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mDataset = (ArrayList<LocationReponseModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }


}