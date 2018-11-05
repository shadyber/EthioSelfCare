package com.ethioroot.selfcare.ethioselfcare;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class giftApps extends AppCompatActivity {


    public Animation animBounce;
    private RecyclerView recyclerView;
    private AppsAdapter adapter;
    private List<AppsMenu> menuList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent home=new Intent(giftApps.this,MainActivity.class);
                    startActivity(home);
                    return true;
                case R.id.navigation_account_man:
                    Intent acc=new Intent(giftApps.this,AccountManager.class);
                    startActivity(acc);
                    return true;
                case R.id.navigation_extra:
                    Intent extra=new Intent(giftApps.this,giftApps.class);
                    startActivity(extra);
                    return  true;
                case R.id.navigation_gebeta:
                    Intent gebeta=new Intent(giftApps.this,GebetaActivity.class);
                    startActivity(gebeta);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_apps);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        menuList = new ArrayList<>();
        adapter = new AppsAdapter(this, menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
prepareAlbums();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_extra);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        AppsMenu a = new AppsMenu("Mereja","https://play.google.com/store/apps/details?id=com.ethioroot.mereja&hl=en_US","com.ethioroot.mereja", "https://lh3.googleusercontent.com/suWTKHpGmA80WejBnX9HzIZh2dpxrkc0R6kBzWvrBf7-z4kJ6M25MW9C33bbSBAYg7Y=s180-rw");
        menuList.add(a);

        a = new AppsMenu("TelePot","https://play.google.com/store/apps/details?id=com.ethioroot.tele.etteleportal","com.ethioroot.tele.etteleportal", "https://lh3.googleusercontent.com/J_MgDbBOzo2lg-mjnUvFkgyJCQFbVQIpMH_yZ1EBQE9AAa7Bs6koe8ijM-8Pp1JNWQ=s180-rw");
        menuList.add(a);
        a = new AppsMenu("Qumar","https://play.google.com/store/apps/details?id=com.com.ethioroot.kumar.kumar","com.com.ethioroot.kumar.kumar", "https://lh3.googleusercontent.com/C9JlUj-3ioABtSNeW0GimjzLYhkVWbzs3QEYDMZuHCbCLiFj8kfRshN_KZXOBgKSqueT=s180-rw");
        menuList.add(a);
        a = new AppsMenu("Hope music","https://play.google.com/store/apps/details?id=com.ethioroot.hopeentertainment","com.ethioroot.hopeentertainment", "https://lh3.googleusercontent.com/hhjCMJIpozR0-ytF6HFM9iqFFLFwePie-m57N8AyJsvC-2fymHOK3gu72Jhg2-R7N7Vz=s180-rw");
        menuList.add(a);

        a = new AppsMenu("All In One","https://play.google.com/store/apps/details?id=com.ethioroot.facebooklite","com.ethioroot.facebooklite", "https://lh3.googleusercontent.com/jJtUhHfNMdAobWRsPZ5evnkDtBGC-17xXUjJlfazmJ-lnIIFNACurWUtI_m2MjxzPt4M=s180-rw");
        menuList.add(a);

        a = new AppsMenu("Amharic keyboard FynGeez - Ethiopia","https://play.google.com/store/apps/details?id=com.fynsystems.fyngeez","com.fynsystems.fyngeez", "https://lh3.googleusercontent.com/po1rzTYojq9GTs5wN08gMgNshdBy6Q4snnjOT9SvnnEDtIgqpY5ZAKI6cpzHaEkJsbE=s180-rw");
        menuList.add(a);


        a = new AppsMenu("Edna Mall","https://play.google.com/store/apps/details?id=com.ednamall.ednamall.ednamall2&hl=en","com.ednamall.ednamall.ednamall2", "https://lh3.googleusercontent.com/xRCupukA3T_BmJW63cNTycztEMegJ9dowiymIAOAlnY1tL-2tVIcpxFlXjuN-zFToNFP=s180-rw");
        menuList.add(a);

        a = new AppsMenu("Ethiopian Calendar","https://play.google.com/store/apps/details?id=com.shalom.calendar&rdid=com.shalom.calendar","com.shalom.calendar", "https://lh3.googleusercontent.com/tMB_0w6F-YQxn2crpViaDQH_yCU2JLx06X06aDl1pzj_r9i9j1fEJfkrQXSj3XxOTh0=s180-rw");
        menuList.add(a);


        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
