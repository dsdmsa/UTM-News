package dsdmsa.utmnews.domain.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FragmentNavigation {

    private int fragmentContainerId;
    private FragmentManager fm;

    private HashMap<Integer, Fragment> fragemntMap = new HashMap<>();
    private List<Integer> navigationIndex = new ArrayList<>();

    @Inject
    public FragmentNavigation() {
    }

    public void init(FragmentManager frameLayout, int fragmentContainerId ) {
        this.fm = frameLayout;
        this.fragmentContainerId = fragmentContainerId;
    }

    public void onDestory() {
        this.fragmentContainerId = -1;
        this.fm = null;
    }

    public void showFragment(int fragmentId, Fragment fragment) {
        if (navigationIndex.contains(fragmentId)) {
            navigationIndex.remove(navigationIndex.indexOf(fragmentId));
            navigationIndex.add(fragmentId);
        } else {
            navigationIndex.add(fragmentId);
        }

        if (!fragemntMap.containsKey(fragmentId)) {
            fragemntMap.put(fragmentId, fragment);
            addFragment(fragmentId);
        } else {
            showFragment(fragmentId);
        }
    }

    private void addFragment(int id) {
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(this.fragmentContainerId, fragemntMap.get(id))
                .commit();
    }

    private void showFragment(int id) {
        for (Fragment fragment : fragemntMap.values()) {
            fm.beginTransaction()
                    .hide(fragment).commit();
        }
        fm.beginTransaction()
                .show(fragemntMap.get(id))
                .commit();
    }

    public int bakPressed() {
        if (getSize() == 0) {
            return 0;
        }
        if (getSize() > 1) {
            navigationIndex.remove(navigationIndex.size() - 1);
            fm.beginTransaction()
                    .show(fragemntMap.get(navigationIndex.get(navigationIndex.size() - 1)))
                    .commit();
            return navigationIndex.get(navigationIndex.size() - 1);
        } else {
            fm.beginTransaction()
                    .show(fragemntMap.get(navigationIndex.get(0)))
                    .commit();

            return 0;
        }
    }

    public int getSize() {
        return navigationIndex.size();
    }

}
