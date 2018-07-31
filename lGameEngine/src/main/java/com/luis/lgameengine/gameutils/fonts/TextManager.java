package com.luis.lgameengine.gameutils.fonts;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;


/**
 *
 * @author Luis Valdes Frances
 */
//La funcion de esta clase es pintar textos largos, provenientyes de un archivo .TXT, en pantalla.
//Para ello calcula al anchura de este y despues lo divide en línenas que centra, o no, según unos
//parámetros.
public class TextManager {
	
	
    private static String[] mWORDS;
    private static String[] mCOMPOSE_TEXT;
    public static int mPOS_Y_TEXT;
    
    public static final byte ALING_CENTER = 0;
    public static final byte ALING_LEFT = 1;
    public static final byte ALING_RIGHT = 2;
    
    /**
     * @param font
     * @param text
     * @return Anchura de una o varias:
     */
    public static int calulateWidthWords(Image font, String text) {
        return (font.getWidth() / Font.getNumberChars()) * text.length();
    }

    //Altura del TEXTO:
    private static int calulateHeightText(Image font, String[] text) {
        return text.length * font.getHeight();
    }

    //Recibimos como parametro un String con todos los
    //caracteres que forman el texto.
    //Convermimos este array, de una sola posición, en un array con una palabra
    //por posición.
    private static String[] getArrayWords(String text) {
    	
    	mWORDS = new String[getNumberWords(text)];
        boolean endFrase = false;
        int e = 0;

        for (int i = 0; i < mWORDS.length; i++) {

            while (text.charAt(e) !=  ' ' && !endFrase) {
                if (mWORDS[i] == null) {
                    mWORDS[i] = "" + text.charAt(e);
                } else {
                    mWORDS[i] += text.charAt(e);
                }

                if (e < text.length() - 1) {
                    e++;
                } else {
                    endFrase = true;
                }
            }
            e++;
        }
        return mWORDS;
    }

    //Convierte un string de palabras, en un String de un grupo de palabras, 
    //dependiendo de la anchura del recuadro.
    //t > array de palabras.
    private static void composeText(Image font, String[] words, int rectangle_width) {
        int lines_text = 1;
        int size = 0;

        for (int i = 0; i < words.length; i++) {
            if (size + (calulateWidthWords(font, words[i])
                    + font.getWidth() / Font.getNumberChars())
                    < rectangle_width ) {
                size += ((calulateWidthWords(font, words[i]) 
                        + (font.getWidth() / Font.getNumberChars()))) ;
            } else {
                size = calulateWidthWords(font, words[i]);
                lines_text++;
            }
        }
        //System.out.println("Número de líneas es :" + lines_text);
        mCOMPOSE_TEXT = new String[lines_text];
        for (int i = 0; i < mCOMPOSE_TEXT.length; i++) {
            mCOMPOSE_TEXT[i] = "";
        }

        //Repetimos el proceso anterior, pero rellenando nuestro array recien creado:
        int e = 0;
        size = 0;
        for (int i = 0; i < words.length; i++) {
            if ((size + calulateWidthWords(font, words[i])) 
                    + font.getWidth() / Font.getNumberChars()
                    < rectangle_width) {
                size += (calulateWidthWords(font, words[i]) 
                        + (font.getWidth() / Font.getNumberChars()));
                mCOMPOSE_TEXT[e] += words[i];
                //Aañadimos un espacio vacio tras cada palabra siempre EXCEPTO 	SI ES LA ULTIMA PALABRA
                //de la línea o del texto:
                if(i < words.length-1
                		&&(size + calulateWidthWords(font, words[i+1])) 
                        + font.getWidth() / Font.getNumberChars()
                        < rectangle_width){
                	mCOMPOSE_TEXT[e] +=" ";
	            }else{
	            	//size -= (font.getWidth() / Font.getNumberChars());
	            }

            } else {
                e++;
                size = calulateWidthWords(font, words[i]);
                mCOMPOSE_TEXT[e] += words[i];
                if(i < words.length-1)mCOMPOSE_TEXT[e] +=" ";

            }
            //System.out.println("Añadido " + words[i] + " " + "a la línea " + e);

        }

//        System.out.println("Texto montado es:");
//        for (int i = 0; i < newText.length; i++) {
//            System.out.println(newText[i]);
//        }

        }
    
