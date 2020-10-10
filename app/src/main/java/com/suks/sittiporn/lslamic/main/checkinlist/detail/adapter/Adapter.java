package com.suks.sittiporn.lslamic.main.checkinlist.detail.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.model.reponse.ImgReponseModel;

import java.util.List;

import static com.suks.sittiporn.lslamic.manager.Retrofit2.BASE_URL;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ImgReponseModel> mApps;
    private boolean mHorizontal;
    private Context context;

    public Adapter(boolean horizontal, List<ImgReponseModel> apps , Context context) {
        mHorizontal = horizontal;
        mApps = apps;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mHorizontal ? new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false)) :
                new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_snap_vertical, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        App app = mApps.get(position);
        String url = "https://lslamicplace.com/locationmuslim/" + mApps.get(position).getImage_url();

        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bg_home)
                        .error(R.drawable.bg_home)
                        .fitCenter())
                .into(holder.imageView);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(view.getContext(), DetailNewsXMLActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView ratingTextView;
        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }

    }

}

