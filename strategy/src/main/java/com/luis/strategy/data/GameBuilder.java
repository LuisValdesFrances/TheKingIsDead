package com.luis.strategy.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.GameState;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.datapackage.scene.ArmyData;
import com.luis.strategy.datapackage.scene.BuildingData;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.KingdomData;
import com.luis.strategy.datapackage.scene.PlayerData;
import com.luis.strategy.datapackage.scene.TroopData;
import com.luis.strategy.map.ActionIA;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.Building;
import com.luis.strategy.map.CityManagement;
import com.luis.strategy.map.GameScene;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Player;
import com.luis.strategy.map.Troop;

public class GameBuilder {
	
	private static GameBuilder instance;
	
	public static GameBuilder getInstance(){
		if(instance == null){
			instance = new GameBuilder();
		}
		return instance;
	}
	
	public GameScene buildStartPassAndPlay(){
		
		GameScene gameScene = new GameScene(GameState.getInstance()
				.getMap(),
				0,// GfxManager.imgMap.getWidth()/2,
				0,// GfxManager.imgMap.getHeight()/2,
				DataKingdom.MAP_PARTS_WIDTH,
				DataKingdom.MAP_PARTS_HEIGHT);

		gameScene.setKingdomList(DataKingdom.getMap(gameScene.getMapObject(), GameState.getInstance().getMap()));
		
		List<Player> playerList = new ArrayList<Player>();
		GameState.PlayerConf[] playerConfList = GameState.getInstance().getPlayerConfList();
		
		for (int i = 0; i < playerConfList.length; i++) {
			
			int k1 = DataKingdom.INIT_MAP_DATA[GameState.getInstance().getMap()][i][0];
			
			Player player = new Player(
					GameState.getInstance().getPlayerConfList()[i].name, 
					GameState.getInstance().getPlayerConfList()[i].IA?new ActionIA():null, 
							GameState.getInstance().getPlayerConfList()[i].flag, 
							k1);
			
			player.setGold(GameParams.START_GOLD);
			player.getKingdomList().add(gameScene.getKingdom(k1));
			
			Army army = new Army(
					gameScene.getMapObject(), player, gameScene.getKingdom(k1), player.getFlag(), 
					gameScene.getMapObject().getX(), gameScene.getMapObject().getY(), gameScene.getMapObject().getWidth(), gameScene.getMapObject().getHeight());
			army.initTroops();
			player.getArmyList().add(army);
			
			playerList.add(player);
		}
		
		gameScene.setPlayerList(playerList);
		return gameScene;
	}
	
	public GameScene buildStartOnLine(){
		GameScene gameScene = new GameScene(GameState.getInstance()
				.getMap(),
				0,// GfxManager.imgMap.getWidth()/2,
				0,// GfxManager.imgMap.getHeight()/2,
				DataKingdom.MAP_PARTS_WIDTH,
				DataKingdom.MAP_PARTS_HEIGHT);

		gameScene.setKingdomList(DataKingdom.getMap(gameScene.getMapObject(), GameState.getInstance().getMap()));
		
		List<Player> playerList = new ArrayList<Player>();
		
		for(int i = 0; i < GameState.getInstance().getSceneData().getPlayerDataList().size(); i++){
			PlayerData playerData =  GameState.getInstance().getSceneData().getPlayerDataList().get(i);
			
			int k1 = DataKingdom.INIT_MAP_DATA[GameState.getInstance().getMap()][i][0];
			
			Player player = new Player(
					playerData.getName(), 
					null, 
					i, 
					k1);
			
			player.setGold(GameParams.START_GOLD);
			player.getKingdomList().add(gameScene.getKingdom(k1));
			
			Army army = new Army(
					gameScene.getMapObject(), player, gameScene.getKingdom(k1), player.getFlag(), 
					gameScene.getMapObject().getX(), gameScene.getMapObject().getY(), gameScene.getMapObject().getWidth(), gameScene.getMapObject().getHeight());
			army.initTroops();
			player.getArmyList().add(army);
			
			playerList.add(player);
		}
		
		/*//Deben de venir ordenados por fecha de inscripcion del servidor
		//Ordeno los players para que, en caso de una nueva partida, sea el host el primero
		if(GameState.getInstance().getSceneData().getState() == 0){
			for(int i = 0; i < playerList.size(); i++){
				if(i > 0 && playerList.get(i).getName().equals(GameState.getInstance().getName())){
					Player first = playerList.get(i);
					Player aux = playerList.get(0);
					playerList.set(0, first);
					playerList.set(i, aux);
				}
			}
		}
		*/
		gameScene.setPlayerList(playerList);
		return gameScene;
	}
	
