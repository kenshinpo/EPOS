package com.pochih.mokapos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pochih.mokapos.AppApplication;
import com.pochih.mokapos.R;
import com.pochih.mokapos.adapter.DiscountAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by A-Po on 2018/01/04.
 */

public class DiscountListFragment extends Fragment {

    @BindView(R.id.rvDiscount)
    RecyclerView rvDiscount;

    private DiscountAdapter adapter;

    public DiscountListFragment() {
    }

    public static DiscountListFragment newInstance() {
        DiscountListFragment fragment = new DiscountListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        try {
            ButterKnife.bind(this, view);
            //region Setup RecyclerView UI
            rvDiscount.setLayoutManager(new LinearLayoutManager(getContext()));
            //endregion
        } catch (Exception e) {
            Timber.e(e);
        }
        return view;
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            adapter = new DiscountAdapter(getContext(), AppApplication.instance.getDiscounts());
            rvDiscount.setAdapter(adapter);
        } catch (Exception ex) {
            Timber.e(ex);
        }
    }

    @OnClick(R.id.ivBack)
    void goBack() {
        try {

            getFragmentManager().beginTransaction()
                    .replace(R.id.flLeft, LibraryFragment.newInstance())
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
