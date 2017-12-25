package kz.idealaboratory.idealaboratory;

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

import kz.idealaboratory.idealaboratory.models.Pers;

public class PersDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PersDetailActivity";

    public static final String EXTRA_PERS_KEY = "pers_key";
    public static final String EXTRA_PERS_TYPE = "pers_type";

    private DatabaseReference mPersReference;
    //private DatabaseReference mCommentsReference;
    private ValueEventListener mPersListener;
    private String mPersKey;

    //private CommentAdapter mAdapter;

    private TextView mNameView;
    private TextView mCountView;
    private TextView mHourlyView;
    //private TextView mShiftField;
    private Button mPlusButton;
    private Button mMinusButton;
    //private RecyclerView mCommentsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pers_detail);

        // Get post key from intent
        mPersKey = getIntent().getStringExtra(EXTRA_PERS_KEY);
        if (mPersKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        mPersReference = FirebaseDatabase.getInstance().getReference()
                .child("Pers").child(mPersKey);
        Log.d(TAG, mPersReference.toString());
        /*
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("post-comments").child(mPostKey);
*/
        // Initialize Views
        mNameView = (TextView) findViewById(R.id.pers_detail_name);
        mCountView = (TextView) findViewById(R.id.pers_count);
        mHourlyView = (TextView) findViewById(R.id.pers_hourly);
        //mShiftField = (TextView) findViewById(R.id.pers_shift);
        //TODO
        //mPlusButton = (Button) findViewById(R.id.button_pers_plus);
        //mMinusButton = (Button) findViewById(R.id.button_pers_minus);

      //  mCommentButton.setOnClickListener(this);
      //  mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener persListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Pers pers = dataSnapshot.getValue(Pers.class);
                // [START_EXCLUDE]
                Log.d(TAG, pers.Name);
                mNameView.setText(pers.Name);
                mHourlyView.setText("Цена за час: " + pers.Hourly);
                //mShiftField.setText("Цена за смену: " + pers.Shift);
                mCountView.setText("Количество: " + pers.count);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(PersDetailActivity.this, "Failed to load pers.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPersReference.addValueEventListener(persListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPersListener = persListener;

        // Listen for comments
       // mAdapter = new CommentAdapter(this, mCommentsReference);
      //  mCommentsRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mPersListener != null) {
            mPersReference.removeEventListener(mPersListener);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        /*
        if (i == R.id.button_post_comment) {
            postComment();
        }
        */
    }
}
