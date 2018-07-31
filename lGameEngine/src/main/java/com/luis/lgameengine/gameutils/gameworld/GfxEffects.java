package com.luis.lgameengine.gameutils.gameworld;

import java.util.Random;

/**
 *
 * @author G-TEAM
 */
public class GfxEffects {
	
	private static GfxEffects instance;
	public static GfxEffects getInstance(){
		if(instance == null){
			instance = new GfxEffects();
		}
		return instance;
	}
	
	private float tremorX;
	private float tremorY;
	private float currentDuration;
	private float totalDuration;
	private float distance;
	public void createTremor(float totalDuration, float distance){
		this.totalDuration = totalDuration;
		this.distance = distance;
		this.currentDuration = 0;
	}
	
	public void update(float deltaTime){
		//Tremor
		if(currentDuration < totalDuration){
			currentDuration+= deltaTime;
			float modDur = 1f-(currentDuration/totalDuration);
			
			float d = distance*modDur;
			tremorX = getRandom((int)-d, (int)d);
			tremorY = getRandom((int)-d, (int)d);
		}else{
			tremorX = 0;
			tremorY = 0;
		}
	}

	public float getTremorX() {
		return tremorX;
	}

	public float getTremorY() {
		return tremorY;
	}
	
	public boolean isModule(int number) {
        if ((number % 2) == 1) {
            return false;//impar
        } else {
            return true;//par
        }
    }
	
	//Resources:
    private Random vRandom;// = new Random(0);
    //Obtiene un randon entre el primer parametro(Numero menor) y el segundo parametro(numero mayor). 
    //Ambos incluidos.
    public int getRandom(int _i0, int _i1) {
        if(vRandom == null) vRandom = new Random();
        return _i0 + Math.abs(vRandom.nextInt() % (1 + _i1 - _i0));
    }
    
