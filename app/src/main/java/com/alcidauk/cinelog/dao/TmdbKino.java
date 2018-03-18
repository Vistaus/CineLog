package com.alcidauk.cinelog.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.parceler.Parcel;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by alcidauk on 18/03/18.
 */
@Parcel
@Entity
public class TmdbKino {

    @Id
    Long movie_id;

    String poster_path;
    String overview;
    int year;
    String release_date;

    @Generated(hash = 226405629)
    public TmdbKino(Long movie_id, String poster_path, String overview, int year, String release_date) {
        this.movie_id = movie_id;
        this.poster_path = poster_path;
        this.overview = overview;
        this.year = year;
        this.release_date = release_date;
    }

    @Generated(hash = 1879968548)
    public TmdbKino() {
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Long getMovie_id() {
        return this.movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TmdbKino tmdbKino = (TmdbKino) o;

        if (year != tmdbKino.year) return false;
        if (movie_id != null ? !movie_id.equals(tmdbKino.movie_id) : tmdbKino.movie_id != null)
            return false;
        if (poster_path != null ? !poster_path.equals(tmdbKino.poster_path) : tmdbKino.poster_path != null)
            return false;
        if (overview != null ? !overview.equals(tmdbKino.overview) : tmdbKino.overview != null)
            return false;
        return release_date != null ? release_date.equals(tmdbKino.release_date) : tmdbKino.release_date == null;
    }

    @Override
    public int hashCode() {
        int result = movie_id != null ? movie_id.hashCode() : 0;
        result = 31 * result + (poster_path != null ? poster_path.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        return result;
    }
}
