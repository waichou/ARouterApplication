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
    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.init(this);
    }
}
