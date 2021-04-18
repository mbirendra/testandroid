package com.birendra.lensdayshop

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
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
class UpdateCartTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(BottomActivity::class.java)

    @Test
    fun updateCartUI()
    {
        runBlocking {
            RetrofitService.Online = true
            var userRepo = UserRepository()
            RetrofitService.token = "Bearer "+userRepo.loginUser("admins","admin").token
        }

        onView(withId(R.id.navigation_dashboard))
            .perform(click())

        Thread.sleep(400)

        onView(withId(R.id.recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BookingAdapter.BookingViewHolder>(0,CustomAction.clickItemWithId(R.id.ivAdd)))

        Thread.sleep(2000)


        onView(withText("2"))
            .check(matches(isDisplayed()))




    }


}