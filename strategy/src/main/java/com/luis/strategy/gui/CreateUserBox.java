package com.luis.strategy.gui;


import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.Keyboard;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;



public class CreateUserBox extends MenuBox{
	
	private Keyboard keyboard;
	
	private String textName;
	private String textPassword;
	private String textRepPassword;
	
	private int inputPointer;
	private int newInputPointer;
	
	private int lineNamePosY;
	private int linePassPosY;
	private int lineRepPassPosY;
	
	private Button inputBoxName;
	private Button inputBoxPass;
	private Button inputBoxRepPass;
	
	private Button btnOk;
	
	private int labelX;

	public CreateUserBox() {
		super(
				Define.SIZEX, Define.SIZEY, 
				null,
				null, 
				null,
				Define.SIZEX2, Define.SIZEY2,
				null, null,
				-1, -1, -1, Main.FX_NEXT);
		keyboard = new Keyboard(
				Define.SIZEX2, 
				Define.SIZEY-GfxManager.imgButtonKeyboardRelease.getHeight()*2, 
				GfxManager.imgButtonKeyboardRelease, GfxManager.imgButtonKeyboardFocus, 
				GfxManager.imgButtonKeyboardReleaseSp, GfxManager.imgButtonKeyboardFocusSp,
				Font.FONT_BIG, Font.FONT_SMALL, Define.MAX_NAME_CHAR,
				Main.FX_SELECT){
			
			@Override
			public void onButtonPressDown() {}
			@Override
			public void onButtonPressUp() {}
		};
		
		
		textName = new String("");
		textPassword = new String("");
		textRepPassword = new String("");
		
		keyboard.setTextChain(textName);
		
		
		
		int sepW = Define.SIZEX64;
		int formWidth = GfxManager.imgInputBox.getWidth() + sepW + Font.getFontWidth(Font.FONT_MEDIUM)*RscManager.allText[RscManager.TXT_REPEAT_PASS].length();
		int inputX = Define.SIZEX2 + formWidth/2 - GfxManager.imgInputBox.getWidth()/2;
		labelX = Define.SIZEX2 - formWidth/2;
		
		int sepH = Define.SIZEY64;
		int formHeight = GfxManager.imgInputBox.getHeight()*3 + sepH*2;
		int centerForm = (Define.SIZEY-keyboard.getHeight())/2;
		
		lineNamePosY = centerForm - formHeight/2 +  GfxManager.imgInputBox.getHeight()/2;
		linePassPosY = lineNamePosY + GfxManager.imgInputBox.getHeight() + sepH;
		lineRepPassPosY = linePassPosY + GfxManager.imgInputBox.getHeight() + sepH;
		
		inputBoxName = new Button(
				GfxManager.imgInputBox, GfxManager.imgInputBox, inputX, lineNamePosY, null, -1){
			@Override
			public void onButtonPressUp() {
				super.onButtonPressUp();
				reset();
				newInputPointer = 0;
				if(newInputPointer == inputPointer){
					return;
				}
				switch(inputPointer){
				case 1:
					textPassword = keyboard.getTextChain();
					break;
				case 2:
					textRepPassword = keyboard.getTextChain();
					break;
				}
				inputPointer = newInputPointer;
				keyboard.setTextChain(textName);
			}
		};
		inputBoxPass = new Button(
				GfxManager.imgInputBox, GfxManager.imgInputBox, inputX, linePassPosY, null, -1){
			@Override
			public void onButtonPressUp() {
				super.onButtonPressUp();
				reset();
				newInputPointer = 1;
				if(newInputPointer == inputPointer){
					return;
				}
				switch(inputPointer){
				case 0:
					textName = keyboard.getTextChain();
					break;
				case 2:
					textRepPassword = keyboard.getTextChain();
					break;
				}
				inputPointer = newInputPointer;
				keyboard.setTextChain(textPassword);
			}
		};
		inputBoxRepPass = new Button(
				GfxManager.imgInputBox, GfxManager.imgInputBox, inputX, lineRepPassPosY, null, -1){
			@Override
			public void onButtonPressUp() {
				super.onButtonPressUp();
				reset();
				newInputPointer = 2;
				if(newInputPointer == inputPointer){
					return;
				}
				switch(inputPointer){
				case 0:
					textName = keyboard.getTextChain();
					break;
				case 1:
					textPassword = keyboard.getTextChain();
					break;
				}
				inputPointer = newInputPointer;
				keyboard.setTextChain(textRepPassword);
			}
		};
		
		
		btnOk = new Button(GfxManager.imgButtonArrowNextRelease, GfxManager.imgButtonArrowNextFocus,
				Define.SIZEX - Define.SIZEX32-GfxManager.imgButtonArrowBackRelease.getWidth()/2,
				Define.SIZEY - Define.SIZEY32-GfxManager.imgButtonArrowBackRelease.getHeight()/2,
				null, -1){
			@Override
			public void onButtonPressUp() {
				onSendForm();
			};
		};
		
		keyboard.setModY(Define.SIZEY);
	}
	
	
	public void onSendForm() {
		btnOk.reset();
	};
	
	
	public boolean update(MultiTouchHandler touchHandler, float delta){
		
		if(state ==  STATE_TO_ACTIVE){
			keyboard.setModY(modPosX*-1/2);
		}
		else if(state ==  STATE_TO_UNACTIVE){
			keyboard.setModY(modPosX/2);
		}
		
		if(state == STATE_ACTIVE){
			keyboard.update(touchHandler);
			inputBoxName.update(touchHandler);
			inputBoxPass.update(touchHandler);
			inputBoxRepPass.update(touchHandler);
			btnOk.update(touchHandler);
		}
		
		if(
				textName.length() < Define.MIN_NAME_CHAR || 
				textPassword.length() < Define.MIN_PASSWORD_CHAR || 
				textRepPassword.length() < Define.MIN_PASSWORD_CHAR){
			btnOk.setDisabled(true);
			
		}else{
			btnOk.setDisabled(false);
		}
		
		
		return super.update(touchHandler, delta);
	}
	
