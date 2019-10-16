package com.example.android_recruitment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.android_recruitment.controller.HistoryActivity
import com.example.android_recruitment.controller.MainActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.*

import org.junit.runner.RunWith

import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainScreenTest {

    @get:Rule
    var mainRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    @Throws(Exception::class)
    fun before() {
        Intents.init()
    }

    @After
    @Throws(Exception::class)
    fun after() {
        Intents.release()
    }

    @Test
    fun changeFromCurrency() {

        onView(allOf(withId(R.id.main_from_layout), isDisplayed())).perform(click())

        Thread.sleep(3000)

        onView(withId(R.id.bottom_sheet_currency_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(3000)

        onView(withId(R.id.main_from_currecy)).check(matches(withText("AUD")))
    }

    @Test
    fun changeToCurrency() {

        onView(allOf(withId(R.id.main_to_layout), isDisplayed())).perform(click())

        Thread.sleep(3000)

        onView(withId(R.id.bottom_sheet_currency_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        Thread.sleep(3000)

        onView(withId(R.id.main_to_currecy)).check(matches(withText("BGN")))
    }

    @Test
    fun openHistory() {

        onView(withId(R.id.main_recycler_currencies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        Thread.sleep(3000)

        intended(hasComponent(HistoryActivity::class.java.name))
    }

    @Test
    fun changeButton() {

        onView(withId(R.id.main_from_currecy)).check(matches(withText("BRL")))
        onView(withId(R.id.main_to_currecy)).check(matches(withText("USD")))

        onView(allOf(withId(R.id.main_change_button), isDisplayed())).perform(click())

        Thread.sleep(3000)

        onView(withId(R.id.main_from_currecy)).check(matches(withText("USD")))
        onView(withId(R.id.main_to_currecy)).check(matches(withText("BRL")))
    }
}
