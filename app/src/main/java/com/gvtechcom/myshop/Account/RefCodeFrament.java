package com.gvtechcom.myshop.Account;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gvtechcom.myshop.MainActivity;
import com.gvtechcom.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefCodeFrament extends Fragment {
    private View rootView;

    private FragmentManager fragmentManager;

    private AccountActivity accountActivity;

    @BindView(R.id.layout_main_register)
    ConstraintLayout layoutMainRegister;

    @BindView(R.id.image_account)
    ImageView imagAccount;

    @BindView(R.id.textView_message_tren_edt)
    TextView textViewMessageUpEdt;

    @BindView(R.id.textView_message_duoi_edt)
    TextView txtTextViewMessageDownEdt;

    @BindView(R.id.editText_account)
    EditText editText_account;

    @BindView(R.id.button_account)
    Button buttonAccount;

    @BindView(R.id.textView_message_duoi_btn)
    TextView textViewMessageDownBtn;

    @BindView(R.id.rules_account)
    LinearLayout ruleAccount;

    @BindView(R.id.dieu_khoan)
    TextView txtDieuKhoan;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_giao_dien_account, container, false);
        ButterKnife.bind(this, rootView);
        accountActivity = (AccountActivity) getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setGiaoDien();
        buttonRuleAccount();
        bttonSkipRefCode();
        buttonXacNhan();
        accountActivity.onListenKeyboard(accountActivity, layoutMainRegister);
    }

    private void setGiaoDien(){
        imagAccount.setImageResource(R.drawable.nhap_code);
        txtDieuKhoan.setText(Html.fromHtml("<u>Terms and Conditions</u>"));
        textViewMessageUpEdt.setText("Enter the referral code to enjoy more offers from eCom");
        editText_account.setHint("Referral code");
        txtTextViewMessageDownEdt.setText("You can skip if none");
        txtTextViewMessageDownEdt.setTextColor(Color.parseColor("#FFAF23"));
        buttonAccount.setText("Confirm");
        textViewMessageDownBtn.setText(Html.fromHtml("<p><u>Skip</u></p>"));

    }

    private void buttonRuleAccount(){
        ruleAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_account, new RuleAccount());
                fragmentTransaction.addToBackStack("frag_refcode");
                fragmentTransaction.commit();
            }
        });
    }

    private void bttonSkipRefCode(){
        textViewMessageDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("account", true);
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("account", "true");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void buttonXacNhan(){
        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.gvtechcom.myshop.firts", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("account", true);
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("account", "true");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}

