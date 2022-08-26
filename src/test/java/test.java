import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.junit.Test;
import util.HttpUtil;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class test {
    @Test
    public void test(){
        BigDecimal a= null;
        List<Object> rowData = new ArrayList<Object>();
        rowData.add(a);
        System.out.println(rowData.get(1));

    }
}
