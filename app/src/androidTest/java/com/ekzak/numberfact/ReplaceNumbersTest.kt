package com.ekzak.numberfact

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekzak.numberfact.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReplaceNumbersTest : BaseTest() {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_not_duplicated_items(): Unit = NumbersPage().run {
        //enter 1
        inputNumber.view().typeText("10")
        factButton.view().click()

        recycler.run {
            viewInRecycler(0, R.id.title).checkText("10")
            viewInRecycler(0, R.id.sub_title).checkText("Fact about 10")
        }
        //enter 2
        inputNumber.view().typeText("20")
        factButton.view().click()

        recycler.run {
            viewInRecycler(0, R.id.title).checkText("20")
            viewInRecycler(0, R.id.sub_title).checkText("Fact about 20")
            viewInRecycler(1, R.id.title).checkText("10")
            viewInRecycler(1, R.id.sub_title).checkText("Fact about 10")
        }
        //enter 1 again
        inputNumber.view().typeText("10")
        factButton.view().click()

        recycler.run {
            viewInRecycler(0, R.id.title).checkText("10")
            viewInRecycler(0, R.id.sub_title).checkText("Fact about 10")
            viewInRecycler(1, R.id.title).checkText("20")
            viewInRecycler(1, R.id.sub_title).checkText("Fact about 20")
        }
    }
}
