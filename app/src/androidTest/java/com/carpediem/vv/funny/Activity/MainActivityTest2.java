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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest2() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_comment), withText("评论"), isDisplayed()));
        appCompatButton.perform(click());

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

        ViewInteraction shiftingBottomNavigationTab3 = onView(
                allOf(withClassName(is("com.ashokvarma.bottomnavigation.ShiftingBottomNavigationTab")),
                        withParent(allOf(withId(R.id.bottom_navigation_bar_item_container),
                                withParent(withId(R.id.bottom_navigation_bar_container)))),
                        isDisplayed()));
        shiftingBottomNavigationTab3.perform(click());

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

    }

}
