package com.luis.strategy.map;

import com.luis.strategy.constants.GameParams;

public class VictoryPoints {

    public int getBattlePoints(Player player){
        int points =
                (player.getWin() + player.getBigWin()*2);
        if(points > 0){
            return points*10;
        }else{
            return 0;
        }
    }

    public int getGoldPoints(Player player){
        int troopPoints = 0;
        for(Army a : player.getArmyList()){
            for(Troop t : a.getTroopList()){
                troopPoints+= GameParams.TROOP_COST[t.getType()];
            }
        }
        return player.getGold()/10 + player.getTaxes() + troopPoints/10;
    }

    public int getCityPoints(Player player){
       int cityPoints = 0;
       for(Kingdom k: player.getKingdomList()){
            if(k.isACity()){
                for(Building b : k.getCityManagement().getBuildingList()){
                    cityPoints += (b.getActiveLevel()+1);
                }
            }
        }
        return cityPoints*100;
    }

    public int getTotalPoints(Player player){
        return getBattlePoints(player) + getGoldPoints(player) + getCityPoints(player);
    }

}
