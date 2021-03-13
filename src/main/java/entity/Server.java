package entity;
//package com.huawei.java.main.entity;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private static int count = 0;
    private int id;
    private String typeName;
    private int maxCPU;
    private int maxMEM;
    private int aCpuUse;
    private int aMemUse;
    private int bCpuUse;
    private int bMemUse;
    private int priority;
    private List<Integer> virtualList;//id


    public Server(String i_typeName, int i_maxCPU, int i_maxMemory, int i_priority) {
        id = count++;
        typeName = i_typeName;
        maxCPU = i_maxCPU;
        maxMEM = i_maxMemory;
        priority = i_priority;
        virtualList=new ArrayList();
        aCpuUse=0;
        aMemUse =0;
        bCpuUse=0;
        bMemUse =0;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Server.count = count;
    }

    public int getId() {
        return id;
    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getTypeName() {
        return typeName;
    }

//    public void setTypeName(String typeName) {
//        this.typeName = typeName;
//    }

    public int getMaxCPU() {
        return maxCPU;
    }

//    public void setMaxCPU(int maxCPU) {
//        this.maxCPU = maxCPU;
//    }

    public int getMaxMEM() {
        return maxMEM;
    }

//    public void setMaxMEM(int maxMEM) {
//        this.maxMEM = maxMEM;
//    }

    public int getaCpuUse() {
        return aCpuUse;
    }

    public void setaCpuUse(int aCpuUse) {
        this.aCpuUse = aCpuUse;
    }

    public int getaMemUse() {
        return aMemUse;
    }

    public void setaMemUse(int aMemUse) {
        this.aMemUse = aMemUse;
    }

    public int getbCpuUse() {
        return bCpuUse;
    }

    public void setbCpuUse(int bCpuUse) {
        this.bCpuUse = bCpuUse;
    }

    public int getbMemUse() {
        return bMemUse;
    }

    public void setbMemUse(int bMemUse) {
        this.bMemUse = bMemUse;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Integer> getVirtualList() {
        return virtualList;
    }
    public void setVirtual(Virtual v){
        this.virtualList.add(v.getId());
    }

//    public void setVirtualList(List<Integer> virtualList) {
//        this.virtualList = virtualList;
//    }
}
