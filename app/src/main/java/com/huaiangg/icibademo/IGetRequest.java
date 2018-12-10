package com.huaiangg.icibademo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @description:
 * @author: HuaiAngg
 * @create: 2018-12-08 19:36
 */
public interface IGetRequest {

    @GET("ajax.php?a=fy&f=auto")
    Observable<TransalteWord> getCall(@Query("t") String target, @Query("w") String word);
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // 采用Observable<...>接口
    // getCall()是接受网络请求数据的方法


//    @GET("ajax.php?a=fy&f=auto")
//    Call<TransalteWord> getCall(@Query("t") String target, @Query("w") String word);
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法


//    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
//    Call<Transaltion> getCall();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
}
