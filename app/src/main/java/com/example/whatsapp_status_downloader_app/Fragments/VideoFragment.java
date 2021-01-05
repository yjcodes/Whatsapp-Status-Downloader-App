package com.example.whatsapp_status_downloader_app.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.whatsapp_status_downloader_app.Adapter.VideoAdapter;
import com.example.whatsapp_status_downloader_app.Models.Status;
import com.example.whatsapp_status_downloader_app.R;
import com.example.whatsapp_status_downloader_app.Utils.Common;

public class VideoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Status> videoList = new ArrayList<>();
    private Handler handler = new Handler();
    private VideoAdapter videoAdapter;
    private RelativeLayout container;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_videos,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerViewVideo);
        progressBar = view.findViewById(R.id.prgressBarVideo);
        container = view.findViewById(R.id.videos_container);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),android.R.color.holo_orange_dark)
                ,ContextCompat.getColor(getActivity(),android.R.color.holo_green_dark),
                ContextCompat.getColor(getActivity(),R.color.colorPrimary),
                ContextCompat.getColor(getActivity(),android.R.color.holo_blue_dark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStatus();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        getStatus();

        super.onViewCreated(view, savedInstanceState);
    }

    private void getStatus() {

        if (Common.STATUS_DIRECTORY.exists()){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] statusFiles = null;
                    statusFiles = Common.STATUS_DIRECTORY.listFiles();
                    videoList.clear();

                    if (statusFiles!=null && statusFiles.length>0){

                        Arrays.sort(statusFiles);
                        for (File file : statusFiles){
                            Status status = new Status(file, file.getName(), file.getAbsolutePath());

                            if (status.isVideo()){
                                videoList.add(status);
                                status.setThumbnail(getThumbnail(status));
                            }

                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                videoAdapter = new VideoAdapter(videoList, container);
                                recyclerView.setAdapter(videoAdapter);
                                videoAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    }else {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Dir doest not exists", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            }).start();

        }else {
            Toast.makeText(getActivity(), "Cant find WhatsApp Dir", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private Bitmap getThumbnail(Status status) {
        return com.example.internship_whatsapp_status_downloader_app.Utils.ThumbnailUtils.createVideoThumbnail(status.getFile().getAbsolutePath(),
                3);
    }

}
