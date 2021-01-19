package com.suks.sittiporn.lslamic.login.ui.main;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.suks.sittiporn.lslamic.BuildConfig;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.forgetpassword.forgetemail.ForgetEmailActivity;
import com.suks.sittiporn.lslamic.home.HomeActivity;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.MemberModel;
import com.suks.sittiporn.lslamic.model.request.MemberRequestModel;
import com.suks.sittiporn.lslamic.realm.DiskRealmDao;
import com.suks.sittiporn.lslamic.realm.RealmUtil;
import com.suks.sittiporn.lslamic.register.RegisterActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends Fragment {

    private EditText user;
    private EditText password;
    private Button login;
    private Button register;
    //    private TextView register, forgetpassword;
    static final int REQUEST_LOCATION = 1;
    private int count = 0;

    String email;
    String pass;
    TextView forgetpassword;

//
//    @BindView(R.id.btn_login)
//    Button login;
//    Unbinder unbinder;

    public LoginFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
//        unbinder = ButterKnife.bind(this, rootView);
        initInstances(rootView, savedInstanceState);
        return rootView;
//        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.init(view, savedInstanceState);
    }

    private void init(View view, Bundle savedInstanceState) {
        user = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.btn_login);
        register = (Button) view.findViewById(R.id.btn_register);
        forgetpassword = (TextView) view.findViewById(R.id.forgotpass);
        initinstanceState();
    }

    private void initinstanceState() {
        user.setSingleLine();
//        email = user.getText().toString().toLowerCase();
//        pass = password.getText().toString();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user.getText().toString().toLowerCase();
                pass = password.getText().toString();

                if (!pass.equals("") && !email.equals("")) {

                    if (Build.VERSION.SDK_INT > 22) {

                        if (((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//                                PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) !=
                                PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) !=
                                PackageManager.PERMISSION_GRANTED))) {

                            if (count == 0) {
                                count = 1;
                                ActivityCompat.requestPermissions(getActivity(), new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_LOCATION);

                            } else {
                                showMessageOKCancel("You need to allow access to permission",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                                                startActivity(i);


                                            }
                                        });
                            }
                        } else {
                            login(email, pass);
                        }

                    } else {
                        login(email, pass);
                    }
                } else {
                    Toast.makeText(getContext(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {

                    if (((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//                            PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) !=
                            PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) !=
                            PackageManager.PERMISSION_GRANTED))) {

                        if (count == 0) {
                            count = 1;
                            ActivityCompat.requestPermissions(getActivity(), new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_LOCATION);

                        } else {
                            showMessageOKCancel("You need to allow access to permission",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                                            startActivity(i);


                                        }
                                    });
                        }
                    } else {
                        Intent intent = new Intent(getContext(), RegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(getContext(), RegisterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ForgetEmailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("Setting", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    void login(String email, String pass) {


//        Intent intent = new Intent(getContext(), HomeActivity.class);
//        intent.putExtra("vv", "vv");
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);


        MemberRequestModel model = new MemberRequestModel();
        model.setUser(email);
        model.setPass(pass);
//        Toast.makeText(getContext(), email+"SSSSSSS"+pass, Toast.LENGTH_SHORT).show();
        ApiService apiService = Retrofit2.getApiService();
        Observable<MemberModel> observable = apiService.login(model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<MemberModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(MemberModel response) {
                        DiskRealmDao diskRealmDao = new DiskRealmDao(getContext());
                        diskRealmDao.saveMember(response);
//                        if (count > 0) {
                        Intent intent = new Intent(getContext(), HomeActivity.class);
//                        intent.putExtra("vv", "vv");
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
//                        }
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
}
