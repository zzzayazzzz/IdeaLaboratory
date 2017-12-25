package kz.idealaboratory.idealaboratory.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kz.idealaboratory.idealaboratory.R;
import kz.idealaboratory.idealaboratory.models.Item;

public class TechViewHolder extends RecyclerView.ViewHolder {

    public TextView nameView;
    public TextView hourlyView;
    //public ImageView starView;
    public TextView countView;
    public TextView shiftView;

    public TechViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.tech_name);
        hourlyView = (TextView) itemView.findViewById(R.id.tech_hourly);
        countView = (TextView) itemView.findViewById(R.id.tech_count);
        shiftView = (TextView) itemView.findViewById(R.id.tech_shift);
    }

    public void bindToTech(Item item) {
        nameView.setText(item.Name);

//        hourlyView.setText(item.Hourly);
        //TODO
        //countView.setText(String.valueOf(item.count));
//        shiftView.setText(item.Shift);

        //starView.setOnClickListener(starClickListener);
    }
}
