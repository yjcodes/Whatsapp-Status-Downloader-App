package com.example.whatsapp_status_downloader_app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        CardView contact = findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendinstagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/yuvansh.joshi/"));
                startActivity(sendinstagram);

            }
        });

        CardView email = findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
                String mail = "mailto:" + getString(R.string.mail_id);
                sendEmail.setData(Uri.parse(mail));
                startActivity(sendEmail);
            }
        });

        TextView app_version = findViewById(R.id.app_version);
        app_version.setText(getAppVersion());

    }

    private String getAppVersion(){

        String result = "Internship Assignment";
        return result;

        /* In Future if we want to show app version then enable the below code*/
       /* try {
            result = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(),0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-","");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } */
    }

}
