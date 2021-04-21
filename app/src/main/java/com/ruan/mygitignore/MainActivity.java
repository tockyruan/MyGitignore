package com.ruan.mygitignore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isApkDebugable(MainActivity.this)){
                    Log.d("debug","我是debug模式");
                }else {
                    Log.d("debug","我是release模式");
                }

            }
        });
    }
    public static boolean isApkDebugable(Context context){
        try {
            ApplicationInfo info=context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        }catch (Exception e){
            return false;
        }

    }
}