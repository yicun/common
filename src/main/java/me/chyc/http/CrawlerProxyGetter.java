package me.chyc.http;

import me.chyc.entity.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chyc on 8/14/14.
 */
public class CrawlerProxyGetter {
    public static void main(String args[]) throws Exception {
        List<Pair<String, Integer>> proxies = Getter("http://pachong.org");
        for (Pair<String, Integer> proxy : proxies) {
            System.out.println(proxy.toString());
        }
    }

    public static List<ProxyInfo> Getter2(String url) throws Exception {
        List<Pair<String, Integer>> list = Getter(url);
        List<ProxyInfo> proxyInfos = new ArrayList<ProxyInfo>();
        for (Pair<String, Integer> pair : list)
            proxyInfos.add(new ProxyInfo(pair.value1, pair.value2, null, null, true));
        return proxyInfos;
    }

    public static List<Pair<String, Integer>> Getter(String url) throws Exception {
        List<Pair<String, Integer>> proxies = new ArrayList<Pair<String, Integer>>();
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
//        String url = "http://pachong.org";
        if (url == null)
            url = "http://pachong.org";
        Element menu = Jsoup.parse(WebPageGetter.getWebPage(url, "UTF-8")).select("ul[class=menu]").first();
        for (Element li : menu.select("li")) {
            Element a = li.select("a[href]").first();
            if (a == null)
                continue;
            String url2 = url + a.attr("href");
//            System.out.println(url2 + "\t" + a.text());
            String html = WebPageGetter.getWebPage(url2, "UTF-8").replaceAll("&nbsp;", " ");
            Element element = Jsoup.parse(html);
            String script = null;
            for (Element scriptItem : element.select("script[type=text/javascript]")) {
                String data = scriptItem.data();
                if (data != null && data.length() != 0)
                    script = data;
            }
//            System.out.println(script);
            for (Element tr : element.select("tbody").select("tr")) {
                Elements tds = tr.select("td");
                if (tds == null || tds.size() < 3)
                    continue;
                String host = tds.get(1).text();
                if (host.matches("[\\d]+\\.[\\d]+\\.+[\\d]+\\.[\\d]+")) {
                    String script2 = tds.get(2).select("script").first().data().replaceAll("document.write", "port = ");
                    se.eval(script + " " + script2);
                    int port = Double.valueOf(se.get("port").toString()).intValue();
//                    System.out.println(host + "\t" + port);
                    proxies.add(new Pair<String, Integer>(host, port));
                }
            }
        }
        return proxies;
    }
}

