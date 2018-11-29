package com.jmtad.jftt.module.collection;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.CollectionAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.dialog.CommonDialog;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCollectionActivity extends BaseActivity<CollectionPresenter> implements CollectionContract.ICollectionView {
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivBack;
    @BindView(R.id.iv_toolbar_right_button)
    ImageView ivDelete;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.rl_collections)
    LinearLayout llCollections;
    @BindView(R.id.rv_collections)
    RecyclerView rvCollections;
    @BindView(R.id.bt_delete)
    Button btDelete;
    private CollectionAdapter collectionAdapter;
    private List<Banner> mBanners = new ArrayList<>();
    CommonDialog tipDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(MyCollectionActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次到收藏页面重新请求数据
        presenter.queryCollections(Banner.Type.INFO);
    }

    @Override
    protected void initView() {
        tvTitle.setText(getString(R.string.collection_title));
        ivBack.setImageResource(R.drawable.back_black);
        ivDelete.setVisibility(View.INVISIBLE);
        ivDelete.setImageResource(R.drawable.del);
        rvCollections.setLayoutManager(new LinearLayoutManager(MyCollectionActivity.this, LinearLayoutManager.VERTICAL, false));
        rvCollections.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = (int) getResources().getDimension(R.dimen.px_30);
            }
        });
        collectionAdapter = new CollectionAdapter(MyCollectionActivity.this, mBanners);
        rvCollections.setAdapter(collectionAdapter);
        collectionAdapter.setItemListener(new CollectionAdapter.OnItemListener() {
            @Override
            public void onDeleteMode() {
                btDelete.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_favorite;
    }

    @Override
    protected void initPresenter() {
        presenter = new CollectionPresenter();
    }

    @Override
    public void showEmpty() {
        llCollections.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBanners(List<Banner> banners) {
        llEmpty.setVisibility(View.GONE);
        ivDelete.setVisibility(View.VISIBLE);
        llCollections.setVisibility(View.VISIBLE);
        mBanners.clear();
        mBanners.addAll(banners);
        collectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuc(List<Banner> deleteing) {
        collectionAdapter.setDeleteMode(false);
        mBanners.removeAll(deleteing);
        collectionAdapter.notifyDataSetChanged();
        btDelete.setVisibility(View.GONE);

    }

    /**
     * 点击右上方删除按钮，如果处于编辑模式之前执行删除，否则进入编辑模式显示底部删除按钮
     */
    @OnClick(R.id.iv_toolbar_right_button)
    public void chooseDelete() {
        if (collectionAdapter.isDeleteMode()) {
            delete();
        } else {
            collectionAdapter.setDeleteMode(true);
            collectionAdapter.notifyDataSetChanged();
            btDelete.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.bt_delete)
    public void delete() {
        //弹出提示框
        if (null == tipDialog) {
            tipDialog = new CommonDialog(MyCollectionActivity.this, R.style.BaseDialog, getString(R.string.collection_delete_tips), new CommonDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        //点击删除按钮删除选择的图文刷新列表，然后退出编辑状态
                        List<Banner> deletingBanners = collectionAdapter.getDeletingBanners();
                        presenter.deleteCollections(deletingBanners);

                    } else {
                        //点击取消直接退出编辑状态
                        collectionAdapter.setDeleteMode(false);
                        collectionAdapter.notifyDataSetChanged();
                    }
                    dialog.dismiss();
                }
            }).setTitle(getString(R.string.tips))
                    .setNegativeButton(getString(R.string.cancel))
                    .setPositiveButton(getString(R.string.delete))
                    .setButtonBgId(R.drawable.profile_radio_bt_bg_normal, R.drawable.setting_bt_switch_account)
                    .setCancelBtVisible()
                    .setButtonTextColorId(R.color.black_textColor, R.color.white);
        }
        tipDialog.show();


    }
}
