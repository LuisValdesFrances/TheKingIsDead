package com.luis.lgameengine.gameutils.fonts;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.implementation.graphics.Image;


/**
 *
 * @author Luis
 */
//Esta clase sólo funciona con las imágens .PNG que sigan el mismo formato que la de este juego.
//Lo que hace es averiguar en que mPOSción se encuentra el caracter deseado y devolver la medida width
//del ancho de una línea .TXT para que otra clase sepa a partir de que punto tiene que empezar a pintar.
public class Font {

    public static Image ms_Fonts[];

    public static final int FONT_SMALL = 0;
    public static final int FONT_MEDIUM = 1;
    public static final int FONT_BIG = 2;

    public static final float[] SYSTEM_SIZE = {
            12* Settings.getInstance().getScale(),
            16* Settings.getInstance().getScale(),
            24 * Settings.getInstance().getScale(),
            32 * Settings.getInstance().getScale()};

    private static boolean isGraphicFont;
    private static int iNumberChars;

    /**
     * Init Font settings
     * @param _FontSmall Small image graphic font
     * @param _FontMedium Medium image graphic font
     * @param _FontBig Big image graphic font
     */
    public static void init(Image _FontSmall, Image _FontMedium, Image _FontBig){

        if(_FontSmall == null || _FontMedium == null || _FontBig == null){
            isGraphicFont = false;
            iNumberChars = -1;
        }else{
            iNumberChars = getLetterPos('Ú')+1;
            ms_Fonts = new Image[3];
            ms_Fonts[0] = _FontSmall;
            ms_Fonts[1] = _FontMedium;
            ms_Fonts[2] = _FontBig;
            isGraphicFont = true;
        }
    }

    /**
     * Init Font settings
     */
    public static void init(){
        isGraphicFont = false;
        iNumberChars = -1;
    }

    public static boolean isGraphicFont(){
        return isGraphicFont;
    }
    public static int getNumberChars(){
        return iNumberChars;
    }

    public static int getFontWidth(int _iSize){
        if(isGraphicFont){
            return ms_Fonts[_iSize].getWidth()/iNumberChars;
        }else{
            return (int)SYSTEM_SIZE[_iSize];
        }
    }

    public static int getFontHeight(int _iSize){
        if(isGraphicFont){
            return ms_Fonts[_iSize].getHeight();
        }else{
            return (int)SYSTEM_SIZE[_iSize];
        }
    }


