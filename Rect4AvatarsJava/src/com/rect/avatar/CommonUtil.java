//*************************************************************************
//	创建日期:	2015-9-23
//	文件名称:	CommonUtil.java
//  创建作者:	Rect 	
//	版权所有:	Shadowkong.com
//	相关说明:	
//*************************************************************************
package com.rect.avatar;

import android.util.Log;

public class CommonUtil 
{
	// ----------------------------------------------------------------------
	// 参数类型
	public enum ENUM_PARAM 
	{
		eParam_ObjectName, 
		eParam_FuncName, 
		eParam_FileName
	}

	// ----------------------------------------------------------------------
	// 返回消息类型 
	public enum ENUM_RESULT 
	{
		eResult_Failed, 
		eResult_Camera, 
		eResult_Picture, 
		eResult_Cancel, 
		eResult_Success, 
		eResult_Finish
	}

	// ----------------------------------------------------------------------
	private static String m_strLogTag = "Rect4Avatar";
	// ----------------------------------------------------------------------
	public static void Log(String strLog) 
	{
		Log.e(m_strLogTag, strLog);
	}
}
