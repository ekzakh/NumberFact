package com.ekzak.numberfact

import org.junit.Test

class ReplaceNumbersTest : BaseTest() {

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
