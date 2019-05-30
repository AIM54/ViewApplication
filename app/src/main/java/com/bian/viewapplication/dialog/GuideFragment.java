package com.bian.viewapplication.dialog;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.ViewLoactionBean;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.view.NewGuildeView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;


public class GuideFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int[] mParam1;
    private ViewLoactionBean mParam2;
    private NewGuildeView newGuildeView;
    private ListView mListView;
    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0);
        getDialog().getWindow().getDecorView().setPadding(0, 0, 0, 0);
    }


    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    public static GuideFragment newInstance(int[] param1, ViewLoactionBean param2) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putIntArray(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        if (getArguments() != null) {
            mParam1 = getArguments().getIntArray(ARG_PARAM1);
            mParam2 = getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newGuildeView = view.findViewById(R.id.guildev_view);
        new Handler().post(() -> {
         int activityHeight=getActivity().getWindow().getDecorView().getMeasuredHeight();
         int dialogHeight=getDialog().getWindow().getDecorView().getMeasuredHeight();
         CommonLog.i(String.format("activityHeight:%d,dialogHeight:%d",activityHeight,dialogHeight));
            if (mParam2 != null) {
                CommonLog.i(mParam2.toString());
                newGuildeView.setTargetView(mParam2);
            }
        });
    }

    public void setNewLocationBean(@NotNull ViewLoactionBean viewloactin) {
        if (newGuildeView != null) {
            newGuildeView.setTargetView(viewloactin);
        }
    }


    public void initOption(){
        RequestOptions options2 = RequestOptions.bitmapTransform(new RoundedCorners(6))
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate();
        Glide.with(getContext())
                .load("file:///android_asset/ic_image.jpg")
                .apply(options2)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    }
                });
    }
}
