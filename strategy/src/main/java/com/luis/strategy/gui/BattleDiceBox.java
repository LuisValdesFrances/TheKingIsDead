package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GameState;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.game.GameUtils;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.Kingdom;

public class BattleDiceBox {
	
	private int state;
	private int stateCombat = 0;
	public static final int STATE_UNACTIVE = 0;
	public static final int STATE_START = 1;
	public static final int STATE_COMBAT_1 = 2;
	public static final int STATE_COMBAT_2 = 3;
	public static final int STATE_COMBAT_3 = 4;
	public static final int STATE_END = 5;
	
	
	private Button buttonCombat;
	
	private int shieldX;
	private int shieldY;
	private int parchmentX;
	private int parchmentY;
	private int shieldIconX;
	private int[] shieldIconY;
	private int totalHeight;
	
	private int modPosY;
	
	private int modPosDice;
	
	private int diceValue;
	private int diceDifficult;
	private boolean[] result;
	
	private boolean callToFX;

	private Army army;
	
	private ResultIconPropierties[] resultIcon;
	
	public BattleDiceBox() {

		int shieldSep = GfxManager.imgShieldIcon.getWidth()/2;
		int parchmentSep = -GfxManager.imgNotificationBox.getHeight()/3;
		int totalWidth = GfxManager.imgNotificationBox.getWidth() 
				+ GfxManager.imgShieldIcon.getWidth() + shieldSep;
		totalHeight = GfxManager.imgShield.getHeight() + 
				GfxManager.imgNotificationBox.getHeight() + parchmentSep;
		
		shieldX = Define.SIZEX2-totalWidth/2+GfxManager.imgNotificationBox.getWidth()/2;
		shieldY = (Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2) - totalHeight/2 + GfxManager.imgShield.getHeight()/2;
		parchmentX = shieldX;
		parchmentY = shieldY +GfxManager.imgShield.getHeight()/2 + parchmentSep + GfxManager.imgNotificationBox.getHeight()/2;
		
		shieldIconY = new int[4];
		
		int iconSep = (totalHeight - GfxManager.imgShieldIcon.getHeight()*4)/5;
		shieldIconX = parchmentX + GfxManager.imgNotificationBox.getWidth()/2 + shieldSep + GfxManager.imgShieldIcon.getWidth()/2;
		for(int i = 0; i < shieldIconY.length; i++){
			shieldIconY[i] = iconSep*(i+1) + GfxManager.imgShieldIcon.getHeight()*i + GfxManager.imgShieldIcon.getHeight()/2;
		}
		
		buttonCombat = new Button(
				GfxManager.imgButtonCombatRelease, GfxManager.imgButtonCombatFocus, 
				shieldIconX, 
				iconSep*shieldIconY.length + GfxManager.imgShieldIcon.getHeight()*(shieldIconY.length-1) + GfxManager.imgShieldIcon.getHeight()/2 , 
				null, -1){
			@Override
			public void onButtonPressDown() {
				super.onButtonPressDown();
				SndManager.getInstance().playFX(Main.FX_SWORD, 0);
			}
			
			@Override
			public void onButtonPressUp() {
				state++;
				onCombat();
				buttonCombat.reset();
				if(state >= STATE_COMBAT_1 && state <= STATE_COMBAT_3){
					modPosDice = -Define.SIZEX;
					stateCombat++;
					combat();
				}
			};
		};
	}
	
	private boolean autoPlay;
	public void start(
			Kingdom kingdom, 
			Army armyAtack, Army armyDefense, boolean autoPlay){
	    this.army = armyAtack;
		this.state = STATE_START;
		this.modPosY = -((Define.SIZEY-totalHeight)+totalHeight);
		this.modPosDice = -Define.SIZEX;
		this.diceDifficult = GameUtils.getInstance().calculateDifficult(
				kingdom, armyAtack, armyDefense);
		this.stateCombat = 0;
		this.autoPlay = autoPlay;
		
		result = new boolean[3];
		resultIcon = new ResultIconPropierties[3];
		for(int i = 0; i < resultIcon.length;i++){
			resultIcon[i] = new ResultIconPropierties();
		}
		combat();
	}
	
