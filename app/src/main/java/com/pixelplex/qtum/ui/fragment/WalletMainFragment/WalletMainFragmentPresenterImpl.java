package com.pixelplex.qtum.ui.fragment.WalletMainFragment;
import android.content.Context;
import android.util.Log;

import com.pixelplex.qtum.dataprovider.UpdateService;
import com.pixelplex.qtum.dataprovider.listeners.TokenListener;
import com.pixelplex.qtum.model.contract.Token;
import com.pixelplex.qtum.ui.activity.main_activity.MainActivity;
import com.pixelplex.qtum.ui.fragment.BaseFragment.BaseFragmentPresenterImpl;
import com.pixelplex.qtum.datastorage.TinyDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


class WalletMainFragmentPresenterImpl extends BaseFragmentPresenterImpl {

    private WalletMainFragmentInteractorImpl mWalletMainFragmentInteractor;
    private WalletMainFragmentView mWalletMainFragmentView;
    private UpdateService mUpdateService;

    WalletMainFragmentPresenterImpl(WalletMainFragmentView walletFragmentView) {
        mWalletMainFragmentView = walletFragmentView;
        mWalletMainFragmentInteractor = new WalletMainFragmentInteractorImpl();
    }

    @Override
    public WalletMainFragmentView getView() {
        return mWalletMainFragmentView;
    }

    @Override
    public void onResume(Context context) {
        super.onResume(context);
        getView().getMainActivity().subscribeServiceConnectionChangeEvent(new MainActivity.OnServiceConnectionChangeListener() {
            @Override
            public void onServiceConnectionChange(boolean isConnecting) {
                if(isConnecting) {
                    mUpdateService = getView().getMainActivity().getUpdateService();
                    mUpdateService.addTokenListener(new TokenListener() {
                        @Override
                        public void newToken() {
                            checkOtherTokens();
                        }
                    });
                }
            }
        });
        checkOtherTokens();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUpdateService.removeTokenListener();
    }

    private void checkOtherTokens() {
        getTokens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Token>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TOKENS ERROR", "---------------- "+e.getMessage());
                    }

                    @Override
                    public void onNext(List<Token> tokens) {
                        getView().showOtherTokens(tokens != null && tokens.size() > 0);
                    }
                });
    }

    private Observable<List<Token>> getTokens() {
        return Observable.fromCallable(new Callable<List<Token>>() {
            @Override
            public List<Token> call() throws Exception {
                TinyDB tinyDB = new TinyDB(getView().getContext());
                List<Token> tokenList = tinyDB.getTokenList();
                List<Token> tokens = new ArrayList<>();

                for (Token token: tokenList) {
                    if(token.isHasBeenCreated() && token.isSubscribe()){
                        tokens.add(token);
                    }
                }
                tokenList.clear();
                return tokens;
            }
        });
    }
}