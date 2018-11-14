package com.jmtad.jftt.module.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.DealQueryVPAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.http.bean.node.OilRecord;
import com.jmtad.jftt.module.mine.contract.DealContract;
import com.jmtad.jftt.module.mine.presenter.DealPresenter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 交易查询页面
 */
public class DealQueryActivity extends BaseActivity<DealPresenter> implements DealContract.IDealView {

    @BindView(R.id.vp_deal_query_cotent)
    ViewPager viewPager;
    private DealQueryVPAdapter adapter;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tl_deal_query_tab)
    TabLayout tabLayout;
    //所有交易记录
    private RecordFragment allFragment;
    //收入
    private RecordFragment incomeFragment;
    //支出
    private RecordFragment expendFragment;
    private List<RecordFragment> fragments = new ArrayList<>();
    private int selectedYear, selectedMonth;
    @BindView(R.id.tv_oil_remain)
    TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        Calendar now = Calendar.getInstance();
        selectedYear = now.get(Calendar.YEAR);
        selectedMonth = now.get(Calendar.MONTH) + 1;
        String date = selectedYear + "-" + selectedMonth;
        tvDate.setText(date);

        //初始化fragment
        allFragment = new RecordFragment();
        Bundle all = new Bundle();
        all.putString(RecordFragment.KEY_TYPE, RecordFragment.TYPE_ALL);
        allFragment.setArguments(all);

        incomeFragment = new RecordFragment();
        Bundle income = new Bundle();
        income.putString(RecordFragment.KEY_TYPE, RecordFragment.TYPE_INCOME);
        incomeFragment.setArguments(income);

        expendFragment = new RecordFragment();
        Bundle expend = new Bundle();
        expend.putString(RecordFragment.KEY_TYPE, RecordFragment.TYPE_EXPEND);
        expendFragment.setArguments(expend);
        fragments.add(allFragment);
        fragments.add(incomeFragment);
        fragments.add(expendFragment);
        adapter = new DealQueryVPAdapter(this, fragments, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deal_query;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.queryOilBalance();
    }

    @Override
    protected void initPresenter() {
        presenter = new DealPresenter();
    }

    @OnClick(R.id.ll_date_select)
    public void selectDate() {
        DatePicker picker = new DatePicker(this, cn.qqtheme.framework.picker.DatePicker.YEAR_MONTH);
        picker.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        //设置统一颜色
        picker.setTextColor(getResources().getColor(R.color.red_1));
        picker.setDividerColor(getResources().getColor(R.color.red_1));
        picker.setCancelTextColor(getResources().getColor(R.color.red_1));
        picker.setTopLineColor(getResources().getColor(R.color.red_1));
        picker.setSubmitTextColor(getResources().getColor(R.color.red_1));
        picker.setLabelTextColor(getResources().getColor(R.color.red_1));
        picker.setLineSpaceMultiplier(3.0f);
        picker.setSubmitText(R.string.sure);
        picker.setCancelText(getString(R.string.cancel));
        picker.setSize((int) (picker.getScreenWidthPixels() * 0.8), (int) (picker.getScreenHeightPixels() * 0.6));
//        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
//        picker.setHeight((int) (picker.getScreenHeightPixels() * 0.6));
        picker.setRangeStart(2015, 10, 14);
        picker.setRangeEnd(2099, 11, 11);
        picker.setSelectedItem(selectedYear, selectedMonth);
        picker.setOnDatePickListener((DatePicker.OnYearMonthPickListener) (year, month) -> {
            selectedYear = Integer.valueOf(year);
            selectedMonth = Integer.valueOf(month);
            String date = year + "-" + month;
            tvDate.setText(date);
            fragments.get(viewPager.getCurrentItem()).loadByDate(date);

        });
        picker.show();
    }

    @Override
    public void loadDealRecords(List<OilRecord> records) {

    }

    @OnClick({R.id.iv_mine_back, R.id.tv_title})
    public void back() {
        finish();
    }

    @Override
    public void showBalance(String balance) {
        NumberFormat format = new DecimalFormat("#,###");
        double bal = Double.valueOf(balance);
        tvBalance.setText(format.format(bal));
    }

}
