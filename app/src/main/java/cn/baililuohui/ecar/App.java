package cn.baililuohui.ecar;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jared on 2017/4/26.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext(){
        return mContext;
    }
}
