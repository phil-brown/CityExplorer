package self.philbrown.cityexplorer;

import android.cts.util.PollingCheck;
import android.test.InstrumentationTestCase;

import self.philbrown.cityexplorer.model.City;

/**
 * TODO Description
 *
 * @author Phil Brown
 * @since 11:09 PM Jul 30, 2015
 */
public class NavigationDrawerFragmentTest extends InstrumentationTestCase {

    int count;

    public void testParseCitiesTask() throws Exception {
        new NavigationDrawerFragment.ParseCitiesTask(getInstrumentation().getTargetContext(), "cities.txt", new NavigationDrawerFragment.CitiesParserCallbacks() {
            @Override
            public void onCityParsed(final City city) {
                count++;
            }

            @Override
            public void onParseCompleted() {
                assertEquals("Not all cities counted.", 25, count);
            }
        }).execute();
        new PollingCheck(10000) {
            @Override
            protected boolean check() {
                return 25 == count;
            }
        }.run();
    }
}
