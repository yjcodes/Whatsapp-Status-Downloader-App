package com.example.whatsapp_status_downloader_app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.whatsapp_status_downloader_app.Models.Status;
import com.example.whatsapp_status_downloader_app.R;
import com.example.whatsapp_status_downloader_app.Utils.Common;

public class ImageAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<Status> imagesList;
    private Context context;
    private RelativeLayout container;

    public ImageAdapter(List<Status> imagesList, RelativeLayout container) {
        this.imagesList = imagesList;
        this.container = container;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {

        final Status status = imagesList.get(position);
        Picasso.get().load(status.getFile()).into(holder.imageView);

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Common.copyFile(status,context,container);

            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("image/jpg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+status.getFile().getAbsolutePath()));
                context.startActivity(Intent.createChooser(shareIntent, "Share image"));

            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.view_image_full_screen, null);
                alertD.setView(view);

                ImageView imageView = view.findViewById(R.id.img);
                Picasso.get().load(status.getFile()).into(imageView);

                AlertDialog alert = alertD.create();
                alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
