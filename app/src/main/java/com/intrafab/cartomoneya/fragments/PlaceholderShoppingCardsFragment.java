package com.intrafab.cartomoneya.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.intrafab.cartomoneya.R;
import com.intrafab.cartomoneya.adapters.ShopCardAdapter;
import com.intrafab.cartomoneya.data.ShopCard;
import com.intrafab.cartomoneya.widgets.EmptyRecyclerView;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

/**
 * Created by Artemiy Terekhov on 07.05.2015.
 */
public class PlaceholderShoppingCardsFragment extends Fragment {
    public static final String TAG = PlaceholderShoppingCardsFragment.class.getName();

    private EmptyRecyclerView mRecyclerView;
    private LinearLayout mEmptyLayout;
    private ProgressWheel mProgress;

    private ShopCardAdapter mAdapter;
    private ShopCardAdapter.OnClickListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (ShopCardAdapter.OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ShopCardAdapter.OnClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        setData(null);
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ShopCardAdapter(mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cards, container, false);

        mRecyclerView = (EmptyRecyclerView) rootView.findViewById(R.id.listViews);
        mEmptyLayout = (LinearLayout) rootView.findViewById(R.id.layoutEmptyList);
        mProgress = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setEmptyView(mEmptyLayout);
    }

    public void setData(List<ShopCard> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
    }

    private void showEmptyView(boolean isShow) {
        int count = mAdapter.getItemCount();
        if (count <= 0 && isShow)
            mEmptyLayout.setVisibility(View.VISIBLE);
        else
            mEmptyLayout.setVisibility(View.GONE);
    }

    public boolean isProgress() {
        return mProgress.getVisibility() == View.VISIBLE;
    }

    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        showEmptyView(false);
    }

    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }
}
