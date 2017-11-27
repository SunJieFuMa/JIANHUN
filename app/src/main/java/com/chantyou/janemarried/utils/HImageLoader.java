package com.chantyou.janemarried.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.util.SparseArray;
import android.widget.ImageView;

import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.ref.SoftReference;
import java.util.Collection;

@SuppressLint("NewApi")
public class HImageLoader {

    private static HImageLoader instance;

    protected static final boolean DEVELOPER_MODE = true;
    protected ImageLoader imageLoader;
    DisplayImageOptions mOptions;
	private final SparseArray<SoftReference<DisplayImageOptions>> mSparseArray = new SparseArray<SoftReference<DisplayImageOptions>>();

	public static HImageLoader init() {
        if (instance == null) {
            synchronized (HImageLoader.class) {
                if (instance == null) {
					instance = new HImageLoader(AppAndroid.getApp());
                }
            }
        }
		return instance;
    }

    private HImageLoader(Context context) {
        imageLoader = ImageLoader.getInstance();
//		initImageLoader(context);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
		.memoryCacheExtraOptions(480, 800) // max width, max height 
		.threadPoolSize(3)//线程池内加载的数量 
		.threadPriority(Thread.NORM_PRIORITY - 2)  //降低线程的优先级保证主UI线程不受太大影响
		.denyCacheImageMultipleSizesInMemory() 
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.memoryCache(new LruMemoryCache(5 * 1024 * 1024))
		.memoryCacheSize(5 * 1024 * 1024)
		.diskCacheSize(50 * 1024 * 1024) // 50Mb
		.memoryCache(new LruMemoryCache(5 * 1024 * 1024)) //建议内存设在5-10M,可以有比较好的表现
		.tasksProcessingOrder(QueueProcessingType.LIFO) 
		.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) 
		.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)
        .build();
        // Initialize ImageLoader with configuration.
		imageLoader.init(config);
    }

	public static ImageLoader getImageLoader() {
		if (instance == null) {
			init();
		}
		return instance.imageLoader;
	}
	
	/**
	 * 本地文件缓存
	 * @param uri
	 */
	public void removeItemFileCache(String uri) {
		Collection<String> keys = imageLoader.getMemoryCache().keys();
		if(keys != null) {
			for(String key : keys) {
				if(key.contains(uri)) {
					imageLoader.getMemoryCache().remove(key);
				}
			}
		}
//        imageLoader.getMemoryCache().remove("file://" + uri);
        imageLoader.getDiskCache().remove("file://" + uri);
    }
	
	/**
	 * 显示本地文件
	 * 
	 * @param path
	 * @param iv
	 * @param options
	 */
	public static void setBitmapFile(String path, ImageView iv, DisplayImageOptions options) {
		String uri = "file://" + path;
		getImageLoader().displayImage(uri, new SimpleImageAware(iv, 0, 0), options);
//		displayImage(uri, iv, options);
	}

	public static void setBitmapDrawable(int recId, ImageView iv, int defId) {
		getImageLoader().displayImage(ImageDownloader.Scheme.DRAWABLE.wrap(recId+""), new SimpleImageAware(iv, 0, 0), createOptions(defId, 0));
	}

	/**
	 * 加载并显示图片
	 *
	 * @param uri
	 * @param iv
	 * @param defRes
	 */
	public static void displayImage(String uri, ImageView iv, int defRes) {
		displayImage(uri, iv, createOptions(defRes, 0), null);
	}

	/**
	 * 加载并显示图片
	 * 
	 * @param uri
	 * @param iv
	 * @param options
	 */
	public static void displayImage(String uri, ImageView iv, DisplayImageOptions options) {
		displayImage(uri, iv, options, null);
    }
	
	/**
	 * 加载并显示图片 +回调
	 * 
	 * @param uri
	 * @param iv
	 * @param options
	 * @param listener
	 */
	public static void displayImage(String uri, ImageView iv, DisplayImageOptions options, ImageLoadingListener listener) {
		displayImage(uri, iv, options, 0, 0, listener);
	}

	/**
	 * 按指定大学 显示图片
	 * @param uri
	 * @param iv
	 * @param options
	 * @param width
	 * @param height
	 */
	public static void displayImage(String uri, final ImageView iv,
			DisplayImageOptions options, int width, int height) {
		displayImage(uri, iv, options, width, height, null);
    }
	
	/**
	 * 
	 * @param uri
	 * @param iv
	 * @param options
	 * @param width
	 * @param height
	 * @param listener
	 */
	protected static void displayImage(String uri, final ImageView iv,
			DisplayImageOptions options, int width, int height, ImageLoadingListener listener) {
		if(uri != null && !uri.startsWith("http")) {
			uri = "";
		}
		getImageLoader().displayImage(uri, new SimpleImageAware(iv, width, height), options, listener);
	}
	
	public static void loadImge(String uri, ImageLoadingListener listener) {
		getImageLoader().loadImage(uri, listener);
	}

	protected void removeItemCache(String uri) {
        imageLoader.getMemoryCache().remove(uri);
        imageLoader.getDiskCache().remove(uri);
    }

    public void clearCache() {
        getImageLoader().getMemoryCache().clear();
        getImageLoader().getDiskCache().clear();
    }

	public static DisplayImageOptions createOptions(int defRecId, int duration) {
		SoftReference<DisplayImageOptions> soft = init().mSparseArray.get(defRecId);
		if(soft != null && soft.get() != null) {
			return soft.get();

		}
		DisplayImageOptions options =  
				new DisplayImageOptions.Builder()
					.showImageOnLoading(defRecId == 0 ? android.R.color.white : R.color.white_gray)
					.showImageForEmptyUri(defRecId == 0 ? android.R.color.white : defRecId)
					.showImageOnFail(defRecId == 0 ? android.R.color.white : defRecId)
//					.showStubImage(defRecId == 0 ? android.R.color.white : defRecId)
					.cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true)
					.resetViewBeforeLoading(true)
					.displayer(new FadeInBitmapDisplayer(duration))
					.bitmapConfig(Config.RGB_565)
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
					.build();
		init().mSparseArray.put(defRecId, new SoftReference<DisplayImageOptions>(options));
		return options;
    }
}
