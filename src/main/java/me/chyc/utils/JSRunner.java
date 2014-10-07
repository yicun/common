package me.chyc.utils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by chyc on 8/14/14.
 */

public class JSRunner {

    public static void main(String[] args) throws Exception {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
        try {

            //调用直接JAVASCRIPT语句
            se.eval("print('111');");
            String tmpstr = "test string";
            se.eval(("print('" + tmpstr + "');"));

            //调用无参数方法JAVASCRIPT函数
            se.eval("function sayHello() {"
                    + "  print('Hello '+strname+'!');return 'my name is '+strname;" + "}");
            Invocable invocableEngine = (Invocable) se;
            se.put("strname", "testname");
            String callbackvalue = (String) invocableEngine.invokeFunction("sayHello");
            System.out.println(callbackvalue);

            //调用有参数JAVASCRIPT函数
            se.eval("function sayHello2(strname2) {"
                    + "  print('Hello '+strname+'!');return 'my name is '+strname2;" + "}");
            callbackvalue = (String) invocableEngine.invokeFunction("sayHello2", "testname2");
            System.out.println(callbackvalue);

            se.eval("var cat=4288+3247;var worm=308+7437^cat;var duck=3994+7147^worm;var fish=896+7912^duck;var seal=2458+8483^fish;");

            int cat = (Integer)se.get("cat");
            System.out.println(cat);
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

