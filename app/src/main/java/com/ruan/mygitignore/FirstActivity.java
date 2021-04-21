package com.ruan.mygitignore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**Dialog相关事宜*/
public class FirstActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button btnTwo = findViewById(R.id.btn_two);
        Button btnThree = findViewById(R.id.btn_three);
        Button btnList = findViewById(R.id.btn_list);
        Button btnMultiSelect = findViewById(R.id.btn_multi_select);
        Button btnSingleSelect = findViewById(R.id.btn_single_select);
        Button btnWaiting = findViewById(R.id.btn_waiting);
        Button btnLoading = findViewById(R.id.btn_loading);
        Button btnInput = findViewById(R.id.btn_input);
        Button btnMyStyle = findViewById(R.id.btn_my_style);
        Button btnTime=findViewById(R.id.btn_my_time);


        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnMultiSelect.setOnClickListener(this);
        btnSingleSelect.setOnClickListener(this);
        btnWaiting.setOnClickListener(this);
        btnLoading.setOnClickListener(this);
        btnInput.setOnClickListener(this);
        btnMyStyle.setOnClickListener(this);
        btnTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_two://最普通dialog
                showTwo();
                break;
            case R.id.btn_three://三个按钮dialog
                showThree();
                break;
            case R.id.btn_list://列表样式dialog
                showList();
                break;
            case R.id.btn_multi_select://多选dialog
                showMultiSelect();
                break;
            case R.id.btn_single_select://单选dialog
                // showSingSelect();
                break;
            case R.id.btn_waiting://等待dialog
                showWaiting();
                break;
            case R.id.btn_loading://加载进度dialog
                showLoading();
                break;
            case R.id.btn_input://输入框dialog
                showInput();
                break;
            case R.id.btn_my_style://自定义dialog
                 showMyStyle();
                break;
            case R.id.btn_my_time:
                //showMyTime();
                showMyDate();
                break;
            default:
        }

    }

    private void showMyDate() {
        //获取日历的一个实例，里面包含了当前的年月日
        Calendar calendar=Calendar.getInstance();
        //构建一个日期对话框，该对话框已经集成了日期选择器
        //DatePickerDialog的第二个构造参数指定了日期监听器
        DatePickerDialog dialog=new DatePickerDialog(this,this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //把日期对话框显示在界面上
        dialog.show();
    }

    /**时间选择Dialog*/
    private void showMyTime() {
        //获取日历的一个实例，里面包含了当前的时分秒
        Calendar calendar=Calendar.getInstance();
        //构建一个时间对话框，该对话框已经集成了时间选择器
        //TimePickerDialog的第二个构造参数指定了事件监听器
        TimePickerDialog dialog=new TimePickerDialog(this,this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);//true表示使用二十四小时制
        //把时间对话框显示在界面上
        dialog.show();

    }

    private void showMyStyle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.setCancelable(false).create();
        popFromBottom(dialog);
        View dialogView = View.inflate(this, R.layout.dialog_login, null);
        //设置对话框布局
        dialog.setView(dialogView);
        dialog.show();
        dialog.getWindow().setLayout(ScreenUtils.getScreenWidth(this)/2,LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText etName = (EditText) dialogView.findViewById(R.id.et_name);
        EditText etPwd = (EditText) dialogView.findViewById(R.id.et_pwd);
        Button btnLogin = (Button) dialogView.findViewById(R.id.btn_login);
        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        setScreenBgDarken();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String pwd = etPwd.getText().toString();
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(pwd)) {
                    Toast.makeText(FirstActivity.this, "用户名和密码均不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("TAG", "用户名：" + name);
                Log.e("TAG", "密码：" + pwd);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        setScreenBgLight();


    }

    private void showInput() {
        final EditText editText = new EditText(this);
        builder = new AlertDialog.Builder(this).setTitle("输入框dialog").setView(editText)
                .setPositiveButton("读取输入框内容", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(FirstActivity.this, "输入内容为：" + editText.getText().toString()
                                , Toast.LENGTH_LONG).show();
                    }
                });
        builder.create().show();

    }

    /**
     * 显示进度条的Dialog
     */
    private void showLoading() {
        final int MAX_VALUE = 100;
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("带有加载进度dialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_VALUE);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < MAX_VALUE) {
                    try {
                        Thread.sleep(100);
                        progress++;
                        progressDialog.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //加载完毕自动关闭dialog
                progressDialog.cancel();
            }
        }).start();

    }

    // 设置屏幕背景变暗
    private void setScreenBgDarken() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
    }
    // 设置屏幕背景变亮
    private void setScreenBgLight() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        lp.dimAmount = 1.0f;
        getWindow().setAttributes(lp);
    }

    private void popFromBottom(Dialog dialog) {
        Window win = dialog.getWindow();
        win.setGravity(Gravity.BOTTOM);   // 这里控制弹出的位置
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
       // dialog.getWindow().setBackgroundDrawable(null);//这是设置window背景为空
        win.setAttributes(lp);
    }

    private void showMultiSelect() {
        final List<Integer> choice = new ArrayList<>();
        final String[] items = {"多选1", "多选2", "多选3", "多选4", "多选5", "多选6"};
        //默认都未选中
        boolean[] isSelect = {false, false, false, false, false, false};

        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                .setTitle("多选dialog")
                .setMultiChoiceItems(items, isSelect, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        if (b) {
                            choice.add(i);
                        } else {
                            choice.remove(choice.indexOf(i));
                        }

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder str = new StringBuilder();

                        for (int j = 0; j < choice.size(); j++) {
                            str.append(items[choice.get(j)]);
                        }
                        Toast.makeText(FirstActivity.this, "你选择了" + str, Toast.LENGTH_LONG).show();
                    }
                });

        builder.create().show();
    }

    /**
     * 列表选择 dialog
     */

    int checkedItem = 0;

    private void showList() {
        //默认选中的item
        final String[] items = {"西湖区", "江干区", "上城区", "下城区", "拱墅区", "滨江区", "萧山区"};
        builder = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("列表选择dialog")
                .setCancelable(false)
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkedItem = i;
                        Toast.makeText(FirstActivity.this, "你点击的内容为： " + items[i], Toast.LENGTH_LONG).show();

                    }
                });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    private void showThree() {
        /**
         * 三个按钮的 dialog
         */

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("三个按钮dialog标题")
                .setMessage("三个按钮dialog内容")
                .setCancelable(false)
                .setPositiveButton("确定（积极）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(FirstActivity.this, "确定按钮", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("你猜（中立）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(FirstActivity.this, "你猜按钮", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消（消极）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(FirstActivity.this, "关闭按钮", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e("TAG", "对话框消失了");
            }


        });
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.e("TAG", "对话框显示了");
            }
        });
        //显示对话框
        dialog.show();


    }

    private void showWaiting() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("加载dialog");
        progressDialog.setMessage("加载中...");
        progressDialog.setIndeterminate(true);// 是否形成一个加载动画  true表示不明确加载进度形成转圈动画  false 表示明确加载进度
        progressDialog.setCancelable(false);//点击返回键或者dialog四周是否关闭dialog  true表示可以关闭 false表示不可关闭
        progressDialog.show();


    }

    /**
     * 显示两个按钮
     */
    private void showTwo() {
        builder = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("两个按钮的dialog")
                .setMessage("我是两个按钮的dialog内容")
                .setCancelable(false)//点击对话框以外的区域是否让对话框消失
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(FirstActivity.this, "确定按钮", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(FirstActivity.this, "关闭按钮", Toast.LENGTH_LONG).show();
                        // dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //获取时间对话框设定的小时和分钟数
        String desc=String.format("您选择的时间是%d时%d分",hourOfDay,minute);
        Toast.makeText(FirstActivity.this, desc, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //获取日期对话框设定的年月份
        String desc=String.format("您选择的日期是%d年%d月%d日",year,month+1,dayOfMonth);
        Toast.makeText(FirstActivity.this, desc, Toast.LENGTH_LONG).show();
    }
}
