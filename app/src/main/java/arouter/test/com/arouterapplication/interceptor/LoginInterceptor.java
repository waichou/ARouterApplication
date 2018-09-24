package arouter.test.com.arouterapplication.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import arouter.test.com.arouterapplication.Utils;

@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {
    /**
     * The operation of this tollgate.
     *
     * @param postcard
     *         meta
     * @param callback
     *         callback
     */
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        // 是否需要登录
        if (1 == postcard.getExtra()) {
            // 是否已经登录
            if (Utils.isLogin) {
                callback.onContinue(postcard);
            } else {
                ARouter.getInstance().build("/user/login").navigation();
                callback.onInterrupt(new Exception("this is error!"));
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    /**
     * Do your initScratch work in this method, it well be call when processor has been load.
     *
     * @param context
     *         context
     */
    @Override
    public void init(Context context) {
    }
}
