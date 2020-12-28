package com.markeisjones.notekeeper

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jwhh.notekeeper.CourseRecyclerAdapter
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest{
    @Rule @JvmField
    val itemsActivity = ActivityTestRule(ItemsActivity::class.java)

@Test
  fun  selectNoteAfterNavigationDrawerChange(){
    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_courses))

    val coursePosition = 0
    onView(withId(R.id.listItems)).perform(
        RecyclerViewActions.actionOnItemAtPosition<CourseRecyclerAdapter.ViewHolder>(coursePosition, click())
    )

    onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
    onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes))

    val notePosition = 0
    onView(withId(R.id.listItems)).perform(
        RecyclerViewActions.actionOnItemAtPosition<NoteRecyclerAdapter.ViewHolder>(notePosition,
            click())
    )

    val note = DataManager.notes[notePosition]
    onView(withId(R.id.spinnerCourses)).check(matches(withSpinnerText(containsString(note.course?.title))))
    onView(withId(R.id.textNoteTitle)).check(matches(withText(containsString(note.title))))
    onView(withId(R.id.textNoteText)).check(matches(withText(containsString(note.text))))

    }




}