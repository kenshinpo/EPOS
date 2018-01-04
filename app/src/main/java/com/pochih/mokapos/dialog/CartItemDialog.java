package com.pochih.mokapos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.pochih.mokapos.AppApplication;
import com.pochih.mokapos.R;
import com.pochih.mokapos.entity.CartItem;
import com.pochih.mokapos.fragment.ShoppingFragment;
import com.pochih.mokapos.utility.InputFilterQuantity;

import timber.log.Timber;

/**
 * Created by A-Po on 2018/01/03.
 */

public class CartItemDialog implements DialogInterface.OnCancelListener, View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private CartItem cartItem;
    private boolean isUpdating;

    private TextView tvCancel, tvSave, tvTitle;
    private EditText etQuantity;
    private Button btnAddQuantity, btnReduceQuantity;
    private Switch swDiscountA, swDiscountB, swDiscountC, swDiscountD, swDiscountE;

    public CartItemDialog(Context context, CartItem cartItem, boolean isUpdating) {
        this.context = context;
        this.cartItem = cartItem;
        this.isUpdating = isUpdating;
    }

    public CartItemDialog show() {
        try {
            dialog = new Dialog(context, R.style.DialogFullscreen);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(this);

            //region Views setting
            tvCancel = dialog.findViewById(R.id.tvCancel);
            tvTitle = dialog.findViewById(R.id.tvTitle);
            tvSave = dialog.findViewById(R.id.tvSave);
            etQuantity = dialog.findViewById(R.id.etQuantity);
            btnAddQuantity = dialog.findViewById(R.id.btnAddQuantity);
            btnReduceQuantity = dialog.findViewById(R.id.btnReduceQuantity);
            swDiscountA = dialog.findViewById(R.id.swDiscountA);
            swDiscountB = dialog.findViewById(R.id.swDiscountB);
            swDiscountC = dialog.findViewById(R.id.swDiscountC);
            swDiscountD = dialog.findViewById(R.id.swDiscountD);
            swDiscountE = dialog.findViewById(R.id.swDiscountE);

            tvTitle.setText(cartItem.getTitle() + " - $" + cartItem.getPrice());
            etQuantity.setText(String.valueOf(cartItem.getQuantity()));
            turnOffAllSwitch();
            if (cartItem.getDiscount() == 0.0) {
                swDiscountA.setChecked(true);
            } else if (cartItem.getDiscount() == 10.0) {
                swDiscountB.setChecked(true);
            } else if (cartItem.getDiscount() == 35.5) {
                swDiscountC.setChecked(true);
            } else if (cartItem.getDiscount() == 50.0) {
                swDiscountD.setChecked(true);
            } else if (cartItem.getDiscount() == 100.0) {
                swDiscountE.setChecked(true);
            } else {
                // do nothing
            }

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            tvSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int quantity = Integer.valueOf(etQuantity.getText().toString().trim());
                        cartItem.setQuantity(quantity);
                        AppApplication.setCartItem(cartItem, isUpdating);
                        Intent intent = new Intent();
                        intent.setAction(ShoppingFragment.class.getPackage().getName());
                        context.sendBroadcast(intent);
                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        Timber.e(e);
                    } catch (Exception ex) {
                        Timber.e(ex);
                    }
                }
            });

            btnAddQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartItem.getQuantity() < 1001) {
                        etQuantity.setText(String.valueOf(cartItem.getQuantity() + 1));
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                    }
                }
            });
            btnReduceQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartItem.getQuantity() > 1) {
                        etQuantity.setText(String.valueOf(cartItem.getQuantity() - 1));
                        cartItem.setQuantity(cartItem.getQuantity() - 1);
                    }
                }
            });

            etQuantity.setFilters(new InputFilter[]{new InputFilterQuantity("1", "1000")});
            etQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int quantity = Integer.valueOf(String.valueOf(s));
                        cartItem.setQuantity(quantity);
                    } catch (NumberFormatException e) {
                        Timber.e(e);
                    } catch (Exception ex) {
                        Timber.e(ex);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            swDiscountA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        swDiscountB.setChecked(false);
                        swDiscountC.setChecked(false);
                        swDiscountD.setChecked(false);
                        swDiscountE.setChecked(false);
                    }
                    setDiscountValue();
                }
            });

            swDiscountB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        swDiscountA.setChecked(false);
                        swDiscountC.setChecked(false);
                        swDiscountD.setChecked(false);
                        swDiscountE.setChecked(false);
                    }
                    setDiscountValue();
                }
            });

            swDiscountC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        swDiscountA.setChecked(false);
                        swDiscountB.setChecked(false);
                        swDiscountD.setChecked(false);
                        swDiscountE.setChecked(false);
                    }
                    setDiscountValue();
                }
            });

            swDiscountD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        swDiscountA.setChecked(false);
                        swDiscountB.setChecked(false);
                        swDiscountC.setChecked(false);
                        swDiscountE.setChecked(false);
                    }
                    setDiscountValue();
                }
            });

            swDiscountE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        swDiscountA.setChecked(false);
                        swDiscountB.setChecked(false);
                        swDiscountC.setChecked(false);
                        swDiscountD.setChecked(false);
                    }
                    setDiscountValue();
                }
            });

            //endregion

            dialog.show();
        } catch (Exception e) {
            Timber.e(e);
        }
        return this;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }

    private void turnOffAllSwitch() {
        swDiscountA.setChecked(false);
        swDiscountB.setChecked(false);
        swDiscountC.setChecked(false);
        swDiscountD.setChecked(false);
        swDiscountE.setChecked(false);
    }

    private void setDiscountValue() {
        if (swDiscountA.isChecked()) {
            cartItem.setDiscount(0.0);
        } else if (swDiscountB.isChecked()) {
            cartItem.setDiscount(10.0);
        } else if (swDiscountC.isChecked()) {
            cartItem.setDiscount(35.5);
        } else if (swDiscountD.isChecked()) {
            cartItem.setDiscount(50.0);
        } else if (swDiscountE.isChecked()) {
            cartItem.setDiscount(100.0);
        } else {
            cartItem.setDiscount(0.0);
        }
    }
}
