package com.mhh.lib.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {
	
	public static FileOutputStream createFileOutputStream(String strPath) throws Exception{
		final File file = new File(strPath);
		try{
			return new FileOutputStream(file);
		}catch(Exception e){
			final File fileParent = file.getParentFile();
			if(!fileParent.exists()){
				if(fileParent.mkdirs()){
					return new FileOutputStream(file);
				}
			}
		}
		
		return null;
	}
	
	public static boolean isFileExists(String path){
		return new File(path).exists();
	}
	
	public static void checkOrCreateDirectory(String path){
		File file = new File(path);
		if(!file.exists()){
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
		}
	}
	
	public static void deleteFile(String strPath){
		File file = new File(strPath);
		file.delete();
	}
	
	public static void deleteFolder(String strPath){
		File file = new File(strPath);
		if(file.isDirectory()){
			File fileChilds[] = file.listFiles();
			if(fileChilds == null){
				file.delete();
			}else{
				final int nLength = fileChilds.length;
				if(nLength > 0){
					for(File fileChild : fileChilds){
						if(fileChild.isDirectory()){
							deleteFolder(fileChild.getAbsolutePath());
						}else{
							fileChild.delete();
						}
					}
					file.delete();
				}else{
					file.delete();
				}
			}
		}else{
			file.delete();
		}
	}
	
	public static void saveBitmapToFile(String pathDst,Bitmap bmp){
		saveBitmapToFile(pathDst, bmp, 80);
	}
	
	public static void saveBitmapToFile(String pathDst,Bitmap bmp,int quality){
		try {
			FileOutputStream fos = createFileOutputStream(pathDst);
			bmp.compress(CompressFormat.JPEG, quality, fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFile(String strPathDst,String strPathSrc){
		if(strPathDst != null && !strPathDst.equals(strPathSrc)){
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = createFileOutputStream(strPathDst);
				fis = new FileInputStream(strPathSrc);
				byte buf[] = new byte[1024];
				int nReadBytes = 0;
				while((nReadBytes = fis.read(buf, 0, buf.length)) != -1){
					fos.write(buf, 0, nReadBytes);
				}
				fos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(fos != null){
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fis != null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static String readFileToString(String strFilePath){
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(strFilePath),"GBK"));
			final StringBuffer sb = new StringBuffer();
			String strLine = null;
			while((strLine = br.readLine()) != null){
				sb.append(strLine);
			}
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static String getFileExt(String fileName,String def) {
		if(fileName == null){
			return def;
		}
		int pos = fileName.lastIndexOf(".");
		if(pos >= 0){
			return fileName.substring(pos + 1);
		}
		return def;
	}
	
	public static String base64Encode(String pic) {
		if (TextUtils.isEmpty(pic))
			return "";
		String path = pic.replace("file://", "");
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		// InputStream stream;
		try {
			// stream = new FileInputStream(path);
			// Bitmap bitmap = BitmapFactory.decodeStream(stream);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 90, outStream);
			byte[] buffer = outStream.toByteArray();
			return Base64.encodeToString(buffer, false);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
