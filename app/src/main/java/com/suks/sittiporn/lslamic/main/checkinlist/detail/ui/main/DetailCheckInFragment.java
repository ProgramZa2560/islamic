package com.suks.sittiporn.lslamic.main.checkinlist.detail.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.adapter.ListCheckInAdapter;
import com.suks.sittiporn.lslamic.adapter.ListCommentAdapter;
import com.suks.sittiporn.lslamic.home.HomeActivity;
import com.suks.sittiporn.lslamic.login.LoginActivity;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.adapter.App;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.adapter.Snap;
import com.suks.sittiporn.lslamic.main.checkinlist.detail.adapter.SnapAdapter;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.AddCommentModel;
import com.suks.sittiporn.lslamic.model.reponse.CommentListModel;
import com.suks.sittiporn.lslamic.model.reponse.CommentReponseModel;
import com.suks.sittiporn.lslamic.model.reponse.ImgListModel;
import com.suks.sittiporn.lslamic.model.reponse.ImgReponseModel;
import com.suks.sittiporn.lslamic.model.reponse.LocationListModel;
import com.suks.sittiporn.lslamic.model.reponse.LocationReponseModel;
import com.suks.sittiporn.lslamic.model.reponse.MemberModel;
import com.suks.sittiporn.lslamic.model.request.CommentRequestModel;
import com.suks.sittiporn.lslamic.model.request.MemberRequestModel;
import com.suks.sittiporn.lslamic.realm.RealmUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailCheckInFragment extends Fragment {

    private DetailCheckInViewModel mViewModel;
    RecyclerView mRecyclerView;
    RecyclerView recyclerViewComment;

    private ListCommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    EditText editText_Comment;
    Button btnComment;

    String locationId;
    String id;
    String time;
    String room;
    String person;
    String name;
    String lat;
    String lng;
    String location;
    String desc;

    ImageView imgLocation;
    TextView tv_location;
    TextView tvName;
    TextView tvRoom;
    TextView tvPerson;
    TextView tvTime;
    TextView tvDesc;

    ImageButton star;
    boolean isEnable = true;

    List<ImgReponseModel> list = new ArrayList<>();


    public static DetailCheckInFragment newInstance() {
        return new DetailCheckInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_check_in_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.init(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initinstanceState();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void init(View view, Bundle savedInstanceState) {

        binID(view, savedInstanceState);

        tv_location.setText(lat +", " + lng);

        getDescLocation(locationId);
        getListComment(locationId);

        boolean favorite = false;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        isEnable = favorite;
        if (isEnable){
//            isEnable = false;
            star.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_off));
        }else{
//            isEnable = true;
            star.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));
        }

//        setupAdapter();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (isShowLast(recyclerView)) {
//                    if (!isScroll) {
//                        isScroll = true;
////                        presenter.LoadLeavelistdata(companyEmployeeViewModel);
//
//
//                    }
//                }
                getListImgLocation(locationId);;
//                setupAdapter();
            }

        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListComment(locationId);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                adbConfirmExit.create();
                adbConfirmExit.setTitle("Comment");
                adbConfirmExit.setMessage("คุณต้องการ sent ใช่หรือไม่!");
                adbConfirmExit.setNegativeButton("ยกเลิก", null);
                adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        addComment("2020-10-10","12:00", editText_Comment.getText().toString());
                    }
                });
                adbConfirmExit.create().show();



            }
        });

        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
//
//                String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                intent.setPackage("com.google.android.apps.maps");
//                startActivity(intent);

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEnable){
                    isEnable = false;
                    star.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_off));
                }else{
                    isEnable = true;
                    star.setImageDrawable(ContextCompat.getDrawable(getContext(),android.R.drawable.btn_star_big_on));
                }
