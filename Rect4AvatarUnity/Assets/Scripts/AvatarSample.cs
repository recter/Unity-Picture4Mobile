//*************************************************************************
//	创建日期:	2015-9-24
//	文件名称:	AvatarSample.cs
//  创建作者:	Rect 	
//	版权所有:	shadowkong.com
//	相关说明:	
//*************************************************************************


//-------------------------------------------------------------------------


using UnityEngine;
using System.Collections;

public class AvatarSample : MonoBehaviour
{

    //-------------------------------------------------------------------------
    private Texture m_texture;
    private string m_LogMessage;
    private NativeHandle m_Native;
    //-------------------------------------------------------------------------
    // Use this for initialization
    void Start()
    {
        m_Native = new NativeHandle();
        m_LogMessage = "";
    }
    //-------------------------------------------------------------------------
    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.Home))
        {
            Application.Quit();
        }
    }
    //-------------------------------------------------------------------------
    void OnGUI()
    {
        if (GUI.Button(new Rect(10, 10, 200, 50), "设置用户头像"))
        {
            m_LogMessage = "Check Unity Button";
            m_Native.SettingAvaterFormMobile("Main Camera", "OnAvaterCallBack", "image.png");
        }

        GUI.Label(new Rect(10, 100, 500, 500), m_LogMessage);

        if (null != m_texture)
        {
            GUI.DrawTexture(new Rect(100, 300, 300, 300), m_texture);
        }
    }
    //-------------------------------------------------------------------------
    void OnAvaterCallBack(string strResult)
    {
        m_LogMessage += " - " + strResult;

        if (strResult.Equals(ENUM_AVATAR_RESULT.eResult_Success.ToString()))
        {
            // 成功
            StartCoroutine(LoadTexture("image.png"));
        }
        else if (strResult.Equals(ENUM_AVATAR_RESULT.eResult_Cancel.ToString()))
        {
            // 取消
        }
        else if (strResult.Equals(ENUM_AVATAR_RESULT.eResult_Failed.ToString()))
        {
            // 失败
        }
    }
    //-------------------------------------------------------------------------
    IEnumerator LoadTexture(string name)
    {
        string path = "file://" + Application.persistentDataPath + "/" + name;
        using (WWW www = new WWW(path))
        {
            yield return www;

            if (www.error != null)
            {

            }
            else
            {
                if (www.isDone)
                {
                    //将图片赋予卡牌项
                    m_texture = www.texture;
                }
            }
        };
    }
    //-------------------------------------------------------------------------
}

    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    

        
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

