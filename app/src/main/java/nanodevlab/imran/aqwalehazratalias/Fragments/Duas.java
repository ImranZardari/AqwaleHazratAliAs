package nanodevlab.imran.aqwalehazratalias.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Adapters.DuaAdapter;
import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.DataSource.DataSource;
import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Models.DuaModel;
import nanodevlab.imran.aqwalehazratalias.R;

public class Duas extends Fragment {

    private RecyclerView dbRecyclerView;
    private DuaAdapter duaAdapter;
    private List<DuaModel> duaModelList = new ArrayList<>();


    public Duas() {

    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_duas, container, false);


        duaModelList = new DataSource(getContext()).DuaModelWithTranslation();

        dbRecyclerView = view.findViewById(R.id.dbRecyclerView);
        dbRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbRecyclerView.setHasFixedSize(true);
        dbRecyclerView.setItemAnimator(new DefaultItemAnimator());
        duaAdapter = new DuaAdapter(duaModelList);
        dbRecyclerView.setAdapter(duaAdapter);


        return view;
    }



}