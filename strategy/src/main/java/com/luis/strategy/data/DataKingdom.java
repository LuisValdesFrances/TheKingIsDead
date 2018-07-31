package com.luis.strategy.data;

import java.util.ArrayList;
import java.util.List;

import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.MapObject;
import com.luis.strategy.map.Terrain;

public class DataKingdom {
	
	/**Establece los dos reinos con los que se empieza por defecto
	 * Dimension 0: mapa
	 * Dimension 1: jugador
	 * Dimension 2. kingdoms
	*/
	public static final int[][][] INIT_MAP_DATA =
		{
		{{1}, {14}},
		{{1}, {19}, {25}},
		{{19}, {25}, {36}, {51}},
		{{1}, {14}, {19}, {25}, {36}},
		{{1}, {14}, {19}, {25}, {36}, {51}},
		};
	
	public static final int MAP_PARTS_WIDTH = 4;
	public static final int MAP_PARTS_HEIGHT = 4;

	public static final String[] SCENARY_LIST = new String[]{
		"2 Kingdoms",
		"3 Kingdoms",
		"4 Kingdoms",
		"5 Kingdoms",
		"6 Kingdoms"
		};

	public static final String[] SCENARY_NAME_LIST = new String[]{
		"Occitane",
		"Kingdoms",
		"Levantia",
		"Conquest",
		"Imperium"
	};

	
	public static List<Kingdom> getMap(MapObject map, int mapId){
		
		int mapWidth = 2400;
		int mapHeight = 1480;
		
		List<Terrain> terrainList = null;
		List<Kingdom> kingdomList = new ArrayList<Kingdom>();
		
		Kingdom k1 = null;
		Kingdom k2 = null;
		Kingdom k3 = null;
		Kingdom k4 = null;
		Kingdom k5 = null;
		Kingdom k6 = null;
		Kingdom k7 = null;
		Kingdom k8 = null;
		Kingdom k9 = null;
		Kingdom k10 = null;
		Kingdom k11 = null;
		Kingdom k12 = null;
		Kingdom k13 = null;
		Kingdom k14 = null;
		Kingdom k15 = null;
		Kingdom k16 = null;
		Kingdom k17 = null;
		Kingdom k18 = null;
		Kingdom k19 = null;
		Kingdom k20 = null;
		Kingdom k21 = null;
		Kingdom k22 = null;
		Kingdom k23 = null;
		Kingdom k24 = null;
		Kingdom k25 = null;
		Kingdom k26 = null;
		Kingdom k27 = null;
		Kingdom k28 = null;
		Kingdom k29 = null;
		Kingdom k30 = null;
		Kingdom k31 = null;
		Kingdom k32 = null;
		Kingdom k33 = null;
		Kingdom k34 = null;
		Kingdom k35 = null;
		Kingdom k36 = null;
		Kingdom k37 = null;
		Kingdom k38 = null;
		Kingdom k39 = null;
		Kingdom k40 = null;
		Kingdom k41 = null;
		Kingdom k42 = null;
		Kingdom k43 = null;
		Kingdom k44 = null;
		Kingdom k45 = null;
		Kingdom k46 = null;
		Kingdom k47 = null;
		Kingdom k48 = null;
		Kingdom k49 = null;
		Kingdom k50 = null;
		Kingdom k51 = null;
		Kingdom k52 = null;
		Kingdom k53 = null;
		Kingdom k54 = null;
		Kingdom k55 = null;
		Kingdom k56 = null;
		Kingdom k57 = null;
		
		
		if(mapId == 0 || mapId == 1 || mapId == 3   || mapId == 4){
			k1 = new Kingdom(map,
				(520f/mapWidth*100), (420f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k1.setId(1);
			k1.setName("Genterex");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(430f/mapWidth*100), (510f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(500f/mapWidth*100), (510f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(570f/mapWidth*100), (510f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(405f/mapWidth*100), (400f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k1.setTerrainList(terrainList);
		
		
			k2 = new Kingdom(map,
					(360f/(float)mapWidth*100f), (580f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k2.setId(2);
			k2.setName("Surett");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(290f/(float)mapWidth*100f), (680f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(360f/(float)mapWidth*100f), (680f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(260f/(float)mapWidth*100f), (592f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k2.setTerrainList(terrainList);
			
			k3 = new Kingdom(map,
				(460f/(float)mapWidth*100f), (790f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k3.setId(3);
			k3.setName("Lyecee");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(382f/(float)mapWidth*100f), (940f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(452f/(float)mapWidth*100f), (940f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(372f/(float)mapWidth*100f), (852f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k3.setTerrainList(terrainList);
			
			k4 = new Kingdom(map,
				(730f/(float)mapWidth*100f), (530f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k4.setId(4);
			k4.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(790f/(float)mapWidth*100f), (575f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(860f/(float)mapWidth*100f), (575f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(930f/(float)mapWidth*100f), (575f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k4.setTerrainList(terrainList);
			
			k5 = new Kingdom(map,
				(500f/(float)mapWidth*100f), (610f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k5.setId(5);
			k5.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(460f/(float)mapWidth*100f), (710f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(530f/(float)mapWidth*100f), (710f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k5.setTerrainList(terrainList);
			
			k6 = new Kingdom(map,
				(660f/(float)mapWidth*100f), (570f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k6.setId(6);
			k6.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(650f/(float)mapWidth*100f), (660f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(720f/(float)mapWidth*100f), (660f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k6.setTerrainList(terrainList);
			
			k7 = new Kingdom(map,
				(580f/(float)mapWidth*100f), (840f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k7.setId(7);
			k7.setName("Tiraslye");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(640f/(float)mapWidth*100f), (840f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(710f/(float)mapWidth*100f), (840f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(780f/(float)mapWidth*100f), (840f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(650f/(float)mapWidth*100f), (750f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k7.setTerrainList(terrainList);
			
			k8 = new Kingdom(map,
				(830f/(float)mapWidth*100f), (940f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k8.setId(8);
			k8.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(620f/(float)mapWidth*100f), (980f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(690f/(float)mapWidth*100f), (980f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(760f/(float)mapWidth*100f), (980f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k8.setTerrainList(terrainList);
		}
		
		if(mapId == 0 || mapId == 1 || mapId == 4){
			//Daergonais
			k9 = new Kingdom(map,
				(340f/(float)mapWidth*100f), (80f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k9.setId(9);
			k9.setName("Krull");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(245f/(float)mapWidth*100f), (95f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(150f/(float)mapWidth*100f), (95f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k9.setTerrainList(terrainList);
			
			k10 = new Kingdom(map,
				(450f/(float)mapWidth*100f), (210f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k10.setId(10);
			k10.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(500f/(float)mapWidth*100f), (300f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k10.setTerrainList(terrainList);
			
			k11 = new Kingdom(map,
				(305f/(float)mapWidth*100f), (250f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k11.setId(11);
			k11.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(220f/(float)mapWidth*100f), (240f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k11.setTerrainList(terrainList);
			
			k12 = new Kingdom(map,
				(695f/(float)mapWidth*100f), (420f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k12.setId(12);
			k12.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(635f/(float)mapWidth*100f), (360f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k12.setTerrainList(terrainList);
			
			k13 = new Kingdom(map,
				(780f/(float)mapWidth*100f), (410f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k13.setId(13);
			k13.setName("Daergonais");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(730f/(float)mapWidth*100f), (330f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(800f/(float)mapWidth*100f), (330f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(870f/(float)mapWidth*100f), (430f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k13.setTerrainList(terrainList);
		}
		
		//Quaca
		if(mapId == 0 || mapId == 3 || mapId == 4){
			k14 = new Kingdom(map,
			(400/(float)mapWidth*100f), (1070f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k14.setId(14);
			k14.setName("Quaca");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(220f/(float)mapWidth*100f), (1100f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(280f/(float)mapWidth*100f), (1100f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(340f/(float)mapWidth*100f), (1100/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(280f/(float)mapWidth*100f), (1020f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k14.setTerrainList(terrainList);
			
			k15 = new Kingdom(map,
				(125/(float)mapWidth*100f), (1240f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k15.setId(15);
			k15.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(200f/(float)mapWidth*100f), (1210f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k15.setTerrainList(terrainList);
			
			k16 = new Kingdom(map,
				(470/(float)mapWidth*100f), (1130f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k16.setId(16);
			k16.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(485f/(float)mapWidth*100f), (1060f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(555f/(float)mapWidth*100f), (1060f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k16.setTerrainList(terrainList);
			
			k17 = new Kingdom(map,
				(480/(float)mapWidth*100f), (1235f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k17.setId(17);
			k17.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(380f/(float)mapWidth*100f), (1240f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k17.setTerrainList(terrainList);
			
			k18 = new Kingdom(map,
				(575/(float)mapWidth*100f), (1340f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k18.setId(18);
			k18.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(490f/(float)mapWidth*100f), (1380f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k18.setTerrainList(terrainList);
		}
		
		//Lye
		if(mapId == 1 || mapId == 2 || mapId == 3 || mapId == 4){
			k19 = new Kingdom(map,
			(1040/(float)mapWidth*100f), (190f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k19.setId(19);
			k19.setName("Lyee");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(900f/(float)mapWidth*100f), (290f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(960f/(float)mapWidth*100f), (290f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1020f/(float)mapWidth*100f), (290/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(970f/(float)mapWidth*100f), (380f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k19.setTerrainList(terrainList);
			
			k20 = new Kingdom(map,
				(900/(float)mapWidth*100f), (70f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k20.setId(20);
			k20.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(800f/(float)mapWidth*100f), (90f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			k20.setTerrainList(terrainList);
			
			k21 = new Kingdom(map,
				(1440/(float)mapWidth*100f), (160f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k21.setId(21);
			k21.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1150f/(float)mapWidth*100f), (200f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1220f/(float)mapWidth*100f), (200f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1290f/(float)mapWidth*100f), (200f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(1250f/(float)mapWidth*100f), (140f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k21.setTerrainList(terrainList);
			
			k22 = new Kingdom(map,
				(1580/(float)mapWidth*100f), (180f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k22.setId(22);
			k22.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1690f/(float)mapWidth*100f), (255f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1760f/(float)mapWidth*100f), (225f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k22.setTerrainList(terrainList);
			
			k23 = new Kingdom(map,
				(1270/(float)mapWidth*100f), (315f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k23.setId(23);
			k23.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1110f/(float)mapWidth*100f), (340f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(1180f/(float)mapWidth*100f), (340f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k23.setTerrainList(terrainList);
			
			k24 = new Kingdom(map,
				(1520/(float)mapWidth*100f), (260f/(float)mapHeight*100f), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k24.setId(24);
			k24.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1360f/(float)mapWidth*100f), (300f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1430f/(float)mapWidth*100f), (300f/(float)mapHeight*100f), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k24.setTerrainList(terrainList);
		}
		
		//Crom
		if(mapId == 1 || mapId == 2 || mapId == 3 || mapId == 4){
			k25 = new Kingdom(map,
				(1575f/mapWidth*100), (660f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k25.setId(25);
			k25.setName("Crom");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1410f/mapWidth*100), (760f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1480f/mapWidth*100), (760f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1550f/mapWidth*100), (760f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(1450f/mapWidth*100), (620f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k25.setTerrainList(terrainList);
			
			k26 = new Kingdom(map,
				(1300f/mapWidth*100), (450f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k26.setId(26);
			k26.setName("Cromgast");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1020f/mapWidth*100), (470f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(1080f/mapWidth*100), (470f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(1190f/mapWidth*100), (440f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k26.setTerrainList(terrainList);
			
			k27 = new Kingdom(map,
				(1410f/mapWidth*100), (460f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k27.setId(27);
			k27.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1430f/mapWidth*100), (380f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(1500f/mapWidth*100), (380f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k27.setTerrainList(terrainList);
			
			k28 = new Kingdom(map,
				(1580f/mapWidth*100), (500f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k28.setId(28);
			k28.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1510f/mapWidth*100), (480f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k28.setTerrainList(terrainList);
			
			k29 = new Kingdom(map,
				(1055f/mapWidth*100), (580f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k29.setId(29);
			k29.setName("");
				//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(910f/mapWidth*100), (675f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(980f/mapWidth*100), (675f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k29.setTerrainList(terrainList);
			
			k30 = new Kingdom(map,
				(1195f/mapWidth*100), (600f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k30.setId(30);
			k30.setName("");
				//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1280f/mapWidth*100), (700f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			k30.setTerrainList(terrainList);
			
			k31 = new Kingdom(map,
				(1680f/mapWidth*100), (650f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k31.setId(31);
			k31.setName("");
				//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1745f/mapWidth*100), (675f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1815f/mapWidth*100), (675f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k31.setTerrainList(terrainList);
			
			k32 = new Kingdom(map,
				(1070f/mapWidth*100), (780f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k32.setId(32);
			k32.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1050f/mapWidth*100), (920f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1120f/mapWidth*100), (920f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(985f/mapWidth*100), (800f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k32.setTerrainList(terrainList);
			
			k33 = new Kingdom(map,
				(1210f/mapWidth*100), (795f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k33.setId(33);
			k33.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1210f/mapWidth*100), (890f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1280f/mapWidth*100), (890f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1350f/mapWidth*100), (820f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k33.setTerrainList(terrainList);
			
			k34 = new Kingdom(map,
				(1550f/mapWidth*100), (1030f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k34.setId(34);
			k34.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1620f/mapWidth*100), (1040f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1690f/mapWidth*100), (1040f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1750f/mapWidth*100), (1040f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(1530f/mapWidth*100), (930f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k34.setTerrainList(terrainList);
			
			k35 = new Kingdom(map,
				(1650f/mapWidth*100), (795f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k35.setId(35);
			k35.setName("");
				//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1700f/mapWidth*100), (890f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k35.setTerrainList(terrainList);
		}
		
		//Gapeangue
		if(mapId == 2 || mapId == 3 || mapId == 4){
			k36 = new Kingdom(map,
				(1285f/mapWidth*100), (1030f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k36.setId(36);
			k36.setName("Gapeangue");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1260f/mapWidth*100), (1140f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1330f/mapWidth*100), (1140f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1400f/mapWidth*100), (1140f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.FOREST).getWidth(), GfxManager.imgTerrain.get(GameParams.FOREST).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1145f/mapWidth*100), (1085f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.CITY).getWidth(), GfxManager.imgTerrain.get(GameParams.CITY).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k36.setTerrainList(terrainList);
			
			k37 = new Kingdom(map,
				(1015f/mapWidth*100), (1065f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k37.setId(37);
			k37.setName("");
				//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(930f/mapWidth*100), (1010f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k37.setTerrainList(terrainList);
			
			k38 = new Kingdom(map,
				(1045f/mapWidth*100), (1200f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k38.setId(38);
			k38.setName("");
				//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(900f/mapWidth*100), (1165f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(970f/mapWidth*100), (1165f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(830f/mapWidth*100), (1150f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k38.setTerrainList(terrainList);
			
			k39 = new Kingdom(map,
				(815f/mapWidth*100), (1275f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k39.setId(39);
			k39.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(750f/mapWidth*100), (1250f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			k39.setTerrainList(terrainList);
			
			k40 = new Kingdom(map,
				(1330f/mapWidth*100), (1330f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k40.setId(40);
			k40.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1180f/mapWidth*100), (1260f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1140f/mapWidth*100), (1340f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k40.setTerrainList(terrainList);
			
			k41 = new Kingdom(map,
				(1440f/mapWidth*100), (1360f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k41.setId(41);
			k41.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1370f/mapWidth*100), (1270f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1440f/mapWidth*100), (1270f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k41.setTerrainList(terrainList);
			
			k42 = new Kingdom(map,
				(735f/mapWidth*100), (1085f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k42.setId(42);
			k42.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(600f/mapWidth*100), (1160f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(670f/mapWidth*100), (1160f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k42.setTerrainList(terrainList);
		}
		
		//Myr
		if(mapId == 2 || mapId == 3 || mapId == 4){
			k43 = new Kingdom(map,
				(1600f/mapWidth*100), (1235f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k43.setId(43);
			k43.setName("Myr");
				//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1750f/mapWidth*100), (1240f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1820f/mapWidth*100), (1240f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(1660f/mapWidth*100), (1160f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k43.setTerrainList(terrainList);
			
			
			k44 = new Kingdom(map,
				(1830f/mapWidth*100), (1370f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k44.setId(44);
			k44.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1720f/mapWidth*100), (1375f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			k44.setTerrainList(terrainList);
			
			k45 = new Kingdom(map,
				(2055f/mapWidth*100), (1315f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k45.setId(45);
			k45.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1980f/mapWidth*100), (1315f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			k45.setTerrainList(terrainList);
		}
		
		//Montag
		if(mapId == 4){
			k46 = new Kingdom(map,
				(1920f/mapWidth*100), (450f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k46.setId(46);
			k46.setName("Montag");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1955f/mapWidth*100), (555f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(2025f/mapWidth*100), (555f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(2005f/mapWidth*100), (440f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k46.setTerrainList(terrainList);
			
			k47 = new Kingdom(map,
				(1730f/mapWidth*100), (500f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k47.setId(47);
			k47.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1610f/mapWidth*100), (370f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1680f/mapWidth*100), (370f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1750f/mapWidth*100), (370f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k47.setTerrainList(terrainList);
			
			k48 = new Kingdom(map,
				(2120f/mapWidth*100), (370f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k48.setId(48);
			k48.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(2210f/mapWidth*100), (420f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(2280f/mapWidth*100), (420f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k48.setTerrainList(terrainList);
			
			k49 = new Kingdom(map,
				(2130f/mapWidth*100), (520f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k49.setId(49);
			k49.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(2130f/mapWidth*100), (640f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k49.setTerrainList(terrainList);
			
			k50 = new Kingdom(map,
				(2300f/mapWidth*100), (525f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k50.setId(50);
			k50.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(2280f/mapWidth*100), (640f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(2210f/mapWidth*100), (570f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k50.setTerrainList(terrainList);
		}
		
		//Levantia
		if(mapId == 2 || mapId == 3 || mapId == 4){
			k51 = new Kingdom(map,
				(2015f/mapWidth*100), (950f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k51.setId(51);
			k51.setName("Levantia");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(2020f/mapWidth*100), (1050f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(2090f/mapWidth*100), (1050f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(2160f/mapWidth*100), (1050f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(2140f/mapWidth*100), (950f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k51.setTerrainList(terrainList);
			
			k52 = new Kingdom(map,
				(2170f/mapWidth*100), (750f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k52.setId(52);
			k52.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(2240f/mapWidth*100), (760f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(2310f/mapWidth*100), (760f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k52.setTerrainList(terrainList);
			
			k53 = new Kingdom(map,
				(1935f/mapWidth*100), (660f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k53.setId(53);
			k53.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1920f/mapWidth*100), (765f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.PLAIN, false));
			terrainList.add(new Terrain(map,
				(1975f/mapWidth*100), (830f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k53.setTerrainList(terrainList);
			
			k54 = new Kingdom(map,
				(1830f/mapWidth*100), (760f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k54.setId(54);
			k54.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1755f/mapWidth*100), (785f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k54.setTerrainList(terrainList);
			
			k55 = new Kingdom(map,
				(1875f/mapWidth*100), (960f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k55.setId(55);
			k55.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1810f/mapWidth*100), (880f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			terrainList.add(new Terrain(map,
				(1880f/mapWidth*100), (880f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			k55.setTerrainList(terrainList);
			
			k56 = new Kingdom(map,
				(1885f/mapWidth*100), (1110f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k56.setId(56);
			k56.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(1970f/mapWidth*100), (1140f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.MONTAIN, false));
			terrainList.add(new Terrain(map,
				(1965f/mapWidth*100), (1190f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.CITY, false));
			k56.setTerrainList(terrainList);
			
			k57 = new Kingdom(map,
				(2145f/mapWidth*100), (1180f/mapHeight*100), map.getX(), map.getY(), map.getWidth(), map.getHeight());
			k57.setId(57);
			k57.setName("");
			//La posicion del terreno se expresa en porcentajes respecto al ancho y alto del mapa
			terrainList = new ArrayList<Terrain>();
			terrainList.add(new Terrain(map,
				(2070f/mapWidth*100), (1175f/mapHeight*100), GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth(), GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight(), map.getX(), map.getY(), map.getWidth(), map.getHeight(), GameParams.FOREST, false));
			k57.setTerrainList(terrainList);
		}
		
		
		//Genterex
		if(k1 != null){
			k1.getBorderList().add(k2);
			k1.getBorderList().add(k4);
			k1.getBorderList().add(k5);
			k1.getBorderList().add(k6);
			k1.getBorderList().add(k6);
			k1.getBorderList().add(k6);
			k1.getBorderList().add(k10);
			k1.getBorderList().add(k11);
			k1.getBorderList().add(k12);
		}
		
		if(k2 != null){
			k2.getBorderList().add(k1);
			k2.getBorderList().add(k3);
			k2.getBorderList().add(k5);
		}
		
		if(k3 != null){
			k3.getBorderList().add(k2);
			k3.getBorderList().add(k5);
			k3.getBorderList().add(k7);
			k3.getBorderList().add(k8);
			k3.getBorderList().add(k14);
			k3.getBorderList().add(k16);
		}
		
		if(k4 != null){
			k4.getBorderList().add(k1);
			k4.getBorderList().add(k6);
			k4.getBorderList().add(k7);
			k4.getBorderList().add(k12);
			k4.getBorderList().add(k13);
			k4.getBorderList().add(k26);
			k4.getBorderList().add(k29);
		}
		
		if(k5 != null){
			k5.getBorderList().add(k1);
			k5.getBorderList().add(k2);
			k5.getBorderList().add(k3);
			k5.getBorderList().add(k6);
			k5.getBorderList().add(k7);
		}
		
		if(k6 != null){
			k6.getBorderList().add(k1);
			k6.getBorderList().add(k4);
			k6.getBorderList().add(k5);
			k6.getBorderList().add(k7);
		}
		
		if(k7 != null){
			k7.getBorderList().add(k3);
			k7.getBorderList().add(k4);
			k7.getBorderList().add(k5);
			k7.getBorderList().add(k6);
			k7.getBorderList().add(k8);
			k7.getBorderList().add(k32);
		}
		
		if(k8 != null){
			k8.getBorderList().add(k7);
			k8.getBorderList().add(k3);
			k8.getBorderList().add(k16);
			k8.getBorderList().add(k42);
		}
		
		//Daergonais
		if(k9 != null){
			k9.getBorderList().add(k10);
			k9.getBorderList().add(k11);
		}
		
		if(k10 != null){
			k10.getBorderList().add(k9);
			k10.getBorderList().add(k11);
			k10.getBorderList().add(k1);
			k10.getBorderList().add(k12);
		}
		
		if(k11 != null){
			k11.getBorderList().add(k9);
			k11.getBorderList().add(k10);
			k11.getBorderList().add(k1);
		}
		
		if(k12 != null){
			k12.getBorderList().add(k10);
			k12.getBorderList().add(k1);
			k12.getBorderList().add(k4);
			k12.getBorderList().add(k13);
		}
		
		if(k13 != null){
			k13.getBorderList().add(k12);
			k13.getBorderList().add(k20);
			k13.getBorderList().add(k19);
			k13.getBorderList().add(k4);
			k13.getBorderList().add(k26);
		}
		
		//Quaca
		if(k14 != null){
			k14.getBorderList().add(k3);
			k14.getBorderList().add(k16);
			k14.getBorderList().add(k17);
			k14.getBorderList().add(k15);
		}
		
		if(k15 != null){
			k15.getBorderList().add(k14);
			k15.getBorderList().add(k17);
		}
		
		if(k16 != null){
			k16.getBorderList().add(k14);
			k16.getBorderList().add(k3);
			k16.getBorderList().add(k8);
			k16.getBorderList().add(k17);
			k16.getBorderList().add(k42);
		}
		
		if(k17 != null){
			k17.getBorderList().add(k15);
			k17.getBorderList().add(k14);
			k17.getBorderList().add(k16);
			k17.getBorderList().add(k18);
			k17.getBorderList().add(k42);
		}
		
		if(k18 != null){
			k18.getBorderList().add(k17);
		}
		
		//Lye
		if(k19 != null){
			k19.getBorderList().add(k13);
			k19.getBorderList().add(k20);
			k19.getBorderList().add(k21);
			k19.getBorderList().add(k23);
			k19.getBorderList().add(k26);
		}
		
		if(k20 != null){
			k20.getBorderList().add(k13);
			k20.getBorderList().add(k19);
		}
		
		if(k21 != null){
			k21.getBorderList().add(k19);
			k21.getBorderList().add(k22);
			k21.getBorderList().add(k23);
			k21.getBorderList().add(k24);
		}
		
		if(k22 != null){
			k22.getBorderList().add(k21);
			k22.getBorderList().add(k24);
			k22.getBorderList().add(k47);
		}
		
		if(k23 != null){
			k23.getBorderList().add(k19);
			k23.getBorderList().add(k21);
			k23.getBorderList().add(k24);
			k23.getBorderList().add(k27);
			k23.getBorderList().add(k26);
		}
		
		if(k24 != null){
			k24.getBorderList().add(k21);
			k24.getBorderList().add(k22);
			k24.getBorderList().add(k23);
			k24.getBorderList().add(k27);
			k24.getBorderList().add(k47);
		}
		
		//Crom
		if(k25 != null){
			k25.getBorderList().add(k26);
			k25.getBorderList().add(k27);
			k25.getBorderList().add(k28);
			k25.getBorderList().add(k31);
			k25.getBorderList().add(k35);
			k25.getBorderList().add(k34);
			k25.getBorderList().add(k33);
			k25.getBorderList().add(k30);
		}
		
		if(k26 != null){
			k26.getBorderList().add(k30);
			k26.getBorderList().add(k29);
			k26.getBorderList().add(k4);
			k26.getBorderList().add(k13);
			k26.getBorderList().add(k19);
			k26.getBorderList().add(k23);
			k26.getBorderList().add(k27);
			k26.getBorderList().add(k25);
		}
		
		if(k27 != null){
			k27.getBorderList().add(k28);
			k27.getBorderList().add(k25);
			k27.getBorderList().add(k26);
			k27.getBorderList().add(k23);
			k27.getBorderList().add(k24);
			k27.getBorderList().add(k47);
		}
		
		if(k28 != null){
			k28.getBorderList().add(k31);
			k28.getBorderList().add(k25);
			k28.getBorderList().add(k27);
			k28.getBorderList().add(k47);
		}
		
		if(k29 != null){
			k29.getBorderList().add(k4);
			k29.getBorderList().add(k26);
			k29.getBorderList().add(k30);
			k29.getBorderList().add(k32);
			k29.getBorderList().add(k7);
		}
		
		if(k30 != null){
			k30.getBorderList().add(k33);
			k30.getBorderList().add(k32);
			k30.getBorderList().add(k29);
			k30.getBorderList().add(k26);
			k30.getBorderList().add(k25);
		}
		
		if(k31 != null){
			k31.getBorderList().add(k35);
			k31.getBorderList().add(k25);
			k31.getBorderList().add(k28);
			k31.getBorderList().add(k47);
			k31.getBorderList().add(k53);
			k31.getBorderList().add(k54);
		}
		
		if(k32 != null){
			k32.getBorderList().add(k7);
			k32.getBorderList().add(k29);
			k32.getBorderList().add(k30);
			k32.getBorderList().add(k33);
			k32.getBorderList().add(k37);
			k32.getBorderList().add(k36);
		}
		
		if(k33 != null){
			k33.getBorderList().add(k32);
			k33.getBorderList().add(k30);
			k33.getBorderList().add(k25);
			k33.getBorderList().add(k34);
			k33.getBorderList().add(k36);
		}
		
		if(k34 != null){
			k34.getBorderList().add(k33);
			k34.getBorderList().add(k25);
			k34.getBorderList().add(k35);
			k34.getBorderList().add(k36);
			k34.getBorderList().add(k43);
			k34.getBorderList().add(k55);
		}
		
		if(k35 != null){
			k35.getBorderList().add(k34);
			k35.getBorderList().add(k25);
			k35.getBorderList().add(k31);
			k35.getBorderList().add(k54);
			k35.getBorderList().add(k55);
		}
		
		//Gapeangue
		if(k36 != null){
			k36.getBorderList().add(k37);
			k36.getBorderList().add(k32);
			k36.getBorderList().add(k33);
			k36.getBorderList().add(k34);
			k36.getBorderList().add(k41);
			k36.getBorderList().add(k40);
			k36.getBorderList().add(k38);
			k36.getBorderList().add(k43);
		}
		
		if(k37 != null){
			k37.getBorderList().add(k32);
			k37.getBorderList().add(k36);
			k37.getBorderList().add(k38);
		}
		
		if(k38 != null){
			k38.getBorderList().add(k39);
			k38.getBorderList().add(k42);
			k38.getBorderList().add(k37);
			k38.getBorderList().add(k36);
			k38.getBorderList().add(k40);
			k38.getBorderList().add(k37);
			k38.getBorderList().add(k36);
		}
		
		if(k39 != null){
			k39.getBorderList().add(k18);
			k39.getBorderList().add(k38);
		}
		
		if(k40 != null){
			k40.getBorderList().add(k38);
			k40.getBorderList().add(k36);
			k40.getBorderList().add(k41);
		}
		if(k41 != null){
			k41.getBorderList().add(k40);
			k41.getBorderList().add(k36);
			k41.getBorderList().add(k43);
			k41.getBorderList().add(k44);
		}
		
		if(k42 != null){
			k42.getBorderList().add(k17);
			k42.getBorderList().add(k16);
			k42.getBorderList().add(k8);
			k42.getBorderList().add(k38);
		}
		
		//Myr
		if(k43 != null){
			k43.getBorderList().add(k41);
			k43.getBorderList().add(k36);
			k43.getBorderList().add(k34);
			k43.getBorderList().add(k44);
			k43.getBorderList().add(k45);
			k43.getBorderList().add(k55);
			k43.getBorderList().add(k56);
		}
		
		if(k44 != null){
			k44.getBorderList().add(k41);
			k44.getBorderList().add(k43);
			k44.getBorderList().add(k45);
		}
		
		if(k45 != null){
			k45.getBorderList().add(k44);
			k45.getBorderList().add(k43);
			k45.getBorderList().add(k56);
			k45.getBorderList().add(k57);
		}
		
		//Montag
		if(k46 != null){
			k46.getBorderList().add(k47);
			k46.getBorderList().add(k48);
			k46.getBorderList().add(k49);
			k46.getBorderList().add(k53);
		}
		
		if(k47 != null){
			k47.getBorderList().add(k22);
			k47.getBorderList().add(k46);
			k47.getBorderList().add(k31);
			k47.getBorderList().add(k28);
			k47.getBorderList().add(k27);
			k47.getBorderList().add(k24);
			k47.getBorderList().add(k53);
		}
		
		if(k48 != null){
			k48.getBorderList().add(k46);
			k48.getBorderList().add(k49);
			k48.getBorderList().add(k50);
		}
		
		if(k49 != null){
			k49.getBorderList().add(k46);
			k49.getBorderList().add(k48);
			k49.getBorderList().add(k50);
			k49.getBorderList().add(k52);
			k49.getBorderList().add(k53);
		}
		
		if(k50 != null){
			k50.getBorderList().add(k49);
			k50.getBorderList().add(k48);
			k50.getBorderList().add(k52);
		}
		
		//Levantia
		if(k51 != null){
			k51.getBorderList().add(k57);
			k51.getBorderList().add(k56);
			k51.getBorderList().add(k55);
			k51.getBorderList().add(k53);
		}
		
		if(k52 != null){
			k52.getBorderList().add(k50);
			k52.getBorderList().add(k49);
			k52.getBorderList().add(k53);
		}
		
		if(k53 != null){
			k53.getBorderList().add(k52);
			k53.getBorderList().add(k49);
			k53.getBorderList().add(k46);
			k53.getBorderList().add(k47);
			k53.getBorderList().add(k31);
			k53.getBorderList().add(k54);
			k53.getBorderList().add(k55);
			k53.getBorderList().add(k51);
		}
		
		if(k54 != null){
			k54.getBorderList().add(k53);
			k54.getBorderList().add(k31);
			k54.getBorderList().add(k35);
			k54.getBorderList().add(k55);
		}
		
		if(k55 != null){
			k55.getBorderList().add(k53);
			k55.getBorderList().add(k54);
			k55.getBorderList().add(k35);
			k55.getBorderList().add(k34);
			k55.getBorderList().add(k43);
			k55.getBorderList().add(k56);
			k55.getBorderList().add(k51);
		}
		
		if(k56 != null){
			k56.getBorderList().add(k51);
			k56.getBorderList().add(k55);
			k56.getBorderList().add(k43);
			k56.getBorderList().add(k45);
			k56.getBorderList().add(k57);
		}
		
		if(k57 != null){
			k57.getBorderList().add(k51);
			k57.getBorderList().add(k56);
			k57.getBorderList().add(k45);
		}
		
		if(k1 != null) kingdomList.add(k1);
		if(k2 != null) kingdomList.add(k2);
		if(k3 != null) kingdomList.add(k3);
		if(k4 != null) kingdomList.add(k4);
		if(k5 != null) kingdomList.add(k5);
		if(k6 != null) kingdomList.add(k6);
		if(k7 != null) kingdomList.add(k7);
		if(k8 != null) kingdomList.add(k8);
		
		if(k9 != null) kingdomList.add(k9);
		if(k10 != null) kingdomList.add(k10);
		if(k11 != null) kingdomList.add(k11);
		if(k12 != null) kingdomList.add(k12);
		if(k13 != null) kingdomList.add(k13);
		
		if(k14 != null) kingdomList.add(k14);
		if(k15 != null) kingdomList.add(k15);
		if(k16 != null) kingdomList.add(k16);
		if(k17 != null) kingdomList.add(k17);
		if(k18 != null) kingdomList.add(k18);
		
		if(k19 != null) kingdomList.add(k19);
		if(k20 != null) kingdomList.add(k20);
		if(k21 != null) kingdomList.add(k21);
		if(k22 != null) kingdomList.add(k22);
		if(k23 != null) kingdomList.add(k23);
		if(k24 != null) kingdomList.add(k24);
		
		if(k25 != null) kingdomList.add(k25);
		if(k26 != null) kingdomList.add(k26);
		if(k27 != null) kingdomList.add(k27);
		if(k28 != null) kingdomList.add(k28);
		if(k29 != null) kingdomList.add(k29);
		if(k30 != null) kingdomList.add(k30);
		if(k31 != null) kingdomList.add(k31);
		if(k32 != null) kingdomList.add(k32);
		if(k33 != null) kingdomList.add(k33);
		if(k34 != null) kingdomList.add(k34);
		if(k35 != null) kingdomList.add(k35);
		
		if(k36 != null) kingdomList.add(k36);
		if(k37 != null) kingdomList.add(k37);
		if(k38 != null) kingdomList.add(k38);
		if(k39 != null) kingdomList.add(k39);
		if(k40 != null) kingdomList.add(k40);
		if(k41 != null) kingdomList.add(k41);
		if(k42 != null) kingdomList.add(k42);
		
		if(k43 != null) kingdomList.add(k43);
		if(k44 != null) kingdomList.add(k44);
		if(k45 != null) kingdomList.add(k45);
		
		if(k46 != null) kingdomList.add(k46);
		if(k47 != null) kingdomList.add(k47);
		if(k48 != null) kingdomList.add(k48);
		if(k49 != null) kingdomList.add(k49);
		if(k50 != null) kingdomList.add(k50);
		
		if(k51 != null) kingdomList.add(k51);
		if(k52 != null) kingdomList.add(k52);
		if(k53 != null) kingdomList.add(k53);
		if(k54 != null) kingdomList.add(k54);
		if(k55 != null) kingdomList.add(k55);
		if(k56 != null) kingdomList.add(k56);
		if(k57 != null) kingdomList.add(k57);
		
		for(Kingdom k :kingdomList){
			k.setCityManagement(null);
		}
		
		//Rastreo null
		for(int i = 0; i < kingdomList.size(); i++){
			for(int j = 0; j < kingdomList.get(i).getBorderList().size(); j++){
				if(kingdomList.get(i).getBorderList().get(j) == null){
					kingdomList.get(i).getBorderList().remove(j);
					j--;
				}
			}
		}
		
		return kingdomList;
	}

}
