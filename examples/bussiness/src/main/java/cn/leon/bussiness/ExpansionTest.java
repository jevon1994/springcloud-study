package cn.leon.bussiness;

import java.util.HashMap;

public class ExpansionTest {
    public static void main(String[] args) {
        int hash = hash("0f31bcadb525448e9af9ad1feba34845/test.zip");
    }

    public static int hash(Object key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
