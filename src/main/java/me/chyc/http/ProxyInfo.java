package me.chyc.http;

/**
 * Created by yicun.chen on 10/20/14.
 */
public class ProxyInfo {
    private String host;
    private int port;
    private String user;
    private String password;
    private boolean enable;


    public ProxyInfo(String host, int port, String user, String password, boolean enable) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.enable = enable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable(){
        return enable;
    }

    @Override
    public String toString() {
        return "ProxyInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                '}';
    }
}
