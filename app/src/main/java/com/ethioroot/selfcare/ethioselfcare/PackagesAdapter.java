package com.ethioroot.selfcare.ethioselfcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.MyViewHolder> {

    private Context mContext;
    private List<PackagesMenu> menuList;


    public Animation animBounce,animZoomin,animZoomout;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, period;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            period = (TextView) view.findViewById(R.id.period);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);

        }

    }

    public PackagesAdapter(Context mContext, List<PackagesMenu> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_card, parent, false);

        return new MyViewHolder(itemView);
    }
    private int lastPosition = -1;

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    private void setZoomAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.zoom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PackagesMenu menulist = menuList.get(position);


      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

              builder.setTitle("Confirm Action");
              builder.setMessage("Your are Making Call For  "+menulist.getName()+" For  "+menulist.getPeriod() +"\n "+menulist.getPhoneNum());

              builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                  public void onClick(DialogInterface dialog, int which) {

                      Toast.makeText(mContext, "Calling "+menulist.getPhoneNum(), Toast.LENGTH_SHORT).show();
                      PhoneCaller.MakeCall(menulist.getPhoneNum(),mContext);

                      dialog.dismiss();
                  }
              });

              builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                      // Do nothing
                      dialog.dismiss();
                  }
              });

              AlertDialog alert = builder.create();
              alert.show();




          }
      });
        holder.title.setText(menulist.getName());

holder.count.setText(menulist.getPhoneNum());
holder.period.setText(menulist.getPeriod());

        Picasso.with(mContext).load(menulist.getThumbnail()).fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.thumbnail);


        setAnimation(holder.itemView, position);


/*
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
         */
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        Toast.makeText(mContext,"Cliecked ",Toast.LENGTH_LONG).show();


        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:


                        return true;
                case R.id.action_play_next:


                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
