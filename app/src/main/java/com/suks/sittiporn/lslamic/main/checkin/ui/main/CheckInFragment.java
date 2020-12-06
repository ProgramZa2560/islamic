package com.suks.sittiporn.lslamic.main.checkin.ui.main;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.suks.sittiporn.lslamic.BuildConfig;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.home.HomeActivity;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.LocationListModel;
import com.suks.sittiporn.lslamic.model.reponse.SuccessModel;
import com.suks.sittiporn.lslamic.model.request.ImgRequestModel;
import com.suks.sittiporn.lslamic.model.request.LocationRequestModel;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.util.DateTimeUtils;
import com.suks.sittiporn.lslamic.util.GalleryDispatcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static com.suks.sittiporn.lslamic.util.GalleryDispatcher.REQUEST_CODE_CAMERA;
import static com.suks.sittiporn.lslamic.util.GalleryDispatcher.REQUEST_CODE_GALLERY;

public class CheckInFragment extends Fragment {

    private CheckInViewModel mViewModel;
    String id;
    EditText editTextNameplace;
    TextView txt_location;
    ImageView imageViewMaps;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    Button bt_save;
    Button bt_can;
    EditText editTextNumberRoom;
    EditText editTextNumber;
    EditText editTextDesc;
    EditText editTextTimeOpen;
    EditText editTextTimeClose;

    String encodedImage1 = "";
    String encodedImage2 = "";
    String encodedImage3 = "";
    int btnNumber = 0;

    String time;
    String date;

    LinearLayout llviewlooad;

    private static final int PICK_IMAGE = 10;
    private static final int SELECT_FILE = 2;
    private static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_CODE_CAMERA = 1001;
    public static final int REQUEST_CODE_GALLERY = 1002;
    private Uri photoUri;
    private Uri filePath;

    Bitmap bitmap;
    private static final int REQUEST_LOCATION = 1;
    String n;
    String x;


