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
class SignUpTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun checkSignUpUI()
    {
        onView(withId(R.id.etFname))
            .perform(typeText("Ashish"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.etLname))
            .perform(typeText("Pandey"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.etEmail))
            .perform(typeText("asisPandeyyy@gmail.com"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.etMobile))
            .perform(typeText("9803609163"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.etUsername))
            .perform(typeText("adminAshish"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.etAddress))
            .perform(typeText("Gongabu"))
        closeSoftKeyboard()
        Thread.sleep(200)
        onView(withId(R.id.etPassword))
            .perform(typeText("123456"))
        closeSoftKeyboard()
        Thread.sleep(200)

        onView(withId(R.id.cirRegisterButton))
            .perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.cirLoginButton))
            .check(matches(isDisplayed()))

    }

}