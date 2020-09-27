package com.accelstackace.selfilmindia.Prime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accelstackace.selfilmindia.R;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import com.squareup.picasso.Picasso;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.accelstackace.selfilmindia.R.drawable.selfilmlogopng;

public class ottplayer extends AppCompatActivity implements Player.EventListener {



    JzvdStd jzvdStd;
    String movieurl ;

    TextView movietitle,moviedescription,heroname,heroinename,directorname,producername;
    CircleImageView heroimage,heroinepic,directorpic,producrepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ottplayer);

        heroimage = (CircleImageView)findViewById(R.id.heroimage);
        heroinepic = (CircleImageView)findViewById(R.id.heroinepic);
        directorpic = (CircleImageView)findViewById(R.id.directorpic);
        producrepic = (CircleImageView)findViewById(R.id.producerpic);
        movietitle = (TextView)findViewById(R.id.movietitle);
        heroname = (TextView)findViewById(R.id.heroname);
        heroinename = (TextView)findViewById(R.id.heroinename);
        producername = (TextView)findViewById(R.id.producername);
        directorname = (TextView)findViewById(R.id.directorname);
        moviedescription = (TextView)findViewById(R.id.moviedescription);
        Bundle bundle = getIntent().getExtras();
        movietitle.setText(bundle.getString("MovieName"));
        moviedescription.setText(bundle.getString("Description"));
        heroname.setText(bundle.getString("HeroName")+" (Hero)");
        heroinename.setText(bundle.getString("HeroineName")+" (Heroine)");
        directorname.setText(bundle.getString("DirectorName")+" (Director)");
        producername.setText(bundle.getString("ProducerName")+" (Producer)");
        jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
        jzvdStd.setUp(bundle.getString("MovieUrl"),bundle.getString("MovieName"),Jzvd.SCREEN_WINDOW_NORMAL);
        jzvdStd.thumbImageView.setImageURI(Uri.parse(bundle.getString("HeroThum")));


        Picasso.get()
                .load(bundle.getString("HeroThum"))
                .placeholder(this.getResources().getDrawable(R.drawable.profile_image_placeholder))
                .resize(200,200).centerCrop().into(heroimage);
        Picasso.get()
                .load(bundle.getString("HeroineThum"))
                .placeholder(this.getResources().getDrawable(R.drawable.profile_image_placeholder))
                .resize(200,200).centerCrop().into(heroinepic);
        Picasso.get()
                .load(bundle.getString("DirectorThum"))
                .placeholder(this.getResources().getDrawable(R.drawable.profile_image_placeholder))
                .resize(200,200).centerCrop().into(directorpic);
        Picasso.get()
                .load(bundle.getString("ProducerThum"))
                .placeholder(this.getResources().getDrawable(R.drawable.profile_image_placeholder))
                .resize(200,200).centerCrop().into(producrepic);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        jzvdStd.release();
    }
}
