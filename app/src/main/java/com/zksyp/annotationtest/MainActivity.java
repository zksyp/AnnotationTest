package com.zksyp.annotationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zksyp.annotation.BindView3;
import com.zksyp.annotationtest.AnnotateHelper.AnnotateBindUtils;
import com.zksyp.annotationtest.AnnotateHelper.BindView1;
import com.zksyp.annotationtest.AnnotateHelper.BindView2;
import com.zksyp.lib_bind.BindViewTest;

public class MainActivity extends AppCompatActivity {

    @BindView1(R.id.tv_title1)
    TextView tv1;

    @BindView2(R.id.tv_title2)
    TextView tv2;

    @BindView3(R.id.tv_title3)
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnnotateBindUtils.bind(this);
        BindViewTest.bind(this);
        tv1.setText("Bind View One Succeed");
        tv2.setText("Bind View Two Succeed");
        tv3.setText("Bind View Three Succeed");

    }
}
