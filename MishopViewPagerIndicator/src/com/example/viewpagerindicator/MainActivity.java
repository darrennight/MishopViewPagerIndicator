
package com.example.viewpagerindicator;

import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity {
    private LinearLayout tabs;
    private ImageView tabContainer;
    private ViewPager pager;
    private int PAGE_SIZE = 5;

    private int tabWidth;// 单个标题宽度
    private int initLeftMargin;// 初始的左边距

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (LinearLayout) findViewById(R.id.tabs);
        tabContainer = (ImageView) findViewById(R.id.tab_container);
        pager = (ViewPager) findViewById(R.id.viewpager);
        PAGE_SIZE = tabs.getChildCount();

        for (int i = 0; i < PAGE_SIZE; i++) {
            final int index = i;
            tabs.getChildAt(i).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(index);

                }
            });
        }

        tabs.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {// 当View可见时获取单个宽度
                        tabs.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                        tabWidth = tabs.getWidth() / PAGE_SIZE;
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tabContainer
                                .getLayoutParams();
                        initLeftMargin = -tabWidth * PAGE_SIZE + tabWidth / 2;
                        params.leftMargin = initLeftMargin;
                        tabContainer.setLayoutParams(params);
                    }
                });

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tabContainer
                        .getLayoutParams();
                float f1 = -tabWidth * PAGE_SIZE + tabWidth / 2;
                float offset = positionOffset * tabWidth;
                float leftMargin = f1 + offset + tabWidth * position;
                float rightMargin = -offset - tabWidth * position;
                ;

                params.leftMargin = (int) leftMargin;
                params.rightMargin = (int) rightMargin;
                tabContainer.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ColorFragment();
        }

        @Override
        public int getCount() {
            return PAGE_SIZE;
        }

    }

    public static class ColorFragment extends Fragment {

        public ColorFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = new View(getActivity());

            Random r = new Random();
            int color = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
            view.setBackgroundDrawable(new ColorDrawable(color));
            return view;
        }
    }

}
