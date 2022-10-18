import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class PQ_LL {
    private LinkedList<Request> ll = new LinkedList<>();
    private int numberRequests;
    
    //Create the Linked List from file data.txt
    public PQ_LL() {
        //point at file
        File file = new File("data.txt");
        boolean created = false;
        //if file does not exist, create it
        try {
            if (file.createNewFile()) {
                created = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if the file already existed add the information to the Linked List
        if (created) {
            numberRequests = 0;
        } else {
            //Creates a Scanner to read the file
            Scanner sc = null;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Checks if file is empty
            if (sc.hasNextInt()) {
                this.numberRequests = sc.nextInt();
                sc.nextLine();
                String line;
                Request request;
                //Adds the file to the Linked List
                int lineNum = 0;
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    lineNum += 1;
                    request = new Request(line, lineNum);
                    ll.add(request);
                }
            } else {
                numberRequests = 0;
            }
        }
    }
    
    public int getNumberRequests() {
        return this.numberRequests;
    }
    
    public void updateFile() {
        //Creates a Writer for the file
        try {
            FileWriter myWriter = new FileWriter("data.txt");
            //Adds number of requests to the top of file
            myWriter.write(String.valueOf(numberRequests) + "\n");
            Request request;
            //Iterates through the Linked List and adds to the file
            for (int i = 0; i < numberRequests; i++) {
                request = ll.get(i);
                myWriter.write(request.getID() + " " + request.getArrivalTime() + " " + request.getPriority() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }
    }
    
    public String displayRequests(){
        String display = "";
        for(int i = 0; i < numberRequests; i++){
            display = display + ll.get(i).toString() + "\n";
        }
        return display;
    }
    
    public boolean addRequest(Request request) {
        ll.add(request);
        numberRequests += 1;
        return true;
    }
    
    public boolean removeRequest(String ID) {
        boolean removed = false;
        boolean search = true;
        //Begin a Search for the ID
        int i = 0;
        while (search) {
            //If the ID is Found the Request is removed
            if (ll.get(i).getID().equals(ID)) {
                ll.remove(i);
                search = false;
                //Ends Search
                removed = true;
                numberRequests -= 1;
            } else {
                i += 1;
            }
            //If ID is not found, Returns False, meaning no such ID in List
            if (i == numberRequests) {
                search = false;
            }
        }
        return removed;
    }
    
    public Request servingRequest() {
        numberRequests -= 1;
        return ll.pop();
    }
    
    public boolean bubbleSort() {
        boolean reRun = false;
        for (int i = 0; i < numberRequests - 1; i++) {
            for (int j = i + 1; j < numberRequests; j++) {
                //Swap i and j if i has a larger priority than j
                if (ll.get(i).getPriority() > ll.get(j).getPriority()) {
                    Request temp = ll.get(i);
                    ll.add(j + 1, temp);
                    ll.remove(i);
                }
                //Swap i and j if i has a larger arrival time, when priority is the same
                else if (ll.get(i).getPriority() == ll.get(j).getPriority()){
                    if (ll.get(i).getArrivalTime() > ll.get(j).getArrivalTime()){
                        Request temp = ll.get(i);
                        ll.add(j + 1, temp);
                        ll.remove(i);
                        reRun = true;
                    }
                }
            }
        }
        return reRun;
    }
    
    public String searchRequest(String ID) {
        String requests = "";
        for(int i = 0; i < numberRequests; i ++){
            if (ll.get(i).getID().equals(ID)){
                requests = requests + ll.get(i).toString() + "\n";
            }
        }
        return requests;
    }
    
    public String searchRequest(double Priority) {
        String requests = "";
        for(int i = 0; i < numberRequests; i ++){
            if (ll.get(i).getPriority() == (Priority)){
                requests = requests + ll.get(i).toString() + "\n";
            }
        }
        return requests;
    }
    
    public void changePriority(double newPriority, String request){
        String newRequest = "";
        String ID;
        long arrivalTime;
        double priority;
        String[] splits = request.split(" ");
        ID = splits[0];
        arrivalTime = Long.parseLong(splits[1]);
        priority = Double.parseDouble(splits[2]);
        for(int i = 0; i < numberRequests; i ++){
            if (ll.get(i).getID().equals(ID)){
                newRequest = newRequest + i + " ";
            }
        }
        Request temp;
        String[] newSplits = newRequest.split(" ");
        if (newSplits.length > 1){
            String newRequestTwo = "";
            for(int i = 0; i < newSplits.length; i++) {
                if (ll.get(Integer.parseInt(newSplits[i])).getArrivalTime() == (arrivalTime)) {
                    newRequestTwo = newRequestTwo + Integer.parseInt(newSplits[i]) + " ";
                }
            }
            String[] newSplitsTwo = newRequestTwo.split(" ");
            if (newSplitsTwo.length > 1){
                String newRequestThree = "";
                for(int j = 0; j < newSplitsTwo.length; j++) {
                    if (ll.get(Integer.parseInt(newSplitsTwo[j])).getPriority() == (priority)){
                        newRequestThree = newRequestThree + Integer.parseInt(newSplitsTwo[j]) + " ";
                    }
                }
                newRequestThree = newRequestThree.strip();
                temp =ll.get(Integer.parseInt(newRequestThree));
                temp.reOrder(newPriority);
                ll.remove(Integer.parseInt(newRequestThree));
            }
            else{
                newRequestTwo = newRequestTwo.strip();
                temp =ll.get(Integer.parseInt(newRequestTwo));
                temp.reOrder(newPriority);
                ll.remove(Integer.parseInt(newRequestTwo));
            }
        }
        else{
            newRequest = newRequest.strip();
            temp =ll.get(Integer.parseInt(newRequest));
            temp.reOrder(newPriority);
            ll.remove(Integer.parseInt(newRequest));
        }
        ll.add(temp);
    }
}