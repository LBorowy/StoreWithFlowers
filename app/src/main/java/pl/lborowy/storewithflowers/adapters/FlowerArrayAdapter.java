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

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private OnItemClicked onItemClicked;

    public FlowerArrayAdapter(Context context, List<Flower> flowerList, OnItemClicked onItemClicked) {
        super(context, R.layout.flower_item);
        // pojedynczy widok widok
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.flowerList = flowerList;
        this.onItemClicked = onItemClicked;

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
        final Flower flower = flowerList.get(position);

        View rowView = convertView;
        if (rowView == null)
            rowView = inflater.inflate(R.layout.flower_item, parent, false);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnRowClickListener(view, flower);
            }
        });

        ViewHolder holder = (ViewHolder) rowView.getTag();
        if (holder == null) {
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        }

//        TextView nameText = (TextView) rowView.findViewById(R.id.flowerItem_nameText);
//        TextView priceText = (TextView) rowView.findViewById(R.id.flowerItem_prizeText);
//        ImageView flowerImage = (ImageView) rowView.findViewById(R.id.flowerItem_imageView);

        holder.nameText.setText(flower.getName());
        holder.priceText.setText(String.format("%.2f $", flower.getPrice()));
//        flowerImage.setImageResource(Integer.parseInt("http://services.hanselandpetal.com/photos/" + flower.getPhoto()));

        Picasso.with(getContext())
                .load(flower.getPfotoUrl())
                .resize(imageSizePixels, imageSizePixels)
                .centerCrop()
                .into(holder.flowerImage);

        holder.priceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnPriceClickListener(view, flower);
            }
        });

        return rowView;
    }

    private void setOnPriceClickListener(View view, Flower flower) {
        if (view != null) {
            onItemClicked.onPriceClicked(flower); // klikniecie na cene
        }
    }

    private void setOnRowClickListener(View view, Flower flower) {
        if (view != null) {
            onItemClicked.onRowClicked(flower); // klikniecie na caly element
        }
    }

    @Override
    public int getCount() {
        return flowerList.size();
    }

    @Override
    public boolean isEmpty() {
        return flowerList.isEmpty();
    }

    // co sie dzieje z Flower
    public interface OnItemClicked {
        void onRowClicked(Flower flower);

        void onPriceClicked(Flower flower);
    }

    class ViewHolder {
        @BindView(R.id.flowerItem_nameText)
        TextView nameText;

        @BindView(R.id.flowerItem_prizeText)
        TextView priceText;

        @BindView(R.id.flowerItem_imageView)
        ImageView flowerImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
