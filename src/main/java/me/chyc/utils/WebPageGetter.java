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

/**
 * Created by chyc on 8/11/14.
 */
public class WebPageGetter {
    public static List<Pair<String, Integer>> proxies = new ArrayList<Pair<String, Integer>>();

    static {
        try {
            proxies = CrawlerProxyGetter.Getter(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getWebPagewithRandomProxy(String url) throws IOException {
        if (proxies == null || proxies.size() == 0)
            return null;
        int size = proxies.size();
        Pair<String, Integer> proxy = proxies.get((int) (System.currentTimeMillis() % size));
        System.out.println(proxy.toString());
        return getWebPagewithProxy(url, proxy.value1, proxy.value2);
    }

    public static String getWebPage(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
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

    public static String getWebPage2(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public static String getWebPage(String url, String encode) throws Exception {
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

    public static String getWebPagewithDefaultProxy(String url) throws IOException {
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

    public static String getWebPagewithDefaultProxy(String url, String encode) throws IOException {
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

    public static String getWebPagewithProxy(String url, String host, int port) throws IOException {
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

    public static String getWebPagewithProxy(String url, String host, int port, String encode) throws IOException {
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

    public static String getWebPagewithProxy(String url, String host, int port, String user, String password) throws IOException {
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

    public static String getWebPagewithProxy(String url, String host, int port, String user, String password, String encode) throws IOException {
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


    public static void main(String args[]) throws Exception {

    }
}
