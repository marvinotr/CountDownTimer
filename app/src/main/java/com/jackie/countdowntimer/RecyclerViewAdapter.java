package com.jackie.countdowntimer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jackie on 2015/12/18.
 * RecyclerView Adapter
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<String> mNumberList;

    public RecyclerViewAdapter(Context context, List<String> numberList) {
        this.mContext = context;
        this.mNumberList = numberList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.number_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.RecyclerViewHolder holder, final int position) {
        holder.numberTextView.setText(mNumberList.get(position));
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.numberTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberList.remove(position);
                //通知刷新
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNumberList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView numberTextView;
        ImageButton deleteImageButton;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            numberTextView = (TextView) itemView.findViewById(R.id.number_item);
            deleteImageButton = (ImageButton) itemView.findViewById(R.id.number_item_delete);
        }
    }
}