    Button btnGetLocation;
    TextView showLocation;
    private LocationManager locationManager;
    String latitude, longitude;

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static CheckInFragment newInstance() {
        return new CheckInFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.check_in_fragment, container, false);
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

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }

    private void initinstanceState() {

    }

    private void getLocation() {
        locationManager = (LocationManager) getContext()
                .getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
//                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(getContext(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }

        txt_location.setText(latitude +", "+ longitude);
    }

    private void init(View view, Bundle savedInstanceState) {

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);


        id = RealmUtil.getMemberId();
        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
        editTextNameplace = (EditText) view.findViewById(R.id.editTextNameplace);
        editTextDesc = (EditText) view.findViewById(R.id.editTextDesc);
        txt_location = (TextView) view.findViewById(R.id.txt_location);
        imageViewMaps = (ImageView) view.findViewById(R.id.imageViewMaps);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);
        bt_save = (Button) view.findViewById(R.id.bt_save);
        bt_can = (Button) view.findViewById(R.id.bt_can);
        editTextNumberRoom = (EditText) view.findViewById(R.id.editTextNumberRoom);
        editTextNumber = (EditText) view.findViewById(R.id.editTextNumber);
        editTextTimeOpen = (EditText) view.findViewById(R.id.editTextTimeOpen);
        editTextTimeClose = (EditText) view.findViewById(R.id.editTextTimeClose);

        llviewlooad.setVisibility(View.GONE);

        getLocation();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (editTextNameplace.getText().toString().equals("")){

                   final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                   adbConfirmExit.create();
                   adbConfirmExit.setTitle("ผิดพลาด");
                   adbConfirmExit.setMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
                   adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface arg0, int arg1) {

                       }
                   });
                   adbConfirmExit.create().show();

               }else {
                   final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                   adbConfirmExit.create();
                   adbConfirmExit.setTitle("เพิ่มสถานที่");
                   adbConfirmExit.setMessage("คุณต้องการเพิ่มเพิ่มสถานที่ใช่หรือไม่?");
                   adbConfirmExit.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface arg0, int arg1) {

                           llviewlooad.setVisibility(View.VISIBLE);
                           addLocation(editTextNumberRoom.getText().toString(),
                                   editTextNumber.getText().toString(),
                                   editTextNameplace.getText().toString(),
                                   editTextDesc.getText().toString(),
                                   editTextTimeOpen.getText().toString(),
                                   editTextTimeClose.getText().toString());

                       }
                   });
                   adbConfirmExit.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface arg0, int arg1) {

                       }
                   });

                   adbConfirmExit.create().show();

               }





            }
        });

        bt_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                adbConfirmExit.create();
                adbConfirmExit.setTitle("กลับเมนูหลัก");
                adbConfirmExit.setMessage("");
                adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        getActivity().onBackPressed();

                    }
                });
                adbConfirmExit.create().show();



            }
        });

        imageViewMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser(1);

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(2);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(3);
            }
        });

    }

    public String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage.trim();
    }

    private void setProfileImage(Uri uri) {
        photoUri = uri;
        InputStream imageStream = null;
        try {
            imageStream = getActivity().getContentResolver().openInputStream(photoUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        if (this.btnNumber == 1){
            encodedImage1 = encodeImage(selectedImage);
            Glide.with(imageView1.getContext())
                    .load(uri)
                    .apply(new RequestOptions().fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(imageView1);
        }else if (this.btnNumber == 2){
            encodedImage2 = encodeImage(selectedImage);
            Glide.with(imageView2.getContext())
                    .load(uri)
                    .apply(new RequestOptions().fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(imageView2);
        }else {
            encodedImage3 = encodeImage(selectedImage);
            Glide.with(imageView3.getContext())
                    .load(uri)
                    .apply(new RequestOptions().fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(imageView3);
        }


    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    private void showFileChooser(final int btnNumber) {
        this.btnNumber = btnNumber;
        PopupMenu popup;
        if (this.btnNumber == 1){
             popup = new PopupMenu(getContext(), imageView1);
        }else if (this.btnNumber == 2){
             popup = new PopupMenu(getContext(), imageView2);
        }else {
             popup = new PopupMenu(getContext(), imageView3);
        }

        popup.getMenuInflater().inflate(R.menu.popup_menu_choose_image, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        if (Build.VERSION.SDK_INT > 23) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                File photoFile = null;
                                try {
                                    photoFile = createImageFile();


                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                if (photoFile != null) {
                                    //  photoUri = Uri.fromFile(photoFile);
                                    photoUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                                }
                            }
                        }else {
                            captureCAPTURE();
                        }
                        return true;
                    case R.id.action_gallery:
                        if (Build.VERSION.SDK_INT > 23) {

                            Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            try {
                                selectFileIntent.setType("image/*");
                                selectFileIntent.putExtra("CONTENT_TYPE", "image/*");
                                selectFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(
                                        Intent.createChooser(selectFileIntent, "Select File"),
                                        REQUEST_CODE_GALLERY);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }else {
                            captureImage();
                        }
                        return true;
                    case R.id.action_remove:
                        photoUri = null;


                        if (btnNumber == 1){
                            encodedImage1 = "";
                            Glide.with(imageView1.getContext())
                                    .load(R.drawable.camera200)
                                    .into(imageView1);

                        }else if (btnNumber == 2){
                            encodedImage2 = "";
                            Glide.with(imageView2.getContext())
                                    .load(R.drawable.camera200)
                                    .into(imageView2);
                        }else {
                            encodedImage3 = "";
                            Glide.with(imageView3.getContext())
                                    .load(R.drawable.camera200)
                                    .into(imageView3);
                        }


                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void captureImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), PICK_IMAGE);

    }

    private void captureCAPTURE() {

        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt,REQUEST_CAMERA);
    }
    public Uri getPhotoUri() {
        return photoUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RegisterActivity.RESULT_OK) {
//            if (requestCode == REQUEST_CAMERA) {
//                Uri captureImageUri = galleryDispatcher.getPhotoUri();
//                setProfileImage(captureImageUri);
//            }
//            if (requestCode == SELECT_FILE) {
//                Uri selectedImageUri = data.getData();
//                setProfileImage(selectedImageUri);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT > 23){
                if (requestCode == REQUEST_CODE_CAMERA) {
                    Uri captureImageUri = getPhotoUri();
                    setProfileImage(captureImageUri);
                }
                if (requestCode == REQUEST_CODE_GALLERY) {
                    Uri selectedImageUri = data.getData();
                    setProfileImage(selectedImageUri);
                }

            }else {
                if (requestCode == REQUEST_CAMERA) {
                    filePath = data.getData();
//                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
//                    getContext().getContentResolver().notifyChange(uri, null);
//                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                                bitmap, 200, 150, false);


                       if (this.btnNumber == 1){
                           encodedImage1 = encodeImage(resizedBitmap);
                           imageView1.setImageBitmap(resizedBitmap);
                       }else if (this.btnNumber == 2){
                           encodedImage2 = encodeImage(resizedBitmap);
                           imageView2.setImageBitmap(resizedBitmap);
                       }else {
                           encodedImage3 = encodeImage(resizedBitmap);
                           imageView3.setImageBitmap(resizedBitmap);
                       }

                        photoUri = filePath;
//                        new ImageSaver(getContext())
//                                .setFileName("test.png")
//                                .setDirectoryName("folder").save(resizedBitmap);
//                        path = getString(R.string.select_image_camera);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (requestCode == PICK_IMAGE) {
                    try {
                        filePath = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                                bitmap, 200, 150, false);

//                        cardView.setVisibility(View.VISIBLE);
                        if (this.btnNumber == 1){
                            encodedImage1 = encodeImage(resizedBitmap);
                            imageView1.setImageBitmap(resizedBitmap);
                        }else if (this.btnNumber == 2){
                            encodedImage2 = encodeImage(resizedBitmap);
                            imageView2.setImageBitmap(resizedBitmap);
                        }else {
                            encodedImage3 = encodeImage(resizedBitmap);
                            imageView3.setImageBitmap(resizedBitmap);

                        }

                        photoUri = filePath;
//                        path = getPath(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addLocation(final String editTextNumberRoom, final String editTextNumber,
                             final String name, final String address, String editTextTimeOpen, String editTextTimeClose) {

        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                n = String.valueOf(rand.nextInt(9) + 0);
            } else {
                n = n + String.valueOf(rand.nextInt(9) + 0);
            }
        }

        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                x = String.valueOf(rand.nextInt(9) + 0);
            } else {
                x = x + String.valueOf(rand.nextInt(9) + 0);
            }
        }


        time = DateTimeUtils.getDate("HH:mm:ss");
        date = DateTimeUtils.getDate("yyyy-MM-dd");
        LocationRequestModel requestModel = new LocationRequestModel();
        requestModel.setLatitude(latitude);
        requestModel.setLongitude(longitude);
        requestModel.setName_l(name);
        requestModel.setNumberFull((editTextNumber.equals("") ? "0" : editTextNumber));
        requestModel.setStatus("0");
        requestModel.setRoomNumber((editTextNumberRoom.equals("") ? "0" : editTextNumberRoom));
        requestModel.setTimeStart((editTextTimeOpen.equals("") ? "07:00" : editTextTimeOpen));
        requestModel.setTimeEnd((editTextTimeClose.equals("") ? "18:00" : editTextTimeClose));
        requestModel.setUser_id(id);
        requestModel.setDatetime(date+ " " + time);
        requestModel.setAddress(address);
        requestModel.setLocation(n+x);

        ApiService apiService = Retrofit2.getApiService();
        Observable<LocationListModel> observable = apiService.addLocation(requestModel);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<LocationListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        llviewlooad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(LocationListModel response) {

                        if (response.getSuccess().equals("true")) {


                            boolean count  = false;
                            if (encodedImage1 !=  ""){
                                count  = true;
                                addImgLocation(n+x, 1);
                            }
                            if (encodedImage2 !=  ""){
                                count  = true;
                                addImgLocation(n+x, 2);
                            }

                            if (encodedImage3 !=  ""){
                                count  = true;
                                addImgLocation(n+x, 3);
                            }
                            if (!count){
                                llviewlooad.setVisibility(View.GONE);
                                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                                adbConfirmExit.create();
                                adbConfirmExit.setTitle("กลับเมนูหลัก");
                                adbConfirmExit.setMessage(response.getMessage());
                                adbConfirmExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        Intent intent = new Intent(getContext(), HomeActivity.class);
//                                    intent.putExtra("vv", "vv");
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
//                                    getActivity().finish();

                                    }
                                });
                                adbConfirmExit.create().show();
                            }

                        } else {

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        llviewlooad.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void addImgLocation(final String locationId, int btnNumber) {
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                n = String.valueOf(rand.nextInt(9) + 0);
            } else {
                n = n + String.valueOf(rand.nextInt(9) + 0);
            }
        }

        ImgRequestModel requestModel = new ImgRequestModel();
        requestModel.setLocation_id(locationId);
        if (btnNumber == 1){
            requestModel.setUrl(encodedImage1.trim());
        }else if (btnNumber == 2){
            requestModel.setUrl(encodedImage2.trim());
        }else {
            requestModel.setUrl(encodedImage3.trim());
        }
        requestModel.setNameUrl(id + "_" + locationId + "_" + date + "_" + n + ".png");


        ApiService apiService = Retrofit2.getApiService();
        Observable<SuccessModel> observable = apiService.addImg(requestModel);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SuccessModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SuccessModel response) {
                        llviewlooad.setVisibility(View.GONE);
                        if (response.getResult().equals("true")) {

                            final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                            adbConfirmExit.create();
                            adbConfirmExit.setTitle("กลับเมนูหลัก");
                            adbConfirmExit.setMessage(response.getMesage());
                            adbConfirmExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    Intent intent = new Intent(getContext(), HomeActivity.class);
//                                    intent.putExtra("vv", "vv");
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
//                                    getActivity().finish();

                                }
                            });
                            adbConfirmExit.create().show();
                        } else {
//                            final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
//                            adbConfirmExit.create();
//                            adbConfirmExit.setTitle("เกิดความผิดพลาด");
//                            adbConfirmExit.setMessage(response.getMesage());
//                            adbConfirmExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface arg0, int arg1) {
//
//                                }
//                            });
//                            adbConfirmExit.create().show();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        llviewlooad.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}