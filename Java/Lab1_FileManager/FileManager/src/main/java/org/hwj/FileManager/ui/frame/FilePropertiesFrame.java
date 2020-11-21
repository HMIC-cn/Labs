package org.hwj.FileManager.ui.frame;

import org.hwj.FileManager.file.tool.AttributeTool;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 一个文件或文件夹的属性界面视图
 * 对已有代码做了一些适应性调整
 * @author liugezi/FileManager
 */
public class FilePropertiesFrame extends JFrame implements ActionListener {
	public String path;
	public JTextField NameField, Location;
	public JLabel ImageLabel, SizeLabel, Size, LocationLabel, CreateTimeLabel, CreateTime, ModifyTimeLabel, ModifyTime, AccessTimeLabel, AccessTime,
	TypeLabel, Type, IncludeLabel, Include, FileSysLabel, FileSys, UsedSizeLabel, UsedSize, AvaiSizeLabel, AvaiSize, TotalSizeLabel, TotalSize;	
	public JPanel HeadPanel, MidPanel, BotPanel;
	public JButton EnsureBtn;
	private AttributeTool attributeTool = new AttributeTool();
	private Integer[] filesCount = new Integer[2];

	public FilePropertiesFrame(File file) {
		//文件右击选择属性时
		super();
		if (file.isFile()) {
			this.setTitle("文件属性");
			this.setBounds(500, 500, 360, 630);
			this.getContentPane().setLayout(null);
			Init1();
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setResizable(false);

			this.path = file.getAbsolutePath();
			this.NameField.setText(file.getName());
			this.Size.setText(attributeTool.getFileSize(file));
			this.Location.setText(file.getAbsolutePath());
			this.CreateTime.setText(attributeTool.getFileCreationTime(file));
			this.ModifyTime.setText(attributeTool.getFileModifyTime(file));
			this.AccessTime.setText(attributeTool.getFileLastAccessTime(file));
		} else {
			this.setTitle("文件夹属性");
			this.setBounds(500, 500, 360, 630);
			this.getContentPane().setLayout(null);
			Init2();
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setResizable(false);

			this.path = file.getAbsolutePath();
			this.NameField.setText(file.getName());
			this.Type.setText("文件夹");
			new Thread(() -> {
				filesCount[0] = filesCount[1] = 0;
				getFilesCount(file);
				this.Include.setText(filesCount[0] + "个文件," + filesCount[1] + "个文件夹");
			}).start();
			new Thread(() -> this.Size.setText(attributeTool.getFileSize(file))).start();
			this.Location.setText(file.getAbsolutePath());
			this.CreateTime.setText(attributeTool.getFileCreationTime(file));
		}
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
	}

	/**
	 * 统计目录下文件和文件夹的个数
	 * TODO 做成多线程的递归方式找到目录下的所有个数
	 * @param file 需要查询个数的目录
	 */
	private void getFilesCount(File file) {
		if (file == null) return;
		if (file.isFile()) {
			filesCount[0]++;
		} else {
			if (file.listFiles() != null) {
				for (File file1 : file.listFiles()) {
					getFilesCount(file1);
				}
				filesCount[1]++;
			}
		}
	}

