package com.suks.sittiporn.lslamic.features.favorite.ui.main;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.core.ui.adapter.FavoriteAdapter;
import com.suks.sittiporn.lslamic.core.ui.adapter.ListCheckInAdapter;
import com.suks.sittiporn.lslamic.features.checkinlist.ui.main.CheckInListFragment;
import com.suks.sittiporn.lslamic.features.checkinlist.ui.main.CheckInListViewModel;
import com.suks.sittiporn.lslamic.core.network.RetrofitClient;
import com.suks.sittiporn.lslamic.core.network.ApiService;
import com.suks.sittiporn.lslamic.data.remote.response.LocationListModel;
import com.suks.sittiporn.lslamic.data.remote.response.LocationReponseModel;
import com.suks.sittiporn.lslamic.data.local.RealmUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FavoriteFragment extends Fragment {


    private CheckInListViewModel mViewModel;
    private RecyclerView recyclerView;
    private FavoriteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView search;
    private String id;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_fragment, container, false);
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
//        Intent intent = getActivity().getIntent();
//        examination_head_id = intent.getStringExtra("examination_id");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        search = (SearchView) view.findViewById(R.id.search);
        search.setQueryHint("Search");

        initinstanceState();
    }

    private void initinstanceState() {

        id = RealmUtil.getMemberId();
        getList(id);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList(id);
            }
        });
    }
    private void getList(String id) {

        ApiService apiService = RetrofitClient.getApiService();

        Observable<LocationListModel> observable = apiService.getListFavoriteUse(id);
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

                        for(LocationReponseModel model : dataModel.getData()){
                            LocationReponseModel locationReponseModel = transform(model);
                            list.add(locationReponseModel);

                        }
                        swipeRefreshLayout.setRefreshing(false);
                        mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new FavoriteAdapter(getContext(), list);
                        recyclerView.setAdapter(mAdapter);
                        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {

                                mAdapter.getFilter().filter(newText);

                                return false;
                            }
                        });
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
        }

        return model;
    }


}