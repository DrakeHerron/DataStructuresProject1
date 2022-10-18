public class Request {
    private String ID;
    private long arrivalTime;
    private double priority;
    
    public Request(){
        this.ID = "Sample";
        this.arrivalTime = 0;
        this.priority = 0;
    }
    public Request(String ID, long arrivalTime, double priority){
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }
    public Request(String line, int lineNum){
        String[] splits = line.split(" ");
        if(splits.length == 3) {
            try {
                this.ID = splits[0];
                this.arrivalTime = Long.parseLong(splits[1]);
                this.priority = Double.parseDouble(splits[2]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                this.ID = "ErrorOnLine " + lineNum + " - Numbers";
                this.arrivalTime = 0;
                this.arrivalTime = 0;
            }
        }
        //If file has spaces in ID, Removes Spaces
        else {
            try {
                this.priority = splits.length - 1;
                this.arrivalTime = splits.length - 2;
                String temp = "";
                for (int i = 0; i < splits.length - 2; i++) {
                    temp = temp + splits[i];
                }
                this.ID = temp;
            } catch (Exception e) {
                e.printStackTrace();
                this.ID = "ErrorOnLine " + lineNum + " - Spacing";
                this.arrivalTime = 0;
                this.arrivalTime = 0;
            }
    
        }
    }
    
    public String getID(){ return this.ID; }
    public long getArrivalTime(){ return this.arrivalTime; }
    public double getPriority(){ return this.priority; }
    
    public boolean reOrder(double priority){
        this.priority = priority;
        return true;
    }
    
    public String toString(){
        return ID + " " + arrivalTime + " " + priority;
    }
}
