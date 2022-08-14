package com.simbirsoft.page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReqresApiMethods {
    public static boolean checkValue(String data) {
        String regexp = "(<input type=\"checkbox\" name=\"removefromcart\" value=\"\\d{7}\"\\s\\/>)";
        Pattern pattern = Pattern.compile(regexp);
        boolean value = pattern.matcher(data).find();
        return value;
    }

    private static String checkQuantity(String data) {
        String regexp = "<input name=\"itemquantity\\d{7}\" type=\"text\" value=\"\\d{1,}\" class=\"qty-input\" \\/>";
        Pattern pattern = Pattern.compile(regexp);
        Matcher sdf = pattern.matcher(data);
        sdf.find();
        String quantity = sdf.group();
        return quantity;
    }

    public static Integer getQuantity(String data) {
        String regexp = "(?<=\")\\d{1,}";
        Pattern pattern = Pattern.compile(regexp);
        Matcher sdf = pattern.matcher(checkQuantity(data));
        sdf.find();
        Integer integg = Integer.valueOf(sdf.group());
        return integg;
    }
}
