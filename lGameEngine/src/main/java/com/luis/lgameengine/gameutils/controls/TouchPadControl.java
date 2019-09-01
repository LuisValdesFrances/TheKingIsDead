package com.luis.lgameengine.gameutils.controls;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.gameworld.Math2D;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;

/**
 *
 * @author Luis Valdes Frances
 */
public class TouchPadControl implements GameControl{

    //Debug variables:

    private static boolean isDebug = false;
    //private static Font vDefaultFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    private int iActionDOWN;
    private int iActionDRAG;
    private int iActionUP;
    private boolean[] isObjectsToDraw = new boolean[2];//0 area pad, 1 Buttons
    private int iPadAreaX;
    private int iPadAreaY;
    private int[] iButtonsX;
    private int[] iButtonsY;
    private Image vImgPadArea;
    private Image vImgPad;
    private static int iPadAreaSize;
    private static int iPadSize;
    private static int iButtonsize;
    private boolean isPadAnchored;
    private int indexTouchingPad;
    private int iPadX;//Desplazamiento x del pad analogico desde el centro.
    private int iPadY;//Desplazamiento y del pad analogico desde el centro.
    //private int iDespX;//Desplazamiento x del pad analogico desde el centro.
    //private int iDespY;//Desplazamiento y del pad analogico desde el centro.
    private int iAngle;
    //Para el movimiento analogico. Retorna valor entre 0 y 1 donde 1 sera el desplazamiento maximo desde el radio y 0 cuando este no se mueva:
    private float fForce;
    private int iPadAreaWidth;
    private int iPadAreaHeight;
    private int iPadWidth;
    private int iPadHeight;
    private int iLastTouchValidX;
    private int iLastTouchValidY;
    private int[] iButtonsWidth;
    private int[] iButtonsHeight;
    private Image[] vImgButtons;
    private Image[] vImgButtonsPressed;
    private boolean[] isButtonsPressed;//Devuelve si se esta tocando el area del boton
    private boolean[] isButtonsActived;//Devuelve si el boton es activo (Solo pressDOWN)
    private int[] iButtonsFinger;//Guarda el indice touch del boton
    private int[] iButtonsCounter;//Devuelve el numero de frames en los que esta pressed un boton

    /**
     * Create a virtual pad and virtual buttons. The position pad is fixed in coordinates x and y
     *
     * @param _vImgPadArea image area. Can be null
     * @param _vImgPad image center. Can be null
     * @param _iPadX pad position x in screen. Can be null
     * @param _iPadY pad position x in screen. Can be null
     * @param _vImgButtons vector of Buttons. They can be call width ID by the
     * order in array position. Can be null.
     * @param _vImgButtonsPressed vector of Buttons thats is pressed. They can be call
     * width ID by the order in array position. Can be null.
     * @param _iButtonsX Buttons position x in screen. Can be null.
     * @param _iButtonsY Buttons position y in screen. Can be null.
     * @param _iValorDOWN index thats return the event. You can retrieve it by
     * getters
     * @param _iValorDRAG index thats return the event. You can retrieve it by
     * getters
     * @param _iValorUP index thats return the event. You can retrieve it by
     * getters
     */
    public TouchPadControl(
            Image _vImgPadArea, Image _vImgPad, int _iPadX, int _iPadY,
            Image[] _vImgButtons, Image[] _vImgButtonsPressed, int[] _iButtonsX, int[] _iButtonsY,
            int _iValorDOWN, int _iValorDRAG, int _iValorUP) {

        this.indexTouchingPad = -1;
        iPadAreaSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 4;
        iPadSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 12;
        iButtonsize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 8;

        this.iPadAreaX = _iPadX;
        this.iPadAreaY = _iPadY;
        this.iButtonsX = _iButtonsX;
        this.iButtonsY = _iButtonsY;
        this.vImgPadArea = _vImgPadArea;
        this.vImgPad = _vImgPad;;
        this.vImgButtons = _vImgButtons;
        this.vImgButtonsPressed = _vImgButtonsPressed;

        this.isPadAnchored = true;
        this.iPadX = iPadAreaX + (iPadAreaWidth >> 1);
        this.iPadY = iPadAreaY + (iPadAreaHeight >> 1);

        iActionDOWN = _iValorDOWN;
        iActionDRAG = _iValorDRAG;
        iActionUP = _iValorUP;

        iPadAreaWidth = _vImgPadArea != null ? _vImgPadArea.getWidth() : iPadAreaSize;
        iPadAreaHeight = _vImgPadArea != null ? _vImgPadArea.getHeight() : iPadAreaSize;
        iPadWidth = _vImgPad != null ? _vImgPad.getWidth() : iPadSize;
        iPadHeight = _vImgPad != null ? _vImgPad.getHeight() : iPadSize;
        isButtonsPressed = new boolean[_vImgButtons.length];
        isButtonsActived = new boolean[_vImgButtons.length];
        iButtonsFinger = new int[_vImgButtons.length];
        iButtonsCounter = new int[_vImgButtons.length];
        iButtonsWidth = new int[_vImgButtons.length];
        iButtonsHeight = new int[_vImgButtons.length];
        for (int i = 0; i < vImgButtons.length; i++) {
            isButtonsPressed[i] = false;
            iButtonsWidth[i] = vImgButtons[i] != null ? vImgButtons[i].getWidth() : iButtonsize;
            iButtonsHeight[i] = vImgButtons[i] != null ? vImgButtons[i].getHeight() : iButtonsize;
            iButtonsFinger[i] = -1;
        }

        isObjectsToDraw[0] = true;
        isObjectsToDraw[1] = true;


    }

