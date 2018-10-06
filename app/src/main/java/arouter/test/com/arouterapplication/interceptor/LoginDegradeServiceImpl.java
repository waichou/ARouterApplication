package arouter.test.com.arouterapplication.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author zhouwei
 * @date 2018/9/24
 * @time 22:02
 * @desc 全局降级策略
 */
//@Route(path = "/user/*")
public class LoginDegradeServiceImpl implements DegradeService {
    Context mContext;

    @Override
    public void onLost(Context context, Postcard postcard) {
        //
        System.out.println("DegradeService onLost:" + postcard);
        ARouter.getInstance().build("/user/login").navigation();
    }

    @Override
    public void init(Context context) {
        this.mContext = context ;
    }
}
