package com.phantom.asalama.movies.screen.detail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phantom.asalama.movies.MovieApplication;
import com.phantom.asalama.movies.R;
import com.phantom.asalama.movies.Util.TmdbUrls;
import com.phantom.asalama.movies.models.Movie;
import com.phantom.asalama.movies.models.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideosRecyclerViewAdapter extends RecyclerView.Adapter<VideosRecyclerViewAdapter.ViewHolder> {

    private final MovieDetailActivity mParent;
    private List<Video> mVideos;

    public VideosRecyclerViewAdapter(MovieDetailActivity parent, List<Video> videos) {
        mParent = parent;
        mVideos = videos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_video, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso picasso = ((MovieApplication) (mParent.getApplication())).getmPicasso();
        picasso.load(TmdbUrls.YOUTUBE_THUMB + mVideos.get(position).getKey()
                + TmdbUrls.YOUTUBE_MEDIUM_QUALITY).into(holder.trailerImage);
        holder.trailerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(TmdbUrls.YOUTUBE_URL + mVideos.get(position).getKey()));
                mParent.startActivity(youtubeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mVideos != null)
            return mVideos.size();
        else return 0;
    }

    public void setNewData(List<Video> newData) {
        mVideos = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView trailerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            trailerImage = itemView.findViewById(R.id.trailer_image);
        }
    }
}
