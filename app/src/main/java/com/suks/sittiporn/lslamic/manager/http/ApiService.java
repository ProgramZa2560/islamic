package com.suks.sittiporn.lslamic.manager.http;

import com.suks.sittiporn.lslamic.model.reponse.AddCommentModel;
import com.suks.sittiporn.lslamic.model.reponse.CommentListModel;
import com.suks.sittiporn.lslamic.model.reponse.GetTimeModel;
import com.suks.sittiporn.lslamic.model.reponse.ImgListModel;
import com.suks.sittiporn.lslamic.model.reponse.LocationListModel;
import com.suks.sittiporn.lslamic.model.reponse.MemberModel;
import com.suks.sittiporn.lslamic.model.request.CommentRequestModel;
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


    @GET("getListLocation.php")
    Observable<LocationListModel> getListLocation(@Query("id") String id);

    @GET("getTime.php")
    Observable<GetTimeModel> getTime(@Query("date") String date);

    @GET("getListFavoriteUse.php")
    Observable<LocationListModel> getListFavoriteUse(@Query("id") String id);

    @GET("getComment.php")
    Observable<CommentListModel> getListComment(@Query("location_id") String location_id);

    @POST("addComment.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<AddCommentModel> addComent(@Body CommentRequestModel requestModel);

    @GET("getImgLocation.php")
    Observable<ImgListModel> getListImgLocation(@Query("id") String id);


}
