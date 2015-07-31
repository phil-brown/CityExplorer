package self.philbrown.cityexplorer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * City model that contains simple information about a city to display in the app.
 *
 * @author Phil Brown
 * @since 12:46 PM Jul 30, 2015
 */
public class City implements Parcelable {

    /**
     * Name of this city
     */
    private String name;

    /**
     * Country where this city is located.
     */
    private String country;

    /**
     * URL of an image of this city
     */
    private String imageUrl;

    /**
     * A user-friendly description of this city
     */
    private String description;

    /** Bitwise flag stating that the {@link #name} field is set. */
    private static final int FLAG_HAS_NAME = 1;

    /** Bitwise flag stating that the {@link #country} field is set. */
    private static final int FLAG_HAS_COUNTRY = 2;

    /** Bitwise flag stating that the {@link #imageUrl} field is set. */
    private static final int FLAG_HAS_IMAGE_URL = 4;

    /** Bitwise flag stating that the {@link #description} field is set. */
    private static final int FLAG_HAS_DESCRIPTION = 8;


    /**
     * used to parcel and unparcel this Model
     */
    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel parcel) {
            return new City(parcel);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    /**
     * Default Constructor
     */
    public City() {
    }

    /**
     * Constructor
     * @param name          name of the city
     * @param country       country where the city is located
     * @param imageUrl      ULR of an image of the city
     * @param description   A user-friendly description of the city
     */
    public City(String name, String country, String imageUrl, String description) {
        this.name = name;
        this.country = country;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    /**
     * Parcel Constructor. This allows a {@code City} object to be passed to different Activities
     * or saved across orientation changes
     * @param in    The Parcel
     */
    public City(Parcel in) {
        this();
        int contents = in.readInt();
        if (containsFlag(contents, FLAG_HAS_NAME))
            name = in.readString();
        if (containsFlag(contents, FLAG_HAS_COUNTRY))
            country = in.readString();
        if (containsFlag(contents, FLAG_HAS_IMAGE_URL))
            imageUrl = in.readString();
        if (containsFlag(contents, FLAG_HAS_DESCRIPTION))
            description = in.readString();

    }

    @Override
    public int describeContents() {
        return  (name == null ? 0 : FLAG_HAS_NAME) |
                (country == null ? 0 : FLAG_HAS_COUNTRY) |
                (imageUrl == null ? 0 : FLAG_HAS_IMAGE_URL) |
                (description == null ? 0 : FLAG_HAS_DESCRIPTION);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int contents = describeContents();
        dest.writeInt(contents);
        if (containsFlag(contents, FLAG_HAS_NAME))
            dest.writeString(name);
        if (containsFlag(contents, FLAG_HAS_COUNTRY))
            dest.writeString(country);
        if (containsFlag(contents, FLAG_HAS_IMAGE_URL))
            dest.writeString(imageUrl);
        if (containsFlag(contents, FLAG_HAS_DESCRIPTION))
            dest.writeString(description);
    }

    /**
     * Shortcut method for determining if a content flag was bitwise or'd into the given contents
     * @param contents  the integer that contains all of the or'd flags
     * @param flag      the flag to check
     * @return          {@code true} iff {@code flag} was included in {@code contents}.
     */
    protected boolean containsFlag(int contents, int flag) {
        return (contents & flag) == flag;
    }

    /**
     * Get the name of this city
     * @return  the name of this city
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this city
     * @param name  the name of this city
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the country where this city is located
     * @return  the name of the country where this city is located
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country where this city is located
     * @param country   the name of the country where this city is located
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the URL of an image of this city
     * @return  a URL of an image of this city
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Set the URL of an image of this city
     * @param imageUrl  a URL of an image of this city
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Get a user-friendly description of this city
     * @return  a description of this city
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set a user-friendly description of this city
     * @param description   a description of this city
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "name:%s; country:%s; imageUrl:%s, description:%s", name, country, imageUrl, description);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + ((name == null) ? 0 : name.hashCode());
        result = 31 * result + ((country == null) ? 0 : country.hashCode());
        result = 31 * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
        result = 31 * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof City)) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }
}
