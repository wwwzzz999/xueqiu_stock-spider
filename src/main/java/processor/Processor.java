package processor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import util.HttpUtil;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Processor {
    public void getStockMessage(){
        File file = new File("F://桌面//temp//stock_22.csv");
        try {
            BufferedWriter writer = null;
            OutputStream os = new FileOutputStream(file);
            OutputStreamWriter out = new OutputStreamWriter(os,"GBK");
            writer = new BufferedWriter(out);
//            设置表头

            writer.write("股票名,资产负债率,资产负债率,资产负债率,资产负债率,资产负债率,资产合计,资产合计,资产合计,资产合计,资产合计,负债合计,负债合计,负债合计,负债合计,负债合计,非流动负债合计,非流动负债合计,非流动负债合计,非流动负债合计,非流动负债合计,长期借款,长期借款,长期借款,长期借款,长期借款,流动负债合计,流动负债合计,流动负债合计,流动负债合计,流动负债合计,短期借款,短期借款,短期借款,短期借款,短期借款,股东权益合计,股东权益合计,股东权益合计,股东权益合计,股东权益合计");
            writer.newLine();
            for (int i = 0; i < 8 ;i++){
                if (i ==0) writer.write("股票名,2021,2020,2019,2018,2017");
                else writer.write(",2021,2020,2019,2018,2017");
            }
            writer.newLine();
            List<String> stockId=getId();
            for (String id : stockId){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Object[] rowData =new Object[41];
                List<Object> rowData = new ArrayList<Object>(41);
                String url = "https://stock.xueqiu.com/v5/stock/finance/cn/indicator.json?symbol="+id+"&type=Q4&is_detail=true&count=5&timestamp=";
                JSONObject json= JSONObject.parseObject(HttpUtil.sendHttp(url));
                String url_2 = "https://stock.xueqiu.com/v5/stock/finance/cn/balance.json?symbol="+id+"&type=Q4&is_detail=true&count=5&timestamp=";
                JSONObject json_2=JSONObject.parseObject(HttpUtil.sendHttp(url_2));
                String name=(String) JSONPath.eval(json,"$.data.quote_name");
//                rowData.add(name);
                writer.write(name);
                for (int i=0;i<5;i++){
                    BigDecimal asset_liab_ratio= (BigDecimal) JSONPath.eval(json,"$.data.list["+i+"].asset_liab_ratio[0]");
                    rowData.add(asset_liab_ratio);
                }
                for (int i=0;i<5;i++){
                    BigDecimal total_assets=(BigDecimal) JSONPath.eval(json_2,"$.data.list["+i+"].total_assets[0]");
                    rowData.add(total_assets);
                }
                for (int i=0;i<5;i++){
                    BigDecimal total_liab=(BigDecimal) JSONPath.eval(json_2,"$.data.list["+i+"].total_liab[0]");
                    rowData.add(total_liab);
                }

                for (int i=0;i<5;i++){
                    BigDecimal total_current_liab=(BigDecimal) JSONPath.eval(json_2,"$.data.list["+i+"].total_current_liab[0]");
                    rowData.add(total_current_liab);
                }

                for (int i=0;i<5;i++){
                    BigDecimal lt_loan=(BigDecimal) JSONPath.eval(json_2,"$.data.list["+i+"].lt_loan[0]");
                    rowData.add(lt_loan);
                }

                for (int i=0;i<5;i++){
                    BigDecimal total_current_liab=(BigDecimal) JSONPath.eval(json_2,"$.data.list["+i+"].total_current_liab[0]");
                    rowData.add(total_current_liab);
                }

                for (int i=0;i<5;i++){
                    BigDecimal st_loan=(BigDecimal) JSONPath.eval(json_2,"$.data.list["+i+"].st_loan[0]");
                    rowData.add(st_loan);
                }

                for (int i=0;i<5;i++){
                    BigDecimal total_holders_equity=(BigDecimal) JSONPath.eval(json_2,"$.data.list["+i+"].total_holders_equity[0]");
                    rowData.add(total_holders_equity);
                }





                for(Object i:rowData){
                    if(i==null){
                        writer.write(","+" ");
                    }
                   else writer.write(","+i.toString());
                }

            writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getId(){
        List<String> list = new ArrayList<String>();
        String s=HttpUtil.sendHttp("https://stock.xueqiu.com/v5/stock/forum/stocks.json?ind_code=BK0031");
        JSONObject json=JSONObject.parseObject(s);
        list = (List<String>) JSONPath.eval(json,"$.data.items.symbol");
//////        test
//        for(int i=0; i<10; i++){
//            list.remove(i);
//        }
////
//////
        return list;
    }
}
