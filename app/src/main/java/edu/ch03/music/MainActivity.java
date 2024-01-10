package edu.ch03.music;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // 登录界面所填写的用户名和密码
    private EditText et_name_login, et_psw_login;
    private String name, phone, psw;
    // 注册页面对的用户名、密码、电话号码
    private EditText et_username;
    private EditText et_phone_regist;
    private EditText et_psw_regist;
    // 已经存在的用户名
    private String existName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        et_name_login = (EditText) findViewById(R.id.name_et);
        et_psw_login = (EditText) findViewById(R.id.password_et);
    }

    // 登录事件
    public void login(View view) {
        String name = et_name_login.getText().toString().trim();
        String psw = et_psw_login.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "选项不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 获取密码，如果账号密码匹配则登录成功
        String password = UserInfoManager.getInstance(this).findData(name,
                UserInfoManager.PASSWORD);
        if (psw.equals(password)) {
            // 获取注册时的电话号码
            String phone = UserInfoManager.getInstance(this).findData(name,
                    UserInfoManager.PHONE);
            Intent intent = new Intent(this, SecondActivity.class);
            // 启动MainActivity
            startActivity(intent);
            // 结束当前Activity
            finish();
        } else {
            Toast.makeText(this, "用户不存在，请先注册", Toast.LENGTH_SHORT).show();
        }
    }

    // 注册对话框
    public void register(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        view = View.inflate(this, R.layout.regist_dialog, null);
        builder.setView(view);
        et_username = (EditText) view.findViewById(R.id.editTxt_username_regist);
        et_phone_regist = (EditText) view.findViewById(R.id.editTxt_phone_regist);
        et_psw_regist = (EditText) view.findViewById(R.id.editTxt_psw_regist);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = et_username.getText().toString().trim();
                phone = et_phone_regist.getText().toString().trim();
                psw = et_psw_regist.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(psw)) {
                    Toast.makeText(MainActivity.this, "选项不能为空",
                            Toast.LENGTH_SHORT).show();
                    UserInfoManager.dialogState(dialog, false);
                    return;
                }
                List<User> all = UserInfoManager.getInstance(MainActivity.this)
                        .queryAll();
                for (User user : all) {
                    existName = user.getName();
                }
                if (existName != null && existName.equals(name)) {
                    Toast.makeText(MainActivity.this, "该用户已存在，无需重复注册",
                            Toast.LENGTH_SHORT).show();
                    UserInfoManager.dialogState(dialog, false);
                } else {
                    User user = new User(name, psw, phone);
                    UserInfoManager.getInstance(MainActivity.this)
                            .insertUser(user);
                    UserInfoManager.dialogState(dialog, true);
                    Toast.makeText(MainActivity.this, "注册成功，请登录",
                            Toast.LENGTH_SHORT).show();
                    et_name_login.setText(name);
                    et_psw_login.setText(psw);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserInfoManager.dialogState(dialog, true);
            }
        });
        builder.create().show();
    }
}
