package self.philbrown.cityexplorer;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Simple Tests for MainActivity
 *
 * @author Phil Brown
 * @since 11:09 PM Jul 30, 2015
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testConstructor() throws Exception {
        new MainActivity();
    }

    //TODO more tests
}
