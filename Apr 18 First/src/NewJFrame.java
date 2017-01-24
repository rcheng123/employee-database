
import java.awt.event.KeyEvent;
import java.util.Objects;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robin
 */
public class NewJFrame extends javax.swing.JFrame {

    private EmployeeInfo myEmployee;
    private NewJDialog modifyEmployee = new NewJDialog(this, true);
    private MyHT myHashTable = new MyHT(5); //5 buckets
    private boolean added;

    private String fileSaveLocation = null; //Determine where file is saved

    private int tempEmpNum; //Temporary variable for employee #

    public int getTempEmpNum() {
        return (tempEmpNum);
    }

    public MyHT getMyHashTable() {
        return (myHashTable);
    }

    public EmployeeInfo getMyEmployee() {
        return (myEmployee);

    }

    public boolean getadded() {
        return (added);

    }

    //Displaying employee list on jtable
    public void setDisplayTable(ArrayList<EmployeeInfo>[] myArrayList) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        table.setAutoCreateRowSorter(true);

        model.setRowCount(0);   //Clear first

        int countsize = 0;

        for (int i = 0; i < myArrayList.length; i++) {
            countsize = countsize + myArrayList[i].size();
        }

        model.setRowCount(countsize);   //Set number of rows for jtable

        int rowcounter = 0;

