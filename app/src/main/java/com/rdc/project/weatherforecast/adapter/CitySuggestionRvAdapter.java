package com.rdc.project.weatherforecast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdc.project.weatherforecast.R;
import com.rdc.project.weatherforecast.base.BaseRecyclerViewAdapter;
import com.rdc.project.weatherforecast.bean.CityBean;

import butterknife.BindView;

public class CitySuggestionRvAdapter extends BaseRecyclerViewAdapter<CityBean.ResultsBean> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CitySuggestionRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_city_suggestion, null);
        return new CitySuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CitySuggestionViewHolder) holder).bindView(mDataList.get(position));
    }

    class CitySuggestionViewHolder extends BaseRvHolder {

        @BindView(R.id.tv_city_name)
        TextView mTvCityName;

        public CitySuggestionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(CityBean.ResultsBean resultsBean) {
            mTvCityName.setText(resultsBean.getPath());
        }
    }
}
