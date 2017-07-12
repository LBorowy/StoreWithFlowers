package pl.lborowy.storewithflowers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.lborowy.storewithflowers.R;
import pl.lborowy.storewithflowers.models.Flower;

/**
 * Created by RENT on 2017-07-12.
 */

public class FlowerArrayAdapter extends ArrayAdapter<Flower> {

    // #8

    private final LayoutInflater inflater;
    private final int imageSizePixels;
    private List<Flower> flowerList;

    public FlowerArrayAdapter(Context context, List<Flower> flowerList) {
        super(context, R.layout.flower_item);
        // pojedynczy widok widok
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.flowerList = flowerList;

        imageSizePixels = context.getResources().getDimensionPixelSize(R.dimen.image_size);
    }

//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //// TODO: 2017-07-12 show view
//        return super.getView(position, convertView, parent);
//    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Flower flower = flowerList.get(position);

        View rowView = convertView;
        if(rowView == null)
            rowView = inflater.inflate(R.layout.flower_item, parent, false);

        TextView nameText = (TextView) rowView.findViewById(R.id.flowerItem_nameText);
        TextView priceText = (TextView) rowView.findViewById(R.id.flowerItem_prizeText);
        ImageView flowerImage = (ImageView) rowView.findViewById(R.id.flowerItem_imageView);

        nameText.setText(flower.getName());
        priceText.setText(String.format("%.2f $", flower.getPrice()));
//        flowerImage.setImageResource(Integer.parseInt("http://services.hanselandpetal.com/photos/" + flower.getPhoto()));

        Picasso.with(getContext())
                .load(flower.getPfotoUrl())
                .resize(imageSizePixels, imageSizePixels)
                .centerCrop()
                .into(flowerImage);

        return rowView;
    }

    @Override
    public int getCount() {
        return flowerList.size();
    }

    @Override
    public boolean isEmpty() {
        return flowerList.isEmpty();
    }


}
