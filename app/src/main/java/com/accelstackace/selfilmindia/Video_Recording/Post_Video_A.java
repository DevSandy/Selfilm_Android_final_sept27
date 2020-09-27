package com.accelstackace.selfilmindia.Video_Recording;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.accelstackace.selfilmindia.Main_Menu.MainMenuActivity;
import com.accelstackace.selfilmindia.R;
import com.accelstackace.selfilmindia.Services.ServiceCallback;
import com.accelstackace.selfilmindia.Services.Upload_Service;
import com.accelstackace.selfilmindia.SimpleClasses.Functions;
import com.accelstackace.selfilmindia.SimpleClasses.Variables;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static android.provider.Contacts.SettingsColumns.KEY;

public class Post_Video_A extends AppCompatActivity implements ServiceCallback,View.OnClickListener {


    ImageView video_thumbnail;
    ProgressDialog dialog;
    String video_path;
    String KEY = "AKIASB56EEFSELG4W75V";
    String soundthumb = "https://firebasestorage.googleapis.com/v0/b/selfilmandroid.appspot.com/o/selfilmlogo%2FCapture.PNG?alt=media&token=f6d22329-7cd5-4f86-8c3d-5b0bb8bc46fd";
    String SECRET = "lOJT9pbSBDiGvnw8XXw03nnfxlFzlVS7WiS7nZS2";
    String audiofilename = UUID.randomUUID().toString();
    String maudiopath= Environment.getExternalStorageDirectory().toString() + File.separator +"audio"+audiofilename+".mp3";;


    ServiceCallback serviceCallback;
    EditText description_edit;

    String draft_file,duet_video_id,videodownloadurl,soundid,sounddownloadurl,thumbname,thumbdownloadurl,allow_comment,allow_duet;

    TextView privcy_type_txt;
    Switch comment_switch,duet_switch;