	private void combat() {
		callToFX = true;
		this.diceValue = getRandom();
		if(diceValue == GameParams.ROLL_SYSTEM){
			NotificationBox.getInstance().
			addMessage(RscManager.allText[RscManager.TXT_GAME_CRITICAL]);
			result[stateCombat] = true;
		}else if(diceValue == 1){
			NotificationBox.getInstance().
			addMessage(RscManager.allText[RscManager.TXT_GAME_BLUNDER]);
			result[stateCombat] = false;
		}else{
			result[stateCombat] = diceValue >= diceDifficult;
		}
	}

	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(state != STATE_UNACTIVE){
			switch(state){
			case STATE_START:
				modPosY -= (modPosY*8)*delta -1;
				if(modPosY >= 0){
					modPosY = 0;
					state = STATE_COMBAT_1;
				}
				break;
			case STATE_COMBAT_1:
			case STATE_COMBAT_2:
			case STATE_COMBAT_3:
				
				if(modPosDice < 0){
					modPosDice -= (modPosDice*8f)*delta - 1f;
				}else{
					for(int i = 0; i < resultIcon.length; i++){
						if(i <= stateCombat){
							if(resultIcon[i].modSize > 0.01f){
								resultIcon[i].modSize -= (resultIcon[i].modSize*8f)*delta;
								resultIcon[i].modAlpha = (int) ((resultIcon[i].modSize*255f)/ResultIconPropierties.MAX_SIZE);
							}
							if(resultIcon[i].modSize <= 0.01f){
								resultIcon[i].modAlpha = 0;
								resultIcon[i].modSize = 0f;
							}
						}
					}
					
					//buttonCombat.setDisabled(modPosDice < 0 || resultIcon[stateCombat].modSize > 0);
					if(callToFX){
						callToFX = false;
						if(result[stateCombat]){
							SndManager.getInstance().playFX(Main.FX_SWORD_STRONG, 0);
						}else{
							SndManager.getInstance().playFX(Main.FX_SWORD_BLOOD, 0);
						}
						
						
						if(state == STATE_COMBAT_3){
							buttonCombat.setImgRelese(GfxManager.imgButtonOkRelease);
							buttonCombat.setImgFocus(GfxManager.imgButtonOkFocus);
						}
					}
				}
				
				if(autoPlay && resultIcon[stateCombat].modAlpha == 0 && modPosDice == 0){
					buttonCombat.trigger();
				}
				
				buttonCombat.setDisabled(autoPlay  || resultIcon[stateCombat].modAlpha != 0 || modPosDice != 0);
				buttonCombat.update(touchHandler);
				
				break;
			case STATE_END:
				modPosY += (modPosY*16f)*delta + 1f;
				if(modPosY >= ((Define.SIZEY-totalHeight)+totalHeight)){
					modPosY = Define.SIZEY;
					state = STATE_UNACTIVE;
				}
				break;
			}
			return true;
		}else{
			return false;
		}
	}
	
	public void draw(Graphics g){
		if(state != STATE_UNACTIVE){
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			int m = ((Define.SIZEY-totalHeight)+totalHeight);
			int modAlpha = (int) ((Math.abs(modPosY) * MenuElement.bgAlpha) / m);
			g.setAlpha(MenuElement.bgAlpha-modAlpha);
			g.drawImage(GfxManager.imgBlackBG, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
			g.setAlpha(255);
			
			int modY = state==STATE_END?modPosY*-1:modPosY;
			
			g.drawImage(GfxManager.imgNotificationBox, 
					parchmentX, 
					parchmentY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			TextManager.drawSimpleText(g, Font.FONT_BIG, 
					RscManager.allText[RscManager.TXT_GAME_DIFFICULTY] + " "+ diceDifficult, 
					parchmentX, parchmentY + modY, Graphics.VCENTER | Graphics.HCENTER);
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			
			g.drawImage(GfxManager.imgShield, 
					shieldX, 
					shieldY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			g.drawImage(GfxManager.imgRollList.get(diceValue-1), 
					shieldX + modPosDice, 
					shieldY + modY, 
					Graphics.VCENTER | Graphics.HCENTER);
			
			for(int i = 0;i < shieldIconY.length-1; i++){
				g.drawImage(GfxManager.imgShieldIcon, 
						shieldIconX, 
						shieldIconY[i] + modY, 
						Graphics.VCENTER | Graphics.HCENTER);
				
				if(state >= STATE_COMBAT_1){
					if(i <= stateCombat){
						g.setAlpha(255-resultIcon[i].modAlpha);
						g.setImageSize(
								1f+resultIcon[i].modSize, 
								1f+resultIcon[i].modSize);
						g.drawImage(
							result[i]?GfxManager.imgOkIcon:GfxManager.imgCrossIcon, 
							shieldIconX, 
							shieldIconY[i] + modY, 
							Graphics.VCENTER | Graphics.HCENTER);
					}
				}
				g.setAlpha(255);
				g.setImageSize(1, 1);
			}
			
			buttonCombat.draw(g, 0, modY);
		}
	}
	
	
	public void onCombat(){}
	
	public void onResult(){}
	
	class ResultIconPropierties{
		
		public static final float MAX_SIZE = 16f;
		public float modSize;
		public int modAlpha;
		
		public ResultIconPropierties(){
			this.modSize = MAX_SIZE+1;
			this.modAlpha = 255;
		}
	}
	
	public int getResult(){
		int r = 0;
		for(int i = 0; i < this.result.length; i++){
			if(result[i])
				r++;
		}
		return r;
	}

	private int getRandom(){
		//return Main.getRandom(1, GameParams.ROLL_SYSTEM);
        if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE) {
            long seed = army.getSeed() * (army.getKingdom().getId() + 1) * (stateCombat + 1);
            return Main.getRandom(seed, 1, GameParams.ROLL_SYSTEM);
        }else{
            return Main.getRandom(1, GameParams.ROLL_SYSTEM);
        }

	}
	

}
