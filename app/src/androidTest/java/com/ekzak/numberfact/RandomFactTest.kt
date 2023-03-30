package com.ekzak.numberfact

import org.junit.Test

class RandomFactTest: BaseTest() {

    @Test
    fun test() {
        NumbersPage().run {
            randomFactButton.view().click()
            recycler.run {
                viewInRecycler(0, title).checkText("1")
                viewInRecycler(0, subTitle).checkText("Fact about 1")
            }
            randomFactButton.view().click()
            recycler.run {
                viewInRecycler(0, title).checkText("2")
                viewInRecycler(0, subTitle).checkText("Fact about 2")
                viewInRecycler(1, title).checkText("1")
                viewInRecycler(1, subTitle).checkText("Fact about 1")
            }
        }
    }
}
