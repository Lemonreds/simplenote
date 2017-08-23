package com.example.notes.Util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 地图工具类
 * From 高德地图
 */

public class LocationUtil {

    private Context context;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;

    private static StringBuilder location;

    public LocationUtil(Context context) {
        this.context = context;
    }

    public String getLocation(){

        //初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        //mLocationOption.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


        //设置定位回调监听
        mLocationClient.setLocationListener( new AMapLocationListener(){
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {

                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //解析amapLocation获取相应内容。

                        location =  new StringBuilder();
                        location.append(amapLocation.getDistrict()).append(" ");
                        location.append(amapLocation.getCity()).append(" ");
                        location.append(amapLocation.getProvince()).append(" ");
                        location.append(amapLocation.getCountry());
                       // location.append(amapLocation.getAddress());

                    }
                    //else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                       // Log.e("AmapError","location Error, ErrCode:"
                     //           + amapLocation.getErrorCode() + ", errInfo:"
                        //        + amapLocation.getErrorInfo());
                   // }

                }
            }

        });



        if(location!=null){
            stop();
            return location.toString();

        }
        return "神秘地区";

    }


    public void stop(){

        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
       // mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }


}
