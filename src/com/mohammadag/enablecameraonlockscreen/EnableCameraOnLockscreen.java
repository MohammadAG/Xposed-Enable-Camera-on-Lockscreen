package com.mohammadag.enablecameraonlockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class EnableCameraOnLockscreen implements IXposedHookLoadPackage {

	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		
		if (!lpparam.packageName.equals("com.sec.android.app.camera"))
			return;
		
		XposedHelpers.findAndHookMethod("com.sec.android.app.camera.AbstractCameraActivity",
				lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
			
    		@Override
    		protected void afterHookedMethod(MethodHookParam param) throws Throwable {
    			Activity activity = (Activity) param.thisObject;
    			Window window = activity.getWindow();
    			window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
    				    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
    				    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
    				    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON); 
    		}
		});
	}

}
