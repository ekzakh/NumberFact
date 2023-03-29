package com.ekzak.numberfact.presentation

import com.ekzak.numberfact.presentation.main.MainViewModel
import com.ekzak.numberfact.presentation.main.NavigationStrategy
import com.ekzak.numberfact.presentation.numbers.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class MainViewModelTest : BaseTest() {
    @Ignore //todo check test with viewBinding
    @Test
    fun `test navigation at start`() {
        val navigation = TestNavigationCommunication()
        val mainViewModel = MainViewModel(navigation)

        mainViewModel.init(true)
        assertEquals(1, navigation.count)
        assertEquals(true, navigation.strategy is NavigationStrategy.Replace)

        mainViewModel.init(false)
        assertEquals(1, navigation.count)
    }
}