	private void Init1() {
		//文件属性界面初始化
		HeadPanel = new JPanel();
		HeadPanel.setBounds(10, 10, 330, 100);
		HeadPanel.setLayout(null);
		ImageLabel = new JLabel();
		ImageLabel.setBounds(50, 30, 40, 40);
		NameField = new JTextField();
		NameField.setBounds(80, 40, 200, 30);
		NameField.setEditable(false);
		HeadPanel.add(ImageLabel);
		HeadPanel.add(NameField);
		HeadPanel.setBorder(BorderFactory.createTitledBorder("文件名称"));
		this.add(HeadPanel);
		
		//中间的Panel
		MidPanel = new JPanel();
		MidPanel.setBounds(10, 110, 330, 150);
		MidPanel.setLayout(null);
		MidPanel.setBorder(BorderFactory.createTitledBorder("大小及位置"));
		
		SizeLabel = new JLabel();
		SizeLabel.setLocation(10, 30);
		SizeLabel.setSize(50, 30);
		SizeLabel.setText("大小:");
		Size = new JLabel();
		Size.setLocation(60, 30);
		Size.setSize(300, 30);

		LocationLabel = new JLabel("位置：");
		LocationLabel.setBounds(10,80,60,30);
		Location = new JTextField();
		Location.setSize(260,30);
		Location.setLocation(60,80);
		Location.setEditable(false);
		MidPanel.add(SizeLabel);
		MidPanel.add(Size);
		MidPanel.add(LocationLabel);
		MidPanel.add(Location);		
		this.add(MidPanel);
		
		//底部的Panel
		BotPanel = new JPanel();
		BotPanel.setBounds(10, 270, 330, 270);
		BotPanel.setLayout(null);
		BotPanel.setBorder(BorderFactory.createTitledBorder("相关时间"));
		
		CreateTimeLabel = new JLabel("创建时间：");
		CreateTimeLabel.setBounds(10,50,90,20);
	    CreateTime = new JLabel();
	    CreateTime.setBounds(100,50,200,20);

	    ModifyTimeLabel = new JLabel("修改时间：");
	    ModifyTimeLabel.setBounds(10,100,90,20);
	    ModifyTime = new JLabel();
	    ModifyTime.setBounds(100,100,200,20);

	    AccessTimeLabel = new JLabel("访问时间：");
	    AccessTimeLabel.setBounds(10,150,90,20);
	    AccessTime = new JLabel();
	    AccessTime.setBounds(100,150,200,20);

	    BotPanel.add(CreateTimeLabel);
	    BotPanel.add(CreateTime);
	    BotPanel.add(ModifyTimeLabel);
	    BotPanel.add(ModifyTime);	  
	    BotPanel.add(AccessTimeLabel);
	    BotPanel.add(AccessTime);	  
		this.add(BotPanel);
	}
	
	private void Init2() {
		//文件夹属性界面初始化
		HeadPanel = new JPanel();
		HeadPanel.setBounds(10, 10, 330, 100);
		HeadPanel.setLayout(null);
		ImageLabel = new JLabel();
		ImageLabel.setBounds(50, 30, 40, 40);
		NameField = new JTextField();
		NameField.setBounds(80, 40, 200, 30);
		NameField.setEditable(false);
		HeadPanel.add(ImageLabel);
		HeadPanel.add(NameField);
		HeadPanel.setBorder(BorderFactory.createTitledBorder("文件夹名称"));
		this.add(HeadPanel);
		
		//中间的Panel
		MidPanel = new JPanel();
		MidPanel.setBounds(10, 110, 330, 190);
		MidPanel.setLayout(null);
		MidPanel.setBorder(BorderFactory.createTitledBorder("参数"));		
		
		TypeLabel = new JLabel();
		TypeLabel.setLocation(10, 30);
		TypeLabel.setSize(50, 20);
		TypeLabel.setText("类型:");
		Type = new JLabel();
		Type.setLocation(60, 30);
		Type.setSize(300, 20);
		Type.setText("文件夹");
		
		SizeLabel = new JLabel();
		SizeLabel.setLocation(10, 65);
		SizeLabel.setSize(50, 20);
		SizeLabel.setText("大小:");
		Size = new JLabel();
		Size.setLocation(60, 65);
		Size.setSize(300, 20);
		Size.setText("0MB");
		
		LocationLabel = new JLabel("位置：");
		LocationLabel.setBounds(10,100,90,30);
		Location = new JTextField();
		Location.setSize(260,30);
		Location.setLocation(60,100);
		Location.setEditable(false);

		IncludeLabel = new JLabel("包含：");
		IncludeLabel.setBounds(10,135,90,20);
		Include = new JLabel();
		Include.setSize(260,20);
		Include.setLocation(60,135);		

		MidPanel.add(TypeLabel);
		MidPanel.add(Type);
		MidPanel.add(SizeLabel);
		MidPanel.add(Size);
		MidPanel.add(LocationLabel);
		MidPanel.add(Location);		
		MidPanel.add(IncludeLabel);
		MidPanel.add(Include);
		this.add(MidPanel);
		
		//底部的Panel
		BotPanel = new JPanel();
		BotPanel.setBounds(10, 330, 330, 100);
		BotPanel.setLayout(null);
		BotPanel.setBorder(BorderFactory.createTitledBorder("相关时间"));
		
		CreateTimeLabel = new JLabel("创建时间：");
		CreateTimeLabel.setBounds(10,30,90,20);
	    CreateTime = new JLabel();
	    CreateTime.setBounds(100,30,200,20);

	    BotPanel.add(CreateTimeLabel);
	    BotPanel.add(CreateTime);
	   
		this.add(BotPanel);

		//确定按钮
		EnsureBtn = new JButton("确定");
		EnsureBtn.setBounds(240, 550, 90, 30);
		EnsureBtn.addActionListener(this);
		this.add(EnsureBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
}
