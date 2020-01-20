package com.luis.lgameengine.implementation.sound;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SndManager extends Activity {

    private static SndManager sndManager;

    public static SndManager getInstance() {
        if (sndManager == null) {
            sndManager = new SndManager();
        }
        return sndManager;
    }

    private boolean sound;

    private Context context;

    public static final byte FX_NOSOUND = 0;
    private SoundPool soundPool;

    public static final int MUSIC_NOSOUND = -1;
    private MediaPlayer mediaPlayer;
    private int currentClip;
    private int musicFileList[];
    private int streamID;

    public void inicialize(Context context, int[] musicFileList, int[] fxFileList) {

        try {
            this.context = context;
            this.musicFileList = musicFileList;
            this.sound = true;
            int numFX = fxFileList.length > 0 ? fxFileList.length : 0;
            this.soundPool = new SoundPool(numFX, AudioManager.STREAM_MUSIC, 0);

            for (int i = 0; i < numFX; i++) {
                soundPool.load(context, fxFileList[i], 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic(int _iMusicID, boolean _bLoop) {
        if (sound) {
            try {

                // if the player is already playing the song returns. if is
                // playing other song it stops
                if (mediaPlayer != null) {
                    if (currentClip == _iMusicID && mediaPlayer.isPlaying())
                        return;

                    stopMusic();
                }

                // create, volume and finally plays a wonderful song
                mediaPlayer = MediaPlayer.create(context, musicFileList[_iMusicID]);
                mediaPlayer.setLooping(_bLoop);
                mediaPlayer.setVolume(1f, 1f);
                /*
                 * float streamVolume =
                 * vAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                 * fVolume = streamVolume /
                 * vAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                 * vMusicPlayer.setVolume(fVolume, fVolume); if (fVolume ==
                 * 0.0f) fVolume = 1.0f;
                 */

                mediaPlayer.start();

                currentClip = _iMusicID;

            } catch (Exception e) {
                e.printStackTrace();
                currentClip = MUSIC_NOSOUND;
            }
        }
    }

    public int getMusicDuration() {
        if (sound) {
            if (mediaPlayer != null) {
                return mediaPlayer.getDuration();
            }
        }
        return -1;
    }

    public void pauseMusic() {
        try {
            if (mediaPlayer != null)
                mediaPlayer.pause();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void unpauseMusic() {
        try {
            if (mediaPlayer != null)
                mediaPlayer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopMusic() {

        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            currentClip = MUSIC_NOSOUND;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int playFX(int fxID, int loop) {
        streamID = 0;
        if (sound) {
            try {
                streamID = soundPool.play(fxID + 1, 1f, 1f, 1, loop, 1f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return streamID;
    }

    public void stopFX(int fxID) {
        if (sound) {
            try {
                soundPool.stop(fxID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseFX() {
        soundPool.autoPause();
    }

    public void flushSndManager() {
        try {
            soundPool.release();
            soundPool = null;
            mediaPlayer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentClip() {
        return currentClip;
    }

    public void setCurrentClip(int currentClip) {
        this.currentClip = currentClip;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }


}