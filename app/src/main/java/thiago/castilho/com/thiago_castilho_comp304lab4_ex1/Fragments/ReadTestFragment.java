package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.MainActivity;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Patient;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Test;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.R;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.App;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.DB;

/**
 * Created by thiag on 30/11/2017.
 */

public class ReadTestFragment extends Fragment {

    ListView lvPatientTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.read_test_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Read Test");

        setUI();
        setListData();
    }

    void setUI(){
        lvPatientTest = getView().findViewById(R.id.lvPatientTests);
        lvPatientTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] title = ((Map<String, String>)((ListView) adapterView).getAdapter().getItem(i)).get("subTitle").split("|");
                Integer testId = Integer.parseInt(title[1]);

                ((MainActivity) getActivity()).test = DB.database.testDao().getTestById(testId);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new AddTestFragment());
                ft.commit();

            }
        });
    }

    void setListData(){
        List<Map<String,String>> data  = new ArrayList<>();
        for(Test test: DB.database.testDao().getAllTests()){
            Map<String, String> datum = new HashMap<String, String>(2);
            Patient patient = DB.database.patientDao().getPatientById(test.patientId);
            datum.put("title", patient.id + ": " + patient.firstName + " " + patient.lastName );
            datum.put("subTitle", test.id + "# " + "BPH: " + test.bph + " BPL: " + test.bpl + " Temperature: " + test.temperature );
            data.add(datum);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "subTitle"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        lvPatientTest.setAdapter(adapter);
    }
}
