package com.luis.lgameengine.gui;


import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;

/**
 *
 * @author Luis Valdes Frances
 */
public class MenuManager {
	/**
    Esta clase es capaz de montar los menus, botones, cajas y texto partiendo de la anchura y la altura de la pantalla.
    Para que esta funcione, las imagenes de los botones y los sofkeys deben de se seguir un patron.
    La clase es capaz de hacer lo siguiente:
    -Pintar los softkeys en sus respectivos lugares.
    -Pintar botones alienados verticalemente y pintar texto centrado en ellos (Botones eje Y)
    -Pintar un unico boton, con dos pestanyas a cada lado con las cuales cambiamos de opcion (Bototnes en el eje X)
    
    Softkeys: Una imagen con los cuatro softkits, dos arriba y dos abajo de la siguiente manera:
    SELECT-UNSELECT
    SELECT-UNSELECT
    
    Botones horizontales-Y: Una imagen que contiene boton seleccionado y deseleccioando:
    SELECCIONADO
    DESELECCIONADO
    Velocidad de actualizacion de los botones seleccionados.
    */
    private static boolean isFocusY;
    private static int iLastFocus;
    public static int iSepBottonsX;
    private static int iSepBottonsY;
    public static int[] iListPosY;
    private static int[] iPosTextY;
    private static int iPosY;
    public static int iPosX;
    private static int iButtonHeight;
    private static int iReferenceButtonCent;
    
    private static final int UPDATE_BUTTON_COUNT = 16;
    
    //Alineaci�n de los botones verticales:
    public static final int BUTTON_UP = 0;
    public static final int BUTTON_CENTER = 1;
    public static final int BUTTON_DOWN = 2;
    
    //private static int buttonW;
	private static int buttonH;
	//private static int softkeyW;
	private static int softkeyH;
	//private static int arrowW;
	private static int arrowH;

	/**
	 * Inicia un menu complento con botones y sofkeys, todos del mismo tama�o
	 * @param buttonW Anchura del boton
	 * @param buttonH Altura del boton
	 * @param softkeyW Anchura del sofkey
	 * @param softkeyH Altura del sofkey
	 * @param arrowW Anchura del la flecha para men�s de orden horizontal
	 * @param arrowH Anchura del la flecha para men�s de orden horizontal
	 */
    public static void init(int buttonW, int buttonH, int softkeyW, int softkeyH, int arrowW, int arrowH) {
    	//MenuManager.buttonW = (int)buttonW;
    	MenuManager.buttonH = (int)buttonH;
    	//MenuManager.softkeyW = (int)softkeyW;
    	MenuManager.softkeyH = (int)softkeyH;
    	//MenuManager.arrowW = (int)arrowW;
    	MenuManager.arrowH = (int)arrowH;
    	isFocusY = true;
        iLastFocus = 0;
        
        int sizeY = Settings.getInstance().getScreenHeight() - (MenuManager.softkeyH >> 1);
        int center_screen = sizeY>>1;
        
        iSepBottonsX = (center_screen >> 1) + (MenuManager.buttonH >> 2);
        iSepBottonsY = MenuManager.buttonH >> 2;
        iReferenceButtonCent = 0;
    }
    
    
    //La operacion %2 devuelve 0 (true) cuando es par y 1 (false) cuando es impar.
    private static boolean module(int number) {
        return (number % 2) == 0;
    }
    
