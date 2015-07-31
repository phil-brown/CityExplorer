package self.philbrown.cityexplorer;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import self.philbrown.cityexplorer.model.City;

/**
 * Detailed view for displaying City data.
 *
 * @author Phil Brown
 * @since 5:27 PM Jul 30, 2015
 */
public class CityDetailFragment extends Fragment {

    /**
     * Specifies the arguments key for the given city value
     */
    public static final String ARGUMENT_CITY = "city";

    /**
     * Reusable Picasso instance for loading images
     */
    private Picasso mPicasso;

    /**
     * The city to display details about
     */
    private City mCity;

    /**
     * Create a new {@code CityDetailFragment}
     * @param city  the city to display
     * @return      a CityDetailFragment for showing details about the give city.
     */
    public static CityDetailFragment newInstance(City city) {
        CityDetailFragment fragment = new CityDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_CITY, city);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicasso = new Picasso.Builder(getActivity()).loggingEnabled(BuildConfig.DEBUG).indicatorsEnabled(BuildConfig.DEBUG).build();
        mCity = getArguments().getParcelable(ARGUMENT_CITY);
        updateActionBarTitle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_detail, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView description = (TextView) view.findViewById(R.id.description);
        mPicasso.load(mCity.getImageUrl()).placeholder(R.drawable.loading).into(image);
        description.setText(mCity.getDescription());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        updateActionBarTitle();
    }

    /**
     * Update the Action Bar title by setting it to the name of the location.
     */
    private void updateActionBarTitle() {
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null)
            actionBar.setTitle(String.format(Locale.US, "%s, %s", mCity.getName(), mCity.getCountry()));
    }
}
