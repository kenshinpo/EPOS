package com.pochih.mokapos.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.pochih.mokapos.AppApplication;
import com.pochih.mokapos.R;
import com.pochih.mokapos.adapter.ItemAdapter;
import com.pochih.mokapos.entity.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by A-Po on 2018/01/02.
 */

public class ItemListFragment extends Fragment {

    private static final int PRICE_RANDOM_MIN = 10;
    private static final int PRICE_RANDOM_MAX = 99;

    @BindView(R.id.rvItem)
    RecyclerView rvItem;

    private ProgressDialog mDialog;
    private List<Item> items = new ArrayList<>();
    private ItemAdapter adapter;

    public ItemListFragment() {
        // Required empty public constructor
    }

    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_items, container, false);
        try {
            ButterKnife.bind(this, view);

            //region Setup ProgressDialog
            mDialog = new ProgressDialog(getContext());
            mDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            mDialog.setCancelable(false);
            mDialog.setTitle(getString(R.string.text_Loading));
            mDialog.setMessage(getString(R.string.text_Wait_while_loading));
            //endregion

            //region Setup RecyclerView UI
            rvItem.setLayoutManager(new LinearLayoutManager(getContext()));
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

            adapter = new ItemAdapter(getContext(), AppApplication.instance.getItems());
            rvItem.setAdapter(adapter);

//            //region Http call
//            mDialog.show();
//            Call<List<Item>> call = AppApplication.httpService.getItems();
//            call.enqueue(new Callback<List<Item>>() {
//
//                @Override
//                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
//                    //region set price
//                    for (int i = 0; i < response.body().size(); i++) {
//                        int price = response.body().get(i).getId() * ((int) (Math.random() * (PRICE_RANDOM_MAX - PRICE_RANDOM_MIN + 1)) + PRICE_RANDOM_MIN);
//                        response.body().get(i).setPrice(price);
//                    }
//                    //endregion
//                    if (items == null) {
//                        items = new ArrayList();
//                    }
//                    items.clear();
//                    items.addAll(response.body());
//                    adapter = new ItemAdapter(getContext(), items);
//                    rvItem.setAdapter(adapter);
//                    mDialog.dismiss();
//                }
//
//                @Override
//                public void onFailure(Call<List<Item>> call, Throwable t) {
//                    Timber.e(t);
//                    mDialog.dismiss();
//                    Toast.makeText(getContext(), getString(R.string.text_Please_check_your_network_connection), Toast.LENGTH_LONG).show();
//                }
//            });
//            //endregion
        } catch (Exception ex) {
            Timber.e(ex);
            mDialog.dismiss();
            Toast.makeText(getContext(), getString(R.string.text_Please_check_your_network_connection), Toast.LENGTH_LONG).show();
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
