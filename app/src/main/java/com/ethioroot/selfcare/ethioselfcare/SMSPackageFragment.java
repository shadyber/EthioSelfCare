package com.ethioroot.selfcare.ethioselfcare;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SMSPackageFragment extends Fragment {


    EditText phoneNumber;
    TextView txtphone,txtname;
    Button btnSelectContat;
    RadioButton radioother,radiome;
    RadioGroup radioGroup;

    public Animation animBounce;
    private RecyclerView recyclerView;
    private PackagesAdapter adapter;
    private List<PackagesMenu> menuList;



    public void selectContact(){
        Intent calContctPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        calContctPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(calContctPickerIntent, 1);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode)
        {
            case (1) :
                if (resultCode == Activity.RESULT_OK)
                {
                    Uri contctDataVar = data.getData();

                    Cursor contctCursorVar = getContext().getContentResolver().query(contctDataVar, null,
                            null, null, null);
                    if (contctCursorVar.getCount() > 0)
                    {
                        while (contctCursorVar.moveToNext())
                        {
                            String ContctUidVar = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.Contacts._ID));

                            String ContctNamVar = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                            Log.i("Names", ContctNamVar);
                            txtname.setText("Name : "+ContctNamVar);
                            if (Integer.parseInt(contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                            {
                                // Query phone here. Covered next
                                String ContctMobVar = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                Log.i("Number", ContctMobVar);
                                txtphone.setText(ContctMobVar);
                                phoneNumber.setText(ContctMobVar);
                                radioother.setChecked(true);
                                prepareAlbumsforother();
                            }

                        }
                    }
                }
                break;
        }
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.voickpackagefragment, parent, false);
    }


    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);


        recyclerView =   view.findViewById(R.id.recycler_view);


        txtname=view.findViewById(R.id.textName);
        txtphone=view.findViewById(R.id.textPhone);
        phoneNumber=view.findViewById(R.id.txtContact);
        btnSelectContat=view.findViewById(R.id.btnSelectContat);
        radiome=view.findViewById(R.id.radiome);
        radioother=view.findViewById(R.id.radioother);

        radioother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContact();
            }
        });

        radiome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtphone.setText("");
                txtname.setText("");
                phoneNumber.setText("");
            }
        });

     radioGroup = (RadioGroup) view.findViewById(R.id.sendto);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                  if(checkedId==R.id.radiome){

                      prepareAlbums();
                  }else if (checkedId==R.id.radioother){
                    //  prepareAlbumsforother();

                  }
            }
        });



        btnSelectContat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContact();
            }
        });

        phoneNumber.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                txtphone.setText(phoneNumber.getText().toString().replace("+251","0"));
                prepareAlbumsforother();
                return false;
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtphone.setText(phoneNumber.getText().toString().replace("+251","0").replace(" ",""));
                radioother.setChecked(true);
prepareAlbumsforother();
            }
        });


        menuList = new ArrayList<>();
        adapter = new PackagesAdapter(getContext(), menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SMSPackageFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();




        animBounce = AnimationUtils.loadAnimation(getContext(),
                R.anim.bounce);



    }
    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        menuList.clear();
        int[] covers = new int[]{
                R.drawable.voice,
                R.drawable.data,
                R.drawable.sms};

        PackagesMenu a = new PackagesMenu("2 Birr 18 SMS","*999*1#1#3#1#1#1#","24 Hr",  covers[2]);
        menuList.add(a);

        a = new PackagesMenu("3 Birr 35 SMS","*999*1#1#3#1#2#1#","24 Hr",  covers[2]);
        menuList.add(a);
        a = new PackagesMenu("5 Birr 70 SMS","*999*1#1#3#1#3#1#","24 Hr",  covers[2]);
        menuList.add(a);
//week


        a = new PackagesMenu("10 Birr 140 SMS","*999*1#1#3#2#1#1#","1 Week",  covers[2]);
        menuList.add(a);

        a = new PackagesMenu("15 Birr 283 SMS","*999*1#1#3#2#2#1#","1 Week",  covers[2]);
        menuList.add(a);
// monthly

        a = new PackagesMenu("30 Birr 525 SMS","*999*1#1#3#3#1#1#","1 Month",  covers[2]);
        menuList.add(a);

        a = new PackagesMenu("50 Birr 1050 SMS","*999*1#1#3#3#2#1#","1 Month",  covers[2]);
        menuList.add(a);

        adapter.notifyDataSetChanged();
    }

    private void prepareAlbumsforother() {
String number=txtphone.getText().toString().replace("+251","0").replace(" ","");
        menuList.clear();
        int[] covers = new int[]{
                R.drawable.voice,
                R.drawable.data,
                R.drawable.sms};

        PackagesMenu a = new PackagesMenu("2 Birr 18 SMS","*999*1#1#3#1#1#"+number+"#1#","24 Hr",  covers[2]);
        menuList.add(a);

        a = new PackagesMenu("3 Birr 35 SMS","*999*1#1#3#1#2#"+number+"#1#","24 Hr",  covers[2]);
        menuList.add(a);
        a = new PackagesMenu("5 Birr 70 SMS","*999*1#1#3#1#3#"+number+"#1#","24 Hr",  covers[2]);
        menuList.add(a);
//week


        a = new PackagesMenu("10 Birr 140 SMS","*999*1#1#3#2#1#"+number+"#1#","1 Week",  covers[2]);
        menuList.add(a);

        a = new PackagesMenu("15 Birr 283 SMS","*999*1#1#3#2#2#"+number+"#1#","1 Week",  covers[2]);
        menuList.add(a);
// monthly

        a = new PackagesMenu("30 Birr 525 SMS","*999*1#1#3#3#1#"+number+"#1#","1 Month",  covers[2]);
        menuList.add(a);

        a = new PackagesMenu("50 Birr 1050 SMS","*999*1#1#3#3#2#"+number+"#1#","1 Month",  covers[2]);
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
