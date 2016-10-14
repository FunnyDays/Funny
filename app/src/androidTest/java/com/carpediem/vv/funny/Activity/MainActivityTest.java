package com.carpediem.vv.funny.Activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.carpediem.vv.funny.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction shiftingBottomNavigationTab = onView(
                allOf(withClassName(is("com.ashokvarma.bottomnavigation.ShiftingBottomNavigationTab")),
                        withParent(allOf(withId(R.id.bottom_navigation_bar_item_container),
                                withParent(withId(R.id.bottom_navigation_bar_container)))),
                        isDisplayed()));
        shiftingBottomNavigationTab.perform(click());

        ViewInteraction shiftingBottomNavigationTab2 = onView(
                allOf(withClassName(is("com.ashokvarma.bottomnavigation.ShiftingBottomNavigationTab")),
                        withParent(allOf(withId(R.id.bottom_navigation_bar_item_container),
                                withParent(withId(R.id.bottom_navigation_bar_container)))),
                        isDisplayed()));
        shiftingBottomNavigationTab2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.music_play),
                        withParent(allOf(withId(R.id.image_button),
                                withParent(withId(R.id.linearLayout_root)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.music_play),
                        withParent(allOf(withId(R.id.image_button),
                                withParent(withId(R.id.linearLayout_root)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.music_play),
                        withParent(allOf(withId(R.id.image_button),
                                withParent(withId(R.id.linearLayout_root)))),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.music_play),
                        withParent(allOf(withId(R.id.image_button),
                                withParent(withId(R.id.linearLayout_root)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.music_play),
                        withParent(allOf(withId(R.id.image_button),
                                withParent(withId(R.id.linearLayout_root)))),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.music_play),
                        withParent(allOf(withId(R.id.image_button),
                                withParent(withId(R.id.linearLayout_root)))),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.music_play),
                        withParent(allOf(withId(R.id.image_button),
                                withParent(withId(R.id.linearLayout_root)))),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

    }



}
