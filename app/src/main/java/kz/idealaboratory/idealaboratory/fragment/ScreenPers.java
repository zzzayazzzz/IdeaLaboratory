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

import kz.idealaboratory.idealaboratory.PersDetailActivity;
import kz.idealaboratory.idealaboratory.R;
import kz.idealaboratory.idealaboratory.models.Item;
import kz.idealaboratory.idealaboratory.view.SlidingTabLayout;
import kz.idealaboratory.idealaboratory.viewholder.PersViewHolder;

public class ScreenPers extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private String[] mPersTitles;
    public ScreenPers() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screen_pers, container, false);
        mPersTitles = getResources().getStringArray(R.array.pers_types_array);
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

        /**
         * Return the number of pages to display
         */
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPersTitles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int mPosition) {
            DatabaseReference mDatabase;

             FirebaseRecyclerAdapter<Item, PersViewHolder> mAdapter;
             RecyclerView mRecycler;
             LinearLayoutManager mManager;

            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_all_pers,
                    container, false);

            container.addView(view);

            mDatabase = FirebaseDatabase.getInstance().getReference();

            mRecycler = (RecyclerView) view.findViewById(R.id.pers_list);
            mRecycler.setHasFixedSize(false);

            mManager = new LinearLayoutManager(getActivity());
            mManager.setReverseLayout(true);
            mManager.setStackFromEnd(true);
            mRecycler.setLayoutManager(mManager);

            Query persQuery = getQuery(mDatabase, mPosition + 1);

            mAdapter = new FirebaseRecyclerAdapter<Item, PersViewHolder>(Item.class, R.layout.item_pers,
                    PersViewHolder.class, persQuery) {
                @Override
                protected void populateViewHolder(final PersViewHolder viewHolder, final Item model, final int position) {
                    final DatabaseReference persRef = getRef(position);
                    // Set click listener for the whole post view
                    final String persKey = persRef.getKey();
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Launch PersDetailActivity
                            //TODO
                            //Toast.makeText(getContext(), Integer.toString(model.Type) , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), PersDetailActivity.class);
                            intent.putExtra(PersDetailActivity.EXTRA_PERS_KEY, persKey);
                            intent.putExtra(PersDetailActivity.EXTRA_PERS_TYPE, mPosition + 1);
                            startActivity(intent);
                        }
                    });

                    // Bind Pers to ViewHolder, setting OnClickListener
                    viewHolder.bindToPers(model);
                }
            };
            mRecycler.setAdapter(mAdapter);
            // Return the View
            return view;

        }

        /**
         * Destroy the item from the ViewPager. In our case this is simply
         * removing the View.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    public Query getQuery(DatabaseReference databaseReference, int qType) {

        Query recentPostsQuery = databaseReference.child("Pers").orderByChild("Type").startAt(qType).endAt(qType);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}