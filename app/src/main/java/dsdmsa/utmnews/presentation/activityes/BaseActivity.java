package dsdmsa.utmnews.presentation.activityes;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends MvpAppCompatActivity implements LifecycleOwner{

    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @Override
    public Lifecycle getLifecycle() {
        return new LifecycleRegistry(this);
    }
}
