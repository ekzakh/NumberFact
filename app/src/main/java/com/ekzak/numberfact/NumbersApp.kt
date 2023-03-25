package com.ekzak.numberfact

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.ekzak.numberfact.sl.Core
import com.ekzak.numberfact.sl.DependencyContainer
import com.ekzak.numberfact.sl.ProvideViewModel
import com.ekzak.numberfact.sl.ViewModelsFactory

class NumbersApp : Application(), ProvideViewModel {

    private lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        viewModelsFactory = ViewModelsFactory(DependencyContainer.Base(Core.Base(this, !BuildConfig.DEBUG)))
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clazz]
}