    public static int getLetterPos(char letter) {

        if (letter == 'a') {
            return 0;
        } else if (letter == 'b') {
            return 1;
        } else if (letter == 'c') {
            return 2;
        } else if (letter == 'd') {
            return 3;
        } else if (letter == 'e') {
            return 4;
        } else if (letter == 'f') {
            return 5;
        } else if (letter == 'g') {
            return 6;
        } else if (letter == 'h') {
            return 7;
        } else if (letter == 'i') {
            return 8;
        } else if (letter == 'j') {
            return 9;
        } else if (letter == 'k') {
            return 10;
        } else if (letter == 'l') {
            return 11;
        } else if (letter == 'm') {
            return 12;
        } else if (letter == 'n') {
            return 13;
        } else if (letter == 'o') {
            return 14;
        } else if (letter == 'p') {
            return 15;
        } else if (letter == 'q') {
            return 16;
        } else if (letter == 'r') {
            return 17;
        } else if (letter == 's') {
            return 18;
        } else if (letter == 't') {
            return 19;
        } else if (letter == 'u') {
            return 20;
        } else if (letter == 'v') {
            return 21;
        } else if (letter == 'w') {
            return 22;
        } else if (letter == 'x') {
            return 23;
        } else if (letter == 'y') {
            return 24;
        } else if (letter == 'z') {
            return 25;
        }
        else if (letter == 'A') {
            return 26;
        } else if (letter == 'B') {
            return 27;
        } else if (letter == 'C') {
            return 28;
        } else if (letter == 'D') {
            return 29;
        } else if (letter == 'E') {
            return 30;
        } else if (letter == 'F') {
            return 31;
        } else if (letter == 'G') {
            return 32;
        } else if (letter == 'H') {
            return 33;
        } else if (letter == 'I') {
            return 34;
        } else if (letter == 'J') {
            return 35;
        } else if (letter == 'K') {
            return 36;
        } else if (letter == 'L') {
            return 37;
        } else if (letter == 'M') {
            return 38;
        } else if (letter == 'N') {
            return 39;
        } else if (letter == 'O') {
            return 40;
        } else if (letter == 'P') {
            return 41;
        } else if (letter == 'Q') {
            return 42;
        } else if (letter == 'R') {
            return 43;
        } else if (letter == 'S') {
            return 44;
        } else if (letter == 'T') {
            return 45;
        } else if (letter == 'U') {
            return 46;
        } else if (letter == 'V') {
            return 47;
        } else if (letter == 'W') {
            return 48;
        } else if (letter == 'X') {
            return 49;
        } else if (letter == 'Y') {
            return 50;
        } else if (letter == 'Z') {
            return 51;
        }
        else if (letter == '1') {
            return 52;
        } else if (letter == '2') {
            return 53;
        } else if (letter == '3') {
            return 54;
        } else if (letter == '4') {
            return 55;
        } else if (letter == '5') {
            return 56;
        } else if (letter == '6') {
            return 57;
        } else if (letter == '7') {
            return 58;
        } else if (letter == '8') {
            return 59;
        } else if (letter == '9') {
            return 60;
        } else if (letter == '0') {
            return 61;
        }
        else if (letter == '.') {
            return 62;
        } else if (letter == ',') {
            return 63;
        } else if (letter == ';') {
            return 64;
        } else if (letter == ':') {
            return 65;
        } else if (letter == '¡') {
            return 66;
        } else if (letter == '!') {
            return 67;
        } else if (letter == '¿') {
            return 68;
        } else if (letter == '?') {
            return 69;
        } else if (letter == '/') {
            return 70;
        } else if (letter == '*') {
            return 71;
        } else if (letter == '+') {
            return 72;
        } else if (letter == '-') {
            return 73;
        } else if (letter == '$') {
            return 74;
        } else if (letter == 'º') {
            return 75;
        }
        else if (letter == 'ç') {
            return 76;
        } else if (letter == 'ñ') {
            return 77;
        } else if (letter == 'ã') {
            return 78;
        } else if (letter == 'â') {
            return 79;
        } else if (letter == 'à') {
            return 80;
        } else if (letter == 'á') {
            return 81;
        } else if (letter == 'õ') {
            return 82;
        } else if (letter == 'ô') {
            return 83;
        } else if (letter == 'ò') {
            return 84;
        } else if (letter == 'ó') {
            return 85;
        } else if (letter == 'ì') {
            return 86;
        } else if (letter == 'í') {
            return 87;
        } else if (letter == 'è') {
            return 88;
        } else if (letter == 'é') {
            return 89;
        } else if (letter == 'ù') {
            return 90;
        } else if (letter == 'ú') {
            return 91;
        }
        else if (letter == 'Ç') {
            return 92;
        } else if (letter == 'Ñ') {
            return 93;
        } else if (letter == 'Ã') {
            return 94;
        } else if (letter == 'Â') {
            return 95;
        } else if (letter == 'À') {
            return 96;
        } else if (letter == 'Á') {
            return 97;
        } else if (letter == 'Õ') {
            return 98;
        } else if (letter == 'Ô') {
            return 99;
        } else if (letter == 'Ò') {
            return 100;
        } else if (letter == 'Ó') {
            return 101;
        } else if (letter == 'Ì') {
            return 102;
        } else if (letter == 'Í') {
            return 103;
        } else if (letter == 'È') {
            return 104;
        } else if (letter == 'É') {
            return 105;
        } else if (letter == 'Ù') {
            return 106;
        } else if (letter == 'Ú') {
            return 107;
        } else{
            return -1;
        }

    }
}