    /**
     * Pinta un menu de orden vertical
     * @param g Objeto donde se va a pintar
     * @param number_options Numero de opciones del men�
     * @param pos_text N�mero de la primera l�nea, en el archivo .txt, donde se encuentra el texto deseado
     * @param allTexts Array con todos los textos del juego
     * @param _iFontType Fuente del texto: 0 small, 1 big
     * @param select_option Opci�n seleccionada por defecto
     * @param softkeys Imagen de los softkeys
     * @param button Imagen de los botones
     * @param frame Frame actual
     */
    public static void drawButtonsAndTextY(Graphics g, int number_options, int pos_text, String[] allTexts,
    		int _iFontType, int select_option, Image softkeys, Image button, int frame) {
        //Obtenemos las posicion X/Y de cada bot�n partiendo de que se pintar�n cogiendo como
        //referencia arriba-izquierda:
        iListPosY = new int[number_options];

        if(softkeys != null)
        iButtonHeight = Settings.getInstance().getScreenHeight() - (softkeys.getHeight() >>1);
        else
        iButtonHeight = Settings.getInstance().getScreenHeight();

        //comprobamos si el numero de botones es par o impar:
        //Si es par:
        if (module(iListPosY.length)) {
            iReferenceButtonCent = ((iListPosY.length)>>1) - 1;
            iListPosY[iReferenceButtonCent] = (iButtonHeight >>1) - ((button.getHeight() >>1) + (iSepBottonsY >>1));
            //Si es impar:
        } else {
            iReferenceButtonCent = (iListPosY.length >>1);//posicion en el array del boton central
            iListPosY[iReferenceButtonCent] = (iButtonHeight >>1) - (button.getHeight() >> 2);
        }

        //Colocamos desde boton central hacia abajo:
        for (int i = iReferenceButtonCent + 1; i < iListPosY.length; i++) {
            iListPosY[i] = iListPosY[i - 1] + ((button.getHeight() >>1) + iSepBottonsY);
        }

        //Colocamos del bototn central hacia arriba:
        for (int i = iReferenceButtonCent - 1; i > -1; i--) {
            iListPosY[i] = iListPosY[i + 1] - ((button.getHeight() >>1) + iSepBottonsY);
        }
        iPosX = (Settings.getInstance().getScreenWidth() / 2) - (button.getWidth() >>1);
        changeButtonFocus(select_option, frame);

        //Pintamos los botones:
        for (int i = 0; i < number_options; i++) {
            //g.setClip(iPosX, iListPosY[i], button.getWidth(), (button.getHeight()>>1));

            if (i == select_option) {
                if (isFocusY) {
                    g.drawImage(button, iPosX, iListPosY[i] - (button.getHeight() >>1),
                            Graphics.TOP | Graphics.LEFT);
                } else {
                    g.drawImage(button, iPosX, iListPosY[i], Graphics.TOP | Graphics.LEFT);
                }
            } else {
            	g.drawImage(button, iPosX, iListPosY[i], Graphics.TOP | Graphics.LEFT);
            }
        }
        drawTextButtonY(g, selectText(allTexts, pos_text, number_options), iListPosY, _iFontType, button);
    }
    
    
    
    /**
     * Pinta un menu de orden vertical
     * @param g Objeto donde se va a pintar
     * @param number_options Numero de opciones del men�
     * @param pos_text N�mero de la primera l�nea, en el archivo .txt, donde se encuentra el texto deseado
     * @param texts Array con los textos del menu
     * @param _iFontType Fuente del texto: 0 small, 1 big
     * @param select_option Opci�n seleccionada por defecto
     * @param softkeys Imagen de los softkeys
     * @param button Imagen de los botones
     * @param frame Frame actual
     */
    public static void drawButtonsAndTextY(Graphics g, int number_options, String[] texts,
    		int _iFontType, int select_option, Image softkeys, Image button, int frame) {
        //Obtenemos las posicion X/Y de cada bot�n partiendo de que se pintar�n cogiendo como
        //referencia arriba-izquierda:
        iListPosY = new int[number_options];

        if(softkeys != null)
        iButtonHeight = Settings.getInstance().getScreenHeight() - (softkeys.getHeight() >>1);
        else
        iButtonHeight = Settings.getInstance().getScreenHeight();

        //comprobamos si el numero de botones es par o impar:
        //Si es par:
        if (module(iListPosY.length)) {
            iReferenceButtonCent = ((iListPosY.length)>>1) - 1;
            iListPosY[iReferenceButtonCent] = (iButtonHeight >>1) - ((button.getHeight() >>1) + (iSepBottonsY >>1));
            //Si es impar:
        } else {
            iReferenceButtonCent = (iListPosY.length >>1);//posicion en el array del boton central
            iListPosY[iReferenceButtonCent] = (iButtonHeight >>1) - (button.getHeight() >> 2);
        }

        //Colocamos desde boton central hacia abajo:
        for (int i = iReferenceButtonCent + 1; i < iListPosY.length; i++) {
            iListPosY[i] = iListPosY[i - 1] + ((button.getHeight() >>1) + iSepBottonsY);
        }

        //Colocamos del bototn central hacia arriba:
        for (int i = iReferenceButtonCent - 1; i > -1; i--) {
            iListPosY[i] = iListPosY[i + 1] - ((button.getHeight() >>1) + iSepBottonsY);
        }
        iPosX = (Settings.getInstance().getScreenWidth() / 2) - (button.getWidth() >>1);
        changeButtonFocus(select_option, frame);

        //Pintamos los botones:
        for (int i = 0; i < number_options; i++) {
            //g.setClip(iPosX, iListPosY[i], button.getWidth(), (button.getHeight()>>1));

            if (i == select_option) {
                if (isFocusY) {
                    g.drawImage(button, iPosX, iListPosY[i] - (button.getHeight() >>1),
                            Graphics.TOP | Graphics.LEFT);
                } else {
                    g.drawImage(button, iPosX, iListPosY[i], Graphics.TOP | Graphics.LEFT);
                }
            } else {
            	g.drawImage(button, iPosX, iListPosY[i], Graphics.TOP | Graphics.LEFT);
            }
        }
        drawTextButtonY(g, texts, iListPosY, _iFontType, button);
    }
    
    
    

