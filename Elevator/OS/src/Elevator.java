import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javax.swing.*;
import java.lang.Math;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Elevator implements Initializable {
    int signal=0;
    int TotalFloor=0;
    boolean isClicked=false;
    String algo;
    int CIndex=0;
    int AIndex=0;

    int[][] newArray;
    int [] gant ;
    int[] Arrival;
    String [] process;

    double Turn,FTurn=0, SJTurn=0, RTurn=0 , STurn=0;
    double Waiting,FWaiting=0, SJWaiting=0, RWaiting=0, SWaiting=0;
    double Response, FResponse=0, SJResponse=0, RResponse=0, SResponse=0;

    String Algorithm;

    @FXML
    private Rectangle BoxNumberFloor;
    @FXML
    private Label TextResponse;

    @FXML
    private Label TextTurn;

    @FXML
    private Label TextWaiting;

    @FXML
    private Text ValRes;

    @FXML
    private Text ValTurn;

    @FXML
    private Text ValWait;


    @FXML
    private TextField DestinationFloor;

    @FXML
    private Rectangle InputOterBox;

    @FXML
    private AnchorPane ProcessPage;

    @FXML
    private TextField StartFloor;

    @FXML
    private Label TextEnterDFloor;

    @FXML
    private Label TextEnterFloor;

    @FXML
    private Text TextNumberOfFloor;

    @FXML
    private Button ButtonStart;
    @FXML
    private Button ButtonNew;

    @FXML
    private TextField InputArrival;

    //---------------------------------------------------------------------------------------- Table

    @FXML
    private TableView<Table> TableResult;
    @FXML
    private TableColumn<Table, String> TableFirstColumn;
    @FXML
    private TableColumn<Table, String> TableSecondColumn;

    @FXML
    private TableColumn<Table, String> TableThirdColumn;

    @FXML
    private TableColumn<Table, String> TableFourthColumn;
    @FXML
    private TableColumn<Table, String > TableFifthColumn;

    //-----------------------------------------------------------------------------------------------------

    @FXML
    void ButtonStartAction(MouseEvent event) {
        // there is new array
        //there is Arrival
        int[][] tmpNewArray = new int[3][50];
        int []tmpArrival = new int[50];

        int minIndex ;
        int min;
        int index=0;
        while(Arrival[0]!=1000) {

            OptionalInt minOpt = Arrays.stream(Arrival).min();
            min = minOpt.getAsInt();
            minIndex = Arrays.stream(Arrival).boxed().collect(Collectors.toList()).indexOf(min);
            tmpArrival[index]=Arrival[minIndex];
            tmpNewArray[0][index]=newArray[0][minIndex];
            tmpNewArray[1][index]=newArray[1][minIndex];
            tmpNewArray[2][index]=newArray[2][minIndex];

            for(int i=minIndex+1 ; i<Arrival.length; i+=1){
                Arrival[i-1]=Arrival[i];
                newArray[0][i-1]=newArray[0][i];
                newArray[1][i-1]=newArray[1][i];
                newArray[2][i-1]=newArray[2][i];
            }
            index+=1;

        }
        newArray=tmpNewArray;
        Arrival=tmpArrival;

        System.out.println("this is newArray\n"+Arrays.deepToString(newArray));
        System.out.println(Arrays.toString(Arrival));

        FCFS();
        TurnAround();
        FTurn=Turn;
        WaitingTime();
        FWaiting=Waiting;
        Response();
        FResponse=Response;


        SJF();
        TurnAround();
        SJTurn = Turn;
        WaitingTime();
        SJWaiting = Waiting;
        Response();
        SJResponse= Response;

        RR();
        TurnAround();
        RTurn = Turn;
        WaitingTime();
        RWaiting = Waiting;
        Response();
        RResponse=Response;

        SRTF();
        TurnAround();
        STurn=Turn;
        WaitingTime();
        SWaiting= Waiting;
        Response();
        SResponse= Response;

        if(Objects.equals(Algorithm, "FCFS")) {
            System.out.println("this is FCFS");
            ValWait.setText(String.valueOf(FWaiting));
            ValRes.setText(String.valueOf(FResponse));
            ValTurn.setText(String.valueOf(FTurn));
        }
        else if(Objects.equals(Algorithm, "SJF")){
            System.out.println("this is SJF");
            ValWait.setText(String.valueOf(SJWaiting));
            ValRes.setText(String.valueOf(SJResponse));
            ValTurn.setText(String.valueOf(SJTurn));
        }
        else if(Objects.equals(Algorithm, "RR")){
            System.out.println("this is RR");
            ValWait.setText(String.valueOf(RWaiting));
            ValRes.setText(String.valueOf(RResponse));
            ValTurn.setText(String.valueOf(RTurn));
        }
        else if(Objects.equals(Algorithm, "SRTF")){
            System.out.println("this is SRTF");
            ValWait.setText(String.valueOf(SWaiting));
            ValRes.setText(String.valueOf(SResponse));
            ValTurn.setText(String.valueOf(STurn));
        }

        ObservableList<Table> list = FXCollections.observableArrayList(
                new Table("Waiting", String.valueOf(FWaiting), String.valueOf(SJWaiting), String.valueOf(RWaiting)
                        , String.valueOf(SWaiting) ),
                new Table("Response", String.valueOf(FResponse), String.valueOf(SJResponse), String.valueOf(RResponse)
                        , String.valueOf(SResponse) ),
                new Table("Turnaround", String.valueOf(FTurn), String.valueOf(SJTurn), String.valueOf(RTurn)
                        , String.valueOf(STurn) )
        );
        TableResult.setItems(list);
    }

    @FXML
    void ButtonSubmit(ActionEvent event) {

    }
    @FXML
    void ButtonStart(ActionEvent event){

    }

    @FXML
    void SubmitAction(MouseEvent event) {

        int floorStart= Integer.parseInt(StartFloor.getText());
        int floorDest= Integer.parseInt(DestinationFloor.getText());
        int arrival = Integer.parseInt(InputArrival.getText());
        StartFloor.setText("");
        DestinationFloor.setText("");
        InputArrival.setText("");
        signal=1;
        (newArray)[0][CIndex]= floorStart;
        (newArray)[1][CIndex]= floorDest;
        (newArray)[2][CIndex]= Math.abs(floorDest-floorStart);
        CIndex+=1;

        (Arrival) [AIndex]= arrival;
        AIndex+=1;

    }

    void Response(){

        int first=0;
        int last=0;
        double sum=0;

        for(int i=0 ; i<CIndex ; i+=1){
            for(int j=0 ; j<process.length ; j+=1){
                if(Objects.equals(process[j], "P" + Integer.toString(i + 1))){

                    first=gant[j];
                    break;
                }
            }

            for(int j=process.length -1 ; j>-1; j-=1){
                if(Objects.equals(process[j], "P" + Integer.toString(i + 1))){

                    last=gant[j+1];
                    break;
                }
            }

            sum+= (last-first);
        }

        Response= sum/CIndex;;

    }

    void WaitingTime(){
        int next;
        int prev;
        double sum=0;
        for(int i=0 ; i<CIndex ; i+=1){
            prev=Arrival[i];
            for(int j=0 ; j < process.length ; j+=1){
                if(Objects.equals(process[j], "P" + Integer.toString(i + 1))){
                    next = gant[j];
                    sum+= next-prev;
                    prev= gant[j+1];
                }
            }
        }
        Waiting = sum/CIndex;

    }
    void TurnAround(){

        double sum=0;
        for(int i=0 ; i<CIndex ; i+=1){
            for(int j=process.length-1 ; j>-1 ; j-=1){
                if(Objects.equals(process[j], "P" + Integer.toString(i + 1))){
                    sum+=gant[j+1]-Arrival[i];
                    break;
                }
            }
        }
        Turn= sum/CIndex;

    }


    void process(int floor, String Algo, String AlgoChoice){
        // now we know the algorithm
        // we know the numbers of floors
        TotalFloor=floor;
        newArray = new int[3][50];

        Arrival = new int[50];
        algo=Algo;

        for(int i=0 ;i<50 ; i+=1){
            newArray[0][i]=floor+1;
            newArray[1][i]=floor+1;
            newArray[2][i]=floor+1;
            Arrival[i]=1000;
        }
        Algorithm=AlgoChoice;

    }

    void FCFS(){
        System.out.println("This is FCFS");
        gant = new int[50];
        process = new String[50];
        gant[0]=0;
        int gantIndex=1;
        int processIndex=0;
        int current=0;
        for(int i=0 ; i<CIndex ; i+=1){

            if(Arrival[i] > gant[gantIndex-1]){
                if(gant[gantIndex-1]!=Arrival[i]) {
                    gant[gantIndex] = Arrival[i];
                    gantIndex += 1;
                }

                process[processIndex] = "idle";
                processIndex+=1;
            }

            if(newArray[0][i] - current !=0){
                process[processIndex] = "idle";
                processIndex+=1;

                if(gant[gantIndex-1]!=Math.abs(newArray[0][i] - current)*3+gant[gantIndex-1]) {
                    gant[gantIndex] = Math.abs(newArray[0][i] - current) * 3 + gant[gantIndex - 1];
                    gantIndex += 1;
                }
                current=newArray[0][i];
                TextNumberOfFloor.setText(String.valueOf(current));

            }
            if(gant[gantIndex-1] != Math.abs(newArray[0][i] - current)*3+gant[gantIndex-1]) {
                gant[gantIndex] = Math.abs(newArray[0][i] - current) * 3 + gant[gantIndex - 1];
                gantIndex += 1;
            }
            process[processIndex] = "P" + Integer.toString(i + 1);
            processIndex += 1;
            current = newArray[0][i];
            if(gant[gantIndex-1]!=Math.abs(newArray[2][i])*3+gant[gantIndex-1] ) {
                gant[gantIndex] = Math.abs(newArray[2][i]) * 3 + gant[gantIndex - 1];
                gantIndex += 1;
            }
            current = newArray[1][i];
            TextNumberOfFloor.setText(String.valueOf(current));
        }

        System.out.println(Arrays.toString(gant));
        System.out.println(Arrays.toString(process));
    }

    void SJF(){
        System.out.println("This is SJF");
        gant = new int[50];
        process = new String[50];
        // tmp is an array which the values are distance
        int[] tmp= new int[50];

        Arrays.fill(tmp, TotalFloor + 1);

        gant[0]=0;
        int gantIndex=1;
        int processIndex=0;
        int index=0;
        int tmpIndex=0;
        int current=0;

        while(Arrival[index] !=0){

            for(int i=tmpIndex ; i<tmp.length ;i+=1){
                if(Arrival[i]<= gant[gantIndex-1]){
                    if(newArray[2][i]==0)break;
                    tmp[tmpIndex]=newArray[2][i];
                    tmpIndex+=1;
                }
                else break;
            }


            OptionalInt minOpt = Arrays.stream(tmp).min();
            int minIndex ;
            int min = minOpt.getAsInt();
            minIndex = Arrays.stream(tmp).boxed().collect(Collectors.toList()).indexOf(min);

            if(min==TotalFloor+1){
                if(gant[gantIndex-1]!= Arrival[index]) {
                    gant[gantIndex] = Arrival[index];
                    gantIndex += 1;
                }
                process[processIndex] = "idle";
                processIndex+=1;
            }

            else if(min!=0){

                if(newArray[0][minIndex] - current !=0){
                    process[processIndex] = "idle";
                    processIndex+=1;
                    if(gant[gantIndex-1]!=Math.abs(newArray[0][minIndex] - current)*3+gant[gantIndex-1]) {
                        gant[gantIndex] = Math.abs(newArray[0][minIndex] - current) * 3 + gant[gantIndex - 1];
                        gantIndex += 1;
                    }
                    current=newArray[0][minIndex];
                }
                if(gant[gantIndex-1]!= Math.abs(newArray[0][minIndex] - current)*3+gant[gantIndex-1]) {
                    gant[gantIndex] = Math.abs(newArray[0][minIndex] - current) * 3 + gant[gantIndex - 1];
                    gantIndex += 1;
                }
                process[processIndex] = "P" + Integer.toString(minIndex+1 );
                processIndex += 1;

                current = newArray[0][minIndex];
                if(gant[gantIndex-1]!= Math.abs(newArray[2][minIndex])*3+gant[gantIndex-1]) {
                    gant[gantIndex] = Math.abs(newArray[2][minIndex]) * 3 + gant[gantIndex - 1];
                    gantIndex += 1;
                }
                current = newArray[1][minIndex];
                tmp[minIndex]=TotalFloor+1;
                index+=1;
            }
        }

        System.out.println(Arrays.toString(gant));
        System.out.println(Arrays.toString(process));
    }

    void RR(){
        System.out.println("this is RR");
        //calculating the value of time quantum
        int processIndex=0;
        int gantIndex=1;
        gant=new int[50];
        gant[0]=0;
        process =new String[50];
        int[] tmp = new int[50];

        int tmpId=0;
        int [] start = new int[50];

        int complete=0;
      //  System.arraycopy(newArray[2], 0, tmp, 0, newArray.length);
        System.arraycopy(newArray[0], 0, start, 0, newArray.length);


        int current=0;

        int quantum = (int)Math.round(0.8*Arrays.stream(newArray[2]).sum()/CIndex);
        System.out.println(quantum);

        int [] tmpArrival = new int[50];
        tmpArrival=Arrival;
        int r;
        boolean newTimes;
        int times=0;
        while(complete!=CIndex){
            r=-1;
            newTimes=false;
            while(r<times){
                r+=1;
                //must initial the values of
                for(int i=tmpId ; i<Arrival.length ; i+=1){
                    if(tmpArrival[i] <= gant[gantIndex-1]){
                        if(tmpArrival[i]==0)break;
                        tmp[tmpId]=newArray[2][i];
                        start[tmpId]=newArray[0][i];
                        tmpId+=1;
                        times+=1;
                        newTimes=true;
                    }
                    else break;
                }

                if(tmp[r]!=0){
                    if(tmp[r]<= quantum){
                        if(start[r] - current !=0){
                            process[processIndex] = "idle";
                            processIndex+=1;
                            if(gant[gantIndex-1]!= Math.abs(start[r] - current)*3+gant[gantIndex-1]) {
                                gant[gantIndex] = Math.abs(start[r] - current) * 3 + gant[gantIndex - 1];
                                gantIndex += 1;
                            }
                            current=start[r];
                        }
                        if(gant[gantIndex-1]!=Math.abs(start[r] - current)*3+gant[gantIndex-1]) {
                            gant[gantIndex] = Math.abs(start[r] - current) * 3 + gant[gantIndex - 1];
                            gantIndex += 1;
                        }
                        process[processIndex] = "P" + Integer.toString(r + 1);
                        processIndex += 1;

                        current = start[r];
                        if(gant[gantIndex-1]!=Math.abs(tmp[r]*3+gant[gantIndex-1])) {
                            gant[gantIndex] = Math.abs(tmp[r] * 3 + gant[gantIndex - 1]);
                            gantIndex += 1;
                        }
                        current = newArray[1][r];
                        complete+=1;
                        tmp[r]=0;

                    }
                    //this is when the distance is more than the quantum
                    else{
                        if(start[r] - current !=0){
                            process[processIndex] = "idle";
                            processIndex+=1;
                            if(gant[gantIndex-1]!= Math.abs(start[r] - current)*3+gant[gantIndex-1]) {
                                gant[gantIndex] = Math.abs(start[r] - current) * 3 + gant[gantIndex - 1];
                                gantIndex += 1;
                            }
                            current=start[r];
                        }
                        if(gant[gantIndex-1]!=Math.abs(start[r] - current)*3+gant[gantIndex-1] ) {
                            gant[gantIndex] = Math.abs(start[r] - current) * 3 + gant[gantIndex - 1];
                            gantIndex += 1;
                        }
                        process[processIndex] = "P" + Integer.toString(r + 1);
                        processIndex += 1;
                        current = start[r];
                        if(gant[gantIndex-1]!=Math.abs(quantum*3+gant[gantIndex-1])) {
                            gant[gantIndex] = Math.abs(quantum * 3 + gant[gantIndex - 1]);
                            gantIndex += 1;
                        }
                        current = start[r]+quantum;
                        start[r]=current;
                        tmp[r]=tmp[r]-quantum;


                    }
                }
                else{
                    OptionalInt maxOpt = Arrays.stream(tmp).max();
                    int max = maxOpt.getAsInt();
                    if(!newTimes && max==0){
                       if(times==CIndex)break;

                        else {
                            if(gant[gantIndex-1]!= Arrival[tmpId] ) {
                                gant[gantIndex] = Arrival[tmpId];
                                gantIndex += 1;
                            }
                           process[processIndex] = "idle";
                           processIndex += 1;

                           r -= 1;
                       }

                    }
                }

            }

        }
        System.out.println(Arrays.toString(gant));
        System.out.println(Arrays.toString(process));
    }


    void SRTF(){
        System.out.println("This is SRTF");
        gant = new int[50];
        process = new String[50];
        // tmp is an array which the values are distance
        int[] tmp= new int[50];
        int[][] tmpNewArray = new int[3][50];

        tmpNewArray=newArray;

        Arrays.fill(tmp, TotalFloor + 1);

        gant[0]=0;
        int gantIndex=1;
        int processIndex=0;
        int index=0;
        int tmpIndex=0;
        int current=0;

        int situation=0;
        int prev=-1;

        while(Arrival[index] !=0){

            for(int i=tmpIndex ; i<tmp.length ;i+=1){
                if(Arrival[i]<= gant[gantIndex-1]){
                    if(tmpNewArray[2][i]==0)break;
                    tmp[tmpIndex]=tmpNewArray[2][i];
                    tmpIndex+=1;
                }
                else break;
            }

            OptionalInt minOpt = Arrays.stream(tmp).min();
            int minIndex ;
            int min = minOpt.getAsInt();
            minIndex = Arrays.stream(tmp).boxed().collect(Collectors.toList()).indexOf(min);

            if(min==TotalFloor+1){
                gant[gantIndex] = Arrival[index];
                gantIndex += 1;
                process[processIndex] = "idle";
                processIndex+=1;
            }

            else if(min!=0) {

                if (tmpNewArray[0][minIndex] - current != 0) {
                    //this is situation 0
                    if(situation!=0) {

                        process[processIndex] = "idle";
                        processIndex += 1;
                        gant[gantIndex] = 3 + gant[gantIndex - 1];
                        gantIndex += 1;

                    }
                    else{

                        gant[gantIndex-1] = 3 + gant[gantIndex - 1];

                    }

                    //means we are going down
                    if (tmpNewArray[0][minIndex] - current < 0) {
                        current -= 1;

                    } else {
                        current += 1;

                    }
                    situation=0;
                } else {
                    //this is situation 1
                    if(situation!=1 || prev!=minIndex) {

                        gant[gantIndex] = 3 + gant[gantIndex - 1];
                        gantIndex += 1;
                        process[processIndex] = "P" + Integer.toString(minIndex + 1);
                        processIndex += 1;
                    }
                    else {
                        gant[gantIndex-1] = 3 + gant[gantIndex - 1];
                    }

                    if (tmpNewArray[1][minIndex] - tmpNewArray[0][minIndex]< 0) {

                        tmpNewArray[0][minIndex] -= 1;

                        current -= 1;

                    } else {
                        tmpNewArray[0][minIndex] += 1;
                        current+=1;

                    }
                    tmpNewArray[2][minIndex]-=1;
                    if(tmpNewArray[2][minIndex]==0) {
                        tmp[minIndex] = TotalFloor + 1;
                        index += 1;
                    }
                    situation=1;
                    prev=minIndex;
                }

            }
        }

        System.out.println(Arrays.toString(gant));
        System.out.println(Arrays.toString(process));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableFirstColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("TableFirstColumn"));
        TableSecondColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("TableSecondColumn"));
        TableThirdColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("TableThirdColumn"));
        TableFourthColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("TableFourthColumn"));
        TableFifthColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("TableFifthColumn"));


    }
}
