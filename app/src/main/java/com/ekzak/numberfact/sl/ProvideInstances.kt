package com.ekzak.numberfact.sl

import android.content.Context
import com.ekzak.numberfact.data.cache.CacheModule
import com.ekzak.numberfact.data.cloud.CloudModule

interface ProvideInstances {

    fun provideCloudModule(): CloudModule
    fun provideCacheModule(): CacheModule

    class Release(private val context: Context) : ProvideInstances {

        override fun provideCloudModule(): CloudModule = CloudModule.Base()

        override fun provideCacheModule(): CacheModule = CacheModule.Base(context)

    }

    class Mock(private val context: Context) : ProvideInstances {

        override fun provideCloudModule(): CloudModule = CloudModule.Mock()

        override fun provideCacheModule(): CacheModule = CacheModule.Mock(context)

    }
}
