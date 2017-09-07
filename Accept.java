package Handle;

import service.PlayerController;
import service.RoomContoler;
import service.UserinfoController;
import io.netty.channel.Channel;
import service.notify.CenterController;


public class Accept  {
    private Channel ctx;
    private String[] Requst;
    private String result;
    public Accept(String[] a , Channel ctx) {
        this.Requst=a;
        this.ctx=ctx;
    }
    public String loginRequst() throws Exception {

        UserinfoController uc=new UserinfoController();
        RoomContoler rc=new RoomContoler();
        PlayerController pc=new PlayerController();
        if (Requst[0].equals("verify")){
            result=uc.Verify(Integer.parseInt(Requst[1]));//手机号  接受结果为验证码
            return result;
        }
        else if(Requst[0].equals("Registe")){
            result= String.valueOf(uc.regist(Requst[1],Requst[2],Requst[3]));//1 手机号 2 密码 3 验证码
            return result;
        }
        else if(Requst[0].equals("Login")) {
            result = String.valueOf(uc.login(Requst[1], Requst[2]));//1手机号  2密码
            return result;
        }
        else if(Requst[0].equals("Cname")){
            result= uc.Cname(Requst[1],Requst[2]);//1 userid 2 要更改的名字
            return result;
        }
        else if (Requst[0].equals("Cclothes")) {
            result = uc.Cclothes(Requst[1], Requst[2]);//1 UserID ,2 要更改的Clothes(颜色)
            return result;
        }
        else if (Requst[0].equals("GetUserinfo")) {
            result = uc.Getuserinfo(Requst[1]);// 根据userid 取出用户信息 放到rusult[]里
            return result;
        }
        else if (Requst[0].equals("Create")) {
            result= rc.Creat(Requst[1],ctx);//1 userid 2 ctx
            return result;
        }
        else if (Requst[0].equals("Join")) {
            result= rc.Join(Requst[1],Requst[2],ctx);// 1 roomid  2 userid  3 ctx
            return result;
        }
        else if(Requst[0].equals("setup")){
            result= String.valueOf(rc.setup(Requst[1]));//1 roomid
            return result;
        }
        else if(Requst[0].equals("Getplayerinfo")){
            result= pc.Getplayerinfo(Requst[1]);//1 userid
            return result;
        }
        else if(Requst[0].equals("removeblood")){
            result= String.valueOf(pc.removeblood(Requst[1],Requst[2],Requst[3]));//1 userid ,2roomid, 3 clothes
            return result;
        }
        else if(Requst[0].equals("changetype")){
            result= String.valueOf(pc.changetype(Requst[1],Requst[2]));//1 userid ,2 type
            return result;
        }

        pc.removebullet(Requst[1]);//userid  重做

        return "aa";

    }


}
