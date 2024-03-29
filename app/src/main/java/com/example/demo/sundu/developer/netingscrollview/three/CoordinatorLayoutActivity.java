package com.example.demo.sundu.developer.netingscrollview.three;

import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.R;
import com.example.demo.sundu.developer.netingscrollview.NestingScrollRecyclerAdapter;
import com.example.demo.sundu.developer.netingscrollview.NestingScrollUtil;
import com.google.android.material.tabs.TabLayout;


/**
 * @author cginechen
 * @date 2016-12-29
 */

public class CoordinatorLayoutActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private View mHeaderView;
    private LinearLayout mTargetLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SparseArray<RecyclerView> mPageMap = new SparseArray<>();
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            View view = getPageView(position);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "item " + (position + 1);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nesting_scroll_plan_coordinator);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mHeaderView = findViewById(R.id.book_header);
        CoordinatorLayout.LayoutParams headerLp = (CoordinatorLayout.LayoutParams) mHeaderView
                .getLayoutParams();
        headerLp.setBehavior(new CoverBehavior(NestingScrollUtil.dp2px(this, 0), 0));

        mTargetLayout = (LinearLayout) findViewById(R.id.scroll_view);
        CoordinatorLayout.LayoutParams targetLp = (CoordinatorLayout.LayoutParams) mTargetLayout
                .getLayoutParams();
        targetLp.setBehavior(new TargetBehavior(this, NestingScrollUtil.dp2px(this, 150), 0));

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    private RecyclerView getPageView(int pos) {
        RecyclerView view = mPageMap.get(pos);
        if (view == null) {
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new NestingScrollRecyclerAdapter());
            mPageMap.put(pos, recyclerView);
            return recyclerView;
        }
        return view;
    }
}
