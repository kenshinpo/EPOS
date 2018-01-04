package com.pochih.mokapos.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.pochih.mokapos.AppApplication;
import com.pochih.mokapos.R;
import com.pochih.mokapos.database.model.ItemDAO;
import com.pochih.mokapos.entity.Item;
import com.pochih.mokapos.fragment.LibraryFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int PRICE_RANDOM_MIN = 10;
    private static final int PRICE_RANDOM_MAX = 99;

    private ProgressDialog mDialog;

    private ItemDAO itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //region Setup ProgressDialog
            mDialog = new ProgressDialog(this);
            mDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            mDialog.setCancelable(false);
            mDialog.setTitle(getString(R.string.text_Loading));
            mDialog.setMessage(getString(R.string.text_Wait_while_loading));
            //endregion

            itemDAO = new ItemDAO(this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flLeft, LibraryFragment.newInstance(), "flLeft")
                    .commit();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            mDialog.show();

            if (itemDAO.getCount() == 0) {
                //region Get data from Http call
                Timber.d("Get data from Http call");
                Call<List<Item>> call = AppApplication.httpService.getItems();
                call.enqueue(new Callback<List<Item>>() {

                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        for (int i = 0; i < response.body().size(); i++) {
                            int price = response.body().get(i).getId() * ((int) (Math.random() * (PRICE_RANDOM_MAX - PRICE_RANDOM_MIN + 1)) + PRICE_RANDOM_MIN);
                            response.body().get(i).setPrice(price);
                            //region save to SQLite
                            ItemDAO itemDAO = new ItemDAO(getApplicationContext());
                            itemDAO.insert(response.body().get(i));
                            //endregion
                        }
                        AppApplication.instance.setItems(response.body());
                        mDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                        Timber.e(t);
                        mDialog.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.text_Please_check_your_network_connection), Toast.LENGTH_LONG).show();
                    }
                });
                //endregion
            } else {
                //region Get data from SQLite
                Timber.d("Get data from SQLite");
                List<Item> items = itemDAO.getAll();
                AppApplication.instance.setItems(items);
                mDialog.dismiss();
                //endregion
            }

        } catch (Exception e) {
            Timber.e(e);
            mDialog.dismiss();
        }
    }
}
