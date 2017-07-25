package dsdmsa.utmnews.presentation.fragments;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.presentation.activityes.AppInfoActivity;
import dsdmsa.utmnews.presentation.views.ChromeTab;


public class AboutFragment extends BaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public String getName() {
        return "";
    }


    @OnClick({R.id.despre_aplcatie, R.id.despre_utm, R.id.contacte})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.despre_aplcatie:
                startActivity(new Intent(getContext(), AppInfoActivity.class));
                break;
            case R.id.despre_utm:
                new ChromeTab(getActivity(), Constants.URL_DESPRE_UTM);
                break;
            case R.id.contacte:
                new ChromeTab(getActivity(), Constants.URL_CONTACTE_UTM);
                break;
        }
    }
}