    /**
     * Create a virtual pad and virtual buttons. The position pad is not fixed in coordinates x and y, its depends on finger position
     *
     * @param _vImgPadArea image area. Can be null
     * @param _vImgPad image center. Can be null
     * @param _vImgButtons vector of Buttons. They can be call width ID by the
     * order in array position. Can be null if no have Buttons. Can be null.
     * @param _vImgButtonsPressed vector of Buttons thats is pressed. They can be call
     * width ID by the order in array position. Can be null if no have Buttons.
     * Can be null.
     * @param _iButtonsX Buttons position x in screen. Can be null.
     * @param _iButtonsY Buttons position y in screen. Can be null.
     * @param _iValorDOWN index thats return the event. You can retrieve it by
     * getters
     * @param _iValorDRAG index thats return the event. You can retrieve it by
     * getters
     * @param _iValorUP index thats return the event. You can retrieve it by
     * getters
     */
    public TouchPadControl(
            Image _vImgPadArea, Image _vImgPad,
            Image[] _vImgButtons, Image[] _vImgButtonsPressed, int[] _iButtonsX, int[] _iButtonsY,
            int _iValorDOWN, int _iValorDRAG, int _iValorUP) {

        this.indexTouchingPad = -1;
        iPadAreaSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 4;
        iPadSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 12;
        iButtonsize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 8;

        this.iActionDOWN = _iValorDOWN;
        this.iActionDRAG = _iValorDRAG;
        this.iActionUP = _iValorUP;

        this.iButtonsX = _iButtonsX;
        this.iButtonsY = _iButtonsY;
        this.vImgPadArea = _vImgPadArea;
        this.vImgPad = _vImgPad;
        this.vImgButtons = _vImgButtons;
        this.vImgButtonsPressed = _vImgButtonsPressed;

        this.isPadAnchored = false;

        iPadAreaWidth = _vImgPadArea != null ? _vImgPadArea.getWidth() : iPadAreaSize;
        iPadAreaHeight = _vImgPadArea != null ? _vImgPadArea.getHeight() : iPadAreaSize;
        iPadWidth = _vImgPad != null ? _vImgPad.getWidth() : iPadSize;
        iPadHeight = _vImgPad != null ? _vImgPad.getHeight() : iPadSize;
        isButtonsPressed = new boolean[_vImgButtons.length];
        isButtonsActived = new boolean[_vImgButtons.length];
        iButtonsFinger = new int[_vImgButtons.length];
        iButtonsCounter = new int[_vImgButtons.length];
        iButtonsWidth = new int[_vImgButtons.length];
        iButtonsHeight = new int[_vImgButtons.length];
        for (int i = 0; i < vImgButtons.length; i++) {
            isButtonsPressed[i] = false;
            iButtonsWidth[i] = vImgButtons[i] != null ? vImgButtons[i].getWidth() : iButtonsize;
            iButtonsHeight[i] = vImgButtons[i] != null ? vImgButtons[i].getHeight() : iButtonsize;
            iButtonsFinger[i] = -1;
        }

        isObjectsToDraw[0] = true;
        isObjectsToDraw[1] = true;
    }

