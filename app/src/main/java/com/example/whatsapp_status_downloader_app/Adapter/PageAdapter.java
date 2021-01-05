package com.example.whatsapp_status_downloader_app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp_status_downloader_app.Fragments.ImageFragment;
import com.example.whatsapp_status_downloader_app.Fragments.SavedFilesFragment;
import com.example.whatsapp_status_downloader_app.Fragments.VideoFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int totalTabs;

    public PageAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new VideoFragment();
        }else if (position == 2)
            return new SavedFilesFragment();
        return new ImageFragment();

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
