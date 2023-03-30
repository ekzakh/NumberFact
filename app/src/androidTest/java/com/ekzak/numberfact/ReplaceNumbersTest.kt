package com.ekzak.numberfact

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekzak.numberfact.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReplaceNumbersTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_not_duplicated_items() {
        //enter 1
        onView(ViewMatchers.withId(R.id.input_number)).perform(ViewActions.typeText("10"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.fact)).perform(ViewActions.click())

        onView(RecyclerViewMatcher(R.id.recycler).atPosition(0, R.id.title))
            .check(matches(withText("10")))
        onView(RecyclerViewMatcher(R.id.recycler).atPosition(0, R.id.sub_title))
            .check(matches(withText("Fact about 10")))

        //enter 2
        onView(ViewMatchers.withId(R.id.input_number)).perform(ViewActions.typeText("20"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.fact)).perform(ViewActions.click())

        onView(RecyclerViewMatcher(R.id.recycler).atPosition(0, R.id.title))
            .check(matches(withText("20")))
        onView(RecyclerViewMatcher(R.id.recycler).atPosition(0, R.id.sub_title))
            .check(matches(withText("Fact about 20")))
        onView(RecyclerViewMatcher(R.id.recycler).atPosition(1, R.id.title))
            .check(matches(withText("10")))
        onView(RecyclerViewMatcher(R.id.recycler).atPosition(1, R.id.sub_title))
            .check(matches(withText("Fact about 10")))

        //enter 1 again
        onView(ViewMatchers.withId(R.id.input_number)).perform(ViewActions.typeText("10"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.fact)).perform(ViewActions.click())

        onView(RecyclerViewMatcher(R.id.recycler).atPosition(0, R.id.title))
            .check(matches(withText("10")))
        onView(RecyclerViewMatcher(R.id.recycler).atPosition(0, R.id.sub_title))
            .check(matches(withText("Fact about 10")))
        onView(RecyclerViewMatcher(R.id.recycler).atPosition(1, R.id.title))
            .check(matches(withText("20")))
        onView(RecyclerViewMatcher(R.id.recycler).atPosition(1, R.id.sub_title))
            .check(matches(withText("Fact about 20")))
    }
}
