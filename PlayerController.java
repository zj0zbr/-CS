package service;

import bean.Userinfo;
import DAO.PlayerDAO;
import DAO.RoomDAO;
import DAO.UserinfoDAO;
import service.notify.CenterController;
import service.notify.ReadColor;
import bean.Player;
import bean.Room;
import until.Base64;

public class PlayerController {
    private PlayerDAO pd;
    RoomDAO rd=new RoomDAO();
    Player p = new Player();

    CenterController cc=new CenterController();

    public PlayerController(){pd=new PlayerDAO();}

    //获取用户角色信息 playerid|Type|blood|bullet
    public String Getplayerinfo(String userid) throws Exception {
        String playerid=userid+"|#";
        p.setPlayerid(playerid);
        String player=pd.findplay(p).getPlayerid()+"|"+pd.findplay(p).getType()+"|"+pd.findplay(p).getBlood()+"|"+pd.findplay(p).getBullet();
        return player;
    }

    public String removeblood(String userid,String roomid, String imgStr) throws Exception {
        //先减子弹数
        removebullet(userid);

        //将Base64 转换成图片存储到服务器本地
        Base64 b=new Base64();
        String imgFilePath=b.GenerateImage(userid+"|#",imgStr);
        //分析图片颜色 找到对应玩家 判断成功与否
        ReadColor rc = new ReadColor();
        String clo=rc.Read(imgFilePath);//返回play的clothes

        p.setPlayerid(userid+"|#");
        Player p1=new Player();
        p1=rd.findroom(roomid,clo);//从redis里取到 type 及 playerid

        if(pd.findplay(p).getType().equals(p1.getType())){//判断击杀人是否市队友如果类型一致不做处理返回队友
            System.out.println("shoot false,you are friend");
            return "shoot false,you are friend";
        }else{
            int blood=Integer.parseInt(pd.findplay(p1).getBlood());//从 playerid的reids中取得对应角色信息
            //判断血量数
            if(blood-10==0){

                //待实现 若玩家死亡则就不再进入判断
                String[] user =p.getPlayerid().split("//|");
                //成功将对手击杀记录加1
                record(userid);

                int flag=3;
                String some=roomid+"|"+userid;
                cc.doPost(flag,some);
                return "died";
            }else {
                blood -= 10;
                p1.setBlood(String.valueOf(blood));//修改redis的blood数据
                pd.changeblo(p1);

                int flag = 2;
                String some = roomid + "|" +p1.getPlayerid()+"|" +p1.getBlood();
                cc.doPost(flag, some);
                return p1.getPlayerid()+p1.getBlood();
            }
        }
    }


    //每次射击减少相应子弹
    public String removebullet(String userid) throws Exception {
        String playerid=userid+"|#";//得到playerid
        p.setPlayerid(playerid);

        int but= Integer.parseInt(pd.findplay(p).getBullet());
        but-=1;
        p.setBullet(String.valueOf(but));
        pd.save(p);
        return p.getBullet();
    }


    //改变类型信息
    public String changetype(String userid,String type)throws Exception{
        String playerid=userid+"|#";//得到playerid
        p.setPlayerid(playerid);
        p.setType(type);
        String Ct=pd.changetype(p);//返回值为修改后的类型
        return Ct;
    }


    public String record(String userid)throws Exception{
        Userinfo u=new Userinfo();
        UserinfoDAO ud=new UserinfoDAO();
        //设置userid
        u.setUserId(userid);
        int count=Integer.parseInt(ud.finduser(u).getRecord());//根据userid找到record
        count ++;//做加，又击杀一人

        ud.finduser(u).setRecord(String.valueOf(count));//将记录存到redis中
        return String.valueOf(count);
    }


}