    /**
     * Create a virtual buttons
     *
     * @param _vImgButtons vector of Buttons. They can be call width ID by the
     * order in array position. Can be null.
     * @param _vImgButtonsPressed vector of Buttons thats is pressed. They can be call
     * width ID by the order in array position. Can be null.
     * @param _iButtonsX Buttons position x in screen. Can be null.
     * @param _iButtonsY Buttons position y in screen. Can be null.
     * @param _iValorDOWN index thats return the event. You can retrieve it by
     * getters
     * @param _iValorDRAG index thats return the event. You can retrieve it by
     * getters
     * @param _iValorUP index thats return the event. You can retrieve it by
     * getters
     */
    public TouchPadControl(
            Image[] _vImgButtons, Image[] _vImgButtonsPressed, int[] _iButtonsX, int[] _iButtonsY, int _iValorDOWN, int _iValorDRAG, int _iValorUP) {

        iPadAreaSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 4;
        iPadSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 12;
        iButtonsize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 8;

        this.indexTouchingPad = -1;
        this.iActionDOWN = _iValorDOWN;
        this.iActionDRAG = _iValorDRAG;
        this.iActionUP = _iValorUP;

        this.iButtonsX = _iButtonsX;
        this.iButtonsY = _iButtonsY;
        this.vImgButtons = _vImgButtons;
        this.vImgButtonsPressed = _vImgButtonsPressed;

        this.isPadAnchored = true;
        isButtonsPressed = new boolean[_vImgButtons.length];
        isButtonsActived = new boolean[_vImgButtons.length];
        iButtonsFinger = new int[_vImgButtons.length];
        iButtonsCounter = new int[_vImgButtons.length];
        iButtonsWidth = new int[_vImgButtons.length];
        iButtonsHeight = new int[_vImgButtons.length];
        for (int i = 0; i < vImgButtons.length; i++) {
            isButtonsPressed[i] = false;
            iButtonsWidth[i] = vImgButtons[i] != null ? vImgButtons[i].getWidth() : iButtonsize;
            iButtonsHeight[i] = vImgButtons[i] != null ? vImgButtons[i].getHeight() : iButtonsize;
            iButtonsFinger[i] = -1;
        }

        isObjectsToDraw[0] = false;
        isObjectsToDraw[1] = true;
    }

    /**
     * Create a virtual pad. The position pad is fixed in coordinates x and y
     *
     * @param _vImgPadArea image area. Can be null
     * @param _vImgPad image center. Can be null
     * @param _iPadX pad position x in screen. Can be null
     * @param _iPadY pad position x in screen. Can be null
     * @param _iValorDOWN index thats return the event. You can retrieve it by
     * getters
     * @param _iValorDRAG index thats return the event. You can retrieve it by
     * getters
     * @param _iValorUP index thats return the event. You can retrieve it by
     * getters
     */
    public TouchPadControl(
            Image _vImgPadArea, Image _vImgPad, int _iPadX, int _iPadY, int _iValorDOWN, int _iValorDRAG, int _iValorUP) {

        this.indexTouchingPad = -1;
        iPadAreaSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 4;
        iPadSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 12;
        iButtonsize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 8;

        this.iActionDOWN = _iValorDOWN;
        this.iActionDRAG = _iValorDRAG;
        this.iActionUP = _iValorUP;

        this.iPadAreaX = _iPadX;
        this.iPadAreaY = _iPadY;
        this.vImgPadArea = _vImgPadArea;
        this.vImgPad = _vImgPad;;

        this.isPadAnchored = true;
        this.iPadX = iPadAreaX + (iPadAreaWidth >> 1);
        this.iPadY = iPadAreaY + (iPadAreaHeight >> 1);

        iPadAreaWidth = _vImgPadArea != null ? _vImgPadArea.getWidth() : iPadAreaSize;
        iPadAreaHeight = _vImgPadArea != null ? _vImgPadArea.getHeight() : iPadAreaSize;
        iPadWidth = _vImgPad != null ? _vImgPad.getWidth() : iPadSize;
        iPadHeight = _vImgPad != null ? _vImgPad.getHeight() : iPadSize;

        isObjectsToDraw[0] = true;
        isObjectsToDraw[1] = false;
    }

