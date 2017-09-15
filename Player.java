package bean;

import service.notify.CenterController;

import java.util.Observable;

public class Player extends Observable {

    private String Playerid;
    private String type;
    private String Weapen;
    private String palyername;
    private String blood;
    private String bullet;
    private String record;
    private CenterController controller;

    public Player(){};


    public Player(String name,String type,CenterController controller){
        this.Playerid=Playerid;
        this.type=type;
        this.controller=controller;

        controller.attach(this);
    }

    public void setPlayerid(String playerid) {
        Playerid = playerid;
    }

    public String getPlayerid() {
        return Playerid;
    }

    public String getWeapen() {
        return Weapen;
    }

    public void setWeapen(String weapen) {
        Weapen = weapen;
    }

    public String getPalyername() {
        return palyername;
    }

    public void setPalyername(String palyername) {
        this.palyername = palyername;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void displayTeam(String name){
        this.palyername=name;
    }

    public void displayEnemy(String name){
        this.palyername=name;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getBullet() {
        return bullet;
    }

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }



}
