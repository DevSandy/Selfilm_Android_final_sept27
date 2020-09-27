package com.accelstackace.selfilmindia.Prime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.accelstackace.selfilmindia.Prime.ottplayer;
import com.accelstackace.selfilmindia.R;
import com.accelstackace.selfilmindia.SimpleClasses.Variables;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class tabthreeott extends Fragment {

    ArrayList<Discover_Get_Set> datalist;
    Discover_Adapter adapterr;
    RecyclerView recyclerView;
    SliderView sliderView;
    private SliderAdapterExample adapter;
    JSONArray latestmoviesarray;

    View view;

    public tabthreeott() {
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
        view= inflater.inflate(R.layout.fragment_tabthreeott, container, false);
        sliderView = view.findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        datalist=new ArrayList<>();


        recyclerView = (RecyclerView) view.findViewById(R.id.recylerview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapterr=new Discover_Adapter(getContext(), datalist, new Discover_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<Home_Get_Sett> datalist, int postion) {

                OpenWatchVideo(postion,datalist);

            }
        });

        recyclerView.setAdapter(adapterr);

        ApiGetMovies();

        return view;

    }


    private void OpenWatchVideo(int postion, ArrayList<Home_Get_Sett> datalist) {
        Intent intent = new Intent(getContext(),ottplayer.class);
        intent.putExtra("Movieid",datalist.get(postion).Movie_Id);
        intent.putExtra("MovieName",datalist.get(postion).Movie_Name);
        intent.putExtra("MovieUrl",datalist.get(postion).Movie_URL);
        intent.putExtra("Views",datalist.get(postion).Total_Views);
        intent.putExtra("Description",datalist.get(postion).Description);
        intent.putExtra("Language",datalist.get(postion).Movie_Language);
        intent.putExtra("LaunchDate",datalist.get(postion).Launched_Date);
        intent.putExtra("MovieType",datalist.get(postion).Movie_Type);
        intent.putExtra("MovieThum",datalist.get(postion).Movie_Thum);
        intent.putExtra("Created",datalist.get(postion).Created);
        intent.putExtra("HeroName",datalist.get(postion).Male_Actor);
        intent.putExtra("HeroThum",datalist.get(postion).Male_Actor_Thumb);
        intent.putExtra("HeroineName",datalist.get(postion).Female_Actor);
        intent.putExtra("HeroineThum",datalist.get(postion).Female_Actor_Thumb);
        intent.putExtra("DirectorName",datalist.get(postion).Director);
        intent.putExtra("DirectorThum",datalist.get(postion).Director_Thumb);
        intent.putExtra("ProducerName",datalist.get(postion).Producer);
        intent.putExtra("ProducerThum",datalist.get(postion).Producer_Thumb);
        intent.putExtra("MusicName",datalist.get(postion).Music);
        intent.putExtra("MusicThum",datalist.get(postion).Music_Thumb);
        intent.putExtra("OtherCast",datalist.get(postion).Other_Cast);
        getContext().startActivity(intent);    }

    public void openottplayer(){
        Intent intent = new Intent(getContext(), ottplayer.class);
        startActivity(intent);

    }
    public void ApiGetMovies(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();


        try {
            //input your API parameters
            object.put("fb_id", Variables.user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = Variables.GetKidsandcomedy;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject=response;
                            String code=jsonObject.optString("code");
                            if(code.equals("200")){
                                JSONArray msgArray=jsonObject.getJSONArray("msg");
                                JSONObject adsobject=msgArray.optJSONObject(0);
                                JSONArray adsarray = adsobject.getJSONArray("sections_videos");

                                for (int i = 0; i < adsarray.length(); i++){
                                    JSONObject itemdata = adsarray.optJSONObject(i);
                                    JSONObject castdetails = itemdata.getJSONObject("Movie_Cast");
                                    SliderItem sliderItems = new SliderItem();
                                    sliderItems.setDescription(itemdata.optString("Description"));
                                    sliderItems.setImageUrl(itemdata.optString("Movie_Thum"));
                                    sliderItems.setViews(itemdata.optString("Total_Views"));
                                    sliderItems.setLanguage(itemdata.optString("Movie_Language"));
                                    sliderItems.setLaunchedDate(itemdata.optString("Launched_Date"));
                                    sliderItems.setMovieType(itemdata.optString("Movie_Type"));
                                    sliderItems.setMovieId(itemdata.optString("Movie_Id"));
                                    sliderItems.setMovieUrl(itemdata.optString("Movie_URL"));
                                    sliderItems.setMovieCreated(itemdata.optString("Created"));
                                    sliderItems.setMovieName(itemdata.optString("Movie_Name"));
                                    sliderItems.setOtherCast(castdetails.optString("Other_Cast"));
                                    sliderItems.setProducer(castdetails.optString("Producer"));
                                    sliderItems.setMusicThum(castdetails.optString("Music_Thumb"));
                                    sliderItems.setmaleActor(castdetails.optString("Female_Actor"));
                                    sliderItems.setMusic(castdetails.optString("Music"));
                                    sliderItems.setDirectorName(castdetails.optString("Director"));
                                    sliderItems.setmaleActorThumb(castdetails.optString("Male_Actor_Thumb"));
                                    sliderItems.setmaleActor(castdetails.optString("Male_Actor"));
                                    sliderItems.setDirectorThum(castdetails.optString("Director_Thumb"));
                                    sliderItems.setFemaleActorThum(castdetails.optString("Female_Actor_Thumb"));
                                    sliderItems.setProducerThum(castdetails.optString("Producer_Thumb"));

                                    adapter.addItem(sliderItems);

                                }


                                for (int d=1;d<msgArray.length();d++) {

                                    Discover_Get_Set discover_get_set=new Discover_Get_Set();
                                    JSONObject discover_object=msgArray.optJSONObject(d);
                                    discover_get_set.title=discover_object.optString("section_name");

                                    JSONArray video_array=discover_object.optJSONArray("sections_videos");

                                    ArrayList<Home_Get_Sett> video_list = new ArrayList<>();
                                    for (int i = 0; i < video_array.length(); i++) {
                                        JSONObject itemdata = video_array.optJSONObject(i);
                                        Home_Get_Sett item = new Home_Get_Sett();


                                        JSONObject user_info = itemdata.optJSONObject("Movie_Cast");
                                        item.Other_Cast = user_info.optString("Other_Cast");
                                        item.Producer = user_info.optString("Producer");
                                        item.Music_Thumb = user_info.optString("Music_Thumb");
                                        item.Female_Actor = user_info.optString("Female_Actor");
                                        item.Music = user_info.optString("Music");
                                        item.Director =user_info.optString("Director");
                                        item.Male_Actor_Thumb =user_info.optString("Male_Actor_Thumb");
                                        item.Male_Actor =user_info.optString("Male_Actor");
                                        item.Director_Thumb =user_info.optString("Director_Thumb");
                                        item.Female_Actor_Thumb =user_info.optString("Female_Actor_Thumb");
                                        item.Producer_Thumb =user_info.optString("Producer_Thumb");
                                        //ouut
                                        item.Total_Views = itemdata.optString("Total_Views");
                                        item.Movie_Language = itemdata.optString("Movie_Language");
                                        item.Launched_Date = itemdata.optString("Launched_Date");
                                        item.Movie_Type = itemdata.optString("Movie_Type");
                                        item.Description = itemdata.optString("Description");
                                        item.Movie_Id = itemdata.optString("Movie_Id");
                                        item.Movie_URL = itemdata.optString("Movie_URL");
                                        item.Movie_Thum = itemdata.optString("Movie_Thum");
                                        item.Created = itemdata.optString("Created");
                                        item.Movie_Name = itemdata.optString("Movie_Name");

                                        video_list.add(item);
                                    }

                                    discover_get_set.arrayList=video_list;

                                    datalist.add(discover_get_set);

                                }

                                adapterr.notifyDataSetChanged();

                            }else {
                                Toast.makeText(getContext(), ""+jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}