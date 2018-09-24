package arouter.test.com.arouterapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import arouter.test.com.arouterapplication.bean.User;

/**
 * 注意：
 * 1.在接收实体类时：注意要序列化，实现Parcelable接口
 * 2.接收方可以通过注解：Autowired 来对应发送方传递的数据key来获取数据
 * 3.接收后方需要在onCreate方法中定义：ARouter.getInstance().inject(this); 不定义接收不到数据
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setUserLoginClick(View view) {

        Utils.isLogin = true;
    }

    public void setUserOutLoginClick(View view) {

        Utils.isLogin = false;
    }

    public void jump2LoginActivityClick(View view) {
        ARouter.getInstance().build("/user/main")
                .withString("name","zhouwei")
                .withParcelable("user",new User("zhouwei1991"))
                .navigation(this, new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {
                System.out.println("ARouter onFound 找到跳转匹配路径");
            }

            @Override
            public void onLost(Postcard postcard) {
                System.out.println("ARouter onLost 没有匹配到跳转路径");
            }

            @Override
            public void onArrival(Postcard postcard) {
                System.out.println("ARouter onArrival 成功跳转");
            }

            @Override
            public void onInterrupt(Postcard postcard) {
                System.out.println("ARouter onInterrupt 跳转被中断");
                /**  下面的异常信息是在拦截器中拦截返回的信息 **/
                String errorInfo = (String)postcard.getTag();
                System.out.println("error info is "+ errorInfo);
            }
        });
    }
}
