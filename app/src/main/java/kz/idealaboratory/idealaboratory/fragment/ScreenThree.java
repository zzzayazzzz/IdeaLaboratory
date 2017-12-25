package kz.idealaboratory.idealaboratory.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.idealaboratory.idealaboratory.R;

public class ScreenThree extends Fragment {

    public ScreenThree() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screen_third, container,
                false);

        return rootView;
    }

}
