package com.ekzak.numberfact

import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekzak.numberfact.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest : BaseTest(){

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_details_navigation() {

        val numbersPage = NumbersPage()
        numbersPage.run {
            inputNumber.view().typeText("10")
            factButton.view().click()

            recycler.run {
                viewInRecycler(0, title).checkText("10")
                viewInRecycler(0, subTitle).checkText("Fact about 10")
                viewInRecycler(0, subTitle).click()
            }
        }

        DetailsPage().details.view().checkText("10\n\nFact about 10")

        pressBack()

        numbersPage.run {
            recycler.run {
                viewInRecycler(0, title).checkText("10")
                viewInRecycler(0, subTitle).checkText("Fact about 10")
            }
        }
    }
}
