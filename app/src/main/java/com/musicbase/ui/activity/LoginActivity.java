package com.musicbase.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.musicbase.BaseActivity;
import com.musicbase.MyApplication;
import com.musicbase.R;
import com.musicbase.entity.AddRegisterBean;
import com.musicbase.entity.AppInfo;
import com.musicbase.entity.Bean;
import com.musicbase.entity.BindListBean;
import com.musicbase.entity.LoginBean;
import com.musicbase.entity.SmsBean;
import com.musicbase.entity.UpdateBean;
import com.musicbase.implement.OnWXLogin;
import com.musicbase.preferences.Preferences;
import com.musicbase.util.ActivityUtils;
import com.musicbase.util.Constant;
import com.musicbase.util.DialogUtils;
import com.musicbase.util.SPUtility;
import com.musicbase.util.ToastUtil;
import com.music.base.wxapi.WXLoginUtils;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.musicbase.MyApplication.mWxApi;
import static com.musicbase.R.id.et_password;
import static com.musicbase.R.id.et_username;
import static com.musicbase.preferences.Preferences.CONTRACT_USER;
import static com.musicbase.preferences.Preferences.appInfo;
import static com.musicbase.preferences.Preferences.phoneMatcher;
import static com.tencent.connect.common.Constants.PACKAGE_QQ;


public class LoginActivity extends BaseActivity {

    String client_version;
    @BindView(et_username)
    EditText etUsername;
    @BindView(et_password)
    EditText etPassword;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.btn_visible)
    ImageButton btnVisible;
    @BindView(R.id.btn_forget)
    TextView btnForget;
    @BindView(R.id.user_agreement)
    TextView user_agreement;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    @BindView(R.id.btn_wechat)
    ImageButton btnWechat;
    @BindView(R.id.btn_qq)
    ImageButton btnQq;
    @BindView(R.id.btn_sina)
    ImageButton btnSina;
    private final String[] identity_type = {"qq", "weixin", "sina", "telphone"};

    //初始化腾讯服务
    private Tencent mTencent;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;

    private boolean useNewPort = true;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2://0不更新 1软更新 2强制更新
                    final UpdateBean.Data update = (UpdateBean.Data) msg.obj;
                    if (update != null) {
                        if ("2".equals(update.getIsUpdate())) {
//                            if (ActivityUtils.isWifiEnabled(LoginActivity.this)) {
                            Intent intent = new Intent(LoginActivity.this, UpdateActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("update", update);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
//                            } else {
//                                ActivityUtils.showToast(LoginActivity.this, "请连接WIFI后进行更新~");
//                            }
                        } else if ("1".equals(update.getIsUpdate())) {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("更新提醒")
                                    .setMessage(update.getDesc().replace("$", "\n"))
                                    .setPositiveButton("确认更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(LoginActivity.this, UpdateActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("update", update);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    }).setNegativeButton("取消更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    doLogin();
                                    dialog.dismiss();
                                }
                            }).setNeutralButton("不再提醒", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SPUtility.putSPString(LoginActivity.this, "noRemind", update.getUrl());
                                    doLogin();
                                    dialog.dismiss();
                                }
                            }).create().show();

//                            DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_CONFIRM_DIALOG, "更新提醒", ""/**update.getDesc().replace("$", "\n")*/, new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent(LoginActivity.this, UpdateActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("update", update);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });

                        } else if ("0".equals(update.getIsUpdate())) {
                            doLogin();
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private String imei;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();

        client_version = getClientVersion();
        appInfo = new Gson().toJson(new AppInfo(MyApplication.getInstance().getString(R.string.app_name), client_version, "Android OS"));
        checkUpdateVerson();
        mTencent = Tencent.createInstance(Preferences.QQ_APP_ID, this);
        mAuthInfo = new AuthInfo(this, Constant.APP_KEY, Constant.REDIRECT_URL, Constant.SCOPE);
        WbSdk.install(this, mAuthInfo);
        mSsoHandler = new SsoHandler(LoginActivity.this);
    }

    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAccessToken = token;
//                    if (mAccessToken.isSessionValid()) {
                    // 显示 Token
