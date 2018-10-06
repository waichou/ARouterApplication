package arouter.test.com.arouterapplication.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;

import arouter.test.com.arouterapplication.Utils;

/**
 * 单独降级策略
 */
@Interceptor(priority = 2)
public class PayInterceptor implements IInterceptor {
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
        System.out.println("Pay拦截器拦截中...");
        // 是否需要登录
        if (3 == postcard.getExtra()) {
            // 是否已经登录
            if (Utils.isLogin) {
                callback.onContinue(postcard);
            } else {
                ARouter.getInstance().build("/user/login").navigation();
                //这句写上后可以回调到方法上去onInterrupt，反之不会回调。在不写的情况下也没有影响！
                //callback.onInterrupt(new Exception("this is error!"));
            }
        } else {
            callback.onContinue(postcard);//这个方法是必须执行的，跳转目标的必行方法
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
        System.out.println("拦截器init...");
    }
}
