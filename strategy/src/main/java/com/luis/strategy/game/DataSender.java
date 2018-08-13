package com.luis.strategy.game;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.GameState;
import com.luis.strategy.Main;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.data.GameBuilder;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.map.GameScene;

public class DataSender{

	private List<Notification> notificationList;
	
	public DataSender(){
		notificationList = new ArrayList<Notification>();
	}
	
	public void addNotification(String from, String to, int type, int message){
		notificationList.add(new Notification(from, to, message, type));
	}
	
	public String sendGameScene(GameScene gameScene, int state, boolean showWait) {
		GameState.getInstance().setGameScene(gameScene);
		SceneData sceneData = GameBuilder.getInstance().buildSceneData(state);

		if(showWait){
			Main.getInstance().startClock(Main.TYPE_EARTH);
		}
		String result =
				OnlineInputOutput.getInstance().sendDataPackage(
						Main.getInstance().getActivity(),
						OnlineInputOutput.URL_UPDATE_SCENE, sceneData);
		if(showWait){
			Main.getInstance().stopClock();
		}
		return result;
	}
	
	public void sendGameNotifications(){
		for(Notification n : notificationList){

			String type = "" + n.type;

			OnlineInputOutput.getInstance().sendNotification(
					Main.getInstance().getActivity(),
					"" + GameState.getInstance().getSceneData().getId(),
					n.from, n.to, ""+n.message, type);
		}
		/*
		Thread thread = new Thread(){
			@Override
			public void run(){
				for(Notification n : notificationList){
				OnlineInputOutput.getInstance().sendNotification(
					Main.getInstance().getActivity(),
					"" + GameState.getInstance().getSceneData().getId(),
					n.from, n.to, ""+n.message, type);
			}
			}
		};
		thread.start();

		*/
	}
	
	class Notification{
		
		public Notification(String from, String to, int message, int type) {
			super();
			this.from = from;
			this.to = to;
			this.message = message;
			this.type = type;
		}
		String from;
		String to;
		int message;
		int type;
	}
}
