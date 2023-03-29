package com.ekzak.numberfact.sl.main

import android.content.Context
import com.ekzak.numberfact.data.NumberFactDetails
import com.ekzak.numberfact.data.cache.CacheModule
import com.ekzak.numberfact.data.cache.NumbersDataBase
import com.ekzak.numberfact.data.cloud.CloudModule
import com.ekzak.numberfact.presentation.main.ManageResources
import com.ekzak.numberfact.presentation.main.NavigationCommunication
import com.ekzak.numberfact.presentation.numbers.DispatchersList

interface Core : CloudModule, CacheModule, ManageResources, ProvideNavigation, ProvideFactDetails {

    fun provideDispatchers(): DispatchersList

    class Base(
        context: Context,
        private val provideInstances: ProvideInstances,
    ) : Core {

        private val numberFactDetails = NumberFactDetails.Base()

        private val navigationCommunication = NavigationCommunication.Base()

        private val manageResources: ManageResources = ManageResources.Base(context)

        private val cloudModule by lazy {
            provideInstances.provideCloudModule()
        }

        private val cacheModule by lazy {
            provideInstances.provideCacheModule()
        }

        private val dispatchersList by lazy {
            DispatchersList.Base()
        }

        override fun <T> service(clazz: Class<T>): T = cloudModule.service(clazz)

        override fun provideDataBase(): NumbersDataBase = cacheModule.provideDataBase()

        override fun string(id: Int): String = manageResources.string(id)

        override fun provideNavigation(): NavigationCommunication.Mutable = navigationCommunication

        override fun provideFactDetails(): NumberFactDetails.Mutable = numberFactDetails

        override fun provideDispatchers(): DispatchersList = dispatchersList
    }
}