        //Extract array list and display on jtable
        for (int x = 0; x < myArrayList.length; x++) {
            for (int y = 0; y < myArrayList[x].size(); y++) {

                table.getModel().setValueAt(myArrayList[x].get(y).getEmployeeNumber(), rowcounter, 0);
                table.getModel().setValueAt(myArrayList[x].get(y).getFirstName(), rowcounter, 1);
                table.getModel().setValueAt(myArrayList[x].get(y).getLastName(), rowcounter, 2);
                if (myArrayList[x].get(y) instanceof PartTimeEmployee) {
                    table.getModel().setValueAt("Part Time", rowcounter, 3);
                    table.getModel().setValueAt(((PartTimeEmployee) myArrayList[x].get(y)).getNetIncome(), rowcounter, 4);
                } else if (myArrayList[x].get(y) instanceof FullTimeEmployee) {
                    table.getModel().setValueAt("Full Time", rowcounter, 3);
                    table.getModel().setValueAt(((FullTimeEmployee) myArrayList[x].get(y)).getNetIncome(), rowcounter, 4);
                }
                rowcounter++;
            }
        }
    }

    //Determine position of certain character
    public int ordinalIndexOf(String str, String c, int n) {
        int pos = str.indexOf(c, 0);
        while (n-- > 0 && pos != -1) {
            pos = str.indexOf(c, pos + 1);
        }
        return pos;
    }

    //Add part time
    public void addPartTime(String e, String f, String l, String d, String hw, String hpw, String wpy) {
        try {
            int bb = Integer.parseInt(e);
            if (Double.parseDouble(d) > 1 || Double.parseDouble(d) < 0) {
                JOptionPane.showMessageDialog(null, "Deduction rate between 0-1");
                added = false;
            } else if (bb <= 0) {
                JOptionPane.showMessageDialog(null, "Employee number must be a positive integer");
            } else {
                myEmployee = new PartTimeEmployee(Integer.parseInt(e), f, l, Double.parseDouble(d), Integer.parseInt(hw), Integer.parseInt(hpw), Integer.parseInt(wpy));
                myHashTable.addToTable(myEmployee);
                added = true;

                //Clear text field
                add_EN.setText("");
                add_FN.setText("");
                add_LN.setText("");
                add_DR.setText("0.");
                add_S.setText("");
                add_HPW.setText("");
                add_WPY.setText("");

                add_FN.requestFocus();  //Reset cursor
            }

        } catch (NumberFormatException b) {
            System.out.println("Needs a number.");
            JOptionPane.showMessageDialog(null, "input a number");
            added = false;

        }

        //Display new arraylist on jtable
        setDisplayTable(myHashTable.getArrayList());

    }

    //Add full time
    public void addFullTime(String e, String f, String l, String d, String s) {
        try {
            myEmployee = new FullTimeEmployee(Integer.parseInt(e), f, l, Double.parseDouble(d), Double.parseDouble(s));

            if (Double.parseDouble(d) > 1 || Double.parseDouble(d) < 0) {
                JOptionPane.showMessageDialog(null, "Double range 0-1");
                added = false;
            } else if (Integer.parseInt(e) <= 0) {
                JOptionPane.showMessageDialog(null, "Employee number must be a positive integer");
            } else {
                myHashTable.addToTable(myEmployee);
                added = true;
                add_EN.setText("");
                add_FN.setText("");
                add_LN.setText("");
                add_DR.setText("0.");
                add_S.setText("");
                add_HPW.setText("");
                add_WPY.setText("");

                add_FN.requestFocus();
            }
        } catch (NumberFormatException b) {
            System.out.println("Needs a number");
            JOptionPane.showMessageDialog(null, "input a number");
            added = false;
        }
        // safeGuard(1,1);
        setDisplayTable(myHashTable.getArrayList());

    }

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();

        // Set certain textbox visible
        jLabel12.setVisible(false);
        jLabel13.setVisible(false);
        jLabel14.setVisible(false);
        add_S.setVisible(false);
        add_HPW.setVisible(false);
        add_WPY.setVisible(false);
        add_DR.setText("0.");

        add_FN.requestFocus();

        table.setAutoCreateColumnsFromModel(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    /*
    private void safeGuard(int e, int i) {
        if ("".equals(add_FN.getText()) || add_LN.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "insufficent input ");

        } else {
            try {
                switch (e) {
                    case 0:
                        break;
                    case 1:
                        myEmployee = new PartTimeEmployee(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()), Double.parseDouble(add_S.getText()), Double.parseDouble(add_HPW.getText()), Double.parseDouble(add_WPY.getText()));
                        break;
                    case 2:
                        myEmployee = new FullTimeEmployee(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()), Double.parseDouble(add_S.getText()));
                        break;
                    default:
                        break;
                }
                switch (i) {
                    case 1:
                        //add
                        myHashTable.addToTable(myEmployee);
                        break;
                    case 2:
                        //remove
                        myHashTable.removeFromTable(Integer.parseInt(remove_EN.getText()));
                        break;
                    case 3:
                        setDisplayTable(myHashTable.searchDisplayEmployee(Integer.parseInt(search_EN.getText())));
                        break;
                    default:
                        break;
                }

            } catch (NumberFormatException b) {
                System.out.println("Needs a number.");
                JOptionPane.showMessageDialog(null, "input a number");

            }

        }
    }
     */
    //Check for insufficient input, number format, etc.
    private void safeGuard(int e, int i) {
        if (e != 0 && ("".equals(add_FN.getText()) || add_LN.getText().equals(""))) {
            JOptionPane.showMessageDialog(null, "insufficent input ");

        } else {
            try {
                switch (e) {
                    case 0:
                        break;
                    case 1:
                        myEmployee = new PartTimeEmployee(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()), Double.parseDouble(add_S.getText()), Double.parseDouble(add_HPW.getText()), Double.parseDouble(add_WPY.getText()));
                        break;
                    case 2:
                        myEmployee = new FullTimeEmployee(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()), Double.parseDouble(add_S.getText()));
                        break;
                    default:
                        break;
                }
                switch (i) {
                    case 1:
                        //add
                        myHashTable.addToTable(myEmployee);
                        break;
                    case 2:
                        //remove
                        myHashTable.removeFromTable(Integer.parseInt(remove_EN.getText()));
                        break;
                    case 3:
                        setDisplayTable(myHashTable.searchAndDisplayEmployee(Integer.parseInt(search_EN.getText())));
                        break;
                    case 4:

                        break;
                    default:
                        break;
                }

            } catch (NumberFormatException b) {
                System.out.println("Needs a number.");
                JOptionPane.showMessageDialog(null, "input a number");

            }
            /*catch (InputMismatchException |NullPointerException b) {
	    JOptionPane.showMessageDialog(null, "complete all inputs");
	}*/
        }
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        fileChooser = new javax.swing.JFileChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        addEmployee = new javax.swing.JButton();
        add_FN = new javax.swing.JTextField();
        add_LN = new javax.swing.JTextField();
        add_EN = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        add_FTCheckBox = new javax.swing.JCheckBox();
        add_PTCheckBox = new javax.swing.JCheckBox();
        add_DR = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        add_Error = new javax.swing.JLabel();
        add_S = new javax.swing.JTextField();
        add_HPW = new javax.swing.JTextField();
        add_WPY = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        removeEmployee = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        remove_EN = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        search_EN = new javax.swing.JTextField();
        searchEmployee = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cancelSearch = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        };
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        Open = new javax.swing.JMenuItem();
        Save = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jCheckBox1.setText("jCheckBox1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EmployeeInfo Database - New File");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        addEmployee.setText("Add an Employee");
        addEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployeeActionPerformed(evt);
            }
        });
        addEmployee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addEmployeeKeyPressed(evt);
            }
        });

        add_FN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_FNActionPerformed(evt);
            }
        });

        jLabel2.setText("First Name:");

        jLabel3.setText("Last Name:");

        jLabel4.setText("Employee #:");

        add_FTCheckBox.setText("Full-Time");
        add_FTCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_FTCheckBoxMouseClicked(evt);
            }
        });
        add_FTCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_FTCheckBoxActionPerformed(evt);
            }
        });
        add_FTCheckBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_FTCheckBoxKeyPressed(evt);
            }
        });

        add_PTCheckBox.setText("Part-Time");
        add_PTCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_PTCheckBoxMouseClicked(evt);
            }
        });
        add_PTCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_PTCheckBoxActionPerformed(evt);
            }
        });
        add_PTCheckBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_PTCheckBoxKeyPressed(evt);
            }
        });

        add_DR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_DRActionPerformed(evt);
            }
        });
        add_DR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_DRKeyPressed(evt);
            }
        });

        jLabel11.setText("Deduction Rate:");

        add_Error.setText("Full/Part Time:");

        add_S.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_SActionPerformed(evt);
            }
        });
        add_S.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_SKeyPressed(evt);
            }
        });

        add_HPW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_HPWActionPerformed(evt);
            }
        });

        add_WPY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_WPYActionPerformed(evt);
            }
        });
        add_WPY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_WPYKeyPressed(evt);
            }
        });

        jLabel12.setText("Hourly Wage:");

        jLabel13.setText("Hours per Week:");

        jLabel14.setText("Weeks per Year:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(add_Error)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(add_HPW, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(add_S, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(add_FTCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(add_PTCheckBox))
                            .addComponent(add_WPY)
                            .addComponent(add_DR, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(add_LN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(add_FN)
                            .addComponent(add_EN, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(addEmployee)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(add_FN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_LN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_EN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_DR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_PTCheckBox)
                    .addComponent(add_FTCheckBox)
                    .addComponent(add_Error))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_S, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_HPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_WPY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addEmployee)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add", jPanel1);

        removeEmployee.setText("Remove an Employee");
        removeEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeEmployeeActionPerformed(evt);
            }
        });
        removeEmployee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                removeEmployeeKeyPressed(evt);
            }
        });

        jTextField4.setText("Feature Not Available Yet");

        jTextField5.setText("Feature Not Available Yet");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        remove_EN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                remove_ENKeyPressed(evt);
            }
        });

        jLabel5.setText("First Name:");

        jLabel6.setText("Last Name:");

        jLabel7.setText("Employee #:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(remove_EN, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(removeEmployee)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remove_EN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(removeEmployee)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Remove", jPanel2);

        jTextField7.setText("Feature Not Available Yet");
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextField8.setText("Feature Not Available Yet");
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        search_EN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_ENActionPerformed(evt);
            }
        });
        search_EN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_ENKeyPressed(evt);
            }
        });

        searchEmployee.setText("Search");
        searchEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEmployeeActionPerformed(evt);
            }
        });
        searchEmployee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchEmployeeKeyPressed(evt);
            }
        });

        jLabel8.setText("First Name:");

        jLabel9.setText("Last Name:");

        jLabel10.setText("Employee #:");

        cancelSearch.setText("Cancel");
        cancelSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelSearchActionPerformed(evt);
            }
        });
        cancelSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cancelSearchKeyPressed(evt);
            }
        });

        jTextField1.setText("Not Availabe Yet");

        jTextField2.setText("Not Availabe Yet");

        jLabel15.setText("Salary:");

        jLabel16.setText("From");

        jLabel17.setText("To");

        jLabel18.setText("Net Income:");

        jTextField3.setText("Not Availabe Yet");

        jTextField6.setText("Not Availabe Yet");

        jLabel19.setText("From");

        jLabel20.setText("To");

        jLabel21.setText("Not Available Yet");

        jCheckBox2.setText("Full Time");

        jCheckBox3.setText("Part Time");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(search_EN)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel19))
                                        .addGap(10, 10, 10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel16))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField6)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jCheckBox2)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox3)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(searchEmployee)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_EN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchEmployee)
                    .addComponent(cancelSearch))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Search", jPanel3);

        jLabel1.setFont(new java.awt.Font("Maiandra GD", 0, 18)); // NOI18N
        jLabel1.setText("EmployeeInfo Database");

        jButton2.setText("View Details / Modify the Employee");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [0][4],
            new String [] {
                "Employee #", "First Name", "Last Name", "Full/Part Time","Net Income"

            }
        ));
        jScrollPane3.setViewportView(table);

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("New File");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        Open.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        Open.setText("Open");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });
        jMenu1.add(Open);

        Save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });
        jMenu1.add(Save);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItem1.setText("Save As (Not Available Yet)");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem4.setText("Exit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 286, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(246, 246, 246))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployeeActionPerformed
        /*
        if (myHashTable.searchTable(Integer.parseInt(add_EN.getText())) != -1) {
            JOptionPane.showMessageDialog(null, "Employee Number Already Exist");
            return;
        }

//        myEmployee = new EmployeeInfo(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()));
        if (add_PTCheckBox.isSelected() == true) {

//     addPartTime(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()), Double.parseDouble(add_S.getText()), Double.parseDouble(add_HPW.getText()), Double.parseDouble(add_WPY.getText()));
            safeGuard(1, 1);

        } else if (add_FTCheckBox.isSelected() == true) {

//  addFullTime(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()), Double.parseDouble(add_S.getText()));
            safeGuard(2, 1);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a checkbox");
        }

        setDisplayTable(myHashTable.displayTableModel());

        add_EN.setText("");
        add_FN.setText("");
        add_LN.setText("");
        add_DR.setText("");
        add_S.setText("");
        add_HPW.setText("");
        add_WPY.setText("");

        add_FN.requestFocus();
         */

        if ("".equals(add_FN.getText()) || add_LN.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "insufficent input ");
        } //        myEmployee = new EmployeeInfo(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()));
        else if (add_PTCheckBox.isSelected() == true) {
            try {

                //addPartTime(Integer.parseInt(add_EN.getText()), add_FN.getText(), add_LN.getText(), Double.parseDouble(add_DR.getText()), Double.parseDouble(add_S.getText()), Double.parseDouble(add_HPW.getText()), Double.parseDouble(add_WPY.getText()));
                if (myHashTable.searchTable(Integer.parseInt(add_EN.getText())) != -1) {
                    JOptionPane.showMessageDialog(null, "You cannot use the same employee number twice");
                } else {
                    addPartTime(add_EN.getText(), add_FN.getText(), add_LN.getText(), add_DR.getText(), add_S.getText(), add_HPW.getText(), add_WPY.getText());
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please input a number");
            }

            //safeGuard(1, 1);
        } else if (add_FTCheckBox.isSelected() == true) {
            try {
                if (myHashTable.searchTable(Integer.parseInt(add_EN.getText())) != -1) {
                    JOptionPane.showMessageDialog(null, "You cannot use the same employee number twice");
                } else {
                    addFullTime(add_EN.getText(), add_FN.getText(), add_LN.getText(), add_DR.getText(), add_S.getText());
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please input a number");
            }
            //safeGuard(2, 1);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a checkbox");
        }

//        setDisplayTable(myHashTable.displayTableModel());
        setDisplayTable(myHashTable.getArrayList());

    }//GEN-LAST:event_addEmployeeActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void removeEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeEmployeeActionPerformed
        // TODO add your handling code here:
//        myHashTable.removeFromTable(Integer.parseInt(remove_EN.getText()));
        //       remove_EN.setText("");
        //       setDisplayTable(myHashTable.displayTableModel());

        safeGuard(0, 2);
        remove_EN.setText("");
        setDisplayTable(myHashTable.getArrayList());


    }//GEN-LAST:event_removeEmployeeActionPerformed

    private void searchEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchEmployeeActionPerformed
        //       setDisplayTable(myHashTable.searchDisplayEmployee(Integer.parseInt(search_EN.getText())));
        //      search_EN.setText("");

        safeGuard(0, 3);
        search_EN.setText("");


    }//GEN-LAST:event_searchEmployeeActionPerformed

    private void add_FTCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_FTCheckBoxActionPerformed
        // Set certain things visible/invisible if full time is checked
        add_PTCheckBox.setSelected(false);

        jLabel12.setVisible(true);
        jLabel13.setVisible(false);
        jLabel14.setVisible(false);
        add_S.setVisible(true);
        add_HPW.setVisible(false);
        add_WPY.setVisible(false);

        jLabel12.setText("Salary:");

        if (add_FTCheckBox.isSelected() == false) {
            jLabel12.setVisible(false);
            jLabel13.setVisible(false);
            jLabel14.setVisible(false);
            add_S.setVisible(false);
            add_HPW.setVisible(false);
            add_WPY.setVisible(false);
        }

    }//GEN-LAST:event_add_FTCheckBoxActionPerformed

    private void add_PTCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_PTCheckBoxActionPerformed
        // Set certain things visible/invisible if part time is checked

        add_FTCheckBox.setSelected(false);

        jLabel12.setVisible(true);
        jLabel13.setVisible(true);
        jLabel14.setVisible(true);
        add_S.setVisible(true);
        add_HPW.setVisible(true);
        add_WPY.setVisible(true);

        jLabel12.setText("Hourly Wage:");

        if (add_PTCheckBox.isSelected() == false) {
            jLabel12.setVisible(false);
            jLabel13.setVisible(false);
            jLabel14.setVisible(false);
            add_S.setVisible(false);
            add_HPW.setVisible(false);
            add_WPY.setVisible(false);
        }
    }//GEN-LAST:event_add_PTCheckBoxActionPerformed

    private void add_FNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_FNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_FNActionPerformed

    private void add_DRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_DRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_DRActionPerformed

    private void add_DRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_DRKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_add_DRKeyPressed

    private void remove_ENKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_remove_ENKeyPressed
        // set Enter key as shortcut 
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            removeEmployee.doClick();
        }
    }//GEN-LAST:event_remove_ENKeyPressed

    private void search_ENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_ENActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_ENActionPerformed

    private void add_HPWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_HPWActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_HPWActionPerformed

    private void add_WPYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_WPYKeyPressed
        // set Enter key as shortcut
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addEmployee.doClick();
        }
    }//GEN-LAST:event_add_WPYKeyPressed

    private void add_FTCheckBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_FTCheckBoxKeyPressed
        // set Enter key as shortcut
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            add_FTCheckBox.doClick();
        }
    }//GEN-LAST:event_add_FTCheckBoxKeyPressed

    private void add_PTCheckBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_PTCheckBoxKeyPressed
        // set Enter key as shortcut
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            add_PTCheckBox.doClick();
        }
    }//GEN-LAST:event_add_PTCheckBoxKeyPressed

    private void add_SActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_SActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_SActionPerformed

    private void add_PTCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_PTCheckBoxMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_add_PTCheckBoxMouseClicked

    private void add_FTCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_FTCheckBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_add_FTCheckBoxMouseClicked

    private void add_WPYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_WPYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_WPYActionPerformed

    private void add_SKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_SKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER && add_FTCheckBox.isSelected() == true) {
            addEmployee.doClick();
        }
    }//GEN-LAST:event_add_SKeyPressed

    private void search_ENKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_ENKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchEmployee.doClick();
        }

    }//GEN-LAST:event_search_ENKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
