import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DtoObject {
    private String param1;
    private Integer param2;
    private List<Integer> stringList;
    private HashMap<Integer,String> stringHashMap;

    public DtoObject(String param1, Integer param2, List<Integer> stringList,HashMap<Integer,String> stringHashMap) {
        this.stringHashMap= stringHashMap;
        this.param1 = param1;
        this.param2 = param2;
        this.stringList = stringList;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public Integer getParam2() {
        return param2;
    }

    public void setParam2(Integer param2) {
        this.param2 = param2;
    }

    public List<Integer> getStringList() {
        return stringList;
    }

    public void setStringList(List<Integer> stringList) {
        this.stringList = stringList;
    }

    @Override
    public String toString() {
        return "DtoObject{" +
                "param1='" + param1 + '\'' +
                ", param2=" + param2 +
                ", stringList=" + stringList +
                ", stringHashMap=" + stringHashMap +
                '}';
    }

    public void main(){
        List<DtoObject> innerClassList = new ArrayList<DtoObject>();
        ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
        integerArrayList.add(1);
        integerArrayList.add(2);
        integerArrayList.add(3);
        HashMap<Integer,String> hashMap = new HashMap<Integer, String>();
        hashMap.put(1,"deneme");
        hashMap.put(2,"deneme2");
        hashMap.put(3,"deneme3");
        innerClassList.add(new DtoObject("deneme",1,integerArrayList,hashMap));
        ArrayList<Integer> integerArrayList1 = new ArrayList<Integer>();
        integerArrayList1.add(1);
        integerArrayList1.add(2);
        integerArrayList1.add(3);
        innerClassList.add(new DtoObject("deneme2",2,integerArrayList1,hashMap));
        System.out.println(innerClassList.toString());
    }
}
