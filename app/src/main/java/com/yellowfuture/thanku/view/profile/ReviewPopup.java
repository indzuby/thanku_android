package com.yellowfuture.thanku.view.profile;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.Review;
import com.yellowfuture.thanku.network.controller.OrderController;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-27.
 */
public class ReviewPopup extends Dialog implements View.OnClickListener {
    Review mReview;
    String mName;
    boolean isNew = false;
    EditText mCommentEditText;
    int[] scoreViews = {R.id.scoreView1, R.id.scoreView2, R.id.scoreView3, R.id.scoreView4, R.id.scoreView5};
    public ReviewPopup(Context context,Review review,String name) {
        super(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        mReview = review;

        if(review.getScore()==0)
            isNew = true;

        mName = name;
        init();
    }

    public void init() {
        setContentView(R.layout.element_popup_review);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.black50));
        findViewById(R.id.closeButton).setOnClickListener(this);
        setCanceledOnTouchOutside(true);

        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);

        nameTextView.setText(mName);
        mCommentEditText = (EditText) findViewById(R.id.commentEditText);
        mCommentEditText.setText(mReview.getComment());
        setScore(mReview.getScore());

        for(int res : scoreViews)
            findViewById(res).setOnClickListener(this);
        findViewById(R.id.addReviewButton).setOnClickListener(this);

        if(!isNew) {
            mCommentEditText.setFocusable(false);
            ((TextView) findViewById(R.id.addReviewButton)).setText("닫기");
            ((TextView) findViewById(R.id.titleView)).setText("리뷰확인");
            findViewById(R.id.photoButton).setVisibility(View.GONE);
        }
    }

    private void setScore(int score) {
        for (int i = 0; i < score; i++)
            findViewById(scoreViews[i]).setSelected(true);
        for (int i = score; i < scoreViews.length; i++)
            findViewById(scoreViews[i]).setSelected(false);
        mReview.setScore(score);
    }

    private void addReview() {
        String accessToken = SessionUtils.getString(getContext(), CodeDefinition.ACCESS_TOKEN,"");
        OrderController.getInstance(getContext()).addReview(accessToken, mReview, new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "리뷰가 작성되었습니다.", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }


    private void editReview() {
        String accessToken = SessionUtils.getString(getContext(), CodeDefinition.ACCESS_TOKEN,"");
        OrderController.getInstance(getContext()).editReview(accessToken, mReview, new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "리뷰가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout:
            case R.id.closeButton:
                dismiss();
                break;
            case R.id.scoreView1:
                setScore(1);
                break;
            case R.id.scoreView2:
                setScore(2);
                break;
            case R.id.scoreView3:
                setScore(3);
                break;
            case R.id.scoreView4:
                setScore(4);
                break;
            case R.id.scoreView5:
                setScore(5);
                break;
            case R.id.addReviewButton:
                if(mReview.getScore()==0) {
                    Toast.makeText(getContext(), "평점을 매겨주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mReview.setComment(mCommentEditText.getText().toString());
                if(mReview.getComment().length()<=0) {
                    Toast.makeText(getContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isNew) addReview();
                else dismiss();
                break;
        }
    }
}
