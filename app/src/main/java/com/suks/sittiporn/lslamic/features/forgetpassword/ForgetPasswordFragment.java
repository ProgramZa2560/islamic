package com.suks.sittiporn.lslamic.features.forgetpassword;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.suks.sittiporn.lslamic.R;
import com.suks.sittiporn.lslamic.features.forgetpassword.forgetemail.ForgetEmailFragment;
import com.suks.sittiporn.lslamic.features.login.LoginActivity;
import com.suks.sittiporn.lslamic.core.network.RetrofitClient;
import com.suks.sittiporn.lslamic.core.network.ApiService;
import com.suks.sittiporn.lslamic.data.remote.response.EmailResponeModel;
import com.suks.sittiporn.lslamic.data.remote.response.SuccessModel;
import com.suks.sittiporn.lslamic.data.remote.request.UpdatePasswordRequestModel;
import com.suks.sittiporn.lslamic.core.util.Util;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ForgetPasswordFragment extends Fragment {

    EditText password_editText;
    EditText forget_password_editText;
    Button btn_save;
    LinearLayout llviewlooad;
    String ms;
    String id;
    String email;


    public static ForgetPasswordFragment newInstance() {
        return new ForgetPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forgetpassword_fragment, container, false);
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

    private void initinstanceState(View view, Bundle savedInstanceState) {
        bindView(view, savedInstanceState);
        initi();

    }

    private void bindView(View view, Bundle savedInstanceState) {
        password_editText = (EditText) view.findViewById(R.id.password_editText);
        forget_password_editText = (EditText) view.findViewById(R.id.forget_password_editText);
        btn_save = (Button) view.findViewById(R.id.btn_save);
        llviewlooad = (LinearLayout) view.findViewById(R.id.llviewlooad);

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

    }

    private void initi() {

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password_editText.getText().toString();
                String passRe = forget_password_editText.getText().toString();
                if (pass.equals(passRe)){
                    if (password_editText.getText().toString().length() >= 4) {
                        savePass(Util.ID,pass);
                    }else {
                        dialogShow();
                    }

                }else if (pass.equals("") || passRe.equals("")){
                    final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                    adbConfirmExit.create();
                    adbConfirmExit.setCancelable(true);
                    adbConfirmExit.setTitle("แจ้งเตือน");
                    adbConfirmExit.setMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
                    adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
//                            switch1.setChecked(false);
                        }
                    });
                    adbConfirmExit.create().show();
                }
                else {
                    dialogShow();
                }



            }
        });

    }
    private void dialogShow(){
        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
        adbConfirmExit.create();
        adbConfirmExit.setCancelable(true);
        adbConfirmExit.setTitle("แจ้งเตือน");
        adbConfirmExit.setMessage("รหัสผ่านไม่ตรงกัน ตรวจสอบความถูกต้อง");
        adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
//                            switch1.setChecked(false);
            }
        });
        adbConfirmExit.create().show();
    }


    private void savePass(final String id, final String password) {
        llviewlooad.setVisibility(View.VISIBLE);
        UpdatePasswordRequestModel model = new UpdatePasswordRequestModel();
        model.setId(id);
        model.setPassword(password);
        ApiService apiService = RetrofitClient.getApiService();
        Observable<SuccessModel> observable = apiService.updatePassword(model);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<SuccessModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SuccessModel dataModel) {
//                        setString(dataModel);
                        llviewlooad.setVisibility(View.GONE);

                            final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
                            adbConfirmExit.create();
                            adbConfirmExit.setCancelable(true);
                            adbConfirmExit.setTitle("แจ้งเตือน");
                            adbConfirmExit.setMessage(dataModel.getMesage());
                            adbConfirmExit.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (dataModel.getResult().equals("true")){
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
                            });
                            adbConfirmExit.create().show();



                    }

                    @Override
                    public void onError(Throwable e) {
                        llviewlooad.setVisibility(View.GONE);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

//    private void setString(EmailResponeModel dataModel) {
//        Util.ID = dataModel.getData().get(0).getId();
//        Util.EMAIL = dataModel.getData().get(0).getEmail();
//
//        Intent intent = new Intent(getContext(), ForgetPasswordActivity.class);
//        startActivity(intent);
//
//    }
}