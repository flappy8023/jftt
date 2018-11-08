package com.jmtad.jftt.module.mine;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.MessageAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.http.bean.node.Message;
import com.jmtad.jftt.module.mine.contract.MsgContract;
import com.jmtad.jftt.module.mine.presenter.MsgPresenter;
import com.jmtad.jftt.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SysMessageActivity extends BaseActivity<MsgPresenter> implements MsgContract.IMsgView {

    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.rv_message_content)
    RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> mMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        presenter.queryMsg();
    }

    @Override
    protected void initView() {
        ivBack.setImageResource(R.drawable.back_black);
        tvTitle.setText(getString(R.string.menu_my_message));
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        messageAdapter = new MessageAdapter(this, mMessages);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.addItemDecoration(new MessageDecoration());
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sys_message;
    }

    @Override
    protected void initPresenter() {
        presenter = new MsgPresenter();
    }

    @Override
    public void showMsgs(List<Message> messages) {
        mMessages.addAll(messages);
        messageAdapter.notifyDataSetChanged();
    }

    class MessageDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position > 0) {
                outRect.top = (int) getResources().getDimension(R.dimen.px_30);
            }
        }
    }
}
