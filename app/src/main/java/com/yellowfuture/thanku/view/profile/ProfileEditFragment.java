package com.yellowfuture.thanku.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.yellowfuture.thanku.R;
import com.yellowfuture.thanku.model.User;
import com.yellowfuture.thanku.network.controller.UserController;
import com.yellowfuture.thanku.utils.CodeDefinition;
import com.yellowfuture.thanku.utils.SessionUtils;
import com.yellowfuture.thanku.utils.Utils;
import com.yellowfuture.thanku.view.common.BaseFragment;
import com.yellowfuture.thanku.view.search.AddressSearchActivity;
import com.yellowfuture.thanku.view.start.StartActivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 14..
 */
public class ProfileEditFragment extends BaseFragment {

    User mUser;
    EditText mPhoneEditText;
    EditText mNameEditText;
    EditText mNicknameEditText;
    EditText mPasswordEditText;
    EditText mEmailEditText;
    EditText mBirthDayEditText;
    EditText mAddressEditText;
    RadioButton mMaleRadioButton,mFemaleRadioButton,mNoneRadioButton;

    Switch mEmailSwitch,mSmsSwitch,mPushSwitch;


    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        if(v.getId() == R.id.addressSearchButton) {
            intent = new Intent(getContext(), AddressSearchActivity.class);
            startActivityForResult(intent, CodeDefinition.REQUEST_SEARCH_START);
        }else if(v.getId() == R.id.saveButton) {
            updateUser();
        }else if(v.getId() == R.id.logoutButton) {
            intent = new Intent(getContext(), StartActivity.class);
            SessionUtils.putString(getContext(),CodeDefinition.ACCESS_TOKEN,"");
            SessionUtils.putString(getContext(),CodeDefinition.USER_ADDRESS,"");
            SessionUtils.putString(getContext(),CodeDefinition.USER_EMAIL,"");
            SessionUtils.putString(getContext(),CodeDefinition.USER_PHONE,"");
            Toast.makeText(getContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            getActivity().setResult(CodeDefinition.LOGOUT);
            getActivity().finish();
        }else if(v.getId() == R.id.signOutButton) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_edit,container,false);
        init();
        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CodeDefinition.REQUEST_SEARCH_START) {
            String address = data.getStringExtra(CodeDefinition.RESPONSE_SEARCH_RESULT);
            mAddressEditText.setText(address);
        }
    }

    @Override
    public void initView() {
        super.initView();
        SessionUtils.putString(getContext(),CodeDefinition.USER_PHONE,mUser.getPhone());
        SessionUtils.putString(getContext(),CodeDefinition.USER_EMAIL,mUser.getEmail());
        SessionUtils.putString(getContext(),CodeDefinition.USER_ADDRESS,mUser.getAddress());

        mPhoneEditText = (EditText) mView.findViewById(R.id.phoneEditText);
        mNameEditText = (EditText) mView.findViewById(R.id.nameEditText);
        mNicknameEditText = (EditText) mView.findViewById(R.id.nicknameEditText);
        mPasswordEditText = (EditText) mView.findViewById(R.id.passwordEditText);
        mEmailEditText = (EditText) mView.findViewById(R.id.emailEditText);
        mBirthDayEditText = (EditText) mView.findViewById(R.id.birthdayEditText);
        mAddressEditText = (EditText) mView.findViewById(R.id.addressEditText);
        mMaleRadioButton = (RadioButton) mView.findViewById(R.id.maleRadioButton);
        mFemaleRadioButton = (RadioButton) mView.findViewById(R.id.femaleRadioButton);
        mNoneRadioButton = (RadioButton) mView.findViewById(R.id.noneRadioButton);
        mEmailSwitch = (Switch) mView.findViewById(R.id.emailSwitch);
        mSmsSwitch = (Switch) mView.findViewById(R.id.smsSwitch);
        mPushSwitch = (Switch) mView.findViewById(R.id.pushSwitch);

        mPhoneEditText.setText(mUser.getPhone());
        mNameEditText.setText(mUser.getName());
        if(mUser.getNickname()!=null)
            mNicknameEditText.setText(mUser.getNickname());
        mEmailEditText.setText(mUser.getEmail());
        if(mUser.getBirthday() !=null)
            mBirthDayEditText.setText(new DateTime(mUser.getBirthday()).toString("yyyyMMdd"));
        if(mUser.getAddress() !=null)
            mAddressEditText.setText(mUser.getAddress());
        if(mUser.getSexType() !=null){
            if(mUser.getSexType() == User.SexType.MALE)
                mMaleRadioButton.setChecked(true);
            else
                mFemaleRadioButton.setChecked(true);
        }
        mEmailSwitch.setChecked(mUser.isEmailReceiveYn());
        mSmsSwitch.setChecked(mUser.isSmsReceiveYn());
        mPushSwitch.setChecked(mUser.isPushReceiveYn());

    }
    public void updateUser(){

        if(!checkValid())
            return;
        mUser.setName(mNameEditText.getText().toString());
        mUser.setNickname(mNicknameEditText.getText().toString());
        mUser.setPhone(mPhoneEditText.getText().toString());
        mUser.setEmail(mEmailEditText.getText().toString());
        mUser.setAddress(mAddressEditText.getText().toString());
        if(mBirthDayEditText.getText().toString().length()>0) {
            DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("yyyyMMdd");
            DateTime time = dateStringFormat.parseDateTime(mBirthDayEditText.getText().toString());
            mUser.setBirthday(time.getMillis());
        }
        mUser.setEmailReceiveYn(mEmailSwitch.isChecked());
        mUser.setSmsReceiveYn(mSmsSwitch.isChecked());
        mUser.setPushReceiveYn(mPushSwitch.isChecked());
        if(mMaleRadioButton.isChecked())
            mUser.setSexType(User.SexType.MALE);
        else if(mFemaleRadioButton.isChecked())
            mUser.setSexType(User.SexType.FEMALE);
        UserController.getInstance(getActivity()).update(mAccessToken, mUser, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200){
                    Toast.makeText(getContext(),"정보가 변경되었습니다.",Toast.LENGTH_SHORT).show();
                    mUser = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
    public void initData(){
        UserController.getInstance(getActivity()).myInfo(mAccessToken, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200) {
                    mUser = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
    @Override
    public void init() {
        super.init();
        initData();

        mView.findViewById(R.id.addressSearchButton).setOnClickListener(this);
        mView.findViewById(R.id.saveButton).setOnClickListener(this);
        mView.findViewById(R.id.logoutButton).setOnClickListener(this);
        mView.findViewById(R.id.signOutButton).setOnClickListener(this);
    }


    private boolean checkValid() {
        String phone = mPhoneEditText.getText().toString();
        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();

        if (phone.length() <= 0) {
            Toast.makeText(getActivity(), "휴대폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.PHONE, phone)) {
            Toast.makeText(getActivity(), "휴대폰 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.length() <= 0) {
            Toast.makeText(getActivity(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.NAME, name)) {
            Toast.makeText(getActivity(), "이름 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.length() <= 0) {
            Toast.makeText(getActivity(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.validCheck(Utils.ValidType.EMAIL, email)) {
            Toast.makeText(getActivity(), "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