    private static void drawTextButtonY(Graphics g, String[] text, int[] pos_y, int _iFontType, Image button) {
        //Modificamos la posY para pintar cogiendo de referencia de la imagen el 
        //centro derecha en vez de arriba izquierda (bot�n):

        iPosTextY = pos_y;
        for (int i = 0; i < pos_y.length; i++) {
            iPosTextY[i] += (button.getHeight() >>2);
        }
        for (int i = 0; i < text.length; i++) {
	        TextManager.drawSimpleText(g, _iFontType, text[i],
	        Settings.getInstance().getScreenWidth() / 2, iPosTextY[i], Graphics.VCENTER|Graphics.HCENTER);
        }
    }

    private static void drawTextButtonX(Graphics g, String[] text, int pos_y, int _iFontType,
            Image button, int select_option) {
        //Modificamos la posY para pintar cogiendo de referencia de la imagen el 
        //centro derecha en vez de arriba izquierda (bot�n):

        iPosY = pos_y + (button.getHeight()>>2);
        
        TextManager.drawSimpleText(g, _iFontType, text[select_option],
    	        Settings.getInstance().getScreenWidth() / 2, iPosY, 
    	        Graphics.VCENTER|Graphics.HCENTER);
    }
    
    private static void changeButtonFocus(int select_option, int frame) {
		if (frame % UPDATE_BUTTON_COUNT == 0
				|| iLastFocus != select_option) {
			if (!isFocusY || iLastFocus != select_option) {
				isFocusY = true;
				iLastFocus = select_option;
			} else {
				isFocusY = false;
			}
		}
	}

	private static boolean changeArrowsFocus(int frame) {
		if (frame % UPDATE_BUTTON_COUNT > UPDATE_BUTTON_COUNT>>1)
			return false;
		
		return true;
	}

    //Le pasamos el array de TODOS los textos del juego y seleccionamos aquellos
    //que usaremos en el men�:
    //text: array con todos los textos del juego.
    //pos_text: primera l�nea de inicio del texto.
    //num_options: n�mero de opciones.
    private static String[] selectText(String[] text, int pos_text, int num_options) {
        String[] sSelectText = new String[num_options];

        for (int i = 0; i < num_options; i++) {
            sSelectText[i] = text[pos_text];
            pos_text++;
        }
        return sSelectText;
    }
    
