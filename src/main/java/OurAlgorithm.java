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
      * 服务器不够时的处理
      * */
     public static List<List> addPurchaseScheme1(int day){
         List<String> serverTypes=new ArrayList();
         List<Integer> serverPriority=new ArrayList();
         List<Integer> serverNum=new ArrayList();

         Iterator<Map.Entry<String, List<Integer>>> iterator1 = hmForN_serverType.entrySet().iterator();
         Map.Entry<String, List<Integer>> next1 = (Map.Entry)iterator1.next();
         List<Integer> value = (List)next1.getValue();
         serverTypes.add((String)next1.getKey());

         int numCpu = cpus.get(day)-cpus.get(day-1)/(Integer)value.get(0) +1;
         int numMem = mems.get(day)-mems.get(day-1)/(Integer)value.get(1) +1;
         int num=numCpu>numMem?numCpu:numMem;
         int priority = 0;
         serverNum.add(num);
         serverPriority.add(priority);

         List<List> LL=new ArrayList();
         LL.add(serverTypes);
         LL.add(serverPriority);
         LL.add(serverNum);
         return LL;
     }
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

        Iterator<Map.Entry<String, List<Integer>>> iterator1 = hmForN_serverType.entrySet().iterator();
        Map.Entry<String, List<Integer>> next1 = (Map.Entry)iterator1.next();
        List<Integer> value = (List)next1.getValue();
        serverTypes.add((String)next1.getKey());
        int numCpu = Collections.max(cpus)/(Integer)value.get(0) +1;
        int numMem = Collections.max(mems)/(Integer)value.get(1) +1;
        int num=numCpu>numMem?numCpu:numMem;
        int priority = 2;
        serverNum.add(num);
        serverPriority.add(priority);



        List<List> LL=new ArrayList();
        LL.add(serverTypes);
        LL.add(serverPriority);
        LL.add(serverNum);
        return LL;
    }

     public static List<List> purchaseScheme2(){
         List<Integer> cpus_1=new ArrayList<>(cpus);
         List<Integer> mems_1=new ArrayList<>(mems);
         int range = 10;
         int money = 0;
         List<String> serverTypes=new ArrayList();
         List<Integer> serverPriority=new ArrayList();
         List<Integer> serverNum=new ArrayList();
         /**
          *
          * */

         List<Integer> l_cpus=new ArrayList();
         List<Integer> l_mems=new ArrayList();
         Collections.sort(cpus_1);
         Collections.sort(mems_1);
         l_cpus.add(cpus_1.get(0));
         l_mems.add(mems_1.get(0));
         for(int i=1;i<cpus_1.size()/range;i++){
             l_cpus.add(cpus_1.get(i*range)-cpus_1.get((i-1)*range));
             l_mems.add(mems_1.get(i*range)-mems_1.get(i-1)*range);
         }
         if(cpus_1.size()%range != 0){
             l_cpus.add(cpus_1.get(cpus_1.size())-cpus_1.get(cpus_1.size()-cpus_1.size()%range));
             l_mems.add(mems_1.get(cpus_1.size())-mems_1.get(cpus_1.size()-cpus_1.size()%range));
         }

         int len=l_cpus.size();
         for(int i=0 ; i<len ; i++){
             double min = 100000000;
             String typeName = null;
             int num = -1;
             int numCpu = -1;
             int numMem = -1;

             Iterator<Map.Entry<String, List<Integer>>> iterator1 = hmForN_serverType.entrySet().iterator();
             while (iterator1.hasNext()) {
                 Map.Entry<String, List<Integer>> next1 = iterator1.next();
                 List<Integer> value = next1.getValue();
                 double tempa = (double)value.get(3)+(double)value.get(2)/(len-i);
                 double temp = tempa*((double)l_cpus.get(i)/value.get(0)+(double)l_mems.get(i)/value.get(1));
                 if(temp<min){
                     min=temp;
                     numCpu = l_cpus.get(i)/(Integer)value.get(0) + 1;
                     numMem = l_mems.get(i)/(Integer)value.get(1) + 1;
                     num = numCpu>numMem?numCpu:numMem;
                     typeName = (String)next1.getKey();
                 }
             }
             money+=hmForN_serverType.get(typeName).get(2)+hmForN_serverType.get(typeName).get(2)*(len-i+1);
             serverTypes.add(typeName);
             serverNum.add(num);
             serverPriority.add(len-i+1);
         }
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
    public static void purchaseServer(){
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
        List<List> LL=purchaseScheme2();
        List<String> serverTypes=LL.get(0);
        List<Integer> serverPriority=LL.get(1);
        List<Integer> serverNum=LL.get(2);

        serverList=new ArrayList<>();
        serverMap=new LinkedHashMap<>();

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
            if (lhm.containsKey(serverList.get(j).getTypeName())){
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
            outputString("---("+next.getKey()+", "+next.getValue()+")");
//                System.out.println("("+purchaseType.get(j)+", "+purchaseNumber.get(j)+")");
        }
        outputString("(migration, 0)");
    }



     public static void addPurchaseServer(List<List> LL){
        List<String> serverTypes=LL.get(0);
        List<Integer> serverPriority=LL.get(1);
        List<Integer> serverNum=LL.get(2);
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
        boolean addPurchaseForLast=true;
        for (int i = 0; i <allTDayActList.size() ; i++) {//T天
            List<String> assignList=new ArrayList<>();
            if (i>=1 && !addPurchaseForLast){
                assignList.add("(purchase, 0)");
                assignList.add("(migration, 0)");
            }else if (i>=1 && addPurchaseForLast){
                addPurchaseForLast=false;
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
                                assignList.add("("+serverList.get(k).getId()+")");
                            }else {
                                String nodeType=actVirtual.getDeployedServerNode()==1?"a":"b";
                                assignList.add("("+serverList.get(k).getId()+", "+nodeType+")");
                            }
                            break;
                        }else {
                            //调用追加服务器方法 传入天数i -TODO
                            addPurchaseServer(addPurchaseScheme1(i));
                            i--;//需要确保第一天有充足的服务器
                            continue;
                        }
                    }
                    for (int k = 0; k < assignList.size(); k++) {
                        outputString(assignList.get(k));
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
