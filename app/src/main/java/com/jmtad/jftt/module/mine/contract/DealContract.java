package com.jmtad.jftt.module.mine.contract;

import com.jmtad.jftt.base.IBaseContract;
import com.jmtad.jftt.http.bean.node.OilRecord;

import java.util.List;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-25 18:26
 **/
public interface DealContract {
    interface IDealView extends IBaseContract.IBaseView {
        void loadDealRecords(List<OilRecord> records);

        void showBalance(String balance);
    }

    interface IDealPresenter extends IBaseContract.IBasePresenter {
        void queryDealRecord(int pageNo, int pageSize, String type, String date);

        void queryOilBalance();
    }
}