    Bitmap bmThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);

        Intent intent=getIntent();
        if(intent!=null){
            draft_file=intent.getStringExtra("draft_file");
            duet_video_id=intent.getStringExtra("duet_video_id");
        }


        video_path = Variables.output_filter_file;
        video_thumbnail = findViewById(R.id.video_thumbnail);


        description_edit=findViewById(R.id.description_edit);

        // this will get the thumbnail of video and show them in imageview

        bmThumbnail = ThumbnailUtils.createVideoThumbnail(video_path,
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

        if(bmThumbnail != null && duet_video_id!=null){
            Bitmap duet_video_bitmap = null;
            if(duet_video_id!=null){
                duet_video_bitmap= ThumbnailUtils.createVideoThumbnail(Variables.app_showing_folder+duet_video_id+".mp4",
                        MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            }
            Bitmap combined=combineImages(bmThumbnail,duet_video_bitmap);
            video_thumbnail.setImageBitmap(combined);
            Variables.sharedPreferences.edit().putString(Variables.uploading_video_thumb,Functions.Bitmap_to_base64(this,combined)).commit();

        }
        else if(bmThumbnail != null){
            video_thumbnail.setImageBitmap(bmThumbnail);
            Variables.sharedPreferences.edit().putString(Variables.uploading_video_thumb,Functions.Bitmap_to_base64(this,bmThumbnail)).commit();

        }



      privcy_type_txt=findViewById(R.id.privcy_type_txt);
      comment_switch=findViewById(R.id.comment_switch);
      duet_switch=findViewById(R.id.duet_switch);


      findViewById(R.id.Goback).setOnClickListener(this);

      findViewById(R.id.privacy_type_layout).setOnClickListener(this);
      findViewById(R.id.post_btn).setOnClickListener(this);
      findViewById(R.id.save_draft_btn).setOnClickListener(this);



      if(duet_video_id!=null){
          findViewById(R.id.duet_layout).setVisibility(View.GONE);
          duet_switch.setChecked(false);
      }

      else if(Variables.is_enable_duet)
          findViewById(R.id.duet_layout).setVisibility(View.VISIBLE);

      else {
          findViewById(R.id.duet_layout).setVisibility(View.GONE);
          duet_switch.setChecked(false);
      }

}


    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        Bitmap cs = null;

        int width, height = 0;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth();
            height = c.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        return cs;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Goback:
                onBackPressed();
                break;

            case R.id.privacy_type_layout:
                Privacy_dialog();
                break;

            case R.id.save_draft_btn:
                Save_file_in_draft();
                break;

            case R.id.post_btn:
                dialog = new ProgressDialog(Post_Video_A.this);
                dialog.setMessage("Uploading Please Wait");
                dialog.setCancelable( false );
                dialog.show();
                Start_Service();
                break;
        }
    }



    private void Privacy_dialog() {
        final CharSequence[] options = new CharSequence[]{"Public","Private"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);

        builder.setTitle(null);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {
                privcy_type_txt.setText(options[item]);

            }

        });

        builder.show();

    }

    private void uploadvideo() {
        AWSMobileClient.getInstance().initialize(this).execute();


        File file = new File(video_path);
        AmazonS3Client s3Client;
        BasicAWSCredentials credentials;
        credentials = new BasicAWSCredentials(KEY, SECRET);
        s3Client = new AmazonS3Client(credentials);


        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();
        final TransferObserver uploadObserver =
                transferUtility.upload("uservideos/" + UUID.randomUUID().toString() , file, CannedAccessControlList.PublicRead);
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    String url = "https://"+"selfilmandroid-deployments-mobilehub-1573813982"+".s3.ap-south-1.amazonaws.com/" + uploadObserver.getKey();
                    Log.e("URL :,", url);
                    videodownloadurl = url;
                    uploadsound();
//we just need to share this File url with Api service request.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

    }

    private void uploadsound(){
        AWSMobileClient.getInstance().initialize(this).execute();


        File file = new File(maudiopath);
        AmazonS3Client s3Client;
        BasicAWSCredentials credentials;
        credentials = new BasicAWSCredentials(KEY, SECRET);
        s3Client = new AmazonS3Client(credentials);

        soundid =file.getName();


        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();
        final TransferObserver uploadObserver =
                transferUtility.upload("usersounds/" + file.getName() , file, CannedAccessControlList.PublicRead);
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    String url = "https://"+"selfilmandroid-deployments-mobilehub-1573813982"+".s3.ap-south-1.amazonaws.com/" + uploadObserver.getKey();
                    Log.e("URL :,", url);
                    sounddownloadurl = url;
                    uploadthumb();

                    //we just need to share this File url with Api service request.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

    }

    private void hitapi(){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String created = simpleDateFormat.format(new Date());


                    if(comment_switch.isChecked())
                        allow_comment="true";
                    else
                        allow_comment="false";

             if(duet_switch.isChecked())
                 allow_duet = "1";
             else
                 allow_duet = "1";
        try {
            //input your API parameters
            object.put("created",created);
            object.put("description",description_edit.getText().toString());
            object.put("fb_id",Variables.user_id);
            object.put("section","Trending");
            object.put("sound_id",soundid);
            object.put("video_thumb",thumbdownloadurl);
            object.put("video_url",videodownloadurl);
            object.put("video_id",UUID.randomUUID().toString());
            object.put("sound_acc",sounddownloadurl);
            object.put("sound_description","Original sound by "+Variables.user_name);
            object.put("mp3",sounddownloadurl);
            object.put("sound_name","Original sound by "+Variables.user_name);
            object.put("sound_thumb",soundthumb);
            object.put("privacytype",privcy_type_txt.getText().toString());
            object.put("allowduet",allow_duet);
            object.put("allowcomment",allow_comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = Variables.uploadVideo;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response :,", response.toString());
                        dialog.dismiss();
                        Intent intent = new Intent(Post_Video_A.this,MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Post_Video_A.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    private void uploadthumb() {
        FileOutputStream out;
        File land=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()
                +"/thumbnail"+UUID.randomUUID()+".jpg");// image file use to create image u can give any path.
        Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(video_path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);//filePath is your video file path.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        try {
            out=new FileOutputStream(land.getPath());
            out.write(byteArray);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        AWSMobileClient.getInstance().initialize(this).execute();
        File fileu = new File(String.valueOf(land));
        AmazonS3Client s3Client;
        BasicAWSCredentials credentials;
        credentials = new BasicAWSCredentials(KEY, SECRET);
        s3Client = new AmazonS3Client(credentials);


        thumbname = fileu.getName();
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();
        final TransferObserver uploadObserver =
                transferUtility.upload("uservideothumb/" + fileu.getName() , fileu, CannedAccessControlList.PublicRead);
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    String url = "https://"+"selfilmandroid-deployments-mobilehub-1573813982"+".s3.ap-south-1.amazonaws.com/" + uploadObserver.getKey();
                    Log.e("URL :,", url);
                    thumbdownloadurl = url;

                    hitapi();
                    //we just need to share this File url with Api service request.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

    }


    public void generateaudio() throws IOException {
        new AudioExtractor().genVideoUsingMuxer(video_path, maudiopath, -1, -1, true, false);
        uploadvideo();
    }

    // this will start the service for uploading the video into database
    public void Start_Service(){
        try {
            generateaudio();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        serviceCallback=this;
//
//        Upload_Service mService = new Upload_Service(serviceCallback);
//        if (!Functions.isMyServiceRunning(this,mService.getClass())) {
//            Intent mServiceIntent = new Intent(this.getApplicationContext(), mService.getClass());
//            mServiceIntent.setAction("startservice");
//            mServiceIntent.putExtra("draft_file",draft_file);
//            mServiceIntent.putExtra("duet_video_id",duet_video_id);
//            mServiceIntent.putExtra("uri",""+ video_path);
//            mServiceIntent.putExtra("desc",""+description_edit.getText().toString());
//            mServiceIntent.putExtra("privacy_type",privcy_type_txt.getText().toString());
//
//            if(comment_switch.isChecked())
//              mServiceIntent.putExtra("allow_comment","true");
//             else
//                mServiceIntent.putExtra("allow_comment","false");
//
//             if(duet_switch.isChecked())
//                 mServiceIntent.putExtra("allow_duet","1");
//             else
//                 mServiceIntent.putExtra("allow_duet","0");
//
//            startService(mServiceIntent);
//
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    sendBroadcast(new Intent("uploadVideo"));
//                    startActivity(new Intent(Post_Video_A.this, MainMenuActivity.class));
//                }
//            },1000);
//
//
//        }
//        else {
//            Toast.makeText(this, "Please wait video already in uploading progress", Toast.LENGTH_LONG).show();
//        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }


    // when the video is uploading successfully it will restart the appliaction
    @Override
    public void ShowResponce(final String responce) {

        if(mConnection!=null)
        unbindService(mConnection);

        Toast.makeText(this, responce, Toast.LENGTH_SHORT).show();
    }



    // this is importance for binding the service to the activity
    Upload_Service mService;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

           Upload_Service.LocalBinder binder = (Upload_Service.LocalBinder) service;
            mService = binder.getService();

            mService.setCallbacks(Post_Video_A.this);



        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };



    @Override
    protected void onDestroy() {
        if(bmThumbnail!=null){
            bmThumbnail.recycle();
        }
        super.onDestroy();
    }


    public void Save_file_in_draft(){
       File source = new File(video_path);
       File destination = new File(Variables.draft_app_folder+Functions.getRandomString()+".mp4");
        try
        {
            if(source.exists()){

                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(destination);

                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();

                Toast.makeText(Post_Video_A.this, "File saved in Draft", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Post_Video_A.this, MainMenuActivity.class));

            }else{
                Toast.makeText(Post_Video_A.this, "File failed to saved in Draft", Toast.LENGTH_SHORT).show();

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



}
class AudioExtractor {

    private static final int DEFAULT_BUFFER_SIZE = 1 * 1024 * 1024;
    private static final String TAG = "AudioExtractorDecoder";

    /**
     * @param srcPath  the path of source video file.
     * @param dstPath  the path of destination video file.
     * @param startMs  starting time in milliseconds for trimming. Set to
     *                 negative if starting from beginning.
     * @param endMs    end time for trimming in milliseconds. Set to negative if
     *                 no trimming at the end.
     * @param useAudio true if keep the audio track from the source.
     * @param useVideo true if keep the video track from the source.
     * @throwsIOException
     */
    @SuppressLint("NewApi")
    public void genVideoUsingMuxer(String srcPath, String dstPath, int startMs, int endMs, boolean useAudio, boolean useVideo) throws IOException {
        // Set up MediaExtractor to read from the source.
        MediaExtractor extractor = new MediaExtractor();
        extractor.setDataSource(srcPath);
        int trackCount = extractor.getTrackCount();
        // Set up MediaMuxer for the destination.
        MediaMuxer muxer;
        muxer = new MediaMuxer(dstPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        // Set up the tracks and retrieve the max buffer size for selected
        // tracks.
        HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>(trackCount);
        int bufferSize = -1;
        for (int i = 0; i < trackCount; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            boolean selectCurrentTrack = false;
            if (mime.startsWith("audio/") && useAudio) {
                selectCurrentTrack = true;
            } else if (mime.startsWith("video/") && useVideo) {
                selectCurrentTrack = true;
            }
            if (selectCurrentTrack) {
                extractor.selectTrack(i);
                int dstIndex = muxer.addTrack(format);
                indexMap.put(i, dstIndex);
                if (format.containsKey(MediaFormat.KEY_MAX_INPUT_SIZE)) {
                    int newSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
                    bufferSize = newSize > bufferSize ? newSize : bufferSize;
                }
            }
        }
        if (bufferSize < 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }
        // Set up the orientation and starting time for extractor.
        MediaMetadataRetriever retrieverSrc = new MediaMetadataRetriever();
        retrieverSrc.setDataSource(srcPath);
        String degreesString = retrieverSrc.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
        if (degreesString != null) {
            int degrees = Integer.parseInt(degreesString);
            if (degrees >= 0) {
                muxer.setOrientationHint(degrees);
            }
        }
        if (startMs > 0) {
            extractor.seekTo(startMs * 1000, MediaExtractor.SEEK_TO_CLOSEST_SYNC);
        }
        // Copy the samples from MediaExtractor to MediaMuxer. We will loop
        // for copying each sample and stop when we get to the end of the source
        // file or exceed the end time of the trimming.
        int offset = 0;
        int trackIndex = -1;
        ByteBuffer dstBuf = ByteBuffer.allocate(bufferSize);
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        muxer.start();
        while (true) {
            bufferInfo.offset = offset;
            bufferInfo.size = extractor.readSampleData(dstBuf, offset);
            if (bufferInfo.size < 0) {
                Log.d(TAG, "Saw input EOS.");
                bufferInfo.size = 0;
                break;
            } else {
                bufferInfo.presentationTimeUs = extractor.getSampleTime();
                if (endMs > 0 && bufferInfo.presentationTimeUs > (endMs * 1000)) {
                    Log.d(TAG, "The current sample is over the trim end time.");
                    break;
                } else {
                    bufferInfo.flags = extractor.getSampleFlags();
                    trackIndex = extractor.getSampleTrackIndex();
                    muxer.writeSampleData(indexMap.get(trackIndex), dstBuf, bufferInfo);
                    extractor.advance();
                }
            }
        }
        muxer.stop();
        muxer.release();
        return;
    }
}
