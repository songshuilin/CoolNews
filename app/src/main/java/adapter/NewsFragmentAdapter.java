package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListFragments;

    public NewsFragmentAdapter(FragmentManager fm, List<Fragment> mListFragments) {
        super(fm);
        this.mListFragments = mListFragments;
    }

    /**
     * 返回该位置的fragment
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    /**
     * fragment的数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mListFragments.size();
    }
}
