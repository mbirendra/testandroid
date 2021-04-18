package com.birendra.lensdayshop

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.birendra.lensdayshop.adapter.BookingAdapter
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class DeleteCartTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(BottomActivity::class.java)

    @Test
    fun deleteCartUI()
    {
        runBlocking {
            RetrofitService.Online = true
            var userRepo = UserRepository()
            RetrofitService.token = "Bearer "+userRepo.loginUser("admins","admin").token
        }

        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard))
            .perform(ViewActions.click())

        Thread.sleep(400)

        Espresso.onView(ViewMatchers.withId(R.id.recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BookingAdapter.BookingViewHolder>(0,CustomAction.clickItemWithId(R.id.cbCheck)))

        Thread.sleep(400)

        onView(withText("Delete"))
            .perform(click())

        Thread.sleep(2000)

        onView(withText("Continue Shopping"))
            .check(matches(isDisplayed()))
    }
}