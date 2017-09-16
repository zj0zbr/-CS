package service;

import bean.Userinfo;
import DAO.PlayerDAO;
import DAO.RoomDAO;
import DAO.UserinfoDAO;
import service.notify.CenterController;
import io.netty.channel.Channel;
import bean.Player;
import bean.Room;
import bean.Zoom;


public class RoomContoler {

//    private PlayerDAO pd;

    private PlayerDAO pd;
    private UserinfoDAO ud;
    private RoomDAO rd;

    private CenterController cc;

    Room room=new Room();
    Zoom zoom=new Zoom();
    Userinfo u=new Userinfo();
    Player p=new Player();
    int i=0;

    public RoomContoler() {
        pd=new PlayerDAO();
        ud=new UserinfoDAO();
        rd=new RoomDAO();
        cc=new CenterController();
    }


    //生成房间号
    public String ID() {
        String str = "0123456789";
        String string1 = "";
        for (int i = 1; i <= 4; i++) {
            String num = String.valueOf(str.charAt((int) Math.floor(Math.random() * str.length())));
            string1 += num;
            str = str.replaceAll(num, "");
        }
        String roomid = new String(string1);
        System.out.println(roomid);
        return roomid;
    }

    //创建房间
    public String Creat(String userid, Channel ctx) throws Exception {
        room.setRoomid(ID());//得到roomid传给对象
        room.hashmap(userid,ctx);
        zoom.hashmap(room.getRoomid(),room);
        u.setUserId(userid);
        rd.saveclo(room.getRoomid(),ud.finduser(u));
        return room.getRoomid();
    }

    //加入房间
    public String Join(String roomid,String userid,Channel ctx) throws Exception {
        room=zoom.getMroom().get(roomid);//获取房间类中存储用户channel信息的map 对象
        //判断map的大小
        if (room.getMchannel().size() <= 10) {

            //检查衣服是否重复
            u.setUserId(userid);

//            rd.saveclo(roomid,ud.finduser(u));
//            zoom.join(roomid, userid, ctx);
//            cc.notifyPlayers(room);
//            return u.getClothes();
            boolean exist=rd.checkclothes(roomid, ud.finduser(u));
            if(!exist){
                zoom.join(roomid, userid, ctx);
                int flag=0;
                cc.doPost(flag,roomid);
                //测试
                System.out.println("join room success");
                return "join room success";
            }
            else{//测试
                System.out.println("clothes cover");
                return "clothes cover";
            }
        }
        else {
            System.out.println("join room false");
            return "join room false";
        }

    }


    public Channel back(String roomid){

        return null;
    }

    public String setup(String roomid)throws Exception{

        int flag=1;
        room=Zoom.getMroom().get(roomid);
        cc.doPost(flag,roomid);
        if(room.getMchannel().size()%2==0) {
            for (String key : room.getMchannel().keySet()) {//遍历mchannel key即userid
                i++;
                //存入reids RoomDAO:  K:roomid|clothes  V:type|userid
                u.setUserId(key);

                //将player赋值
                String playerid = key + "|#";//赋值playerid
                p.setPlayerid(playerid);
                //设置警匪类型
                if (i % 2 == 0)
                    p.setType("0" + "|" + "police");
                else
                    p.setType("1" + "|" + "bandit");

                p.setBlood("100");//赋值血量
                p.setBullet("1000000");//赋值子弹数
                p.setRecord("00-00");//战绩
                pd.save(p);//存入redis p对象
                rd.save(roomid,ud.finduser(u),p.getType());//存入reids RoomDAO:  K:roomid|clothes  V:type|userid
            }
            return " start OK";
        }else
            return "people num single";
    }


}

