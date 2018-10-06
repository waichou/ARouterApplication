package arouter.test.com.arouterapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import arouter.test.com.arouterapplication.R;
import arouter.test.com.arouterapplication.Utils;
import arouter.test.com.arouterapplication.bean.User;

/**
 * 注意：
 * 1.在接收实体类时：注意要序列化，实现Parcelable接口
 * 2.接收方可以通过注解：Autowired 来对应发送方传递的数据key来获取数据.@Autowired(name = "username")String name|username;
 * 3.接收后方需要在onCreate方法中定义：ARouter.getInstance().inject(this); 不定义接收不到数据
 * 4.拦截策略对于onLost找不到的情况：
 *
 *   单独降级策略：LoginInterceptor
 *   全局降级策略：LoginDegradeServiceImpl
 *
 *   不能两种同时使用，单独降级的方式优先于全局降级，也就是如果同时使用两种方式，
 *   调用完单独降级策略后就不会再调用全局降级。
 *
 *   也可采用监听器的方式来处理路由过程（包含各个情况的回调的处理）
 *   即：.navigation(this, new NavigationCallback())，疑问：是否是和单独降级拦截器配合使用的呢？
 *       答：单独使用监听器吧，因为回调方法中有onLost方法，可以直接跳转到登陆
 *
 *  [拦截的过程]：
 *  #中途拦截，没有重新跳转到新界面
     System.out: ARouter onFound 找到跳转匹配路径
     System.out: 拦截器 拦截中... <<---再此处之后重新调用拦截器callback.onContinue(postcard);进行跳转到目标界面!
     System.out: ARouter onArrival 成功跳转
     System.out: main ---> username=zhouwei,user=User{name='zhouwei1991'},username=zhouwei

     ---------------------------------------------------------------------------------------------------------------------------------------------------
     #中途拦截后，重新跳转到登陆界面（不再回调监听器onArrival方法）
     System.out: ARouter onFound 找到跳转匹配路径
     System.out: 拦截器 拦截中...
     System.out: ARouter onInterrupt 跳转被中断
     System.out: error info is this is error!
     System.out: 拦截器 拦截中...    <<---再此处之后重新调用拦截器callback.onContinue(postcard);进行跳转到目标界面!

 *
 * 5.组件注册问题：仍然要保持组件的注册到配置文件的要求，否则报错：{arouter.test.com.arouterapplication/com.mylibrary.RemoteActivity}; have you declared this activity in your AndroidManifest.xml?
 * 6.greenChannel()方法：表示过滤掉拦截器，即不采用拦截器处理路由请求，直接跳转！
 * 7.单独拦截器是否必须要写上： callback.onContinue(postcard); （必须要写，否则不能跳转）
 *                            callback.onInterrupt() ;(非必须写，写会回调到拦截方法上去！)
 *   @Interceptor(priority = 2) 这个数字越小，优先级越大
 *
 * 参考：https://blog.csdn.net/u013762572/article/details/77150769
 *       https://www.jianshu.com/p/008bd4fa9dc0
 * 8.获取数据，需要在接收方注册：ARouter.getInstance().inject(this);
 * 9.arouter开启处于debug状态时，会toast提示出路径没找到的文字
 * 10.对于拦截login类型的界面，需要定义extra，而对于loginActivity本身没有必要
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

    /**
     * 跳转到不存在的Activity上
     * @param view
     */
    public void jump2FountNotActivityClick(View view) {
        ARouter.getInstance().build("/user/test")
                .greenChannel()
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        System.out.println("ARouter onFound 找到跳转匹配路径");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        //此处为：main 线程
                        System.out.println("ARouter onLost 没有匹配到跳转路径,Thread=" + Thread.currentThread().getName());//<<<<-------------------

                        //通过使用监听器的方式来路由
                        ARouter.getInstance().build("/user/login").greenChannel().navigation();

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

    public void intercepterClick(View view) {
        ARouter.getInstance().build("/user/main")
                .greenChannel()//排除掉兰拦截器的过滤处理
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        System.out.println("ARouter onFound 找到跳转匹配路径");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        System.out.println("ARouter onLost 没有匹配到跳转路径");//<<<<-------------------
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

    public void jump2MoudleActivity(View view) {
        ARouter.getInstance().build("/route2/activity").greenChannel().navigation();
    }

    //跳转到Pay界面
    public void jump2PayActivity(View view) {
        ARouter.getInstance().build("/pay/cast").navigation();
    }
}