	public GameScene buildGameScene(){
		
		GameScene gameScene = new GameScene(
				GameState.getInstance().getMap(),
				0,//GfxManager.imgMap.getWidth()/2, 
				0,//GfxManager.imgMap.getHeight()/2,
				DataKingdom.MAP_PARTS_WIDTH,
				DataKingdom.MAP_PARTS_HEIGHT);
		gameScene.setTurnCount(GameState.getInstance().getSceneData().getTurnCount());
		gameScene.setPlayerIndex(GameState.getInstance().getSceneData().getPlayerIndex());
		
		List<Player>playerList = new ArrayList<Player>();
		
		gameScene.setKingdomList(DataKingdom.getMap(gameScene.getMapObject(), GameState.getInstance().getMap()));
		
		for(PlayerData playerData: GameState.getInstance().getSceneData().getPlayerDataList()){
			
			String name = playerData.getName();
			boolean isIA = playerData.isIA();
			int flag = playerData.getFlag();
			int capitalkingdom = playerData.getCapitalKingdom();
			int gold = playerData.getGold();
			
			ActionIA actionIA = isIA?new ActionIA():null;
			Player player = new Player(name, actionIA, flag, capitalkingdom);
			player.setGold(gold);
			
			for(KingdomData kingdomData : playerData.getKingdomList()){
				Kingdom k = gameScene.getKingdom(kingdomData.getId());
				k.setState(kingdomData.getState());
				k.setProtectedByFaith(kingdomData.isProtectedByFaith());
				
				//City management
				CityManagement cityManagement = null;
				if(kingdomData.getBuildingList() != null){
					cityManagement = new CityManagement();
					
					for(BuildingData bd : kingdomData.getBuildingList()){
						Building building = new Building(bd.getType(), bd.getState(), bd.getLevel());
						
						cityManagement.getBuildingList().set(bd.getType(), building);
					}
				}
				k.setCityManagement(cityManagement);
				
				player.getKingdomList().add(k);
			}
			
			for(ArmyData armyData : playerData.getArmyList()){
				
				Kingdom k = gameScene.getKingdom(armyData.getKingdom().getId());
				k.setState(armyData.getKingdom().getState());
				
				Army army = new Army(
						gameScene.getMapObject(), player, k, player.getFlag(), 
						gameScene.getMapObject().getX(), gameScene.getMapObject().getY(), gameScene.getMapObject().getWidth(), gameScene.getMapObject().getHeight());
				for(TroopData td : armyData.getTroopList()){
					Troop troop = new Troop(td.getType(), td.isSubject());
					army.getTroopList().add(troop);
				}
				
				player.getArmyList().add(army);
			}
			playerList.add(player);
		}
		gameScene.setPlayerList(playerList);
		
		return gameScene;
	}
	
	public SceneData buildSceneData(int state){
		
		if(GameState.getInstance().getGameScene() == null){
			return null;
		}
		
		SceneData sceneData = GameState.getInstance().getSceneData();
		
		sceneData.setMap(GameState.getInstance().getGameScene().getMap());
		sceneData.setPlayerIndex(GameState.getInstance().getGameScene().getPlayerIndex());
		sceneData.setNextPlayer(GameState.getInstance().getGameScene().getPlayerList().get(sceneData.getPlayerIndex()).getName());
		sceneData.setTurnCount(GameState.getInstance().getGameScene().getTurnCount());
		sceneData.setState(state);
		
		List<PlayerData> playerDataList = new ArrayList<PlayerData>();
		for(Player p : GameState.getInstance().getGameScene().getPlayerList()){
			PlayerData pd = new PlayerData();
			pd.setId(p.getId());
			pd.setName(p.getName());
			pd.setGold(p.getGold());
			pd.setCapitalKingdom(p.getCapitalkingdom() != null ? p.getCapitalkingdom().getId(): -1);
			pd.setFlag(p.getFlag());
			pd.setIA(p.getActionIA() != null);
			
			//Add army list to player object
			List<ArmyData> adList = new ArrayList<ArmyData>();
			for(Army a : p.getArmyList()){
				ArmyData ad = new ArmyData();
				KingdomData kingdomData = new KingdomData();
				kingdomData.setId(a.getKingdom().getId());
				kingdomData.setState(a.getKingdom().getState());
				ad.setKingdom(kingdomData);
				//Add troops
				List<TroopData> troopList = new ArrayList<TroopData>();
				for(Troop t : a.getTroopList()){
					TroopData troopData = new TroopData();
					troopData.setType(t.getType());
					troopData.setSubject(t.isSubject());
					troopList.add(troopData);
				}
				ad.setTroopList(troopList);
				adList.add(ad);
			}
			pd.setArmyList(adList);
			
			//Add kingdom list to player object
			List<KingdomData> kdList = new ArrayList<KingdomData>();
			for(Kingdom k : p.getKingdomList()){
				KingdomData kd = new KingdomData();
				kd.setId(k.getId());
				kd.setState(k.getState());
				kd.setProtectedByFaith(k.isProtectedByFaith());
				
				//City management
				List<BuildingData> buildingDataList = null;
				if(k.getCityManagement() != null){
					buildingDataList = new ArrayList<BuildingData>();
					for(Building b : k.getCityManagement().getBuildingList()){
						BuildingData bd = new BuildingData();
						bd.setType(b.getType());
						bd.setLevel(b.getLevel());
						bd.setState(b.getState());
						
						buildingDataList.add(bd);
					}
				}
				kd.setBuildingList(buildingDataList);
				
				kdList.add(kd);
			}
			pd.setKingdomList(kdList);
			
			
			playerDataList.add(pd);
		}
		
		//A�ado las lista de jugadores
		sceneData.setPlayerDataList(playerDataList);
		
		return sceneData;
	}

}
