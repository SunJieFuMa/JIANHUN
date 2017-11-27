package com.chantyou.janemarried.CheckVersion;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/7/24.
 */

@SuppressLint({"NewApi"})
public class DownloadManagerPro {
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    public static final String COLUMN_LOCAL_FILENAME = "local_filename";
    public static final String COLUMN_LOCAL_URI = "local_uri";
    public static final String METHOD_NAME_PAUSE_DOWNLOAD = "pauseDownload";
    public static final String METHOD_NAME_RESUME_DOWNLOAD = "resumeDownload";
    private static boolean isInitPauseDownload = false;
    private static boolean isInitResumeDownload = false;
    private static Method pauseDownload = null;
    private static Method resumeDownload = null;
    private DownloadManager downloadManager;

    public DownloadManagerPro(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    public int getStatusById(long downloadId) {
        return this.getInt(downloadId, "status");
    }

    public int[] getDownloadBytes(long downloadId) {
        int[] bytesAndStatus = this.getBytesAndStatus(downloadId);
        return new int[]{bytesAndStatus[0], bytesAndStatus[1]};
    }

    public int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{-1, -1, 0};
        DownloadManager.Query query = (new DownloadManager.Query()).setFilterById(new long[]{downloadId});
        Cursor c = null;

        try {
            c = this.downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow("bytes_so_far"));
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow("total_size"));
                bytesAndStatus[2] = c.getInt(c.getColumnIndex("status"));
            }
        } finally {
            if (c != null) {
                c.close();
            }

        }

        return bytesAndStatus;
    }

    public int pauseDownload(long... ids) {
        initPauseMethod();
        if (pauseDownload == null) {
            return -1;
        } else {
            try {
                return ((Integer) pauseDownload.invoke(this.downloadManager, new Object[]{ids})).intValue();
            } catch (Exception var3) {
                var3.printStackTrace();
                return -1;
            }
        }
    }

    public int resumeDownload(long... ids) {
        initResumeMethod();
        if (resumeDownload == null) {
            return -1;
        } else {
            try {
                return ((Integer) resumeDownload.invoke(this.downloadManager, new Object[]{ids})).intValue();
            } catch (Exception var3) {
                var3.printStackTrace();
                return -1;
            }
        }
    }

    public static boolean isExistPauseAndResumeMethod() {
        initPauseMethod();
        initResumeMethod();
        return pauseDownload != null && resumeDownload != null;
    }

    private static void initPauseMethod() {
        if (!isInitPauseDownload) {
            isInitPauseDownload = true;

            try {
                pauseDownload = DownloadManager.class.getMethod("pauseDownload", new Class[]{long[].class});
            } catch (Exception var1) {
                var1.printStackTrace();
            }

        }
    }

    private static void initResumeMethod() {
        if (!isInitResumeDownload) {
            isInitResumeDownload = true;

            try {
                resumeDownload = DownloadManager.class.getMethod("resumeDownload", new Class[]{long[].class});
            } catch (Exception var1) {
                var1.printStackTrace();
            }

        }
    }

    public String getFileName(long downloadId) {
        return this.getString(downloadId, Build.VERSION.SDK_INT < 11 ? "local_uri" : "local_filename");
    }

    public String getUri(long downloadId) {
        return this.getString(downloadId, "uri");
    }

    public int getReason(long downloadId) {
        return this.getInt(downloadId, "reason");
    }

    public int getPausedReason(long downloadId) {
        return this.getInt(downloadId, "reason");
    }

    public int getErrorCode(long downloadId) {
        return this.getInt(downloadId, "reason");
    }

    private String getString(long downloadId, String columnName) {
        DownloadManager.Query query = (new DownloadManager.Query()).setFilterById(new long[]{downloadId});
        String result = null;
        Cursor c = null;

        try {
            c = this.downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getString(c.getColumnIndex(columnName));
            }
        } finally {
            if (c != null) {
                c.close();
            }

        }

        return result;
    }

    private int getInt(long downloadId, String columnName) {
        DownloadManager.Query query = (new DownloadManager.Query()).setFilterById(new long[]{downloadId});
        int result = -1;
        Cursor c = null;

        try {
            c = this.downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getInt(c.getColumnIndex(columnName));
            }
        } finally {
            if (c != null) {
                c.close();
            }

        }

        return result;
    }

    public static class RequestPro extends DownloadManager.Request {
        public static final String METHOD_NAME_SET_NOTI_CLASS = "setNotiClass";
        public static final String METHOD_NAME_SET_NOTI_EXTRAS = "setNotiExtras";
        private static boolean isInitNotiClass = false;
        private static boolean isInitNotiExtras = false;
        private static Method setNotiClass = null;
        private static Method setNotiExtras = null;

        public RequestPro(Uri uri) {
            super(uri);
        }

        public void setNotiClass(String className) {
            synchronized (this) {
                if (!isInitNotiClass) {
                    isInitNotiClass = true;

                    try {
                        setNotiClass = DownloadManager.Request.class.getMethod("setNotiClass", new Class[]{CharSequence.class});
                    } catch (Exception var5) {
                        var5.printStackTrace();
                    }
                }
            }

            if (setNotiClass != null) {
                try {
                    setNotiClass.invoke(this, new Object[]{className});
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

        }

        public void setNotiExtras(String extras) {
            synchronized (this) {
                if (!isInitNotiExtras) {
                    isInitNotiExtras = true;

                    try {
                        setNotiExtras = DownloadManager.Request.class.getMethod("setNotiExtras", new Class[]{CharSequence.class});
                    } catch (Exception var5) {
                        var5.printStackTrace();
                    }
                }
            }

            if (setNotiExtras != null) {
                try {
                    setNotiExtras.invoke(this, new Object[]{extras});
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

        }
    }
}
