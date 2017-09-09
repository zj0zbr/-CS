package bean;

import io.netty.channel.Channel;

import java.util.HashMap;


public class Room {

    private String roomid;//用户id

    private static HashMap<String, Channel> mchannel=new HashMap<String, Channel>();

    public static HashMap hashmap(String userid, Channel ctx){
        mchannel.put(userid,ctx);
        return mchannel;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public static HashMap<String, Channel> getMchannel() {
        return mchannel;
    }

    public static void setMchannel(HashMap<String, Channel> mchannel) {
        Room.mchannel = mchannel;
    }



}

