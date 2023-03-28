package com.ekzak.numberfact.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.ekzak.numberfact.R
import com.ekzak.numberfact.presentation.numbers.NumbersFragment
import com.ekzak.numberfact.presentation.numbers.ShowFragment
import com.ekzak.numberfact.sl.ProvideViewModel

class MainActivity : AppCompatActivity(), ShowFragment, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            NavigationStrategy.Add(NumbersFragment.getInstance())
                .navigate(supportFragmentManager, R.id.container)
        }
    }

    override fun show(fragment: Fragment) {
        NavigationStrategy.Replace(fragment)
            .navigate(supportFragmentManager, R.id.container)
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        (application as ProvideViewModel).provideViewModel(clazz, owner)

}
