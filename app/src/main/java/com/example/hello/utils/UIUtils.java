package com.example.hello.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UIUtils {

    public static void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int fragmentFrameId, @Nullable String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentFrameId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public static void replaceFragmentWithoutBack(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int fragmentFrameId, @Nullable String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentFrameId, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
