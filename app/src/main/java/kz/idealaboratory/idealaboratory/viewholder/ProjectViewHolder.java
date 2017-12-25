package kz.idealaboratory.idealaboratory.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kz.idealaboratory.idealaboratory.R;
import kz.idealaboratory.idealaboratory.models.Project;

public class ProjectViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;


    public ProjectViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.project_title);
    }

    public void bindToProject(Project project) {
        titleView.setText(project.title);

//        hourlyView.setText(tech.Hourly);
        //TODO
        //countView.setText(String.valueOf(tech.count));
//        shiftView.setText(tech.Shift);

        //starView.setOnClickListener(starClickListener);
    }
}
