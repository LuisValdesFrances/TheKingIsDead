package com.luis.lgameengine.gameutils.gameworld;

import com.luis.lgameengine.implementation.graphics.Graphics;



public class ParticleManager {
	
	private static ParticleManager instance;
	public static ParticleManager getInstance(){
		if(instance == null){
			instance = new ParticleManager();
		}
		return instance;
	}
	
	
	
	//Particles
	public static final int CAPACITY = 64; 
	public static final int NUMBER_PARTICLES = 8;
	
	private static float[][][] m_bParticleData;
	private static final int POS_X = 0;
	private static final int POS_Y = 1;
	private static final int POS_ANGLE = 2;
	
	private static final int POS_SPEED_X = 3;
	private static final int POS_SPEED_Y = 4;
	
	private static final int POS_GRAVITY = 5;
	private static final int POS_WEIGTH = 6;
	
	private static final int POS_COLOR = 7;
	private static final int POS_CURRENT_WIDTH = POS_COLOR+1;
	private static final int POS_CURRENT_HEIGHT = POS_CURRENT_WIDTH+1;
	private static final int POS_TOTAL_WIDTH = POS_CURRENT_HEIGHT+1;
	private static final int POS_TOTAL_HEIGHT = POS_TOTAL_WIDTH+1;
	private static final int POS_ALPHA = POS_TOTAL_HEIGHT+1;
	private static final int POS_CURRENT_DURATION = POS_ALPHA+1;
	private static final int POS_TOTAL_DURATION = POS_CURRENT_DURATION+1;
	
	
	public static final int COL_LEFT = 0;
	public static final int COL_RIGHT = 1;
	public static final int COL_UP = 2;
	public static final int COL_DOWN = 3;
	public static final int COL_CENTER = -1;
	
	
	public void createParticles(
			int quantity,
			float gravity,
			float posX, 
			float posY, 
			float weigth,
			float speed, 
			int width, int height, 
			int _iType,//La por el que se produce la colisi�n. COL_CENTER estalla desde el centro
			int[] colorList,
			float duration,
			boolean _isSymetricPosAngle,//Si es true, las particulas se reparten en la misma cantidad a los largo de la descripci�n del �ngulo.
			boolean _isSymetricSize){//Tama�o de los fragmentos
		
		if(m_bParticleData == null){
			m_bParticleData = new float[CAPACITY][NUMBER_PARTICLES][POS_TOTAL_DURATION+1];
		}
		
		for(int q = 0; q < quantity; q++){
		
		
		//Buscamos un weco vacio:
		for(int i = 0;i< m_bParticleData.length;i++){
			if(m_bParticleData[i][0][POS_ALPHA]==0){
				
				for(int j = 0; j < NUMBER_PARTICLES; j++){
					m_bParticleData[i][j][POS_X] =  posX;
					m_bParticleData[i][j][POS_Y] =  posY;
					
					switch(_iType){
					
					case COL_RIGHT:
						if(GfxEffects.getInstance().isModule(j)) m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(0, 90);
						else m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(270, 360);
							
						break;
					case COL_UP:
						if(GfxEffects.getInstance().isModule(j)) m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(0, 90);
						else m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(90, 180);
						break;
					case COL_LEFT:
						if(GfxEffects.getInstance().isModule(j)) m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(90, 180);
						else m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(180, 270);
						break;
						
					case COL_DOWN:
						if(GfxEffects.getInstance().isModule(j)) m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(180, 270);
						else m_bParticleData[i][j][POS_ANGLE] =  GfxEffects.getInstance().getRandom(270, 320);
						break;
						
					case COL_CENTER:
							m_bParticleData[i][j][POS_ANGLE] =  ((360/NUMBER_PARTICLES)*(j+1))%360;
					break;
					}
					
					m_bParticleData[i][j][POS_GRAVITY] = gravity;
					m_bParticleData[i][j][POS_WEIGTH] = weigth;
					
					float speedX = _isSymetricPosAngle?
							speed:GfxEffects.getInstance().getRandom((int)speed/2, (int)speed);
				    float speedY = _isSymetricPosAngle?
				    		speed:GfxEffects.getInstance().getRandom((int)speed/2, (int)speed);
				    
				    float angleToRadiants = (float)(m_bParticleData[i][j][POS_ANGLE] * Math.PI) / 180f;
					
					float cos = (float) Math.cos(angleToRadiants);
					float sin = (float) Math.sin(angleToRadiants);
					
					m_bParticleData[i][j][POS_SPEED_X] = cos*speedX;
					m_bParticleData[i][j][POS_SPEED_Y] = sin*speedY;
							
					int index = j%colorList.length;
					m_bParticleData[i][j][POS_COLOR] =  colorList[index];
					
					m_bParticleData[i][j][POS_TOTAL_WIDTH] = _isSymetricSize?width: 
						GfxEffects.getInstance().getRandom(width - (width/2), width + (width/2));
					m_bParticleData[i][j][POS_TOTAL_HEIGHT] = _isSymetricSize?height: 
						GfxEffects.getInstance().getRandom(height - (height/2), height + (height/2));
					
					m_bParticleData[i][j][POS_CURRENT_WIDTH] = m_bParticleData[i][j][POS_TOTAL_WIDTH];
					m_bParticleData[i][j][POS_CURRENT_HEIGHT] = m_bParticleData[i][j][POS_TOTAL_HEIGHT];
					
				    m_bParticleData[i][j][POS_ALPHA] =  255;
				    m_bParticleData[i][j][POS_CURRENT_DURATION] =  0;
				    m_bParticleData[i][j][POS_TOTAL_DURATION] = duration;
				}
				i = m_bParticleData.length;
			}
		}
		}
	}

