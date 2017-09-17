package DAO;

import bean.Userinfo;
import redis.clients.jedis.Jedis;
import until.redis.JedisPool;
import until.redis.Redis;

public class UserinfoDAO {

	public Userinfo verify(Userinfo u) throws Exception {
		//根据phoneID_V从redis取得Verify
		String phoneID_V = u.getPhoneId() + "|#";
		u.setVerify(Redis.get(phoneID_V));
		return u;
	}

	public Userinfo login(Userinfo u) throws Exception {

	    //根据phoneid从redis得到 userid|password
		String[] uid_pwd = Redis.get(u.getPhoneId()).split("\\|");
        u.setUserId(uid_pwd[0]);
        u.setPassword(uid_pwd[1]);
		return u;
	}

	public Userinfo finduser(Userinfo u) throws Exception {
		//根据userid从redis取得 username|clothes|playerid|record
		u.setUsername(Redis.zrange(u.getUserId(),0,0));
		u.setClothes(Redis.zrange(u.getUserId(),1,1));
		u.setPlayerId(Redis.zrange(u.getUserId(),2,2));
		u.setRecord(Redis.zrange(u.getUserId(),3,3));
		return u;
	}



	public void saveV(Userinfo u) throws Exception {
		//注册时记录验证码 存入redis K：phoneid|# V：verify
		String phoneID_V = u.getPhoneId() + "|#";
		Redis.set(phoneID_V, u.getVerify());
	}

	public boolean saveP(Userinfo u) {
		//注册时记录手机号和密码用户名 存入redis K：phoneid V：userid|Password
		Redis.set(u.getPhoneId(), u.getUserId() + "|" + u.getPassword());
		return true;
	}


	public void save(Userinfo u) throws Exception {
		//存入redis K：用户id V：用户名、衣服、角色、战绩
		Redis.zadd(u.getUserId(), 0, u.getUsername());//用户名
		Redis.zadd(u.getUserId(), 1, u.getClothes());//衣服
		Redis.zadd(u.getUserId(), 2, u.getPlayerId());//角色
		Redis.zadd(u.getUserId(), 3, u.getRecord());//战绩

	}

	public String Cname(Userinfo u)throws Exception{
		//先删除用户名信息 存入redis K：userid V：
		String Username= Redis.zrange(u.getUserId(),0,0);//显示获取当前值
		Redis.zrem(u.getUserId(), Username);//删除当前值
		Redis.zadd(u.getUserId(),0,u.getUsername());//加入更改后的值
		return "now:"+Redis.zrange(u.getUserId(),0 ,0);
	}

	public String Cclothes(Userinfo u)throws Exception{
		//先删除用户衣服信息 存入redis K：userid V：
		String clothes = Redis.zrange(u.getUserId(), 1, 1);//显示获取当前值
		Redis.zrem(u.getUserId(), clothes);//删除当前值
		Redis.zadd(u.getUserId(), 1, u.getClothes());//再加入更改后的值
		return "now:"+Redis.zrange(u.getUserId(),1,1);
	}


}
