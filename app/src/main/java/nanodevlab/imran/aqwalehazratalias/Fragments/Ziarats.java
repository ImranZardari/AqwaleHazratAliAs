package nanodevlab.imran.aqwalehazratalias.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Adapters.DuaAdapter;
import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.DataSource.DataSource;
import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Databasehelper.Databasehelper;
import nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Models.DuaModel;
import nanodevlab.imran.aqwalehazratalias.R;


public class Ziarats extends Fragment {

    private RecyclerView ziaratRecyclerView;
    private DuaAdapter ziaratAdapter;
    private List<DuaModel> ziaratModelList = new ArrayList<>();


    public Ziarats() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ziarats, container, false);


        DataSource duaDataSource = new DataSource(getContext());
        ziaratModelList=duaDataSource.ZiaratModelWithTranslation();


        ziaratRecyclerView = view.findViewById(R.id.ziaratRecyclerView);
        ziaratRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ziaratRecyclerView.setHasFixedSize(true);
        ziaratRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ziaratAdapter = new DuaAdapter(ziaratModelList);
        ziaratRecyclerView.setAdapter(ziaratAdapter);



        return view;
    }



}