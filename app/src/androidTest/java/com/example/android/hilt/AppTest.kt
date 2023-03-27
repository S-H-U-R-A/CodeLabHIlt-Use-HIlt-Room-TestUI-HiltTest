package com.example.android.hilt

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this@AppTest)

    /*@get:Rule
    val activity: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)*/


/*    @After
    fun tearDown() {
        // Remove logs after the test finishes
        //ServiceLocator(getInstrumentation().targetContext).loggerLocalDataSource.removeLogs()
    }*/

    @Test
    fun happyPath() {

        //SE PIDE QUE SE INICIE EL MAIN ACTIVITY
        ActivityScenario.launch(MainActivity::class.java)

        //launchActivity<MainActivity>()

        // Check Buttons fragment screen is displayed
        onView(
            withId(R.id.textView)
        ).check(
            matches(
                isDisplayed()
            )
        )

        // Tap on Button 1
        onView(
            withId(R.id.button1)
        ).perform(
            click()
        )

        // Navigate to Logs screen
        onView(
            withId(R.id.all_logs)
        ).perform(
            click()
        )

        // Check Logs fragment screen is displayed
        onView(
            withText(
                containsString("Interaction with 'Button 1'")
            )
        ).check(
            matches(
                isDisplayed()
            )
        )

    }
}
