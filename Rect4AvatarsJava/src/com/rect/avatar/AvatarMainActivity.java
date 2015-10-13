package com.rect.avatar;

import com.unity3d.player.UnityPlayerActivity;

import android.content.Intent;
import android.os.Bundle;


public class AvatarMainActivity extends UnityPlayerActivity {
	
	// ----------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	// ----------------------------------------------------------------------
	/**
	 * 头像设置调用 
	 * TODO: 获取相册图片 - 打开相册拍照
	 * @param strObjectName Unity回调对象名字
	 * @param strFuncName Unity回调挂在对象上的Mono函数名
	 * @param strFileName Unity传过来的 图片保存名字 (仅仅是文件名,并非全路径)
	 * !!Warnning!! : 图片文件统一保存在路径 Application.persistentDataPath (For Unity)中
	 */
	public  void SettingAvaterFormMobile(
			String strObjectName,String strFuncName, String strFileName) 
	{
		if (strFileName.equals(null) || strObjectName.equals(null) || strFuncName.equals(null)) 
		{
			CommonUtil.Log("at func [SettingAvaterFormMobile],some string is null!");
			CommonUtil.Log("strObjectName = " + strObjectName);
			CommonUtil.Log("strFuncName = " + strFuncName);
			CommonUtil.Log("strFileName = " + strFileName);
			return;
		}

		Intent intent = new Intent(this, UserAvatarActivity.class);
		intent.putExtra(CommonUtil.ENUM_PARAM.eParam_ObjectName.toString(),strObjectName);
		intent.putExtra(CommonUtil.ENUM_PARAM.eParam_FuncName.toString(),strFuncName);
		intent.putExtra(CommonUtil.ENUM_PARAM.eParam_FileName.toString(),strFileName);
		this.startActivity(intent);
	}
	// ----------------------------------------------------------------------

}
