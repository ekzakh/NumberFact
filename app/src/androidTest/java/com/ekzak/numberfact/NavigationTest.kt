package com.ekzak.numberfact

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ekzak.numberfact.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_details_navigation() {
        //actions
        onView(withId(R.id.input_number)).perform(typeText("10"))
        closeSoftKeyboard()
        onView(withId(R.id.fact)).perform(click())

        //checks
        onView(withId(R.id.title)).check(matches(withText("10")))
        onView(withId(R.id.sub_title)).check(matches(withText("Fact about 10")))

        //navigate to details
        onView(withId(R.id.sub_title)).perform(click())

        onView(withId(R.id.fact)).check(matches(withText("10\n\nFact about 10")))

        pressBack()
        onView(withId(R.id.title)).check(matches(withText("10")))
        onView(withId(R.id.sub_title)).check(matches(withText("Fact about 10")))
    }
}
