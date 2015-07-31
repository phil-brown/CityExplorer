package self.philbrown.cityexplorer.ui;

import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.view.View;

import java.util.ArrayList;

import self.philbrown.cityexplorer.model.City;

/**
 * Simple tests for the adapter
 *
 * @author Phil Brown
 * @since 11:10 PM Jul 30, 2015
 */
public class CitiesListAdapterTest extends InstrumentationTestCase {

    /**
     * test the constructor
     * @throws Exception
     */
    public void testConstructor() throws Exception {
        new CitiesListAdapter(getInstrumentation().getContext(), new ArrayList<City>());
    }

    /**
     * Test the getView method
     * @throws Exception
     */
    @UiThreadTest
    public void testGetView() throws Exception {
        ArrayList<City> list = new ArrayList<>(1);
        list.add(new City("", "", "", ""));
        CitiesListAdapter adapter = new CitiesListAdapter(getInstrumentation().getTargetContext(), list);
        View view = adapter.getView(0, null, null);
        assertNotNull(view.getTag());
    }
}
