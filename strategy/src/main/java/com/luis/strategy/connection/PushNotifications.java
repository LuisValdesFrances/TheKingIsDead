package com.luis.strategy.connection;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.SceneListData;

public class PushNotifications extends Thread{
	
	private Context context;
	private String user;
	private int notificationId;
	private boolean active;
	
	private List<Notification> notificationList;
	
	public PushNotifications(Context context, String user) {
		this.context = context;
		this.user = user;
		this.notificationList = new ArrayList<Notification>();
		this.notificationId = 0;
		
		String not = FileIO.getInstance().loadData(Define.DATA_NOTIFICATION, context);
		if(not != null){
			//sceneId@notified
			//234@false
			String[] notList = not.split("\n");
			for(int i = 0; i < not.length(); i++){
				try{
					Notification n = new Notification();
					n.scene = Integer.parseInt(notList[i].split("@")[0]);
					n.notified = notList[i].split("@")[1].equals("true");
					notificationList.add(n);
				}catch(Exception e){}
			}
		}
	}
	
	private void saveNotifications(){
		String not = "";
		for(int i = 0; i < notificationList.size(); i++){
			not+=("" + notificationList.get(i).scene + "@" + (notificationList.get(i).notified?"true":"false"));
			if(i < notificationList.size()-1){
				not+="\n";
			}
		}
		FileIO.getInstance().saveData(not, Define.DATA_NOTIFICATION, context);
	}

	@Override
	public void run() {
		Log.i("Debug", "Inicio de las notificaciones");
		active = true;
		while (active) {
			try {

				while (!Main.getInstance().isPaused()) {
					Thread.sleep(5000);
				}

				SceneListData sceneListData = OnlineInputOutput
						.getInstance().reviceSceneListData(Main.getInstance().getActivity(), user);
				
				if (sceneListData != null) {
					//A�ado nuevas escenas si no existen
					for (SceneData sceneData : sceneListData.getSceneDataList()) {
						boolean add = true;
						for(Notification notification : notificationList){
							if(sceneData.getId() == notification.scene){
								add = false;
							}
						}
						if(add){
							Notification n = new Notification();
							n.scene = sceneData.getId();
							n.notified = false;
							notificationList.add(n);
						}
					}
					
					for (SceneData sceneData : sceneListData.getSceneDataList()) {
						//Si la siguiente partida es del jugador, chequeo si ya se ha notificado
						if (sceneData.getNextPlayer().equals(user)) {
							for(Notification notification : notificationList){
								if(
										sceneData.getId() == notification.scene && 
										!notification.notified){
									
									//Aviso al usuario
									Main.getInstance().getActivity().
									sendNotification(notificationId++,
											RscManager.allText[RscManager.TXT_KINGDOM_NEEDS_YOU], 
											""+notification.scene + "-" + RscManager.allText[RscManager.TXT_GAME_ANSWER]);
									
									notification.notified = true;
								}
							}
						}
						//Para aquellas escenas que el next no es el player, las activo
						else{
							//Aun no corresponde notificacion, actualizo para que cuando nextPlayer se actualice, notifique si procede
							for(Notification notification : notificationList){
								if(sceneData.getId() == notification.scene){
									notification.notified = false;
								}
							}
						}
					}
					//Guardo las notificaciones
					saveNotifications();
				}

				//Thread.sleep(1000 * 60 * 60 * 12);
				Thread.sleep(3000);
				
				Log.i("Debug", "Actualizadas las notificaciones...");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.i("Debug", "Fin de las notificaciones");
	}
	
	class Notification{
		public int scene;
		public boolean notified;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
