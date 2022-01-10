import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** @author Harry Rudolph
 * Simulates a cal center
 */
public class Simulation {

    /** the list of customers*/
    private  final ArrayList<Customer> allCustomers;
    /** the list of techs*/
    private final ArrayList<Tech> allTechs;
    /** customers waiting*/
    private HashSetQueue<Customer> queuedCustomers;
    /** Techs waiting*/
    private HashSetQueue<Tech> queuedTechs;
    /** calls in progress*/
    private PriorityQueue<SupportSession> inProgressCalls;
    /** the timer*/
    private final Timer simTimer;
    /** customers waiting*/
    private int waiting;
    /** number of on duty techs*/
    private int onDuty;
    /** hours to simulate*/
    private int hours;
    /** mean call time**/
    private int meanCallTime;
    /** standard deviation*/
    private int sDev;
    /** time elapsed*/
    private int timeElapsed;
    /** the dashboard to report to*/
    private final Gui gui;
    /** random index*/
    private final Random rand;

    /** constructor for the class
     *
     * @param gui  the gui for the simulation
     * @param waiting  customers waiting
     * @param callRate call rate
     * @param onDuty   on duty techs
     * @param hours    hours to sim
     * @param reportInterval  report interval
     * @param meanCallTime   average call time
     *  @param sDev   standard deviation
     *      */

    public Simulation(Gui gui,int waiting, int callRate, int onDuty, int hours, int reportInterval, int meanCallTime, int sDev){
        this.waiting = waiting;
        callRate = callRate*1000;
        this.onDuty = onDuty;
        this.hours = hours*1000*60;
        reportInterval = reportInterval*1000;
        this.meanCallTime = meanCallTime*1000;
        this.sDev = sDev;
        this.gui= gui;
        rand = new Random();
        inProgressCalls = new PriorityQueue<>();
        allCustomers= new ArrayList<>();
        allTechs= new ArrayList<>();
        populateCustomerList();
        populateTechList();
        queuedCustomers = new HashSetQueue<>();
        queuedTechs = new HashSetQueue<>();
        setQueuedCustomers();
        setQueuedTechs();
        simTimer=new Timer();
        timeElapsed = 0;
        createSupportSessions();
        simTimer.scheduleAtFixedRate(new addTime(), 1000, 1000);
        simTimer.scheduleAtFixedRate(new update() , reportInterval,reportInterval );
        simTimer.scheduleAtFixedRate(new addCustomers(),callRate,callRate);
        simTimer.scheduleAtFixedRate(new makeSessions(),1000,1000);
        simTimer.scheduleAtFixedRate(new displayFinished(), 1000, 1000);

    }




    /** populates array list of customers
     *
     * @return  true if no exceptions
     */
    private boolean populateCustomerList(){
        File file = new File("src/customers.csv");
        Scanner sc;
        try{
            sc = new Scanner(file);
        }catch (FileNotFoundException e) {
            return false;
        }
        while (sc.hasNextLine()){
            int number= 0;
            String firstName = "";
            String lastName = "";
            String email = " ";
            String phoneNumber = "";
            Scanner sc2 = new Scanner(sc.nextLine());
            sc2.useDelimiter(",");
            while (sc2.hasNext()) {
                number= sc2.nextInt();
                firstName = sc2.next();
                lastName = sc2.next();
                email = sc2.next();
                phoneNumber = sc2.next();
            }
            Customer c= new Customer(number, firstName, lastName, email, phoneNumber);
            allCustomers.add(c);
        }
        return true;
    }

    /** populates the list of techs
     *
     * @return true if complete
     */
    private boolean populateTechList(){
        File file = new File("src/techs.csv");
        Scanner sc;
        try{
            sc = new Scanner(file);
        }catch (FileNotFoundException e) {
            return false;
        }
        while (sc.hasNextLine()){
            int number= 0;
            String firstName = "";
            String lastName = "";
            String username = " ";

            Scanner sc2 = new Scanner(sc.nextLine());
            sc2.useDelimiter(",");
            while (sc2.hasNext()) {
                number= sc2.nextInt();
                firstName = sc2.next();
                lastName = sc2.next();
                username = sc2.next();
            }
            Tech t= new Tech(number, firstName, lastName, username);
            allTechs.add(t);
        }
        return true;
    }

    /** sets initial customer queue*/
    private void setQueuedCustomers(){

        while (queuedCustomers.getSize() <= waiting){
            queuedCustomers.add(allCustomers.get(rand.nextInt(allCustomers.size())));
        }
    }

    /** sets initial tech queue*/
    private void setQueuedTechs(){

        while (queuedTechs.getSize() < onDuty){
            queuedTechs.add(allTechs.get((rand.nextInt(allTechs.size()))));
        }
    }

    /** creates support sessions*/
    private void createSupportSessions(){

        while (queuedTechs.getSize() > 0 ){
            int callLength= (int) Math.round( rand.nextGaussian()*sDev+meanCallTime);
            int finishTime = callLength+ timeElapsed;
            Tech tech = queuedTechs.peek();
            try { queuedCustomers.peek(); }
            catch (NullPointerException e){
                break;
            }
            Customer customer = queuedCustomers.peek();
            queuedTechs.remove();
            queuedCustomers.remove();
            SupportSession s = new SupportSession(customer,tech,callLength,finishTime);
            inProgressCalls.add(s);
        }
    }

    private class makeSessions extends TimerTask{

        @Override
        public void run() {
            createSupportSessions();
        }
    }
    /** adds a customer if it is time
     *
     */

    private class addCustomers extends TimerTask{

        @Override
        public void run() {
            queuedCustomers.add(allCustomers.get(rand.nextInt(allCustomers.size())));
        }
    }

    /** ends program and stops timer*/
    private void end(){

            simTimer.cancel();
            queuedCustomers.clear();
            queuedTechs.clear();
            System.out.println("Simulation Ended");


    }

    /** updates the dash in the gui*/
    private class update extends TimerTask{
        public void run() {
            gui.update(timeElapsed/(1000), queuedTechs.getSize(), queuedCustomers.getSize());
        }
    }



    /** displays finished calls*/
    private  class displayFinished extends TimerTask{

        public void run() {

                while ((inProgressCalls.size() > -1 && inProgressCalls.peek() != null )
                        && inProgressCalls.peek().getFinishTime() <= timeElapsed) {
                    String str = inProgressCalls.peek().toString()+ " at time: "
                            + inProgressCalls.peek().getFinishTime()/1000;
                    Tech t = inProgressCalls.peek().getTech();
                    inProgressCalls.remove();
                    queuedTechs.add(t);
                    System.out.println(str);

                }


        }
    }

    /** adds time*/
    private class addTime extends TimerTask{

        @Override
        public void run() {
            timeElapsed= timeElapsed + 1000;
            if (timeElapsed >= hours){
                end();
            }
        }

    }

}
