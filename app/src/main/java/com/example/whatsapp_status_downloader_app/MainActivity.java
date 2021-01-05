package com.example.whatsapp_status_downloader_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.Objects;

import com.example.whatsapp_status_downloader_app.Adapter.PageAdapter;
import com.example.whatsapp_status_downloader_app.Utils.Common;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private long back_pressed;

    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbarMainActivity);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.images)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.videos)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.saved_files)));
        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        Toast.makeText(MainActivity.this,"by Yuvansh Joshi",Toast.LENGTH_LONG).show();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

            if(menu instanceof MenuBuilder){
                MenuBuilder m = (MenuBuilder) menu;
                m.setOptionalIconsVisible(true);
            }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_whatsappgallery:
                Intent in = new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("*/*");
                startActivityForResult(in,1);
                return true;
            case R.id.menu_share:
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String app_url = "https://github.com/yuvi2811/Internship_wsdownloader/tree/main/releases";
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at \n\n" + app_url);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"WhatsApp Status Saver");
                startActivity(Intent.createChooser(shareIntent, "Share via"));

                return true;
            case R.id.menu_privacyPolicy:
                Uri uri = Uri.parse("https://docs.google.com/document/d/1bzbZed6nWWpS_T0fgNrZZIB3XGQawMl4MxMGOKN3K7s/edit?usp=sharing");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            case R.id.menu_aboutUs:
                startActivity(new Intent(getApplicationContext(),AboutUs.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==REQUEST_PERMISSIONS && grantResults.length>0){
            if (arePermissionDenied()){
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            }
        }
    }

    private boolean arePermissionDenied(){

        for (String permissions : PERMISSIONS){
            if (ActivityCompat.checkSelfPermission(getApplicationContext(),permissions) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }

        Common.APP_DIR = getExternalFilesDir(null).getAbsolutePath() +
                File.separator + "Status Downloader";


    }


    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()){
            finish();
            moveTaskToBack(true);
        }else {
            Snackbar.make(viewPager, "Press Again to Exit", Snackbar.LENGTH_LONG).show();
            back_pressed = System.currentTimeMillis();
        }
    }

}
