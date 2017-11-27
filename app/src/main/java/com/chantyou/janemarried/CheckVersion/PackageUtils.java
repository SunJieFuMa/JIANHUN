package com.chantyou.janemarried.CheckVersion;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */

public class PackageUtils {
    public static final String TAG = "PackageUtils";
    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static final int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    public static final int INSTALL_FAILED_INVALID_APK = -2;
    public static final int INSTALL_FAILED_INVALID_URI = -3;
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;
    public static final int INSTALL_FAILED_DEXOPT = -11;
    public static final int INSTALL_FAILED_OLDER_SDK = -12;
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;
    public static final int INSTALL_FAILED_NEWER_SDK = -14;
    public static final int INSTALL_FAILED_TEST_ONLY = -15;
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
    public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
    public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;
    public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;
    public static final int INSTALL_FAILED_UID_CHANGED = -24;
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
    public static final int INSTALL_FAILED_OTHER = -1000000;
    public static final int DELETE_SUCCEEDED = 1;
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
    public static final int DELETE_FAILED_INVALID_PACKAGE = -3;
    public static final int DELETE_FAILED_PERMISSION_DENIED = -4;

    public PackageUtils() {
    }

    public static final int install(Context context, String filePath) {
        return !isSystemApplication(context) && !ShellUtils.checkRootPermission() ? (installNormal(context, filePath) ? 1 : -3) : installSilent(context, filePath);
    }

    public static boolean installNormal(Context context, String filePath) {
        Intent i = new Intent("android.intent.action.VIEW");
        File file = new File(filePath);
        if (file != null && file.exists() && file.isFile() && file.length() > 0L) {
            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(268435456);
            context.startActivity(i);
            return true;
        } else {
            return false;
        }
    }

    public static int installSilent(Context context, String filePath) {
        return installSilent(context, filePath, " -r " + getInstallLocationParams());
    }