	public void update(float deltaTime){
		if(m_bParticleData != null){
			
			float speedX, speedY;
			
			for(int i = 0;i< m_bParticleData.length;i++){
				
				if(m_bParticleData[i][0][POS_CURRENT_DURATION] < m_bParticleData[i][0][POS_TOTAL_DURATION]){
					
					
					for(int j = 0; j < NUMBER_PARTICLES; j++){
						
						m_bParticleData[i][j][POS_CURRENT_DURATION] += deltaTime;
						float dif = m_bParticleData[i][j][POS_TOTAL_DURATION]-m_bParticleData[i][j][POS_CURRENT_DURATION];
						
						speedX = m_bParticleData[i][j][POS_SPEED_X];
						speedY = m_bParticleData[i][j][POS_SPEED_Y];
						
						
						float cAcceleration = m_bParticleData[i][j][POS_GRAVITY] * m_bParticleData[i][j][POS_WEIGTH];
						
						/**Gravity force*/
						speedY += (cAcceleration * deltaTime);
						float movement = speedY * deltaTime;
						m_bParticleData[i][j][POS_SPEED_Y] = speedY;
						m_bParticleData[i][j][POS_Y] += movement;
						
						/**Force x */
						float rest = (cAcceleration * deltaTime);
						rest = rest / 4f;
						if (speedX > 0){
			                speedX -= rest;
			                if (speedX < 0) speedX = 0;
			            }
			            else{
			                speedX += rest;
			                if (speedX > 0) speedX = 0;
			            }
			            float mov = speedX * deltaTime;
			            m_bParticleData[i][j][POS_SPEED_X] = speedX;
			            m_bParticleData[i][j][POS_X] += mov;
						//
			            
								
						m_bParticleData[i][j][POS_CURRENT_WIDTH] =(dif*m_bParticleData[i][j][POS_TOTAL_WIDTH]) / m_bParticleData[i][j][POS_TOTAL_DURATION];
						m_bParticleData[i][j][POS_CURRENT_HEIGHT] =(dif*m_bParticleData[i][j][POS_TOTAL_HEIGHT]) / m_bParticleData[i][j][POS_TOTAL_DURATION];
						m_bParticleData[i][j][POS_ALPHA] =(dif*255) / m_bParticleData[i][j][POS_TOTAL_DURATION];
					}
					
				}else{
					m_bParticleData[i][0][POS_ALPHA] = 0;
				}
			}
			
		}
	}
	
	public void draw(Graphics g, WorldConver worldConver, GameCamera camera){
		if(m_bParticleData != null){
			g.setClip(0, 0, (int)worldConver.getLayoutX(), (int)worldConver.getLayoutY());
			for(int i = 0;i< m_bParticleData.length;i++){
				if(m_bParticleData[i][0][POS_ALPHA]>1){
					for(int j = 0; j < NUMBER_PARTICLES; j++){
						g.setColor((int)m_bParticleData[i][j][POS_COLOR]);
						g.setAlpha((int)m_bParticleData[i][j][POS_ALPHA]);
						int x = worldConver.getConversionDrawX(camera.getPosX(), ((int)m_bParticleData[i][j][POS_X]));
						int y = worldConver.getConversionDrawY(camera.getPosY(), ((int)m_bParticleData[i][j][POS_Y]));
						g.fillRect(x, 
								   y, 
								   (int)m_bParticleData[i][j][POS_CURRENT_WIDTH], 
								   (int)m_bParticleData[i][j][POS_CURRENT_HEIGHT]);
					}
				}
			}
			g.setAlpha(255);
		}
	}
	
	public boolean isPartRunning(){
		if(m_bParticleData == null){
			return false;
		}else{
			for(int i = 0;i< m_bParticleData.length;i++){
				if(m_bParticleData[i][0][POS_ALPHA]>0){
					return true;
				}
			}
			return false;
		}
		
	}
}