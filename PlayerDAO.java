package DAO;

import redis.clients.jedis.Jedis;
import bean.Player;
import until.redis.Redis;

public class PlayerDAO {
    Jedis jedis;

    public Player findplay(Player p) throws Exception {

        //根据playerid从redis取得 |type|clothes|blood|bullet|record
        p.setType(String.valueOf(Redis.zrange(p.getPlayerid(),0,0)));//type
        p.setBlood(String.valueOf(Redis.zrange(p.getPlayerid(),1,1)));//blood
        p.setBullet(String.valueOf(Redis.zrange(p.getPlayerid(),2,2)));//bullet
        p.setRecord(String.valueOf(Redis.zrange(p.getPlayerid(),3,3)));//record
        return p;
    }


    public String save(Player p) throws Exception {

        //存入 |type|clothes|blood|bullet|record
        Redis.zadd(p.getPlayerid(), 0, p.getType());//警匪类型
        Redis.zadd(p.getPlayerid(), 1, p.getBlood());//血量
        Redis.zadd(p.getPlayerid(), 2, p.getBullet());//子弹数
        Redis.zadd(p.getPlayerid(), 3, p.getRecord());//战绩 append追加
        return p.getType()+p.getBlood();
    }


    public String changeblo(Player p)throws Exception{

        //先删除玩家血量信息 存入redis K：playerid V：blood
        String blood = String.valueOf(Redis.zrange(p.getPlayerid(), 1, 1));//显示玩家信息
        Redis.zrem(p.getPlayerid(), blood);//删除血量信息
        Redis.zadd(p.getPlayerid(), 1, p.getBlood());//加入玩家新信息

        return p.getBlood();
    }

    public String changetype(Player p)throws Exception{

        //先删除玩家类型信息 存入redis K：playerid V：type
        String type = String.valueOf(Redis.zrange(p.getPlayerid(), 0, 0));//显示玩家信息
        Redis.zrem(p.getPlayerid(), type);//删除类型信息
        Redis.zadd(p.getPlayerid(), 0, p.getType());//加入玩家新信息

        return p.getType();
    }


}

