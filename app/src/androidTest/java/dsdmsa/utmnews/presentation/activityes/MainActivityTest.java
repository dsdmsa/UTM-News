//package dsdmsa.utmnews.presentation.activityes;
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.BeforeClass;
//import org.junit.ClassRule;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import dsdmsa.utmnews.R;
//import tools.fastlane.screengrab.Screengrab;
//import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
//import tools.fastlane.screengrab.locale.LocaleTestRule;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//@RunWith(AndroidJUnit4.class)
//public class MainActivityTest {
//
//    @ClassRule
//    public static final LocaleTestRule localeTestRule = new LocaleTestRule();
//
//    @Rule
//    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
//
//
//    @BeforeClass
//    public static void beforeAll() {
//        Screengrab.setDefaultScreenshotStrategy(new UiAutomatorScreenshotStrategy());
//    }
//
//    @Test
//    public void testTakeScreenshot() {
//        Screengrab.screenshot("mainScreen");
//    }
//
//    @Test
//    public void about(){
//        onView(withId(R.id.btn_about)).perform(click());
//        Screengrab.screenshot("aboutScreen");
//    }
//
//}