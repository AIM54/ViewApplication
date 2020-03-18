package com.bian.viewapplication.activity.camera;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.TakePhotoItem;

import java.util.List;

public class CameraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TakePhotoItem> mDataList;
    private static final int NEED_TAKE_PHOTO = 1;
    private static final int HAS_PHOTOTED = 2;

    private View.OnClickListener itemClickListener;

    public View.OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CameraAdapter(List<TakePhotoItem> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return TextUtils.isEmpty(mDataList.get(position).getImageUrl()) ? NEED_TAKE_PHOTO : HAS_PHOTOTED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NEED_TAKE_PHOTO) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_take_photo, parent, false);
            return new TakePhotoViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_has_photoed, parent, false);
            return new CarPreviewViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TakePhotoViewHolder) {
            ((TakePhotoViewHolder) holder).nameTv.setText(mDataList.get(position).getNameCn());
            ((TakePhotoViewHolder) holder).countTv.setText((position + 1) + "/" + getItemCount());
            ((TakePhotoViewHolder) holder).rootLayout.setSelected(mDataList.get(position).isSelected());
            if (itemClickListener!=null){
                ((TakePhotoViewHolder) holder).rootLayout.setTag(position);
                ((TakePhotoViewHolder) holder).rootLayout.setOnClickListener(itemClickListener);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    private class TakePhotoViewHolder extends RecyclerView.ViewHolder {

        TextView countTv;
        TextView nameTv;
        TextView markTv;
        RelativeLayout rootLayout;

        public TakePhotoViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.tv_point_name);
            countTv = (TextView) itemView.findViewById(R.id.tv_count);
            rootLayout = (RelativeLayout) itemView.findViewById(R.id.rl_root);
            markTv = itemView.findViewById(R.id.tv_mark);
        }
    }

    private class CarPreviewViewHolder extends RecyclerView.ViewHolder {
        ImageView carImageView;
        FrameLayout deleteFl;
        FrameLayout rootLayout;
        TextView countTv;
        TextView nameTv;
        ImageView deleteCarIv;
        TextView markTv;

        public CarPreviewViewHolder(View itemView) {
            super(itemView);
            carImageView = (ImageView) itemView.findViewById(R.id.iv_item_car);
            deleteFl = (FrameLayout) itemView.findViewById(R.id.fl_delete);
            rootLayout = (FrameLayout) itemView.findViewById(R.id.fl_root);
            countTv = (TextView) itemView.findViewById(R.id.tv_count);
            nameTv = (TextView) itemView.findViewById(R.id.tv_point_name);
            deleteCarIv = itemView.findViewById(R.id.iv_delete_car_photo);
            markTv = itemView.findViewById(R.id.tv_mark);
        }
    }
}
