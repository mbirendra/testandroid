package com.birendra.lensdayshop

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class LoginTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun chcekLoginUI()
    {
        onView(withId(R.id.etEmail))
            .perform(typeText("admins"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.etPassword))
            .perform(typeText("admin"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.cirLoginButton))
            .perform(click())
        Thread.sleep(2000)
        onView(withText("FunFurnish"))
            .check(matches(isDisplayed()))
    }
}