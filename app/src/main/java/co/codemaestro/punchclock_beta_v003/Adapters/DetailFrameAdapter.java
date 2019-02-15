package co.codemaestro.punchclock_beta_v003.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.codemaestro.punchclock_beta_v003.Fragments.CategoryCalendarFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.CategoryGoalsFragment;
import co.codemaestro.punchclock_beta_v003.Fragments.TimeBankEntriesFragment;

public class DetailFrameAdapter extends FragmentPagerAdapter {
    private static int NUM_OF_FRAMES = 3;
    private int parentCategoryId;

    public DetailFrameAdapter(FragmentManager fragmentManager, int parentCategoryId) {
        super(fragmentManager);
        this.parentCategoryId = parentCategoryId;
    }



    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = TimeBankEntriesFragment.newInstance(parentCategoryId);
                break;
            case 1:
                fragment = CategoryCalendarFragment.newInstance(parentCategoryId);
                break;
            case 2:
                fragment = CategoryGoalsFragment.newInstance(parentCategoryId);
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_OF_FRAMES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
