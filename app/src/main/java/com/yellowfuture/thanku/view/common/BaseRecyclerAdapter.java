package com.yellowfuture.thanku.view.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zuby on 2016. 7. 7..
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public  Context mContext;
    public final static int LIST_VIEW_TYPE_HEADER = 0;
    public final static int LIST_VIEW_TYPE_CONTENT = 1;
    public final static int LIST_VIEW_TYPE_FOOTER = 2;

    public boolean isFooter(int position) {
        return (position + 1 == getItemCount());

    }
    public boolean isHeader(int position) {
        return (position == 0);

    }

    @Override
    public int getItemViewType(int position) {
        if(isHeader(position))
            return LIST_VIEW_TYPE_HEADER;
        else if (isFooter(position))
            return LIST_VIEW_TYPE_FOOTER;
        else
            return LIST_VIEW_TYPE_CONTENT;
    }
}
