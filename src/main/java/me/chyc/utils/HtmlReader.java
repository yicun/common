package me.chyc.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chyc on 8/11/14.
 */
public class HtmlReader {
    public static String SOURCE_URL = "url";
    public static String ENCODE = "encode";
    public static String PROXY = "proxy";
    public static String PROXY_HOST = "proxy_host";
    public static String PROXY_PORT = "proxy_port";
    public static String PROXY_USER = "proxy_user";
    public static String PROXY_PASSWORD = "proxy_password";


    private List<Pair<String, Integer>> proxies = new ArrayList<Pair<String, Integer>>();

    public HtmlReader(String url) {
        try {
            proxies = CrawlerProxyGetter.Getter(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read(Map<String, String> paraInfos) throws Exception {
        String url = getString(paraInfos, SOURCE_URL, null);
        if (url == null)
            return null;

        String encode = getString(paraInfos, SOURCE_URL, "UTF-8");

        if (getBoolean(paraInfos, PROXY, false)) {
            String host = getString(paraInfos, PROXY_HOST, null);
            int port = getInt(paraInfos, PROXY_PORT, 8080);
            if (host == null)
                return read(url, encode);
            String user = getString(paraInfos, PROXY_USER, null);
            String password = getString(paraInfos, PROXY_PASSWORD, null);
            if (url == null || password == null)
                return read(url, host, port, encode);
            else
                return read(url, host, port, user, password, encode);

        } else
            return read(url, encode);
    }

    public String getString(Map<String, String> paraInfos, String key, String defaultVal) {
        if (paraInfos.containsKey(key))
            return paraInfos.get(key);
        else return defaultVal;
    }

    public int getInt(Map<String, String> paraInfos, String key, int defaultVal) {
        if (paraInfos.containsKey(key)) {
            try {
                return Integer.valueOf(paraInfos.get(key));
            } catch (NumberFormatException e) {
                return defaultVal;
            }
        } else
            return defaultVal;
    }


    public boolean getBoolean(Map<String, String> paraInfos, String key, boolean defaultVal) {
        if (paraInfos.containsKey(key))
            return paraInfos.get(key).equals("true");
        else
            return defaultVal;
    }

    public String read(String url, String encode) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encode));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public String readwithDefaultProxy(String url) throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("proxy.fudan.edu.cn", 8080),
                new UsernamePasswordCredentials("11210240004", "chyc6278"));
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        HttpHost proxy = new HttpHost("proxy.fudan.edu.cn", 8080);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config);

        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "GBK"));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public String readwithDefaultProxy(String url, String encode) throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("proxy.fudan.edu.cn", 8080),
                new UsernamePasswordCredentials("11210240004", "chyc6278"));
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        HttpHost proxy = new HttpHost("proxy.fudan.edu.cn", 8080);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config);

        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encode));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public String read(String url, String host, int port) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpHost proxy = new HttpHost(host, port);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config);
        System.out.println(httpget.getConfig().getProxy().toString());
        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public String read(String url, String host, int port, String encode) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpHost proxy = new HttpHost(host, port);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config);
        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encode));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public String read(String url, String host, int port, String user, String password) throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(host, port),
                new UsernamePasswordCredentials(user, password));
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        HttpHost proxy = new HttpHost(host, port);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config);

        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "GBK"));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public String read(String url, String host, int port, String user, String password, String encode) throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(host, port),
                new UsernamePasswordCredentials(user, password));
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        HttpHost proxy = new HttpHost(host, port);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(config);

        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encode));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public void main(String args[]) throws Exception {

    }
}
