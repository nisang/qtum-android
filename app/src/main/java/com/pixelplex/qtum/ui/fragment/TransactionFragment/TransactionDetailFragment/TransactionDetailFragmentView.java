package com.pixelplex.qtum.ui.fragment.TransactionFragment.TransactionDetailFragment;

import com.pixelplex.qtum.model.gson.history.TransactionInfo;

import java.util.List;


interface TransactionDetailFragmentView {
    void setUpRecyclerView(List<TransactionInfo> transactionInfoList);
}
