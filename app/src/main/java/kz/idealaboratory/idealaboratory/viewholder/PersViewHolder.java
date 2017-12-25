package kz.idealaboratory.idealaboratory.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kz.idealaboratory.idealaboratory.R;
import kz.idealaboratory.idealaboratory.models.Item;

public class PersViewHolder extends RecyclerView.ViewHolder {

    public TextView nameView;
    public TextView hourlyView;
    //public ImageView starView;
    public TextView countView;
    public TextView shiftView;

    public PersViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.pers_name);
        hourlyView = (TextView) itemView.findViewById(R.id.pers_hourly);
        countView = (TextView) itemView.findViewById(R.id.pers_count);
        shiftView = (TextView) itemView.findViewById(R.id.pers_shift);
    }

    public void bindToPers(Item pers) {
        nameView.setText(pers.Name);

//        hourlyView.setText(pers.Hourly);
        //TODO
        //countView.setText(String.valueOf(pers.count));
//        shiftView.setText(pers.Shift);

        //starView.setOnClickListener(starClickListener);
    }
}
