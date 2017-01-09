package org.qtum.mromanovsky.qtum.ui.fragment.StartPageFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.qtum.mromanovsky.qtum.R;
import org.qtum.mromanovsky.qtum.ui.fragment.BaseFragment.BaseFragment;
import org.qtum.mromanovsky.qtum.ui.fragment.BaseFragment.BaseFragmentPresenterImpl;
import org.qtum.mromanovsky.qtum.ui.fragment.CreateWalletNameFragment.CreateWalletNameFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class StartPageFragment extends BaseFragment implements StartPageFragmentView{

    public static final int LAYOUT = R.layout.fragment_start_page;
    public static final String TAG = "StartPageFragment";

    private StartPageFragmentPresenterImpl mStartPageFragmentPresenter;


    @BindView(R.id.bt_create_new) Button mButtonCreateNew;
    @BindView(R.id.bt_import_wallet) Button mButtonImportWallet;

    @OnClick({R.id.bt_import_wallet,R.id.bt_create_new})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.bt_create_new:
                getPresenter().createNewWallet();
                break;
            case R.id.bt_import_wallet:
                getPresenter().importWallet();
                break;
        }
    }

    public static StartPageFragment newInstance(){
        StartPageFragment startPageFragment = new StartPageFragment();
        return startPageFragment;
    }

    @Override
    protected void createPresenter() {
        mStartPageFragmentPresenter = new StartPageFragmentPresenterImpl(this);
    }

    @Override
    protected StartPageFragmentPresenterImpl getPresenter() {
        return mStartPageFragmentPresenter;
    }

    @Override
    protected int getLayout() {
        return LAYOUT;
    }

    @Override
    public void initializeViews() {

    }
}
