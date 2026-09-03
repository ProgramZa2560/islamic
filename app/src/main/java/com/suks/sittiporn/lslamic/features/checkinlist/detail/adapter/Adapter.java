package com.suks.sittiporn.lslamic.features.checkinlist.detail.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.features.maps.FixLocationListAdapter;
import com.suks.sittiporn.lslamic.data.remote.response.ImgReponseModel;

import java.util.List;

import static com.suks.sittiporn.lslamic.core.network.RetrofitClient.BASE_URL;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ImgReponseModel> mApps;
    private boolean mHorizontal;
    private Context context;
    String path = "";
    String url = "";

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        App app = mApps.get(position);

        try {
            path = mApps.get(position).getImage_url().split("/")[0];
        }catch (Throwable e){

        }

        if (path.equals("img")){
             url = BASE_URL + mApps.get(position).getImage_url();
        }else {
             url = "https://lslamicplace.com/locationmuslim/" + mApps.get(position).getImage_url();
        }


        try{
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.frame)
                            .error(R.drawable.frame)
                            .fitCenter())
                    .into(holder.imageView);
        }catch (Exception e){
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.frame));
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_dialog_view_img);
//                dialog.setCancelable(true);
//                Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

                ImageView imageView = (ImageView) dialog.findViewById(R.id.img_url);
                ImageView close = (ImageView) dialog.findViewById(R.id.close);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                try {
                    path = mApps.get(position).getImage_url().split("/")[0];
                }catch (Throwable e){

                }

                if (path.equals("img")){
                    url = BASE_URL + mApps.get(position).getImage_url();
                }else {
                    url = "https://lslamicplace.com/locationmuslim/" + mApps.get(position).getImage_url();
                }

                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.frame)
                                .error(R.drawable.frame)
                                .fitCenter())
                        .into(imageView);


                dialog.show();
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

