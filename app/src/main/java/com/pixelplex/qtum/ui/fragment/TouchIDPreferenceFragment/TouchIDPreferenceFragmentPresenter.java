package com.pixelplex.qtum.ui.fragment.TouchIDPreferenceFragment;


import com.pixelplex.qtum.datastorage.QtumSharedPreference;
import com.pixelplex.qtum.ui.fragment.BackUpWalletFragment.BackUpWalletFragment;
import com.pixelplex.qtum.ui.fragment.BaseFragment.BaseFragment;
import com.pixelplex.qtum.ui.fragment.BaseFragment.BaseFragmentPresenterImpl;
import com.pixelplex.qtum.ui.fragment.WalletMainFragment.WalletMainFragment;

class TouchIDPreferenceFragmentPresenter extends BaseFragmentPresenterImpl{

    private TouchIDPreferenceFragmentView mTouchIDPreferenceFragmentView;

    TouchIDPreferenceFragmentPresenter(TouchIDPreferenceFragmentView touchIDPreferenceFragmentView){
        mTouchIDPreferenceFragmentView = touchIDPreferenceFragmentView;
    }

    void onNextScreen(boolean isImporting){
        if(isImporting){
            WalletMainFragment walletFragment = WalletMainFragment.newInstance(getView().getContext());
            getView().getMainActivity().setRootFragment(walletFragment);
            getView().openRootFragment(walletFragment);
        }else{
            BaseFragment backUpWalletFragment = BackUpWalletFragment.newInstance(getView().getContext(), true, getView().getPin());
            getView().openFragment(backUpWalletFragment);
        }
    }

    void onEnableTouchIdClick(){
        QtumSharedPreference.getInstance().saveTouchIdEnable(getView().getContext(), true);
    }

    @Override
    public TouchIDPreferenceFragmentView getView() {
        return mTouchIDPreferenceFragmentView;
    }
}
