package com.suks.sittiporn.lslamic.main.checkinlist.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.adapter.ListCheckInAdapter;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.LocationListModel;
import com.suks.sittiporn.lslamic.model.reponse.LocationReponseModel;
import com.suks.sittiporn.lslamic.realm.RealmUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CheckInListFragment extends Fragment {

    private CheckInListViewModel mViewModel;
    private RecyclerView recyclerView;
    private ListCheckInAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView search;
    private String id;

    public static CheckInListFragment newInstance() {
        return new CheckInListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.check_in_list_fragment, container, false);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initinstanceState();
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

        ApiService apiService = Retrofit2.getApiService();

        Observable<LocationListModel> observable = apiService.getListLocation(id);
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
                        mAdapter = new ListCheckInAdapter(getContext(), list);
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
            model.setLocation(reponseModel.getLocation());
            model.setAddress(reponseModel.getAddress());
        }

        return model;
    }

}