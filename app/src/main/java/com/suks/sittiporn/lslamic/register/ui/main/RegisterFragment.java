package com.suks.sittiporn.lslamic.register.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.manager.Retrofit2;
import com.suks.sittiporn.lslamic.manager.http.ApiService;
import com.suks.sittiporn.lslamic.model.reponse.SuccessModel;
import com.suks.sittiporn.lslamic.model.reponse.SuccessRegisterModel;
import com.suks.sittiporn.lslamic.model.request.MemberRequestModel;
import com.suks.sittiporn.lslamic.model.request.RegisterRequestModel;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterFragment extends Fragment {

    Button btn_register;
    EditText user_editText, password_editText, re_password_editText;
    EditText firstNameEditText, lastNameEditText;
    boolean check = false;

    RegisterRequestModel model;
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.init(view, savedInstanceState);
    }

    private void init(View view, Bundle savedInstanceState) {

        user_editText = (EditText) view.findViewById(R.id.user_editText);
        password_editText = (EditText) view.findViewById(R.id.password_editText);
        re_password_editText = (EditText) view.findViewById(R.id.re_password_editText);
        firstNameEditText = (EditText) view.findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) view.findViewById(R.id.lastNameEditText);
        btn_register = (Button) view.findViewById(R.id.btn_register);

        initinstanceState();

    }

    private void initinstanceState() {

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = user_editText.getText().toString().toLowerCase();
//                Pattern pattern = Pattern.compile("([a-z]+)  || ([a-z]+[0-9]+)");
//                Matcher matcher = pattern.matcher(text);
//                boolean b = matcher.matches();
//                if (b) {
                    if ( user_editText.getText().toString().equals("") ||
                            password_editText.getText().toString().equals("") ||
                            re_password_editText.getText().toString().equals("") ||
                            password_editText.getText().equals("")) {
                        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                        adbConfirmExit.create();
                        adbConfirmExit.setTitle("ผิดพลาด");
                        adbConfirmExit.setMessage("โปรดตรวจสอบความถูกต้อง");
                        adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                password_editText.setText("");
                                re_password_editText.setText("");

                            }
                        });
                        adbConfirmExit.create().show();
                    } else {
                        if (password_editText.getText().toString().length() >= 4) {
                            if (password_editText.getText().toString().equals(re_password_editText.getText().toString())) {
                                 model = new RegisterRequestModel();

                                model.setEmail(user_editText.getText().toString());
                                model.setPass(password_editText.getText().toString());
                                model.setFristname(firstNameEditText.getText().toString());
                                model.setLastname(lastNameEditText.getText().toString());

                                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                                adbConfirmExit.create();
                                adbConfirmExit.setTitle("บันทึก");
                                adbConfirmExit.setMessage("คุณต้องการสมัครสมาชิกใช่หรือไม่?");
                                adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        register(model);

                                    }
                                });
                                adbConfirmExit.setNegativeButton("ยกเลิก",null);
                                adbConfirmExit.create().show();


                            } else {
                                final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                                adbConfirmExit.create();
                                adbConfirmExit.setTitle("รหัสผ่านไม่ตรงกัน");
                                adbConfirmExit.setMessage("โปรดตรวจสอบความถูกต้อง");
                                adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        password_editText.setText("");
                                        re_password_editText.setText("");

                                    }
                                });
                                adbConfirmExit.create().show();
                            }
                        } else {

                            final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                            adbConfirmExit.create();
                            adbConfirmExit.setTitle("ผิดพลาด");
                            adbConfirmExit.setMessage("กรุณาตั้งรหัสผ่านอย่างน้อย 4 ตัว ไม่เกิน 15 ตัว");
                            adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    password_editText.setText("");
                                    re_password_editText.setText("");

                                }
                            });
                            adbConfirmExit.create().show();

                        }
                    }
//                } else {
//                    final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
//                    adbConfirmExit.create();
//                    adbConfirmExit.setTitle("รูปแบบไม่ถูกต้อง");
//                    adbConfirmExit.setMessage("โปรดกรอกตัวอักษร a-z และมีตัวเลขอย่างน้อย 1 ตัว");
//                    adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
////                            user_editText.setText("");
//                        }
//                    });
//                    adbConfirmExit.create().show();
//                }
            }
        });


    }

    void register(final RegisterRequestModel model) {
        ApiService apiService = Retrofit2.getApiService();
        Observable<SuccessRegisterModel> observable = apiService.register(model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SuccessRegisterModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SuccessRegisterModel response) {
                        if (response.getSuccess().equals("true")) {
//                            login(model);
                            final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                            adbConfirmExit.create();
                            adbConfirmExit.setTitle("สมัครสมาชิก");
                            adbConfirmExit.setMessage("สำเร็จ");
                            adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    getActivity().finish();

                                }
                            });
                            adbConfirmExit.create().show();
                        } else {
                            final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                            adbConfirmExit.create();
                            adbConfirmExit.setTitle("เกิดความผิดพลาด");
                            adbConfirmExit.setMessage("User นี้มีคนใช้แล้ว");
                            adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
//                                    Intent intent = new Intent(getContext(), MainActivity.class);
//                                    intent.addCategory(Intent.CATEGORY_HOME);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);

                                }
                            });
                            adbConfirmExit.create().show();

                        }
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

//    void login(RegisterRequestModel model) {
//        MemberRequestModel modelLogin = new MemberRequestModel();
//        modelLogin.setUser_user(model.getUser_user());
//        modelLogin.setUser_password(model.getUser_password());
//
//        ApiService apiService = Retrofit2.getApiService();
//        Observable<MemberModel> observable = apiService.login(modelLogin);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<MemberModel>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(MemberModel response) {
//                        int count = RealmUtil.addMemberRealm(response, getContext());
////                        if (count > 0) {
//                        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
//                        adbConfirmExit.create();
//                        adbConfirmExit.setTitle("");
//                        adbConfirmExit.setMessage("Success");
//                        adbConfirmExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//
//                                Intent intent = new Intent(getContext(), HomeActivity.class);
//                                intent.addCategory(Intent.CATEGORY_HOME);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//
//                            }
//                        });
//                        adbConfirmExit.create().show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getContext(), "User หรือ Passsword ไม่ถูกต้อง ", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