//                        updateTokenView(false);
                    // 保存 Token 到 SharedPreferences
                    AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
//                    Toast.makeText(LoginActivity.this,
//                            "成功登录 uid:" + mAccessToken.getUid(), Toast.LENGTH_SHORT).show();

                    login(identity_type[2], mAccessToken.getUid(), "");

//                    }
                }
            });
        }

        @Override
        public void cancel() {
            Toast.makeText(LoginActivity.this,
                    "取消登录", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            Toast.makeText(LoginActivity.this, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private String[] myPermissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO
    };

    private void permission() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                myPermissions);
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            doBackup();
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                myPermissions,
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                doBackup();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    /**
     * 第 4 步: 后续操作
     */
    private void doBackup() {

        if (SPUtility.getSPBoolean(this, R.string.islogin, false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        // 本文主旨是讲解如果动态申请权限, 具体备份代码不再展示, 就假装备份一下
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
//            imei = "";
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P)
                imei = tm.getDeviceId();
            else
                imei = getDeviceId();
        } catch (Exception e) {
            // TODO: handle exception
        }

        String userName = SPUtility.getSPString(this, "username");
        String userPwd = SPUtility.getSPString(this, "password");
        String identity_type = SPUtility.getSPString(this, "identity_type");
//        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(identity_type)) {
//            login(identity_type, userName, userPwd);
//        }
    }

    /**
     * 获取设备唯一标识符(Android 10)
     *
     * @return 唯一标识符
     */
    public String getDeviceId() {
        return Settings.System.getString(
                getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("加阅需要访问 “位置” 、 “外部存储器”和“读取本机识别码”权限，请到 “设置 -> 应用权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void doLogin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            permission();
        } else {
            //当前系统小于6.0，直接调用拍照
            doBackup();
        }
    }

    /**
     * x
     * 忘记密码按钮
     */

    private void btnForgot() {
        String username = etUsername.getText().toString().trim();
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        intent.putExtra("telPhone", username);
        startActivity(intent);
    }

    private void checkUpdateVerson() {
        getVerisonUpdate();

    }

    private void getVerisonUpdate() {
        DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_PROGRESS_DIALOG, null, "正在检查新版本...", null);

        RequestParams params = new RequestParams(Preferences.VERISON_UPDATE_URL);
        params.addQueryStringParameter("version", getClientVersion());
        params.addQueryStringParameter("systemType", "android");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Logger.d("getVerisonUpdate=========" + s);
                Gson gson = new Gson();
                Type type = new TypeToken<UpdateBean>() {
                }.getType();
                UpdateBean bean = gson.fromJson(s, type);
                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    if (TextUtils.isEmpty(bean.getData().getUrl()) || bean.getData().getUrl().equalsIgnoreCase(SPUtility.getSPString(LoginActivity.this, "noRemind")))
                        doLogin();
                    else
                        handler.obtainMessage(2, bean.getData()).sendToTarget();
                } else {
                    doLogin();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Logger.d("throwable=" + throwable.getMessage());
                doLogin();
            }

            @Override
            public void onCancelled(CancelledException e) {
                doLogin();
            }

            @Override
            public void onFinished() {
                DialogUtils.dismissMyDialog();

            }
        });
    }

    private String getClientVersion() {
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            return "1.0.0";
        }
    }

    public void initView() {
        if (checkBox.isChecked()) {
            String userName = SPUtility.getSPString(this, "username");
            String userPwd = SPUtility.getSPString(this, "password");
            etUsername.setText(userName);
            etPassword.setText(userPwd);
        }

    }

    /**
     * 注册
     */

    public void btnRegist() {
        if (!ActivityUtils.isNetworkAvailable(LoginActivity.this)) {
            ActivityUtils.showToast(LoginActivity.this, "对不起,不能进行离线注册");
        } else {
            Intent intent = new Intent(this, RegistActivity.class);
            intent.putExtra("type", Preferences.REGIST_CODE);
            startActivityForResult(intent, Preferences.REGIST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Preferences.REGIST_CODE && data != null) {
            login(data.getStringExtra("identity_type"), data.getStringExtra("username"), data.getStringExtra("userPwd"));
        }

        //腾讯QQ回调
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, listener);
            }


        }


        //sina login
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }


    //吊起新浪微博客户端授权，如果未安装这使用web授权
    private void loginToSina() {
        //授权方式有三种，第一种对客户端授权 第二种对Web短授权，第三种结合前两中方式
        mSsoHandler.authorize(new SelfWbAuthListener());

    }

    /**
     * 登录按钮
     */
    private void btnLogin() {
        String username = etUsername.getText().toString().trim();
        if (!TextUtils.isEmpty(username) && username.matches("^[a-zA-Z0-9][a-zA-Z0-9_]{5,16}$")) {

        } else if (username.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(LoginActivity.this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(username)) {
            ActivityUtils.showToast(LoginActivity.this, "请输入用户名");
            return;
        } else {
            ActivityUtils.showToast(LoginActivity.this, "账号请输入6-16位字母或字母+数字!");
            return;
        }
        String password = etPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(password) && password.length() >= 6 || password.length() <= 16) {
        } else if (password.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(LoginActivity.this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(password)) {
            ActivityUtils.showToast(LoginActivity.this, "请输入密码！");
            return;
        } else {
            ActivityUtils.showToast(LoginActivity.this, "您输入的密码不正确!");
            return;
        }

        login(identity_type[3], username, password);
    }

    private void unbind(final String method, String openid) {
        OkGo.<String>post(Preferences.REMOVE_BIND_URL).tag(this)
                .params("user.userId", SPUtility.getUserId(this))
                .params("identity_type", method)
                .params("identifier", openid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.d(response);
                        Gson gson = new Gson();
                        BindListBean bean = gson.fromJson(response.body(), BindListBean.class);
                        DialogUtils.dismissMyDialog();
//                        if (bean != null && bean.getCode().equals("SUCCESS")) {
//                            ToastUtil.showShort(LoginActivity.this, bean.getCodeInfo());
//
//                        } else if (bean != null && bean.getCode().equals("FAIL")) {
//                            if (!TextUtils.isEmpty(bean.getCodeInfo()) && !LoginActivity.this.isFinishing()) {// 获取数据出现异常
////
//                            }
//                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        DialogUtils.dismissMyDialog();
                        super.onError(response);
                    }
                });

    }


    /**
     * @param identity_type 登录方式（qq,weixin,sina,telphone)
     * @param username
     * @param password
     */
    private void login(final String identity_type, final String username, final String password) {
//        String name = SPUtility.getSPString(getBaseContext(), "username");
//        String pwd = SPUtility.getSPString(getBaseContext(), "password");
        if (!ActivityUtils.isNetworkAvailable(this)) {
//            if (name.equalsIgnoreCase(username) && pwd.equalsIgnoreCase(password)) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("update_flag", true);
//                startActivity(intent);
//                LoginActivity.this.finish();
//            } else if (TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd)) {
            DialogUtils.showMyDialog(this, Preferences.SHOW_CONFIRM_DIALOG, "网络设置提示", "网络连接不可用,是否进行设置?", new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                }
            });
