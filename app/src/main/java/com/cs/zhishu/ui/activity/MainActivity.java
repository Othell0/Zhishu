package com.cs.zhishu.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.ui.fragment.DailyListFragment;
import com.cs.zhishu.ui.fragment.HotNewsFragment;
import com.cs.zhishu.ui.fragment.SectionsFragment;
import com.cs.zhishu.ui.fragment.ThemesDailyFragment;
import com.cs.zhishu.util.NightModeHelper;

import org.lzh.framework.updatepluginlib.UpdateBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static java.lang.System.currentTimeMillis;


/*知书主界面*/

public class MainActivity extends AbsBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation mAhBottomNavigation;
    @BindView(R.id.drawer_layout)
    CoordinatorLayout drawerLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private int currentTabIndex;
    private long exitTime = 0;
    private static int mNightMode = Configuration.UI_MODE_NIGHT_UNDEFINED;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        /*Activity中进行更新检查*/
        UpdateBuilder.create().check(MainActivity.this);


        fragments.add(DailyListFragment.newInstance());
        fragments.add(ThemesDailyFragment.newInstance());
        fragments.add(SectionsFragment.newInstance());
        fragments.add(HotNewsFragment.newInstance());

        showFragment(fragments.get(0));
        initBottomNav();
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    private void initBottomNav() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("日报", R.drawable.ic_profile_answer, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("主题", R.drawable.ic_profile_article, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("专栏", R.drawable.ic_profile_column, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("文章", R.drawable.ic_profile_favorite, R.color.colorPrimary);

        mAhBottomNavigation.addItem(item1);
        mAhBottomNavigation.addItem(item2);
        mAhBottomNavigation.addItem(item3);
        mAhBottomNavigation.addItem(item4);

        mAhBottomNavigation.setBehaviorTranslationEnabled(true);
        mAhBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mAhBottomNavigation.setInactiveColor(getResources().getColor(R.color.nav_text_color_mormal));
        mAhBottomNavigation.setCurrentItem(0);

        mAhBottomNavigation.setBehaviorTranslationEnabled(true);
        mAhBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.bg_color));


        mAhBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position, boolean wasSelected) {

                if (currentTabIndex != position) {
                    FragmentTransaction trx = getFragmentManager().beginTransaction();
                    trx.hide(fragments.get(currentTabIndex));
                    if (!fragments.get(position).isAdded()) {
                        trx.add(R.id.content, fragments.get(position));
                    }
                    trx.show(fragments.get(position)).commit();
                }
                currentTabIndex = position;
            }
        });
    }


    @Override
    public void initToolBar() {

        mToolbar.setTitle("知书");
        setSupportActionBar(mToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //设置
                startActivity(new Intent(this, MoreActivity.class));
                return true;
   /*         case R.id.action_mode:
                //切换模式

              *//*      getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);


                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);*//*

                recreate();
                return true;*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (currentTimeMillis() - exitTime > 2000) {
            Snackbar.make(drawerLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}


