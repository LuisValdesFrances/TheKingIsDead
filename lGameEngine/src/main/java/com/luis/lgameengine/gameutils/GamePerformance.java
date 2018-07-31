package com.luis.lgameengine.gameutils;

public class GamePerformance {

	public static final int MAX_PFS = 60;
	public static final int MIN_FPS = 10;
	
	private static GamePerformance gamePerformance;
	
	public static GamePerformance getInstance(){
		if(gamePerformance == null){
			gamePerformance = new GamePerformance();
		}
		return gamePerformance;
	}
	
	public int getOptimalFrames() {
    	if (((Settings.getInstance().CPUMaxMhzs >= 1190 || Settings.getInstance().CPUMaxMhzs == -1)
                && android.os.Build.VERSION.SDK_INT > 16)
                //&& Settings.ms_iDeviceOS == Settings.OS_ANDROID
                //&& Settings.ms_iSize > Settings.HVGA_320X480
                //&& Settings.ms_iSize < Settings.WXGA_720X1280
                ) {
        	return 60;
        } else {
        	return 30;
        }
     }
	
	public float getFrameMult(float fps){
		return MAX_PFS / fps;
	}
}
