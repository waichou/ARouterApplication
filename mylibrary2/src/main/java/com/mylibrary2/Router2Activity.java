package com.mylibrary2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;



@Route(path = "/route2/activity")
public class Router2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router2);

    }

    public void jump2AppClick(View view){
        ARouter.getInstance().build("/user/main").navigation();
    }

    public void jump2OtherMoudleClick(View view){
        ARouter.getInstance().build("/route/activity").navigation();
    }

}
