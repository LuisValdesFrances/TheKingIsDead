package com.luis.lgameengine.gui;

import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.input.TouchData;


/**
 * @author Luis Valdes Frances
 */
public class Button extends MenuElement {

    protected String text;
    protected int fontType;

    protected Image imgRelese;
    protected Image imgFocus;

    protected boolean isTouching;

    protected boolean disabled;

    protected boolean ignoreAlpha;


    public Button(Image imgRelease, Image imgFocus, int x, int y, String text, int fontType) {
        super(0, 0);
        this.imgRelese = imgRelease;
        this.imgFocus = imgFocus;
        this.width = imgRelease.getWidth();
        this.height = imgRelease.getHeight();
        this.text = text;
        this.fontType = fontType;
        setX(x);
        setY(y);
    }

    public Button(int w, int h, int x, int y) {
        super(0, 0);
        this.width = w;
        this.height = h;
        setX(x);
        setY(y);
    }

    public boolean update(MultiTouchHandler touchHandler) {
        if (trigger) {
            trigger = false;
            onButtonPressUp();
            reset();
            return true;
        }

        if (!disabled) {
            if (isTouching) {
                if (!compareTouch(touchHandler)) {
                    isTouching = false;
                } else {
                    if (touchHandler.getTouchAction(0) == TouchData.ACTION_UP) {
                        onButtonPressUp();
                        return true;
                    }
                }
            } else {
                if (compareTouch(touchHandler) &&
                        (touchHandler.getTouchAction(0) == TouchData.ACTION_DOWN || touchHandler.getTouchAction(0) == TouchData.ACTION_MOVE)) {
                    if (!isTouching) {
                        isTouching = true;
                        onButtonPressDown();
                    }
                }
            }
        }
        return false;
    }

    public void reset() {
        trigger = false;
        isTouching = false;
    }

    public void draw(Graphics g, int modX, int modY) {
        draw(g, modX, modY, 1f);
    }

    public void draw(Graphics g, int modX, int modY, float resize) {
        boolean isAlpha = alpha != 255;
        if (isAlpha && disabled && !ignoreAlpha) {
            g.setAlpha(alpha);
        }

        if (isTouching) {
            g.setImageSize(resize, resize);
        }

        g.drawImage(isTouching || (disabled && !isAlpha) && (!isAlpha && !isDisabled()) ? imgFocus : imgRelese,
                getX() + modX,
                getY() + modY, Graphics.VCENTER | Graphics.HCENTER);

        g.setImageSize(1f, 1f);

        if (text != null) {
            TextManager.drawSimpleText(g, fontType, text,
                    getX() + modX,
                    getY() + modY,
                    Graphics.VCENTER | Graphics.HCENTER);
        }

        if (isAlpha && disabled && !ignoreAlpha) {
            g.setAlpha(255);
        }
    }

    private boolean compareTouch(MultiTouchHandler touchHandler) {
        return (
                touchHandler.getTouchX(0) > getX() - width / 2 &&
                        touchHandler.getTouchX(0) < getX() + width / 2 &&
                        touchHandler.getTouchY(0) > getY() - height / 2 &&
                        touchHandler.getTouchY(0) < getY() + height / 2);
    }

    public void onButtonPressDown() {
    }

    public void onButtonPressUp() {
    }

    private boolean trigger;

    public void trigger() {
        trigger = true;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFontType() {
        return fontType;
    }

    public void setFontType(int fontType) {
        this.fontType = fontType;
    }

    public Image getImgRelese() {
        return imgRelese;
    }

    public void setImgRelese(Image imgRelese) {
        this.imgRelese = imgRelese;
    }

    public Image getImgFocus() {
        return imgFocus;
    }

    public void setImgFocus(Image imgFocus) {
        this.imgFocus = imgFocus;
    }

    public boolean isTouching() {
        return isTouching;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isIgnoreAlpha() {
        return ignoreAlpha;
    }

    public void setIgnoreAlpha(boolean ignoreAlpha) {
        this.ignoreAlpha = ignoreAlpha;
    }


}
