package com.example.facey.interfaces;

public interface RetrofitCallBack<T> {
    public void Success(T data);

    public void Failure(String error);
}