    /**
     * Create a virtual pad. The position pad is not fixed in coordinates x and y, its depends on finger position
     *
     * @param _vImgPadArea image area. Can be null
     * @param _vImgPad image center. Can be null
     * @param _iValorDOWN index thats return the event. You can retrieve it by
     * getters
     * @param _iValorDRAG index thats return the event. You can retrieve it by
     * getters
     * @param _iValorUP index thats return the event. You can retrieve it by
     * getters
     */
    public TouchPadControl(
            Image _vImgPadArea, Image _vImgPad, int _iValorDOWN, int _iValorDRAG, int _iValorUP) {

        this.indexTouchingPad = -1;
        iPadAreaSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 4;
        iPadSize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 12;
        iButtonsize = ((Settings.getInstance().getScreenWidth() + Settings.getInstance().getScreenHeight())/2) / 8;

        iActionDOWN = _iValorDOWN;
        iActionDRAG = _iValorDRAG;
        iActionUP = _iValorUP;

        this.vImgPadArea = _vImgPadArea;
        this.vImgPad = _vImgPad;;

        this.isPadAnchored = false;

        iPadAreaWidth = _vImgPadArea != null ? _vImgPadArea.getWidth() : iPadAreaSize;
        iPadAreaHeight = _vImgPadArea != null ? _vImgPadArea.getHeight() : iPadAreaSize;
        iPadWidth = _vImgPad != null ? _vImgPad.getWidth() : iPadSize;
        iPadHeight = _vImgPad != null ? _vImgPad.getHeight() : iPadSize;

        isObjectsToDraw[0] = true;
        isObjectsToDraw[1] = false;
    }

    //Public methodes:
    public float getForce() {
        return fForce;
    }

    public int getAngle() {
        return iAngle;
    }

    public boolean isTouchingPad() {
        return indexTouchingPad != -1;
    }

    public boolean isButtonPressed(int _iIndex) {
        if (isButtonsPressed[_iIndex]) {
            return true;
        } else {
            return false;
        }
    }

    public int getButtonCounter(int _iIndex) {
        return iButtonsCounter[_iIndex];
    }

