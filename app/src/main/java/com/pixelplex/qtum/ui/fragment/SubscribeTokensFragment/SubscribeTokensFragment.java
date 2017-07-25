package com.pixelplex.qtum.ui.fragment.SubscribeTokensFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pixelplex.qtum.R;
import com.pixelplex.qtum.model.contract.Token;
import com.pixelplex.qtum.ui.FragmentFactory.Factory;
import com.pixelplex.qtum.ui.fragment.BaseFragment.BaseFragment;
import com.pixelplex.qtum.utils.SearchBarListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class SubscribeTokensFragment extends BaseFragment implements SubscribeTokensFragmentView, SearchBarListener {

    private SubscribeTokensFragmentPresenter mSubscribeTokensFragmentPresenter;
    protected TokenAdapter mTokenAdapter;
    private String mSearchString;
    protected List<Token> mCurrentList;

    @BindView(R.id.recycler_view)
    protected
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_currency_title)
    TextView mTextViewCurrencyTitle;

    @BindView(R.id.ll_currency)
    FrameLayout mFrameLayoutBase;

    @OnClick({R.id.ibt_back})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ibt_back:
                getActivity().onBackPressed();
                break;
        }
    }

    public static BaseFragment newInstance(Context context) {
        Bundle args = new Bundle();
        BaseFragment fragment = Factory.instantiateFragment(context, SubscribeTokensFragment.class);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void createPresenter() {
        mSubscribeTokensFragmentPresenter = new SubscribeTokensFragmentPresenter(this);
    }

    @Override
    protected SubscribeTokensFragmentPresenter getPresenter() {
        return mSubscribeTokensFragmentPresenter;
    }

    @Override
    public void initializeViews() {
        super.initializeViews();

        mTextViewCurrencyTitle.setText(R.string.chose_to_subscribe);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFrameLayoutBase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    hideKeyBoard();
            }
        });
    }

    @Override
    public List<Token> getTokenList() {
        return mTokenAdapter.getTokenList();
    }

    @Override
    public void onActivate() {

    }

    @Override
    public void onDeactivate() {
        if(mFrameLayoutBase != null) {
            mFrameLayoutBase.requestFocus();
        }
        hideKeyBoard();
    }

    @Override
    public void onRequestSearch(String filter) {
        if(filter.isEmpty()){
            mTokenAdapter.setFilter(mCurrentList);
        } else {
            mSearchString = filter.toLowerCase();
            List<Token> newList = new ArrayList<>();
            for(Token currency: mCurrentList){
                if(currency.getContractName().toLowerCase().contains(mSearchString))
                    newList.add(currency);
            }

            final int searchStringSize = mSearchString.length();

            Collections.sort(newList, new Comparator<Token>() {
                @Override
                public int compare(Token token, Token token2) {
                    if (token.getContractName().substring(0,searchStringSize).equals(mSearchString) && !token2.getContractName().substring(0,searchStringSize).equals(mSearchString)) {
                        return -1;
                    } else if (!token.getContractName().substring(0,searchStringSize).equals(mSearchString) && token2.getContractName().substring(0,searchStringSize).equals(mSearchString)) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });

            mTokenAdapter.setFilter(newList);
        }
    }

    class TokenHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_single_string)
        TextView mTextViewCurrency;
        @BindView(R.id.iv_check_indicator_blue)
        ImageView mImageViewCheckIndicator;
        @BindView(R.id.ll_single_item)
        LinearLayout mLinearLayoutCurrency;

        Token mToken;

        TokenHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getAdapterPosition()>=0) {
                            mTokenAdapter.getTokenList().get(getAdapterPosition()).setSubscribe(!mToken.isSubscribe());
                            mTokenAdapter.notifyItemChanged(getAdapterPosition());
                        }
                    }
                });
        }

        void bindToken(Token currency){
            mTextViewCurrency.setText(currency.getContractName());
            mToken = currency;
            if(currency.isSubscribe()){
                mImageViewCheckIndicator.setVisibility(View.VISIBLE);
            } else {
                mImageViewCheckIndicator.setVisibility(View.GONE);
            }
        }
    }

    public class TokenAdapter extends RecyclerView.Adapter<TokenHolder>{

        List<Token> mTokenList;
        int resId;

        public TokenAdapter(List<Token> tokenList, int resId){
            mTokenList = tokenList;
            this.resId = resId;
        }

        @Override
        public TokenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(resId, parent, false);
            return new TokenHolder(view);
        }

        @Override
        public void onBindViewHolder(TokenHolder holder, int position) {
            holder.bindToken(mTokenList.get(position));
        }

        @Override
        public int getItemCount() {
            return mTokenList.size();
        }

        void setFilter(List<Token> newList){
            mTokenList = new ArrayList<>();
            mTokenList.addAll(newList);
            notifyDataSetChanged();
        }

        List<Token> getTokenList() {
            return mTokenList;
        }
    }
}