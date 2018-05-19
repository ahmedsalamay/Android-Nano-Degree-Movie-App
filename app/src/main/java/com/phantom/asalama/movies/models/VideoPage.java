package com.phantom.asalama.movies.models;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoPage implements Parcelable {

    public final static Parcelable.Creator<VideoPage> CREATOR = new Creator<VideoPage>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VideoPage createFromParcel(Parcel in) {
            return new VideoPage(in);
        }

        public VideoPage[] newArray(int size) {
            return (new VideoPage[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Video> results = null;

    protected VideoPage(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (com.phantom.asalama.movies.models.Video.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public VideoPage() {
    }

    /**
     * @param id
     * @param results
     */
    public VideoPage(Integer id, List<Video> results) {
        super();
        this.id = id;
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

}
