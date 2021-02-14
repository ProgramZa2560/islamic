package com.suks.sittiporn.lslamic.profile.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.suks.sittiporn.lslamic.BuildConfig;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.home.HomeActivity;
import com.suks.sittiporn.lslamic.login.LoginActivity;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.FavoriteByLocationResponeModel;
import com.suks.sittiporn.lslamic.model.reponse.ProfileResponeModel;
import com.suks.sittiporn.lslamic.model.reponse.SuccessModel;
import com.suks.sittiporn.lslamic.model.request.ImgRequestModel;
import com.suks.sittiporn.lslamic.model.request.UpdateProfileRequestModel;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.util.DateTimeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    ImageView bt_logout, ic_upload;
    FloatingActionButton ic_edit;
    CircleImageView ic_person;
    RadioButton radio1, radio2;
    EditText userf, userl, mail, age, status;
    LinearLayout llBtn;
    Button btnCancel, btnSave;

    String url;

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
    String encodedImage = "";
    String sex;
    String date;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initinstanceState(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio1:
                if (checked)
                    sex = "0";
                // Pirates are the best
                break;
            case R.id.radio2:
                if (checked)
                    sex = "1";
                // Ninjas rule
                break;
        }
    }

    private void initinstanceState(View view, Bundle savedInstanceState) {
//        onRadioButtonClicked(view);

//        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//
//        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
//        switch1 = (Switch) view.findViewById(R.id.switch1);
        ic_person = (CircleImageView) view.findViewById(R.id.ic_person);
        bt_logout = (ImageView) view.findViewById(R.id.bt_logout);
        ic_edit = (FloatingActionButton) view.findViewById(R.id.ic_edit);
        ic_upload = (ImageView) view.findViewById(R.id.ic_upload);
        llBtn = (LinearLayout) view.findViewById(R.id.llBtn);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        userf = (EditText) view.findViewById(R.id.userf);
        userl = (EditText) view.findViewById(R.id.userl);
        mail = (EditText) view.findViewById(R.id.mail);
        age = (EditText) view.findViewById(R.id.age);
        status = (EditText) view.findViewById(R.id.status);

        bt_logout = (ImageView) view.findViewById(R.id.bt_logout);
        bt_logout = (ImageView) view.findViewById(R.id.bt_logout);
        bt_logout = (ImageView) view.findViewById(R.id.bt_logout);
        radio1 = (RadioButton) view.findViewById(R.id.radio1);
        radio2 = (RadioButton) view.findViewById(R.id.radio2);
//        radio2.isChecked();

//        switch4 = (Switch) view.findViewById(R.id.switch4);
//        switch5 = (Switch) view.findViewById(R.id.switch5);
//        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);
//        llviewlooad.setVisibility(View.GONE);
//        initi();
//
//
//        id = RealmUtil.getMemberId();

        initi();


    }

    private void Enabled(boolean enabled) {
        userf.setEnabled(enabled);
        userl.setEnabled(enabled);
        age.setEnabled(enabled);


    }

    private void initi() {

        status.setEnabled(false);
        mail.setEnabled(false);
        Enabled(false);

        llBtn.setVisibility(View.GONE);
        ic_upload.setVisibility(View.GONE);
        ic_edit.setVisibility(View.VISIBLE);


        getProfile();
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                adbConfirmExit.create();
                adbConfirmExit.setTitle("ออกจากระบบ");
                adbConfirmExit.setMessage("คุณต้องการออกจากระบบใช่หรือไม่!");
                adbConfirmExit.setNegativeButton("ยกเลิก", null);
                adbConfirmExit.setPositiveButton("ออกจากระบบ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        int count = RealmUtil.deleteRealm(getContext());
                        if (count == 0) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                });
                adbConfirmExit.create().show();
            }
        });

        ic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Enabled(true);
                llBtn.setVisibility(View.VISIBLE);
                ic_upload.setVisibility(View.VISIBLE);
                ic_edit.setVisibility(View.GONE);


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enabled(false);
                llBtn.setVisibility(View.GONE);
                ic_upload.setVisibility(View.GONE);
                ic_edit.setVisibility(View.VISIBLE);

                Glide.with(ic_person.getContext())
                        .load(url)
                        .apply(new RequestOptions().fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .error(R.drawable.group__22)
                                .placeholder(R.drawable.group__22)
                                .skipMemoryCache(true))
                        .into(ic_person);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                adbConfirmExit.create();
                adbConfirmExit.setTitle("บันทึก");
                adbConfirmExit.setMessage("คุณต้องการบันทึกใช่หรือไม่?");
                adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        updateProfile();

                    }
                });
                adbConfirmExit.setNegativeButton("ยกเลิก", null);
                adbConfirmExit.create().show();


            }
        });

        ic_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();

            }
        });


        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "0";
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "1";
            }
        });

