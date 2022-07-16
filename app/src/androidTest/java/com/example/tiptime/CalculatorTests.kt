package com.example.tiptime


import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.math.RoundingMode

@RunWith(AndroidJUnit4::class)
class CalculatorTests {
    private val TAG = "CalculatorTests"

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)
    private val tipAmount = 50.00

    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.calculate_button)).perform(click())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("$10.00"))))
    }

    @Test
    fun calculate_18_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText(tipAmount.toString()))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_eighteen_percent)).perform(click())
        onView(withId(R.id.calculate_button)).perform(click())

        val tipResult = BigDecimal(tipAmount * .18).setScale(2, RoundingMode.HALF_EVEN)

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("$${tipResult}"))))
    }

    @Test
    fun calculate_15_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText(tipAmount.toString()))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_fifteen_percent)).perform(click())
        onView(withId(R.id.round_up_switch)).perform(click())
        onView(withId(R.id.calculate_button)).perform(click())

        val tipResult = BigDecimal(tipAmount * .15).setScale(2, RoundingMode.HALF_EVEN)
        Log.d(TAG, tipResult.toString())

        onView(withId(R.id.tip_result)).check(matches(withText(containsString("$${tipResult}"))))
    }
}