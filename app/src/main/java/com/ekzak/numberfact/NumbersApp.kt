package com.ekzak.numberfact

import android.app.Application
import android.util.Log
import com.ekzak.numberfact.data.CloudModule
import com.ekzak.numberfact.data.NumbersCloudDataSource
import com.ekzak.numberfact.data.NumbersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //todo move out
        val service = if (BuildConfig.DEBUG) {
            CloudModule.Debug().service(NumbersService::class.java)
        } else {
            CloudModule.Base().service(NumbersService::class.java)
        }
        GlobalScope.launch(Dispatchers.IO) {
            val dataSource = NumbersCloudDataSource.Base(service)
            val fact = dataSource.fact("10")
            Log.d("TAG", fact.toString())
        }
    }
}
