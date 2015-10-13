//*************************************************************************
//	创建日期:	2015-9-25
//	文件名称:	UserAvatarController.h
//  创建作者:	Rect 	
//	版权所有:	Shadowkong.com
//	相关说明:	头像设置 拍照 - 打开相册
//*************************************************************************

#import <Foundation/Foundation.h>

#define RESULE_FAILED "eResult_Failed"     // 失败
#define RESULE_CAMERA "eResult_Camera"     // 打开照相机
#define RESULE_PICTURE "eResult_Picture"   // 打开相册
#define RESULE_CANCEL "eResult_Cancel"     // 取消
#define RESULE_SUCCESS "eResult_Success"   // 成功
#define RESULE_FINISH "eResult_Finish"     // 关闭原生界面
//-------------------------------------------------------------------------

@interface UserAvatarController : NSObject<UIApplicationDelegate,UIImagePickerControllerDelegate, UIActionSheetDelegate,UINavigationControllerDelegate>
{
    
	UIView*				_rootView;
	UIViewController*	_rootController;
@private
	id _popoverViewController;
}
@property (nonatomic, retain) id popoverViewController;
@property (nonatomic)  NSString* m_pstrObjectName;
@property (nonatomic)  NSString* m_pstrFuncName;
@property (nonatomic)  NSString* m_pstrFileName;
//-------------------------------------------------------------------------
- (void)SetUnityObjectName:(const char *)pstr;
- (void)SetUnityFuncName:(const char*)pstr;
- (void)SetUnityFileName:(const char*)pstr;
//-------------------------------------------------------------------------
@end

