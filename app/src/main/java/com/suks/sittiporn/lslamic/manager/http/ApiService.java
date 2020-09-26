package com.suks.sittiporn.lslamic.manager.http;

import com.suks.sittiporn.lslamic.model.reponse.MemberModel;
import com.suks.sittiporn.lslamic.model.request.MemberRequestModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

//    @GET("getMenu.php")
//    Observable<ShipListModel> getList(@Query("isAdd") boolean isAdd);

    @POST("login.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<MemberModel> login(@Body MemberRequestModel requestModel);


//    @GET("ShipList/0/20")
//    Observable<ShipListModel> getListShip(@Query("userKey") String userKey);



}
