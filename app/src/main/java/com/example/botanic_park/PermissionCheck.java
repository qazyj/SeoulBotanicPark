package com.example.botanic_park;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

// 권한 체크, 요청을 담당
public class PermissionCheck {

    public static boolean isGrantedPermission(Activity activity, String permission) {
        int permissionResult = ActivityCompat.checkSelfPermission(activity, permission);
        if (permissionResult == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    public static void requestPermissions(Activity activity, String[] permissions, int permissionCode) {
        ActivityCompat.requestPermissions(activity, permissions, permissionCode);
    }

    public static boolean verifyPermission(int[] grantResults) {
        // 모든 권한이 허가 받았는지 확인
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
}