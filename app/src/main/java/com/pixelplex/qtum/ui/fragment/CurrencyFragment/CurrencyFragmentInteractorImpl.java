package com.pixelplex.qtum.ui.fragment.CurrencyFragment;


import android.content.Context;

import com.pixelplex.qtum.dataprovider.RestAPI.gsonmodels.ContractInfo;
import com.pixelplex.qtum.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

class CurrencyFragmentInteractorImpl implements CurrencyFragmentInteractor{

    Context mContext;

    public CurrencyFragmentInteractorImpl(Context context){
        mContext = context;
    }

    @Override
    public List<ContractInfo> getTokenList() {
        List<ContractInfo> contractInfoList = (new TinyDB(mContext)).getListContractInfo();
        return contractInfoList;
    }
}
