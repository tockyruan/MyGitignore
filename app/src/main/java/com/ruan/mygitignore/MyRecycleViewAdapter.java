package com.ruan.mygitignore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mDatas;//数据源
    private Context context;
    private static final int TYPE_ITEM = 0;//定义非加载更多
    private static final int TYPE_FOOTER = 1;//加载更多

    private OnItemClickListener onItemClickListener;

    /**
     * 向外部提供调用设置监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyRecycleViewAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
     */

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_FOOTER) {
            //将我们自定义的脚布局转成View
            View view_foot = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false);
            //将View传递给我们自定义脚FootViewHolder
            return new FootViewHolder(view_foot);

        } else if (viewType == TYPE_ITEM) {
            //将我们自定义的item布局转成View
            View view = LayoutInflater.from(context).inflate(R.layout.item_one, parent, false);
            //将View传递给我们自定义ViewHolder
            return new MyHolder(view);
        }
        return null;
    }

    /**
     * 通过方法提供的ViewHolder,将数据绑定到的ViewHolder中
     */

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).tv_content.setText(mDatas.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, holder.getAdapterPosition() + 1);
                    }
                }
            });
        }


    }

    /**
     * 添加一条数据到index 0数据
     */
    public void addData(int position) {
        mDatas.add(position, "Insert" + position);
        notifyItemInserted(position);//通知插入的位置
        if (position != getItemCount()) {
            notifyItemRangeChanged(position, getItemCount());//排序插入位置到末尾
        }
    }

    /**删除一条数据*/
    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);//第position个被删除的时候刷新
        //notifyItemRangeRemoved(1,10);//批量删除
        if (position!=getItemCount()){
            notifyItemRangeChanged(position,getItemCount());//可以刷新从positionStart开始itemCount数量的item了（这里的刷新指回调onBindViewHolder()方法）。
        }
    }

    /**
     * 添加数据集合到数据集合中
     */
    public void add(List<String> datas) {
//        mDatas.addAll(datas);
        mDatas.addAll(0, datas);
        notifyDataSetChanged();

    }

    /**
     * 获取数据源总的条目
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }

    }

    /**
     * 脚布局的ViewHolder
     */

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * 自定义的接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
