package cn.baililuohui.ecar.ui.activity;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.baililuohui.ecar.MainFragmentFactory;
import cn.baililuohui.ecar.R;
import cn.baililuohui.ecar.adapter.MainFragmentAdapter;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabs;

    @BindView(R.id.pager)
    ViewPager pager;

    SparseArray<Fragment> fragments;
    String[] titles = {"导航", "我", "更多"};
    public static final int CHECK_PERMISSION = 0x1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragments = MainFragmentFactory.createFragment();
        MainFragmentAdapter myAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        pager.setAdapter(myAdapter);
        tabs.setupWithViewPager(pager);
        checkPermissions();
    }

    /**
     * 检查权限
     */
    public void checkPermissions(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        PermissionGen.with(MainActivity.this)
                .addRequestCode(CHECK_PERMISSION)
                .permissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    @PermissionSuccess(requestCode = 100)
    public void doSomething(){
        Toast.makeText(this, "应用授权成功", Toast.LENGTH_SHORT).show();
    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        Toast.makeText(this, "应用于授权失败，已经退出", Toast.LENGTH_SHORT).show();
    }
}
