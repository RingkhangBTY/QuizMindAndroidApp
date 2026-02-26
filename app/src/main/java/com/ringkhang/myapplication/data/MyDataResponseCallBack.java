package com.ringkhang.myapplication.data;

public interface MyDataResponseCallBack<T> {
    void onSuccess(T data);
    void onError(int errorCode, String massage);
    void onFailure(Throwable t);
}