//            } else {
//                ActivityUtils.showToast(getBaseContext(), "账号密码不符");
//            }
//            return;
        }


//        RequestParams params = new RequestParams(Preferences.LOGIN_URL);
//        params.addQueryStringParameter("identity_type", identity_type);
//        params.addQueryStringParameter("identifier", username);
//        params.addQueryStringParameter("user.userPwd", password);
//        params.addQueryStringParameter("user.phoneId", imei);

        if (!LoginActivity.this.isFinishing())
            DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_PROGRESS_DIALOG, null, "正在登录中...", null);

        OkGo.<String>post(Preferences.LOGIN_URL).tag(this)
                .params("identity_type", identity_type)
                .params("identifier", username)
                .params("user.userPwd", password)
                .params("user.phoneId", imei).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Logger.d(response);
                Gson gson = new Gson();
                Type type = new TypeToken<LoginBean>() {
                }.getType();
                LoginBean bean = gson.fromJson(response.body(), type);
                DialogUtils.dismissMyDialog();
                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    LoginBean.Data data = bean.getData();
                    if (data == null) {
                        return;
                    }
                    if (TextUtils.isEmpty(data.getTelephone())) {
//                        ActivityUtils.showToast(LoginActivity.this, "您还没有绑定手机，请先用手机登录后进行绑定！");
//                        SPUtility.putSPInteger(LoginActivity.this, "userid", data.getUserId());
//                        unbind(identity_type, username);
//                        return;
                        ActivityUtils.showToast(LoginActivity.this, "您还没有绑定手机，请先绑定！");
                        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                        intent.putExtra("type", Preferences.BIND_PHONE_CODE);
                        intent.putExtra("identity_type", identity_type);
                        intent.putExtra("identifier", username);
                        startActivityForResult(intent, Preferences.BIND_PHONE_CODE);

                    } else {
                        setSpUserVo(data, password);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }

                } else if (bean != null && bean.getCode().equals("FAIL")) {
                    if (!TextUtils.isEmpty(bean.getCodeInfo()) && !LoginActivity.this.isFinishing()) {// 获取数据出现异常
                        if (bean.getCodeInfo().contains("解绑")) {
                            DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_JIESUO_DIALOG, "", "", new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    DialogUtils.dismissMyDialog();
                                    showYanzhengDialog();
                                }

                            });
                        } else {
                            DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_ERROR_DIALOG, "登录失败", bean.getCodeInfo(), null);
                        }
                    }
                }

            }

            @Override
            public void onError(Response<String> response) {
                DialogUtils.dismissMyDialog();
                super.onError(response);
            }
        });
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//                Logger.d(s);
//                Gson gson = new Gson();
//                Type type = new TypeToken<LoginBean>() {
//                }.getType();
//                LoginBean bean = gson.fromJson(s, type);
//                DialogUtils.dismissMyDialog();
//                if (bean != null && bean.getCode().equals("SUCCESS")) {
//                    LoginBean.Data data = bean.getData();
//                    if (data == null) {
//                        return;
//                    }
//                    setSpUserVo(data, password);
//
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    LoginActivity.this.finish();
//                } else if (bean != null && bean.getCode().equals("FAIL")) {
//                    if (!TextUtils.isEmpty(bean.getCodeInfo()) && !LoginActivity.this.isFinishing()) {// 获取数据出现异常
//                        if (bean.getCodeInfo().contains("解绑")) {
//                            DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_JIESUO_DIALOG, "", "", new OnClickListener() {
//
//                                @Override
//                                public void onClick(View v) {
//                                    // TODO Auto-generated method stub
//                                    DialogUtils.dismissMyDialog();
//                                    showYanzhengDialog();
//                                }
//
//                            });
//                        } else {
//                            DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_ERROR_DIALOG, "登录失败", bean.getCodeInfo(), null);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//                DialogUtils.dismissMyDialog();
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    Button btn_send;
    String verifCode;

    private void showYanzhengDialog() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(this, R.style.my_dialog);
        dialog.setContentView(R.layout.dialog_login_jiesuorenzheng);
        final TextView textView = (TextView) dialog.findViewById(R.id.dialog_titile);
        if (!TextUtils.isEmpty(etUsername.getText().toString()))
            textView.setText(etUsername.getText().toString().trim());
        else
            textView.setText(SPUtility.getSPString(this, "username"));
        final EditText editText = (EditText) dialog.findViewById(R.id.editText1);
        btn_send = (Button) dialog.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                btn_send(textView.getText().toString().trim());
            }
        });
        Button button07 = (Button) dialog.findViewById(R.id.btn_right);
        ImageButton button = (ImageButton) dialog.findViewById(R.id.imageButton1);
        button07.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                verifCode = editText.getText().toString().trim();
                // TODO Auto-generated method stub
                if (editText.getText().toString().trim().equals(verifCode)) {
                    RequestParams params = new RequestParams(Preferences.UNBIND_URL);
                    params.addQueryStringParameter("identity_type", identity_type[3]);
                    params.addQueryStringParameter("identifier", etUsername.getText().toString().trim());
                    params.addQueryStringParameter("user.phoneId", imei);
                    params.addQueryStringParameter("captcha", verifCode);

                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Logger.d(s);
                            Gson gson = new Gson();
                            Type type = new TypeToken<Bean>() {
                            }.getType();
                            Bean bean = gson.fromJson(s, type);

                            if (bean.getCode().equals("SUCCESS")) {
                                ActivityUtils.showToast(LoginActivity.this, "已成功解绑，请登录");
                                dialog.dismiss();
                            } else {
                                ActivityUtils.showToast(LoginActivity.this, "解绑失败");
                            }
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            ActivityUtils.showToast(LoginActivity.this, "解绑失败");
                        }

                        @Override
                        public void onCancelled(CancelledException e) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } else {
                    ActivityUtils.showToast(LoginActivity.this, "验证码错误");
                }
            }
        });
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public void btn_send(String phone) {
        if (TextUtils.isEmpty(phone) && !phone.matches(phoneMatcher)) {
            return;
        }
        if (!TextUtils.isEmpty(phone) && phone.matches(phoneMatcher)) {

            RequestParams params = new RequestParams(Preferences.SEND_SMS);
            params.addQueryStringParameter("phone", phone);

            ActivityUtils.showToast(LoginActivity.this, "正在发送中...");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<SmsBean>() {
                    }.getType();
                    SmsBean bean = gson.fromJson(s, type);

                    if (bean != null && bean.getCode().equals("SUCCESS")) {
//                        verifCode = bean.getData().getCheckCode();
                    } else {
                        ActivityUtils.showToast(LoginActivity.this, "验证码获取失败");
                    }
                    time.start();
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    ActivityUtils.showToast(LoginActivity.this, "验证码获取失败,请检查网络。");
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        }

    }

    TimeCount time = new TimeCount(60000, 1000);

    @OnClick({R.id.btn_visible, R.id.user_agreement, R.id.btn_forget, R.id.btn_login, R.id.btn_regist, R.id.btn_wechat, R.id.btn_qq, R.id.btn_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_visible:
                break;
            case R.id.user_agreement:
                Intent intent3 = new Intent(this, WorkWebActivity.class);
                intent3.putExtra("filePath", CONTRACT_USER);
                intent3.putExtra("name", "用户协议");
                intent3.putExtra("fileType", "html");
                startActivity(intent3);
                break;
            case R.id.btn_forget:
                btnForgot();
                break;
            case R.id.btn_login:
                btnLogin();
                break;
            case R.id.btn_regist:
                btnRegist();
                break;
            case R.id.btn_wechat:
                if (!useNewPort) {
                    ActivityUtils.showToast(this, "此功能暂未开通");
                    return;
                }
//

                if (!mWxApi.isWXAppInstalled()) {
                    ToastUtil.showShort(this, "您还未安装微信客户端");
                    return;
                }
                DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_PROGRESS_DIALOG, null, "正在使用微信登录...", null);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "diandi_wx_login";
                //像微信发送请求
                MyApplication.mWxApi.sendReq(req);
                WXLoginUtils.setOnWXLogin(new OnWXLogin() {

                    @Override
                    public void onSuccess(String openid) {
//                        finish();
                        login(identity_type[1], openid, "");
                    }


                    @Override
                    public void onFailed() {

//                        finish();
                        ToastUtil.showShort(LoginActivity.this, "登录失败，请重试！");
                    }
                });
                break;
            case R.id.btn_qq:
                if (!useNewPort) {
                    ActivityUtils.showToast(this, "此功能暂未开通");
                    return;
                }
                //注意：此段非必要，如果手机未安装应用则会跳转网页进行授权
                if (!hasApp(LoginActivity.this, PACKAGE_QQ)) {
                    ToastUtil.showShort(LoginActivity.this, "您还未安装QQ客户端！");
                    return;
                }
                //如果session无效，就开始做登录操作
//                if (!mTencent.isSessionValid()) {
                DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_PROGRESS_DIALOG, null, "正在使用QQ登录...", null);
                loginQQ();
