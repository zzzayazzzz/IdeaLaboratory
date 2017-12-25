package kz.idealaboratory.idealaboratory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kz.idealaboratory.idealaboratory.models.Item;

public class TechDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "TechDetailActivity";

    public static final String EXTRA_TECH_KEY = "tech_key";
    public static final String EXTRA_TECH_TYPE = "tech_type";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private DatabaseReference mTechReference;
    //private DatabaseReference mCommentsReference;
    private ValueEventListener mTechListener;
    private String mTechKey;

    //private CommentAdapter mAdapter;

    private TextView mNameView;
    private TextView mCountView;
    private TextView mHourlyView;
    private TextView mShiftField;
    private Button mPlusButton;
    private Button mMinusButton;
    private Button addTech;
    private int count;
    //private RecyclerView mCommentsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_detail);
        count = 0;
        // Get post key from intent
        mTechKey = getIntent().getStringExtra(EXTRA_TECH_KEY);
        if (mTechKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        mTechReference = FirebaseDatabase.getInstance().getReference()
                .child("Tech").child(mTechKey);
        Log.d(TAG, mTechReference.toString());
        /*
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("post-comments").child(mPostKey);
*/
        // Initialize Views
        mNameView = (TextView) findViewById(R.id.tech_detail_name);
        mCountView = (TextView) findViewById(R.id.tech_count);
        mHourlyView = (TextView) findViewById(R.id.tech_hourly);
        mShiftField = (TextView) findViewById(R.id.tech_shift);
        addTech = (Button) findViewById(R.id.add_tech);

        addTech.setOnClickListener(this);
        //TODO
        //mPlusButton = (Button) findViewById(R.id.button_tech_plus);
        //mMinusButton = (Button) findViewById(R.id.button_tech_minus);

      //  mCommentButton.setOnClickListener(this);
      //  mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener techListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Item item = dataSnapshot.getValue(Item.class);
                // [START_EXCLUDE]
                Log.d(TAG, item.Name);
                mNameView.setText(item.Name);
                mHourlyView.setText("Цена за час: " + item.Hourly);
                mShiftField.setText("Цена за смену: " + item.Shift);
                mCountView.setText("Кол-во: " + item.count);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(TechDetailActivity.this, "Failed to load tech.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mTechReference.addValueEventListener(techListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mTechListener = techListener;

        // Listen for comments
       // mAdapter = new CommentAdapter(this, mCommentsReference);
      //  mCommentsRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mTechListener != null) {
            mTechReference.removeEventListener(mTechListener);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.add_tech) {
            addTech();
        } else if (i == R.id.plus_tech) {
            count++;
        } else if (i == R.id.minus_tech) {
            count++;
        }

    }

    private void addTech() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String projectKey = prefs.getString("key", "defaultStringIfNothingFound");
        Toast.makeText(this, FirebaseDatabase.getInstance().getReference().getRef().child("Project").child(projectKey).toString(), Toast.LENGTH_SHORT).show();

//        FirebaseDatabase.getInstance().getReference().getRef().child("Project").child(projectKey).toString();


    }
}
