package service.notify;

import DAO.PlayerDAO;
import bean.Player;
import bean.Userinfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.NumberFormat;

public class ReadColor {


     /**
     * 读取一张图片的RGB值
     *
     * @throws Exception
     */

    public String Read(String image) throws Exception {

        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }


        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        int count = 0; //计数器

        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                //定义一个百分比 超过这个百分比表示击杀有效
                double percent = 3D / 10D;    //加D表明为double类型
                NumberFormat nt = NumberFormat.getPercentInstance();//格式化对象
                nt.setMinimumFractionDigits(2);//设置百分比精确度为2
//                double percent = Double.parseDouble(nt.format(p));//格式化赋值

                int pixel = bi.getRGB(i, j);  // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);

                //如果一个像素点在这个范围的话，就将计数器加1
                if (rgb[0] > rgb[1] && rgb[1] > rgb[2] && rgb[0] == 255 && rgb[1] < 90 && rgb[2] < 70) {  //红色
                    count++;
                    if ((double) count / (height * width) >= percent) {
                        String Clothes = "red";
                        return Clothes;
//                        p.setClothes(Clothes);
//                        pd.save(p);
                    }


                } else if (rgb[1] > rgb[0] && rgb[1] > rgb[2] && rgb[0] < 200 && rgb[2] < 150) {//绿色
                    count++;

                    if ((double) count / (height * width) >= percent) {
                        String Clothes = "green";
                        return Clothes;
                    }

                } else if (rgb[2] > rgb[1] && rgb[1] > rgb[0] && rgb[2] == 255 && rgb[0] < 80 && rgb[1] < 100) {//深蓝色
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "blue-black";
                        return Clothes;
                    }

                } else if (rgb[2] > rgb[0] && rgb[0] < 200 && rgb[2] < 240 && rgb[2] > 160 && rgb[1] == 255) {//天蓝色
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "sky-blue";
                        return Clothes;
                    }

                } else if (rgb[0] == rgb[1] == rgb[2] > 245) {//白色
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "white";
                        return Clothes;
                    }

                } else if (rgb[1] == rgb[0] == rgb[2] < 60) {//黑色
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "black";
                        return Clothes;
                    }

                } else if (rgb[1] > rgb[2] && rgb[1] > 240 && rgb[1] < 250 && rgb[2] < 100 && rgb[0] == 255) {//黄色
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "yellow";
                        return Clothes;
                    }

                } else if (rgb[1] < rgb[2] && rgb[1] < 100 && rgb[2] < 250 && rgb[2] > 260 && rgb[0] == 255) {//紫色
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "purple";
                        return Clothes;

                    }
                } else if (rgb[2] > rgb[1] && rgb[1] > 170 && rgb[1] < 200 && rgb[2] < 250 && rgb[2] > 240 && rgb[0] == 255) {//粉色
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "pink";
                        return Clothes;
                    }
                } else if (rgb[1] > 100 && rgb[1] < 180 && rgb[2] < 120 && rgb[2] > 100 && rgb[0] == 255) {
                    count++;
                    if ((float) count / (height * width) >= percent) {
                        String Clothes = "brown";
                        return Clothes;
                    }
                }

//
//                System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + ","
//                        + rgb[1] + "," + rgb[2] + ")");
            }
        }
        return image;
    }




//        public static void main(String[] args) throws Exception {
//            Userinfo u=new Userinfo();
//            ReadColor rc = new ReadColor();
//            rc.Read("/home/zbr/图片/1.jpg");
//            System.out.println(u.getClothes());
//        }
}
