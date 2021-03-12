import java.io.*;
import java.util.*;
public class Main {
    public static String path="E:/huaweiElite/";
    public static List<String> list;//原始数据
    public static int N=-1;//可以采购的服务器类型数量
    public static HashMap<String,List<Integer>> hmForN_serverType;//(型号 | CPU,核数,内存大小,硬件成本,每日能耗成本)
    public static int M=-1;//可以出手的虚拟机类型数量
    public static HashMap<String,List<Integer>> hmForM_virtualType;//(型号 | CPU ,核数,内存大小,是否双节点部署)
    public static int T=-1;//天数
    /**
     * - 共T天，每天R条数据
     * - 每条数据为一个LinkedHashMap：
     * -- key：虚拟机ID
     * -- value: 为“-1” 表示del ； 否则表示 add ，值为虚拟机型号
     * */
    public static List<List<LinkedHashMap<String,String>>> allTDayActList;
    public static List<LinkedHashMap<String,String>> everyTDayActList;//临时

    /**
     * 限制参数
     * */
    public static int maxServerNum=100000;
    public static void main(String[] argv){
        initial();
        System.err.println("ok");
        compute();

    }
    /**
     * 初始化以及清洗数据
     * */
    public static void initial(){
        list = readLogByList(path + "training-1.txt");
        hmForM_virtualType =new LinkedHashMap<>();
        hmForN_serverType=new LinkedHashMap<>();
        allTDayActList=new ArrayList<>();
        everyTDayActList=new ArrayList();
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(i+" : "+list.get(i));
            String s = list.get(i);
            if (s.charAt(0)!='('){
                if (N==-1){
                    N=Integer.parseInt(s);
                    continue;
                }
                if (M==-1){
                    M=Integer.parseInt(s);
                    continue;
                }
                if (T==-1){
                    T=Integer.parseInt(s);
                    continue;
                }
                //都通过表示R，即某一天的条数，可以跳过
                allTDayActList.add(everyTDayActList);
                everyTDayActList=new ArrayList<>();
                continue;
            }else {//实际数据
                if (N==-1){
                }
                if (M==-1){
                    String[] strings = disgardBracket(s);
                    List<Integer> tempList=new ArrayList<>();//CPU,核数,内存大小,硬件成本,每日能耗成本
                    for (int j = 1; j < strings.length; j++) {
                        tempList.add(Integer.parseInt(strings[j]));
                    }
                    hmForN_serverType.put(strings[0],tempList);
                    continue;
                }
                if (T==-1){
                    String[] strings = disgardBracket(s);
                    List<Integer> tempList=new ArrayList<>();//CPU ,核数,内存大小,是否双节点部署
                    for (int j = 1; j < strings.length; j++) {
                        tempList.add(Integer.parseInt(strings[j]));
                    }
                    hmForM_virtualType.put(strings[0],tempList);
                    continue;
                }else {
                    String[] strings = disgardBracket(s);
                    LinkedHashMap<String,String> tempLH=new LinkedHashMap<>();
                    if (strings.length<3){//del
                        tempLH.put(strings[1],"-1");//虚拟机id，'-1'
                    }else {
                        tempLH.put(strings[2],strings[1]);//虚拟机id，虚拟机型号
                    }
                    everyTDayActList.add(tempLH);
                }
            }
        }
        allTDayActList.remove(0);
        allTDayActList.add(everyTDayActList);
    }
    public static String[] disgardBracket(String inputString){
        return inputString.substring(1, inputString.length() - 1).split(", ");
    }
    public static List<String> readLogByList(String path) {
        List<String> lines = new ArrayList<String>();
        String tempstr = null;
        try {
            File file = new File(path);
            if(!file.exists()) {
                throw new FileNotFoundException();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
            while((tempstr = br.readLine()) != null) {
                lines.add(tempstr.toString());
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return lines;
    }

    /**
     * 核心运算代码
     * */
    public static void compute(){

    }
}
