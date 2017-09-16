package service.notify;


import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import bean.Room;
import bean.Zoom;

import java.util.ArrayList;
import java.util.Observable;



public class CenterController {
    private ArrayList <String> players;
//    private Player player;
    private Room r;

    private ReadColor readColor;

    public void detach(Observable observable){}

    public void attach(Observable observable){}

    public void blood(Observable observable){

    }

    public void doPost(int flag,String some) throws Exception {
        switch (flag) {
            case 0:
                notifyPlayers(some);
                break;
            case 1:
                notifysetup(some);
                break;
            case 2:
                notifyblood(some);
                break;
            case 3:
                notifydead(some);
        }
    }


    //加入房间通知 返回用户id进入
    public void notifyPlayers(String roomid){
        //从Zoom的 haspmap中取出 room
        Room room=Zoom.getMroom().get(roomid);

        for (String key : room.getMchannel().keySet()) {
            String userid = String.valueOf(room.getMchannel().keySet());
            Channel channel = room.getMchannel().get(key);//获取channel
            ByteBuf encoded1 = channel.alloc().buffer(userid.length());//定义缓冲区容量
            encoded1.writeBytes(userid.getBytes());//将用户名刷回去
            channel.write(encoded1);//将加入的玩家返回
            channel.flush();
            //测试
            System.out.println("key= " + key + " and value= " + room.getMchannel().get(key));

        }
    }

    public void notifysetup(String roomid) {
        Room room = Zoom.getMroom().get(roomid);

        for (String key : room.getMchannel().keySet()) {
            String setup = "start game";
            Channel channel = room.getMchannel().get(key);//获取channel
            ByteBuf encoded = channel.alloc().buffer(setup.length());//定义缓冲区容量
            encoded.writeBytes(setup.getBytes());
            channel.write(encoded);//将开始游戏告诉大家
            channel.flush();
            //测试
            System.out.println("key= " + key + " and value= " + room.getMchannel().get(key));
        }
    }

    //减血通知  返回减血后血量
    public void notifyblood(String some) {
        String[] roomid_blood=some.toString().split("\\|");
        String roomid=roomid_blood[0];
        Room room = Zoom.getMroom().get(roomid);

        for (String key : room.getMchannel().keySet()) {
            String blood=roomid_blood[1]+roomid_blood[2];
            Channel channel = room.getMchannel().get(key);//获取channel
            ByteBuf encoded = channel.alloc().buffer(blood.length());//定义缓冲区容量
            encoded.writeBytes(blood.getBytes());
            channel.write(encoded);//将血量刷回去
            channel.flush();
            //测试
            System.out.println("key= " + key + " and value= " + room.getMchannel().get(key));
        }
    }

    //玩家死亡通知 返回死亡userid
    public void notifydead(String some){
        String[] roomid_userid=some.toString().split("\\|");
        String roomid=roomid_userid[0];
        Room room = Zoom.getMroom().get(roomid);

        for (String key : room.getMchannel().keySet()) {
            String userid =roomid_userid[1]+"is died";
            Channel channel = room.getMchannel().get(key);//获取channel
            ByteBuf encoded = channel.alloc().buffer(userid.length());//定义缓冲区容量
            encoded.writeBytes(userid.getBytes());
            channel.write(encoded);//将死亡玩家刷回去
            channel.flush();
            //测试
//            System.out.println("key= " + key + " and value= " + room.getMchannel().get(key));
        }
    }




}
