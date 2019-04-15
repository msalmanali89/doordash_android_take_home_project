package com.jobrapp.androidinterview.adapters;


import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jobrapp.androidinterview.R;
import com.jobrapp.androidinterview.commons.ImageLoaderImpl;
import com.jobrapp.androidinterview.dto.RestaurantDTO;
import com.jobrapp.androidinterview.interfaces.ImageLoader;
import com.jobrapp.androidinterview.interfaces.ItemClick;
import com.jobrapp.androidinterview.interfaces.OnBottomReachedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {



    int offset = 0;
    int limit = 20;

    private ItemClick itemClick;
    private List<RestaurantDTO> itemList;
    private Context mContext;
    RequestOptions requestOptions;
    int orientation;
    ConstraintSet constraintSet = new ConstraintSet();

    ImageLoaderImpl imageLoader =  new ImageLoaderImpl();

    OnBottomReachedListener onBottomReachedListener;

    public RestaurantListAdapter(Context mContext , List<RestaurantDTO> itemList) {

        this.orientation = orientation;

        this.itemList = itemList;
        this.mContext = mContext;
        requestOptions= new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

    }
    public void setDataSet(List<RestaurantDTO> itemList){
        this.itemList.addAll(itemList);
    }
    public void setOrientation(int orientation){
        this.orientation = orientation;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick =  itemClick;
    }



    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        view = inflater.inflate(R.layout.user_item_row, parent, false);

        return new RestaurantViewHolder(view,imageLoader);

    }

    @Override
    public void onBindViewHolder(final RestaurantViewHolder holder, int position) {

        holder.populate(itemList.get(position));



        if (isBottom(position)){
            offset = offset + 30;
            onBottomReachedListener.onBottomReached(position,offset,limit);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {


        ConstraintLayout parentContsraint;

        @BindView(R.id.thumbnailImage)
        ImageView thumbnailImage;

        @BindView(R.id.titleTxtView)
        TextView titleTxtView;

        @BindView(R.id.descriptionTxtView)
        TextView descriptionTxtView;

        @BindView(R.id.minTxtView)
        TextView minTxtView;

        ImageLoader imageLoader;


        public RestaurantViewHolder(View itemView, ImageLoader imageLoader) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            parentContsraint = itemView.findViewById(R.id.parentContsraint);

            this.imageLoader = imageLoader;

            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            itemClick.onItemClick(itemList.get(getAdapterPosition()));

        }

        void populate(RestaurantDTO restaurantDTO) {
            imageLoader.load(itemView.getContext(),
                    restaurantDTO.getCoverImgURL(),
                    R.drawable.placeholder,
                    thumbnailImage);



            titleTxtView.setText(restaurantDTO.getName());
            descriptionTxtView.setText(restaurantDTO.getDescription());

            if(restaurantDTO.getStatusType().contentEquals("open")) { // HardCoded
                minTxtView.setText(restaurantDTO.getStatus());
            }else{
                minTxtView.setText(restaurantDTO.getStatusType());
            }

        }
    }

    boolean isBottom(int position){
        if (position == getItemCount() - 1){
            offset = offset + 30;
            return true;
        }
        return false;
    }

    /*

        Glide.with(mContext)
                .load(itemList.get(position).getCoverImgURL())//.transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }



                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        DrawableImageViewTarget glideTarget = (DrawableImageViewTarget) target;
                        final ImageView iv = glideTarget.getView();
                        int width = iv.getMeasuredWidth();
                        int targetHeight = width * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                        if(iv.getLayoutParams().height != targetHeight) {
                            //iv.getLayoutParams().height = targetHeight;
                            //iv.requestLayout();
                        }

                        iv.requestLayout();

                        return false;
                    }
                })
                .into(holder.thumbnailImage);
        holder.titleTxtView.setText(itemList.get(position).getName());
        holder.descriptionTxtView.setText(itemList.get(position).getDescription());

        if(itemList.get(position).getStatusType().contentEquals("open")) { // HardCoded
            holder.minTxtView.setText(itemList.get(position).getStatus());
        }else{
            holder.minTxtView.setText(itemList.get(position).getStatusType());
        }

     */


}

