package au.edu.canberra.mt_a1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by MichaelRajchert on 31/03/2017.
 */

class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, List<String> item) {
        super(context, R.layout.custom_database_row, item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_database_row, parent, false);

        String contactItem = getItem(position);
        TextView rowTextView = (TextView) customView.findViewById(R.id.CustomDBRow_Text);

        rowTextView.setText(contactItem);

        return customView;
    }
}
