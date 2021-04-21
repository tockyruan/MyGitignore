package com.ruan.mygitignore;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

public class ThirdActivity extends AppCompatActivity {
    private SmartRefreshLayout smartRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        smartRefreshLayout= findViewById(R.id.refreshLayout);
        smartRefreshLayout.autoLoadMore();//自动加载
        smartRefreshLayout.autoRefresh();//自动刷新
    }
}
