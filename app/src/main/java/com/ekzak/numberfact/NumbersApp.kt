package com.ekzak.numberfact

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.ekzak.numberfact.sl.Core
import com.ekzak.numberfact.sl.DependencyContainer
import com.ekzak.numberfact.sl.ProvideInstances
import com.ekzak.numberfact.sl.ProvideViewModel
import com.ekzak.numberfact.sl.ViewModelsFactory

class NumbersApp : Application(), ProvideViewModel {

    private lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        val provideInstances = if (BuildConfig.DEBUG) {
            ProvideInstances.Mock(this)
        } else {
            ProvideInstances.Release(this)
        }
        viewModelsFactory = ViewModelsFactory(
            DependencyContainer.Base(
                Core.Base(
                    this,
                    provideInstances
                )
            )
        )
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clazz]
}
