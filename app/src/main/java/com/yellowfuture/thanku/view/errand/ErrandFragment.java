package com.yellowfuture.thanku.view.errand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Errand;
import com.yellowfuture.thanku.view.adapter.ErrandItemAdapter;
import com.yellowfuture.thanku.view.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuby on 2016. 6. 15..
 */
public class ErrandFragment extends BaseFragment{


    GridView mErrandGridView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.element_errand,container,false);
        init();
        return mView;
    }

    public void init(){

        mView.findViewById(R.id.errand_new_button).setOnClickListener(this);

        mErrandGridView = (GridView) mView.findViewById(R.id.errand_grid_view);

        List<Errand> sample = new ArrayList<>();
        for(int i = 0; i<9;i++)
            sample.add(new Errand());
        ErrandItemAdapter adapter = new ErrandItemAdapter(getContext(),sample);
        mErrandGridView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.errand_new_button) {
            Toast.makeText(getContext(), "New Button Click", Toast.LENGTH_SHORT).show();
        }
    }
}
