package com.nathansdev.stack.home.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nathansdev.stack.AppConstants;
import com.nathansdev.stack.home.adapter.QuestionsAdapter;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRow;
import com.nathansdev.stack.home.adapter.QuestionsAdapterRowDataSet;
import com.nathansdev.stack.home.feed.FeedFragment;
import com.nathansdev.stack.home.feed.FeedView;
import com.nathansdev.stack.home.feed.FeedViewPresenter;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Child fragment for displaying loggedin user questions.
 */
public class MyFeedFragment extends FeedFragment implements FeedView {

    @Inject
    public MyFeedFragment() {

    }

    @Inject
    FeedViewPresenter<FeedView> presenter;

    public static MyFeedFragment newInstance() {
        MyFeedFragment fragment = new MyFeedFragment();
        return fragment;
    }

    public void loadQuestions() {
        presenter.loadQuestions();
    }

    private String filterType = "activity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterType = getArguments().getString(AppConstants.ARG_FILTER_TYPE);
        }
    }

    @Override
    protected void setUpView(View view) {
        super.setUpView(view);
        Timber.d("setUpView");
        presenter.init(dataset, filterType);
    }

    @Override
    protected void loadNextPage() {
        presenter.loadNextPage();
    }

    @Override
    protected void attachPresenter() {
        presenter.onAttach(this);
    }

    @Override
    protected QuestionsAdapter getAdapter() {
        return new QuestionsAdapter();
    }

    @Override
    protected QuestionsAdapterRowDataSet getAdapterDataSet(QuestionsAdapter adapter) {
        return QuestionsAdapterRowDataSet.createWithEmptyData(adapter);
    }

    @Override
    protected void loadFeeds() {
        presenter.loadQuestions();
    }

    @Override
    public void onQuestionsLoaded(List<QuestionsAdapterRow> rows) {
        Timber.d("onQuestionsLoaded");
        adapter.notifyDataSetChanged();
    }

    public void cleanUp() {
        if (dataset != null) {
            dataset.clearDataSet();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cleanUp();
        presenter.onDetach();
    }
}
