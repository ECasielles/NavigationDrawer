package com.example.usuario.navigationdrawer;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;


public class UserFragmentPreferences extends PreferenceFragment {
    public static final String TAG = "UserFragmentPreferences";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.account_settings);
    }

}
