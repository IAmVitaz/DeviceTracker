package com.vitaz.devicetracker

import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vitaz.devicetracker.test_utils.MyViewAction
import com.vitaz.devicetracker.test_utils.atPosition
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.typeText
import android.widget.EditText

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun listFragmentIsVisibleOnTheFirstLaunch() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withText("Devices"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canOpenDetailsFragment() {
        ActivityScenario.launch(MainActivity::class.java)

        Thread.sleep(2000)

        // Add first item to favourites
        onView(withId(R.id.devicesRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, MyViewAction.clickChildViewWithId(R.id.itemRow)));

        onView(withText("Details"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun newQueryInSearchViewFilterData() {
        ActivityScenario.launch(MainActivity::class.java)

        Thread.sleep(2000)

        val testString = "Diode"

        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText(testString),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )

        onView(withId(R.id.devicesRecyclerView))
            .check(matches(atPosition(0, hasDescendant(withText(containsString(testString))))))
    }


}