    private static int getNumberWords(String allLeters) {
        int num = 1;
        for (int i = 0; i < allLeters.length(); i++) {
            if (allLeters.charAt(i) == ' ') {
                num++;
            }
        }
        return num;
   }
    
    /**
     * Draw specific text in a screen position specify
     * @param g
     * @param _iFontSize Font.FONT_SMALL | Font.FONT_MEDIUM | Font.FONT_BIG  
     * @param text
     * @param pos_x
     * @param pos_y
     * @param _iAnchor
     */
    public static void drawSimpleText(Graphics g, int _iFontSize, String text, int pos_x, int pos_y, int _iAnchor) {
    	int iExtra_X = 0, iExtra_Y = 0;
    	
    	if ((_iAnchor & Graphics.BOTTOM) != 0)
             iExtra_Y = (Font.getFontHeight(_iFontSize))*-1;
         if ((_iAnchor & Graphics.RIGHT) != 0)
             iExtra_X = (Font.getFontWidth(_iFontSize)*text.length())*-1;
         if ((_iAnchor & Graphics.VCENTER) != 0)
             iExtra_Y = (Font.getFontHeight(_iFontSize) >> 1)*-1;
         if ((_iAnchor & Graphics.HCENTER) != 0)
             iExtra_X = ((Font.getFontWidth(_iFontSize) *text.length())>> 1)*-1;
    	
        if(Font.isGraphicFont()){
	    	int posX = pos_x + iExtra_X;
	        int posY = pos_y + iExtra_Y;
	        
	        for (int e = 0; e < text.length(); e++) {
	                g.setClip(posX, posY, Font.getFontWidth(_iFontSize),Font.getFontHeight(_iFontSize));
	                g.drawImage(Font.ms_Fonts[_iFontSize],
	                        posX - ((Font.getLetterPos(text.charAt(e))) * Font.getFontWidth(_iFontSize)),
	                        posY, 0);
	                posX += Font.getFontWidth(_iFontSize);
	                //g.fillRect(0, 0, Define.Settings.getInstance().getScreenWidth(), Define.Settings.getInstance().getScreenHeight());
	            }
	        g.setClip(0, 0, Settings.getInstance().getScreenWidth(), Settings.getInstance().getScreenHeight());
    	}else{
    		
    	}
    }
    
