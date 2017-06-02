package brewery.giacomo.com.mybrewery.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import brewery.giacomo.com.mybrewery.R;
import brewery.giacomo.com.mybrewery.model.Beer;
import brewery.giacomo.com.mybrewery.model.Lables;

/**
 * Created by giaco on 29/05/2017.
 */

public class listAdapter extends ArrayAdapter<Beer> {
    private static final String TAG = listAdapter.class.getSimpleName();
    private Context context;

    public listAdapter(Context contexts, int resource, List<Beer> values) {
        super(contexts, resource, values);
        context = contexts;
    }
    /*
    * displaying quick data of journeys in a simple listview*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView created = (TextView) rowView.findViewById(R.id.created);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
        Beer singleBeer = getItem(position);
        name.setText(singleBeer.getName());
        created.setText(singleBeer.getCreateDate());
        if(singleBeer.getIconPath().equalsIgnoreCase("null")) {
            icon.setImageResource(R.drawable.icon);
        }else {
            Bitmap iconBt = BitmapFactory.decodeFile(singleBeer.getIconPath());
            BitmapDrawable iconSr = new BitmapDrawable(context.getResources(), iconBt);
            icon.setBackgroundDrawable(iconSr);
        }
        return rowView;
    }
}
