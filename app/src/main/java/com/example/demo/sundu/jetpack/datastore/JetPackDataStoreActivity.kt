package com.example.demo.sundu.jetpack.datastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.demo.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class JetPackDataStoreActivity : AppCompatActivity() {
    val TAG = "JetPackDataStore"
    val PREFERENCE_NAME = "DataStore"

    var dataStore: DataStore<Preferences> = createDataStore(name = PREFERENCE_NAME)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jetpack_activity_data_sotre)
        lifecycleScope.launch {
            editDataStore()
        }
    }


     suspend fun editDataStore(){
        var preKey = preferencesKey<String>("ByteCode")
        dataStore.edit { mutablePreferences ->
            mutablePreferences[preKey] = "test"
        }
    }


    private suspend fun readPreInfo(): String {
        var preKey = preferencesKey<String>("ByteCode")
        var value = dataStore.data.map {
                preferences ->
            preferences[preKey] ?: ""
        }
        return value.first()
    }


    fun editSharePreferences(){
        val sp = getSharedPreferences("BateCode", Context.MODE_PRIVATE)
        val getStringSharePreferences = sp.getString("jetpack","")
        Log.e(TAG,"get SharePreferences jetpack = "+getStringSharePreferences)
        sp.edit().putString("jetpack","测试").apply()
        Log.e(TAG,"get SharePreferences jetpack = "+sp.getString("jetpack",""))
    }
}