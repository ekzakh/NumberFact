package com.ekzak.numberfact

import androidx.test.espresso.Espresso.pressBack
import org.junit.Test

class NavigationTest : BaseTest(){

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