    public static int installSilent(Context context, String filePath, String pmParams) {
        if (filePath != null && filePath.length() != 0) {
            File file = new File(filePath);
            if (file != null && file.length() > 0L && file.exists() && file.isFile()) {
                StringBuilder command = (new StringBuilder()).append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ").append(pmParams == null ? "" : pmParams).append(" ").append(filePath.replace(" ", "\\ "));
                ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command.toString(), !isSystemApplication(context), true);
                if (commandResult.successMsg == null || !commandResult.successMsg.contains("Success") && !commandResult.successMsg.contains("success")) {
                    Log.e("PackageUtils", "installSilent successMsg:" + commandResult.successMsg + ", ErrorMsg:" + commandResult.errorMsg);
                    return commandResult.errorMsg == null ? -1000000 : (commandResult.errorMsg.contains("INSTALL_FAILED_ALREADY_EXISTS") ? -1 : (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_APK") ? -2 : (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_URI") ? -3 : (commandResult.errorMsg.contains("INSTALL_FAILED_INSUFFICIENT_STORAGE") ? -4 : (commandResult.errorMsg.contains("INSTALL_FAILED_DUPLICATE_PACKAGE") ? -5 : (commandResult.errorMsg.contains("INSTALL_FAILED_NO_SHARED_USER") ? -6 : (commandResult.errorMsg.contains("INSTALL_FAILED_UPDATE_INCOMPATIBLE") ? -7 : (commandResult.errorMsg.contains("INSTALL_FAILED_SHARED_USER_INCOMPATIBLE") ? -8 : (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_SHARED_LIBRARY") ? -9 : (commandResult.errorMsg.contains("INSTALL_FAILED_REPLACE_COULDNT_DELETE") ? -10 : (commandResult.errorMsg.contains("INSTALL_FAILED_DEXOPT") ? -11 : (commandResult.errorMsg.contains("INSTALL_FAILED_OLDER_SDK") ? -12 : (commandResult.errorMsg.contains("INSTALL_FAILED_CONFLICTING_PROVIDER") ? -13 : (commandResult.errorMsg.contains("INSTALL_FAILED_NEWER_SDK") ? -14 : (commandResult.errorMsg.contains("INSTALL_FAILED_TEST_ONLY") ? -15 : (commandResult.errorMsg.contains("INSTALL_FAILED_CPU_ABI_INCOMPATIBLE") ? -16 : (commandResult.errorMsg.contains("INSTALL_FAILED_MISSING_FEATURE") ? -17 : (commandResult.errorMsg.contains("INSTALL_FAILED_CONTAINER_ERROR") ? -18 : (commandResult.errorMsg.contains("INSTALL_FAILED_INVALID_INSTALL_LOCATION") ? -19 : (commandResult.errorMsg.contains("INSTALL_FAILED_MEDIA_UNAVAILABLE") ? -20 : (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_TIMEOUT") ? -21 : (commandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_FAILURE") ? -22 : (commandResult.errorMsg.contains("INSTALL_FAILED_PACKAGE_CHANGED") ? -23 : (commandResult.errorMsg.contains("INSTALL_FAILED_UID_CHANGED") ? -24 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NOT_APK") ? -100 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_MANIFEST") ? -101 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION") ? -102 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NO_CERTIFICATES") ? -103 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES") ? -104 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING") ? -105 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME") ? -106 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID") ? -107 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_MALFORMED") ? -108 : (commandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_EMPTY") ? -109 : (commandResult.errorMsg.contains("INSTALL_FAILED_INTERNAL_ERROR") ? -110 : -1000000)))))))))))))))))))))))))))))))))));
                } else {
                    return 1;
                }
            } else {
                return -3;
            }
        } else {
            return -3;
        }
    }

    public static final int uninstall(Context context, String packageName) {
        return !isSystemApplication(context) && !ShellUtils.checkRootPermission() ? (uninstallNormal(context, packageName) ? 1 : -3) : uninstallSilent(context, packageName);
    }

    public static boolean uninstallNormal(Context context, String packageName) {
        if (packageName != null && packageName.length() != 0) {
            Intent i = new Intent("android.intent.action.DELETE", Uri.parse((new StringBuilder(32)).append("package:").append(packageName).toString()));
            i.addFlags(268435456);
            context.startActivity(i);
            return true;
        } else {
            return false;
        }
    }

    public static int uninstallSilent(Context context, String packageName) {
        return uninstallSilent(context, packageName, true);
    }

    public static int uninstallSilent(Context context, String packageName, boolean isKeepData) {
        if (packageName != null && packageName.length() != 0) {
            StringBuilder command = (new StringBuilder()).append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall").append(isKeepData ? " -k " : " ").append(packageName.replace(" ", "\\ "));
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command.toString(), !isSystemApplication(context), true);
            if (commandResult.successMsg != null && (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success"))) {
                return 1;
            } else {
                Log.e("PackageUtils", "uninstallSilent successMsg:" + commandResult.successMsg + ", ErrorMsg:" + commandResult.errorMsg);
                return commandResult.errorMsg == null ? -1 : (commandResult.errorMsg.contains("Permission denied") ? -4 : -1);
            }
        } else {
            return -3;
        }
    }

    public static boolean isSystemApplication(Context context) {
        return context == null ? false : isSystemApplication(context, context.getPackageName());
    }

    public static boolean isSystemApplication(Context context, String packageName) {
        return context == null ? false : isSystemApplication(context.getPackageManager(), packageName);
    }

    public static boolean isSystemApplication(PackageManager packageManager, String packageName) {
        if (packageManager != null && packageName != null && packageName.length() != 0) {
            try {
                ApplicationInfo e = packageManager.getApplicationInfo(packageName, 0);
                return e != null && (e.flags & 1) > 0;
            } catch (PackageManager.NameNotFoundException var3) {
                var3.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static Boolean isTopActivity(Context context, String packageName) {
        if (context != null && !TextUtils.isEmpty(packageName)) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            List tasksInfo = activityManager.getRunningTasks(1);
            if (ListUtils.isEmpty(tasksInfo)) {
                return null;
            } else {
                try {
                    return Boolean.valueOf(packageName.equals(((ActivityManager.RunningTaskInfo) tasksInfo.get(0)).topActivity.getPackageName()));
                } catch (Exception var5) {
                    var5.printStackTrace();
                    return Boolean.valueOf(false);
                }
            }
        } else {
            return null;
        }
    }

    public static String getAppVersionName(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                try {
                    PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionName;
                    }
                } catch (PackageManager.NameNotFoundException var4) {
                    var4.printStackTrace();
                }
            }
        }

        return "0.0.0";
    }

    public static int getAppVersionCode(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                try {
                    PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                    if (pi != null) {
                        return pi.versionCode;
                    }
                } catch (PackageManager.NameNotFoundException var4) {
                    var4.printStackTrace();
                }
            }
        }

        return -1;
    }

    public static int getInstallLocation() {
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", false, true);
        if (commandResult.result == 0 && commandResult.successMsg != null && commandResult.successMsg.length() > 0) {
            try {
                int e = Integer.parseInt(commandResult.successMsg.substring(0, 1));
                switch (e) {
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                }
            } catch (NumberFormatException var2) {
                var2.printStackTrace();
                Log.e("PackageUtils", "pm get-install-location error");
            }
        }

        return 0;
    }

    private static String getInstallLocationParams() {
        int location = getInstallLocation();
        switch (location) {
            case 1:
                return "-f";
            case 2:
                return "-s";
            default:
                return "";
        }
    }

    public static void startInstalledAppDetails(Context context, String packageName) {
        Intent intent = new Intent();
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", packageName, (String) null));
        } else {
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(sdkVersion == 8 ? "pkg" : "com.android.settings.ApplicationPkgName", packageName);
        }

        intent.addFlags(268435456);
        context.startActivity(intent);
    }
}
