package com.ruan.mygitignore;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**自定义添加RecyclerView请求下拉加载更多，上拉加载*/

public class SecondActivity extends AppCompatActivity {
    private RecyclerView tv_recycler;
    private MyRecycleViewAdapter myRecycleViewAdapter;
    private List<String> mDatas=new ArrayList<>();
    private SwipeRefreshLayout swipe_refresh;
    private List<String> mInsertDatas=new ArrayList<>();
    boolean isLoading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();//初始化数据
        tv_recycler=findViewById(R.id.tv_recycler);
        swipe_refresh=findViewById(R.id.swipe_refresh);

        //创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//默认垂直排列
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //layoutManager.setOrientation(RecyclerView.HORIZONTAL);//设置水平排列
        //LinearLayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        tv_recycler.setLayoutManager(layoutManager);
        //添加分割线
        tv_recycler.addItemDecoration(new MyItemDecoration(this,RecyclerView.VERTICAL));
        tv_recycler.setItemAnimator(new DefaultItemAnimator());
        //tv_recycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //创建数据适配器
        myRecycleViewAdapter=new MyRecycleViewAdapter(this,mDatas);
        //设置数据适配器
        tv_recycler.setAdapter(myRecycleViewAdapter);
        myRecycleViewAdapter.setOnItemClickListener(new MyRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SecondActivity.this, "第"+position+"条数据", Toast.LENGTH_SHORT).show();
            }
        });

        swipe_refresh.setColorSchemeResources(new int[]{R.color.start,R.color.min,R.color.end});//设置下拉进度条颜色
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //延迟2秒设置不刷新,模拟耗时操作，需要放在子线程中
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // myRecycleViewAdapter.addData(0);
                        myRecycleViewAdapter.add(mInsertDatas);
                      //  myRecycleViewAdapter.notifyDataSetChanged();
                        swipe_refresh.setRefreshing(false);//设置是否刷新

                    }
                },2000);
            }
        });
        /**RecyclerView使用滚动监听*/
        tv_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 当RecyclerView的滑动状态改变时触发
             */
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
                //如果想通过指尖滑动屏幕变化来判断是否触碰，实现当且仅当滑动到最后一项并且手指上拉抛出时才执行上拉加载更多效果的话，需要配合onScrollStateChanged(RecyclerView recyclerView, int newState的使用，
                //if (newState==RecyclerView.SCROLL_STATE_IDLE)来判断
            }
            /**
             * 当RecyclerView滑动时触发
             * 类似点击事件的MotionEvent.ACTION_MOVE
             */

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition+1==myRecycleViewAdapter.getItemCount()){
                    Log.d("test", "loading executed");
                    boolean isRefreshing=swipe_refresh.isRefreshing();
                    if (isRefreshing){
                        myRecycleViewAdapter.notifyItemRemoved(myRecycleViewAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading){
                        isLoading=true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        },1000);
                    }
                }
            }
        });
    }
    /**获取测试数据*/

    private void getData() {
        for (int i=0;i<6;i++){
            mDatas.add("我是加载更多数据"+i);
        }
        myRecycleViewAdapter.notifyDataSetChanged();
        swipe_refresh.setRefreshing(false);
    }

    private void initData() {
        for (int i=0;i<40;i++){
            mDatas.add("我是item"+i);
        }
        for (int j=0;j<10;j++){
            mInsertDatas.add("我是Insert Data"+j);
        }
    }
}
