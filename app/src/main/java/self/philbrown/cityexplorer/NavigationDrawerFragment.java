package self.philbrown.cityexplorer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import self.philbrown.cityexplorer.model.City;
import self.philbrown.cityexplorer.ui.CitiesListAdapter;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Key for remembering the parsed cities
     */
    private static final String STATE_CITIES = "cities_list";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private MasterViewCallbacks mCallbacks;

    private ArrayList<City> cities;

    /**
     * Used for running an AsyncTask on a separate executor.
     */
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * The parent layout for non-tablets in landscape mode
     */
    private DrawerLayout mDrawerLayout;

    /**
     * The list adapter for displaying simple data about our places
     */
    private CitiesListAdapter mCitiesListAdapter;

    /**
     * The navigation drawer list view
     */
    private ListView mDrawerListView;

    /**
     * The view that contains this fragment
     */
    private View mFragmentContainerView;

    /**
     * Currently-selected position
     */
    private int mCurrentSelectedPosition = 0;

    /**
     * Whether or not the user has "learned" about using the nav drawer in the app
     */
    private boolean mFromSavedInstanceState;

    /**
     * Whether or not the user has "learned" about using the nav drawer in the app
     */
    private boolean mUserLearnedDrawer;

    /**
     * Whether or not parsing has completed. For small data sets, we can just rerun if not complete. For
     * larger sets a new model would be required to
     */
    private boolean parseCompleted = false;

    /**
     * Create a new instance of the NavigationDrawerFragment.
     * @return
     */
    public static NavigationDrawerFragment newInstance() {
        return new NavigationDrawerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
            cities = savedInstanceState.getParcelableArrayList(STATE_CITIES);

            // Select either the default item (0) or the last selected item.
            selectItem(mCurrentSelectedPosition);
        }
        if (cities == null) {
            cities = new ArrayList<>();
        }
        else {
            parseCompleted = true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mCitiesListAdapter = new CitiesListAdapter(getActivity(), cities);
        mDrawerListView.setAdapter(mCitiesListAdapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        if (!parseCompleted) {
            final Handler handler = new Handler();
            new ParseCitiesTask(getActivity(), "cities.txt", new CitiesParserCallbacks() {
                @Override
                public void onCityParsed(final City city) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cities.add(city);
                            mCitiesListAdapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onParseCompleted() {
                    parseCompleted = true;
                    selectItem(mCurrentSelectedPosition);
                    //TODO ability to resume parsing if incomplete (AsyncTaskFragment pattern). This will be useful for larger data sets.
                }
            }).executeOnExecutor(mExecutor);
        }

        return mDrawerListView;
    }

    /**
     * Whether of not the drawer is open
     * @return
     */
    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * called when an item is clicked in the lest.
     * @param position
     */
    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onCitySelected(cities.get(position));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (MasterViewCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
        if (parseCompleted && cities != null) {
            outState.putParcelableArrayList(STATE_CITIES, cities);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            //inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    /**
     * Called when the back button is pressed
     * @return  {@code true} if the even is absorbed by the fragment, otherwise {@code false}.
     */
    public boolean onBackPressed() {
        if (mDrawerLayout != null && isDrawerOpen()) {
            mDrawerLayout.closeDrawers();
            return true;
        }
        return false;
    }

    /**
     * Get the action bar
     * @return
     */
    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface MasterViewCallbacks {
        /**
         * Called when a city in the master view is selected.
         */
        void onCitySelected(City city);
    }

    /**
     * Callbacks interface used to listen for city csv parsing progress
     */
    public static interface CitiesParserCallbacks {
        /**
         * Called when a new city has been parsed
         * @param city  the new city
         */
        void onCityParsed(City city);

        /**
         * Called when the file has completed parsing
         */
        void onParseCompleted();
    }

    /**
     * Asynchronously parses the cities file
     */
    public static class ParseCitiesTask extends AsyncTask<Void,Void,Void> {

        /**
         * Context used to retrieve assets
         */
        private Context mContext;

        /**
         * the name of the asset to load
         */
        private String filePath;

        /**
         * Provides simple methods for the return trip.
         */
        private CitiesParserCallbacks callbacks;

        /**
         * Constructor
         * @param context
         * @param filePath
         * @param callbacks
         */
        public ParseCitiesTask(Context context, String filePath, CitiesParserCallbacks callbacks) {
            Log.e("CitiesListAdapter", "PopulateCitiesTask()");
            this.mContext = context;
            this.filePath = filePath;
            this.callbacks = callbacks;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                AssetManager assets = mContext.getAssets();
                InputStream stream = assets.open(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                City city;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split("\",\"");
                    if (data.length != 4) {
                        if (BuildConfig.DEBUG) {
                            throw new RuntimeException("Invalid data: \"" + line + "\"");
                        }
                        continue;
                    }
                    city = new City(data[0].substring(1), data[1], data[2], data[3].substring(0, data[3].length()-1));
                    if (callbacks != null)
                        callbacks.onCityParsed(city);
                }
                br.close();
                stream.close();
            } catch (IOException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (callbacks != null)
                callbacks.onParseCompleted();
        }
    }
}
