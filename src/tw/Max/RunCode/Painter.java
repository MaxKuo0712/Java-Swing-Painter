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

import tw.Max.Class.MyPainter;

public class Painter extends JFrame {
	private JButton clear, undo, redo, save, obj;
	private MyPainter myPainter;
	
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
		save = new JButton("Save");
		top.add(save);
		
		// obj
		obj = new JButton("Object");
		top.add(obj);
		
		
		setSize(640, 480);
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
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		obj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				obj();
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
	private void save() {
		BufferedImage img = new BufferedImage(myPainter.getWidth(),  // 放入BufferedImage
				myPainter.getHeight(), BufferedImage.TYPE_INT_BGR);
		Graphics g = img.createGraphics(); // 產生Graphics
		myPainter.paint(g); // 重新渲染，不渲染的話會一片空的，沒有畫面
		
		try {
			ImageIO.write(img, "jpg", new File("dir1/Max.jpg")); // 產生檔案
			JOptionPane.showMessageDialog(null, "儲存成功");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	private void obj() {
		
	}

	public static void main(String[] args) {
		new Painter();
	}
}
