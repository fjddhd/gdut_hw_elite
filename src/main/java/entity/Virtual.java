package entity;

public class Virtual {
    private int id;
    private String typeName;
    private int requiredCPU;
    private int requiredMem;
    private int isDouble;
    private int deployedServerId;

    public Virtual(int id, String typeName, int requiredCPU, int requiredMem, int isDouble, int deployedServerId) {
        this.id = id;
        this.typeName = typeName;
        this.requiredCPU = requiredCPU;
        this.requiredMem = requiredMem;
        this.isDouble = isDouble;
        this.deployedServerId = deployedServerId;
    }

    public Virtual(int id, String typeName, int requiredCPU, int requiredMem, int isDouble) {
        this.id = id;
        this.typeName = typeName;
        this.requiredCPU = requiredCPU;
        this.requiredMem = requiredMem;
        this.isDouble = isDouble;
        deployedServerId=-1;
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

    public int getRequiredCPU() {
        return requiredCPU;
    }

//    public void setRequiredCPU(int requiredCPU) {
//        this.requiredCPU = requiredCPU;
//    }

    public int getRequiredMem() {
        return requiredMem;
    }

//    public void setRequiredMem(int requiredMem) {
//        this.requiredMem = requiredMem;
//    }

    public int getIsDouble() {
        return isDouble;
    }

//    public void setIsDouble(int isDouble) {
//        this.isDouble = isDouble;
//    }

    public int getDeployedServerId() {
        return deployedServerId;
    }

    //-TODO
    public void setDeployedServerId(int deployedServerId) {
        this.deployedServerId = deployedServerId;
    }
}
