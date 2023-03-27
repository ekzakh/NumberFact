package com.ekzak.numberfact.sl

import android.content.Context
import com.ekzak.numberfact.data.cache.CacheModule
import com.ekzak.numberfact.data.cache.NumbersDataBase
import com.ekzak.numberfact.data.cloud.CloudModule
import com.ekzak.numberfact.presentation.ManageResources
import com.ekzak.numberfact.presentation.numbers.DispatchersList

interface Core : CloudModule, CacheModule, ManageResources {

    fun provideDispatchers(): DispatchersList

    class Base(
        context: Context,
        private val isRelease: Boolean,
    ) : Core {

        private val manageResources: ManageResources = ManageResources.Base(context)

        private val cloudModule by lazy {
            if (isRelease) {
                CloudModule.Base()
            } else {
                CloudModule.Mock()
            }
        }

        private val cacheModule by lazy {
            if (isRelease) {
                CacheModule.Base(context)
            } else {
                CacheModule.Mock(context)
            }
        }

        private val dispatchersList by lazy {
            DispatchersList.Base()
        }

        override fun <T> service(clazz: Class<T>): T = cloudModule.service(clazz)
        override fun provideDataBase(): NumbersDataBase = cacheModule.provideDataBase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideDispatchers(): DispatchersList = dispatchersList
    }
}
