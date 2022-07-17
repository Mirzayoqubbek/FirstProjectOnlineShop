package com.example.onlineshop

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.onlineshop.db.AppDatabase
import com.orhanobut.hawk.Hawk

class App: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        AppDatabase.initDatabase(this)
    }
}