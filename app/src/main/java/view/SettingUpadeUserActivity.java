package view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.edu.coolnews.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import model.UserBean;
import util.ToastUtil;

/**
 * 注册
 */
public class SettingUpadeUserActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernaem, password, email;
    private Button update;
    private ImageView choiseImg;
    private ImageView back_img;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private String fileName;
    private BmobFile bmobFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_update_user);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        usernaem = (EditText) findViewById(R.id.username);
        back_img = (ImageView) findViewById(R.id.back_img);
        choiseImg = (ImageView) findViewById(R.id.img);
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
        switch (v.getId()) {
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

                String name = usernaem.getText().toString();
                String userpassword = password.getText().toString();
                String useremail = email.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(userpassword) || TextUtils.isEmpty(useremail)) {
                    ToastUtil.MyToast(SettingUpadeUserActivity.this, "你输入有为空，请检查");
                    return;
                }
                UserBean newUser = new UserBean();
                newUser.setPassword(userpassword);
                newUser.setUsername(name);
                newUser.setEmail(useremail);
                newUser.setImage(bmobFile);
                UserBean bmobUser = UserBean.getCurrentUser(UserBean.class);
                newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            ToastUtil.MyToast(SettingUpadeUserActivity.this,"更新用户信息成功");
                        }else{
                            ToastUtil.MyToast(SettingUpadeUserActivity.this,"更新用户信息失败:" + e.getMessage());
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
