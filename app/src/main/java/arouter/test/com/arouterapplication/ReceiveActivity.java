package arouter.test.com.arouterapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import arouter.test.com.arouterapplication.bean.User;

@Route(path = "/user/main",extras = 1)
public class ReceiveActivity extends AppCompatActivity {

    @Autowired
    String name;

    @Autowired(name = "name")
    String username;

    @Autowired(name = "user")
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //inject
        ARouter.getInstance().inject(this);

        System.out.println("main ---> username=" + name + ",user=" + user +",username="+username);
    }
}
