package self.philbrown.cityexplorer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import self.philbrown.cityexplorer.R;
import self.philbrown.cityexplorer.model.City;

/**
 * Simple List Adapter for displaying the cities
 *
 * @author Phil Brown
 * @since 12:46 PM Jul 30, 2015
 */
public class CitiesListAdapter extends ArrayAdapter<City> {

    /**
     * Reusable LayoutInflater
     */
    private LayoutInflater mLayoutInflater;

    /**
     * Constructor
     * @param context   Used to instantiate new views
     * @param cities    the list of cities to display
     */
    public CitiesListAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.cell_city, parent, false);
            holder = new ViewHolder();
            holder.city = (TextView) convertView.findViewById(R.id.city);
            holder.country = (TextView) convertView.findViewById(R.id.country);
            holder.description = (TextView) convertView.findViewById(R.id.description);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        City city = getItem(position);
        holder.city.setText(city.getName());
        holder.country.setText(city.getCountry());
        holder.description.setText(city.getDescription());
        convertView.setTag(holder);
        return convertView;
    }

    /**
     * Simple ViewHolder for easy cell reuse (without expensive lookups)
     */
    public class ViewHolder {

        /**
         * TextView for displaying the city name
         */
        TextView city;

        /**
         * TextView for displaying the country name
         */
        TextView country;

        /**
         * TextView for displaying some quick info about the city.
         */
        TextView description;
    }
}
