package com.example.e_kostan.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String LOGIN_APP = "E-Kostan";
    public static final String NAMA_LENGKAP = "Nama";
    public static final String EMAIL = "Email";
    public static final String TELPON= "Telpon";
    public static final String ALAMAT = "Alamat";
    public static final String ROLE = "Role";
    public static final String JENIS_KELAMIN = "Jenis_Kelamin";
    public static final String PASSWORD = "Password";
    public static final String SP_SUDAH_LOGIN = "SudahLogin";
    public static final String SP_GAMBAR ="Gambar";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(LOGIN_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }
    public String getSPNama(){
        return sp.getString(NAMA_LENGKAP, "");
    }
    public String getSPEmail(){
        return sp.getString(EMAIL, "");
    }
    public String getSPTelepon(){
        return sp.getString(TELPON, "");
    }
    public String getSPAlamat(){
        return sp.getString(ALAMAT, "");
    }
    public String getSPRole(){
        return sp.getString(ROLE, "");
    }
    public String getSPJenis_Kelamin(){
        return sp.getString(JENIS_KELAMIN, "");
    }
    public String getSPPassword(){
        return sp.getString(PASSWORD, "");
    }
    public Boolean getSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
    public String getSpGambar(){
        return sp.getString(SP_GAMBAR, "");
    }
    }

