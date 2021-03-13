//package com.huawei.java.main;

import entity.Server;
import entity.Virtual;

import java.util.*;
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
     public static LinkedHashMap<Integer, Virtual> virtualMap;//id,虚拟机对象，全量存
     public static LinkedHashMap<Integer, Server> serverMap;//id,服务器对象，全量存
     public static List<Integer> cpus;
     public static List<Integer> mems;
    /**
     *-input: 每天cpu需求总量，每天内存需求总量
     *-output: 类型（String）、优先级（Integer）、数量（Integer）
     * */
    /**
     * 异型性构建所有Server对象之前的对应优先级计算
     * */
    public static List<List> purchaseScheme1(){
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
        virtualMap=new LinkedHashMap<>();
        cpus=new ArrayList<>();
        mems=new ArrayList<>();
        int dayAllCpu=0;int dayAllMem=0;
        for (int i = 0; i < allTDayActList.size(); i++) {
            List<List<String>> dayAct = allTDayActList.get(i);
            for (int j = 0; j < dayAct.size(); j++) {
                List<String> listforthisDay = dayAct.get(j);
                if (listforthisDay.size()>=2){//add
                    List<Integer> listforthisTypeVirtual = hmForM_virtualType.get(listforthisDay.get(1));
                    Virtual addedVirtual=new Virtual(Integer.parseInt(listforthisDay.get(0)),listforthisDay.get(1),
                            listforthisTypeVirtual.get(0),listforthisTypeVirtual.get(1),listforthisTypeVirtual.get(2));
                    virtualMap.put(addedVirtual.getId(),addedVirtual);
                    dayAllCpu+=addedVirtual.getRequiredCPU();
                    dayAllMem+=addedVirtual.getRequiredMem();
                }else {//del
                    Virtual virtual = virtualMap.get(Integer.parseInt(listforthisDay.get(0)));
                    virtual.setIsDel(true);
                    dayAllCpu-=virtual.getRequiredCPU();
                    dayAllMem-=virtual.getRequiredMem();
                }
            }
            cpus.add(dayAllCpu);mems.add(dayAllMem);
        }
        List<List> LL=purchaseScheme1();
        List<String> serverTypes=LL.get(0);
        List<Integer> serverPriority=LL.get(1);
        List<Integer> serverNum=LL.get(2);

        serverList=new ArrayList<>();

        for (int i=0;i<serverNum.size();++i) {
            String typeForThisServer=serverTypes.get(i);
            List<Integer> serverProplist = hmForN_serverType.get(typeForThisServer);
            for (int j=0;j<serverNum.get(i);++j) {
                Server generatedServer = new Server(serverTypes.get(i),
                        serverProplist.get(0), serverProplist.get(1), serverPriority.get(i));
                serverMap.put(generatedServer.getId(),generatedServer);
                serverList.add(generatedServer);
            }
        }
        Collections.sort(serverList, new Comparator<Server>() {
            @Override
            public int compare(Server o1, Server o2) {
                return o2.getPriority()-o1.getPriority();
            }
        });

        //第一天输出
        LinkedHashMap<String,Integer> lhm=new LinkedHashMap();
        for (int j = 0; j < serverList.size(); j++) {
            if (lhm.containsKey(serverList.get(j))){
                lhm.put(serverList.get(j).getTypeName(),lhm.get(serverList.get(j).getTypeName())+1);
            }else {
                lhm.put(serverList.get(j).getTypeName(),1);
            }
        }
        outputString("(purchase, "+lhm.size() +")");
//            System.out.println("(purchase, "+purchaseNumber.size()+")");
        Iterator<Map.Entry<String, Integer>> iterator_lhm = lhm.entrySet().iterator();
        while (iterator_lhm.hasNext()) {
            Map.Entry<String, Integer> next = iterator_lhm.next();
            outputString("("+next.getKey()+", "+next.getValue()+")");
//                System.out.println("("+purchaseType.get(j)+", "+purchaseNumber.get(j)+")");
        }
        outputString("(migration, 0)");
    }

    public static void computeAssignment(){
        for (int i = 0; i <allTDayActList.size() ; i++) {//T天
            if (i>=1){
                outputString("(purchase, 0)");
                outputString("(migration, 0)");
            }
            List<List<String>> thisDayActLists = allTDayActList.get(i);
            for (int j = 0; j < thisDayActLists.size(); j++) {//当天每个活动
                List<String> act = thisDayActLists.get(j);
                int actVirtId= Integer.parseInt(act.get(0));
                Virtual actVirtual = virtualMap.get(actVirtId);
                if (act.size()>=2){//add活动
                    for (int k = 0; k < serverList.size(); k++) {
                        if (serverList.get(k).setVirtual(actVirtual)){
                            int isDouble = actVirtual.getIsDouble();
                            if (isDouble==1){
                                outputString("("+serverList.get(k).getId()+")");
                            }else {
                                String nodeType=actVirtual.getDeployedServerNode()==1?"a":"b";
                                outputString("("+serverList.get(k).getId()+", "+nodeType+")");
                            }
                            break;
                        }else {
                            //调用追加服务器方法 传入天数i -TODO

                        }
                    }
                }else {//del活动
                    int actVirtDeployedServerId = actVirtual.getDeployedServerId();
                    Server actServer = serverMap.get(actVirtDeployedServerId);
                    if (actServer.delVirtual(actVirtual)) {

                    }else {
                        System.err.println("移除服务器出错");
                    }
                }
            }
        }
    }
}
