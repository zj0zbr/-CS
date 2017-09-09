package bean;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class Zoom {

    private static Map<String, Room> mroom = new HashMap<String, Room>();

    public static void hashmap(String roomid, Room room) {
        mroom.put(roomid, room);
    }

    public static Map<String, Room> getMroom() {
        return mroom;
    }

    public static void setMroom(Map<String, Room> mroom) {
        Zoom.mroom = mroom;
    }


    public static String join(String roomid,String userid, Channel ctx){

        Room nroom = mroom.get(roomid);
        nroom.hashmap(userid, ctx);
        Zoom.hashmap(roomid, nroom);
        return "";

    }

}
