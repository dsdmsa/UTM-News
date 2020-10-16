package dsdmsa.utmnews;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dsdmsa.utmnews.presentation.activityes.MainActivity;
import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
import tools.fastlane.screengrab.locale.LocaleTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(JUnit4.class)
public class JUnit4StyleTests {

    @ClassRule
    public static final LocaleTestRule localeTestRule = new LocaleTestRule();

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);


    @BeforeClass
    public static void beforeAll() {
        Screengrab.setDefaultScreenshotStrategy(new UiAutomatorScreenshotStrategy());
    }

    @Test
    public void testTakeScreenshot() {
        Screengrab.screenshot("mainScreen");

        onView(withId(R.id.btn_about)).perform(click());
        onView(withId(R.id.btn_about)).perform(click());
        Screengrab.screenshot("aboutScreen");

        onView(withId(R.id.menu_tags)).perform(click());
        Screengrab.screenshot("mainScreenTags");

        onView(withId(R.id.menu_bookmarks)).perform(click());
        Screengrab.screenshot("mainScreenBook");

        onView(withId(R.id.menu_search)).perform(click());
        Screengrab.screenshot("search");

        onView(withId(R.id.et_search)).perform(click()).perform(typeText("utm"));

        onView(withId(R.id.et_search)).perform(click());
        onView(withId(R.id.et_search)).perform(click());
        onView(withId(R.id.et_search)).perform(click());
        Screengrab.screenshot("searchRez");
        onView(withId(R.id.btn_search)).perform(click());
        onView(withId(R.id.btn_search)).perform(click());
        onView(withId(R.id.btn_search)).perform(click());
        Screengrab.screenshot("searchRezNoKey");


    }

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

}
