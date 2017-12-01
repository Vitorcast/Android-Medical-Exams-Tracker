package thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.MainActivity;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Patient;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Test;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.R;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.App;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.DB;

/**
 * Created by thiag on 30/11/2017.
 */

public class AddTestFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spPatient;
    EditText edBPH, edBPL, edTemperature, edtPatient;
    Patient patient;
    Test test;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.add_test_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Test");

        setUI();
        setPatientData();

    }
    void setUI(){
        spPatient = getView().findViewById(R.id.spPatient);
        edBPH = getView().findViewById(R.id.edBPH);
        edBPL = getView().findViewById(R.id.edBPL);
        edTemperature = getView().findViewById(R.id.edTemperature);
        edtPatient = getView().findViewById(R.id.edtPatient);

        if(((MainActivity)getActivity()).test == null){
            edtPatient.setVisibility(View.INVISIBLE);
            spPatient.setEnabled(true);
        } else {
            test = ((MainActivity)getActivity()).test;
            edBPH.setText(test.bph.toString());
            edBPL.setText(test.bpl.toString());
            edTemperature.setText(test.temperature.toString());

            patient = DB.database.patientDao().getPatientById(((MainActivity)getActivity()).test.patientId);
            edtPatient.setText(patient.firstName + " " + patient.lastName);
            edtPatient.setVisibility(View.VISIBLE);
            spPatient.setEnabled(false);
        }
    }

    void setPatientData(){

        spPatient.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_spinner_item, DB.database.patientDao().getAllPatients());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPatient.setAdapter(aa);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onClick(View view){
        Log.i("SELECTED ITEM",spPatient.getSelectedItem().toString());
        String[] fullName = spPatient.getSelectedItem().toString().split(" ");

        Integer selectedId;

        if (patient != null){
            test.temperature = Integer.parseInt(edTemperature.getText().toString());
            test.bph = Integer.parseInt(edBPH.getText().toString());
            test.bpl = Integer.parseInt(edBPL.getText().toString());
            test.nurseId = ((App)getActivity().getApplication()).userId;

            DB.database.testDao().updateUser(test);
        } else {
            selectedId = DB.database.patientDao().getPatientByName(fullName[0], fullName[1]).id;

            DB.database.testDao().insertUser(
                    new Test(Integer.parseInt(edBPL.getText().toString()),
                            Integer.parseInt(edBPH.getText().toString()),
                            Integer.parseInt(edTemperature.getText().toString()),
                            ((App)getActivity().getApplication()).userId,
                            selectedId));
        }

        edBPH.setText("");
        edBPL.setText("");
        edTemperature.setText("");
        edtPatient.setText("");
        test = null;
        ((MainActivity)getActivity()).test = null;

        Toast.makeText(getView().getContext(), "Test Registered", Toast.LENGTH_SHORT).show();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment f = new ReadTestFragment();
            ft.replace(R.id.content_frame, f);
            ((MainActivity)getActivity()).currentFragment = f;
        ft.commit();
    }
}
