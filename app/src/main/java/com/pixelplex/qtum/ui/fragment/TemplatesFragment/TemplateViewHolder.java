package com.pixelplex.qtum.ui.fragment.TemplatesFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.pixelplex.qtum.R;
import com.pixelplex.qtum.model.ContractTemplate;
import com.pixelplex.qtum.utils.DateCalculator;
import com.pixelplex.qtum.utils.FontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TemplateViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    FontTextView title;

    @BindView(R.id.date)
    FontTextView date;

    @BindView(R.id.contract_type)
    FontTextView contractType;

    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    private ContractTemplate mContractTemplate;

    public TemplateViewHolder(View itemView, final TemplateSelectListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectContract(mContractTemplate);
            }
        });
    }

    public void bind(ContractTemplate contractTemplate) {

        mContractTemplate = contractTemplate;

        title.setText(contractTemplate.getName());

        date.setText(DateCalculator.getShortDate(contractTemplate.getDate()));

        contractType.setText(contractTemplate.getContractType().toUpperCase());
    }
}