/*
        int bucketIndex = myHashTable.searchTable(Integer.parseInt(jTable2.getModel().getValueAt(jTable2.getSelectedRow(), 0).toString()));
        tempEmpNum = Integer.parseInt(jTable2.getModel().getValueAt(jTable2.getSelectedRow(), 0).toString());
        EmployeeInfo selectedEmployee = myHashTable.getBuckets(myHashTable.calcBucket(tempEmpNum)).get(bucketIndex);

        modifyEmployee.setTextFieldModify(selectedEmployee);

        modifyEmployee.setVisible(true);
         */

         // Determine and display modify employee
        try {
            // searching employee position within certain bucket
            int bucketIndex = myHashTable.searchTable(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString()));
            // temporary variable to store employee number (incase employee number has been modified)
            tempEmpNum = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
            // get that specific employee
            EmployeeInfo selectedEmployee = myHashTable.getBuckets(myHashTable.calcBucket(tempEmpNum)).get(bucketIndex);
            // set modify window in a new window
            modifyEmployee.setTextFieldModify(selectedEmployee);
            modifyEmployee.setVisible(true);
        } catch (java.lang.NullPointerException | ArrayIndexOutOfBoundsException e) {
            System.out.println("asdfasdfasdf");
            JOptionPane.showMessageDialog(null, "Please select a table with value");

        }


    }//GEN-LAST:event_jButton2ActionPerformed

    //Save file
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed

        boolean successful = true;

        // If no file selected then exit
        if (fileSaveLocation == null) {
            int result = fileChooser.showSaveDialog(this);
            if (result == fileChooser.CANCEL_OPTION) {
                return;
            }
            
            //determine saved location
            fileSaveLocation = fileChooser.getSelectedFile().getAbsolutePath();
        }

       // String fileName = fileChooser.getSelectedFile().toString();
        if (!(fileSaveLocation.endsWith(".txt"))) {
            fileSaveLocation += ".txt";
        }

        File file = new File(fileSaveLocation);

        try {

            if (!file.exists()) {
                file.createNewFile();//create new file
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileSaveLocation)));
            for (int i = 0; i < myHashTable.getArrayList().length; i++) {
                for (int j = 0; j < myHashTable.getArrayList()[i].size(); j++) {

                    //Write each employee entries onto the file
                    if (myHashTable.getArrayList()[i].get(j) instanceof FullTimeEmployee) {
                        out.print("FT#");
                        out.print(myHashTable.getArrayList()[i].get(j).getEmployeeNumber() + "#");
                        out.print(myHashTable.getArrayList()[i].get(j).getFirstName() + "#");
                        out.print(myHashTable.getArrayList()[i].get(j).getLastName() + "#");
                        out.print(myHashTable.getArrayList()[i].get(j).getDeductionsRate() + "#");
                        out.println(((FullTimeEmployee) myHashTable.getArrayList()[i].get(j)).getSalary());
                    } else if (myHashTable.getArrayList()[i].get(j) instanceof PartTimeEmployee) {
                        out.print("PT#");
                        out.print(myHashTable.getArrayList()[i].get(j).getEmployeeNumber() + "#");
                        out.print(myHashTable.getArrayList()[i].get(j).getFirstName() + "#");
                        out.print(myHashTable.getArrayList()[i].get(j).getLastName() + "#");
                        out.print(myHashTable.getArrayList()[i].get(j).getDeductionsRate() + "#");
                        out.print(((PartTimeEmployee) myHashTable.getArrayList()[i].get(j)).getHourlyWage() + "#");
                        out.print(((PartTimeEmployee) myHashTable.getArrayList()[i].get(j)).getHoursPerWeek() + "#");
                        out.println(((PartTimeEmployee) myHashTable.getArrayList()[i].get(j)).getWeeksPerYear());

                    }
                }

            }
            out.println("###END###");

            out.close();

        } catch (IOException e) {
            System.out.println("--------------------------------");
            successful = false;
        } finally {
            if (successful == true) {
                setTitle(fileChooser.getSelectedFile().getName());  //Set tile of header to file name
            }
        }


    }//GEN-LAST:event_SaveActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        // TODO add your handling code here:
        int result = fileChooser.showOpenDialog(this);

        if (result == fileChooser.CANCEL_OPTION) {
            return;
        }

        BufferedReader br = null;

        myHashTable.clearArrayList();   //Clear array list

        boolean successful = true;

        String currentLine = null;
        fileSaveLocation = fileChooser.getSelectedFile().getAbsolutePath();

        try {

            br = new BufferedReader(new FileReader(fileSaveLocation));

            while ((currentLine = br.readLine()) != null) {
                if (Objects.equals(currentLine, "###END###")) {
                    break;
                }
                if (Objects.equals(currentLine.substring(0, 2), "FT")) {
                    addFullTime(
                            currentLine.substring(3, ordinalIndexOf(currentLine, "#", 1)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 1) + 1, ordinalIndexOf(currentLine, "#", 2)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 2) + 1, ordinalIndexOf(currentLine, "#", 3)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 3) + 1, ordinalIndexOf(currentLine, "#", 4)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 4) + 1));

                } else if (Objects.equals(currentLine.substring(0, 2), "PT")) {
                    addPartTime(
                            currentLine.substring(3, ordinalIndexOf(currentLine, "#", 1)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 1) + 1, ordinalIndexOf(currentLine, "#", 2)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 2) + 1, ordinalIndexOf(currentLine, "#", 3)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 3) + 1, ordinalIndexOf(currentLine, "#", 4)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 4) + 1, ordinalIndexOf(currentLine, "#", 5)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 5) + 1, ordinalIndexOf(currentLine, "#", 6)),
                            currentLine.substring(ordinalIndexOf(currentLine, "#", 6) + 1));

                }
            }

        } catch (IOException | StringIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Error. Try Again");
            successful = false;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (successful == false) {
                    fileSaveLocation = null;
                } else {
                    setTitle(fileChooser.getSelectedFile().getName());  //Set header title
                }
            } catch (IOException ee) {
                System.out.println("xxxxxxxxxxxxxxxxxxxx");
            }
        }

        setDisplayTable(myHashTable.getArrayList());    //Display employeeinfo on jtable


    }//GEN-LAST:event_OpenActionPerformed

    private void cancelSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelSearchActionPerformed
        setDisplayTable(myHashTable.getArrayList());
        search_EN.setText("");
    }//GEN-LAST:event_cancelSearchActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // New file
        fileSaveLocation = null;
        setTitle("EmployeeInfo DataBase - New File");
        myHashTable.clearArrayList();   //Clear everything
        setDisplayTable(myHashTable.getArrayList());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void addEmployeeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addEmployeeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addEmployee.doClick();
        }
    }//GEN-LAST:event_addEmployeeKeyPressed

    private void removeEmployeeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_removeEmployeeKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            removeEmployee.doClick();
        }

    }//GEN-LAST:event_removeEmployeeKeyPressed

    private void searchEmployeeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchEmployeeKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchEmployee.doClick();
        }


    }//GEN-LAST:event_searchEmployeeKeyPressed

    private void cancelSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cancelSearchKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cancelSearch.doClick();
        }

    }//GEN-LAST:event_cancelSearchKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Open;
    private javax.swing.JMenuItem Save;
    private javax.swing.JButton addEmployee;
    private javax.swing.JTextField add_DR;
    private javax.swing.JTextField add_EN;
    private javax.swing.JLabel add_Error;
    private javax.swing.JTextField add_FN;
    private javax.swing.JCheckBox add_FTCheckBox;
    private javax.swing.JTextField add_HPW;
    private javax.swing.JTextField add_LN;
    private javax.swing.JCheckBox add_PTCheckBox;
    private javax.swing.JTextField add_S;
    private javax.swing.JTextField add_WPY;
    private javax.swing.JButton cancelSearch;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JButton removeEmployee;
    private javax.swing.JTextField remove_EN;
    private javax.swing.JButton searchEmployee;
    private javax.swing.JTextField search_EN;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
