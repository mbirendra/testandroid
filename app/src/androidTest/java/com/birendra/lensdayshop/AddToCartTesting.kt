package com.birendra.lensdayshop

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule

import androidx.test.filters.LargeTest
import com.birendra.lensdayshop.adapter.LensdaysAdapter
import com.birendra.lensdayshop.api.RetrofitService
import com.birendra.lensdayshop.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class AddToCartTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(BottomActivity::class.java)

    @Test
    fun addToCart()
    {
       runBlocking {
           RetrofitService.Online = true
           var userRepo = UserRepository()
           RetrofitService.token = "Bearer "+userRepo.loginUser("admins","admin").token
       }
        onView(withId(R.id.navigation_home))
            .perform(click())
        Thread.sleep(300)
        onView(withId(R.id.nav_chair))
            .perform(click())
        Thread.sleep(3000)

        onView(withId(R.id.recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<LensdaysAdapter.FurnitureViewHolder>(0,CustomAction.clickItemWithId(R.id.layoutProduct)))

        Thread.sleep(400)

        onView(withId(R.id.btnAdd))
            .perform(click())

        Thread.sleep(2000)

        onView(withText("Product Added to Cart"))
            .check(matches(isDisplayed()))

    }

}