    /*Multouch engines*/
    public void update(int[] originX, int[] originY, int[] touchX, int[] touchY, int[] touchEvents) {
        try {
            if (isObjectsToDraw[1]) {
                updateButtons(touchX, touchY, touchEvents);
            }
            if (isObjectsToDraw[0]) {
                updateVirtualPad(originX, originY, touchX, touchY, touchEvents);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error in TouchControl draw: " + e.toString());
        }
    }

    public void draw(Graphics _g) {
        try {
            //Pad
            if (isObjectsToDraw[0]) {
                if (isTouchingPad()) {
                    _g.setColor(0xFF1E00FF);
                } else {
                    _g.setColor(0xfffd0101);
                }
                //Virtual pad
                if (iPadX>0 || iPadY>0) {
                    _g.drawLine(iPadX, iPadY, iPadAreaX, iPadAreaY);

                    if (vImgPadArea != null) {
                        _g.drawImage(vImgPadArea, iPadAreaX, iPadAreaY, Graphics.VCENTER | Graphics.HCENTER);
                    } else {
                        _g.drawRoundRect(iPadAreaX - (iPadAreaWidth >> 1), iPadAreaY - (iPadAreaHeight >> 1), iPadAreaSize, iPadAreaSize, iPadAreaWidth, iPadAreaHeight);
                        _g.setColor(0xFFFFFFFF);
                        _g.drawRoundRect(iPadAreaX - (iPadAreaWidth >> 1)-1, iPadAreaY - (iPadAreaHeight >> 1)-1, iPadAreaSize+2, iPadAreaSize+2, iPadAreaWidth+2, iPadAreaHeight+2);
                    }
                    if (vImgPad != null) {
                        _g.drawImage(vImgPad, iPadX, iPadY, Graphics.VCENTER | Graphics.HCENTER);
                    } else {
                        _g.drawRoundRect(iPadX - (iPadWidth >> 1), iPadY - (iPadHeight >> 1), iPadSize, iPadSize, iPadSize, iPadSize);
                        _g.setColor(0xFFFFFFFF);
                        _g.drawRoundRect(iPadX - (iPadWidth >> 1)-1, iPadY - (iPadHeight >> 1)-1, iPadSize+2, iPadSize+2, iPadSize+2, iPadSize+2);
                    }
                }
            }
            //Buttons
            if (isObjectsToDraw[1]) {
                for (int i = 0; i < isButtonsPressed.length; i++) {
                    if (isButtonsPressed[i]) {
                        if (vImgButtonsPressed[i] != null) {
                            //#if FastRender
                            //_g.setAlpha(120);
                            //#endif
                            _g.drawImage(vImgButtonsPressed[i], iButtonsX[i], iButtonsY[i], Graphics.VCENTER | Graphics.HCENTER);
                            //#if FastRender
                            //_g.setAlpha(255);
                            //#endif
                        } else {
                            _g.setColor(0xFF1E00FF);
                            _g.drawRoundRect(iButtonsX[i] - (iButtonsWidth[i] >> 1), iButtonsY[i] - (iButtonsHeight[i] >> 1), iButtonsWidth[i], iButtonsHeight[i],
                                    iButtonsWidth[i], iButtonsHeight[i]);
                            _g.setColor(0xFFFFFFFF);
                            _g.drawRoundRect(
                                    iButtonsX[i] - (iButtonsWidth[i] >> 1)-1, iButtonsY[i] - (iButtonsHeight[i] >> 1)-1,
                                    iButtonsWidth[i]+2, iButtonsHeight[i]+2,
                                    iButtonsWidth[i]+2, iButtonsHeight[i]+2);
                        }
                    } else {
                        if (vImgButtons[i] != null) {
                            //#if FastRender
                            //_g.setAlpha(120);
                            //#endif
                            _g.drawImage(vImgButtons[i], iButtonsX[i], iButtonsY[i], Graphics.VCENTER | Graphics.HCENTER);
                            //#if FastRender
                            //_g.setAlpha(255);
                            //#endif
                        } else {
                            _g.setColor(0xFFFF0000);
                            _g.drawRoundRect(iButtonsX[i] - (iButtonsWidth[i] >> 1), iButtonsY[i] - (iButtonsHeight[i] >> 1), iButtonsWidth[i], iButtonsHeight[i],
                                    iButtonsWidth[i], iButtonsHeight[i]);

                            _g.setColor(0xFFFFFFFF);
                            _g.drawRoundRect(
                                    iButtonsX[i] - (iButtonsWidth[i] >> 1)-1, iButtonsY[i] - (iButtonsHeight[i] >> 1)-1,
                                    iButtonsWidth[i]+2, iButtonsHeight[i]+2,
                                    iButtonsWidth[i]+2, iButtonsHeight[i]+2);
                        }
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error in TouchControl draw: " + e.toString());
        }

        if (isDebug) {
            _g.setTextSize((int) (24 * Settings.getInstance().getScale()));
            _g.setAlpha(160);
            _g.setColor(0xff000000);
            _g.fillRect(0, 0, Settings.getInstance().getScreenWidth(), _g.getTextHeight() * 6);
            _g.setAlpha(255);

            if (isObjectsToDraw[0]) {
                _g.drawText("Angle: " + getAngle(), 0, _g.getTextHeight(), 0xffffffff);
                _g.drawText("Force: " + getForce(), Settings.getInstance().getScreenWidth() / 2, _g.getTextHeight(), 0xffffffff);
            }
            if (isObjectsToDraw[1]) {
                for (int i = 0; i < isButtonsPressed.length; i++) {
                    _g.drawText("Press " + "id: " + i + " :" + isButtonsPressed[i], 0, _g.getTextHeight() * (i + 2), 0xffffffff);
                    _g.drawText("Act " + "id: " + i + " :" + isButtonsActived[i],
                            Settings.getInstance().getScreenWidth() / 2 - Settings.getInstance().getScreenWidth() / 8,
                            _g.getTextHeight() * (i + 2), 0xffffffff);
                    _g.drawText("Tim " + "id: " + i + " :" + iButtonsCounter[i],
                            Settings.getInstance().getScreenWidth() - Settings.getInstance().getScreenWidth() / 4,
                            _g.getTextHeight() * (i + 2), 0xffffffff);
                }
            }

        }

    }

    private boolean isAnyIndexTouchingScreen(int[] touchEvents){
        for(int i = 0; i < touchEvents.length; i++){
            if(isIndexTouchingScreen(touchEvents, i)){
                return true;
            }
        }
        return false;
    }

    private boolean isIndexTouchingScreen(int[] touchEvents, int index){
        return touchEvents[index] == iActionDOWN || touchEvents[index] == iActionDRAG;
    }

    /*Multitouch engines*/
    private void updateButtons(int[] _iTouchX, int[] _iTouchY, int[] _iTouchEvents) {
        //Desactivo todo:
        for (int i = 0; i < vImgButtons.length; i++) {
            isButtonsActived[i] = false;
        }

        for (int i = 0; i < _iTouchX.length; i++) {
            //Button collide
            for (int j = 0; j < vImgButtons.length; j++) {
                int ButtonX = iButtonsX[j];
                int ButtonY = iButtonsY[j];
                //Si el index multitouch es no es actionUp y el index multitouch colisiona con algun boton:
                if ((_iTouchEvents[i] != iActionUP)
                        && (_iTouchX[i] > ButtonX - (iButtonsWidth[j] >> 1) && _iTouchX[i] < ButtonX + (iButtonsWidth[j] >> 1)
                        && _iTouchY[i] > ButtonY - (iButtonsHeight[j] >> 1) && _iTouchY[i] < ButtonY + (iButtonsHeight[j] >> 1))) {

                    //Si el index que toca el boton es el reservado para el pad y este esta siendo procesado,
                    //No activamos el boton y nos vamos a la mierda:
                    if (i == indexTouchingPad) {
                        isButtonsPressed[j] = false;
                        isButtonsActived[j] = false;
                        return;
                    }
                    ///////////////////////////////////////////////////////////////////////////////////////////
                    if (iButtonsFinger[j] == -1 || iButtonsFinger[j] == i) {//Si el indice multitouch esta libre o es el que tenemos guardado, lo proceso
                        isButtonsPressed[j] = true;
                        iButtonsFinger[j] = i;//Guardo el index del dedo

                        if (_iTouchEvents[i] == iActionDOWN && iButtonsCounter[j] == 0) {
                            isButtonsActived[j] = true;
                        } else {
                            isButtonsActived[j] = false;
                        }

                        iButtonsCounter[j]++;
                    }
                } //Si el index multitouch es de tipo actionUP o no esta colisionando con ningun boton, debemos de comprobar si dicho index multitouch
                //corresponde algunos de los botonoes que tenemos. En caso de que asi sea, lo desactivamos:
                else if (iButtonsFinger[j] == i) {
                    isButtonsPressed[j] = false;
                    iButtonsCounter[j] = 0;
                    iButtonsFinger[j] = -1;

                }
            }
        }
    }

    /*Single touch*/
    private boolean isButtonCollide(int _iTouchX, int _iTouchY) {
        if (isObjectsToDraw[1]) {
            for (int i = 0; i < vImgButtons.length; i++) {
                if ((_iTouchX + (iPadAreaWidth >> 1) > iButtonsX[i] - (iButtonsWidth[i] >> 1) &&
                        _iTouchX - (iPadAreaWidth >> 1) < iButtonsX[i] + (iButtonsWidth[i] >> 1) &&
                        _iTouchY + (iPadAreaHeight >> 1) > iButtonsY[i] - (iButtonsHeight[i] >> 1) &&
                        _iTouchY - (iPadAreaHeight >> 1) < iButtonsY[i] + (iButtonsHeight[i] >> 1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateVirtualPad(int originX[], int originY[], int _iTouchX[], int _iTouchY[], int[] touchEvents) {

        if(isAnyIndexTouchingScreen(touchEvents)){
            //Mientras se este tocando la pantalla sin colisionar con ningun boton, se esta tocado el pad
            if (indexTouchingPad == -1) {
                //Ontengo el primer indice que no este colisionando con ningun boton:
                indexTouchingPad = -1;
                for (int i = 0; i < touchEvents.length && indexTouchingPad == -1; i++) {
                    if (isIndexTouchingScreen(touchEvents, i) && !isButtonCollide(_iTouchX[i], _iTouchY[i])) {
                        indexTouchingPad = i;
                        iLastTouchValidX = originX[indexTouchingPad];
                        iLastTouchValidY = originY[indexTouchingPad];
                        if (!isPadAnchored) {
                            iPadAreaX = originX[indexTouchingPad];
                            iPadAreaY = originY[indexTouchingPad];
                        }
                    }
                }
            }
            if(indexTouchingPad != -1 && isIndexTouchingScreen(touchEvents, indexTouchingPad)) {
                {
                    int touchX = _iTouchX[indexTouchingPad];
                    int touchY = _iTouchY[indexTouchingPad];
                    int distX = Math.abs(iPadAreaX - touchX);
                    int distY = Math.abs(iPadAreaY - touchY);
                    int maxDistX = (iPadAreaWidth >> 1);
                    int maxDistY = (iPadAreaHeight >> 1);

                    if (distX > maxDistX) {
                        distX = maxDistX;
                    }
                    if (distY > maxDistY) {
                        distY = maxDistY;
                    }

                    iAngle = Math2D.getAngle360(iPadAreaX, iPadAreaY, touchX, touchY);
                    /*
                     * 1 << Math2D.PRECISION_BITS == dist Math2D.cos(touchAngle) == x
                     */
                    iPadX = (iPadAreaX + ((Math2D.cos(iAngle) * distX) / (1 << Math2D.PRECISION_BITS)));//>>8;//Math2D.cos(360));
                    iPadY = (iPadAreaY + ((Math2D.sin(iAngle) * distY) / (-1 << Math2D.PRECISION_BITS)));//>>8;//Math2D.sin(270));

                    //Force:
                    fForce = (float) getMayor(distX, distY) / (float) (iPadAreaWidth / 2) > 1f ? 1f : (float) getMayor(distX, distY) / (float) (iPadAreaWidth / 2);
                }
            }else{
                resetPad();
            }
        }else{
            resetPad();
        }
    }

    public void resetPad(){
        iPadX = iPadAreaX;
        iPadY = iPadAreaY;
        iAngle = -1;
        fForce = 0;
        indexTouchingPad = -1;
    }

    public void reset() {
        if (isObjectsToDraw[0]) {
            indexTouchingPad = -1;
        }
        if (isObjectsToDraw[1]) {
            for (int i = 0; i < isButtonsPressed.length; i++) {
                isButtonsPressed[i] = false;
                isButtonsActived[i] = false;
                iButtonsCounter[i] = 0;
                iButtonsFinger[i] = -1;
            }
        }

    }

    private int getMayor(int _iValue1, int _iValue2) {
        return _iValue1 > _iValue2 ? _iValue1 : _iValue2;
    }
}
