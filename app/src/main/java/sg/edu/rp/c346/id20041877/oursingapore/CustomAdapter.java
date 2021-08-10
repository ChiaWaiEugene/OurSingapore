package sg.edu.rp.c346.id20041877.oursingapore;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context context;
    int layout_id;
    ArrayList<Island> IslandList;

    public CustomAdapter(Context context, int resource, ArrayList<Island> IslandList) {
        super(context, resource, IslandList);

        this.context = context;
        this.layout_id = resource;
        this.IslandList = IslandList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvDescription = rowView.findViewById(R.id.tvDescription);
        TextView tvSquareKm = rowView.findViewById(R.id.tvSquareKm);
        RatingBar rbStars = rowView.findViewById(R.id.rbStars);

        Island ItemI = IslandList.get(position);

        tvName.setText(ItemI.getName());
        tvDescription.setText(ItemI.getDescription());
        tvSquareKm.setText(String.valueOf(ItemI.getSquareKm()));
        rbStars.setRating((ItemI.getStars()));

        tvName.setTextColor(Color.BLUE);
        tvDescription.setTextSize(20f);
        tvDescription.setTextColor(Color.rgb(117,255,255));
        tvSquareKm.setTextColor(Color.GRAY);

        /*
        if (Item.getStars() >= 4) {
            ivNew.setVisibility(View.VISIBLE);
        }
        else {
            ivNew.setVisibility(View.INVISIBLE);
        }
         */

        return rowView;
    }


}
