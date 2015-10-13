//*************************************************************************
//	创建日期:	2015-10-12
//	文件名称:	NativeHandle.cs
//  创建作者:	Rect 	
//	版权所有:	shadowkong.com
//	相关说明:	
//*************************************************************************


//-------------------------------------------------------------------------


using UnityEngine;
using System.Collections;
using System.Runtime.InteropServices;

public class NativeHandle 
{
    //-------------------------------------------------------------------------
    private  object m_NativeObj;
    //-------------------------------------------------------------------------
    public NativeHandle()
    {

#if UNITY_EDITOR
#elif UNITY_ANDROID
        AndroidJavaClass androidActivityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        m_NativeObj = androidActivityClass.GetStatic<AndroidJavaObject>("currentActivity");
#endif
    }
    //-------------------------------------------------------------------------
    /// <summary>
    /// 头像设置调用 !!Warnning!! : 图片文件统一保存在路径 Application.persistentDataPath (For Unity)中
    /// </summary>
    /// <param name="strObjectName">Unity回调对象名字</param>
    /// <param name="strFuncName">Unity回调挂在对象上的Mono函数名</param>
    /// <param name="strFileName">Unity传过来的 图片保存名字 (仅仅是文件名,并非全路径)</param>
    public void SettingAvaterFormMobile(string strObjectName, string strFuncName, string strFileName)
    {
        if (
            string.IsNullOrEmpty(strObjectName) ||
            string.IsNullOrEmpty(strFuncName) ||
            string.IsNullOrEmpty(strFileName)
            )
        {
            Debug.Log("NativeHandle::SettingAvaterFormMobile params is invalid");
            return;
        }

#if UNITY_EDITOR

        Debug.Log("NativeHandle::SettingAvaterFormMobile no handle at this Modlue");

#elif UNITY_ANDROID 
        
        {
            if (null == m_NativeObj)
            {
                return;
            }
            ((AndroidJavaObject)m_NativeObj).Call("SettingAvaterFormMobile", strObjectName, strFuncName, strFileName);
        }
        

#elif UNITY_IPHONE

        {
            SettingAvaterFormiOS(strObjectName, strFuncName, strFileName);
        }
#else

        Debug.Log("NativeHandle::SettingAvaterFormMobile no handle at this Modlue");

#endif

    }
    //-------------------------------------------------------------------------
    [DllImport("__Internal")]
    private static extern void SettingAvaterFormiOS(string strObjectName, string strFuncName, string strFileName);
    //-------------------------------------------------------------------------

}


            

			
			
			
            
             
			
			
			
			
			
			
			 
			
			