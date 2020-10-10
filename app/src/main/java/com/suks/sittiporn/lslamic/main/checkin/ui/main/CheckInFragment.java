package com.suks.sittiporn.lslamic.main.checkin.ui.main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.suks.sittiporn.lslamic.BuildConfig;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.util.GalleryDispatcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import static android.app.Activity.RESULT_OK;
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
    EditText editTextNumberRoom;
    EditText editTextNumber;
    private Uri photoUri;
    String encodedImage = "";
    int btnNumber = 0;

    private static final int PICK_IMAGE = 10;
    private static final int SELECT_FILE = 2;
    private static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_CODE_CAMERA = 1001;
    public static final int REQUEST_CODE_GALLERY = 1002;

    private Uri filePath;
    Bitmap bitmap;

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

    private void initinstanceState() {

    }

    private void init(View view, Bundle savedInstanceState) {

        id = RealmUtil.getMemberId();

        editTextNameplace = (EditText) view.findViewById(R.id.editTextNameplace);
        txt_location = (TextView) view.findViewById(R.id.txt_location);
        imageViewMaps = (ImageView) view.findViewById(R.id.imageViewMaps);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);
        editTextNumberRoom = (EditText) view.findViewById(R.id.editTextNumberRoom);
        editTextNumber = (EditText) view.findViewById(R.id.editTextNumber);



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
//        selectedImage = Bitmap.createScaledBitmap(selectedImage, 120, 120, false);
//        String encodedImage = encodeImage(selectedImage);
//        encodedImage = galleryDispatcher.encodeImage(Bitmap.createScaledBitmap(selectedImage, 120, 120, false));
        encodedImage = encodeImage(selectedImage);

        //        String encodedImage = encodeImage(selectedImage););
//        path = convertMediaUriToPath(photoUri);
//         path = photoUri.getPath();

        if (this.btnNumber == 1){
            Glide.with(imageView1.getContext())
                    .load(uri)
                    .apply(new RequestOptions().fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(imageView1);
        }else if (this.btnNumber == 2){
            Glide.with(imageView2.getContext())
                    .load(uri)
                    .apply(new RequestOptions().fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(imageView2);
        }else {
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
                                // Continue only if the File was successfully created
                                if (photoFile != null) {
                                    //  photoUri = Uri.fromFile(photoFile);
                                    photoUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                                }
                            }
//                            galleryDispatcher.dispatchTakePictureIntent(CheckInFragment.this, REQUEST_CODE_CAMERA);
                        }else {
                            captureCAPTURE();
                        }
                        return true;
                    case R.id.action_gallery:
                        if (Build.VERSION.SDK_INT > 23) {

                            Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);

                            //if (selectFileIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
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

//                            galleryDispatcher.dispatchGalleryIntent(CheckInFragment.this, GalleryDispatcher.REQUEST_CODE_GALLERY);
                        }else {
                            captureImage();
                        }
                        return true;
                    case R.id.action_remove:
                        photoUri = null;
                        encodedImage = "";

                        if (btnNumber == 1){
                            Glide.with(imageView1.getContext())
                                    .load(R.drawable.camera200)
                                    .into(imageView1);

                        }else if (btnNumber == 2){
                            Glide.with(imageView2.getContext())
                                    .load(R.drawable.camera200)
                                    .into(imageView2);
                        }else {
                            Glide.with(imageView3.getContext())
                                    .load(R.drawable.camera200)
                                    .into(imageView3);
                        }


                        break;
                }
                return false;
//                    case R.id.action_camera:
//                        galleryDispatcher.dispatchTakePictureIntent(PaymentFragment.this, REQUEST_CAMERA);
//                        return true;
//                    case R.id.action_gallery:
////                        Intent selectFileIntent = new Intent(
////                                Intent.ACTION_PICK,
////                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                        selectFileIntent.setType("image/*");
////                        startActivityForResult(
////                                Intent.createChooser(selectFileIntent, "Select File"),
////                                SELECT_FILE);
////                        return true;
//                        galleryDispatcher.dispatchGalleryIntent(PaymentFragment.this, GalleryDispatcher.REQUEST_CODE_GALLERY);
//                        return true;
//                    case R.id.action_remove:
//                        photoUri = null;
//                        Glide.with(imgSlip.getContext())
//                                .load(R.drawable.camera200)
////                                .apply(new RequestOptions().fitCenter()
////                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
////                                        .skipMemoryCache(true))
//                                .into(imgSlip);
//                        break;
//                }
//                return false;
            }
        });
        popup.show();

//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
//        pictureDialog.setTitle(R.string.select_action);
//        String[] pictureDialogItems = {
//                getString(R.string.select_photo_from_gallery),
//                getString(R.string.capture_photo_from_camera)};
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                captureImage();
//
//                                break;
//                            case 1:
//                                captureCAPTURE();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
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
                        encodedImage = encodeImage(resizedBitmap);

                       if (this.btnNumber == 1){
                           imageView1.setImageBitmap(resizedBitmap);
                       }else if (this.btnNumber == 2){
                           imageView2.setImageBitmap(resizedBitmap);
                       }else {
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
                        encodedImage = encodeImage(resizedBitmap);
//                        cardView.setVisibility(View.VISIBLE);
                        if (this.btnNumber == 1){
                            imageView1.setImageBitmap(resizedBitmap);
                        }else if (this.btnNumber == 2){
                            imageView2.setImageBitmap(resizedBitmap);
                        }else {
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


}