//        llviewlooad.setVisibility(View.GONE);
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        currentDate = date.format(c.getTime());
//        getTime(currentDate);

    }

    private void updateProfile() {

        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                n = String.valueOf(rand.nextInt(9) + 0);
            } else {
                n = n + String.valueOf(rand.nextInt(9) + 0);
            }
        }
        date = DateTimeUtils.getDate("yyyy-MM-dd");
        String id = RealmUtil.getMemberId();
        UpdateProfileRequestModel requestModel = new UpdateProfileRequestModel();
        requestModel.setId(id);
        requestModel.setUrl((encodedImage.trim().equals("")) ? "1" : encodedImage.trim());
        requestModel.setFristname(userf.getText().toString());
        requestModel.setLastname(userl.getText().toString());
        requestModel.setAge(age.getText().toString());
        requestModel.setGender(sex);
        requestModel.setNameUrl(id + "__" + date + "_" + n + ".png");


        ApiService apiService = Retrofit2.getApiService();
        Observable<SuccessModel> observable = apiService.updateProfile(requestModel);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SuccessModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SuccessModel response) {
//                        if (response.getResult().equals("true")) {

                        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                        adbConfirmExit.create();
                        adbConfirmExit.setTitle("สำเร็จ");
                        adbConfirmExit.setMessage(response.getMesage());
                        adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });
                        adbConfirmExit.create().show();
                        Enabled(false);
                        llBtn.setVisibility(View.GONE);
                        ic_upload.setVisibility(View.GONE);
                        ic_edit.setVisibility(View.VISIBLE);
//                        } else {
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

//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    private void captureCAPTURE() {

        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt, REQUEST_CAMERA);
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    private void captureImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), PICK_IMAGE);

    }


    private void showFileChooser() {

        PopupMenu popup = new PopupMenu(getContext(), ic_upload);
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
                        } else {
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
                        } else {
                            captureImage();
                        }
                        return true;
                    case R.id.action_remove:
                        photoUri = null;
                        encodedImage = "";
                        Glide.with(ic_person.getContext())
                                .load(R.drawable.camera200)
                                .into(ic_person);


                        break;
                }
                return false;
            }
        });
        popup.show();
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

        encodedImage = encodeImage(selectedImage);
        Glide.with(ic_person.getContext())
                .load(uri)
                .apply(new RequestOptions().fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.group__22)
                        .placeholder(R.drawable.group__22)
                        .skipMemoryCache(true))
                .into(ic_person);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT > 23) {
                if (requestCode == REQUEST_CODE_CAMERA) {
                    Uri captureImageUri = getPhotoUri();
                    setProfileImage(captureImageUri);
                }
                if (requestCode == REQUEST_CODE_GALLERY) {
                    Uri selectedImageUri = data.getData();
                    setProfileImage(selectedImageUri);
                }

            } else {
                if (requestCode == REQUEST_CAMERA) {
                    filePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                                bitmap, 200, 150, false);
                        encodedImage = encodeImage(resizedBitmap);
                        ic_person.setImageBitmap(resizedBitmap);

                        photoUri = filePath;

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

                        encodedImage = encodeImage(resizedBitmap);
                        ic_person.setImageBitmap(resizedBitmap);

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

    private void getProfile() {

        ApiService apiService = Retrofit2.getApiService();

        Observable<ProfileResponeModel> observable = apiService.getProfile(RealmUtil.getMemberId());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ProfileResponeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProfileResponeModel dataModel) {

                        if (dataModel.getSuccess().equals("true")) {

                            userf.setText(dataModel.getData().get(0).getFristname());
                            userl.setText(dataModel.getData().get(0).getLastname());
                            mail.setText(dataModel.getData().get(0).getEmail());
                            age.setText(dataModel.getData().get(0).getAge());

//                            if (dataModel.getData().get(0).getUser_image().equals("")){
//                                url = "";
//                                ic_person.setImageDrawable(R.drawable.);
//                                Glide.with(ic_person.getContext())
//                                        .load(url)
//                                        .apply(new RequestOptions().fitCenter()
//                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                                .skipMemoryCache(true))
//                                        .into(ic_person);
//
//                            }else {
                            url = Retrofit2.BASE_URL + dataModel.getData().get(0).getUser_image();
                            Glide.with(ic_person.getContext())
                                    .load(url)
                                    .apply(new RequestOptions().fitCenter()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .error(R.drawable.group__22)
                                            .placeholder(R.drawable.group__22)
                                            .skipMemoryCache(true))
                                    .into(ic_person);

//                            }


                            if (dataModel.getData().get(0).getGender().equals("0")) {
                                radio1.setChecked(true);
                                sex = "0";
                            } else {
                                radio2.setChecked(true);
                                sex = "1";
                            }

                            if (dataModel.getData().get(0).getStatus().equals("0")) {
                                status.setText("ผู้ดูแลระบบ");
                            } else {
                                status.setText("ผู้ใช้");
                            }


                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}