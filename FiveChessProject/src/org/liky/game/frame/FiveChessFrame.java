package org.liky.game.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FiveChessFrame extends JFrame implements MouseListener,Runnable{
	
	BufferedImage bgImage = null; 
	int x=0,y=0;
	int flag=2;
	int [][] allChess=new int[19][19];//0表示没有下棋，1表示黑棋，2表示白棋
	boolean canPlay=true;
	String Message="黑的先走！";
	int maxTime=0;
	Thread t=new Thread(this);//线程操作
	int blackTime=0;
	int whiteTime=0;//保存黑白双方剩余时间信息
	String blackMessage="无限制";
	String whiteMessage="无限制";

	public FiveChessFrame(){
		this.setTitle("屌的一逼的五子棋");
		this.setSize(497,476+30);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width/2-100,height/2-100);
		
		
		
		for (int i=0;i<19;i++){
			for (int j=0;j<19;j++){
				allChess[i][j]=0;
			}
		}
	
		//System.out.println(b/20);
	
		
		this.addMouseListener(this);
		
		
		try {
			bgImage=ImageIO.read(new File("E:/学习的圣土/java/五子棋/图片素材/五子棋盘.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setVisible(true);
		t.start();
		t.suspend();
		
	}

	public void paint(Graphics g){
		
		BufferedImage bi =new BufferedImage(497,476+30,BufferedImage.TYPE_INT_ARGB);
		Graphics g2=bi.createGraphics();
		
		g2.setColor(Color.black);
		g2.drawImage(bgImage,1,21,this);
		g2.setFont(new Font("黑体",Font.BOLD,20));
		g2.drawString("游戏信息："+Message, 124, 60);
		g2.setFont(new Font("宋体",0,12));
		g2.drawString("黑方时间："+blackMessage, 30,468);
		g2.drawString("白方时间："+whiteMessage, 250,468);
		g2.setColor(Color.black);
		g2.drawRect(12,72,360,360);
		for (int i=1;i<=18;i++){
			g2.drawLine(12, 72+20*i, 372, 72+20*i);
			g2.drawLine(12+20*i, 72, 12+20*i, 432);
		}
		g2.fillOval(70,130, 4, 4);
		g2.fillOval(190,130, 4, 4);
		g2.fillOval(310,130, 4, 4);
		g2.fillOval(310,370, 4, 4);
		g2.fillOval(190,370, 4, 4);
		g2.fillOval(70,370, 4, 4);
		g2.fillOval(310,250, 4, 4);
		g2.fillOval(190,250, 4, 4);
		g2.fillOval(70,250, 4, 4);
		
		//绘制棋子
		for (int i=0;i<19;i++){
			for(int j=0;j<19;j++){
				
				if (allChess[i][j]==1){
					g2.setColor(Color.black);
					g2.fillOval(5+i*20,65+j*20,14,14);
				}
				if (allChess[i][j]==2){
					g2.setColor(Color.white);
					g2.fillOval(5+i*20,65+j*20,14,14);
					g2.setColor(Color.black);
					g2.drawOval(5+i*20,65+j*20,14,14);
				}
			}
		}
		
		g.drawImage(bi,0,0,this);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("按下位置：x:"+e.getX()+"按下位置y:"+e.getY());
		// TODO Auto-generated method stub
		if (canPlay==true){
		 x=e.getX();  //200
		 y=e.getY();  //100
		if(((x>=12)&&(x<=372))&&((y>=72)&&(y<=432))){
			x=(x-12+10)/20; //5
			y=(y-72+10)/20;		//3
			if (allChess[x][y]!=0){
				flag=allChess[x][y];
				JOptionPane.showMessageDialog(this,"当前位置已经有棋子，请重新落子!");
			}
			else{
			if (flag==2){
				flag=1;
				Message="该小白你啦！";
			}
			else if (flag==1) {
				flag=2;
				Message="小黑你走啊！";
			}			
			}
			
			allChess[x][y]=flag; //allChess[5][3]=2
			
			boolean winFlag = this.checkWin();
			if (winFlag==true){
				JOptionPane.showMessageDialog(this, "游戏结束,"+(allChess[x][y]==1 ? "黑方":"白方")+"获胜！");
				canPlay=false;
			}
			this.repaint();
			
		}//画棋子：黑子用实心黑圆， 白子用空心黑圆+实心白圆
		}
		//进入点击开始进入游戏按钮
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=74)&&(e.getY()<=101))){
					int result =JOptionPane.showConfirmDialog(this,"是否重新开始游戏？");
					if(result==0){
						allChess=new int[19][19];
						canPlay=true;
						Message="黑的先走！";		
						flag=2;
						blackTime=maxTime;
						whiteTime=maxTime;
						if(maxTime>0){
							blackMessage=maxTime/3600+":"+(maxTime/60-maxTime/3600*60)+":"
									+(maxTime-maxTime/60*60);
									whiteMessage=maxTime/3600+":"+(maxTime/60-maxTime/3600*60)+":"
											+(maxTime-maxTime/60*60);
						}
						else {
							whiteMessage="无限制";
							blackMessage="无限制";
						}
						this.repaint();
					}
				}
		//进入点击开始进入游戏按钮
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=124)&&(e.getY()<=151))){
//					JOptionPane.showMessageDialog(this,"游戏设置");
					String input=JOptionPane.showInputDialog("请输入游戏的最大时间(分钟)，如果输入0，表示没有时间限制。");
				try{
					maxTime = Integer.parseInt(input)*60;
					if (maxTime<0){
						JOptionPane.showMessageDialog(this, "请正确输入信息,不允许输入负数！");
					}
					if (maxTime==0){
						int result = JOptionPane.showConfirmDialog(this, "设置完成!是否重新开始游戏");
						if (result ==0){
							allChess=new int[19][19];
							canPlay=true;
							Message="黑的先走！";
							flag=2;
							whiteMessage="无限制";
							blackMessage="无限制";
							this.repaint();
						}
					}
					if (maxTime>0){
						int result = JOptionPane.showConfirmDialog(this, "设置完成!是否重新开始游戏");
						if (result ==0){
							allChess=new int[19][19];
							canPlay=true;
							Message="黑的先走！";
							flag=2;
							blackTime=maxTime;
							whiteTime=maxTime;
							blackMessage=maxTime/3600+":"+(maxTime/60-maxTime/3600*60)+":"
							+(maxTime-maxTime/60*60);
							whiteMessage=maxTime/3600+":"+(maxTime/60-maxTime/3600*60)+":"
									+(maxTime-maxTime/60*60);
							t.resume();
							this.repaint();
						}			
					}
				}	catch(NumberFormatException e1){
						JOptionPane.showMessageDialog(this, "请正确输入信息，无效字符");
				}
				
				}
		//进入点击游戏设置按钮
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=174)&&(e.getY()<=201))){
					JOptionPane.showMessageDialog(this,"这是一个五子棋程序，黑白双方轮流下棋，当某一方连到五子时，游戏结束。");
				}
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=274)&&(e.getY()<=301))){
					int result = JOptionPane.showConfirmDialog(this,"你真的放弃了吗？");
					if (result==0){
						if (flag==1){
						JOptionPane.showMessageDialog(this,"白方已经认输，游戏结束");
					}
						if(flag==2){
						JOptionPane.showMessageDialog(this,"黑已经认输，游戏结束");	
						}
						canPlay=false;
					}
				}
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=324)&&(e.getY()<=351))){
					JOptionPane.showMessageDialog(this,"这个程序是老子董航编写的，有相关问题，问你大爷去，劳资不知道！");
				}
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=374)&&(e.getY()<=401))){
					JOptionPane.showMessageDialog(this,"游戏结束");
					System.exit(0);
				}
				
	}

	private boolean checkWin(){
		boolean a = false;
//		int count=0;
		int color=allChess[x][y];	

		int count1 =this.checkCount(1, 0,color);
		int count2=this.checkCount(0,1,color);
		int count3=this.checkCount(1,1,color);
		int count4=this.checkCount(-1,1,color);
		System.out.println(count1+count2+count3+count4);
		if ((count1==5)||(count2==5)||(count3==5)||(count4==5)){
			a=true;
		}
			
		return a;
	}
	private int checkCount(int xChange, int yChange, int color){
		int count=1;
		int tempX=xChange;
		int tempY=yChange;
		while((x+xChange>=0&&x+xChange<=18&&y+yChange>=0&&y+yChange<=18)&&(color==allChess[x+xChange][y+yChange])){
			count++;
			if (xChange!=0){
				if(xChange>0){
					xChange++;
				}
				else {
					xChange--;
				}
				
			}
			if (yChange!=0){
				if(yChange>0){
					yChange++;
				}
				else {
					yChange--;
				}
				
			}
						
		}
		xChange=tempX;
		yChange=tempY;
		while ((x-xChange>=0&&x-xChange<=18&&y-yChange>=0&&y-yChange<=18)&&(color==allChess[x-xChange][y-yChange])){			
				count++;
				if (xChange!=0){
					if(xChange>0){
						xChange++;
					}
					else {
						xChange--;
					}
					
				}
				if (yChange!=0){
					if(yChange>0){
						yChange++;
					}
					else {
						yChange--;
					}
					
				}
							
		
		}
		return count;
		
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (maxTime>0){  //判断是否设置了时间
			while(true)
			{
				if (flag==1)
				{
					blackTime--;
					if (blackTime==0)
					{JOptionPane.showMessageDialog(this,"黑方超时，游戏结束！");}
				}
				else{
					whiteTime--;
					if (whiteTime==0)
					{JOptionPane.showMessageDialog(this,"白方超时，游戏结束！");}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				blackMessage=blackTime/3600+":"+(blackTime/60-blackTime/3600*60)+":"
						+(blackTime-blackTime/60*60);
						whiteMessage=whiteTime/3600+":"+(whiteTime/60-whiteTime/3600*60)+":"
								+(whiteTime-whiteTime/60*60);
						this.repaint();
			}
		}
		
	}

}
