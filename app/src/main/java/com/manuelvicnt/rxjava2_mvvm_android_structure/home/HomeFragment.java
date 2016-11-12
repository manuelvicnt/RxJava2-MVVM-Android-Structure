package com.manuelvicnt.rxjava2_mvvm_android_structure.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manuelvicnt.rxjava2_mvvm_android_structure.R;
import com.manuelvicnt.rxjava2_mvvm_android_structure.base.BaseFragment;
import com.manuelvicnt.rxjava2_mvvm_android_structure.base.Lifecycle;
import com.manuelvicnt.rxjava2_mvvm_android_structure.networking.UserDataRequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ManuelVivo on 03/10/15.
 */
public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeContract.ViewModel homeViewModel;

    @BindView(R.id.user_data) TextView userDataText;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDataRequestManager userDataRequestManager =
                UserDataRequestManager.getInstance(getActivity().getApplicationContext());

        homeViewModel = new HomeViewModel(userDataRequestManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        showSuccessfulMessage("Logged in");

        setupRefreshLayout();
        return rootView;
    }

    @Override
    public void showSuccessfulMessage(String message) {

        userDataText.setText(message);
    }

    @Override
    public void hideLoading() {

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {

        return homeViewModel;
    }

    private void setupRefreshLayout() {

        swipeRefreshLayout.setOnRefreshListener(() -> homeViewModel.getUserData());
    }
}