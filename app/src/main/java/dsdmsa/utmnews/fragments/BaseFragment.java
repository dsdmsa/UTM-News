package dsdmsa.utmnews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.ButterKnife;
import dsdmsa.utmnews.FragmentNavigation;

public abstract class BaseFragment extends MvpAppCompatFragment implements FragmentNavigation.View {

    protected View rootView;
    protected FragmentNavigation.Presenter navigationPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getLayout();

    @Override
    public void atachPresenter(FragmentNavigation.Presenter presenter) {
        navigationPresenter = presenter;
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        navigationPresenter.addFragment(fragment);
    }
}
