package dsdmsa.utmnews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dsdmsa.utmnews.mvp.FragmentNavigation;

public abstract class BaseFragment extends MvpAppCompatFragment implements FragmentNavigation.View {

    protected View rootView;
    protected FragmentNavigation.Presenter navigationPresenter;
    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        rootView = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getLayout();

    @Override
    public void atachPresenter(FragmentNavigation.Presenter presenter) {
        navigationPresenter = presenter;
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
