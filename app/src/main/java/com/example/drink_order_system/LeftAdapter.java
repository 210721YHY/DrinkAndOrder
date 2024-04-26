package com.example.drink_order_system;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//LeftAdapter的RecyclerView适配器类，用于显示左侧列表。它包含以下方法：
//
//构造方法：接收一个LeftBean类型的ArrayList作为参数，用于存储列表数据。
//setOnItemClickListener：设置列表项点击事件监听器。
//getItem：根据位置获取列表项数据。
//onCreateViewHolder：创建列表项视图。
//onBindViewHolder：绑定列表项数据和视图。
//getItemCount：获取列表项数量。
//LeftViewHolder：内部静态类，继承自RecyclerView.ViewHolder，用于缓存列表项视图。
//bindBean：将LeftBean对象的数据绑定到列表项视图上。
//OnItemClickListener：接口，定义了列表项点击事件的回调方法。
//setCurrentPosition：设置当前选中的列表项位置。
//getCurrentTitle：获取当前选中的列表项标题。

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftViewHolder> {

//    左边类型列表选择
    private ArrayList<LeftBean> mList;
    private OnItemClickListener onItemClickListener;

    public LeftAdapter( ArrayList<LeftBean> list) {
        this.mList = list;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private LeftBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public LeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.left_list_item,parent,false);

        return new LeftViewHolder(view);

    }

    @Override
    public void onBindViewHolder(LeftViewHolder holder, int position) {
        LeftBean target = getItem(holder.getAdapterPosition());
        if (holder instanceof LeftAdapter.LeftViewHolder) {
            ((LeftAdapter.LeftViewHolder) holder).bindBean(target);
            //绑定监听事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("onClick",holder.getAdapterPosition()+"\t");
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClicked(getItem(holder.getAdapterPosition()).getRightPosition());
                    }
                    for (LeftBean bean:mList){
                        bean.setSelect(false);
                    }
                    getItem(holder.getAdapterPosition()).setSelect(true);
                    notifyDataSetChanged();
                }
            });
        } else {
            throw new IllegalStateException("Illegal state Exception onBindviewHolder");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        //private View cl_left_item;

        LeftViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.TV_drink_type);
        }

        void bindBean(LeftBean target)
        {
            tvTitle.setText(target.getTitle());
            if (target.isSelect()){
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            }
        }
    }

    interface OnItemClickListener {
        void onItemClicked(int rightPosition);
    }

    public void setCurrentPosition(int rightPosition){
        for (int i = 0; i < mList.size(); i++){
            LeftBean bean = mList.get(i);
            if(i < mList.size()-1)
            {
                LeftBean nextBean = mList.get(i+1);
                if (bean.getRightPosition() <= rightPosition && rightPosition < nextBean.getRightPosition()){
                    bean.setSelect(true);
                } else {
                    bean.setSelect(false);
                }
            }
            else
            {
                if(bean.getRightPosition() <= rightPosition)
                {
                    bean.setSelect(true);
                }
                else {
                    bean.setSelect(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String getCurrentTitle(){
        String currentTitle = "";
        for (LeftBean bean:mList){
            if (bean.isSelect()){
                currentTitle = bean.getTitle();
                break;
            }
        }
        return currentTitle;
    }
}