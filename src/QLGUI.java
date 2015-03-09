import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

public class QLGUI extends JFrame{
    private static final String TITLE = "Quicklyst";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int INNER_WIDTH = 783;
    private static final int INNER_HEIGHT = 557;
    private static final int PADDING_LEFT = 10;
    private static final int PADDING_TOP = 10;
    
    private static final int TASKLIST_OFFSET_X = PADDING_LEFT;
    private static final int TASKLIST_OFFSET_Y = PADDING_TOP;
    private static final int TASKLIST_WIDTH = 390;
    private static final int TASKLIST_HEIGHT = 500;
    
    private static final int COMMAND_OFFSET_X = PADDING_LEFT;
    private static final int COMMAND_OFFSET_Y = 2*PADDING_TOP+TASKLIST_HEIGHT;
    private static final int COMMAND_WIDTH = INNER_WIDTH-2*PADDING_LEFT;
    private static final int COMMAND_HEIGHT = INNER_HEIGHT-3*PADDING_TOP-TASKLIST_HEIGHT;
    
    private final static int OVERVIEW_OFFSET_X = 2*PADDING_LEFT+TASKLIST_WIDTH;
    private final static int OVERVIEW_OFFSET_Y = PADDING_TOP;
    private final static int OVERVIEW_WIDTH = INNER_WIDTH-TASKLIST_WIDTH-3*PADDING_LEFT;
    private final static int OVERVIEW_HEIGHT = (TASKLIST_HEIGHT-PADDING_TOP)/2;
    
    private static final int FEEDBACK_OFFSET_X = 2*PADDING_LEFT+TASKLIST_WIDTH;
    private static final int FEEDBACK_OFFSET_Y = (TASKLIST_HEIGHT-PADDING_TOP)/2+2*PADDING_TOP;
    private static final int FEEDBACK_WIDTH = INNER_WIDTH-TASKLIST_WIDTH-3*PADDING_LEFT;
    private static final int FEEDBACK_HEIGHT = (TASKLIST_HEIGHT-PADDING_TOP)/2;
    
    private JPanel taskList;
    private JPanel overview;
    private JTextArea feedback;
    private JTextField command;
    
    public QLGUI() {
        super(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container contentPane = this.getContentPane();
        SpringLayout layout = new SpringLayout();
        
        contentPane.setLayout(layout);
        
        taskList = new JPanel();
        taskList.setLayout(new GridBagLayout());
        
        overview = new JPanel();
        overview.setLayout(new BoxLayout(overview, BoxLayout.PAGE_AXIS));
        
        feedback = new JTextArea();
        
        JPanel temp = new JPanel(new BorderLayout());
        temp.add(taskList, BorderLayout.NORTH);
        JScrollPane taskListScroll = new JScrollPane(temp);
        add(taskListScroll);
        
        JScrollPane feedbackScroll = new JScrollPane(feedback);
        add(feedbackScroll);
        overview.setBackground(Color.BLUE);
        add(overview);
       
        command = new JTextField();
        
        command.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){
                StringBuilder fb = new StringBuilder();
                List<Task> tasks = QLLogic.executeCommand(command.getText(), fb);
                if (!fb.toString().isEmpty()) {
                    feedback.append(fb.toString() + "\r\n");
                }
                taskList.removeAll();
                int i = 1;
                for (Task t : tasks)
                {
                    
                    JPanel p = new JPanel();
                    p.setLayout(new GridBagLayout());
                    p.setBorder(new LineBorder(Color.BLACK));
                    
                    JPanel c = new JPanel();
                    c.setBackground(Color.RED);
                    JLabel name = new JLabel(t.getName());
                    JLabel index = new JLabel("#" + i);
                    JLabel date = new JLabel(t.getStartDate() + " - " + t.getDueDate());
                    JLabel priority = new JLabel(((Character)t.getPriority()).toString());
                    
                    
                    
                    GridBagConstraints con = new GridBagConstraints();
                    con.insets = new Insets(5, 5, 5, 5);
                    con.gridheight = 2;
                    con.gridwidth = 1;
                    con.gridx = 0;
                    con.gridy = 0;
                    con.fill = GridBagConstraints.VERTICAL;
                    p.add(c, con);
                    
                    con.gridheight = 1;
                    con.gridwidth = 1;
                    con.gridx = 1;
                    con.gridy = 0;
                    con.weightx = 1;
                    con.fill = GridBagConstraints.HORIZONTAL;
                    con.anchor = GridBagConstraints.WEST;
                    p.add(name, con);
                    
                    con.weightx = 0;
                    con.fill = GridBagConstraints.NONE;
                    con.anchor = GridBagConstraints.EAST;
                    con.gridheight = 1;
                    con.gridwidth = 1;
                    con.gridx = 2;
                    con.gridy = 0;
                    p.add(index, con);
                    
                    con.anchor = GridBagConstraints.WEST;
                    con.gridheight = 1;
                    con.gridwidth = 1;
                    con.gridx = 1;
                    con.gridy = 1;
                    p.add(date, con);
                    
                    con.anchor = GridBagConstraints.EAST;
                    con.gridheight = 1;
                    con.gridwidth = 1;
                    con.gridx = 2;
                    con.gridy = 1;
                    p.add(priority, con);
                    
                    
                    con.weightx = 1;
                    con.anchor = GridBagConstraints.NORTHEAST;
                    con.fill = GridBagConstraints.HORIZONTAL;
                    con.gridheight = 1;
                    con.gridwidth = 1;
                    con.gridx = 0;
                    con.gridy = i-1;
                    taskList.add(p, con);
                    i++;
                }
                taskList.revalidate();
                taskList.repaint();
            }
          }
        );
        
        
        command.setToolTipText("Field 1 hover");
        add(command);
        
        
        layout.putConstraint(SpringLayout.WEST, command, 10,
                SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, command, -10,
                        SpringLayout.EAST, contentPane); 
        layout.putConstraint(SpringLayout.SOUTH, command, -10,
                        SpringLayout.SOUTH, contentPane);
        layout.getConstraints(command).setHeight(Spring.constant(20));
        
        layout.putConstraint(SpringLayout.WEST, taskListScroll, 10,
                        SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, taskListScroll, 10,
                        SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, taskListScroll, -10, 
                        SpringLayout.NORTH, command);
        layout.getConstraints(taskListScroll).setWidth(Spring.constant(385));
        
        layout.putConstraint(SpringLayout.WEST, overview, 10,
                        SpringLayout.EAST, taskListScroll);
        layout.putConstraint(SpringLayout.NORTH, overview, 10,
                        SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, overview, -10,
                        SpringLayout.EAST, contentPane);
        layout.getConstraints(overview).setHeight(Spring.constant(220));
        
        layout.putConstraint(SpringLayout.WEST, feedbackScroll, 10,
                        SpringLayout.EAST, taskListScroll);
        layout.putConstraint(SpringLayout.NORTH, feedbackScroll, 10,
                        SpringLayout.SOUTH, overview);
        layout.putConstraint(SpringLayout.SOUTH, feedbackScroll, 0,
                        SpringLayout.SOUTH, taskListScroll);
        layout.putConstraint(SpringLayout.EAST, feedbackScroll, -10,
                        SpringLayout.EAST, contentPane);
        
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setVisible(true);
 
    }
    public static void main(String[] args) {
        QLLogic.clearWorkingList();
        QLGUI g = new QLGUI();
        
    }
   
}