    /**
     * Pinta un menu de orden horizontal a partir de un array de String con todos los textos del juego. Solo se visualiza un boton a la vez
     * @param g Objeto donde se va a pintar
     * @param pos_screen Alineaci�n de los botones verticales. Por defecto es BUTTON_CENTER
     * @param number_options Numero de opciones del men�
     * @param pos_text N�mero de la primera l�nea, en el archivo .txt, donde se encuentra el texto deseado
     * @param allTexts Array con todos los textos del juego
     * @param _iFontType Fuente del texto: 0 small, 1 big
     * @param select_option Opci�n seleccionada por defecto
     * @param softkeys Imagen de los softkeys
     * @param button Imagen de los botones
     * @param arrow Imagen de las flechas
     * @param frame Frame actual
     */
    public static void drawButtonsAndTextX(Graphics g, int pos_screen, int number_options, int pos_text, String[] allTexts, 
            int _iFontType, int select_option, Image softkeys, Image button, Image arrows, int frame) {
        //Obtenemos las posicion X/Y de cada bot�n partiendo de que se pintar�n cogiendo como
        //referencia arriba-izquierda:
        iButtonHeight = Settings.getInstance().getScreenHeight() - (softkeys.getHeight()>>1);

        //Todos estos ajustes son relativos a como esten formadas las imagenes de los botones y los sofkeys.
        //Aqui se parte de la idea de partimos de una imagen con dos botones y otra con cuatro softkeys:
        iPosX = Settings.getInstance().getScreenWidth() / 2 - (button.getWidth()>>1);
        iPosY = (iButtonHeight>>1) - (button.getHeight()>>2);//Posicon central, por defecto.
        switch (pos_screen) {
        	case BUTTON_UP://Ariba
                iPosY -= iSepBottonsX;
                break;
            case BUTTON_DOWN://Abajo
                iPosY += iSepBottonsX;
                break;
        }

        //Pintamos el bot�n:
        //g.setClip(iPosX, iPosY, button.getWidth(), (button.getHeight()>>1));
        g.drawImage(button, iPosX, iPosY, Graphics.TOP | Graphics.LEFT);

        //Pintamos las pesta�as:
        if (changeArrowsFocus(frame)) {
       	   //Izquierda:
            /*
           g.setClip(  iPosX - (arrows.getWidth()>>1), 
           		iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
           		(arrows.getWidth()>>1), 
           		MenuManager.arrowH);
           	*/
           g.drawImage(arrows, iPosX - (arrows.getWidth()>>1), 
           		iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
                       Graphics.TOP | Graphics.LEFT);
           //Derecha:
            /*
           g.setClip(  iPosX + button.getWidth(), 
           		iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
           		(arrows.getWidth()>>1), 
           		MenuManager.arrowH);
           	*/
           g.drawImage(arrows, (iPosX + button.getWidth()) - (arrows.getWidth()>>1),
           		iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
                       Graphics.TOP | Graphics.LEFT);
          
       }
        drawTextButtonX(g, selectText(allTexts, pos_text, number_options), iPosY, _iFontType, button, select_option);
    }
    
    /**
     * Pinta un menu de orden horizontal a partir de un array de String con todos los textos del juego. Solo se visualiza un boton a la vez
     * @param g Objeto donde se va a pintar
     * @param pos_screen Alineaci�n de los botones verticales. Por defecto es BUTTON_CENTER
     * @param number_options Numero de opciones del men�
     * @param pos_text N�mero de la primera l�nea, en el archivo .txt, donde se encuentra el texto deseado
     * @param texts Array con los textos del menu
     * @param _iFontType Fuente del texto: 0 small, 1 big
     * @param select_option Opci�n seleccionada por defecto
     * @param softkeys Imagen de los softkeys
     * @param button Imagen de los botones
     * @param arrow Imagen de las flechas
     * @param frame Frame actual
     */
    public static void drawButtonsAndTextX(Graphics g, int _iFontType, int pos_screen, String[] texts,
    		int select_option, Image softkeys, Image button, Image arrows, int frame) {
    	//Obtenemos las posicion X/Y de cada bot�n partiendo de que se pintar�n cogiendo como
        //referencia arriba-izquierda:
        
    	iButtonHeight = Settings.getInstance().getScreenHeight() - (softkeys.getHeight()>>1);
    	iPosX = Settings.getInstance().getScreenWidth() / 2 - (button.getWidth()>>1);
        iPosY = (iButtonHeight>>1) - (button.getHeight()>>2);//Posicon central, por defecto.
        switch (pos_screen) {

             case BUTTON_UP://Ariba
                 iPosY -= iSepBottonsX;
                 break;
             case BUTTON_DOWN://Abajo
                 iPosY += iSepBottonsX;
                 break;
         }


        //Pintamos el bot�n:
        //g.setClip(iPosX, iPosY, button.getWidth(), (button.getHeight()>>1));
        g.drawImage(button, iPosX, iPosY, Graphics.TOP | Graphics.LEFT);

      
        //Pintamos las pesta�as:
        if (changeArrowsFocus(frame)) {
          	 //Izquierda:
              /*
              g.setClip(
                      iPosX - (arrows.getWidth()>>1), 
              	      iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
                      (arrows.getWidth()>>1), 
                      MenuManager.arrowH);
              */
              g.drawImage(arrows, iPosX - (arrows.getWidth()>>1), 
                      iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
                      Graphics.TOP | Graphics.LEFT);
              //Derecha:
              /*
              g.setClip(
                      iPosX + button.getWidth(), 
                      iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
                      (arrows.getWidth()>>1), 
                      MenuManager.arrowH);
              */
              g.drawImage(arrows, (iPosX + button.getWidth()) - (arrows.getWidth()>>1),
                      iPosY + (MenuManager.buttonH>>2) - (MenuManager.arrowH>>1), 
                      Graphics.TOP | Graphics.LEFT);
             
          }
        drawTextButtonX(g, texts, iPosY, _iFontType, button, select_option);
    }
}
