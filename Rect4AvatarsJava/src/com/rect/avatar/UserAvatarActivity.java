//*************************************************************************
//	创建日期:	2015-9-23
//	文件名称:	UserAvatarActivity.java
//  创建作者:	Rect 	
//	版权所有:	shadowkong.com
//	相关说明:	
//*************************************************************************
package com.rect.avatar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.unity3d.player.UnityPlayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserAvatarActivity extends Activity 
{
	
	// ----------------------------------------------------------------------
	private static final String IMAGE_UNSPECIFIED = "image/*";  
	// ----------------------------------------------------------------------
	// 局部变量 - 本类自定义
	private Button m_btnOpenCamera;
	private int m_OpenCameraID;
	private Button m_btnGetPicture;
	private int m_GetPictureID;
	private Button m_btnCancel;
	private int m_CancelID;
	private InternalButtonListener m_linsterForButton;
	private Activity m_contextAct;
	// 局部变量 - Unity传入
	private String m_ImageFileName;
	private String m_UnityObjectName;
	private String m_UnityFuncName;
	// ----------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		try 
		{
			String resName = "useravatar";
			int resId = R.layout.class.getDeclaredField(resName).getInt(R.layout.class);
			setContentView(resId);
		} 
		catch (IllegalAccessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NoSuchFieldException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m_contextAct = this;
		
		__InitButton();
		__InitParamFormUnity();
		
	}
	// ----------------------------------------------------------------------
	/**
	 * 初始化按钮事件
	 */
	private void __InitButton()
	{
		
	    try 
	    {
	    	String resName = "btnOpenCamrea";
	    	m_OpenCameraID = R.id.class.getDeclaredField(resName).getInt(R.id.class);
			m_btnOpenCamera = (Button) findViewById(m_OpenCameraID);
			resName = "btnGetPicture";
			m_GetPictureID = R.id.class.getDeclaredField(resName).getInt(R.id.class);
			m_btnGetPicture = (Button) findViewById(m_GetPictureID);
			resName = "btnCancel";
			m_CancelID = R.id.class.getDeclaredField(resName).getInt(R.id.class);
			m_btnCancel = (Button) findViewById(m_CancelID);
		} 
	    catch (IllegalAccessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    catch (IllegalArgumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    catch (NoSuchFieldException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    if(null == m_btnOpenCamera || null == m_btnGetPicture || null == m_btnCancel)
	    {
	    	CommonUtil.Log("__InitButton button is null");
	    	UnityPlayer.UnitySendMessage(
					m_UnityObjectName,m_UnityFuncName,CommonUtil.ENUM_RESULT.eResult_Failed.toString());
	    	finish();
	    	return;
	    }
	    
	    m_linsterForButton = new InternalButtonListener();
		m_btnOpenCamera.setOnClickListener(m_linsterForButton);
		m_btnGetPicture.setOnClickListener(m_linsterForButton);
		m_btnCancel.setOnClickListener(m_linsterForButton);
	}
	// ----------------------------------------------------------------------
	/**
	 * 更新Unity传过来的参数
	 */
	private void __InitParamFormUnity()
	{
		m_ImageFileName = getIntent().getStringExtra(CommonUtil.ENUM_PARAM.eParam_FileName.toString());
		m_UnityObjectName = getIntent().getStringExtra(CommonUtil.ENUM_PARAM.eParam_ObjectName.toString());
		m_UnityFuncName = getIntent().getStringExtra(CommonUtil.ENUM_PARAM.eParam_FuncName.toString());
	}
	// ----------------------------------------------------------------------
	/**
	 * Activity 回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// 取消哨兵
		Boolean bIsPhotoHandle = false;
		
		// 拍照缩放图片  
		if(requestCode == CommonUtil.ENUM_RESULT.eResult_Camera.ordinal())
		{
			if(0 == resultCode)
			{
				bIsPhotoHandle = true;
			}
			else
			{
				File picture = new File(Environment.getExternalStorageDirectory() + "/" + m_ImageFileName);  
				__PictureScaleHandle(Uri.fromFile(picture));
			}
			
		}
		
		// 相册缩放图片  
        if (requestCode == CommonUtil.ENUM_RESULT.eResult_Picture.ordinal()) 
        {  
        	if(null == data)
        	{
        		bIsPhotoHandle = true;
        	}
        	else
        	{
        		__PictureScaleHandle(data.getData());  
        	}
        } 
        
        if(bIsPhotoHandle)
        {
        	UnityPlayer.UnitySendMessage(
					m_UnityObjectName,m_UnityFuncName,CommonUtil.ENUM_RESULT.eResult_Failed.toString());
        	finish();
        	return;
        }
        
        // 保存头像图片  
        if (requestCode == CommonUtil.ENUM_RESULT.eResult_Success.ordinal()) 
        {  
        	Bundle extras = data.getExtras();  
            if (null != extras) 
            {  

                Bitmap photo = extras.getParcelable("data");
            	try 
            	{
            		__SavePicture(photo);
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
            	
            	UnityPlayer.UnitySendMessage(
    					m_UnityObjectName,m_UnityFuncName,CommonUtil.ENUM_RESULT.eResult_Success.toString());
            	finish();
            }  
        } 
            
		super.onActivityResult(requestCode, resultCode, data);
	}
	// ----------------------------------------------------------------------大
	/**
	 * 裁剪
	 * @param uri
	 */
	private void __PictureScaleHandle(Uri uri)
	{
		Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);  
        intent.putExtra("crop", "true");  
        // aspectX aspectY 是宽高的比例  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        // outputX outputY 是裁剪图片宽高  
        intent.putExtra("outputX", 300);  
        intent.putExtra("outputY", 300);  
        intent.putExtra("return-data", true);  
        startActivityForResult(intent, CommonUtil.ENUM_RESULT.eResult_Success.ordinal());
	}
	// ----------------------------------------------------------------------
	/**
	 * 保存图片
	 * @param photo
	 * @throws IOException
	 */
	@SuppressLint("SdCardPath") 
	private void __SavePicture(Bitmap photo) throws IOException 
	{
		
		FileOutputStream fOut = null;
		try
		{
			String strPackgeName = getApplicationInfo().packageName;
			String path = "/mnt/sdcard/Android/data/" + strPackgeName + "/files";
			File destDir = new File(path);
			if (!destDir.exists()) 
			{
				destDir.mkdirs();
			}
			
			fOut = new FileOutputStream(path + "/" + m_ImageFileName) ;
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		//将Bitmap对象写入本地路径中，Unity在去相同的路径来读取这个文件
		photo.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		
		try 
		{
			fOut.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			fOut.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	// ----------------------------------------------------------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			UnityPlayer.UnitySendMessage(
					m_UnityObjectName,m_UnityFuncName,CommonUtil.ENUM_RESULT.eResult_Finish.toString());
		}
		
		return super.onKeyDown(keyCode, event);
	}
	// ----------------------------------------------------------------------
	/**
	 * 按钮注册事件回调 - 内部类
	 * @author Rect
	 *
	 */
	class InternalButtonListener implements OnClickListener 
	{

		@Override
		public void onClick(View v) 
		{
			
			CommonUtil.ENUM_RESULT eResult = CommonUtil.ENUM_RESULT.eResult_Failed;
			
			if(v.getId() == m_OpenCameraID)
			{
				eResult = CommonUtil.ENUM_RESULT.eResult_Camera;
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
				intent.putExtra(
            		MediaStore.EXTRA_OUTPUT, 
            		Uri.fromFile(new File(Environment.getExternalStorageDirectory(), m_ImageFileName))); 
				m_contextAct.startActivityForResult(intent, eResult.ordinal());
			}
			else if(v.getId() == m_GetPictureID)
			{
				eResult = CommonUtil.ENUM_RESULT.eResult_Picture;
				Intent intent = new Intent(Intent.ACTION_PICK, null);  
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);  
				m_contextAct.startActivityForResult(intent, eResult.ordinal());
			}
			else if(v.getId() == m_CancelID)
			{
				eResult = CommonUtil.ENUM_RESULT.eResult_Cancel;
				m_contextAct.finish();
			}
			else
			{
				m_contextAct.finish();
			}
			
			// 通知Unity层 用户点击了那个按钮
			UnityPlayer.UnitySendMessage(m_UnityObjectName,m_UnityFuncName,eResult.toString());
		}

	}
	// ----------------------------------------------------------------------
}

















