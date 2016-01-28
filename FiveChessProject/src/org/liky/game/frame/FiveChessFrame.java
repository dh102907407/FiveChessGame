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
	int [][] allChess=new int[19][19];//0��ʾû�����壬1��ʾ���壬2��ʾ����
	boolean canPlay=true;
	String Message="�ڵ����ߣ�";
	int maxTime=0;
	Thread t=new Thread(this);//�̲߳���
	int blackTime=0;
	int whiteTime=0;//����ڰ�˫��ʣ��ʱ����Ϣ
	String blackMessage="������";
	String whiteMessage="������";

	public FiveChessFrame(){
		this.setTitle("�ŵ�һ�Ƶ�������");
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
			bgImage=ImageIO.read(new File("E:/ѧϰ��ʥ��/java/������/ͼƬ�ز�/��������.jpg"));
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
		g2.setFont(new Font("����",Font.BOLD,20));
		g2.drawString("��Ϸ��Ϣ��"+Message, 124, 60);
		g2.setFont(new Font("����",0,12));
		g2.drawString("�ڷ�ʱ�䣺"+blackMessage, 30,468);
		g2.drawString("�׷�ʱ�䣺"+whiteMessage, 250,468);
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
		
		//��������
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
		System.out.println("����λ�ã�x:"+e.getX()+"����λ��y:"+e.getY());
		// TODO Auto-generated method stub
		if (canPlay==true){
		 x=e.getX();  //200
		 y=e.getY();  //100
		if(((x>=12)&&(x<=372))&&((y>=72)&&(y<=432))){
			x=(x-12+10)/20; //5
			y=(y-72+10)/20;		//3
			if (allChess[x][y]!=0){
				flag=allChess[x][y];
				JOptionPane.showMessageDialog(this,"��ǰλ���Ѿ������ӣ�����������!");
			}
			else{
			if (flag==2){
				flag=1;
				Message="��С��������";
			}
			else if (flag==1) {
				flag=2;
				Message="С�����߰���";
			}			
			}
			
			allChess[x][y]=flag; //allChess[5][3]=2
			
			boolean winFlag = this.checkWin();
			if (winFlag==true){
				JOptionPane.showMessageDialog(this, "��Ϸ����,"+(allChess[x][y]==1 ? "�ڷ�":"�׷�")+"��ʤ��");
				canPlay=false;
			}
			this.repaint();
			
		}//�����ӣ�������ʵ�ĺ�Բ�� �����ÿ��ĺ�Բ+ʵ�İ�Բ
		}
		//��������ʼ������Ϸ��ť
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=74)&&(e.getY()<=101))){
					int result =JOptionPane.showConfirmDialog(this,"�Ƿ����¿�ʼ��Ϸ��");
					if(result==0){
						allChess=new int[19][19];
						canPlay=true;
						Message="�ڵ����ߣ�";		
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
							whiteMessage="������";
							blackMessage="������";
						}
						this.repaint();
					}
				}
		//��������ʼ������Ϸ��ť
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=124)&&(e.getY()<=151))){
//					JOptionPane.showMessageDialog(this,"��Ϸ����");
					String input=JOptionPane.showInputDialog("��������Ϸ�����ʱ��(����)���������0����ʾû��ʱ�����ơ�");
				try{
					maxTime = Integer.parseInt(input)*60;
					if (maxTime<0){
						JOptionPane.showMessageDialog(this, "����ȷ������Ϣ,���������븺����");
					}
					if (maxTime==0){
						int result = JOptionPane.showConfirmDialog(this, "�������!�Ƿ����¿�ʼ��Ϸ");
						if (result ==0){
							allChess=new int[19][19];
							canPlay=true;
							Message="�ڵ����ߣ�";
							flag=2;
							whiteMessage="������";
							blackMessage="������";
							this.repaint();
						}
					}
					if (maxTime>0){
						int result = JOptionPane.showConfirmDialog(this, "�������!�Ƿ����¿�ʼ��Ϸ");
						if (result ==0){
							allChess=new int[19][19];
							canPlay=true;
							Message="�ڵ����ߣ�";
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
						JOptionPane.showMessageDialog(this, "����ȷ������Ϣ����Ч�ַ�");
				}
				
				}
		//��������Ϸ���ð�ť
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=174)&&(e.getY()<=201))){
					JOptionPane.showMessageDialog(this,"����һ����������򣬺ڰ�˫���������壬��ĳһ����������ʱ����Ϸ������");
				}
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=274)&&(e.getY()<=301))){
					int result = JOptionPane.showConfirmDialog(this,"����ķ�������");
					if (result==0){
						if (flag==1){
						JOptionPane.showMessageDialog(this,"�׷��Ѿ����䣬��Ϸ����");
					}
						if(flag==2){
						JOptionPane.showMessageDialog(this,"���Ѿ����䣬��Ϸ����");	
						}
						canPlay=false;
					}
				}
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=324)&&(e.getY()<=351))){
					JOptionPane.showMessageDialog(this,"������������Ӷ�����д�ģ���������⣬�����үȥ�����ʲ�֪����");
				}
				if (((e.getX()>=402)&&(e.getX()<=470))&&((e.getY()>=374)&&(e.getY()<=401))){
					JOptionPane.showMessageDialog(this,"��Ϸ����");
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
		if (maxTime>0){  //�ж��Ƿ�������ʱ��
			while(true)
			{
				if (flag==1)
				{
					blackTime--;
					if (blackTime==0)
					{JOptionPane.showMessageDialog(this,"�ڷ���ʱ����Ϸ������");}
				}
				else{
					whiteTime--;
					if (whiteTime==0)
					{JOptionPane.showMessageDialog(this,"�׷���ʱ����Ϸ������");}
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
