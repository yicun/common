package me.chyc.http;

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
import java.io.InputStreamReader;

/**
 * Created by chyc on 8/11/14.
 */
public class HttpClientTest {
    public static void main(String[] args) throws Exception {
        String url = "http://www.siluke.info/0/14/14772/4133990.html";


        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("proxy.fudan.edu.cn", 8080),
                new UsernamePasswordCredentials("11210240004", "chyc6278"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        try {
//            HttpHost target = new HttpHost(url);
            HttpHost proxy = new HttpHost("proxy.fudan.edu.cn", 8080);

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(config);




            CloseableHttpResponse response =httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
//                EntityUtils.consume(response.getEntity());
                StringBuilder sb = new StringBuilder();
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpEntity.getContent(), "GBK"));

                    String str;
                    while ((str = br.readLine()) != null) {
                        sb.append(str+"\n");
                    }
                    br.close();
                }
                System.out.println(sb.toString());
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

    }

}