	public int getRandom(int _iNumber) {
        if(vRandom == null) vRandom = new Random();
        if (_iNumber < 0) {
            return (vRandom.nextInt() % -_iNumber);
        }
        try {
            return Math.abs(vRandom.nextInt()) % _iNumber;
        } catch (Exception e) {
            e.printStackTrace();
           return 0;
        }
    }
	
//    public static void drawGradientBG(Graphics _g, int _iColor1, int _iColor2, int _iX, int _iY, int _iWidth, int _iHeight, int _iSteps) {
//
//        int stepSize = _iHeight / _iSteps;
//
//        int _iColor1RGB[] = new int[]{(_iColor1 >> 16) & 0xff, (_iColor1 >> 8) & 0xff, _iColor1 & 0xff};
//        int _iColor2RGB[] = new int[]{(_iColor2 >> 16) & 0xff, (_iColor2 >> 8) & 0xff, _iColor2 & 0xff};
//
//        int colorCalc[] = new int[]{
//            ((_iColor2RGB[0] - _iColor1RGB[0]) << 16) / _iSteps,
//            ((_iColor2RGB[1] - _iColor1RGB[1]) << 16) / _iSteps,
//            ((_iColor2RGB[2] - _iColor1RGB[2]) << 16) / _iSteps
//        };
//
//        for (int i = 0; i < _iSteps; i++) {
//            _g.setColor(_iColor1RGB[0] + ((i * colorCalc[0] >> 16)) << 16
//                    | _iColor1RGB[1] + ((i * colorCalc[1] >> 16)) << 8
//                    | _iColor1RGB[2] + ((i * colorCalc[2] >> 16)));
//            if (i != _iSteps - 1) {
//                _g.fillRect(_iX, _iY + i * stepSize, _iWidth, stepSize);
//            } else {
//                _g.fillRect(_iX, _iY + i * stepSize, _iWidth, stepSize + 30); //+20 corrects presicion los due to divisions
//            }
//        }
//    }
//
//    public static final int PRECISION_BITS = 8;
//    //<editor-fold defaultstate="collapsed" desc="EXPLOSION PARTICLES">
//    //Sistema de particulas by Luis:
//    public static final int TOTAL_EXP_CAPACITY = 96;
//    public static final int NUMBER_EXP_PARTICLES = 16;
//    private static int[][][] iExpParticleData;
//    private static final int POS_TYPE = 0;//SMALL,NORMAL,BIG
//    private static final int POS_EXP_X = 1;
//    private static final int POS_EXP_Y = 2;
//    private static final int POS_EXP_ANGLE = 3;
//    private static final int POS_EXP_MAX_SPEED = 4;
//    private static final int POS_EXP_CURRENT_SPEED = 5;
//    private static final int POS_EXP_FRAME = 6;
//    private static final int POS_EXP_ALPHA_DECREMENT = 7;
//    private static final int POS_EXP_TOTAL_COUNT = 8;
//    private static final int POS_EXP_CURRENT_COUNT = 9;
//    private static final int POS_EXP_ALPHA = 10;
//
//    public static final int TYPE_SMALL=0;
//    public static final int TYPE_MEDIUM=1;
//    public static final int TYPE_BIG=2;
//
//    public static final int COL_CENTER = 0;
//    public static final int COL_LEFT = 1;
//    public static final int COL_RIGHT = 2;
//    public static final int COL_UP = 3;
//    public static final int COL_DOWN = 4;
//    public static final int COL_CHAOTIC = -1;
//    public static final int NUMBER_FRAMES = 6;
//
//
//
//    public static void createExplosionParticles(
//            int _iType,
//            int _iPosX,
//            int _iPosY,
//            int _iSpeed,//Pixeles de desplazamiento por segundo
//            int _iTypeExp,//La por el que se produce la colisión. COL_CENTER estalla desde el centro
//            boolean _isSymetricSpeed) {//Si es true, las particulas se reparten en la misma cantidad a los largo de la descripción del ángulo.
//
//        if (iExpParticleData == null) {
//            iExpParticleData = new int[TOTAL_EXP_CAPACITY][NUMBER_EXP_PARTICLES][POS_EXP_ALPHA + 1];
//        }
//        //Buscamos un weco vacio:
//        for (int i = 0; i < iExpParticleData.length; i++) {
//            if (iExpParticleData[i][0][POS_EXP_ALPHA] <= 0) {
//
//                for (int j = 0; j < NUMBER_EXP_PARTICLES; j++) {
//                    iExpParticleData[i][j][POS_TYPE] = _iType;
//                    iExpParticleData[i][j][POS_EXP_X] = _iPosX << PRECISION_BITS;
//                    iExpParticleData[i][j][POS_EXP_Y] = _iPosY << PRECISION_BITS;
//
//                    switch (_iTypeExp) {
//                        case COL_RIGHT:
//                            if (Main.isModule(j)) {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(0, 90);
//                            } else {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(270, 360);
//                            }
//                            break;
//                        case COL_UP:
//                            if (Main.isModule(j)) {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(0, 90);
//                            } else {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(90, 180);
//                            }
//                            break;
//                        case COL_LEFT:
//                            if (Main.isModule(j)) {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(90, 180);
//                            } else {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(180, 270);
//                            }
//                            break;
//                        case COL_DOWN:
//                            if (Main.isModule(j)) {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(180, 270);
//                            } else {
//                                iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(270, 320);
//                            }
//                            break;
//                        case COL_CENTER:
//                            iExpParticleData[i][j][POS_EXP_ANGLE] = ((360 / NUMBER_EXP_PARTICLES) * (j + 1)) % 360;
//                            break;
//                        case COL_CHAOTIC:
//                            iExpParticleData[i][j][POS_EXP_ANGLE] = Main.getRandom(360);
//                            break;
//                    }
//                    iExpParticleData[i][j][POS_EXP_MAX_SPEED] = _iSpeed;
//                            //_isSymetricSpeed? _iSpeed : (((NUMBER_PARTICLES-j))*(_iSpeed))/(NUMBER_PARTICLES);
//                    iExpParticleData[i][j][POS_EXP_CURRENT_SPEED] = iExpParticleData[i][j][POS_EXP_MAX_SPEED];
//                    iExpParticleData[i][j][POS_EXP_FRAME] = 0;
//                    iExpParticleData[i][j][POS_EXP_ALPHA_DECREMENT] = (6+j)*Define.FRAME_SPEED_DEC;
//                    iExpParticleData[i][j][POS_EXP_CURRENT_COUNT] = 0;
//                    iExpParticleData[i][j][POS_EXP_TOTAL_COUNT] = 120;//(6/Define.FRAME_SPEED>0?6/Define.FRAME_SPEED:1)*j;
//                    iExpParticleData[i][j][POS_EXP_ALPHA] = 255;
//                }
//                i = iExpParticleData.length;
//            }
//        }
//    }
//    public static void runExplosionParticles(long _lDeltaTime) {
//        if (iExpParticleData != null) {
//            long incrementX, incrementY;
//            for (int i = 0; i < iExpParticleData.length; i++) {
//
//                for (int j = 0; j < iExpParticleData[i].length; j++) {
//                    if (iExpParticleData[i][j][POS_EXP_ALPHA] > 0) {
//                        if(iExpParticleData[i][j][POS_EXP_CURRENT_COUNT]>=iExpParticleData[i][j][POS_EXP_TOTAL_COUNT]){
//                            int seno = Math2D.sin(iExpParticleData[i][j][POS_EXP_ANGLE]);//SINCOS[convertAngleToByte(iExpParticleData[i][j][POS_EXP_ANGLE])];
//                            int coseno = Math2D.cos(iExpParticleData[i][j][POS_EXP_ANGLE]);//SINCOS[convertAngleToByte(iExpParticleData[i][j][POS_EXP_ANGLE] + 90)];
//                            long spd = (((_lDeltaTime * iExpParticleData[i][j][POS_EXP_CURRENT_SPEED]) / 1000) << PRECISION_BITS);
//                            incrementX = ((coseno * spd) / (1 << Math2D.PRECISION_BITS));
//                            incrementY = ((seno * spd) / (1 << Math2D.PRECISION_BITS));
//                            iExpParticleData[i][j][POS_EXP_X] += (incrementX);
//                            iExpParticleData[i][j][POS_EXP_Y] += (incrementY);
//                            iExpParticleData[i][j][POS_EXP_FRAME] = (NUMBER_FRAMES-1) - ((iExpParticleData[i][j][POS_EXP_ALPHA] * (NUMBER_FRAMES)) >> 8);//NUMBER_FRAMES - (iExpParticleData[i][j][POS_EXP_ALPHA] * (NUMBER_FRAMES)) / 256;
//                            iExpParticleData[i][j][POS_EXP_ALPHA] -= iExpParticleData[i][j][POS_EXP_ALPHA_DECREMENT];
//                            iExpParticleData[i][j][POS_EXP_CURRENT_SPEED] = ((iExpParticleData[i][j][POS_EXP_MAX_SPEED] * iExpParticleData[i][j][POS_EXP_ALPHA]) / 255);
//                        }else{
//                            iExpParticleData[i][j][POS_EXP_CURRENT_COUNT]+=(int)Main.getDeltaMilis();//++;;
//                        }
//                    }
//                }
//            }
//        }
//    }
//    public static void drawExplosionParticles(Graphics _g, Image[] _vImgParticles) {
//
//        if (iExpParticleData != null) {
//            for (int i = 0; i < iExpParticleData.length; i++) {
//                for (int j = 0; j < iExpParticleData[i].length; j++) {
//
//                    if (iExpParticleData[i][j][POS_EXP_ALPHA] > 0 && iExpParticleData[i][j][POS_EXP_CURRENT_COUNT]>=iExpParticleData[i][j][POS_EXP_TOTAL_COUNT]) {
//                        //g.setAlpha(iExpParticleData[i][j][POS_EXP_ALPHA]);
//                        _g.setClip((iExpParticleData[i][j][POS_EXP_X] >> PRECISION_BITS) - ((_vImgParticles[iExpParticleData[i][j][POS_TYPE]].getWidth() / NUMBER_FRAMES) >> 1),
//                                (iExpParticleData[i][j][POS_EXP_Y] >> PRECISION_BITS) - ((_vImgParticles[iExpParticleData[i][j][POS_TYPE]].getWidth() / NUMBER_FRAMES) >> 1),
//                                (_vImgParticles[iExpParticleData[i][j][POS_TYPE]].getWidth() / NUMBER_FRAMES), _vImgParticles[iExpParticleData[i][j][POS_TYPE]].getHeight());
//                        _g.drawImage(_vImgParticles[iExpParticleData[i][j][POS_TYPE]],
//                                ((iExpParticleData[i][j][POS_EXP_X] >> PRECISION_BITS) -
//                                ((_vImgParticles[iExpParticleData[i][j][POS_TYPE]].getWidth() / NUMBER_FRAMES) * iExpParticleData[i][j][POS_EXP_FRAME])) - ((_vImgParticles[iExpParticleData[i][j][POS_TYPE]].getWidth() / NUMBER_FRAMES) >> 1),
//                                (iExpParticleData[i][j][POS_EXP_Y] >> PRECISION_BITS) - ((_vImgParticles[iExpParticleData[i][j][POS_TYPE]].getWidth() / NUMBER_FRAMES) >> 1), 0);
//                    }
//                }
//            }
//            _g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
//            //g.setAlpha(255);
//        }
//    }
//
//    public static boolean isExplosionPartRunning() {
//        if (iExpParticleData == null) {
//            return false;
//        } else {
//            for (int i = 0; i < iExpParticleData.length; i++) {
//                for (int j = 0; j < NUMBER_EXP_PARTICLES; j++) {
//                    if (iExpParticleData[i][j][POS_EXP_ALPHA] > 0) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
//    //</editor-fold>
//
//    public static Image getImageColor(
//            Image image,
//            int _iPosX, int _iPosY, int _iWidth, int _iHeight,
//            int _iRed, int _iGreen, int _iBlue) {
//        try {
//            // create array to hold the image data
//            int[] rgbData = new int[image.getWidth() * image.getHeight()];
//            // Obtains ARGB pixel data from the specified region of this image and stores
//            // it in the provided array of integers
//            image.getRGB(rgbData, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
//            int pixel;
//            int alpha;
//            int red;
//            int green;
//            int blue;
//            // loop through all pixels and ajust colors
//            for (int i = 0; i < rgbData.length; i++) {
//                pixel = rgbData[i];
//                alpha = (pixel & 0xff000000) >> 24;
//                _iRed = _iRed > 255 ? 255 : _iRed;
//                _iGreen = _iGreen > 255 ? 255 : _iGreen;
//                _iBlue = _iBlue > 255 ? 255 : _iBlue;
//                green = _iGreen;
//                red = _iRed;
//                blue = _iBlue;
//
//                // put the colors back into the ARGB format
//                rgbData[i] = (alpha << 24) + (red << 16) + (green << 8) + blue;
//            }
//            return Image.createRGBImage(rgbData, image.getWidth(), image.getHeight(), true);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//public static final int ADDITIVE = 0;
//public static final int SUBTRACTIVE = 1;
//public static final int NORMAL = 2;
//
//private static int[] blendImage(int[] rgbData, int _iColor, int _iMode) {
//    try {
//        int pixel;
//        int arrA, arrR, arrG, arrB;
//        int colA, colR, colG, colB;
//        int destA = 0, destR = 0, destG = 0, destB = 0;
//        int red, green, blue;
//
//        colA = (_iColor >> 24) & 0xFF;
//        colR = (_iColor >> 16) & 0xFF;
//        colG = (_iColor >> 8) & 0xFF;
//        colB = _iColor & 0xFF;
//
//        float alpha = colA / 255f;
//        // loop through all pixels and ajust colors
//        for (int i = 0; i < rgbData.length; i++) {
//            pixel = rgbData[i];
//            arrA = (pixel >> 24) & 0xFF;
//            arrR = (pixel >> 16) & 0xFF;
//            arrG = (pixel >> 8) & 0xFF;
//            arrB = pixel & 0xFF;
//            switch (_iMode) {
//            case ADDITIVE:
//                red = (int) (colR * alpha) + arrR;
//                green = (int) (colG * alpha) + arrG;
//                blue = (int) (colB * alpha) + arrB;
//                destA = arrA;
//                destR = red > 255 ? 255 : red;
//                destG = green > 255 ? 255 : green;
//                destB = blue > 255 ? 255 : blue;
//                break;
//            case SUBTRACTIVE:
//                red = (int) (colR * alpha) - arrR;
//                green = (int) (colG * alpha) - arrG;
//                blue = (int) (colB * alpha) - arrB;
//                destA = arrA;
//                destR = red < 0 ? 0 : red;
//                destG = green < 0 ? 0 : green;
//                destB = blue < 0 ? 0 : blue;
//                break;
//            case NORMAL:
//                destA = arrA;
//                destR = colR;
//                destG = colG;
//                destB = colB;
//                break;
//            }
//            // put the colors back into the ARGB format
//            rgbData[i] = (destA << 24) + (destR << 16) + (destG << 8) + destB;
//        }
//        return rgbData;
//    } catch (Exception e) {
//        return null;
//    }
//}
//
//    public static Image doThingsToMyImagePls(Image image, int _iPosX, int _iPosY, int _iWidth, int _iHeight, int _iColor, int _iMode) {
//        try {
//            // create array to hold the image data
//            int[] rgbData = new int[image.getWidth() * image.getHeight()];
//            // Obtains ARGB pixel data from the specified region of this image and stores
//            // it in the provided array of integers
//            image.getRGB(rgbData, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
//
//            return image.createRGBImage(blendImage(rgbData, _iColor, _iMode), image.getWidth(), image.getHeight(), true);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static Image doThingsToMyImagePls(Image image, int _iColor, int _iMode) {
//        try {
//            // create array to hold the image data
//            int[] rgbData = new int[image.getWidth() * image.getHeight()];
//            // Obtains ARGB pixel data from the specified region of this image and stores
//            // it in the provided array of integers
//            image.getRGB(rgbData, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
//
//            return image.createRGBImage(blendImage(rgbData, _iColor, _iMode), image.getWidth(), image.getHeight(), true);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//
//     /**
//     * Draws a image onto a graphics with a scaled size and variable alpha.
//     *
//     * @param g the Graphics onto it'll paint.
//     * @param source the source image to scale.
//     * @param scale the scale of the Image when it's being painted. 1 is original size, 0.5 the half, 2 the double.
//     * @param alpha the alpha of the Image when it's being painted. 1 is original alpha, 0.5 the half, 2 the double.
//     * @param gDestX the x coordinate of the anchor point in the destination drawing area.
//     * @param gDestY the y coordinate of the anchor point in the destination drawing area.
//     * @param anchor the anchor point for positioning the image.
//     * @throws NullPointerException - if src is null
//     * @throws IllegalArgumentException - if anchor is invalid
//     * @throws IllegalArgumentException - if src is the same image as the destination of this Graphics object
//     */
//
//    public static void drawScaledImage(Graphics g, Image source, float scale, float alpha, int gDestX, int gDestY, int anchor) {
//        int destW = (source.getWidth()  * (int) (scale * 256)) >> 8;
//        int destH = (source.getHeight() * (int) (scale * 256)) >> 8;
//
//        if (destW == 0 || destH == 0)  return;
//
//        // anchor
//        if ((anchor & Graphics.BOTTOM) != 0)
//            gDestY -= (source.getHeight() * (int) (scale * 256)) >> 8;
//        if ((anchor & Graphics.RIGHT) != 0)
//            gDestX -= (source.getWidth()  * (int) (scale * 256)) >> 8;
//        if ((anchor & Graphics.VCENTER) != 0)
//            gDestY -= (source.getHeight() * (int) (scale * 256)) >> 9;
//        if ((anchor & Graphics.HCENTER) != 0)
//            gDestX -= (source.getWidth()  * (int) (scale * 256)) >> 9;
//
//        int offsetX = 0, offsetY = 0;
//
//        try {
//            // create pixel arrays
//            int[] srcPixels = new int[source.getWidth()];
//            int[] destPixels = new int[destW]; // array to hold destination pixels
//
//            // precalculate src/dest ratios
//            int ratioW = (source.getWidth()  << 13) / destW;
//            int ratioH = (source.getHeight() << 13) / destH;
//
//            // fix the bug when we try to paint out of the canvas
//            if (gDestX < g.getClipX())
//                offsetX = g.getClipX() - gDestX;
//
//            if (gDestX + destW > g.getClipX() + g.getClipWidth())
//                destW -= (gDestX + destW) - (g.getClipX() + g.getClipWidth());
//
//            if (gDestY < g.getClipY())
//                offsetY = g.getClipY() - gDestY;
//
//            if (gDestY + destW > g.getClipY() + g.getClipHeight())
//                destH -= (gDestY + destH) - (g.getClipY() + g.getClipHeight());
//
//            int srcX, srcY;
//
//            if ((alpha * 256) > 254){
//                for (int destY = offsetY; destY < destH; ++destY) {
//                    srcY = (destY * ratioH) >> 13; // calculate beginning of sample
//                    source.getRGB(srcPixels, 0, source.getWidth(), 0, srcY + 0, source.getWidth(), 1);
//                    for (int destX = offsetX; destX < destW; ++destX) {
//                        srcX = (destX * ratioW) >> 13; // calculate beginning of sample
//                        //#if s60dp3 || s60dp3n73 || s60dp3e61
////#                         int iTransparentColor = 0;
////#                         if (srcPixels[srcX] == iTransparentColor)
////#                             destPixels[destX] = 0x00000000;
////#                         else
//                        //#endif
//                        destPixels[destX] = srcPixels[srcX];
//                    }
//                    g.drawRGB(destPixels, 0, destW, gDestX, gDestY + destY, destW, 1, true);
//                }
//            } else {
//                 int iAlpha = ((int) (alpha * 256) << 24);
//                 for (int destY = offsetY; destY < destH; ++destY) {
//                     srcY = (destY * ratioH) >> 13; // calculate beginning of sample
//                     source.getRGB(srcPixels, 0, source.getWidth(), 0, srcY, source.getWidth(), 1);
//                         for (int destX = offsetX; destX < destW; ++destX) {
//                             srcX = (destX * ratioW) >> 13; // calculate beginning of sample
//                            //#if s60dp3 || s60dp3n73 || s60dp3e61
////#                             int iTransparentColor = 0;
////#                             if (srcPixels[srcX] == iTransparentColor)
////#                                 destPixels[destX] = 0x00000000;
////#                             else
////#                                 destPixels[destX] = srcPixels[srcX] + iAlpha;
//                            //#else
//                             if (((srcPixels[srcX] >> 24) & 0xFF) == 0)
//                                 destPixels[destX] = srcPixels[srcX];
//                             else
//                                 destPixels[destX] = srcPixels[srcX] + iAlpha;
//                            //#endif
//                         }
//                     g.drawRGB(destPixels, 0, destW, gDestX, gDestY + destY, destW, 1, true);
//                 }
//             }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void drawRegionScaledImage(
//            Graphics g, Image _vImage, float scale, float alpha,
//            int _iImageX, int _iImageY, int _iWidth, int _iHeight,
//            int gDestX, int gDestY, int anchor) {
//
//        int destW = (_iWidth  * (int) (scale * 256)) >> 8;
//        int destH = (_iHeight * (int) (scale * 256)) >> 8;
//
//        if (destW == 0 || destH == 0
//                || _iImageX + _iWidth > Define.SIZEX
//                || _iImageY + _iHeight > Define.SIZEX
//                || _iImageX < 0
//                || _iImageY < 0)  return;
//
//        // anchor
//        if ((anchor & Graphics.BOTTOM) != 0)
//            gDestY -= (_iWidth * (int) (scale * 256)) >> 8;
//        if ((anchor & Graphics.RIGHT) != 0)
//            gDestX -= (_iWidth  * (int) (scale * 256)) >> 8;
//        if ((anchor & Graphics.VCENTER) != 0)
//            gDestY -= (_iHeight * (int) (scale * 256)) >> 9;
//        if ((anchor & Graphics.HCENTER) != 0)
//            gDestX -= (_iWidth * (int) (scale * 256)) >> 9;
//
//        int offsetX = 0, offsetY = 0;
//
//        try {
//            // create pixel arrays
//            int[] srcPixels = new int[_iWidth];
//            int[] destPixels = new int[destW]; // array to hold destination pixels
//
//            // precalculate src/dest ratios
//            int ratioW = (_iWidth  << 13) / destW;
//            int ratioH = (_iHeight << 13) / destH;
//
//            // fix the bug when we try to paint out of the canvas
//            if (gDestX < g.getClipX())
//                offsetX = g.getClipX() - gDestX;
//
//            if (gDestX + destW > g.getClipX() + g.getClipWidth())
//                destW -= (gDestX + destW) - (g.getClipX() + g.getClipWidth());
//
//            if (gDestY < g.getClipY())
//                offsetY = g.getClipY() - gDestY;
//
//            if (gDestY + destW > g.getClipY() + g.getClipHeight())
//                destH -= (gDestY + destH) - (g.getClipY() + g.getClipHeight());
//
//            int srcX, srcY;
//
//            if ((alpha * 256) > 254){
//                for (int destY = offsetY; destY < destH; ++destY) {
//                    srcY = (destY * ratioH) >> 13; // calculate beginning of sample
//
//                    _vImage.getRGB(srcPixels, 0, _iWidth,
//                            _iImageX, srcY + _iImageY, _iWidth, 1);
//
//                    for (int destX = offsetX; destX < destW; ++destX) {
//                        srcX = (destX * ratioW) >> 13; // calculate beginning of sample
//                        //#if s60dp3 || s60dp3n73 || s60dp3e61
////#                         int iTransparentColor = 0;
////#                         if (srcPixels[srcX] == iTransparentColor)
////#                             destPixels[destX] = 0x00000000;
////#                         else
//                        //#endif
//                        destPixels[destX] = srcPixels[srcX];
//                    }
//                    g.drawRGB(destPixels, 0, destW, gDestX, gDestY + destY, destW, 1, true);
//                }
//            } else {
//                 int iAlpha = ((int) (alpha * 256) << 24);
//                 for (int destY = offsetY; destY < destH; ++destY) {
//                     srcY = (destY * ratioH) >> 13; // calculate beginning of sample
//                     _vImage.getRGB(srcPixels, 0, _iWidth,
//                            _iImageX, srcY + _iImageY, _iWidth, 1);
//                         for (int destX = offsetX; destX < destW; ++destX) {
//                             srcX = (destX * ratioW) >> 13; // calculate beginning of sample
//                            //#if s60dp3 || s60dp3n73 || s60dp3e61
////#                             int iTransparentColor = 0;
////#                             if (srcPixels[srcX] == iTransparentColor)
////#                                 destPixels[destX] = 0x00000000;
////#                             else
////#                                 destPixels[destX] = srcPixels[srcX] + iAlpha;
//                            //#else
//                             if (((srcPixels[srcX] >> 24) & 0xFF) == 0)
//                                 destPixels[destX] = srcPixels[srcX];
//                             else
//                                 destPixels[destX] = srcPixels[srcX] + iAlpha;
//                            //#endif
//                         }
//                     g.drawRGB(destPixels, 0, destW, gDestX, gDestY + destY, destW, 1, true);
//                 }
//             }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static Image cloneImage(Image _vImage) {
//        try{
//            int width = _vImage.getWidth();
//            int height = _vImage.getHeight();
//
//            int[] infoRGB = new int[_vImage.getHeight()*_vImage.getWidth()];
//
//            _vImage.getRGB(infoRGB, 0, width, 0, 0, width, height);
//            Image img = Image.createRGBImage(infoRGB, width, height, true);
//
//            return img;
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//   
//    private static int COLOR_CONTROL= 0XFF05fd22;
//    public static Image getDistorsionedImage(Image _vImage, float _fDistUp, float _fDistDown){
//        //Obtengo las medidas maximas y minimas para operar:
//         float min = (_fDistDown <_fDistUp?_fDistDown:_fDistUp);
//         float max = (_fDistDown >_fDistUp?_fDistDown:_fDistUp);
//        
//        //Extraigo la info RGB de la imagen original:
//        int[] infoOriginalRGB = new int[_vImage.getHeight()*_vImage.getWidth()];
//        _vImage.getRGB(infoOriginalRGB, 0, _vImage.getWidth(), 0, 0, _vImage.getWidth(), _vImage.getHeight());
//
//        //Lo monto en un nuevo array de dos dimensiones donde la primera dimesion son las filas y la segunda las columnas.
//        int[][] infoOriginalRGB2 = new int[_vImage.getHeight()][_vImage.getWidth()];
//        int savefilePos=0;
//        for(int i = 0; i < infoOriginalRGB2.length;i++){
//           for(int j = 0; j < infoOriginalRGB2[0].length;j++){
//               infoOriginalRGB2[i][j]=infoOriginalRGB[savefilePos];
//               savefilePos++;
//            }
//        }
//        //System.out.println("Construido array original con: "+infoOriginalRGB2[0].length+ " pixeles de ancho y "+ infoOriginalRGB2.length + " pixeles de alto");
//
//       
//        //La nueva imagen a devolver tendra las dimensiones de la disorsion maxima.
//        float sizeNewArray = (float)infoOriginalRGB2[0].length*max;
//        int[][] infoTransRGB2 = new int[_vImage.getHeight()][(int)sizeNewArray];
//        //System.out.println("Construido nuevo array original con: "+infoTransRGB2[0].length+ " pixeles de ancho y "+ infoTransRGB2.length + " pixeles de alto");
//
//        //Relleno el nuevo array con un color por defecto.
//        for(int i = 0; i < infoTransRGB2.length; i++){
//            for(int j = 0; j < infoTransRGB2[i].length; j++){
//                infoTransRGB2[i][j]=COLOR_CONTROL;
//            }
//        }
//
//        //A partir del centro de la nueva imagen, y colocado en la posicion 0 del eje y, me desplazo un numero de pixeles hacia la izquierda 
//        //que dependera de las dimensiones de la nueva imagen. Maximo si la distorsion mayor es arriba (pos x=0) y minimo si la distorision mayor
//        //es arriba (pos x = centro_de_la_nueva_imagen/2 - minima_distorsion/2)
//        float posCenter;
//        posCenter = (infoTransRGB2[0].length/2)-((float)_vImage.getWidth()*min)/2;
//        
//        float[] positionDrawX = new float[_vImage.getHeight()];
//        for(int i = 0; i < infoOriginalRGB2.length; i++){
//            if(_fDistDown >_fDistUp)
//                positionDrawX[i]= posCenter - ((i*posCenter)/positionDrawX.length);
//            else
//                positionDrawX[i]=  ((i*(posCenter+1))/positionDrawX.length);
//        }
//        
//        float dif;
//        float repetions;
//        
//        //Pinto la imagen original en mi nuevo array grande CENTRO
//        for(int i = 0; i < infoOriginalRGB2.length; i++){
//            dif = max - min;
//            if(_fDistDown >_fDistUp){
//                repetions= min + ((i*dif)/(float)(_vImage.getHeight()-1)); 
//            }else{
//                repetions = min + ((((_vImage.getHeight()-1)-i)*dif)/(float)(_vImage.getHeight()-1)); 
//            }
//           
//            float fAcumulated=repetions;
//            int pos = (int)positionDrawX[i];
//            int lastIntPart=0;
//            int currentIntPart=0;
//            
//            //System.out.println("repetions " + i + " : "+repetions);
//            for(int j = 0; j < infoOriginalRGB2[i].length; j++){
//                //Centro el pintado:
//                try{
//                    if (currentIntPart >= lastIntPart) {
//                        
//                        int numberRepitions = currentIntPart > 0?(int)(currentIntPart - lastIntPart):(int)repetions;
//                        for (float k = 0; k < numberRepitions && pos < infoTransRGB2[i].length; k++) {
//                            infoTransRGB2[i][pos] = infoOriginalRGB2[i][j];
//                            pos++;
//                        }
//                    }
//                    lastIntPart = (int)fAcumulated;
//                    fAcumulated += repetions;
//                    currentIntPart = (int)fAcumulated;
//                    
//                }catch(Exception e){
//                    System.out.println("infoOriginalRGB2 con: "+infoOriginalRGB2[0].length+ " pixeles de ancho.");
//                    System.out.println("infoTransRGB2 con: "+infoTransRGB2[0].length+ " pixeles de ancho.");
//                    System.out.println("j: "+j);
//                    System.out.println("indexDraw: "+pos);
//                    System.out.println("repetions: "+repetions);
//                         return null;
//               }
//            }
//        }
//
//        /*
//        //Pinto la imagen original en mi nuevo array grande IZQUIERDA
//        for(int i = 0; i < infoOriginalRGB2.length; i++){
//            for(int j = 0; j < infoOriginalRGB2[i].length; j++){
//                infoTransRGB2[i][j]=infoOriginalRGB2[i][j];
//            }
//        }
//        * 
//        */
//
//        //Monto mi array de dos dimensiones en una sola:
//        int[] finalArray = new int[infoTransRGB2.length*infoTransRGB2[0].length];
//
//        int index=0;
//        for(int i = 0; i <infoTransRGB2.length; i++){
//            for(int j = 0; j < infoTransRGB2[i].length; j++){
//                finalArray[index]=infoTransRGB2[i][j];
//                index++;
//            }
//        }
//        Image img = Image.createRGBImage(finalArray, (int)sizeNewArray, _vImage.getHeight(), true);
//        return img;
//    }
//    
//    /*
//     * Igual que el anterior, pero distorisina en y tanto como en x:
//     */
//    public static Image getDistorsionedImageXY(Image _vImage, float _fDistUp, float _fDistDown){
//        //Obtengo las medidas maximas y minimas para operar:
//         float min = (_fDistDown <_fDistUp?_fDistDown:_fDistUp);
//         float max = (_fDistDown >_fDistUp?_fDistDown:_fDistUp);
//        
//        //Extraigo la info RGB de la imagen original:
//        int[] infoOriginalRGB = new int[_vImage.getHeight()*_vImage.getWidth()];
//        _vImage.getRGB(infoOriginalRGB, 0, _vImage.getWidth(), 0, 0, _vImage.getWidth(), _vImage.getHeight());
//
//        //Lo monto en un nuevo array de dos dimensiones donde la primera dimesion son las filas y la segunda las columnas.
//        int[][] infoOriginalRGB2 = new int[_vImage.getHeight()][_vImage.getWidth()];
//        int savefilePos=0;
//        for(int i = 0; i < infoOriginalRGB2.length;i++){
//           for(int j = 0; j < infoOriginalRGB2[0].length;j++){
//               infoOriginalRGB2[i][j]=infoOriginalRGB[savefilePos];
//               savefilePos++;
//            }
//        }
//        //System.out.println("Construido array original con: "+infoOriginalRGB2[0].length+ " pixeles de ancho y "+ infoOriginalRGB2.length + " pixeles de alto");
//
//       
//        //La nueva imagen a devolver tendra las dimensiones de la disorsion maxima.
//        float sizeNewArrayX = (float)infoOriginalRGB2[0].length*max;
//        float sizeNewArrayY = (float)infoOriginalRGB2.length*max;
//        int[][] infoTransRGB2 = new int[(int)sizeNewArrayY][(int)sizeNewArrayX];
//        //System.out.println("Construido nuevo array original con: "+infoTransRGB2[0].length+ " pixeles de ancho y "+ infoTransRGB2.length + " pixeles de alto");
//
//        //Relleno el nuevo array con un color por defecto.
//        for(int i = 0; i < infoTransRGB2.length; i++){
//            for(int j = 0; j < infoTransRGB2[i].length; j++){
//                infoTransRGB2[i][j]=COLOR_CONTROL;
//            }
//        }
//
//        //A partir del centro de la nueva imagen, y colocado en la posicion 0 del eje y, me desplazo un numero de pixeles hacia la izquierda 
//        //que dependera de las dimensiones de la nueva imagen. Maximo si la distorsion mayor es arriba (pos x=0) y minimo si la distorision mayor
//        //es arriba (pos x = centro_de_la_nueva_imagen/2 - minima_distorsion/2)
//        float posCenter;
//        posCenter = (infoTransRGB2[0].length/2)-((float)_vImage.getWidth()*min)/2;
//        
//        float[] positionDrawX = new float[_vImage.getHeight()];
//        
//       
//        for(int i = 0; i < infoOriginalRGB2.length; i++){
//            if(_fDistDown >_fDistUp)
//                positionDrawX[i]= posCenter - ((i*posCenter)/positionDrawX.length);
//            else
//                positionDrawX[i]=  ((i*(posCenter+1))/positionDrawX.length);
//        }
//        
//        float dif;
//        float repetions;
//        int mod_i=0;
//        
//        //Pinto la imagen original en mi nuevo array grande CENTRO
//        for(int i = 0; i < infoOriginalRGB2.length; i++){
//            
//           for(int to_repetat=0; to_repetat<_fDistDown;to_repetat++){
//            
//            
//            
//            dif = max - min;
//            if(_fDistDown >_fDistUp){
//                repetions= min + ((i*dif)/(float)(_vImage.getHeight()-1)); 
//            }else{
//                repetions = min + ((((_vImage.getHeight()-1)-i)*dif)/(float)(_vImage.getHeight()-1)); 
//            }
//           
//            float fAcumulated=repetions;
//            int pos = (int)positionDrawX[i];
//            int lastIntPart=0;
//            int currentIntPart=0;
//            
//            //System.out.println("repetions " + i + " : "+repetions);
//            for(int j = 0; j < infoOriginalRGB2[i].length; j++){
//                //Centro el pintado:
//                try{
//                    if (currentIntPart >= lastIntPart) {
//                        
//                        int numberRepitions = currentIntPart > 0?(int)(currentIntPart - lastIntPart):(int)repetions;
//                        for (float k = 0; k < numberRepitions && pos < infoTransRGB2[i].length; k++) {
//                            infoTransRGB2[mod_i][pos] = infoOriginalRGB2[i][j];
//                            pos++;
//                        }
//                    }
//                    lastIntPart = (int)fAcumulated;
//                    fAcumulated += repetions;
//                    currentIntPart = (int)fAcumulated;
//                    
//                }catch(Exception e){
//                    System.out.println("infoOriginalRGB2 con: "+infoOriginalRGB2[0].length+ " pixeles de ancho.");
//                    System.out.println("infoTransRGB2 con: "+infoTransRGB2[0].length+ " pixeles de ancho.");
//                    System.out.println("j: "+j);
//                    System.out.println("indexDraw: "+pos);
//                    System.out.println("repetions: "+repetions);
//                    e.printStackTrace();
//                    return null;
//               }
//            }
//            mod_i++;
//            //System.out.println("mod_i: " + mod_i);
//           }
//            
//        }
//
//        int[] finalArray = new int[infoTransRGB2.length*infoTransRGB2[0].length];
//
//        int index=0;
//        for(int i = 0; i <infoTransRGB2.length; i++){
//            for(int j = 0; j < infoTransRGB2[i].length; j++){
//                finalArray[index]=infoTransRGB2[i][j];
//                index++;
//            }
//        }
//        Image img = Image.createRGBImage(finalArray, (int)sizeNewArrayX, (int)sizeNewArrayY, true);
//        return img;
//    }
//    
//    
//    
//    public static Image getDistorsionedImageXY2(Image _vImage, float _fDistUp, float _fDistDown){
//        //Obtengo las medidas maximas y minimas para operar:
//         float min = (_fDistDown <_fDistUp?_fDistDown:_fDistUp);
//         float max = (_fDistDown >_fDistUp?_fDistDown:_fDistUp);
//        
//        //Extraigo la info RGB de la imagen original:
//        int[] infoOriginalRGB = new int[_vImage.getHeight()*_vImage.getWidth()];
//        _vImage.getRGB(infoOriginalRGB, 0, _vImage.getWidth(), 0, 0, _vImage.getWidth(), _vImage.getHeight());
//
//        //Lo monto en un nuevo array de dos dimensiones donde la primera dimesion son las filas y la segunda las columnas.
//        int[][] infoOriginalRGB2 = new int[_vImage.getHeight()][_vImage.getWidth()];
//        int savefilePos=0;
//        for(int i = 0; i < infoOriginalRGB2.length;i++){
//           for(int j = 0; j < infoOriginalRGB2[0].length;j++){
//               infoOriginalRGB2[i][j]=infoOriginalRGB[savefilePos];
//               savefilePos++;
//            }
//        }
//        //System.out.println("Construido array original con: "+infoOriginalRGB2[0].length+ " pixeles de ancho y "+ infoOriginalRGB2.length + " pixeles de alto");
//
//       
//        //La nueva imagen a devolver tendra las dimensiones de la disorsion maxima.
//        float sizeNewArrayX = (float)infoOriginalRGB2[0].length*max;
//        float sizeNewArrayY = (float)infoOriginalRGB2.length*max;
//        int[][] infoTransRGB2 = new int[(int)sizeNewArrayY][(int)sizeNewArrayX];
//        //System.out.println("Construido nuevo array original con: "+infoTransRGB2[0].length+ " pixeles de ancho y "+ infoTransRGB2.length + " pixeles de alto");
//
//        //Relleno el nuevo array con un color por defecto.
//        for(int i = 0; i < infoTransRGB2.length; i++){
//            for(int j = 0; j < infoTransRGB2[i].length; j++){
//                infoTransRGB2[i][j]=COLOR_CONTROL;
//            }
//        }
//
//        //A partir del centro de la nueva imagen, y colocado en la posicion 0 del eje y, me desplazo un numero de pixeles hacia la izquierda 
//        //que dependera de las dimensiones de la nueva imagen. Maximo si la distorsion mayor es arriba (pos x=0) y minimo si la distorision mayor
//        //es arriba (pos x = centro_de_la_nueva_imagen/2 - minima_distorsion/2)
//        float posCenter;
//        posCenter = (infoTransRGB2[0].length/2)-((float)_vImage.getWidth()*min)/2;
//        
//        float[] positionDrawX = new float[_vImage.getHeight()];
//        
//       
//        for(int i = 0; i < infoOriginalRGB2.length; i++){
//            if(_fDistDown >_fDistUp)
//                positionDrawX[i]= posCenter - ((i*posCenter)/positionDrawX.length);
//            else
//                positionDrawX[i]=  ((i*(posCenter+1))/positionDrawX.length);
//        }
//        
//        float dif;
//        float repetionsX;
//        float repetionsY;
//        int posY=0;
//        int lastIntPartY=0;
//        int currentIntPartY=0;
//        float fAcumulatedY=0;
//        
//        
//        for(int i = 0; i < infoOriginalRGB2.length; i++){
//            
//            dif = max - min;
//            if (_fDistDown > _fDistUp) {
//                repetionsY = min + (((infoOriginalRGB2.length-1) * dif) / (float) (_vImage.getHeight() - 1));
//            } else {
//                repetionsY = min + ((((_vImage.getHeight() - 1) - (infoOriginalRGB2.length-1)) * dif) / (float) (_vImage.getHeight() - 1));
//            }
//           int numberRepitionsY = currentIntPartY > 0?(int)(currentIntPartY - lastIntPartY):(int)repetionsY;
//           for (float m = 0; m < numberRepitionsY && posY < infoTransRGB2.length; m++) {
//               
//                dif = max - min;
//                if(_fDistDown >_fDistUp){
//                    repetionsX= min + ((i*dif)/(float)(_vImage.getHeight()-1)); 
//                }else{
//                    repetionsX = min + ((((_vImage.getHeight()-1)-i)*dif)/(float)(_vImage.getHeight()-1)); 
//                }
//
//                float fAcumulatedX=repetionsX;
//                int posX = (int)positionDrawX[i];
//                int lastIntPartX=0;
//                int currentIntPartX=0;
//
//                //System.out.println("repetions " + i + " : "+repetions);
//                for(int j = 0; j < infoOriginalRGB2[i].length; j++){
//                    //Centro el pintado:
//                    try{
//                        if (currentIntPartX >= lastIntPartX) {
//
//                            int numberRepitionsX = currentIntPartX > 0?(int)(currentIntPartX - lastIntPartX):(int)repetionsX;
//                            for (float k = 0; k < numberRepitionsX && posX < infoTransRGB2[i].length; k++) {
//                                infoTransRGB2[posY][posX] = infoOriginalRGB2[i][j];
//                                posX++;
//                            }
//                        }
//                        lastIntPartX = (int)fAcumulatedX;
//                        fAcumulatedX += repetionsX;
//                        currentIntPartX = (int)fAcumulatedX;
//
//                    }catch(Exception e){
//                        e.printStackTrace();
//                        System.out.println("infoOriginalRGB2 con: "+infoOriginalRGB2[0].length+ " pixeles de ancho.");
//                        System.out.println("infoTransRGB2 con: "+infoTransRGB2[0].length+ " pixeles de ancho.");
//                        System.out.println("j: "+j);
//                        System.out.println("indexDraw: "+posX);
//                        System.out.println("repetions: "+repetionsX);
//                        return null;
//                    }
//                }
//                posY++;
//                //System.out.println("iPosY: " + posY);
//           }
//            
//            lastIntPartY = (int)fAcumulatedY;
//            fAcumulatedY += repetionsY;
//            currentIntPartY = (int)fAcumulatedY;
//            
//        }
//
//        int[] finalArray = new int[infoTransRGB2.length*infoTransRGB2[0].length];
//
//        int index=0;
//        for(int i = 0; i <infoTransRGB2.length; i++){
//            for(int j = 0; j < infoTransRGB2[i].length; j++){
//                finalArray[index]=infoTransRGB2[i][j];
//                index++;
//            }
//        }
//        
//        Image img = Image.createRGBImage(finalArray, (int)sizeNewArrayX, (int)sizeNewArrayY, true);
//        return img;
//    }



}
