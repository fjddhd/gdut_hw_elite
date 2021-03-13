//package com.huawei.java.main;

import entity.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * public static int N=-1;//可以采购的服务器类型数量
 *     public static HashMap<String,List<Integer>> hmForN_serverType;//(型号 | CPU核数,内存大小,硬件成本,每日能耗成本)
 *     public static int M=-1;//可以出售的虚拟机类型数量
 *     public static HashMap<String,List<Integer>> hmForM_virtualType;//(型号 | CPU核数,内存大小,是否双节点部署)
 *     public static int T=-1;//天数
 *     /**
 *      * - 共T天，每天R条数据
 *      * - 每条数据为一个List：
 *      * -- size=1 表示del 虚拟机id； size=2表示 add ，虚拟机id，虚拟机型号
 *      * */
 /*
 *      *public static List<List<List<String>>>allTDayActList;
 * */
 /***
  * 必须要在initial()方法执行完毕后才可使用以上变量
  * */
public class OurAlgorithm extends Base {
    public static List<Server> serverList;
    /**
     *-input: 每天cpu需求总量，每天内存需求总量
     *-output: 类型（String）、优先级（Integer）、数量（Integer）
     * */
    /**
     * 异型性构建所有Server对象之前的对应优先级计算
     * */
    public List<List> purchaseScheme1(List<Integer> cpus,List<Integer> mems){
        List<String> serverTypes=new ArrayList();
        List<Integer> serverPriority=new ArrayList();
        List<Integer> serverNum=new ArrayList();
        /**
         *
         * */


        List<List> LL=new ArrayList();
        LL.add(serverTypes);
        LL.add(serverPriority);
        LL.add(serverNum);
        return LL;
    }

    /**
     * -对输出进行处理：
     *  生成一个包含服务器对象的列表，按照优先级从大到小排序
     * */
    public void purchaseServer(){
        List<Integer> cpus=new ArrayList<>();
        List<Integer> mems=new ArrayList<>();
        for (int i = 0; i < allTDayActList.size(); i++) {
            List<List<String>> dayAct = allTDayActList.get(i);
            int dayAllCpu=0;int dayAllMem=0;
            for (int j = 0; j < dayAct.size(); j++) {
                List<String> listforthisDay = dayAct.get(i);
                if (listforthisDay.size()>=2){
                    List<Integer> listforthisTypeVirtual = hmForM_virtualType.get(listforthisDay.get(1));
                    dayAllCpu+=listforthisTypeVirtual.get(0);
                    dayAllMem+=listforthisTypeVirtual.get(1);
                }
            }
            cpus.add(dayAllCpu);mems.add(dayAllMem);
        }
        List<List> LL=purchaseScheme1(cpus,mems);
        List<String> serverTypes=LL.get(0);
        List<Integer> serverPriority=LL.get(1);
        List<Integer> serverNum=LL.get(2);

        serverList=new ArrayList<>();
        for (int i=0;i<serverNum.size();++i) {
            String typeForThisServer=serverTypes.get(i);
            List<Integer> serverProplist = hmForN_serverType.get(typeForThisServer);
            serverList.add(new Server(serverTypes.get(i),serverProplist.get(0),serverProplist.get(1),serverPriority.get(i)));
        }
        Collections.sort(serverList, new Comparator<Server>() {
            @Override
            public int compare(Server o1, Server o2) {
                return o2.getPriority()-o1.getPriority();
            }
        });
    }
}
