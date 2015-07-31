package self.philbrown.cityexplorer;

import android.test.InstrumentationTestCase;

import self.philbrown.cityexplorer.model.City;

/**
 * Test for {@link CityDetailFragment}
 *
 * @author Phil Brown
 * @since 11:09 PM Jul 30, 2015
 */
public class CityDetailFragmentTest extends InstrumentationTestCase {

    public void testArguments() throws Exception {
        CityDetailFragment fragment = CityDetailFragment.newInstance(new City("Minneapolis", "United States", "http://media-cdn.tripadvisor.com/media/photo-o/06/2d/cc/9b/pool.jpg", "This is where I currently live."));
        assertNotNull(fragment.getArguments().getParcelable(CityDetailFragment.ARGUMENT_CITY));
    }
}
