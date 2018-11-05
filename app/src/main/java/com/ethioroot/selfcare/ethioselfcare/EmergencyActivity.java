package com.ethioroot.selfcare.ethioselfcare;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class EmergencyActivity extends AppCompatActivity {


    public Animation animBounce;
    private RecyclerView recyclerView;
    private PackagesAdapter adapter;
    private List<PackagesMenu> menuList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);



        recyclerView =   findViewById(R.id.recycler_view);


        menuList = new ArrayList<>();
        adapter = new PackagesAdapter(EmergencyActivity.this, menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(EmergencyActivity.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new EmergencyActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();


        animBounce = AnimationUtils.loadAnimation(EmergencyActivity.this,
                R.anim.bounce);

    }


    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        menuList.clear();
        int[] covers = new int[]{
                R.drawable.emergency,
                R.drawable.data,
                R.drawable.sms};

        PackagesMenu a = new PackagesMenu("Police","*991","Plice",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Police","+251115512744","Federal Plice",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Police","+251111559122","Addis Ababa Plice",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Police","+251115528222","Tiraffic Plice",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","991","Ethiopian Electric Power Corporation (EEPCo)",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0114431478","South Addis Abeba ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0114431478","South Addis Abeba ",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0112-595657","North Addis Abeba ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0113490585","West Addis Abeba ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Ethiopian Electric Power Corporation (EEPCo)","0111262105","Central Addis Abeba ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Information","997","Infrmation ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Information","998","International Information ",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Addis Ababa Ambulance Service","991","Ambulance Service ",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Addis Ababa Ambulance Service","+251111234272","Minilik Hospital",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Ambulance Service","+251111115348","Municipality ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Red Cross society","907","Red Cross society ",  covers[0]);
        menuList.add(a);



        a = new PackagesMenu("Addis Ababa Fire Brigades","991","Addis Ababa Fire Brigades",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Fire Brigades","+251111123341","First station (Arada) ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Fire Brigades","+251114160279","Second station( kirkos)",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Addis Ababa Fire Brigades","+251112134239","Third station (Addis Ketema)",  covers[0]);
        menuList.add(a);


        a = new PackagesMenu("Addis Ababa Fire Brigades","+251114425563","Fourth station ( Lafto) ",  covers[0]);
        menuList.add(a);

        a = new PackagesMenu("Addis Ababa Fire Brigades","+251114340096","Fifth staion (Akaki Kality) ",  covers[0]);
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
