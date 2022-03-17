package tw.Max.Class;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

public class MyPainter extends JPanel {
	private LinkedList<LinkedList<HashMap<String, Integer>>> lines, recycle;
	
	public MyPainter() {
		MyListener myListener = new MyListener();
		lines = new LinkedList<>();
		recycle = new LinkedList<>();
		
		setBackground(Color.lightGray);
		addMouseListener(myListener);
		addMouseMotionListener(myListener);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 不知道父類別在做什麼的時候可以拿掉
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.DARK_GRAY); // 畫線顏色
		g2d.setStroke(new BasicStroke(4)); // 畫線粗細
		
		for (LinkedList<HashMap<String, Integer>> line : lines) { // 尋訪每一條線
			for (int i = 1; i < line.size(); i++) {
				HashMap<String, Integer> p1 = line.get(i-1); // 取得上一個點
				HashMap<String, Integer> p2 = line.get(i); // 取得當下的點
				g2d.drawLine(p1.get("x"), p1.get("y"), p2.get("x"), p2.get("y")); // 畫線
			}
		}
	}
	
	// 內部類別 方便存取 MouseAdapter是連接器 可以免除匿名類別要產出一堆不必要的Listener
	private class MyListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			HashMap<String, Integer> point = new HashMap<>();
			point.put("x", e.getX());
			point.put("y", e.getY());
			
			LinkedList<HashMap<String, Integer>> line = new LinkedList<>();
			line.add(point); // 產生一條新的線
			lines.add(line); // 放到多線中
			repaint(); // 重新渲染
			recycle.clear();
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			HashMap<String, Integer> point = new HashMap<>();
			point.put("x", e.getX());
			point.put("y", e.getY());
			lines.getLast().add(point); // 新增最後一條
			repaint();
		}
	}
	
	public void clear() {
		lines.clear();
		repaint();
	}
	
	public void undo() {
		if (lines.size() > 0) {
			recycle.add(lines.removeLast()); // 丟掉時放入收藏箱
			repaint();
		}
	}
	
	public void redo() {
		if (recycle.size() > 0) {
			lines.add(recycle.removeLast());
			repaint();
		}
	}
}
