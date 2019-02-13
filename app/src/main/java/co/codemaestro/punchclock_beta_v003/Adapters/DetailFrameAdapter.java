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

    public DetailFrameAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = TimeBankEntriesFragment.newInstance(1);
                break;
            case 1:
                fragment = CategoryCalendarFragment.newInstance(2);
                break;
            case 2:
                fragment = CategoryGoalsFragment.newInstance(3);
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