	public void draw(Graphics g){
		TextManager.drawSimpleText(g, Font.FONT_MEDIUM, RscManager.allText[RscManager.TXT_NAME],
				labelX+(int)modPosX, lineNamePosY, 
				Graphics.VCENTER | Graphics.LEFT);
		TextManager.drawSimpleText(g, Font.FONT_MEDIUM, RscManager.allText[RscManager.TXT_PASS], 
				labelX+(int)modPosX, linePassPosY, 
				Graphics.VCENTER | Graphics.LEFT);
		TextManager.drawSimpleText(g, Font.FONT_MEDIUM, RscManager.allText[RscManager.TXT_REPEAT_PASS], 
				labelX+(int)modPosX, lineRepPassPosY, 
				Graphics.VCENTER | Graphics.LEFT);
		
		inputBoxName.draw(g, (int)modPosX, 0);
		inputBoxPass.draw(g, (int)modPosX, 0);
		inputBoxRepPass.draw(g, (int)modPosX, 0);
		
		if(Main.iFrame%20<10){
			int textW = Font.getFontWidth(Font.FONT_SMALL)*(keyboard.getTextChain().length()+1);
			switch(inputPointer){
			case 0:
				textName = keyboard.getTextChain();
				g.drawImage(GfxManager.imgTextPointer, 
						inputBoxName.getX() + textW/2+(int)modPosX, lineNamePosY, 
						Graphics.VCENTER | Graphics.HCENTER);
				break;
			case 1:
				textPassword = keyboard.getTextChain();
				g.drawImage(GfxManager.imgTextPointer, 
						inputBoxName.getX() + textW/2+(int)modPosX, linePassPosY, 
						Graphics.VCENTER | Graphics.HCENTER);
				break;
			case 2:
				textRepPassword = keyboard.getTextChain();
				g.drawImage(GfxManager.imgTextPointer, 
						inputBoxName.getX() + textW/2+(int)modPosX, lineRepPassPosY, 
						Graphics.VCENTER | Graphics.HCENTER);
				break;
			}
		}
		
		TextManager.drawSimpleText(g, Font.FONT_SMALL, textName, 
				inputBoxName.getX()+(int)modPosX, lineNamePosY, 
				Graphics.VCENTER | Graphics.HCENTER);
		String pass = "";
		for(int i = 0; i < textPassword.length(); i++){
			pass+="*";
		}
		TextManager.drawSimpleText(g, Font.FONT_SMALL, pass, 
				inputBoxPass.getX()+(int)modPosX, linePassPosY, 
				Graphics.VCENTER | Graphics.HCENTER);
		pass = "";
		for(int i = 0; i < textRepPassword.length(); i++){
			pass+="*";
		}
		TextManager.drawSimpleText(g, Font.FONT_SMALL, pass, 
				inputBoxRepPass.getX()+(int)modPosX, lineRepPassPosY, 
				Graphics.VCENTER | Graphics.HCENTER);
		
		
		
		keyboard.draw(g);
		btnOk.draw(g, 0, 0);
	}

	


	public String getTextName() {
		return textName;
	}


	public String getTextPassword() {
		return textPassword;
	}


	public String getTextRepPassword() {
		return textRepPassword;
	}


	public void setTextRepPassword(String textRepPassword) {
		this.textRepPassword = textRepPassword;
	}
	
	

}
