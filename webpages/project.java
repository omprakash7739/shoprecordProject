/**
 * @(#)MAINFRAME.java
 *
 *
 * @author Ajit
 * @version 1.00 2010/3/10
 */



import java.io.*;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.JTextArea;

 
public class MAINFRAME extends JFrame implements ActionListener 
    {
    /**********Parameters************/
    /****************parameters for the gabor filters****************/
    public double dx =1.8;
    public double dy=0.8;
    public double t1=4;
    public double t2=28;
    public int[][] pixel1;// =new int [1000][1000];
    public int[][] pixel2;// =new int [1000][1000];
    public int[][] sdx1 ;//= new int [1000][1000];
    public int[][]sdx2 ;//=new int [1000][1000];
    float fx[];
    float fy[];
    
    public int[][] sdy1;// =new int [1000][1000];
    public int[][]sdy2 ;//= new int [1000][1000];
    public double [][] vx1;// = new double [1000][1000];
    public double [][] vy1;// =new double [1000][1000];
    public double [][] vx2;// = new double [1000][1000];
    public double [][] vy2 ;//=new double [1000][1000];
    
    public double [][] thita1;// = new double [1000][1000];
    public double [][] thita2;// =new double [1000][1000];
    public double [][]gaborfilter1;// = new double[1000][1000];
    public double [][]gaborfilter2;//= new double [1000][1000];
    public  BufferedImage bimg1, bimg2;
    /******************************/
//  Color []clrimg;
    /**************************/
    int n,m;
    static int img =0;
    //static int framecount=0;
    //ImageIcon  [] image;
    public  BufferedImage image1, image2;
    public BufferedImage []images;
    /****************************************/
    JDesktopPane Thedesktop = new JDesktopPane();
    JPanel panel = new JPanel();
    JScrollPane internalScroll = new JScrollPane();
    
    //MENUBAR 
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    JButton button = new JButton("MATCH_BUTTON");
    
    //MENUITEMS.
    JMenuItem open1MenuItem = new JMenuItem("OPEN1");
    //JMenuItem open2MenuItem= new JMenuItem("OPEN2");
    JMenuItem exitMenuItem = new JMenuItem("EXIT");
    //JTextArea textarea = new JTextArea();
    
    File  file1;
    
    JFileChooser jfc = new JFileChooser();
    

    public MAINFRAME() 
        {
            super ("   FINGER PRINT MATCHING SYSTEM  ");
            setSize(600,600);
            menuBar.add(menuFile);
            setJMenuBar (menuBar);
            menuBar.add(button);
            menuFile.add(open1MenuItem);
        //  menuFile.add(open2MenuItem);
            menuFile.add(exitMenuItem);
        /*  textarea.setLocation(10,500);
            textarea.setVisible(true);
            textarea.setEditable(true);
            textarea.setBackground(Color.green);
            super.add(textarea);*/
            
            
            //File component listener
            open1MenuItem.addActionListener(this);
        //  open2MenuItem.addActionListener(this);
            button.addActionListener(this);
            exitMenuItem.addActionListener(this);
            
            getContentPane().add(Thedesktop);
 
            setVisible(true);
            
    }
    
    
 /***************************************************************/
   
    public void actionPerformed (ActionEvent e)
        {
            /////////////////
        if (e.getSource() == open1MenuItem)
            {
                
                int returnValue = jfc.showOpenDialog(MAINFRAME.this);
                if (returnValue == JFileChooser.CANCEL_OPTION)
                 return;
                
                file1 = jfc.getSelectedFile();
                
                JInternalFrame internalframe = new JInternalFrame(file1.getName(),true,true,true,true);
                if(img==0)
                {
                
                internalframe.setLocation(0,0);
                
                }
                else if(img==1)
                {
                
                internalframe.setLocation(272,0);
                }
                img++;
                Container container = internalframe.getContentPane();
                Fingerprint_internalframe frame = new Fingerprint_internalframe(file1);
                JScrollPane internalScroll = new JScrollPane(frame);
                container.add (internalScroll,BorderLayout.CENTER);
                internalframe.pack();
                Thedesktop.add(internalframe);
                internalframe.setVisible(true);
        /**************************************************/
    //  IMPORTANT CODE  
            //////////////
                try
                {
                    if (image1==null)
                    {
                       image1 = (BufferedImage)ImageIO.read(file1);
                       //image1=null;
                       System.out.println("image1 is loaded");
                    }
                    else if(image1!=null && image2==null)
                    {
                        image2=(BufferedImage) ImageIO.read(file1);
                        System.out.println("image2 is loaded");
                    }
                    else if(image1!=null && image2!=null)
                    {
                        System.out.println("both images are loaded" );
                        return;
                    }
                }//end of try 1
                catch (Exception E)
                {
                    System.out.println(E);
                }// end of catch 1
            
            }//end of if 
        
        else if (e.getSource()==button)
        {
            try // try block to count pixel values of the both images.
            {// main try in else if
                 //load imges 1 and image 2 vlaue in bimg image for pixel value and keep original images as well.
                 try
                 {  //inner try in else if
                    //BufferedImage bimg1= new BufferedImage(image1.getWidth(),image1.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
                    bimg1 = this.image1;
                    bimg2 =this.image2;
                    if (bimg1!=image1 && bimg2!=image2)
                    {//if starts
                        throw new IOException("Images read fails");
                    }//if end
                
                 }// end of internal try.
                 catch(Exception E)
                 {
                    System.out.println("\n"+E);
                 }//end of internal catch
         
                    pixel1 = new int[(bimg1.getWidth()/2)][(bimg1.getHeight()/2)];
                    pixel2 = new int[(bimg2.getWidth()/2)][(bimg2.getHeight()/2)];
                 System.out.println("\nPIXELS ARE COUNTED"+ pixel1);
                    sdx1 = new int [(bimg1.getWidth()/2)+1][(bimg1.getHeight()/2)+1];
                    sdy1 = new int [(bimg1.getWidth()/2)+1][(bimg1.getHeight()/2)+1];
                System.out.println("\nSDX1 and SDY1 are calculated"+sdx1 +"  "+sdy1);
                    sdx2 = new int [(bimg2.getWidth()/2)+1][(bimg2.getHeight()/2)+1];
                    sdy2 = new int [(bimg2.getWidth()/2)+1][(bimg2.getHeight()/2)+1];
                System.out.println("\nSDX2 and SDY2 are calculated");
                    vx1 =new double [(bimg1.getWidth()/2)][(bimg1.getHeight()/2)];
                    vy1= new double [(bimg1.getWidth()/2)][(bimg1.getHeight()/2)];
                System.out.println("\nvtx and vty are calculated: "+vx1+""+vy1);
                    vx2 =new double [(bimg2.getWidth()/2)][(bimg2.getHeight()/2)];
                    vy2= new double [(bimg2.getWidth()/2)][(bimg2.getHeight()/2)];

                    thita1 = new double [(bimg1.getWidth()/2)][(bimg1.getHeight()/2)];
                    thita2 = new double [(bimg2.getWidth()/2)][(bimg2.getHeight()/2)];
                System.out.println("\nthita are calculated");
                    gaborfilter1 = new double [(bimg1.getWidth()/2)][(bimg1.getHeight()/2)];
                    gaborfilter2 = new double [(bimg2.getWidth()/2)][(bimg2.getHeight()/2)];
                System.out.println("\ngabrofilter is calculated");
        
        
        
        }// end of outer try block.
        catch (Exception E)
        {
            System.out.println("Error in pixel counting and image loading."+E);
        }// end of outer catch
            System.out.println("Pixel counting and image loading is done");             
    
         /*************************************************************/
         /***BINARISE THE IMAGE IN 0 AND 1 FORM***************THE COLOR OF TH 10 10 IS  :java.awt.Color[r=159,g=149,b=137]*/
         
         try{// try 3
                   if(bimg1!=null)
                  
                   {//if starts
                     int i,j,k=0;
                     
                  //    ImageAveraging imageav=new ImageAveraging(bimg1);
                    
                     int n = bimg1.getHeight();
                    int m= bimg1.getWidth();
                    int ni=n/2;
                    int mi=m/2;
                    int n1=ni-100;
                    int m1= mi-100;
                    int n2=ni+100;
                    int m2=mi+100;
                    //System.out.println(" "+n+ ""+m);
                    //BufferedImage newimage= new BufferedImage(n,m,BufferedImage.TYPE_INT_RGB);
                    //newimage=createCompatibleDestImage();
                    //Graphics g1=newimage.getGraphics();
                    //int [][]pixels;
                    // Averaging image to pixel vise.
                 
                    try{//inner try
                    for (i=n1;i<n2;i++)
                    {
                        for ( j=m1; j<m2;j++)
                        {
                            Color clr1 = new Color(bimg1.getRGB(i-1,j-1));
                            Color clr2 = new Color(bimg1.getRGB(i-1,j));
                            Color clr3 = new Color(bimg1.getRGB(i-1,j+1));
                            Color clr4 = new Color(bimg1.getRGB(i+1,j-1));
                            Color clr5 = new Color(bimg1.getRGB(i,j+1));
                            Color clr6 = new Color(bimg1.getRGB(i+1,j-1));
                            Color clr7 = new Color(bimg1.getRGB(i+1,j));
                            Color clr8 = new Color(bimg1.getRGB(i+1,j+1));
                            Color clr9 = new Color(bimg1.getRGB(i,j));
                            int r= ((clr1.getRed()+clr2.getRed()+clr3.getRed()+clr4.getRed()+clr5.getRed()+clr6.getRed()+clr7.getRed()+clr8.getRed()+clr9.getRed())/9);
                            int b= ((clr1.getBlue()+clr2.getBlue()+clr3.getBlue()+clr4.getBlue()+clr5.getBlue()+clr6.getBlue()+clr7.getBlue()+clr8.getBlue()+clr9.getBlue())/9);
                            int g=((clr1.getGreen()+clr2.getGreen()+clr3.getGreen()+clr4.getGreen()+clr5.getGreen()+clr6.getGreen()+clr7.getGreen()+clr8.getGreen()+clr9.getGreen())/9);
                            
                            //System.out.print("\n "+i+" "+j+" r:"+r);
                            //System.out.println(" b: "+B)/>;
                            //System.out.println("g :"+g);
                                                
                            //Color clrim= new Color(r,g,B)/>;
                            
                            //String clrname= new Color (clrim.getName());
                            //System.out.println(clrim);
                            
                        }//end of j.
                    }//End of I.
                    
                    System.out.println("rgb values are calculated for image 1");
                    }// End of inner try block.
                    catch(Exception E)
                    {
                        System.out.println(" Error in RGB calculation for image1"+E);
                    }//end of inner catch
                    
                    
               /***************************************************************************/
                    System.out.println("\nvalue of n1="+n1+" && n2="+n2);
                    System.out.println("\nvalue of m1="+m1+" && m2="+m2);
                     for (i=n1;i<n2;i++){
                    for (j=m1; j<m2;j++){
                    k++;
                    Color clr = new Color(bimg1.getRGB(i,j));
                    System.out.print(" CLR="+clr);
                        
         if((clr.getRed()<=130)&&(clr.getBlue()<=130)&&(clr.getGreen()<=130))
          {pixel1[i][j] = 0;}
         else 
         {pixel1[i][j] = 1;
          }//end of else.
          } // end of for j.
          }// end of for i.
          } // End of if condition.
         }// END OF outer TRY.
         catch (Exception E)
         {
         System.out.println("The Error Is In Color Calculating OF IMAGE 1"+E+"Pixel1="+pixel1.length);
         }// end of outer catch.
        
        System.out.println("image 1 sharpening is done");
         
         
         
         try{// try 3
                   
                     int i,j,k=0;
                     
                    //ImageAveraging imageav=new ImageAveraging(bimg1);
                    
                     int n = bimg2.getHeight();
                    int m= bimg2.getWidth();
                    //int ni=n/2;
                    //int mi=m/2;
                    //int n1=ni-100;
                    //int m1= mi-100;
                    //int n2=ni+100;
                    //int m2=mi+100;
                    //System.out.println(" "+n+ ""+m);
                    //BufferedImage newimage= new BufferedImage(n,m,BufferedImage.TYPE_INT_RGB);
                    //newimage=createCompatibleDestImage();
                    //Graphics g1=newimage.getGraphics();
                    //int [][]pixels;
                    // Averaging image to pixel vise.
                 
                    //try{//inner try
                    //for (i=n1;i<n2;i++)
                    //{
                        //for ( j=m1; j<m2;j++)
                        //{
                            //Color clr1 = new Color(bimg2.getRGB(i-1,j-1));
                            //Color clr2 = new Color(bimg2.getRGB(i-1,j));
                            //Color clr3 = new Color(bimg2.getRGB(i-1,j+1));
                            //Color clr4 = new Color(bimg2.getRGB(i+1,j-1));
                            //Color clr5 = new Color(bimg2.getRGB(i,j+1));
                            //Color clr6 = new Color(bimg2.getRGB(i+1,j-1));
                            //Color clr7 = new Color(bimg2.getRGB(i+1,j));
                            //Color clr8 = new Color(bimg2.getRGB(i+1,j+1));
                            //Color clr9 = new Color(bimg2.getRGB(i,j));
                            //int r= ((clr1.getRed()+clr2.getRed()+clr3.getRed()+clr4.getRed()+clr5.getRed()+clr6.getRed()+clr7.getRed()+clr8.getRed()+clr9.getRed())/9);
                            //int b= ((clr1.getBlue()+clr2.getBlue()+clr3.getBlue()+clr4.getBlue()+clr5.getBlue()+clr6.getBlue()+clr7.getBlue()+clr8.getBlue()+clr9.getBlue())/9);
                            //int g=((clr1.getGreen()+clr2.getGreen()+clr3.getGreen()+clr4.getGreen()+clr5.getGreen()+clr6.getGreen()+clr7.getGreen()+clr8.getGreen()+clr9.getGreen())/9);
                            
                            //System.out.print("\n "+i+" "+j+" r:"+r);
                            //System.out.println(" b: "+B)/>;
                            //System.out.println("g :"+g);
                                                
                            //Color clrim= new Color(r,g,B)/>;
                            
                            //String clrname= new Color (clrim.getName());
                            //System.out.println(clrim);
                            
                        //}//end of j.
                    //}//End of I.
                    
                    //System.out.println("rgb values are calculated for image 2");
                    //}// End of inner try block.
                    //catch(Exception E)
                    //{
                    //  System.out.println(" Error in RGB calculation for image 2"+E);
                    //}//end of inner catch
                    
                    
               /***************************************************************************/
                    //System.out.println("\nvalue of n1="+n1+" && n2="+n2);
                    //System.out.println("\nvalue of m1="+m1+" && m2="+m2);
                    //int i,j;
                     for (i=0;i<n;i++){
                    for (j=0; j<m;j++){
                    k++;
                    Color clr = new Color(bimg2.getRGB(i,j));
                    System.out.print(" CLR="+clr);
                        
         if((clr.getRed()<=130)&&(clr.getBlue()<=130)&&(clr.getGreen()<=130))
          {pixel2[i][j] = 0;}
         else 
         {pixel2[i][j] = 1;
          }//end of else.
          } // end of for j.
          }// end of for i.
          
         }// END OF outer TRY.
         catch (Exception E)
         {
         System.out.println("The Error Is In Color Calculating OF IMAGE 2"+E+"Pixel1="+pixel1.length);
         }// end of outer catch.
        
        System.out.println("image 2 sharpening is done");
         

                    
                    /******************sobel operator for the sobel values of the pixels.*******************************/
          try{
            //int width=bimg1.getWidth()-1;
            //int height=bimg1.getHeight()-1;
            //System.out.println("width="+width);
            //System.out.println("Height="+height);
            //System.out.println("pixel1 length"+pixel1.length);
          //while(i !<-1 and j!<pixel1.length )
          int min=1;
          int max=pixel1.length;
       
      
          for (int i1=1;i1<=bimg1.getWidth()-1;i1++){
                    
          for (int j1=1; j1<=bimg1.getHeight()-1;j1++){
            while(min<=max){
 sdx1[i1][j1] = (pixel1[i1-1][j1-1]+ 2*pixel1[i1][j1-1] + pixel1[i1+1][j1-1])-(pixel1[i1-1][j1+1]+(2*pixel1[i1][j1+1])+pixel1[i1+1][j1+1]);
 sdy1[i1][j1]=(pixel1[i1-1][j1-1]-pixel1[i1-1][j1+1]+(2*(pixel1[i1][j1-1]-pixel1[i1][j1+1]))+pixel1[i1+1][j1-1]-pixel1[i1+1][j1+1]);
 //System.out.println("min="+min);
 min++;
 }//end of while
 }//end of inner loop
 }//end of outer loop
 
 }// End of try block to calculate sobel operator.
 catch (Exception E){System.out.println("Error in the sobel operator IMAGE 1="+E+ "sdx1 length="+sdx1.length+ "sds1 length="+sdy1.length);}
                    
           /**************************************************************************************/
                        
                        System.out.println("Sobel for Image 1 Calculated...");
                        
                try
                    {
                    int min=1;
                    int max=pixel2.length;
       
                    for (int i1=1;i1<=bimg2.getWidth()-1;i1++)
                         {
                        for (int j1=1; j1<=bimg2.getHeight()-1;j1++)
                            {
                        while(min<=max){
                            sdx2[i1][j1] = (pixel2[i1-1][j1-1]+ 2*pixel2[i1][j1-1] + pixel2[i1+1][j1-1])-(pixel2[i1-1][j1+1]+(2*pixel2[i1][j1+1])+pixel2[i1+1][j1+1]);
                            sdy2[i1][j1]=(pixel2[i1-1][j1-1]-pixel2[i1-1][j1+1]+(2*(pixel2[i1][j1-1]-pixel2[i1][j1+1]))+pixel2[i1+1][j1-1]-pixel2[i1+1][j1+1]);
                        //System.out.println("min="+min);                           
                        min++;
                        }//end of while                         
                        }//end of inner loop
                        }//end of outer loop
                    }// End of try block to calculate sobel operator.
                    catch (Exception E)
                    {
                    System.out.println("Error in the sobel operator IMAGE 2"+E);
                        
                    }//end of catch
                    System.out.println("Sobel for Image 2 Calculated...");
        /************************************************************/
        /** this is claculate values of the vx and vy****/
            try
            {
              System.out.println("THE VX AND VY FOR THE IMAGE 1");
                int i2,j2;
                int n1= bimg1.getWidth();
                int m1=bimg1.getHeight();
                    i2=1;
                    j2=1;            
                 //for (i2=1 ; i2<n1-1 ; i2++)
                    //{
                    //for ( j2=1 ; j2<m1 ; j2++)
                        //{
                            vx1=vetox1(i2,j2);
                            vy1=vetoy1(i2,j2);
                        //  for(int b=1;b<vx1.length;b++){
                        //      for(int g=1;g<vx1[b].length;g++){
                        //          System.out.println("vx1["+b+"]["+g+"]="+vx1[b][g]);
                        //  }//end of inner loop
                        //  }//end of main loop
                            
                        //}//end of inner loop
                        
                        //System.out.println("vx1 and vy1 are :" +vx1 +" "+vy1);
                    //}//end of outer loop
            }//end of try
            catch(Exception E)
            {
                System.out.println("The Erore is in the try vX AND vy block OF IMAGE 1:"+E);
            }
            
            
            System.out.println("VX and VY of image 1 calculated...");
           /*******************************************************/
           try
            {
              System.out.println("THE VX AND VY FOR THE IMAGE 2");
               int i2,j2=0;
                
                int n1= bimg2.getWidth();
                int m1=bimg2.getHeight();
                   i2=1;
                   j2=1;  
                 //for (i2=1 ; i2<n1-1 ; i2++)
                    //{
                    //for ( j2=1 ; j2<m1 ; j2++)
                        //{
                            vx2=vetox2(i2,j2);
                            vy2=vetoy2(i2,j2);
                        //}
                    //  System.out.println("Vx2 and Vy2 are : "+vx2+ " "+vy2);
                    //}
            }//end of try
            catch(Exception E)
            {
                System.out.println("The Erore is in the try vX AND vy block :"+E);
            }
            System.out.println("VX and VY for image 2 are calculated");
            match();
        
        
         }/// End of the Button Action.
        
        else
          System.exit(0);
    
    }; /// END OF ACTION PERFORMED.
    /****************************************/
    /*******************************************/
     
       
       public double[][] vetox1(int i, int j) /// vx method.
       {
        double temprary[][]=new double [1000][1000];
       //   try
        //{
            for (int k=i-1;k<=i+1;k++)// for (int k=i-1;k<=i+1;k++)
            {
                for (int l=j; l<=j+1;l++)//for (int l=j-1; l<=j+1;l++)
                {
                temprary[k][l] = temprary[k][l]+(sdx1[k][l]*sdy1[k][l]*2);
                }
            }
            return temprary;
        //}
        /*catch (Exception E)
            {
                System.out.println("\nThe Error is in the vetox function:  "+E );
            }*/
        
        
       }// END of CALUTATION OF VETA X.
       
       
       /**********************************************/
       public double[][] vetoy1(int i,int j)//// vy method.
       {
        double temperary[][]=new double[1000][1000];
       /*   try
        {*/
            for (int u = i-1; u<= i+1 ; u++)
            {
            for (int v= j-1; v<= j+1 ; v++)
                {

                temperary[u][v] = temperary[u][v]+((sdx1[u][v] * sdx1[u][v]) *(sdy1[u][v] * sdy1[u][v]));
               }
           }

         return temperary;
        /*}// try end.
        catch(Exception E){System.out.println("\n The Error is in vetoy Block: "+E);}*/
        
       }// end of the main vetoy calculation.
       
       /****************************************************************************************/
       
       
       public double[][] vetox2(int i, int j) /// vx method.
       {
        double temprary[][]=new double [1000][100];
       //   try
        //{
            for (int k=i-1;k<=i+1;k++)// for (int k=i-1;k<=i+1;k++)
            {
                for (int l=j; l<=j+1;l++)//for (int l=j-1; l<=j+1;l++)
                {
                temprary[k][l] = temprary[k][l]+(sdx2[k][l]*sdy2[k][l]*2);
                }
            }
            return temprary;
        //}
        /*catch (Exception E)
            {
                System.out.println("\nThe Error is in the vetox function:  "+E );
            }*/
        
        
       }// END of CALUTATION OF VETA X.
       
       
       /**********************************************/
       public double[][] vetoy2(int i,int j)//// vy method.
       {
        double temperary[][]=new double[1000][1000];
       /*   try
        {*/
            for (int u = i-1; u<= i+1 ; u++)
            {
            for (int v= j-1; v<= j+1 ; v++)
                {

                temperary[u][v] = temperary[u][v]+((sdx2[u][v] * sdx2[u][v]) *(sdy2[u][v] * sdy2[u][v]));
               }
           }

         return temperary;
        /*}// try end.
        catch(Exception E){System.out.println("\n The Error is in vetoy Block: "+E);}*/
        
       }// end of the main vetoy calculation.
       /*****************************************************************/
        /*********************************************************************/      
        
            
            
            
            
        public void thitaCOMPUTE1()  /// compute thita Value for gaborfilter equation.
            {
             try{
                int min=1;
                int max_vx=vx1.length;
                int max_vy=vy1.length;
                System.out.println("VX1 length="+max_vx);
                System.out.println("Vy1 length="+max_vy);
                
                System.out.println("image 1 width="+(bimg1.getWidth()-1));
                System.out.println("image 1 width="+(bimg1.getHeight()-1));
                
                
                
                
                for (int i=1 ; i < bimg1.getWidth()-1 ; i++)
                    {//outr loop starts
                 for (int j=1 ; j< bimg1.getHeight()-1 ; j++)
                    {//inner loop starts
                    while(min <max_vx || min<max_vy){
                    
                      if ((vx1[i][j]==0)&&(vy1[i][j])==0)
                        {// if starts
                        thita1[i][j]=Math.PI/2;
                        
                         }//if end   
                      else  if (vx1[i][j]==0)
                        {//else if starts
                        thita1[i][j]=0.5 * Math.atan(Math.signum(vy1[i][j])*2000000000);
                        
                         }//else if end
                    else if (vx1[i][j]!=0) 
                        {//else if starts
                       thita1[i][j] = 0.5 * Math.atan(vy1[i][j]/vx1[i][j]);
                        }//else if ends
                        min++;
                    }//end while
                 }//inner loop ends
                    }//outer loop ends

        }// try End.
         catch(Exception E){System.out.println("\n The error is in the block thita compute 1   "+E);}
        

    }// end of the main thitacompute.
    
    public void thitaCOMPUTE2()  /// compute thita Value for gaborfilter equation.
            {
             try{
                int min=1;
                int max_vx=vx2.length;
                int max_vy=vy2.length;
                System.out.println("VX2 length="+max_vx);
                System.out.println("Vy2 length="+max_vy);
                
                System.out.println("image 1 width="+(bimg1.getWidth()-1));
                System.out.println("image 1 width="+(bimg1.getHeight()-1));
                
                for (int i=1 ; i < bimg2.getWidth()-1 ; i++)
                    {
                 for (int j=1 ; j< bimg2.getHeight()-1 ; j++)
                    {
                    while(min <max_vx || min<max_vy){
                      if ((vx2[i][j]==0)&&(vy2[i][j])==0)
                        {
                        thita2[i][j]=Math.PI/2;
                         }   
                      else  if (vx2[i][j]==0)
                        {
                        thita2[i][j]=0.5 * Math.atan(Math.signum(vy2[i][j])*2000000000);
                         }
                    else if (vx2[i][j]!=0) 
                        {
                       thita2[i][j] = 0.5 * Math.atan(vy2[i][j]/vx2[i][j]);
                         }
                    min++;
                    }//end of while
                 }
                    }

        }// try End.
         catch(Exception E){System.out.println("\n The error is in the block thita compute 2   "+E);}
        

    }// end of the main thitacompute.
    
    
    /***********************************************************************************/
    /**********************************************************************************/
    public void GABORFILTER1()  // calcuate gabor equation values.
        {
        try 
        {
                int min=1;
                int max=thita1.length;
            for (int i=1 ; i< bimg1.getWidth()-1 ; i++)
            {
            for (int j=1 ; j< bimg1.getHeight()-1 ; j++)
                {
                    
                while(min <max){
                
                 gaborfilter1[i][j] = Math.exp((-(i*Math.cos(thita1[i][j])+j*Math.sin(thita1[i][j])))/(2*dx*dx));
                 
                 gaborfilter1[i][j] = gaborfilter1[i][j]* Math.exp((-(-i*Math.sin(thita1[i][j])+j*Math.cos(thita1[i][j])))/(2*dy*dy));
                
                gaborfilter1[i][j] = gaborfilter1[i][j]* Function((i*Math.cos(thita1[i][j])+j*Math.sin(thita1[i][j])),t1,t2);
                min++;
                }//end of while
            }
        }
        } // end of try block for gabor.
        catch(Exception E)
        {
            System.out.println("The Error is in the GABORFILTER BLOCK 1 :"+E);
        }
    }//end of method
    
    public void GABORFILTER2()  // calcuate gabor equation values.
        {
        try 
        {
            int min=1;
                int max=thita2.length;
                
            for (int i=1 ; i< bimg1.getWidth()-1 ; i++)
            {
            for (int j=1 ; j< bimg1.getHeight()-1 ; j++)
                {
                while(min<max){
                
                 gaborfilter2[i][j] = Math.exp((-(i*Math.cos(thita2[i][j])+j*Math.sin(thita2[i][j])))/(2*dx*dx));
                 
                 gaborfilter2[i][j] = gaborfilter2[i][j]* Math.exp((-(-i*Math.sin(thita2[i][j])+j*Math.cos(thita2[i][j])))/(2*dy*dy));
                
                gaborfilter2[i][j] = gaborfilter2[i][j]* Function((i*Math.cos(thita2[i][j])+j*Math.sin(thita2[i][j])),t1,t2);
                min++;
                }//end of while
            }
        }
        } // end of try block for gabor.
        catch(Exception E)
        {
            System.out.println("The Error is in the GABORFILTER BLOCK 2 :"+E);
        }
    }
    /*************************************************************/
    /**********************************************************/
    private double Function (double xFi, double t1, double t2) // 
        {
            try
            { 
                
            }catch(Exception E)
            {
                System.out.println("The Error is in the function calculation   :"+E);
            }
            return function2(xFi-(Math.ceil(xFi/(t1/2+t2/2))*(t1/2+t2/2)));
        }
        
        
/******************************************/
    private double function2(double x)
        {
         double sum=0;
         try
         {
            if ((x >= 0)&&(x <= (t1/4)))
            {
             sum = Math.cos(( 2 * Math.PI * x)/t1);
            }
            else if ((x > t1/4)&&(x < (t1/4+t2/2)))
                {
                   sum = - Math.cos(2 * Math.PI * (x - t1/4 -t2/2)/t2);
                }
                else if ((x >= (t1/4 + t2/2))&&(x <= (t1/2 + t2/2)))
                    {
                    sum = Math.cos(2 * Math.PI * (x - t1/4 -t2/2)/t2);
                    }
                return sum;
          }
          catch(Exception E)
          {
            System.out.println("The Error Is in the Function2 block :"+E);
          }
          return sum;
        }
    
    
    
    public void match ()
    {
        thitaCOMPUTE1();
        System.out.println("Thita For image 1 computed successfully");
        
        thitaCOMPUTE2();
        System.out.println("Thita For image 2 computed successfully");
        
        GABORFILTER1();
        System.out.println("GaborFilter for 1 is calculated");
        
        GABORFILTER2();
        System.out.println("GaborFilter for 2 is calculated");
        
        try{//try starts
        
        int k=0,l=0;
        int min=1;
                int max=gaborfilter2.length;
                //int max_vy=vy2.length;
        
        for (int i=1 ; i< bimg1.getWidth()-1 ; i++)
            {//outer loop starts
            for (int j=1 ; j< bimg1.getHeight()-1 ; j++)
                {//inner loop starts
                while(min<max){
                
                    if (gaborfilter1[i][j] == gaborfilter2[i][j]){
                    
                        k++;}//end of outer if
                    if (gaborfilter1[i][j] != gaborfilter2[i][j]){
                    
                        l++;}//end of 2nd if
                        min++;
                }//end of while
                }//end of inner loop
            }//end of outer loop
            System.out.println("The K is "+k);
            System.out.println("\n The value of L is :"+l);
    
    }//end of try
    catch(Exception e){
        System.out.println("Error in main function="+e);
        }//end of cathc
    
    }//end of match
    
    
    
    /*****************************************************************/
    /**********************************************************/
    
    public static void main(String[] args)
        {
           SwingUtilities.invokeLater(new Runnable()
              {
                     public void run()
                      {
                           try
                          {
                           MAINFRAME app = new MAINFRAME();
                               //app.match();
                               app.setDefaultCloseOperation(EXIT_ON_CLOSE);
                               System.gc();
                               
                            }
                           catch (Exception exception)
                           {
                               exception.printStackTrace();
                           }
                      }
              });
             
              
    }
    
}// END OF MAIN. 
