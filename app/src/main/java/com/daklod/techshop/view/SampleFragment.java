package com.daklod.techshop.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daklod.techshop.AllCategory;
import com.daklod.techshop.DTO.CUSTOMER;
import com.daklod.techshop.DTO.USER;
import com.daklod.techshop.LoginActivity;
import com.daklod.techshop.R;
import com.daklod.techshop.SearchActivity;
import com.daklod.techshop.controller.Api;
import com.daklod.techshop.controller.CheckAuthAPI;
import com.daklod.techshop.controller.LoginAPI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SampleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SampleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn;
    TextView username;
    public SampleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SampleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SampleFragment newInstance(String param1, String param2) {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn = getView().findViewById(R.id.btnLogout);
        username = getView().findViewById(R.id.username);
        CUSTOMER user = CheckAuthAPI.getUserFromToken();
        if (user != null){
            username.setText(user.getName());
        }
        else{
            username.setText("Null");
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAuthAPI.clearToken(getContext());
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }
}