    //Pinta en el centro de la pantalla con la anchura que le indiquemos:
    /**
     * 
     * @param g
     * @param font
     * @param text
     * @param rectangle_width
     * @param aling Alineacion del texto dentro de la caja: ALING_CENTER, ALING_LEFT, ALING_RIGHT
     * @param number_leters
     */
    public static void drawInCenter(Graphics g, Image font, String text, int rectangle_width, byte aling, int number_leters) {
    	if(Font.isGraphicFont()){
        composeText(font, getArrayWords(text),rectangle_width);

        int maxWidthText;
        int IniposX;
        int posX;
        int posY = 0;
        int l;//Longitud de la línea mas larga.
        int s;//Posición del array donde se encuentra la línea mas larga.

        //Control de pintado de letras pro frames:
        boolean end = false;
        int size = 0;

        switch (aling) {
            
            case ALING_CENTER:
                //System.out.println("Texto alineado en el centro.");
                //Tenemos que encontrar el punto x y el punto y de donde se pintara 
                //cada línea de texto:
                int[] ArrayPosX = new int[mCOMPOSE_TEXT.length];
                for (int i = 0; i < mCOMPOSE_TEXT.length; i++) {
                    ArrayPosX[i] = (Settings.getInstance().getScreenWidth() / 2) - (calulateWidthWords(font, mCOMPOSE_TEXT[i]) >> 1);
                }

                posY = (Settings.getInstance().getScreenHeight() / 2) - (calulateHeightText(font, mCOMPOSE_TEXT) >> 1);
               
                if(number_leters > 0 || number_leters == -1)
                for (int i = 0; i < mCOMPOSE_TEXT.length && !end; i++) {
                    for (int e = 0; e < mCOMPOSE_TEXT[i].length(); e++) {
                        g.setClip(
                        		ArrayPosX[i], posY, font.getWidth() / Font.getNumberChars(), font.getHeight());
                        g.drawImage(font, ArrayPosX[i] - ((Font.getLetterPos(mCOMPOSE_TEXT[i].charAt(e)))
                                * (font.getWidth() / Font.getNumberChars())),
                                posY, Graphics.TOP | Graphics.LEFT);
                        ArrayPosX[i] += font.getWidth() / Font.getNumberChars();
                        
                        size++;
                        if(size == number_leters && number_leters != -1){
                        	end = true;
                        	break;
                        }
                    }

                    posY += font.getHeight();
                }
                break;
           
            case ALING_LEFT:
                //System.out.println("Texto alineado a la izquierda.");
                //Tenemos que encontrar el punto x y el punto y de donde se pintara el texto:
                //Buscamos la línea mas larga:
                l = 0;
                s = 0;
                for (int i = 0; i < mCOMPOSE_TEXT.length; i++) {
                    if (mCOMPOSE_TEXT[i].length() > l) {
                        l = mCOMPOSE_TEXT[i].length();
                        s = i;
                    }
                }
                //System.out.println("La línea mas larga es la " + (s + 1));
                maxWidthText = calulateWidthWords(font, mCOMPOSE_TEXT[s]);
                IniposX = (Settings.getInstance().getScreenWidth() / 2) - (maxWidthText>>1);
                posX = IniposX;
                posY = (Settings.getInstance().getScreenHeight() / 2) - (calulateHeightText(font, mCOMPOSE_TEXT) >> 1);
                
                if(number_leters > 0 || number_leters == -1)
                for (int i = 0; i < mCOMPOSE_TEXT.length && !end; i++) {
                    for (int e = 0; e < mCOMPOSE_TEXT[i].length(); e++) {
                        g.setClip(posX, posY, font.getWidth() / Font.getNumberChars(),font.getHeight());
                        g.drawImage(font,posX - ((Font.getLetterPos(mCOMPOSE_TEXT[i].charAt(e)))
                                * (font.getWidth() / Font.getNumberChars())),posY, Graphics.TOP | Graphics.LEFT);
                        posX += font.getWidth() / Font.getNumberChars();
                       
                        size++;
                        if(size == number_leters && number_leters != -1){
                        	end = true;
                        	break;
                        }
                    }
                    posX = IniposX;
                    posY += font.getHeight();
                }
                break;
            
            case ALING_RIGHT:
                //System.out.println("Texto alineado a la derecha.");
                //Tenemos que encontrar el punto x y el punto y de donde se pintara el texto:
                //Buscamos la línea mas larga:
                l = 0;
                s = 0;
                for (int i = 0; i < mCOMPOSE_TEXT.length; i++) {
                    if (mCOMPOSE_TEXT[i].length() > l) {
                        l = mCOMPOSE_TEXT[i].length();
                        s = i;
                    }
                }
                //System.out.println("La línea mas larga es la " + (s + 1));
                maxWidthText = calulateWidthWords(font, mCOMPOSE_TEXT[s]);
                IniposX = (Settings.getInstance().getScreenWidth() / 2) - (maxWidthText >> 1);
                posX = IniposX + (maxWidthText - calulateWidthWords(font, mCOMPOSE_TEXT[0]));
                posY = (Settings.getInstance().getScreenHeight() / 2) - (calulateHeightText(font, mCOMPOSE_TEXT) >> 1);
                
                if(number_leters > 0 || number_leters == -1)
                for (int i = 0; i < mCOMPOSE_TEXT.length && !end; i++) {
                    for (int e = 0; e < mCOMPOSE_TEXT[i].length(); e++) {
                        g.setClip(posX, posY, font.getWidth() / Font.getNumberChars(),font.getHeight());
                        g.drawImage(font,
                                posX - ((Font.getLetterPos(mCOMPOSE_TEXT[i].charAt(e)))
                                * (font.getWidth() / Font.getNumberChars())),
                                posY, Graphics.TOP | Graphics.LEFT);
                        posX += font.getWidth() / Font.getNumberChars();
                        
                        size++;
                        if(size == number_leters && number_leters != -1){
                        	end = true;
                        	break;
                        }
                    }
                    if (i < mCOMPOSE_TEXT.length - 1) {
                        IniposX = ((Settings.getInstance().getScreenWidth() / 2) - (maxWidthText >> 1))
                                + (maxWidthText - calulateWidthWords(font, mCOMPOSE_TEXT[i + 1]));
                    }
                    posX = IniposX;
                    posY += font.getHeight();
                }
                break;

        }
         mPOS_Y_TEXT = posY;
         g.setClip(0, 0, Settings.getInstance().getScreenWidth(), Settings.getInstance().getScreenHeight());
    	}else{
    		
    	}
    }
    
