package tw.Max.RunCode;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import tw.Max.Class.MyClock;
import tw.Max.Class.MyPainter;

public class Painter extends JFrame {
	private JButton clear, undo, redo, saveJPG, saveObj, loadObj;
	private MyPainter myPainter;
	private MyClock myClock;
	
	public Painter() {
		// 定義視窗
		super("Painter");
		setLayout(new BorderLayout());
		
		// 放置上方選單列
		JPanel top = new JPanel(new FlowLayout());
		add(top, BorderLayout.NORTH);
		
		// 畫布
		myPainter = new MyPainter();
		add(myPainter, BorderLayout.CENTER);
		
		// 時鐘
		myClock = new MyClock();
		top.add(myClock);
		
		// 清除鍵
		clear = new JButton("Clear");
		top.add(clear);
		
		// undo
		undo = new JButton("UnDo");
		top.add(undo);
		
		// redo
		redo = new JButton("ReDo");
		top.add(redo);
		
		// save
		saveJPG = new JButton("Save JPG");
		top.add(saveJPG);
		
		// saveObj
		saveObj = new JButton("Save Object");
		top.add(saveObj);
		
		// load
		loadObj = new JButton("Load Object");
		top.add(loadObj);
		
		
		setSize(720, 480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setListener();
	}
	
	private void setListener() {
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		
		redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redo();
			}
		});
		
		saveJPG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveJPG();
			}
		});
		
		saveObj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveObj();
			}
		});
		
		loadObj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadObj();
			}
		});
	}
	
	private void clear() {
		myPainter.clear();
	}
	
	private void redo() {
		myPainter.redo();
	}
	
	private void undo() {
		myPainter.undo();
	}
	
	// BufferedImage.TYPE_INT_BGR 基本款
	private void saveJPG() {
		BufferedImage img = new BufferedImage(myPainter.getWidth(),  // 放入BufferedImage
				myPainter.getHeight(), BufferedImage.TYPE_INT_BGR);
		Graphics g = img.createGraphics(); // 產生Graphics
		myPainter.paint(g); // 重新渲染，不渲染的話會一片空的，沒有畫面
		
		JFileChooser jfc = new JFileChooser();
		if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			try {
				ImageIO.write(img, "jpg", new File(file.toString().concat(".jpg"))); // 產生檔案
				JOptionPane.showMessageDialog(null, "儲存成功");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "儲存失敗");
			}
		}
	}
	
	private void saveObj() {
		JFileChooser jfc = new JFileChooser();
		if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			try {
				myPainter.saveLines(file);
				JOptionPane.showMessageDialog(null, "儲存成功");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "儲存失敗");
			}
		}
	}
	
	private void loadObj() {
		JFileChooser jfc = new JFileChooser();
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			try {
				myPainter.loadLines(file);
				JOptionPane.showMessageDialog(null, "載入成功");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "載入失敗");
			}
		}
	}

	public static void main(String[] args) {
		new Painter();
	}
}
