package com.luanthanhthai.android.liteworkouttimer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Thai on 03.02.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class CreateRoutinesFragment extends Fragment {

    private RecyclerView mRoutineRecyclerView;
    private RoutineAdapter mRoutineAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_routines, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        mRoutineRecyclerView = (RecyclerView) view.findViewById(R.id.create_routine_recycler);
        mRoutineRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        // For backwards compatibility set this
        // near the end
        setHasOptionsMenu(true);

        return view;
    }

    private void updateUI() {
        RoutineSingleton routineSingleton = RoutineSingleton.get(getActivity());
        List<Routine> routines = routineSingleton.getRoutines();

        mRoutineAdapter = new RoutineAdapter(routines);
        mRoutineRecyclerView.setAdapter(mRoutineAdapter);
    }

    private class RoutineHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Routine mRoutine;
        private CheckBox mCheckedCheckBox;
        private TextView mTitleTextView;
        private TextView mDescrTextView;

        public RoutineHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mCheckedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_checkbox);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_routine_title);
            mDescrTextView = (TextView) itemView.findViewById(R.id.list_item_routine_description);
        }

        public void bindRoutine(Routine routine) {
            mRoutine = routine;
            mCheckedCheckBox.setChecked(mRoutine.isChecked());
            mTitleTextView.setText(mRoutine.getTitle());
            mDescrTextView.setText(mRoutine.getDescription());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mRoutine.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class RoutineAdapter extends RecyclerView.Adapter<RoutineHolder> {
        private List<Routine> mRoutines;

        public RoutineAdapter(List<Routine> routines) {
            mRoutines = routines;
        }

        @Override
        public RoutineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_routine, parent, false);

            return new RoutineHolder(view);
        }

        @Override
        public void onBindViewHolder(RoutineHolder holder, int position) {
            Routine routine = mRoutines.get(position);
            holder.bindRoutine(routine);
        }

        @Override
        public int getItemCount() {
            return mRoutines.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_routines, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ic_add:

                return true;

            case R.id.menu_ic_vertical:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
