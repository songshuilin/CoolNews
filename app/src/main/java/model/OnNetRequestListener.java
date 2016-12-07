package model;




/**
 * Created by Administrator on 2016/12/6.
 */

public interface OnNetRequestListener {

    /**
     * 网络请求开始
     */
    void onStart();

    /**
     * 网络请求结束
     */
    void onFinish();

    /**
     * 网络请求成功
     * @param data 返回的数据实体类信息 泛型定义
     */
    void onSuccess(NewsBean data);

    /**
     * 请求失败
     * @param t 异常
     */
    void onFailure(Throwable t);
}
