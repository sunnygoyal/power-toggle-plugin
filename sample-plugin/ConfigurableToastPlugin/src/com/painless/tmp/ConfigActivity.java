package com.painless.tmp;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ConfigActivity extends Activity implements TextWatcher, OnClickListener {

    private View mAcceptBtn;
    private EditText mMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

        mAcceptBtn = findViewById(R.id.btn_accept);
        mAcceptBtn.setOnClickListener(this);

        mMsg = (EditText) findViewById(R.id.editText);
        mMsg.addTextChangedListener(this);
        setResult(RESULT_CANCELED);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        mAcceptBtn.setEnabled(!mMsg.getText().toString().isEmpty());
    }

    @Override
    public void onClick(View v) {
        String id = UUID.randomUUID().toString();
        PreferenceManager.getDefaultSharedPreferences(this).edit()
            .putString(id, mMsg.getText().toString()).commit();
        setResult(RESULT_OK, new Intent().putExtra(Intent.EXTRA_UID, id));
        finish();
    }
}