//                }

                break;
            case R.id.btn_sina:
                if (!useNewPort) {
                    ActivityUtils.showToast(this, "此功能暂未开通");
                    return;
                }
//                mSsoHandler = new SsoHandler(WBAuthActivity.this);
//                mSsoHandler.authorizeClientSso(new WbAuthListener());
                DialogUtils.showMyDialog(LoginActivity.this, Preferences.SHOW_PROGRESS_DIALOG, null, "正在使用微博登录...", null);
                loginToSina();
                break;
        }
    }

    /**
     * QQ登录
     */
    private IUiListener listener;

    private void loginQQ() {
        listener = new IUiListener() {
            @Override
            public void onComplete(Object object) {

                Logger.e("登录成功: " + object.toString());

                JSONObject jsonObject = (JSONObject) object;
                try {
                    //得到token、expires、openId等参数
                    String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                    String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);

                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);
//                    Log.e(TAG, "token: " + token);
//                    Log.e(TAG, "expires: " + expires);
//                    Log.e(TAG, "openId: " + openId);
//
//                    //获取个人信息
//                    getQQInfo();
                    login(identity_type[0], openId, "");
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败
                Logger.e("登录失败" + uiError.errorDetail);
                Logger.e("登录失败" + uiError.errorMessage);
                Logger.e("登录失败" + uiError.errorCode + "");

            }

            @Override
            public void onCancel() {
                //登录取消
                Logger.e("登录取消");

            }
        };
        //context上下文、第二个参数SCOPO 是一个String类型的字符串，表示一些权限
        //应用需要获得权限，由“,”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
        //第三个参数事件监听器
        mTencent.login(this, "all", listener);
        //注销登录
        //mTencent.logout(this);
    }


    /**
     * true 安装了相应包名的app
     */
    private boolean hasApp(Context context, String packName) {
        boolean is = false;
        List<PackageInfo> packages = context.getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            String packageName = packageInfo.packageName;
            if (packageName.equals(packName)) {
                is = true;
            }
        }
        return is;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btn_send.setText("重新验证");
            btn_send.setTextColor(getResources().getColor(R.color.red_e61b19));
            btn_send.setClickable(true);
            int regist_sms_button1 = getResources().getIdentifier("regist_sms_button1", "drawable", getPackageName());
            btn_send.setBackgroundResource(regist_sms_button1);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            int regist_sms_button2 = getResources().getIdentifier("regist_sms_button2", "drawable", getPackageName());
            btn_send.setBackgroundResource(regist_sms_button2);
            btn_send.setClickable(false);
            btn_send.setTextColor(getResources().getColor(R.color.login_hint_color));
            btn_send.setText("(" + millisUntilFinished / 1000 + "秒)");
        }

    }

    /**
     * 将User写入xml
     */
    private void setSpUserVo(LoginBean.Data data, String password) {
        SPUtility.putSPBoolean(this, R.string.islogin, true);
        SPUtility.putSPString(this, "password", password);
        SPUtility.putSPInteger(this, "userid", data.getUserId());
        SPUtility.putSPString(this, "nickname", data.getNickName());
        SPUtility.putSPString(this, "identity_type", data.getIdentity_type());
        SPUtility.putSPString(this, "username", data.getIdentifier());
        SPUtility.putSPInteger(this, "userStatus", data.getUserStatus());
        String id = JPushInterface.getRegistrationID(LoginActivity.this);
        JPushInterface.setAlias(LoginActivity.this, 0, data.getUserId() + "");
        RequestParams params = new RequestParams(Preferences.ADD_REGISTER_ID);
        params.addQueryStringParameter("userId", data.getUserId() + "");
        params.addQueryStringParameter("phoneType", "android");
        params.addQueryStringParameter("registrationId", id);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                AddRegisterBean bean = gson.fromJson(s, AddRegisterBean.class);
                if (bean != null && bean.getCode().equals("SUCCESS")) {
//                        verifCode = bean.getData().getCheckCode();
                } else {
                    ActivityUtils.showToast(LoginActivity.this, bean.getCodeInfo());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    long exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
