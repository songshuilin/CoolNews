package util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ToastUtil {
    public static void MyToast(Context context , String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
