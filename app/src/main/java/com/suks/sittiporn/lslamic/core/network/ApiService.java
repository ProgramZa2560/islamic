package com.suks.sittiporn.lslamic.core.network;

import com.suks.sittiporn.lslamic.data.remote.response.AddCommentModel;
import com.suks.sittiporn.lslamic.data.remote.response.CommentListModel;
import com.suks.sittiporn.lslamic.data.remote.response.EmailResponeModel;
import com.suks.sittiporn.lslamic.data.remote.response.FavoriteByLocationResponeModel;
import com.suks.sittiporn.lslamic.data.remote.response.GetTimeModel;
import com.suks.sittiporn.lslamic.data.remote.response.ImgListModel;
import com.suks.sittiporn.lslamic.data.remote.response.LocationListModel;
import com.suks.sittiporn.lslamic.data.remote.response.MemberModel;
import com.suks.sittiporn.lslamic.data.remote.response.ProfileResponeModel;
import com.suks.sittiporn.lslamic.data.remote.response.SuccessModel;
import com.suks.sittiporn.lslamic.data.remote.response.SuccessRegisterModel;
import com.suks.sittiporn.lslamic.data.remote.request.CommentRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.FavoriteUseRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.ImgRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.LocationRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.MemberRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.RegisterRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.TimeStatusRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.UpdatePasswordRequestModel;
import com.suks.sittiporn.lslamic.data.remote.request.UpdateProfileRequestModel;

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

    @POST("register.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SuccessRegisterModel> register(@Body RegisterRequestModel requestModel);

    @GET("getLocation.php")
    Observable<LocationListModel> getLocation(@Query("id") String id);

    @GET("getListLocation.php")
    Observable<LocationListModel> getListLocation(@Query("id") String id);

    @GET("getTime.php")
    Observable<GetTimeModel> getTime(@Query("date") String date);


    @POST("updateTimeStatus.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SuccessModel> updateTimeStatus(@Body TimeStatusRequestModel requestModel);

    @GET("getListFavoriteUse.php")
    Observable<LocationListModel> getListFavoriteUse(@Query("id") String id);

    @GET("getComment.php")
    Observable<CommentListModel> getListComment(@Query("location_id") String location_id);

    @POST("addComment.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<AddCommentModel> addComent(@Body CommentRequestModel requestModel);

    @GET("getImgLocation.php")
    Observable<ImgListModel> getListImgLocation(@Query("id") String id);

    @POST("addImage.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SuccessModel> addImg(@Body ImgRequestModel requestModel);

    @POST("addLocation.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<LocationListModel> addLocation(@Body LocationRequestModel requestModel);

    @POST("addFavoriteUse.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SuccessModel> addFavoriteUse(@Body FavoriteUseRequestModel requestModel);

    @GET("getFavoritebyLocation.php")
    Observable<FavoriteByLocationResponeModel> getFavoritebyLocation(@Query("user_id") String user_id,
                                                                     @Query("location_id") String location_id);

    @GET("getProfile.php")
    Observable<ProfileResponeModel> getProfile(@Query("id") String id);

    @POST("updateProfile.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SuccessModel> updateProfile(@Body UpdateProfileRequestModel requestModel);


    @GET("checkEmail.php")
    Observable<EmailResponeModel> getEmail(@Query("email") String email);

    @POST("updatePassword.php")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SuccessModel> updatePassword(@Body UpdatePasswordRequestModel requestModel);


}
