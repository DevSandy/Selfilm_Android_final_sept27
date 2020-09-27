package com.accelstackace.selfilmindia.SimpleClasses;

import android.content.SharedPreferences;
import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by AQEEL on 2/15/2019.
 */

public class Variables {


    public static final String device="android";

    public static int screen_width;
    public static int screen_height;

    public static final String SelectedAudio_AAC ="SelectedAudio.aac";

    public static final String root= Environment.getExternalStorageDirectory().toString();
    public static final String app_hided_folder =root+"/.HidedSelfilm/";
    public static final String app_showing_folder =root+"/Selfilm/";
    public static final String draft_app_folder= app_hided_folder +"Draft/";


    public static int max_recording_duration=18000;
    public static int recording_duration=18000;
    public static int min_time_recording=5000;

    public static String output_frontcamera= app_hided_folder + "output_frontcamera.mp4";
    public static String outputfile= app_hided_folder + "output.mp4";
    public static String outputfile2= app_hided_folder + "output2.mp4";
    public static String output_filter_file= app_hided_folder + "output-filtered.mp4";
    public static String gallery_trimed_video= app_hided_folder + "gallery_trimed_video.mp4";
    public static String gallery_resize_video= app_hided_folder + "gallery_resize_video.mp4";



    public static SharedPreferences sharedPreferences;
    public static final String pref_name="pref_name";
    public static final String u_id="u_id";
    public static final String u_name="u_name";
    public static final String u_pic="u_pic";
    public static final String f_name="f_name";
    public static final String l_name="l_name";
    public static final String gender="u_gender";
    public static final String islogin="is_login";
    public static final String device_token="device_token";
    public static final String api_token="api_token";
    public static final String device_id="device_id";
    public static final String uploading_video_thumb="uploading_video_thumb";

    public static String user_id;
    public static String user_name;
    public static String user_pic;



    public static String tag="selfilm";

    public static String Selected_sound_id="null";

    public static  boolean Reload_my_videos=true;
    public static  boolean Reload_my_videos_inner=true;
    public static  boolean Reload_my_likes_inner=true;
    public static  boolean Reload_my_notification=true;


    public static final String gif_firstpart="https://media.giphy.com/media/";
    public static final String gif_secondpart="/100w.gif";

    public static final String gif_firstpart_chat="https://media.giphy.com/media/";
    public static final String gif_secondpart_chat="/200w.gif";


    public static final SimpleDateFormat df =
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZZ", Locale.ENGLISH);

    public static final SimpleDateFormat df2 =
            new SimpleDateFormat("dd-MM-yyyy HH:mmZZ", Locale.ENGLISH);


    public static final boolean is_secure_info=false;
    public static final boolean is_remove_ads=true;
    public static final boolean is_show_gif=true;

    public static final boolean is_demo_app=false;

    // if you want to add a duet function into our project you have to set this value to "true"
    // and also get the extended apis
    public static final boolean is_enable_duet=true;



    public final static int permission_camera_code=786;
    public final static int permission_write_data=788;
    public final static int permission_Read_data=789;
    public final static int permission_Recording_audio=790;
    public final static int Pick_video_from_gallery=791;



    
  public static String gif_api_key1="giphy_api_key_here";

    public static final String privacy_policy="https://www.privacypolicygenerator.info/live.php?token=";


     public static final  String main_domain="http://domain.com/";
     public static final String base_url=main_domain+"API/";
     public static final String api_domain =base_url+"index.php?p=";


    public static final String SignUp = "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-signup";
    public static final String uploadVideo = "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-addvideoposts";
    public static final String showAllVideos = "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-showalllvideos";
    public static final String showMyAllVideos= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-showuserprofile";
    public static final String likeDislikeVideo= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-likedislikevideo";
    public static final String updateVideoView= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-updatevideoview";
    public static final String allSounds= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-showallsounds";
    public static final String fav_sound= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-addfavsound";
    public static final String my_FavSound= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-favsoundlist";
    public static final String my_liked_video= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-mylikedvideos";
    public static final String follow_users= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-followunfollowusers";
    public static final String discover= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-discover";
    public static final String showVideoComments= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-getvideocomments";
    public static final String postComment= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-postvideocomments";
    public static final String edit_profile= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-editprofile";
    public static final String get_user_data= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-getuserprofile";
    public static final String get_followers= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-showfollowers";
    public static final String get_followings= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-showfollowings";
    public static final String SearchByHashTag= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-hashtags";
    public static final String sendPushNotification= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-pushnotification";
    public static final String uploadImage= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-updateprofilepicture";
    public static final String DeleteVideo= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-deleteuservideo";
    public static final String search= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-search";
    public static final String getNotifications= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-getnotificationlist";
    public static final String getVerified= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-accountverification";
    public static final String downloadFile= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-downloadvideopost";
    public static final String reportvideopost= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-reportvideopost";
    public static final String offvideopost= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-offvideopost";
    public static final String Getmoviesforprime= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-premiummovies\n";
    public static final String GetShortMovies= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-shortvideos";
    public static final String GetKidsandcomedy= "https://ng4q4b6th7.execute-api.ap-south-1.amazonaws.com/selfilm/selfilm-comedyvideos";

}
