package com.acme.a3csci3130;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

/**
 * CRUD Interface test
 *
 * Create, Retrieve, Update, Delete
 */

// Run in alphabetical order!
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CRUDInterfaceTest {

    String businessNumber = "123456789";
    String name = "Test Name";
    String primaryBusiness = "Distributor";

    String newName = "New Test Name";
    String newPrimaryBusiness = "Fisher";

    // Test is done on MainActivity
    @Rule
    public ActivityTestRule<MainActivity> activityrule =
            new ActivityTestRule<>(MainActivity.class);

    // Test create functionality
    @Test
    public void A_createTest() {
        // Click CREATE CONTACT button on MainActivity
        onView(withId(R.id.createButton)).perform(click());
        // Select the "Business Number" field and enter 123456789, close keyboard
        onView(withId(R.id.number)).perform(typeText(businessNumber), closeSoftKeyboard());
        // Select the "Name" field and enter "Test Name", close keyboard
        onView(withId(R.id.name)).perform(typeText(name), closeSoftKeyboard());
        // Select the "Primary Business" spinner field
        onView(withId(R.id.primaryBusinessSpinner)).perform(click());
        // Select the spinner item "Distributor"
        onData(allOf(is(instanceOf(String.class)), is(primaryBusiness))).perform(click());
        onView(withId(R.id.primaryBusinessSpinner)).check(matches(withSpinnerText(primaryBusiness)));
        // Click CREATE CONTACT button
        onView(withId(R.id.createButton)).perform(click());
    }

    // Test retrieve functionality
    @Test
    public void B_retrieveTest() {
        SystemClock.sleep(1500);
        // Select the first list item
        onData(anything()).inAdapterView(withId(R.id.lv)).atPosition(0).perform(click());
        // Confirm the data matches the text we entered during create
        onView(withId(R.id.number)).check(matches(withText(businessNumber)));
        onView(withId(R.id.name)).check(matches(withText(name)));
        onView(withId(R.id.primaryBusinessSpinner)).check(matches(withSpinnerText(primaryBusiness)));
    }

    // Test update functionality
    @Test
    public void C_updateTest() {
        SystemClock.sleep(1500);
        // Select the first list item
        onData(anything()).inAdapterView(withId(R.id.lv)).atPosition(0).perform(click());
        // Select the "Name" field and replace the text with "New Test Name", close keyboard
        onView(withId(R.id.name)).perform(replaceText(newName), closeSoftKeyboard());
        // Select the Primary Business spinner field
        onView(withId(R.id.primaryBusinessSpinner)).perform(click());
        // Select the spinner item Fisher
        onData(allOf(is(instanceOf(String.class)), is(newPrimaryBusiness))).perform(click());
        onView(withId(R.id.primaryBusinessSpinner)).check(matches(withSpinnerText(newPrimaryBusiness)));
        // Click UPDATE CONTACT button
        onView(withId(R.id.updateButton)).perform(click());
    }

    // Test delete functionality
    @Test
    public void D_deleteTest() {
        SystemClock.sleep(1500);
        // Select the first list item
        onData(anything()).inAdapterView(withId(R.id.lv)).atPosition(0).perform(click());
        // Click ERASE CONTACT button
        onView(withId(R.id.deleteButton)).perform(click());
    }
}