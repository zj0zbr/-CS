package DAO;

import bean.Userinfo;
import redis.clients.jedis.Jedis;
import bean.Player;
import bean.Room;
import until.redis.Redis;

public class RoomDAO {
    Jedis jedis;
    Player p=new Player();

    public Player findroom(String roomid, String clothes) throws Exception{
        //从redis取值 以结构 K：roomid|clothes  V：type|userid
        String room_clothes=roomid+"|"+clothes;
        String[] type_userid = Redis.get(room_clothes).split("\\|");
        p.setType(type_userid[0]+type_userid[1]);
//        u.setPlayerId(String.valueOf(Redis.zrange(u.getUserId(),3,3)));
        p.setPlayerid(type_userid[2]+"|#");
        return p;
    }

    public boolean save(String roomid ,Userinfo u,String Type) throws Exception {
        //存入redis 以结构 K：roomid|clothes  V：type|userid
        Redis.set(roomid+"|"+ u.getClothes(),Type+"|" + u.getUserId());

        return false;
    }

    public boolean saveclo(String roomid,Userinfo u){
        //存入redis 结构： K:roomid V: userid|clothes
        Redis.sadd(roomid,u.getUserId()+"|"+u.getClothes());

        return false;
    }

    //检查房间内衣服是否有重复
    public boolean checkclothes(String roomid,Userinfo u)throws Exception{
        //存入redis 结构： K:roomid V: userid|clothes
        Boolean exist=Redis.sismember(roomid,u.getUserId()+"|"+u.getClothes());

        if(!exist)
            Redis.sadd(roomid, u.getUserId()+"|"+u.getClothes());
        else
            System.out.println("cover");
        return exist;
    }

}
