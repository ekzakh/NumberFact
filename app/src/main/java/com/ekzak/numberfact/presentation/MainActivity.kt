package com.ekzak.numberfact.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ekzak.numberfact.R
import com.ekzak.numberfact.presentation.numbers.NumbersFragment
import com.ekzak.numberfact.presentation.numbers.ShowFragment

class MainActivity : AppCompatActivity(), ShowFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigate(NumbersFragment.getInstance(), true)
        }
    }

    private fun navigate(fragment: Fragment, add: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        val container = R.id.container
        if (add) {
            transaction.add(container, fragment)
        } else {
            transaction.replace(container, fragment)
        }
        transaction.addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

    override fun show(fragment: Fragment) {
        navigate(fragment, false)
    }
}
