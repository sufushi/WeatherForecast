package com.rdc.project.weatherforecast.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.project.weatherforecast.R;
import com.rdc.project.weatherforecast.base.BaseRecyclerViewAdapter;
import com.rdc.project.weatherforecast.bean.LifestyleBean;

import butterknife.BindView;

public class LifeSuggestionRvAdapter extends BaseRecyclerViewAdapter<LifestyleBean> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public LifeSuggestionRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_life_suggestion, null);
        return new LifeSuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LifeSuggestionViewHolder) holder).bindView(mDataList.get(position));
    }

    class LifeSuggestionViewHolder extends BaseRvHolder {

        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_brf)
        TextView mTvBrf;
        @BindView(R.id.tv_txt)
        TextView mTvTxt;

        public LifeSuggestionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(LifestyleBean lifestyleBean) {
            Log.e("error", lifestyleBean.getType());
            String type = lifestyleBean.getType();
            Drawable drawable;
            if ("comf".equals(type)) {
                mTvType.setText("舒适度指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_comf);
            } else if ("cw".equals(type)) {
                mTvType.setText("洗车指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_cw);
            } else if ("drsg".equals(type)) {
                mTvType.setText("穿衣指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_drsg);
            } else if ("flu".equals(type)) {
                mTvType.setText("感冒指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_flu);
            } else if ("sport".equals(type)) {
                mTvType.setText("运动指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_sport);
            } else if ("trav".equals(type)) {
                mTvType.setText("旅游指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_trav);
            } else if ("uv".equals(type)) {
                mTvType.setText("紫外线指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_uv);
            } else if ("air".equals(type)) {
                mTvType.setText("空气污染扩散条件指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_air);
            } else {
                mTvType.setText("生活指数");
                drawable = mContext.getResources().getDrawable(R.drawable.ic_comf);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvType.setCompoundDrawablesRelative(drawable, null, null, null);
            mTvBrf.setText(lifestyleBean.getBrf());
            mTvTxt.setText(lifestyleBean.getTxt());
        }
    }
}
