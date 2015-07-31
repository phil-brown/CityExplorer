package self.philbrown.cityexplorer.model;

import android.os.Parcel;
import android.test.InstrumentationTestCase;

/**
 * Tests for the City class.
 *
 * @author Phil Brown
 * @since 11:10 PM Jul 30, 2015
 */
public class CityTest extends InstrumentationTestCase {

    /**
     * Test Constructors
     * @throws Exception
     */
    public void testConstructors() throws Exception {
        new City();
        new City("", "", "", "");
    }

    /**
     * Test Parcelable/Equals/hashcode methods
     * @throws Exception
     */
    public void testParcelable() throws Exception {
        Parcel parcel = Parcel.obtain();
        City original = new City("Marshall", "United States", "http://media-cdn.tripadvisor.com/media/photo-o/06/2d/cc/9b/pool.jpg", "This is where I currently live.");
        original.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        City parceledAnswer = City.CREATOR.createFromParcel(parcel);
        assertEquals(original, parceledAnswer);
    }
}
