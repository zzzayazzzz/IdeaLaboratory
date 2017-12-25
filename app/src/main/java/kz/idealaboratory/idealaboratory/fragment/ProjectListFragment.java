package kz.idealaboratory.idealaboratory.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import kz.idealaboratory.idealaboratory.MainActivity;
import kz.idealaboratory.idealaboratory.R;
import kz.idealaboratory.idealaboratory.SignInActivity;
import kz.idealaboratory.idealaboratory.models.Project;
import kz.idealaboratory.idealaboratory.viewholder.ProjectViewHolder;

import static android.content.Context.MODE_PRIVATE;

public class ProjectListFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "ProjectListFragment";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    private Button newProjectButton;
    private EditText newProjectTitle;
    private String username;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Project, ProjectViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private ProgressDialog dialog;

    public ProjectListFragment() {}

    public static ProjectListFragment newInstance(int index) {
        ProjectListFragment f = new ProjectListFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_all_projects, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Подождите");
        dialog.setMessage("Идет загрузка проектов...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        newProjectButton = (Button) rootView.findViewById(R.id.new_project_button);
        newProjectButton.setOnClickListener(this);
        newProjectTitle = (EditText) rootView.findViewById(R.id.new_project_title);


        //username = this.getArguments().getString("username");

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.project_list);
        mRecycler.setHasFixedSize(false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        username = prefs.getString("username", "No username");

        Log.d("heoooo", "user: "+username);
        Log.d(TAG, "Hi!");

        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getContext(),"Привет, " + username + "!" , Toast.LENGTH_SHORT).show();
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query


        Query projectQuery = getQuery(mDatabase);

        projectQuery = projectQuery.getRef();

        mAdapter = new FirebaseRecyclerAdapter<Project, ProjectViewHolder>(Project.class, R.layout.item_project,
                ProjectViewHolder.class, projectQuery) {
            @Override
            protected void populateViewHolder(final ProjectViewHolder viewHolder, final Project model, final int position) {
                final DatabaseReference projectRef = getRef(position);

                // Set click listener for the whole post view
                final String projectKey = projectRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity

                        //TODO

                        //startActivity(new Intent(ProjectListFragment.super.getActivity(), MainActivity.class).putExtra("username", username));
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        //intent.putExtra(MainActivity.EXTRA_PROJECT_KEY, projectKey);
                        bindToProj(projectKey);
                        startActivity(intent);
                    }
                });

                // Bind Post to ViewHolder, setting OnClickListener
                viewHolder.bindToProject(model);
                dialog.dismiss();
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery(DatabaseReference databaseReference) {
        Query openProjectsQuery = databaseReference.child("Projects").orderByChild("isClosed").startAt(0).endAt(0);
        return openProjectsQuery;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.new_project_button) {
            createProject(newProjectTitle.getText().toString());
        }
    }

    private void createProject(final String projectTitle){

        FirebaseDatabase.getInstance().getReference().child("Projects")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        Project newProject = new Project(projectTitle, new ArrayList<String>(Arrays.asList(username)));


                        DatabaseReference newProjectRef = FirebaseDatabase.getInstance().getReference()
                                .child("Projects").push().getRef();

                        newProjectRef.setValue(newProject);

                        // Clear the field
                        newProjectTitle.setText(null);

                        newProjectRef.push().setValue(username);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
    public void bindToProj(String key){
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("key", key);
        editor.apply();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event

        // Handle action buttons
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), SignInActivity.class));
            getActivity().finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
