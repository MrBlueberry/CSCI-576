import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class image {

	public static void main(String[] args) {
		int dark_pix = 0xff000000 | ((0 & 0xff) << 16) | ((0 & 0xff) << 8)
				| (0 & 0xff);
		int width = 512, height = 512;
		int n = 8;
		double s = 2.0;
		double pi = Math.PI;
		double increasement = 2 * pi / n;
		boolean anchor;
		int dir_x, dir_y;
		double slope = 0;
		int start_x = 255, start_y = 255;
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				byte r = (byte) 255;
				byte g = (byte) 255;
				byte b = (byte) 255;
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8)
						| (b & 0xff);
				img.setRGB(x, y, pix);
			}
		}
		img.setRGB(start_x, start_y, dark_pix);
		for (int i = 0; i < n; i++) {
			double angle = i * increasement;
			if ((angle >= 0 && angle <= pi / 4)
					|| (angle >= pi * 3 / 4 && angle <= pi * 5 / 4)
					|| (angle >= pi * 7 / 4 && angle <= 2 * pi)) {
				anchor = true;
				slope = Math.tan(angle);
			} else {
				anchor = false;
				slope = Math.cos(angle) / Math.sin(angle);

			}
			if (angle > pi / 2 && angle < pi * 3 / 2)
				dir_x = -1;
			else
				dir_x = 1;
			if (angle > 0 && angle < pi)
				dir_y = 1;
			else
				dir_y = -1;
			if (anchor) {
				double y = start_y + 0.5;
				for (int x = start_x + dir_x; x > 0 && y + slope * dir_x > 0
						&& x + dir_x < 512 && y + slope * dir_x < 512; x += dir_x) {
					y = y + slope * dir_x;
					img.setRGB(x, (int) Math.floor(y), dark_pix);
				}
			} else {
				double x = start_x + 0.5;
				for (int y = start_y + dir_y; x + slope * dir_y > 0 && y > 0
						&& x + slope * dir_y < 512 && y + dir_y < 512; y += dir_y) {
					x = x + slope * dir_y;
					img.setRGB((int) Math.floor(x), y, dark_pix);
				}
			}
		}
		
		try {
			BufferedImage bi = img;
			File f = new File("MyLine.png");
			ImageIO.write(bi, "PNG", f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int newWidth = (int)Math.floor(width/s);
		int newHeight = newWidth;
		BufferedImage img1 = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < newWidth; i++)
			for(int j = 0; j < newHeight; j++)
			{
				int x = (int)Math.floor(i*s);
				int y = (int)Math.floor(j*s);
				img1.setRGB(i, j, img.getRGB(x, y));
			}
		JPanel panel = new JPanel();
		panel.add(new JLabel(new ImageIcon(img)));
		panel.add(new JLabel(new ImageIcon(img1)));
		JFrame frame = new JFrame("Display images");
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}