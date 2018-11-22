package com.ulicae.cinelog.android.activities.add;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ulicae.cinelog.R;
import com.ulicae.cinelog.data.DataService;
import com.ulicae.cinelog.network.TmdbServiceWrapper;
import com.ulicae.cinelog.network.task.NetworkTaskManager;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public abstract class AddReviewActivity<T> extends AppCompatActivity {

    static final int RESULT_VIEW_KINO = 4;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.kino_search)
    EditText kino_search;
    @BindView(R.id.kino_results)
    ListView kino_results_list;
    @BindView(R.id.kino_search_progress_bar)
    ProgressBar kino_search_progress_bar;

    protected TmdbServiceWrapper tmdbServiceWrapper;
    protected NetworkTaskManager networkTaskManager;

    protected DataService dataService;

    private Handler handler;

    private final static int TRIGGER_SERACH = 1;

    // Where did 1000 come from? It's arbitrary, since I can't find average android typing speed.
    private final static long SEARCH_TRIGGER_DELAY_IN_MS = 1000;

    public static int RESULT_EDIT_REVIEW = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kino);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tmdbServiceWrapper = new TmdbServiceWrapper(this);

        handler = new AddReviewHandler(new WeakReference<AddReviewActivity>(this));
    }

    private void startSearchTask() {
        if (isNetworkAvailable()) {
            executeTask(kino_search.getText().toString());
        } else {
            Toast t = Toast.makeText(getApplicationContext(),
                    getString(R.string.addkino_error_no_network),
                    Toast.LENGTH_LONG);
            t.show();
        }
    }

    protected abstract void executeTask(String textToSearch);

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @SuppressWarnings("unused")
    @OnTextChanged(R.id.kino_search)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0) {
            kino_search_progress_bar.setVisibility(View.VISIBLE);
            handler.removeMessages(TRIGGER_SERACH);
            handler.sendEmptyMessageDelayed(TRIGGER_SERACH, SEARCH_TRIGGER_DELAY_IN_MS);
        } else if (count == 0) {
            clearListView();
        }
    }

    public void clearListView() {
        if (kino_results_list != null && kino_results_list.getAdapter() != null) {
            kino_results_list.setAdapter(null);
        }
        kino_search_progress_bar.setVisibility(View.GONE);
    }

    public abstract void populateListView(final List<T> movies);

    static class ViewHolder {
        @BindView(R.id.kino_title)
        TextView title;
        @BindView(R.id.kino_year)
        TextView year;
        @BindView(R.id.kino_poster)
        ImageView poster;

        @BindView(R.id.add_review_button)
        ImageButton add_review_button;

        @BindView(R.id.kino_rating_bar_review)
        RatingBar kino_rating_bar_review;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class AddReviewHandler extends Handler {
        private WeakReference<AddReviewActivity> addKino;

        AddReviewHandler(WeakReference<AddReviewActivity> addKino) {
            this.addKino = addKino;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TRIGGER_SERACH) {
                addKino.get().startSearchTask();
            }
        }
    }
}