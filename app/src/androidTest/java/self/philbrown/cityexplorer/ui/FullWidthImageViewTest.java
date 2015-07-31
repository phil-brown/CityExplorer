package self.philbrown.cityexplorer.ui;

import android.test.InstrumentationTestCase;

/**
 * Simple tests for the {@link FullWidthImageView} class
 *
 * @author Phil Brown
 * @since 11:11 PM Jul 30, 2015
 */
public class FullWidthImageViewTest extends InstrumentationTestCase {

    /**
     * Test the constructors
     * @throws Exception
     */
    public void testConstructors() throws Exception {
        new FullWidthImageView(getInstrumentation().getContext());
        new FullWidthImageView(getInstrumentation().getContext(), null);
        new FullWidthImageView(getInstrumentation().getContext(), null, 0);
    }
}
