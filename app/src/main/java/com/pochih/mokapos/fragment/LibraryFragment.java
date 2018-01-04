package com.pochih.mokapos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pochih.mokapos.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LibraryFragment extends Fragment {

    public LibraryFragment() {
    }

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        try {
            ButterKnife.bind(this, view);
        } catch (Exception e) {
            Timber.e(e);
        }
        return view;
    }

    @OnClick(R.id.llAllDiscounts)
    void goToAllDiscounts() {
        try {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flLeft, DiscountListFragment.newInstance())
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.llAllItems)
    void goToAllItems() {
        try {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flLeft, ItemListFragment.newInstance())
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
