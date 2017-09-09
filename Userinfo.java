package bean;

public class Userinfo {

    private String PhoneId;//电话号码
    private String UserId;//用户ID
    private String verify;//验证码
    private String PlayerId;//游戏ID 战备id（包括血量，子弹，衣服颜色，）
    private String username;//用户名
    private String password;//密码
    private String clothes;//衣服颜色
    private String Record;//战绩
    private String packet;//背包


    public String getPhoneId() {
        return PhoneId;
    }

    public void setPhoneId(String phoneId) {
        PhoneId = phoneId;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getClothes() {
        return this.clothes;
    }

    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String Record) {
        this.Record = Record;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public String toString(){
        return this.username+"|"+this.clothes+"|"+this.PlayerId;
    }


}
