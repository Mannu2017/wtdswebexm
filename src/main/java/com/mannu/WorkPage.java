package com.mannu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.mannu.model.AckDetail;
import com.mannu.utility.DataUtility;
import com.mortennobel.imagescaling.ResampleOp;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class WorkPage extends Thread{
	private String username,fullname,role;
	DataUtility utility=new DataUtility();
	private JLabel imglbl;
	JPanel panel;
	AckDetail ackDetail;
	private JTextField ackno;
	private double zoom = 1.0;
	
	public WorkPage(String username, String fullname, String role) {
		this.username=username;
		this.fullname=fullname;
		this.role=role;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void run() {
		JFrame frame=new JFrame("Welcome "+fullname);
		frame.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		JButton btnShow = new JButton("Start");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ackDetail = utility.getImage(username,role);
				if(ackDetail.getAckno()==null) {
					JOptionPane.showMessageDialog(null, "No Record Found");
				} else {
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File(ackDetail.getFilepath()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Image image=img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
					imglbl.setIcon(new ImageIcon(image));
					ackno.setText(ackDetail.getAckno()+" / "+ackDetail.getSamackno());
				}
			}
		});
		btnShow.setBounds(80,  (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-130, 89, 23);
		frame.getContentPane().add(btnShow);
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 10, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-20, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-150);
		frame.getContentPane().add(panel);
		
		imglbl = new JLabel("");
		panel.add(imglbl);
		
		JButton btnSave = new JButton("Save");
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ackDetail = utility.getImageSave(username,role,ackDetail.getAckno());
				if(ackDetail.getAckno()==null) {
					JOptionPane.showMessageDialog(null, "No Pending Record");
				} else {
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File(ackDetail.getFilepath()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Image image=img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
					imglbl.setIcon(new ImageIcon(image));
					ackno.setText(ackDetail.getAckno()+" / "+ackDetail.getSamackno());
				}
			}
		});
		btnSave.setBounds(183, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-130, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnRotateRight = new JButton("Rotate Right");
		btnRotateRight.setMnemonic(KeyEvent.VK_R);
		btnRotateRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					BufferedImage bi = null;
					try {
						bi = ImageIO.read(new File("C:\\temp\\temp.png"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	                BufferedImage output = rotate90ToRight(bi);
	                try {
						ImageIO.write(output, "png", new File("C:\\temp\\temp.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
	                Image image=output.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
					imglbl.setIcon(new ImageIcon(image));
					ackno.setText(ackDetail.getAckno()+" / "+ackDetail.getSamackno());
			}
		});
		btnRotateRight.setBounds(285, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-130, 117, 23);
		frame.getContentPane().add(btnRotateRight);
		
		JButton btnRotateLeft = new JButton("Rotate Left");
		btnRotateLeft.setMnemonic(KeyEvent.VK_L);
		btnRotateLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage bi = null;
				try {
					bi = ImageIO.read(new File("C:\\temp\\temp.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                BufferedImage output = rotate90ToLeft(bi);
                try {
					ImageIO.write(output, "png", new File("C:\\temp\\temp.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
                Image image=output.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
				imglbl.setIcon(new ImageIcon(image));
				ackno.setText(ackDetail.getAckno()+" / "+ackDetail.getSamackno());
			}
		});
		btnRotateLeft.setBounds(414, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-130, 117, 23);
		frame.getContentPane().add(btnRotateLeft);
		
		JButton btnReject = new JButton("Reject");
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ackDetail = utility.getImageReject(username,role,ackDetail.getAckno(),ackDetail.getEmail(),ackDetail.getSamackno(),ackDetail.getAckdate(),ackDetail.getResname(),ackDetail.getSmartinwardno(),ackDetail.getScandate());
				if(ackDetail.getAckno()==null) {
					JOptionPane.showMessageDialog(null, "No Pending Record");
				} else {
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File(ackDetail.getFilepath()));
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					Image image=img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
					imglbl.setIcon(new ImageIcon(image));
					ackno.setText(ackDetail.getAckno()+" / "+ackDetail.getSamackno());
				}
				
			}
		});
		btnReject.setBounds(541, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-130, 117, 23);
		frame.getContentPane().add(btnReject);
		
		JLabel lblShort = new JLabel("Shortcut Keys:");
		lblShort.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblShort.setBounds(90, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-95, 101, 14);
		frame.getContentPane().add(lblShort);
		
		JLabel lblForSaveAlt = new JLabel("For Save:  Alt + S   /  Rotate Right: Alt + R  /  Rotate Left: Alt + L");
		lblForSaveAlt.setBounds(193, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-95, 350, 14);
		frame.getContentPane().add(lblForSaveAlt);
		
		JLabel lblAckNo = new JLabel("Barcode / Ack No:");
		lblAckNo.setBounds(668, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-127, 117, 14);
		frame.getContentPane().add(lblAckNo);
		
		ackno = new JTextField();
		ackno.setFont(new Font("Tahoma", Font.BOLD, 11));
		ackno.setEditable(false);
		ackno.setBounds(779, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-130, 304, 20);
		frame.getContentPane().add(ackno);
		ackno.setColumns(10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public BufferedImage rotate90ToRight( BufferedImage inputImage ){
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage( height, width , inputImage.getType() );

		for( int x = 0; x < width; x++ ) {
			for( int y = 0; y < height; y++ ) {
				returnImage.setRGB( height - y -1, x, inputImage.getRGB( x, y  )  );
			}
		}
		return returnImage;
	}
	
	public BufferedImage rotate90ToLeft( BufferedImage inputImage ){
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage( height, width , inputImage.getType()  );
				for( int x = 0; x < width; x++ ) {
			for( int y = 0; y < height; y++ ) {
				returnImage.setRGB(y, width - x - 1, inputImage.getRGB( x, y  )  );
			}
			}
		return returnImage;
	}
}
