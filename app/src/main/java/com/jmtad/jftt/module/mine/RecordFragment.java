package com.jmtad.jftt.module.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.DealQueryRVAdapter;
import com.jmtad.jftt.base.BaseMvpFragment;
import com.jmtad.jftt.http.bean.node.OilRecord;
import com.jmtad.jftt.module.mine.contract.DealContract;
import com.jmtad.jftt.module.mine.presenter.DealPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * @description:交易记录内容
 * @author: luweiming
 * @create: 2018-10-25 19:14
 **/
public class RecordFragment extends BaseMvpFragment<DealPresenter> implements DealContract.IDealView {
    public static final String TYPE_ALL = "0";
    public static final String TYPE_INCOME = "1";
    public static final String TYPE_EXPEND = "2";
    public static final String KEY_TYPE = "type";
    @BindView(R.id.rv_record)
    RecyclerView recyclerView;

    private DealQueryRVAdapter adapter;
    private List<OilRecord> mRecords = new ArrayList<>();
    //屏幕当前最后一个可见item的位置
    private int lastVisiablePosition = 0;
    private LinearLayoutManager layoutManager;
    private int pageNo = 1;
    private static final int PAGE_SIZE = 20;
    private String mDate = "";
    /**
     * 交易类型 0 全部| 1 收入| 2 支出
     */
    private String type = "0";

    @Override
    protected void initView(View view) {
        if (null != getArguments()) {
            type = getArguments().getString(KEY_TYPE);
        }
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DealQueryRVAdapter(getActivity(), mRecords);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
        Calendar now = Calendar.getInstance();
        //第一次初始化展示当前月份的交易记录
        String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1);
        loadByDate(date);
    }


    public void loadByDate(String date) {
        //如果此次选择的日期与上次一致,不再请求,否则重新请求
        if (TextUtils.equals(mDate, date)) {
            return;
        }
        mRecords.clear();
        pageNo = 1;
        presenter.queryDealRecord(pageNo, PAGE_SIZE, type, date);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
            // 滑动状态停止并且剩余少于5个item时，自动加载下一页
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisiablePosition + 5 >= layoutManager.getItemCount()) {
                pageNo++;
                presenter.queryDealRecord(pageNo, PAGE_SIZE, type, mDate);
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //获取加载的最后一个可见视图在适配器的位置
            lastVisiablePosition = layoutManager.findLastVisibleItemPosition();
        }
    };

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_record_layout;
    }

    @Override
    protected void initPresenter() {
        presenter = new DealPresenter();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void loadDealRecords(List<OilRecord> records) {
        mRecords.addAll(records);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showBalance(String balance) {
    }
}
