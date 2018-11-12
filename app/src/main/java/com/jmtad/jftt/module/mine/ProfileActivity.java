package com.jmtad.jftt.module.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.CircleImageView;
import com.jmtad.jftt.http.bean.node.User;
import com.jmtad.jftt.module.mine.bean.Education;
import com.jmtad.jftt.module.mine.contract.ProfileContract;
import com.jmtad.jftt.module.mine.data.JobProvider;
import com.jmtad.jftt.module.mine.presenter.ProfilePresenter;
import com.jmtad.jftt.util.GlideUtil;
import com.jmtad.jftt.util.JsonParse;
import com.jmtad.jftt.util.StatusBarUtil;
import com.jmtad.jftt.wechat.model.WXUserInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class ProfileActivity extends BaseActivity<ProfilePresenter> implements ProfileContract.IProfileView {

    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.tv_profile_city)
    TextView tvCity;
    @BindView(R.id.tv_profile_edu)
    TextView tvEdu;
    @BindView(R.id.tv_profile_job)
    TextView tvJob;
    @BindView(R.id.iv_profile_headImg)
    CircleImageView ivHead;
    @BindView(R.id.et_profile_nickName)
    EditText etNickName;
    @BindView(R.id.rg_gender)
    RadioGroup groupGender;
    @BindView(R.id.tv_profile_introduction)
    EditText etPersonalPro;
    private String headImg = "";
    /**
     * 学历信息
     */
    private List<Education> eduData = null;
    /**
     * 城市信息
     */
    private ArrayList<Province> cityData = null;

    /**
     * 职业信息
     */
    private LinkagePicker.DataProvider jobProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        presenter.queryUserInfo();
    }

    @Override
    protected void initView() {
        ivBack.setImageResource(R.drawable.back_black);
        tvTitle.setText(getString(R.string.profile_title));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initPresenter() {
        presenter = new ProfilePresenter();
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }

    @OnClick(R.id.tv_profile_city)
    public void cityPick() {
        try {
            if (null == cityData) {
                cityData = new ArrayList<>();
                String json = ConvertUtils.toString(getAssets().open("city.json"));
                cityData.addAll(JsonParse.jsonToClassList(json, Province.class));
            }
            AddressPicker picker = new AddressPicker(this, cityData);
            //设置统一颜色
            picker.setTextColor(getResources().getColor(R.color.red_1));
            picker.setDividerColor(getResources().getColor(R.color.red_1));
            picker.setCancelTextColor(getResources().getColor(R.color.red_1));
            picker.setTopLineColor(getResources().getColor(R.color.red_1));
            picker.setSubmitTextColor(getResources().getColor(R.color.red_1));
            picker.setLineSpaceMultiplier(3.0f);
            picker.setSubmitText(R.string.sure);
            picker.setCancelText(getString(R.string.cancel));
            picker.setShadowVisible(true);
            picker.setTextSizeAutoFit(true);
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    String provinceStr = "";
                    String cityStr = "";
                    String countryStr = "";
                    if (null != province) {
                        provinceStr = province.getAreaName();
                    }
                    if (null != city) {
                        cityStr = city.getAreaName();
                    }
                    if (null != county) {
                        countryStr = county.getAreaName();
                    }
                    tvCity.setText(provinceStr + cityStr + countryStr);
                }
            });
            picker.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.bt_profile_submit)
    public void submit() {
        String nickName = etNickName.getText().toString();
        String gender = "";
        if (groupGender.getCheckedRadioButtonId() == R.id.rb_gender_male) {
            gender = WXUserInfo.SEX_MALE;
        } else {
            gender = WXUserInfo.SEX_FEMALE;
        }
        String city = tvCity.getText().toString();
        String profession = tvJob.getText().toString();
        String edu = tvEdu.getText().toString();
        String personalPro = etPersonalPro.getText().toString();
        presenter.submitUserInfo(nickName, headImg, city, gender, profession, personalPro, edu);
    }

    @OnClick(R.id.tv_profile_job)
    public void jobPick() {
        if (null == jobProvider) {
            jobProvider = new JobProvider();
        }
        LinkagePicker picker = new LinkagePicker(this, jobProvider);
        picker.setLineSpaceMultiplier(3.0f);
        //设置统一颜色
        picker.setTextColor(getResources().getColor(R.color.red_1));
        picker.setDividerColor(getResources().getColor(R.color.red_1));
        picker.setCancelTextColor(getResources().getColor(R.color.red_1));
        picker.setTopLineColor(getResources().getColor(R.color.red_1));
        picker.setSubmitTextColor(getResources().getColor(R.color.red_1));
        picker.setCycleDisable(true);
        picker.setSubmitText(R.string.sure);
        picker.setCancelText(getString(R.string.cancel));
        picker.setOnStringPickListener(new LinkagePicker.OnStringPickListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                tvJob.setText(second);
            }
        });
        picker.setSubmitText(getString(R.string.sure));
        picker.show();
    }

    @OnClick(R.id.tv_profile_edu)
    public void eduPick() {
        //初始化学历信息
        if (null == eduData) {
            eduData = new ArrayList<>();
            eduData.add(Education.XIAOXUE);
            eduData.add(Education.CHUZHONG);
            eduData.add(Education.GAOZHONG);
            eduData.add(Education.ZHONGZHUAN);
            eduData.add(Education.ZHIXIAO);
            eduData.add(Education.ZHONGJI);
            eduData.add(Education.ZHUANKE);
            eduData.add(Education.BENKE);
            eduData.add(Education.SHUOSHI);
            eduData.add(Education.BOSHI);
            eduData.add(Education.OTHER);
        }
        SinglePicker<Education> picker = new SinglePicker<>(this, eduData);
        //设置统一颜色
        picker.setTextColor(getResources().getColor(R.color.red_1));
        picker.setDividerColor(getResources().getColor(R.color.red_1));
        picker.setCancelTextColor(getResources().getColor(R.color.red_1));
        picker.setTopLineColor(getResources().getColor(R.color.red_1));
        picker.setSubmitTextColor(getResources().getColor(R.color.red_1));
        picker.setLineSpaceMultiplier(3.0f);
        picker.setCancelText(R.string.cancel);
        picker.setSubmitText(R.string.sure);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener((index, item) -> tvEdu.setText(item.getName()));
        picker.show();
    }

    @Override
    public void showUserInfo(User user) {
        //判空
        if (null == user) {
            return;
        }
        headImg = user.getHeadImgUrl();
        if (null != user.getHeadImgUrl()) {
            GlideUtil.loadImage(this, user.getHeadImgUrl(), ivHead);
        }
        if (null != user.getNickName()) {
            try {
                String name = URLDecoder.decode(user.getNickName(), "UTF-8");
                etNickName.setText(name);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (null != user.getSex()) {
            //1男性 2女性
            if (TextUtils.equals(WXUserInfo.SEX_MALE, user.getSex())) {
                groupGender.check(R.id.rb_gender_male);
            } else {
                groupGender.check(R.id.rb_gender_female);
            }
        }
        if (null != user.getCity()) {
            tvCity.setText(user.getCity());
        }
        if (null != user.getProfession()) {
            tvJob.setText(user.getProfession());
        }
        if (null != user.getEducation()) {
            tvEdu.setText(user.getEducation());
        }
        if (null != user.getPersonalProfile()) {
            etPersonalPro.setText(user.getPersonalProfile());
        }
    }

    @Override
    public void updateUserSucc() {
        showMsg("修改成功");
    }
}
