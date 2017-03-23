package view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.edu.coolnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import db.CollectNewsHelp;
import db.dao.CollectNewsDao;
import model.UserBean;
import util.ToastUtil;

/**
 * 注册
 */
public class SettingUpadeUserActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernaem, email;
    private Button update;
    private SimpleDraweeView choiseImg;
    private ImageView back_img;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private String fileName;
    private BmobFile bmobFile;
    private UserBean userBean;
    private Button updatePassword;
    private AlertDialog.Builder builder;
    private View update_password_view;
    private EditText old_password, new_password, ok_password;
    private CollectNewsHelp help;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update_user);
        userBean = BmobUser.getCurrentUser(UserBean.class);
        getSupportActionBar().hide();
        initView();
        help = new CollectNewsHelp(this, "collect.db", null, 1);
        db = help.getReadableDatabase();
    }

    private void initView() {

        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("修改密码");
        update_password_view = LayoutInflater.from(this).inflate(R.layout.update_password, null);
        old_password = (EditText) update_password_view.findViewById(R.id.old_password);
        new_password = (EditText) update_password_view.findViewById(R.id.new_password);
        ok_password = (EditText) update_password_view.findViewById(R.id.ok_password);
        builder.setView(update_password_view);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(ok_password.getText().toString()) || TextUtils.isEmpty(new_password.getText().toString())) {
                    ToastUtil.MyToast(SettingUpadeUserActivity.this, "你输入有为空，请检查");
                    return;
                }

                if (!(new_password.getText().toString().equals(ok_password.getText().toString()))) {
                    ToastUtil.MyToast(SettingUpadeUserActivity.this, "新密码跟确认密码不一样！");
                    return;
                }
                BmobUser.updateCurrentUserPassword(old_password.getText().toString(), new_password.getText().toString(),
                        new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtil.MyToast(SettingUpadeUserActivity.this, "密码修改成功，可以用新密码进行登录啦");
                                    Intent intent = new Intent(SettingUpadeUserActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    BmobUser.logOut();
                                    finish();
                                } else {
                                    ToastUtil.MyToast(SettingUpadeUserActivity.this, "失败:" + e.getMessage());
                                }
                            }

                        });
                dialog.dismiss();
            }
        });


        builder.setNegativeButton("取消", null);
        updatePassword = (Button) findViewById(R.id.updatepassword);
        updatePassword.setOnClickListener(this);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        email = (EditText) findViewById(R.id.email);
        usernaem = (EditText) findViewById(R.id.username);
        back_img = (ImageView) findViewById(R.id.back_img);
        choiseImg = (SimpleDraweeView) findViewById(R.id.img);
        if (userBean!=null){
            usernaem.setText(userBean.getUsername());
            email.setText(userBean.getEmail());
            choiseImg.setImageURI(Uri.parse(userBean.getImage().getFileUrl()));
        }
        back_img.setOnClickListener(this);
        choiseImg.setOnClickListener(this);

    }


    /**
     * 为view注册点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) update_password_view.getParent();
        if (parent != null) {
            parent.removeView(update_password_view);
        }

        switch (v.getId()) {
            case R.id.updatepassword:
                builder.show();
                break;
            case R.id.back_img:
                finish();
                break;
            case R.id.img:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
                break;
            case R.id.update:
                final String name = usernaem.getText().toString();
                String useremail = email.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(useremail)) {
                    ToastUtil.MyToast(SettingUpadeUserActivity.this, "你输入有为空，请检查");
                    return;
                }
                UserBean newUser = new UserBean();
                newUser.setUsername(name);
                newUser.setEmail(useremail);
                newUser.setImage(bmobFile);
                UserBean bmobUser = UserBean.getCurrentUser(UserBean.class);
                newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.MyToast(SettingUpadeUserActivity.this, "更新用户信息成功");
                            BmobUser.getCurrentUser().setUsername(name);
                            CollectNewsDao.updateUsername(db,userBean.getUsername(),name);
                            Intent intent = new Intent(SettingUpadeUserActivity.this, LoginActivity.class);
                            startActivity(intent);
                            BmobUser.logOut();
                            finish();
                        } else {
                            ToastUtil.MyToast(SettingUpadeUserActivity.this, "更新用户信息失败:" + e.getMessage());
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                cropPhoto(data.getData());// 裁剪图片
                break;

            case 2:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        // head = toRoundBitmap1(head);//调用圆角处理方法
                        setPicToView(head);// 保存在SD卡中
                        choiseImg.setImageBitmap(head);// 用ImageView显示出来
                        if (head != null && head.isRecycled()) {
                            head.recycle();
                        }
                        /**
                         * 上传服务器代码
                         */
                        bmobFile = new BmobFile(new File(fileName));
                        bmobFile.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                    //  ToastUtil.MyToast(RegistActivity.this,"上传文件成功:" + bmobFile.getFileUrl());
                                    Log.i("TAGll", "done: " + bmobFile.getFileUrl());
                                } else {
                                    //  ToastUtil.MyToast(RegistActivity.this,"上传文件失败:" + bmobFile.getFileUrl());
                                    Log.i("TAGll", "done: dd" + bmobFile.getFileUrl());
                                }

                            }

                            @Override
                            public void onProgress(Integer value) {
                                Log.i("TAG", "onProgress: " + value);
                            }
                        });
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

//    private String getImagePath(Uri uri, String seletion) {
//        String path = null;
//        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
//            }
//            cursor.close();
//
//        }
//        return path;
//    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // 从本地的文件中以保存的图片中 获取图片的方法
    private Bitmap getBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