//                isEnable = !isEnable;
            }
        });


    }

    private void initinstanceState() {

    }

    private void binID(View view, Bundle savedInstanceState) {

        recyclerViewComment = (RecyclerView) view.findViewById(R.id.recyclerViewComment);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        editText_Comment = (EditText) view.findViewById(R.id.editText_Comment);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewImg);
        btnComment = (Button) view.findViewById(R.id.btnComment);
        star = (ImageButton) view.findViewById(R.id.star);

        imgLocation = (ImageView) view.findViewById(R.id.imgLocation);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvRoom = (TextView) view.findViewById(R.id.tvRoom);
        tvPerson = (TextView) view.findViewById(R.id.tvPerson);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);

        Intent intent = getActivity().getIntent();
        locationId = intent.getStringExtra("locationId");
        time = intent.getStringExtra("time");
        room = intent.getStringExtra("room");
        person = intent.getStringExtra("person");
        name = intent.getStringExtra("name");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");

        desc = intent.getStringExtra("desc");



        id = RealmUtil.getMemberId();

    }

    private void setupAdapter() {
        getListImgLocation(locationId);;


    }

    private List<ImgReponseModel> getApps() {

          getListImgLocation(locationId);

//        List<ImgReponseModel> apps =   getListImgLocation(locationId);
//        ;

//        apps.add(new App("Gmail", R.drawable.toyota, 4.8f));
//        apps.add(new App("Gmail", R.drawable.mg, 4.8f));
//        apps.add(new App("Gmail", R.drawable.honda, 4.8f));
//        apps.add(new App("Inbox", R.drawable.ks, 4.5f));
//        apps.add(new App("Google Keep", R.drawable.kbank, 4.2f));
//        apps.add(new App("Google Keep", R.drawable.ktb, 4.2f));
//        apps.add(new App("Google Keep", R.drawable.kfc, 4.2f));
        return list;
    }

    private void getListComment(String location_id) {

        ApiService apiService = Retrofit2.getApiService();

        Observable<CommentListModel> observable = apiService.getListComment(location_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<CommentListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentListModel dataModel) {
                        List<CommentReponseModel> list = new ArrayList<>();
                        for (CommentReponseModel model : dataModel.getData()) {
                            CommentReponseModel commentReponseModel = transform(model);
                            list.add(commentReponseModel);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewComment.setLayoutManager(mLayoutManager);
                        mAdapter = new ListCommentAdapter(getContext(), list);
                        recyclerViewComment.setAdapter(mAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public CommentReponseModel transform(CommentReponseModel reponseModel) {
        CommentReponseModel model = null;
        if (reponseModel != null) {
            model = new CommentReponseModel();
            model.setLocation_id(reponseModel.getLocation_id());
            model.setUser_id(reponseModel.getUser_id());
            model.setDate(reponseModel.getDate());
            model.setTime(reponseModel.getTime());
            model.setComment(reponseModel.getComment());
        }

        return model;
    }
  public ImgReponseModel transform(ImgReponseModel reponseModel) {
      ImgReponseModel model = null;
        if (reponseModel != null) {
            model = new ImgReponseModel();
            model.setImage_url(reponseModel.getImage_url());

        }

        return model;
    }

    private void addComment(String date, String time, String comment) {
        CommentRequestModel model = new CommentRequestModel();
        model.setComment(comment);
        model.setUser_id(id);
        model.setLocation_id(locationId);
        model.setDate(date);
        model.setTime(time);

        ApiService apiService = Retrofit2.getApiService();
        Observable<AddCommentModel> observable = apiService.addComent(model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<AddCommentModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddCommentModel response) {
                        editText_Comment.setText("");
                        Toast.makeText(getContext(), "สำเร็จ", Toast.LENGTH_LONG).show();
                        getListComment(locationId);
//                        response.getData().get(0).getLocation_id();
//                        getListComment("2");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "User หรือ Passsword ไม่ถูกต้อง ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getListImgLocation(String location_id) {

        ApiService apiService = Retrofit2.getApiService();

        Observable<ImgListModel> observable = apiService.getListImgLocation(location_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ImgListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImgListModel dataModel) {
//                        List<ImgReponseModel> list = new ArrayList<>();
                        for (ImgReponseModel model : dataModel.getData()) {
                            ImgReponseModel commentReponseModel = transform(model);
                            list.add(commentReponseModel);
                        }

                        List<ImgReponseModel> apps = list;
                        SnapAdapter snapAdapter = new SnapAdapter();
                        snapAdapter.addSnap(new Snap(Gravity.START, "Snap start", apps, getContext()));
                        mRecyclerView.setAdapter(snapAdapter);



//                        swipeRefreshLayout.setRefreshing(false);
//                        mLayoutManager = new LinearLayoutManager(getContext());
//                        recyclerViewComment.setLayoutManager(mLayoutManager);
//                        mAdapter = new ListCommentAdapter(getContext(), list);
//                        recyclerViewComment.setAdapter(mAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getDescLocation(String id) {

        ApiService apiService = Retrofit2.getApiService();

        Observable<LocationListModel> observable = apiService.getLocation(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<LocationListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LocationListModel dataModel) {
                        List<LocationReponseModel> list = new ArrayList<>();
                        tvName.setText(dataModel.getData().get(0).getName());
                        tvRoom.setText(dataModel.getData().get(0).getRoomnumber() + " ห้อง");
                        tvPerson.setText(dataModel.getData().get(0).getNumberfull() + " คน");
//                        tvTime.setText(.getTimeEnd());
                        tvTime.setText(dataModel.getData().get(0).getTimeStart().split(":")[0] +":"+
                                dataModel.getData().get(0).getTimeStart().split(":")[1] +"-"+
                                dataModel.getData().get(0).getTimeEnd().split(":")[0] +":"+
                                dataModel.getData().get(0).getTimeEnd().split(":")[1] +" น.");
                        tvDesc.setText(dataModel.getData().get(0).getAddress());
                        getListImgLocation(dataModel.getData().get(0).getLocation());


//                        for (int i = 0; i < dataModel.getData().size(); i++) {
//                            LocationReponseModel reponseModel = new LocationReponseModel();
//                            reponseModel.setName(dataModel.getApprovedListDataReponseModels().get(i).getExamination_status_id());
//                            reponseModel.setExamination_status_head(dataModel.getApprovedListDataReponseModels().get(i).getExamination_status_head());
//                            reponseModel.setExamination_status_user(dataModel.getApprovedListDataReponseModels().get(i).getExamination_status_user());
//                            reponseModel.setExamination_set_id(dataModel.getApprovedListDataReponseModels().get(i).getExamination_set_id());
//                            reponseModel.setExamination_set(dataModel.getApprovedListDataReponseModels().get(i).getExamination_set());
//                            reponseModel.setExamination_status_user_id(dataModel.getApprovedListDataReponseModels().get(i).getExamination_status_user_id());
//                            reponseModel.setExamination_status_slip(dataModel.getApprovedListDataReponseModels().get(i).getExamination_status_slip());
//                            list.add(reponseModel);
//                        }

//                        for(LocationReponseModel model : dataModel.getData()){
//                            LocationReponseModel locationReponseModel = transform(model);
//                            list.add(locationReponseModel);
//
//                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public LocationReponseModel transform(LocationReponseModel reponseModel) {
        LocationReponseModel model = null;
        if (reponseModel != null) {
            model = new LocationReponseModel();
            model.setId(reponseModel.getId());
            model.setName(reponseModel.getName());
            model.setCounty(reponseModel.getCounty());
            model.setDate(reponseModel.getDate());
            model.setDatetime(reponseModel.getDatetime());
            model.setLatitude(reponseModel.getLatitude());
            model.setLongitude(reponseModel.getLongitude());
            model.setNumberfull(reponseModel.getNumberfull());
            model.setRoomnumber(reponseModel.getRoomnumber());
            model.setStatus(reponseModel.getStatus());
            model.setTimeEnd(reponseModel.getTimeEnd());
            model.setTimeStart(reponseModel.getTimeStart());
            model.setZone(reponseModel.getZone());
            model.setImage_url(reponseModel.getImage_url());
            model.setLocation(reponseModel.getLocation());
            model.setAddress(reponseModel.getAddress());
        }

        return model;
    }

}