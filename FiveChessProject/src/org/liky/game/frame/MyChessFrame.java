package org.liky.game.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MyChessFrame extends JFrame implements MouseListener {
	public MyChessFrame() {
	
		this.setTitle("屌的一逼的五子棋");
		this.setSize(497,476+20);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-100,height/2-100);
		
		this.addMouseListener(this);
		this.setVisible(true);
		
	}
	public void paint(Graphics g){

		BufferedImage image=null;
		try {
			image = ImageIO.read(new File("E:/学习的圣土/java/五子棋/图片素材/五子棋盘.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//做IO操作可能会读不到文件，做异常操作处理
		g.drawImage(image,1,21,this);
	//	
		g.setColor(Color.RED);
		g.drawOval(getX()-10,getY()-10,20,20);
	//	g.fillOval(20,40,40,40);
		g.drawRect(40,40,40,20);
		g.setFont(new Font("黑体",30,30));
		g.drawString("五子棋游戏",40,80);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("按下位置：x:"+e.getX()+"按下位置y:"+e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("抬起位置：x:"+e.getX()+"抬起位置y:"+e.getY());
	}
}
