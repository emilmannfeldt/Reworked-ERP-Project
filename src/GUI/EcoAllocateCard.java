package GUI;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controllers.EcoCreateAllocationController;
import objects.Project;
import objects.User;
import controllers.TextFieldLimit;

public class EcoAllocateCard extends JPanel {
	private JTextField OtherRoleText;
	private DefaultListModel<Project> ListModelProject;
	DefaultListModel<User> ListModelUser;
	EcoCreateAllocationController controller = new EcoCreateAllocationController();
	private JTextField HourlyRateText;
	boolean other=false;
	/**
	 * Create the panel.
	 */
	public EcoAllocateCard() {
		setLayout(null);
		
	
		
		JLabel lblChooseConsultant = new JLabel("1. Choose consultant");
		lblChooseConsultant.setBounds(20, 33, 147, 14);
		add(lblChooseConsultant);
		
		JLabel lblChoose = new JLabel("2. Choose project");
		lblChoose.setBounds(231, 33, 160, 14);
		add(lblChoose);
		
		JLabel lblSetOptions = new JLabel("3. Set Options");
		lblSetOptions.setBounds(444, 33, 108, 14);
		add(lblSetOptions);
		
		OtherRoleText = new JTextField();
		OtherRoleText.setBounds(538, 151, 133, 20);
		add(OtherRoleText);
		OtherRoleText.setColumns(10);
		OtherRoleText.show(false);
		
		JLabel OtherLabel = new JLabel("Other Role");
		OtherLabel.setBounds(444, 154, 84, 14);
		add(OtherLabel);
		OtherLabel.show(false);
		
		JComboBox RoleBox = new JComboBox();
		RoleBox.setBounds(538, 120, 133, 20);
		RoleBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				//om other �r valt s� st�ngs boxen av och en text ruta �ppnas ist�llet
                String selected = RoleBox.getSelectedItem().toString();
                if(selected.equals("Other")){
                	OtherRoleText.setText(" ");
                	OtherRoleText.show(true);
                	OtherLabel.show(true);
                	other = true;
                	          	
                }
                else{
                	OtherRoleText.show(false);
                	OtherLabel.show(false);
                	other=false;
                }
			}
		});
		this.add(RoleBox);
		RoleBox.addItem("Business analyst");
		RoleBox.addItem("Projekt ledare");
		RoleBox.addItem("Scrum master");
		RoleBox.addItem("Process manager");
		RoleBox.addItem("Development manager");
		RoleBox.addItem("Quality coordinator");
		RoleBox.addItem("Other");
		
		
		JLabel lblProjectRole = new JLabel("Project Role");
		lblProjectRole.setBounds(444, 123, 84, 14);
		this.add(lblProjectRole);
		
		JLabel lblWorkLoad = new JLabel("Work Load");
		lblWorkLoad.setBounds(445, 92, 83, 14);
		this.add(lblWorkLoad);
		
		JComboBox WorkLoadBox = new JComboBox();
		WorkLoadBox.setBounds(538, 89, 55, 20);
		this.add(WorkLoadBox);
		WorkLoadBox.addItem("25");
		WorkLoadBox.addItem("50");
		WorkLoadBox.addItem("75");
		WorkLoadBox.addItem("100");
		
		//Fetch all projects from database
		List<Project> allprojects;
		allprojects=controller.getallprojects();	
		
		//create adds projects to a listmodel
		ListModelProject = new DefaultListModel<Project>();
		
		for(int i =0;i<allprojects.size();i++){
			
			
			ListModelProject.add(i, allprojects.get(i));
			
			
			
			
		}
		
		List<User> allconsultants;
		allconsultants=controller.getAllPersons();
		
		//create adds users to a listmodel
		ListModelUser = new DefaultListModel<User>();
		
		for(int i =0;i<allconsultants.size();i++){
			
			
			ListModelUser.add(i, allconsultants.get(i));
	
			
		}

		
		JLabel label_1 = new JLabel("Hourly Rate");
		label_1.setBounds(444, 61, 89, 14);
		this.add(label_1);
		
		HourlyRateText = new JTextField();
		HourlyRateText.setBounds(538, 58, 86, 20);
		HourlyRateText.setText("323, 12, 867, 498");
		HourlyRateText.setColumns(10);
		this.add(HourlyRateText);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 49, 256, 438);
		add(scrollPane_1);
		scrollPane_1.setSize(200, 250);
		
		JList ConsultantList = new JList();
		scrollPane_1.setViewportView(ConsultantList);
		ConsultantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ConsultantList.setModel(ListModelUser);
		ConsultantList.setSize(200, 250);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(231, 49, 272, 438);
		add(scrollPane);
		scrollPane.setSize(200, 250);
		
		JList ProjectList = new JList();
		scrollPane.setViewportView(ProjectList);
		ProjectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ProjectList.setModel(ListModelProject); 
		ProjectList.setSize(200, 250);
		
		JButton AllocateButton = new JButton("Allocate Resource");
		AllocateButton.setBounds(538, 276, 133, 23);
		HourlyRateText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char i = arg0.getKeyChar();
				if(!(Character.isDigit(i)||(i==KeyEvent.VK_BACK_SPACE)|| i==KeyEvent.VK_DELETE ))
				{
					getToolkit().beep();
					arg0.consume();
				}
			}
		});
		HourlyRateText.setDocument(new TextFieldLimit(4));
		OtherRoleText.setDocument(new TextFieldLimit(15));
		
		AllocateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String role = "No role assigned";
				String projectnr=ListModelProject.getElementAt(ProjectList.getSelectedIndex()).getProjectNumber();
				String user=ListModelUser.getElementAt(ConsultantList.getSelectedIndex()).getSSN();
				
				String workload=WorkLoadBox.getSelectedItem().toString();
				String hourlyrate=HourlyRateText.getText();
				if(other){
					role=OtherRoleText.getText();
					RoleBox.enable(true);
					RoleBox.setSelectedIndex(0);
				}
				else{
					role=RoleBox.getSelectedItem().toString();
				}
				//insert this into projectmember table
				controller.CreateAllocation(hourlyrate,projectnr,role,user,workload);
				OtherLabel.show(false);
				OtherRoleText.show(false);
				
			
			}
		});
		this.add(AllocateButton);
		
		JLabel label = new JLabel("%");
		label.setBounds(596, 95, 55, 14);
		add(label);

	}
}
