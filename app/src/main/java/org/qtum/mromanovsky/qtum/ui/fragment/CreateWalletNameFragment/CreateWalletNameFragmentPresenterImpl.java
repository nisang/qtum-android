package org.qtum.mromanovsky.qtum.ui.fragment.CreateWalletNameFragment;

import android.content.Context;

import org.qtum.mromanovsky.qtum.R;
import org.qtum.mromanovsky.qtum.datastorage.QtumSharedPreference;
import org.qtum.mromanovsky.qtum.ui.fragment.BaseFragment.BaseFragmentPresenterImpl;
import org.qtum.mromanovsky.qtum.ui.fragment.PinFragment.PinFragment;


public class CreateWalletNameFragmentPresenterImpl extends BaseFragmentPresenterImpl implements CreateWalletNameFragmentPresenter {

    private CreateWalletNameFragmentView mCreateWalletNameFragmentView;

    public CreateWalletNameFragmentPresenterImpl(CreateWalletNameFragmentView createWalletNameFragmentView) {
        mCreateWalletNameFragmentView = createWalletNameFragmentView;
    }

    @Override
    public void confirm(String name) {
        if (name.isEmpty()) {
            getView().incorrectName(getView().getContext().getString(R.string.empty_name));
        } else {
            QtumSharedPreference.getInstance().saveWalletName(getView().getContext(), name);
            PinFragment pinFragment = PinFragment.newInstance(PinFragment.CREATING);
            getView().clearError();
            getView().openFragmentWithOutPopBackStack(pinFragment);
        }
    }

    @Override
    public void cancel() {
        getView().getFragmentActivity().onBackPressed();
    }

    @Override
    public CreateWalletNameFragmentView getView() {
        return mCreateWalletNameFragmentView;
    }

    @Override
    public void onPause(Context context) {
        super.onPause(context);
        getView().hideKeyBoard();
    }
}
