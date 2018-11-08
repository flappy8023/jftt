package com.jmtad.jftt.module.banner;

import com.github.chrisbanes.photoview.PhotoView;
import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.util.GlideUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-31 14:25
 **/
public class PictureActivity extends BaseActivity {
    public static final String KEY_IMG = "img";
    @BindView(R.id.phototview)
    PhotoView photoView;

    @Override
    protected void initView() {
        if (null != getIntent()) {
            String img = getIntent().getStringExtra(KEY_IMG);
            GlideUtil.loadImage(this, img, photoView);
        }
    }

    @OnClick(R.id.phototview)
    public void close() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic;
    }

    @Override
    protected void initPresenter() {

    }
}
