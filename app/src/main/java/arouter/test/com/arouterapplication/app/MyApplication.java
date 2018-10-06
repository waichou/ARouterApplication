package arouter.test.com.arouterapplication.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author zhouwei
 * @date 2018/9/24
 * @time 18:26
 * @desc
 */
public class MyApplication extends Application {
    public boolean isDebug = false;

    @Override
    public void onCreate() {
        super.onCreate();if (isDebug){//处于debug状态时，会toast提示出路径没找到的问题
            ARouter.openLog();
            ARouter.openDebug();
        }

        ARouter.init(this);


    }
}
