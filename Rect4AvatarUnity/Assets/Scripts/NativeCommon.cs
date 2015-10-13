//*************************************************************************
//	创建日期:	2015-9-24
//	文件名称:	NativeCommon.cs
//  创建作者:	Rect 	
//	版权所有:	shadowkong.com
//	相关说明:	
//*************************************************************************


//-------------------------------------------------------------------------


using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

/// <summary>
///  获取用户头像状态信息
/// </summary>
public enum ENUM_AVATAR_RESULT
{
    eResult_Failed,     // 失败
    eResult_Camera,     // 打开照相机  
    eResult_Picture,    // 打开相册
    eResult_Cancel,     // 取消
    eResult_Success,    // 成功
    eResult_Finish      // 关闭原生界面
}

class NativeCommon
{
}
