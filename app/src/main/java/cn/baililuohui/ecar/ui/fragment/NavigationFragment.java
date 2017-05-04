package cn.baililuohui.ecar.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.baililuohui.ecar.App;
import cn.baililuohui.ecar.R;

public class NavigationFragment extends Fragment implements AMapLocationListener {

    private static final String TAG = "NavigationFragment";
    @BindView(R.id.map)
    MapView mMapView;


    private AMap aMap;
    public AMapLocationClient mLocationClient = null;//声明AMapLocationClient类对象
    public AMapLocationClientOption mLocationOption = null;//声明AMapLocationClientOption对象

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);
        initMap();
        return view;
    }

    /**
     * 初始化地图 和 定位
     */
    private void initMap() {
        if (null == aMap) {
            aMap = mMapView.getMap();
        }
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

        mLocationClient = new AMapLocationClient(getActivity());//初始化定位
        mLocationClient.setLocationListener(this);//设置定位回调监听
        mLocationOption = new AMapLocationClientOption();//初始化AMapLocationClientOption对象
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setOnceLocation(true);//获取一次定位结果
        mLocationOption.setOnceLocationLatest(true);//获取最近3s内精度最高的一次定位结果
        mLocationOption.setNeedAddress(true);//设置是否返回地址信息（默认返回地址信息）
        mLocationClient.setLocationOption(mLocationOption);//给定位客户端对象设置定位参数
        mLocationClient.startLocation();//启动定位

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置默认缩放按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    public NavigationFragment() {
    }

    public static NavigationFragment newInstance() {
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //TODO
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {//可在其中解析amapLocation获取相应内容。
                aMap.moveCamera(CameraUpdateFactory.zoomTo(16.0f));
                mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                aMapLocation.getFloor();//获取当前室内定位的楼层
                aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                aMapLocation.getPoiName();
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                Log.d(TAG, "onLocationChanged: " + aMapLocation.getPoiName());
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
