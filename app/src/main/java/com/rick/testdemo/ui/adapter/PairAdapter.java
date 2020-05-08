package com.rick.testdemo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rick.testdemo.R;
import com.rick.testdemo.entity.BlueToothEntity;

import java.util.List;

/**
 * package: adapter
 * author: Rick Li
 * date: 2020/4/22 10:33
 * desc:
 */
public class PairAdapter extends RecyclerView.Adapter<PairAdapter.VH> {

    private Context mContext;
    private List<BlueToothEntity> pairArray;

    public PairAdapter(Context mContext, List<BlueToothEntity> pairArray) {
        this.mContext = mContext;
        this.pairArray = pairArray;
    }


    @NonNull
    @Override
    public PairAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_bt, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PairAdapter.VH holder, int position) {
        BlueToothEntity bte = pairArray.get(position);
        if (bte.getBtName() == null) {
            holder.bt_name.setText(bte.getBtAddress());
        } else {
            holder.bt_name.setText(bte.getBtName());
            holder.bt_address.setText(bte.getBtAddress());
        }
    }

    @Override
    public int getItemCount() {
        return pairArray.size();
    }


    public class VH extends RecyclerView.ViewHolder {

        private TextView bt_name;

        private TextView bt_address;

        public VH(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            bt_name = itemView.findViewById(R.id.bt_name);
            bt_address = itemView.findViewById(R.id.bt_address);
        }

    }

}
