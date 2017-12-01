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
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Doctor;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Patient;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Model.Test;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.R;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.App;
import thiago.castilho.com.thiago_castilho_comp304lab4_ex1.Util.DB;

/**
 * Created by thiag on 30/11/2017.
 */

public class PatientInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spDoctor;
    Patient patient = null;
    EditText edFirstName, edLastName, edDepartment, edRoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.patient_info_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Patient Info");

        setUI();
        setDoctorData();
    }

    void setUI(){
        spDoctor = getView().findViewById(R.id.spDoctor);
        edDepartment = getView().findViewById(R.id.edtDepartment);
        edFirstName = getView().findViewById(R.id.edtPatientFirstName);
        edLastName =getView().findViewById(R.id.edtPatientLastName);
        edRoom = getView().findViewById(R.id.edtRoom);
    }

    void setDoctorData(){
        spDoctor.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_spinner_item, DB.database.doctorDao().getAllDoctors());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDoctor.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void onClick(View view){

        String[] fullName = spDoctor.getSelectedItem().toString().split(" ");

        Integer selectedId;

        if (patient != null){
            patient.firstName = edFirstName.getText().toString();
            patient.lastName = edLastName.getText().toString();
            patient.department = edDepartment.getText().toString();
            patient.room = Integer.parseInt(edRoom.getText().toString());

            DB.database.patientDao().updateUser(patient);


        } else {
            selectedId = DB.database.doctorDao().getDoctorByName(fullName[0], fullName[1]).id;

            DB.database.patientDao().insertUser(
                    new Patient(edFirstName.getText().toString(),
                            edLastName.getText().toString(),
                            edDepartment.getText().toString(),
                            selectedId,
                            Integer.parseInt(edRoom.getText().toString())));
        }

        edFirstName.setText("");
        edLastName.setText("");
        edDepartment.setText("");
        edRoom.setText("");
        patient = null;

        Toast.makeText(getView().getContext(), "Patient Registered", Toast.LENGTH_SHORT).show();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment f = new ReadTestFragment();
        ft.replace(R.id.content_frame, f);
        ((MainActivity)getActivity()).currentFragment = f;
        ft.commit();
    }
}
