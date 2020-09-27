package com.accelstackace.selfilmindia;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.accelstackace.selfilmindia.Main_Menu.MainMenuActivity;
import com.accelstackace.selfilmindia.SimpleClasses.Variables;
import com.accelstackace.selfilmindia.Video_Recording.Post_Video_A;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.facebook.FacebookSdk.getApplicationContext;


public class reportfragment extends Fragment {

    View view;
    Context context;

    String video_id;
    String reason;
    String user_id;


    FrameLayout reportscreen;
    Button reportbtn,offbtn;


    public reportfragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getContext();

        view= inflater.inflate(R.layout.fragment_reportfragment, container, false);



        Bundle bundle=getArguments();
        if(bundle!=null){
            video_id=bundle.getString("video_id");
            user_id=bundle.getString("user_id");

        }

        reportscreen=view.findViewById(R.id.reportscreen);


        reportscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });
        view.findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.reportbtn).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_layout);
                List<String> stringList=new ArrayList<>();  // here is list
                        stringList.add("Spam");
                        stringList.add("Nudity");
                        stringList.add("Violence");
                        stringList.add("Harassment");
                        stringList.add("False news");
                        stringList.add("Hate speech");
                        stringList.add("Terrorism");
                        stringList.add("Suicide or self-injury");
                RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radioGroup);
                Button repprtbutton =(Button)dialog.findViewById(R.id.btn_dialog);

                repprtbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reason==null){
                            Toast.makeText(context, "Please select option to proceed", Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.cancel();
                            hitapitoreport();
                        }
                    }
                });

                for(int i=0;i<stringList.size();i++){
                    RadioButton rb=new RadioButton(getContext()); // dynamically creating RadioButton and adding to RadioGroup.
                    rb.setText(stringList.get(i));
                    rb.setTextColor(R.color.black);
                    rg.addView(rb);
                }

                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int childCount = group.getChildCount();
                        for (int x = 0; x < childCount; x++) {
                            RadioButton btn = (RadioButton) group.getChildAt(x);
                            if (btn.getId() == checkedId) {
                                reason = btn.getText().toString();
                                Log.e("selected RadioButton->",btn.getText().toString());

                            }
                        }
                    }
                });
                dialog.show();
            }
        });
        view.findViewById(R.id.offbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitapitooff();
            }
        });
        return view;

    }

    public void hitapitooff(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();


        try {
            //input your API parameters
            object.put("my_fb_id", Variables.user_id);
            object.put("video_id",video_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = Variables.offvideopost;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Off successfull", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void hitapitoreport(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();


        try {
            //input your API parameters
            object.put("fb_id", Variables.user_id);
            object.put("video_id",video_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = Variables.reportvideopost;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Reported successfully", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}