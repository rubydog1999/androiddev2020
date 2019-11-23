package vn.edu.usth.usth_weather;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    int numberTabs = 3;
    private String titles [] = new String[] {"HaNoi", "Paris", "Toulouse"};
    public HomeFragmentAdapter (FragmentManager fm){
        super(fm);
    }
    @Override
    public int getCount()
    {
        return numberTabs;
    }
    @Override
    public Fragment getItem (int page) {
        switch (page) {
            case 0:
                WeatherAndForecastFragment wff = new WeatherAndForecastFragment();
                return wff;
            case 1:
                WeatherAndForecastFragment wff2 = new WeatherAndForecastFragment();
                return wff2;
            case 2:
                WeatherAndForecastFragment wff3 = new WeatherAndForecastFragment();
                return wff3;
        }
        return null;

    }
    @Override
    public CharSequence getPageTitle (int page){
        return titles[page];
    }
}