    //Se coge como referencia el centro
    /**
     * Pinta un texto en la pantalla, aplicando margenes y lineas automaticas
     * @param g Objeto de tipo graphics donde se va a pintar
     * @param _iFontSize Font.FONT_SMALL | Font.FONT_MEDIUM | Font.FONT_BIG  
     * @param text Cadena de texto
     * @param pos_x Posicion x
     * @param pos_y Posicion y
     * @param rectangle_width Anchura de la caja que contiene el texto
     * @param aling Alineacion del texto dentro de la caja: ALING_CENTER, ALING_LEFT, ALING_RIGHT
     * @param number_leters
     */
    public static void draw(Graphics g, int _iFontSize, String text,
    		int pos_x, int pos_y, int rectangle_width, byte aling, int number_leters) {
   	 
    	if(Font.isGraphicFont()){
	    	composeText(Font.ms_Fonts[_iFontSize], getArrayWords(text),rectangle_width);
	
	        int maxWidthText;
	        int IniposX;
	        int posX;
	        int l;//Longitud de la línea mas larga.
	        int s;//Posición del array donde se encuentra la línea mas larga.
	        
	        //Control de pintado de letras pro frames:
	        boolean end = false;
	        int size = 0;
	
	
	        switch (aling) {
	            //Centro:
	            case ALING_CENTER:
	                //System.out.println("Texto alineado en el centro.");
	                //Tenemos que encontrar el punto x y el punto y de donde se pintara 
	                //cada línea de texto:
	                int[] ArrayPosX = new int[mCOMPOSE_TEXT.length];
	                for (int i = 0; i < mCOMPOSE_TEXT.length; i++) {
	                    ArrayPosX[i] = pos_x - (calulateWidthWords(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT[i]) >> 1);
	                }
	                pos_y = pos_y - (calulateHeightText(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT) >> 1);
	
	                if(number_leters > 0 || number_leters == -1)
	                for (int i = 0; i < mCOMPOSE_TEXT.length && !end; i++) {
	                    for (int e = 0; e < mCOMPOSE_TEXT[i].length(); e++) {
	                        g.setClip(ArrayPosX[i], pos_y, 
	                        		Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars(), Font.ms_Fonts[_iFontSize].getHeight());
	                        g.drawImage(Font.ms_Fonts[_iFontSize],
	                                ArrayPosX[i] - ((Font.getLetterPos(mCOMPOSE_TEXT[i].charAt(e)))
	                                * (Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars())),
	                                pos_y, Graphics.TOP | Graphics.LEFT);
	                        ArrayPosX[i] += Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars();
	                      
	                        size++;
	                        if(size == number_leters && number_leters != -1){
	                        	end = true;
	                        	break;
	                        }
	                    }
	
	                    pos_y += Font.ms_Fonts[_iFontSize].getHeight();
	                }
	                break;
	            //Izqierda:
	            case ALING_LEFT:
	                //System.out.println("Texto alineado a la izquierda.");
	                //Tenemos que encontrar el punto x y el punto y de donde se pintara el texto:
	                //Buscamos la línea mas larga:
	                l = 0;
	                s = 0;
	                for (int i = 0; i < mCOMPOSE_TEXT.length; i++) {
	                    if (mCOMPOSE_TEXT[i].length() > l) {
	                        l = mCOMPOSE_TEXT[i].length();
	                        s = i;
	                    }
	                }
	                //System.out.println("La línea mas larga es la " + (s + 1));
	                maxWidthText = calulateWidthWords(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT[s]);
	                IniposX = pos_x - (maxWidthText >> 1);
	                posX = IniposX;
	                pos_y = pos_y - (calulateHeightText(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT) >> 1);
	                
	                if(number_leters > 0 || number_leters == -1)
	                for (int i = 0; i < mCOMPOSE_TEXT.length && !end; i++) {
	                    for (int e = 0; e < mCOMPOSE_TEXT[i].length(); e++) {
	                        g.setClip(posX, pos_y, 
	                        		Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars(), Font.ms_Fonts[_iFontSize].getHeight());
	                        g.drawImage(Font.ms_Fonts[_iFontSize],
	                                posX - ((Font.getLetterPos(mCOMPOSE_TEXT[i].charAt(e)))
	                                * (Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars())),
	                                pos_y, Graphics.TOP | Graphics.LEFT);
	                        posX += Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars();
	                        
	                        size++;
	                        if(size == number_leters && number_leters != -1){
	                        	end = true;
	                        	break;
	                        }
	                    }
	                    posX = IniposX;
	                    pos_y += Font.ms_Fonts[_iFontSize].getHeight();
	                }
	                break;
	            //Derecha:
	            case ALING_RIGHT:
	                //System.out.println("Texto alineado a la derecha.");
	                //Tenemos que encontrar el punto x y el punto y de donde se pintara el texto:
	                //Buscamos la línea mas larga:
	                l = 0;
	                s = 0;
	                for (int i = 0; i < mCOMPOSE_TEXT.length; i++) {
	                    if (mCOMPOSE_TEXT[i].length() > l) {
	                        l = mCOMPOSE_TEXT[i].length();
	                        s = i;
	                    }
	                }
	                //System.out.println("La línea mas larga es la " + (s + 1));
	                maxWidthText = calulateWidthWords(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT[s]);
	                IniposX = pos_x - (maxWidthText  >> 1);
	                posX = IniposX
	                        + (maxWidthText - calulateWidthWords(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT[0]));
	                pos_y = pos_y - (calulateHeightText(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT) >> 1);
	                
	                if(number_leters > 0 || number_leters == -1)
	                for (int i = 0; i < mCOMPOSE_TEXT.length && !end; i++) {
	                    for (int e = 0; e < mCOMPOSE_TEXT[i].length(); e++) {
	                        g.setClip(posX, pos_y, 
	                        		Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars(), Font.ms_Fonts[_iFontSize].getHeight());
	                        g.drawImage(Font.ms_Fonts[_iFontSize],
	                                posX - ((Font.getLetterPos(mCOMPOSE_TEXT[i].charAt(e)))
	                                * (Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars())),
	                                pos_y, Graphics.TOP | Graphics.LEFT);
	                        posX += Font.ms_Fonts[_iFontSize].getWidth() / Font.getNumberChars();
	                        
	                        
	                        size++;
	                        if(size == number_leters && number_leters != -1){
	                        	end = true;
	                        	break;
	                        }
	                    }
	                    if (i < mCOMPOSE_TEXT.length - 1) {
	                        IniposX = (pos_x - (maxWidthText >> 1))
	                                + (maxWidthText - calulateWidthWords(Font.ms_Fonts[_iFontSize], mCOMPOSE_TEXT[i + 1]));
	                    }
	                    posX = IniposX;
	                    pos_y += Font.ms_Fonts[_iFontSize].getHeight();
	                }
	                break;
	
	        	}
		        mPOS_Y_TEXT = pos_y;
		        g.setClip(0, 0, Settings.getInstance().getScreenWidth(), Settings.getInstance().getScreenHeight());
	    	}else{
	    		
	    	}
    }
    
    public static int getScreenWidthMarc(){
        return Settings.getInstance().getScreenWidth() - ((Settings.getInstance().getScreenWidth() * 10)/100);
    }
    
    public static int getNumberLines(Image font, String text, int rectangle_width) {
    	composeText(font, getArrayWords(text),rectangle_width);
    	if(mCOMPOSE_TEXT != null)
    		return mCOMPOSE_TEXT.length;
    	else return -1;
    }
    
    
}
