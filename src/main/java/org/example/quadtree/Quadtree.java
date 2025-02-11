package org.example.quadtree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.*;

public class Quadtree implements Initializable {
    @FXML
    public GridPane gridPane;
    public int length;
    public Node root;//current root
    public Node updateRoot;
    public Node MainRoot;
    public int pixelDepth;
    public boolean sw;
    public boolean imageSw;
    public Button newrootbutton=new Button();
    public Button mainrootbutton=new Button();
    public Button findDepthbutton=new Button();
    public Button pixelDepthbutton=new Button();
    public Button maskbutton=new Button();
    public Button subspacebutton=new Button();
    public Button compressbutton=new Button();
    public Button rgb=new Button();
    public Button gray1=new Button();
    public Button gray2=new Button();
    public Button gray3=new Button();
    public Button T1=new Button();
    public Button T2=new Button();
    public Label findDepthLabel=new Label();
    public Label pixelDepthLabel=new Label();
    public TextField pixelx;
    public TextField pixely;
    public TextField maskx1;
    public TextField masky1;
    public TextField maskx2;
    public TextField masky2;
    public TextField subspacex1;
    public TextField subspacey1;
    public TextField subspacex2;
    public TextField subspacey2;
    public TextField compressSize;
    public int[][] imageRGB=new int[256][256];
    public int[][] imageGray1=new int[512][512];
    public int[][] imageT1=new int[512][512];
    public int[][] imageT2=new int[512][512];
    public int[][] imageGray2=new int[256][256];
    public int[][] imageGray3=new int[1024][1024];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        findDepthbutton.setOnMouseEntered(e -> findDepthbutton.setStyle("-fx-background-color:  #540e26 ;-fx-background-radius: 10;"));
        findDepthbutton.setOnMouseExited(e -> findDepthbutton.setStyle("-fx-background-color:   #8a203e;-fx-background-radius: 10;"));
        pixelDepthbutton.setOnMouseEntered(e -> pixelDepthbutton.setStyle("-fx-background-color:  #540e26;-fx-background-radius: 10;"));
        pixelDepthbutton.setOnMouseExited(e -> pixelDepthbutton.setStyle("-fx-background-color: #8a203e;-fx-background-radius: 10;"));
        maskbutton.setOnMouseEntered(e -> maskbutton.setStyle("-fx-background-color:  #540e26;-fx-background-radius: 10;"));
        maskbutton.setOnMouseExited(e -> maskbutton.setStyle("-fx-background-color: #8a203e;-fx-background-radius: 10;"));
        subspacebutton.setOnMouseEntered(e -> subspacebutton.setStyle("-fx-background-color:  #540e26;-fx-background-radius: 10;"));
        subspacebutton.setOnMouseExited(e -> subspacebutton.setStyle("-fx-background-color: #8a203e;-fx-background-radius: 10;"));
        compressbutton.setOnMouseEntered(e -> compressbutton.setStyle("-fx-background-color:  #540e26;-fx-background-radius: 10;"));
        compressbutton.setOnMouseExited(e -> compressbutton.setStyle("-fx-background-color:  #8a203e;-fx-background-radius: 10;"));
        newrootbutton.setOnMouseEntered(e -> newrootbutton.setStyle("-fx-background-color:  #04305e ;-fx-background-radius: 10;"));
        newrootbutton.setOnMouseExited(e -> newrootbutton.setStyle("-fx-background-color: #215c8a;-fx-background-radius: 10;"));
        mainrootbutton.setOnMouseEntered(e -> mainrootbutton.setStyle("-fx-background-color:  #04305e ;-fx-background-radius: 10;"));
        mainrootbutton.setOnMouseExited(e -> mainrootbutton.setStyle("-fx-background-color: #215c8a;-fx-background-radius: 10;"));
        rgb.setOnMouseEntered(e -> rgb.setStyle("-fx-background-color:  #4a1349 ;-fx-background-radius: 12;"));
        rgb.setOnMouseExited(e -> rgb.setStyle("-fx-background-color: #701f6f;-fx-background-radius: 12;"));
        gray1.setOnMouseEntered(e -> gray1.setStyle("-fx-background-color:  #262526 ;-fx-background-radius: 12;"));
        gray1.setOnMouseExited(e -> gray1.setStyle("-fx-background-color:  #757a7d;-fx-background-radius: 12;"));
        gray2.setOnMouseEntered(e -> gray2.setStyle("-fx-background-color:  #262526 ;-fx-background-radius: 12;"));
        gray2.setOnMouseExited(e -> gray2.setStyle("-fx-background-color:   #a1a4a6;-fx-background-radius: 12;"));
        gray3.setOnMouseEntered(e -> gray3.setStyle("-fx-background-color:  #262526 ;-fx-background-radius: 12;"));
        gray3.setOnMouseExited(e -> gray3.setStyle("-fx-background-color:   #5a5f63;-fx-background-radius: 12;"));
        T1.setOnMouseEntered(e -> T1.setStyle("-fx-background-color:  #262526 ;-fx-background-radius: 12;"));
        T1.setOnMouseExited(e -> T1.setStyle("-fx-background-color:   #a1a4a6;-fx-background-radius: 12;"));
        T2.setOnMouseEntered(e -> T2.setStyle("-fx-background-color:  #262526 ;-fx-background-radius: 12;"));
        T2.setOnMouseExited(e -> T2.setStyle("-fx-background-color:   #5a5f63;-fx-background-radius: 12;"));
        findDepthbutton.setOnAction(e->{
            if(imageSw)treeDepth();
        });
    }
    public void RGBPhoto(ActionEvent event){
        String csvFile = "C:\\Users\\sesin\\OneDrive\\Desktop\\java\\Quadtree\\src\\main\\resources\\image4_RGB.csv";
        String line = "";
        String[] data=new String[65536];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            line=br.readLine();
            data= line.split(",");
        } catch (IOException e) { e.printStackTrace(); }
        int i=0;
        for(int j=0;j<256;j++) {
            for (int k = 0; k < 256; k++) {
                String x = data[3 * i].substring(1);
                String y = data[3 * i + 1];
                String z = data[3 * i + 2].substring(0, data[3 * i + 2].length() - 1);
                if (y.length() == 1) y = STR."00\{y}";
                else if (y.length() == 2) y = STR."0\{y}";
                if (z.length() == 1) z = STR."00\{z}";
                else if (z.length() == 2) z = STR."0\{z}";
                imageRGB[j][k] = Integer.parseInt(x + y + z);
                i++;
            }
        }
        length= imageRGB.length;
        root=new Node(imageRGB,1);
        creat(root,root);
        MainRoot=root;
        updateRoot=null;
        sw=false;
        imageSw=true;
        display(root);
    }
    public void Gray1Photo(ActionEvent event){
        String csvFile = "C:\\Users\\sesin\\OneDrive\\Desktop\\java\\Quadtree\\src\\main\\resources\\image1_gray.csv";
        String line = "";
        String[] data=new String[262144];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            line=br.readLine();
            data= line.split(",");
        } catch (IOException e) { e.printStackTrace(); }
        int i=0;
        for(int j=0;j<512;j++){
            for (int k=0;k<512;k++) {
                String x = data[i];
                if (x.length() == 1) x = STR."00\{x}";
                else if (x.length() == 2) x = STR."0\{x}";
                imageGray1[j][k] = Integer.parseInt(x + x + x);
                i++;
            }
        }
        length= imageGray1.length;
        root=new Node(imageGray1,1);
        creat(root,root);
        MainRoot=root;
        imageSw=true;
        updateRoot=null;
        sw=false;
        display(root);
    }
    public void Gray2Photo(ActionEvent event){
        String csvFile = "C:\\Users\\sesin\\OneDrive\\Desktop\\java\\Quadtree\\src\\main\\resources\\image2_gray.csv";
        String line = "";
        String[] data=new String[65536];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            line=br.readLine();
            data= line.split(",");
        } catch (IOException e) { e.printStackTrace(); }
        int i=0;
        for(int j=0;j<256;j++){
            for (int k=0;k<256;k++) {
                String x = data[i];
                if (x.length() == 1) x = STR."00\{x}";
                else if (x.length() == 2) x = STR."0\{x}";
                imageGray2[j][k] = Integer.parseInt(x + x + x);
                i++;
            }
        }
        length= imageGray2.length;
        root=new Node(imageGray2,1);
        creat(root,root);
        MainRoot=root;
        imageSw=true;
        updateRoot=null;
        sw=false;
        display(root);
    }
    public void Gray3Photo(ActionEvent event){
        String csvFile = "C:\\Users\\sesin\\OneDrive\\Desktop\\java\\Quadtree\\src\\main\\resources\\image3_gray.csv";
        String line = "";
        String[] data=new String[1048576];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            line=br.readLine();
            data= line.split(",");
        } catch (IOException e) { e.printStackTrace(); }
        int i=0;
        for(int j=0;j<1024;j++){
            for (int k=0;k<1024;k++) {
                String x = data[i];
                if (x.length() == 1) x = STR."00\{x}";
                else if (x.length() == 2) x = STR."0\{x}";
                imageGray3[j][k] = Integer.parseInt(x + x + x);
                i++;
            }
        }
        length= imageGray3.length;
        root=new Node(imageGray3,1);
        creat(root,root);
        MainRoot=root;
        imageSw=true;
        updateRoot=null;
        sw=false;
        display(root);
    }
    public void T1Photo(ActionEvent event){
        String csvFile = "C:\\Users\\sesin\\OneDrive\\Desktop\\java\\Quadtree\\src\\main\\resources\\T1.csv";
        String line = "";
        String[] data=new String[262144];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            line=br.readLine();
            data= line.split(",");
        } catch (IOException e) { e.printStackTrace(); }
        int i=0;
        for(int j=0;j<512;j++){
            for (int k=0;k<512;k++) {
                String x = data[i];
                if (x.length() == 1) x = STR."00\{x}";
                else if (x.length() == 2) x = STR."0\{x}";
                imageT1[j][k] = Integer.parseInt(x + x + x);
                i++;
            }
        }
        length= imageT1.length;
        root=new Node(imageT1,1);
        creat(root,root);
        MainRoot=root;
        imageSw=true;
        updateRoot=null;
        sw=false;
        display(root);
    }
    public void T2Photo(ActionEvent event){
        String csvFile = "C:\\Users\\sesin\\OneDrive\\Desktop\\java\\Quadtree\\src\\main\\resources\\T2.csv";
        String line = "";
        String[] data=new String[262144];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            line=br.readLine();
            data= line.split(",");
        } catch (IOException e) { e.printStackTrace(); }
        int i=0;
        for(int j=0;j<512;j++){
            for (int k=0;k<512;k++) {
                String x = data[i];
                if (x.length() == 1) x = STR."00\{x}";
                else if (x.length() == 2) x = STR."0\{x}";
                imageT2[j][k] = Integer.parseInt(x + x + x);
                i++;
            }
        }
        length= imageT2.length;
        root=new Node(imageT2,1);
        creat(root,root);
        MainRoot=root;
        imageSw=true;
        updateRoot=null;
        sw=false;
        display(root);
    }
    public void creat(Node node,Node root){
        if(checkCreatQuad(node)){
            creatQuadTree(node);
            creat(node.topLeft,root);
            creat(node.topRight,root);
            creat(node.bottomLeft,root);
            creat(node.bottomRight,root);
        }
        else{
            node.color=node.image[0][0];
            node.leaf=true;
        }
        if(node.level>root.treeDepth)root.treeDepth=node.level;
    }
    public boolean checkCreatQuad(Node node){
        int color=-1;
        for(int i=0;i<node.length;i++){
            for(int j=0;j<node.length;j++){
                if(color==-1)color=node.image[i][j];
                else if(color!=node.image[i][j])return true;
            }
        }
        return false;
    }
    public void creatQuadTree(Node node){
        int[][] image1=new int[node.length/2][node.length/2];
        int[][] image2=new int[node.length/2][node.length/2];
        int[][] image3=new int[node.length/2][node.length/2];
        int[][] image4=new int[node.length/2][node.length/2];
        for (int i = 0; i < node.length / 2; i++) {
            for (int j = 0; j < node.length / 2; j++) {
                image1[i][j]=node.image[i][j];
            }
        }
        for (int i =0,t=0; i < node.length/2 ; i++,t++) {
            for (int j = node.length/2,s=0; j < node.length; j++,s++) {
                image2[t][s]=node.image[i][j];
            }
        }
        for (int i = node.length/2,t=0; i < node.length ; i++,t++) {
            for (int j = 0,s=0; j < node.length / 2; j++,s++) {
                image3[t][s]=node.image[i][j];
            }
        }
        for (int i = node.length/2,t=0; i < node.length ; i++,t++) {
            for (int j = node.length/2,s=0; j < node.length; j++,s++) {
                image4[t][s]=node.image[i][j];
            }
        }
        node.topLeft=new Node(image1,node.level+1);
        node.topRight=new Node(image2,node.level+1);
        node.bottomLeft=new Node(image3,node.level+1);
        node.bottomRight=new Node(image4,node.level+1);
    }
    public void display(Node node){
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.setPrefSize(800,800);
         for (int i = 0; i < node.length; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(800.0/node.length);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < node.length; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(800.0/node.length);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        for (int row = 0; row < node.length; row++) {
            for (int col = 0; col < node.length; col++) {
                StackPane cell = new StackPane();
                String x= String.valueOf(node.image[row][col]);
                if(x.length()==8)x= STR."0\{x}";
                else if(x.length()==7)x= STR."00\{x}";
                else if(x.length()==6)x= STR."000\{x}";
                else if(x.length()==5)x= STR."0000\{x}";
                else if(x.length()==4)x= STR."00000\{x}";
                else if(x.length()==3)x= STR."000000\{x}";
                else if(x.length()==2)x= STR."0000000\{x}";
                else if(x.length()==1)x= STR."00000000\{x}";
                int r= Integer.parseInt(x.substring(0,3));
                int g= Integer.parseInt(x.substring(3,6));
                int b= Integer.parseInt(x.substring(6,9));
                String s=String.format("%02X%02X%02X",r,g,b);
                cell.setStyle("-fx-background-color: #"+s+";");
                gridPane.add(cell, col, row);
            }
        }
    }
    public int treeDepth(){
        findDepthLabel.setText(String.valueOf(root.treeDepth));
        findDepthLabel.setVisible(false);
        findDepthLabel.setVisible(true);
        return root.treeDepth;
    }
    public int pixelDepth(int px,int py){
         findDepth(px,py,root);
         pixelDepthLabel.setText(String.valueOf(pixelDepth));
         pixelDepthLabel.setVisible(false);
         pixelDepthLabel.setVisible(true);
         return pixelDepth;
    }
    public void findDepth(int x,int y,Node node){
        if(node.leaf)pixelDepth=node.level;
        else if(x < node.length / 2&&y < node.length / 2)findDepth(x,y,node.topLeft);
        else if(x < node.length/2&&y < node.length&&y>=node.length / 2)findDepth(x,y-node.length / 2,node.topRight);
        else if(x < node.length&&x>=node.length/2&&y<node.length/2)findDepth(x-node.length / 2,y,node.bottomLeft);
        else if(x < node.length&&x>=node.length/2&&y<node.length&&y>=node.length/2)findDepth(x-node.length / 2,y-node.length / 2,node.bottomRight);
    }
    public int[][] compress(int newSize){
        int size=root.length/newSize;
        System.out.println(size);
        int[][] image=new int[newSize][newSize];
        for(int i=0,p=0;i<root.length;i+=size,p++){
            for(int j=0,q=0;j<root.length;j+=size,q++){
                int rSum=0;
                int gSum=0;
                int bSum=0;
                for(int t=i;t<i+size;t++){
                    for(int s=j;s<j+size;s++) {
                        String x = String.valueOf(root.image[t][s]);
                        if (x.length() == 8) x = STR."0\{x}";
                        else if (x.length() == 7) x = STR."00\{x}";
                        int r = Integer.parseInt(x.substring(0, 3));
                        int g = Integer.parseInt(x.substring(3, 6));
                        int b = Integer.parseInt(x.substring(6, 9));
                        rSum += r;
                        gSum += g;
                        bSum += b;
                    }
                }
                String rAverage= String.valueOf(rSum / (size*size));
                String gAverage= String.valueOf(gSum / (size*size));
                String bAverage= String.valueOf(bSum / (size*size));
                if(gAverage.length()==1)gAverage= STR."00\{gAverage}";
                else if(gAverage.length()==2)gAverage= STR."0\{gAverage}";
                if(bAverage.length()==1)bAverage= STR."00\{bAverage}";
                else if(bAverage.length()==2)bAverage= STR."0\{bAverage}";
                image[p][q]=Integer.parseInt(rAverage+gAverage+bAverage);
            }
        }
        Node newRoot=new Node(image,1);
        creat(newRoot,newRoot);
        display(newRoot);
        return image;
    }
    public int[][] compressMainTree(int newSize){
        if(newSize<=root.length) {
            int[][] image = new int[root.length][root.length];
            for (int i = 0; i < root.length; i++) {
                for (int j = 0; j < root.length; j++) {
                    image[i][j] = root.image[i][j];
                }
            }
            Node newRoot = new Node(image, 1);
            creat(newRoot, newRoot);
            compressAction(newRoot, newSize);
            newRoot.length = newSize;
            if(newSize!=1) {
                newRoot.image = new int[newSize][newSize];
                TreeToArray(0, newSize - 1, 0, newSize - 1, newRoot, newRoot);
                Node newRoot1 = new Node(newRoot.image, 1);
                creat(newRoot1, newRoot1);
                updateRoot = newRoot1;
                display(newRoot1);
                return newRoot1.image;
            }
            else{
                updateRoot = newRoot;
                display(newRoot);
                return newRoot.image;
            }
        }
        return null;
    }
    public void arrayCompress(Node node,Node root,int size,int x1,int x2,int y1,int y2){
        if(pow(2,node.level-1)!=size){
            if(node.leaf){
                for(int k=0;k<node.length;k++){
                    for(int t=0;t<node.length;t++){
                        root.image[x1][y1]=node.image[k][t];
                        y1++;
                    }
                    x1++;
                }
            }
            else {
                arrayCompress(node.topLeft, root, size,x1, (x1 + x2) / 2, y1,(y1 + y2)/2);
                arrayCompress(node.topRight, root, size,x1, (x1 + x2) / 2, (y1 + y2) / 2 + 1, y2);
                arrayCompress(node.bottomLeft, root, size,(x1 + x2) / 2 + 1, x2, y1, (y1 + y2) / 2);
                arrayCompress(node.bottomRight, root, size,(x1 + x2) / 2 + 1, x2, (y1 + y2) / 2 + 1, y2);
            }
        }
        else{
            root.image[x1][y1]=node.image[0][0];
        }
    }
    public void TreeToArray(int x1,int x2,int y1,int y2,Node node,Node root){
        if(!node.leaf) {
            TreeToArray(x1, (x1 + x2) / 2, y1,(y1 + y2)/2,node.topLeft,root);
            TreeToArray(x1, (x1 + x2) / 2, (y1 + y2) / 2 + 1, y2, node.topRight,root);
            TreeToArray((x1 + x2) / 2 + 1, x2, y1, (y1 + y2) / 2, node.bottomLeft,root);
            TreeToArray((x1 + x2) / 2 + 1, x2, (y1 + y2) / 2 + 1, y2, node.bottomRight,root);
        }
        else{
             for(int i=x1;i<x2+1;i++){
                    for(int j=y1;j<y2+1;j++){
                        root.image[i][j]=node.image[0][0];
                    }
             }
        }
    }
     public void compressAction(Node node,int size){
         if(pow(2,node.level-1)==size){
             int rSum=0;
             int gSum=0;
             int bSum=0;
             for(int i=0;i<node.length;i++){
                 for(int j=0;j<node.length;j++) {
                     String x = String.valueOf(node.image[i][j]);
                     if(x.length()==8)x= STR."0\{x}";
                     else if(x.length()==7)x= STR."00\{x}";
                     else if(x.length()==6)x= STR."000\{x}";
                     else if(x.length()==5)x= STR."0000\{x}";
                     else if(x.length()==4)x= STR."00000\{x}";
                     else if(x.length()==3)x= STR."000000\{x}";
                     else if(x.length()==2)x= STR."0000000\{x}";
                     else if(x.length()==1)x= STR."00000000\{x}";
                     int r = Integer.parseInt(x.substring(0, 3));
                     int g = Integer.parseInt(x.substring(3, 6));
                     int b = Integer.parseInt(x.substring(6, 9));
                     rSum += r;
                     gSum += g;
                     bSum += b;
                 }
             }
             node.leaf=true;
             node.topLeft=null;
             node.topRight=null;
             node.bottomLeft=null;
             node.bottomRight=null;
             String rAverage= String.valueOf(rSum / (node.length*node.length));
             String gAverage= String.valueOf(gSum / (node.length*node.length));
             String bAverage= String.valueOf(bSum / (node.length*node.length));
             if(gAverage.length()==1)gAverage= STR."00\{gAverage}";
             else if(gAverage.length()==2)gAverage= STR."0\{gAverage}";
             if(bAverage.length()==1)bAverage= STR."00\{bAverage}";
             else if(bAverage.length()==2)bAverage= STR."0\{bAverage}";
             node.length=1;
             node.image[0][0]=Integer.parseInt(rAverage+gAverage+bAverage);
         }
         else if(!node.leaf){
             compressAction(node.topLeft,size);
             compressAction(node.topRight,size);
             compressAction(node.bottomLeft,size);
             compressAction(node.bottomRight,size);
         }
    }
    public void searchSubspacesWithRange(int x1,int y1,int x2,int y2){
        int[][] image=new int[root.length][root.length];
        for(int i=0;i<root.length;i++){
            for(int j=0;j<root.length;j++){
                image[i][j]=root.image[i][j];
            }
        }
        Node newRoot=new Node(image,1);
        creat(newRoot,newRoot);
        for(int i=0;i<root.length;i++){
            for(int j=0;j<root.length;j++){
                if(!(i>=x1&&i<=x2&&j>=y1&&j<=y2)){
                    creatMask(i, j, newRoot);
                    creatLeaf(newRoot, false);
                }
            }
        }
        TreeToArray(0,root.length-1,0,root.length-1,newRoot,newRoot);
        Node newRoot1=new Node(newRoot.image,1);
        creat(newRoot1,newRoot1);
        updateRoot=newRoot1;
        display(newRoot1);
    }
    public int[][] Mask(int x1,int y1,int x2,int y2){
        int[][] image=new int[root.length][root.length];
        for(int i=0;i<root.length;i++){
            for(int j=0;j<root.length;j++){
                image[i][j]=root.image[i][j];
            }
        }
        Node newRoot=new Node(image,1);
        creat(newRoot,newRoot);
        for(int i=x1;i<x2+1;i++){
            for(int j=y1;j<y2+1;j++){
                creatMask(i,j,newRoot);
                creatLeaf(newRoot,false);
            }
        }
        TreeToArray(0,root.length-1,0,root.length-1,newRoot,newRoot);
        Node newRoot1=new Node(newRoot.image,1);
        creat(newRoot1,newRoot1);
        updateRoot=newRoot1;
        display(newRoot1);
        return newRoot1.image;
    }
    public void creatMask(int x,int y,Node node){
        if (node.leaf) {
            if (node.length == 1){
                node.image[0][0] = 255255255;
            }
            else {
                node.image[x][y] = 255255255;
            }
        }
        else if(x < node.length / 2&&y < node.length / 2)creatMask(x,y,node.topLeft);
        else if(x < node.length/2&&y < node.length&&y>=node.length / 2)creatMask(x,y-node.length / 2,node.topRight);
        else if(x < node.length&&x>=node.length/2&&y<node.length/2)creatMask(x-node.length / 2,y,node.bottomLeft);
        else if(x < node.length&&x>=node.length/2&&y<node.length&&y>=node.length/2)creatMask(x-node.length / 2,y-node.length / 2,node.bottomRight);
    }
    public void creatLeaf(Node node,boolean sw){
        if(!node.leaf&&!sw){
            creatLeaf(node.topLeft,false);
            creatLeaf(node.topRight,false);
            creatLeaf(node.bottomLeft,false);
            creatLeaf(node.bottomRight,false);
        }
        else if(checkCreatQuad(node)){
            node.leaf=false;
            creatQuadTree(node);
            creatLeaf(node.topLeft,true);
            creatLeaf(node.topRight,true);
            creatLeaf(node.bottomLeft,true);
            creatLeaf(node.bottomRight,true);
        }
        else{
            node.color=node.image[0][0];
            node.leaf=true;
        }
    }
    public void newTree(ActionEvent e) throws IOException {
        if(updateRoot!=null){
            sw=true;
            root=updateRoot;
            display(root);
        }
    }
    public void MainTree(ActionEvent e) throws IOException {
        if(sw){
            root=MainRoot;
            display(root);
        }
    }
    public void Mask(ActionEvent e) throws IOException {
          if(!maskx1.getText().isEmpty()&& !masky1.getText().isEmpty() &&!maskx2.getText().isEmpty()&&!masky2.getText().isEmpty()&&imageSw){
              int x1= Integer.parseInt(maskx1.getText());
              int y1=Integer.parseInt(masky1.getText());
              int x2= Integer.parseInt(maskx2.getText());
              int y2=Integer.parseInt(masky2.getText());
              Mask(x1,y1,x2,y2);
              sw=true;
          }
    }
    public void SubSpace(ActionEvent e) throws IOException {
        if(!subspacex1.getText().isEmpty()&& !subspacey1.getText().isEmpty() &&!subspacex2.getText().isEmpty()&&!subspacey2.getText().isEmpty()&&imageSw){
            int x1= Integer.parseInt(subspacex1.getText());
            int y1=Integer.parseInt(subspacey1.getText());
            int x2= Integer.parseInt(subspacex2.getText());
            int y2=Integer.parseInt(subspacey2.getText());
            searchSubspacesWithRange(x1,y1,x2,y2);
            sw=true;
        }
    }
    public void Compress(ActionEvent e) throws IOException {
        if(!compressSize.getText().isEmpty()&&imageSw){
            int size= Integer.parseInt(compressSize.getText());
            compressMainTree(size);
            sw=true;
        }
    }
    public void Pixel(ActionEvent e) throws IOException {
        if(!pixelx.getText().isEmpty()&&!pixely.getText().isEmpty()&&imageSw){
            int x= Integer.parseInt(pixelx.getText());
            int y=Integer.parseInt(pixely.getText());
            pixelDepth(x,y);
        }
    }
}
