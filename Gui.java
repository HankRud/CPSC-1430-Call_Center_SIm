import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** @author harryrudolph

 */

/** Graphic Interface
 *
 */
public class Gui {
    private Simulation simulation;
    /** the frame*/
    private JFrame frame;
    /** main panel*/
    private JPanel panel;
    /** subpanel for dashboard*/
    private JPanel dash;
    /** initial number of customers*/
    private JSlider waiting;

    /** number of on duty techs*/
    private JSlider onDuty;
    /** hours to simulate*/
    private JSlider hours;
    /** incoming call rate*/
    private JSlider callRate;
    /** reporting interval*/
    private JSlider reportInterval;
    /** mean call time**/
    private JSlider meanCallTime;
    /** standard deviation*/
    private JSlider sDev;
    /** the button to run a simulation*/
    private JButton startSim;
    /** label for time return*/
   private JLabel time;
   /** label for techs*/
   private JLabel techs;
   /** customer label*/
   private JLabel customers;




    /** class constructor
     *

     */
    public Gui(){

        this.panel= new JPanel();
        this.frame = new JFrame("Call Center Simulation");
        frame.setSize(900,900);
        panel.setSize(900,900);
        frame.add(panel);
        makeWidgets();
        this.dash= new JPanel();
        dash.setSize(600,100);
        dash.setLayout(new BoxLayout(dash, BoxLayout.PAGE_AXIS));
        panel.add(dash);
        frame.setVisible(true);
        panel.setVisible(true);
        makeDash();
    }

    /** makes buttons and adds them to panel*/
    private void makeWidgets(){
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        this.waiting = new JSlider(JSlider.HORIZONTAL,15,1000,15);
        JLabel waitingLabel = new JLabel("Customers Waiting: ");
        waiting.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                waitingLabel.setText("Customers Waiting: "+waiting.getValue());
            }
        });
        waiting.setMinorTickSpacing(10);
        waiting.setMajorTickSpacing(100);

        this.sDev = new JSlider(JSlider.HORIZONTAL,1,10,1);
        JLabel sDevLabel = new JLabel("Standard Deviation: ");
        sDev.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sDevLabel.setText("Standard Deviation: "+sDev.getValue());
            }
        });
        sDev.setMinorTickSpacing(1);
        sDev.setMajorTickSpacing(2);

        this.onDuty = new JSlider(JSlider.HORIZONTAL,1,50,15);
        JLabel onDutyLabel = new JLabel("Techs on Duty: " );
        onDuty.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                onDutyLabel.setText("Techs on Duty: "+onDuty.getValue());
            }
        });
        onDuty.setMinorTickSpacing(1);
        onDuty.setMajorTickSpacing(5);

        this.hours = new JSlider(JSlider.HORIZONTAL,1,24,1);
        JLabel hoursLabel = new JLabel("Hours to Simulate: ");
        hours.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hoursLabel.setText("Hours to Simulate: "+hours.getValue());
            }
        });
        hours.setMinorTickSpacing(1);
        hours.setMajorTickSpacing(4);

        this.callRate = new JSlider(JSlider.HORIZONTAL,1,20,1);
        JLabel callRateLabel = new JLabel("Call Rate: ");
        callRate.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                callRateLabel.setText("Call Rate: "+callRate.getValue());
            }
        });
        callRate.setMinorTickSpacing(1);
        callRate.setMajorTickSpacing(5);

        this.reportInterval = new JSlider(JSlider.HORIZONTAL,1,10,1);
        JLabel reportIntervalLabel = new JLabel("Reporting Interval: ");
        reportInterval.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                reportIntervalLabel.setText("Reporting Interval: "+reportInterval.getValue());
            }
        });
        reportInterval.setMinorTickSpacing(1);
        reportInterval.setMajorTickSpacing(2);

        this.meanCallTime = new JSlider(JSlider.HORIZONTAL,1,10,1);
        JLabel meanCallTimeLabel = new JLabel("Mean Call Time: ");
        meanCallTime.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                meanCallTimeLabel.setText("Mean Call Time: "+meanCallTime.getValue());
            }
        });
        meanCallTime.setMinorTickSpacing(1);
        meanCallTime.setMajorTickSpacing(2);

        JSlider[] sliders = {waiting,onDuty,callRate,meanCallTime, sDev,hours,reportInterval};
        JLabel[] labels = {waitingLabel,onDutyLabel,callRateLabel,meanCallTimeLabel,sDevLabel,hoursLabel,reportIntervalLabel};
        
        for (int indx = 0; indx<7; indx++) {
            sliders[indx].setPaintLabels(true);
            sliders[indx].setPaintTicks(true);
            sliders[indx].setPaintTrack(true);
            labels[indx].setHorizontalAlignment(JLabel.CENTER);
            panel.add(labels[indx]);
            panel.add(sliders[indx]);
            panel.add(Box.createRigidArea(new Dimension(0, 30)));
        }

        startSim = new JButton("Start");
        startSim.setSize(30, 10);
        startSim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulation= new Simulation(Gui.this, waiting.getValue(), callRate.getValue(), onDuty.getValue(),
                        hours.getValue(), reportInterval.getValue(),
                        meanCallTime.getValue(), sDev.getValue());
            }
        });
        panel.add(startSim);


    }

    /** updates dashboard for display*/
    public void update(int timeElapsed,int techsQueued, int customersQueued){
        time.setText("Time Elapsed: " + String.valueOf(timeElapsed));
        techs.setText("Techs in Queue: "+ String.valueOf(techsQueued));
        customers.setText("Customers in Queue:"+ String.valueOf(customersQueued));
        dash.repaint();

    }

    /** creates the dashboard
     *
     */
    public void makeDash(){
        time= new JLabel("Time Elapsed: " );
        time.setVisible(true);
        techs= new JLabel("Techs in Queue: ");
        techs.setVisible(true);
        customers = new JLabel("Customers in Queue:");
        customers.setVisible(true);

        dash.add(time);
        dash.add(techs);
        dash.add(customers);
        dash.setVisible(true);
        dash.setLocation(600, 0);
        panel.repaint();
        dash.repaint();
        frame.repaint();
    }




}
