package com.chantyou.janemarried.ui.qingjian;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chantyou.janemarried.AppAndroid;
import com.chantyou.janemarried.R;
import com.chantyou.janemarried.base.MyBaseActivity;
import com.chantyou.janemarried.config.UrlConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Call;

public class PhotoViewActivity extends MyBaseActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 2;
    private String photoView_imageUrl;
    private ImageView icon_tu;
    //    private AbsChoosePhotoHelper2 choosePhotoHelper;
    private String mPicPath;
    private ImageButton ib_back;
    private TextView tv_save;
    private ImageView iv_bg;
    private long userPageId;
    private long userTempleteId;
    private int componentId;
    //    private PhotoView iv_photo2;
    private ImageView iv_photo2;
    private int width;
    private int height;
    private int top;
    private int[] location = new int[2];
    private RelativeLayout rl_container;
    private boolean canAddPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        rl_container = (RelativeLayout) findViewById(R.id.rl_container);
        icon_tu = (ImageView) findViewById(R.id.icon_tu);
        icon_tu.setOnClickListener(this);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        iv_bg = (ImageView) findViewById(R.id.iv_photo);
        iv_bg.setOnClickListener(this);
        //        iv_photo2 = (PhotoView) findViewById(R.id.iv_photo2);
        iv_photo2 = (ImageView) findViewById(R.id.iv_photo2);
        //intent传递过来的图片地址
        photoView_imageUrl = getIntent().getStringExtra("photoView_imageUrl");
        userPageId = getIntent().getLongExtra("userPageId", 0);
        userTempleteId = getIntent().getLongExtra("userTempleteId", 0);
        componentId = getIntent().getIntExtra("componentId", 0);
        System.out.println("接收到的pageId::" + userPageId + " userTempleteId::" + userTempleteId
                + "componentId::" + componentId);
        //这个布尔值是用来判断是否可以在这个模板页面上添加图片，默认是可以的
        canAddPhoto = getIntent().getBooleanExtra("canAddPhoto", true);

        Glide.with(AppAndroid.getContext()).load(photoView_imageUrl)
                .into(iv_bg);

        Toast.makeText(this, "可以通过点击中间的红色图标去图库中选择图片~", Toast.LENGTH_LONG).show();
        //        rl_container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        //            @Override
        //            public void onGlobalLayout() {
        //                //                Toast.makeText(PhotoViewActivity.this, " sdasdasdasdasdadad", Toast.LENGTH_SHORT).show();
        //                rl_container.getLocationOnScreen(location);
        //                width = rl_container.getWidth();
        //                height = rl_container.getHeight();
        //                top = rl_container.getTop();
        //                rl_container.getViewTreeObserver()
        //                        .removeGlobalOnLayoutListener(this);//取消监听
        //            }
        //        });


        //        iv_photo2.post(new Runnable() {
        //            @Override
        //            public void run() {
        //                measuredWidth = iv_photo2.getWidth();
        //                measuredHeight = iv_photo2.getHeight();
        //                Toast.makeText(PhotoViewActivity.this,
        //                        "得到的宽高1：" + measuredWidth + " " + measuredHeight, Toast.LENGTH_SHORT).show();
        //            }
        //        });


       /* iv_bg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_bg.getLocationOnScreen(location);
                width = iv_bg.getWidth();
                height = iv_bg.getHeight();
                iv_photo2.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });*/
        //        Toast.makeText(PhotoViewActivity.this,
        //                "得到的宽高：" + width + " " + height, Toast.LENGTH_SHORT).show();
        //        Toast.makeText(PhotoViewActivity.this,
        //                "location宽：" + location[0] + " location高" + location[1], Toast.LENGTH_SHORT).show();

        /*choosePhotoHelper = new AbsChoosePhotoHelper2(this) {
            @Override
            public void launchActivityForResult(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }

            @Override
            public void onPictureChoosed(String filePath, String displayName) {
                super.onPictureChoosed(filePath, displayName);
                mPicPath = filePath;
                icon_tu.setVisibility(View.GONE);
                //                Glide.with(AppAndroid.getContext()).load(mPicPath).into(iv_photo2);
                //                这样写的话得到的宽高都为0
                //                int measuredWidth = iv_photo2.getMeasuredWidth();
                //                int measuredHeight = iv_photo2.getMeasuredHeight();
                //                Toast.makeText(PhotoViewActivity.this,
                //                        "得到的宽高："+measuredWidth+" "+measuredHeight, Toast.LENGTH_SHORT).show();
                //                Glide.with(AppAndroid.getContext()).load(mPicPath).into(iv_photo2);
                *//*
                //走不到这个方法里面
                iv_photo2.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                    @Override
                    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                        int measuredWidth = iv_photo2.getMeasuredWidth();
                        int measuredHeight = iv_photo2.getMeasuredHeight();
                        Toast.makeText(PhotoViewActivity.this,
                        "得到的宽高："+measuredWidth+" "+measuredHeight, Toast.LENGTH_SHORT).show();
                        Glide.with(AppAndroid.getContext()).load(mPicPath)
                                .override(measuredWidth, measuredHeight).into(iv_photo2);

                    }
                });*//*

                //                Toast.makeText(PhotoViewActivity.this,
                //                        "得到的宽高2：" + measuredWidth + " " + measuredHeight, Toast.LENGTH_SHORT).show();
                //                Glide.with(AppAndroid.getContext()).load(mPicPath)
                //                        .override(measuredWidth, measuredHeight).into(iv_photo2);
                //                Glide.with(AppAndroid.getContext()).load(mPicPath).fitCenter().into(iv_photo2);
                HImageLoader.init().removeItemFileCache(filePath);
                HImageLoader.setBitmapFile(filePath, iv_photo2, HImageLoader.createOptions(R.color.transparent, 0));
                //让布局里面fragment里面最上面的这个view失去焦点，不能点击，这样下面的view才能获取焦点，拖拽放大缩小
                iv_bg.setClickable(false);
                iv_bg.setFocusable(false);
                iv_bg.setFocusableInTouchMode(false);
            }
        };*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //        choosePhotoHelper.onActivityResult(requestCode, resultCode, data, RESULT_OK);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mPicPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT).get(0);
                System.out.println("本地图片地址："+mPicPath);
                Glide.with(this).load(mPicPath)
                        .into(iv_photo2);
                icon_tu.setVisibility(View.GONE);
                //让布局里面fragment里面最上面的这个view失去焦点，不能点击，这样下面的view才能获取焦点，拖拽放大缩小
                iv_bg.setClickable(false);
                iv_bg.setFocusable(false);
                iv_bg.setFocusableInTouchMode(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo:
                if (canAddPhoto)//如果这个模板能在上面添加图片，那么就让图标显示，否则不显示
                    icon_tu.setVisibility(View.VISIBLE);
                break;
            case R.id.icon_tu:
                doRequestPermissions();
                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_save:
                doSave();
                break;
        }

    }

    private void takePhoto() {
        MultiImageSelector.create(this)
                .showCamera(true) // show camera or not. true by default
                .count(1) // max select image size, 9 by default. used width #.multi()
                .single() // single mode
                //                    .multi() // multi mode, default mode;
                //                    .origin(ArrayList < String >) // original select data set, used width #.multi()
                .start(this, REQUEST_IMAGE);
    }

    //进行动态权限获取
    private void doRequestPermissions() {
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //这里PERMISSION_GRANTED表示具有权限，PERMISSION_DENIED表示无权限
                //具有拍照权限和读取存储卡权限，直接进入相册
                //具体调用代码
                takePhoto();
            } else {
                /*
                不具有拍照权限，需要进行权限申请
                在判断应用没有相关权限的后，我们通过requestPermissions进行权限申请，
                这里的 String[] permissions是个字符串数组，可以对多个权限进行申请
                REQUEST_PERMISSION_CAMERA_CODE是个标识码，类似Intent跳转的REQUEST_CODE的，
                然后我们就可以在onRequestPermissionsResult进行权限申请的回调处理
                 */
                ActivityCompat.requestPermissions(PhotoViewActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_CAMERA_CODE);
            }
        } else {
            //当前系统小于6.0，直接调用拍照
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //拍照
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            if (grantResults.length >= 1) {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    //具有拍照权限，调用相机
                    takePhoto();
                } else {
                    //不具有相关权限，给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限开启
                    //                    tishi("请去系统设置-应用管理里面把相机权限开启");
                    tishi("可以到系统设置-应用管理里打开被禁止的权限");
                }
            }

        }
    }

    private void tishi(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void doSave() {
        //要判断是否从图库里面返回来了图片
        if (null == mPicPath || TextUtils.isEmpty(mPicPath)) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        showProgressDialog(null, "正在保存...");//最底层封装的
        //将一个view转换成bitmap格式，然后再将bitmap格式转成file格式保存到手机SD卡根目录下
        saveBitmap(convertViewToBitmap(rl_container));

        //这样的话就只要从手机SD卡根目录下找到我们保存的图片并上传到服务器即可
        final String url = UrlConfig.QingjianHost+"/qingjian/user/template/save";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("type", 1 + "")
                .addParams("userTempleteId", userTempleteId + "")
                .addParams("userPageId", userPageId + "")
                .addParams("componentId", componentId + "")
                //在将view转换成bitmap格式然后保存到本地的时候命名的就是这个名字  jianhun.PNG
                .addFile("file", "myphoto", new File(Environment.getExternalStorageDirectory(), "jianhun.PNG"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AppAndroid.getContext(), "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                        System.out.println("保存图片返回的结果::" + response);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        //上传进度展示，progress是0到1，一直在变化。total是上传的文件总字节数（大小），是固定的
                        System.out.println("进度：：progress:" + progress + "   total:" + total);
                    }
                });


        /*//如果位置发生了改变就要用这个接口保存从图库返回来的图片的位置等信息
        final String url2 = "http://101.201.209.200:1661/qingjian/action/qingjian/user/template/savetext";
        ArrayList<CardSaveBean.DatasEntity> datasEntities = new ArrayList<>();
        MyCard.DataEntity.PagesEntity.CommpentsEntity commpentsEntity
                = new MyCard.DataEntity.PagesEntity.CommpentsEntity();
        commpentsEntity.setX(100);
        commpentsEntity.setY(100);
        commpentsEntity.setWidth(100);
        commpentsEntity.setHeight(100);
        System.out.println("commpentsEntity111:::"+commpentsEntity.toString());
        datasEntities.add(new CardSaveBean.DatasEntity(componentId, new Gson().toJson(commpentsEntity)));
        System.out.println("commpentsEntity222:::"+new Gson().toJson(commpentsEntity));

        OkHttpUtils
                .post()
                .url(url2)
                .addParams("userId", AppAndroid.getUid() + "")
                .addParams("type", 1 + "")
                .addParams("userTempleteId", userTempleteId + "")
                .addParams("userPageId", userPageId + "")
                .addParams("datas", new Gson().toJson(datasEntities))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("上传出错信息是：" + e.getMessage());
                        Toast.makeText(AppAndroid.getContext(), "加载出错，请检查网络或重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson2(response);
                        System.out.println("保存图片位置返回的结果::" + response);
                    }
                });
*/
    }

    /*private void parseJson2(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                Toast.makeText(this, "保存图片位置成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "保存图片位置失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
            if (status == 1) {
                setResult(2, new Intent());
                dismissProgressDialog();//最底层封装的
                Toast.makeText(this, "保存图片成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "保存图片失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //将view转变成bitmap格式
    public static Bitmap convertViewToBitmap(View view) {
        view.destroyDrawingCache();
        /*
        下面这几行代码我注释了，是因为不注释的话就会测量view的大小、设置布局位置，
        那么rl_container在代码里设置的填充父窗体就会失效，也就是rl_container里面的view多大，那么
        rl_container就会变得多大
         */
        //        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        //                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }

    //这个也是将view转变成bitmap格式，跟上面那个一样
    public Bitmap viewToBitmap() {
        rl_container.setDrawingCacheEnabled(true);
        rl_container.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        rl_container.layout(0, 0, rl_container.getMeasuredWidth(), rl_container.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(rl_container.getDrawingCache());
        rl_container.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void saveBitmap(Bitmap bitmap) {
        FileOutputStream fos;
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                File file = new File(sdRoot, "jianhun.PNG");
                fos = new FileOutputStream(file);
            } else
                throw new Exception("创建文件失败!");

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////下面的都没用到//////////////////////////

    // 获取指定Activity的截屏，保存到png文件
    public static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);

        // 获取屏幕宽和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    // 获取指定Activity的截屏，保存到png文件
    public static Bitmap takeScreenShot2(Activity activity, int x, int y, int width, int height) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);

        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

}
