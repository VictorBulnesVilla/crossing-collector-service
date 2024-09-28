package org.example;
import java.time.Instant;

public class Crossing {
    private String type;
    private Instant timeStamp;
    private int portCode;
    
    public Crossing(String type, Instant timeStamp, int portCode) {
        this.type = type;
        this.timeStamp = timeStamp;
        this.portCode = portCode;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Instant getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
    public int getPortCode() {
        return portCode;
    }
    public void setPortCode(int portCode) {
        this.portCode = portCode;
    }
    @Override
    public String toString() {
        return "Crossing [type=" + type + ", timeStamp=" + timeStamp + ", portCode=" + portCode + "]";
    }
    public String toJson(){
        return "{\"type\":\""+type+"\","
        +"\"timeStamp\":\""+timeStamp+"\","
        +"\"portCode\":"+portCode+"}";
    }
}
