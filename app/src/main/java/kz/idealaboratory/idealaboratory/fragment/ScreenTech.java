package kz.idealaboratory.idealaboratory.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import kz.idealaboratory.idealaboratory.R;
import kz.idealaboratory.idealaboratory.TechDetailActivity;
import kz.idealaboratory.idealaboratory.models.Tech;
import kz.idealaboratory.idealaboratory.view.SlidingTabLayout;
import kz.idealaboratory.idealaboratory.viewholder.TechViewHolder;

public class ScreenTech extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private String[] mTechTitles;
    public ScreenTech() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screen_tech, container, false);
        mTechTitles = getResources().getStringArray(R.array.tech_types_array);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());

        // Give the SlidingTabLayout the ViewPager, this must be
        // done AFTER the ViewPager has had it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    // Adapter
    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 8;
        }

        /**
         * Return true if the value returned from is the same object as the View
         * added to the ViewPager.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTechTitles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int mPosition) {
            DatabaseReference mDatabase;

            FirebaseRecyclerAdapter<Tech, TechViewHolder> mAdapter;
            RecyclerView mRecycler;
            LinearLayoutManager mManager;

            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_all_tech,
                    container, false);

            container.addView(view);

            mDatabase = FirebaseDatabase.getInstance().getReference();

            mRecycler = (RecyclerView) view.findViewById(R.id.tech_list);
            mRecycler.setHasFixedSize(false);

            mManager = new LinearLayoutManager(getActivity());
            mManager.setReverseLayout(true);
            mManager.setStackFromEnd(true);
            mRecycler.setLayoutManager(mManager);

            Query techQuery = getQuery(mDatabase, mPosition + 1);

            mAdapter = new FirebaseRecyclerAdapter<Tech, TechViewHolder>(Tech.class, R.layout.item_tech,
                    TechViewHolder.class, techQuery) {
                @Override
                protected void populateViewHolder(final TechViewHolder viewHolder, final Tech model, final int position) {
                    final DatabaseReference techRef = getRef(position);
                    // Set click listener for the whole post view
                    final String techKey = techRef.getKey();
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Launch TechDetailActivity
                            //TODO
                            //Toast.makeText(getContext(), Integer.toString(model.Type) , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), TechDetailActivity.class);
                            intent.putExtra(TechDetailActivity.EXTRA_TECH_KEY, techKey);
                            startActivity(intent);
                        }
                    });
                    // Bind Tech to ViewHolder, setting OnClickListener
                    viewHolder.bindToTech(model);
                }
            };
            mRecycler.setAdapter(mAdapter);
            // Return the View
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    public Query getQuery(DatabaseReference databaseReference, int qType) {
        Query recentPostsQuery = databaseReference.child("Tech").orderByChild("Type").startAt(qType).endAt(qType);
        return recentPostsQuery;
    }
}