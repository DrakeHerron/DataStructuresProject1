import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        PQ_LL ll = new PQ_LL();
        boolean quit = true;
        int response = 0;
        while(quit) {
            System.out.println("1 - Display Current Requests\n2 - Create New Request\n3 - Search\n4 - Delete a Request\n5 - Serving a Request\n6 - Increase/Decrease a Priority of a Request\n7 - Save\n8 - Exit");
            if(s.hasNextInt()) {
                response = s.nextInt();
                s.nextLine();
                //1 - Display Current Requests
                if(response == 1){
                    //Sorts file
                    boolean reRun = ll.bubbleSort();
                    while (reRun){
                        reRun = ll.bubbleSort();
                    }
                    String display = "";
                    display = ll.displayRequests();
                    System.out.println(display);
                }
                //2 - Create New Request
                else if(response == 2){
                    boolean check = true;
                    //Get ID for Request
                    System.out.println("Enter a ID: ");
                    String ID = null;
                    while(check) {
                        ID = s.nextLine();
                        if(ID.equals("") || ID.equals(" ")){
                            System.out.println("Enter a ID: ");
                        }
                        else{
                            check = false;
                        }
                    }
                    //Replaces Spaces with Underscores
                    ID = ID.replace(" ", "_");
                    check = true;
                    //Get Arrival Time for Request
                    System.out.println("Enter a Arrival Time: ");
                    long arrivalTime = 0;
                    while(check) {
                        if(s.hasNextLong()) {
                            arrivalTime = Long.parseLong(s.nextLine());
                            check = false;
                        }
                        else{
                            System.out.println("Enter a Number: ");
                        }
                    }
                    check = true;
                    //Get Priority for Request
                    System.out.println("Enter a Priority: ");
                    double Priority = 0;
                    while(check) {
                        if(s.hasNextDouble()) {
                            Priority = Double.parseDouble(s.nextLine());
                            check = false;
                        }
                        else{
                            System.out.println("Enter a Number: ");
                        }
                    }
                    Request request = new Request(ID, arrivalTime, Priority);
                    check = ll.addRequest(request);
                    if(check){
                        System.out.println("Request Added");
                    }
                    else{
                        System.out.println("Request Failed to Add");
                    }
                }
                //3 - Search (By ID; by Priority)
                else if(response == 3){
                    boolean first = true;
                    while(first) {
                        System.out.println("1 - ID\n2 - Priority");
                        if (s.hasNextInt()) {
                            response = s.nextInt();
                            s.nextLine();
                            //Search by ID
                            if (response == 1) {
                                first = false;
                                System.out.println("Enter a ID: ");
                                String ID = s.nextLine();
                                String request = ll.searchRequest(ID);
                                if (request.equals("")) {
                                    System.out.println("This Request Does Not Exist");
                                } else {
                                    System.out.println(request);
                                }
                            }
                            //Search by Priority
                            else if (response == 2) {
                                first = false;
                                System.out.println("Enter a Priority: ");
                                boolean check = true;
                                double Priority = 0;
                                while (check) {
                                    if (s.hasNextDouble()) {
                                        Priority = s.nextDouble();
                                        check = false;
                                    } else {
                                        System.out.println("Enter a Number: ");
                                    }
                                    s.nextLine();
                                }
                                String request = ll.searchRequest(Priority);
                                if (request.equals("")) {
                                    System.out.println("This Request Does Not Exist");
                                } else {
                                    System.out.println(request);
                                }
                            }
                        }
                        else {
                            s.nextLine();
                        }
                    }
                }
                //4 - Delete a Request (By ID)
                else if(response == 4){
                    System.out.println("Enter the ID of the Item your want to Remove: ");
                    String ID = s.nextLine();
                    boolean check = ll.removeRequest(ID);
                    if(check){
                        System.out.println("Successfully Removed Request");
                    }
                    else{
                        System.out.println("Request Has Not Been Removed - Does Not Exist");
                    }
                }
                //5 - Serving a Request
                else if(response == 5){
                    boolean reRun = ll.bubbleSort();
                    while (reRun){
                        reRun = ll.bubbleSort();
                    }
                    Request request = ll.servingRequest();
                    System.out.println(request.toString());
                }
                //6 - Increase/Decrease a Priority of a Request (Ask ID then new Priority)
                else if(response == 6){
                    double priority;
                    boolean wait = true;
                    System.out.println("Enter a ID: ");
                    String ID = s.nextLine();
                    String request = ll.searchRequest(ID);
                    if (request.equals("")) {
                        System.out.println("This Request Does Not Exist");
                    } else {
                        String[] splits = request.split("\n");
                        if (splits.length > 1) {
                            //More Than One Request with same ID
                            int choice;
                            while(wait) {
                                System.out.println("There is more than One Request associated with this ID. Choose which to edit: ");
                                for (int i = 0; i < splits.length; i++) {
                                    System.out.println(i + " - " + splits[i]);
                                }
                                if(s.hasNextInt()){
                                    choice = s.nextInt();
                                    s.nextLine();
                                    if(splits.length > choice){
                                        request = splits[choice];
                                        System.out.println("Enter a new Priority: ");
                                        if (s.hasNextDouble()) {
                                            priority = s.nextDouble();
                                            ll.changePriority(priority, request);
                                            wait = false;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            System.out.println(request);
                            System.out.println("Enter a new Priority: ");
                            if (s.hasNextDouble()) {
                                priority = s.nextDouble();
                                ll.changePriority(priority, request);
                            }
                        }
                        System.out.println("Request has Changed Priority\n");
                    }
                }
                //7 - Save (data.txt)
                else if(response == 7){
                    ll.bubbleSort();
                    ll.updateFile();
                    System.out.println("File has been Saved to data.txt1");
                }
                //8 - Exit
                else if(response == 8){
                    quit = false;
                }
            }
            else {
                s.nextLine();
            }
        }
    }
}
