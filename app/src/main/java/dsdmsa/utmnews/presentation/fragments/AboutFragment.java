package dsdmsa.utmnews.presentation.fragments;

import android.widget.LinearLayout;

import butterknife.BindView;
import dsdmsa.utmnews.R;


public class AboutFragment extends BaseFragment {

    @BindView(R.id.despre_aplcatie)
    LinearLayout despreAplcatie;
    @BindView(R.id.despre_utm)
    LinearLayout despreUtm;
    @BindView(R.id.termeni)
    LinearLayout termeni;
    @BindView(R.id.contacte)
    LinearLayout contacte;

    @Override
    protected int getLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public String getName() {
        return "";
    }

}
