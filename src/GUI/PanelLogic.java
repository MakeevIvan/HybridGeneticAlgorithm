package GUI;

import сommon.Customer;
import main.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.List;
import java.util.Random;

public class PanelLogic extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stroke line = new BasicStroke(2.0F);
	private Color[] colors = new Color[] { Color.orange, Color.green,
			Color.blue, Color.red, Color.magenta, Color.yellow, Color.pink };
	
	
	private class P2D {
		public int x;
		public int y;

		public P2D(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public BufferedImage makeImage() {
		BufferedImage img;
		Dimension d;

		d = this.getPreferredSize();
		img = new BufferedImage(d.width, d.height, BufferedImage.TYPE_3BYTE_BGR);
		this.paint(img.getGraphics());
		return img;
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MainPanel.panelDefWidth, MainPanel.panelDefHeight);
		((Graphics2D) g).setStroke(line);

		if (MainPanel.signal > 0) {
			int x, y;
			/*
			 * double[][] testData = { { 10.00, 20.00, 0.00}, // depot { 100.00,
			 * 100.00, 10.00}, // 1 { 50.00, 70.00, 30.00}, // 2 { 80.00, 25.00,
			 * 10.00}, // 3 { 20.00, 80.00, 10.00}, // 4 { 15.00, 69.00, 10.00},
			 * // 5 };
			 * 
			 * 
			 * Problem.InitCustomers(testData); Population popul = new
			 * Population(); popul.initialize(); Chromosome chromosome =
			 * popul.get(0);
			 * 
			 * MainPanel.stat.setText("Случайная задаа" + chromosome );
			 */

			// double a = Problem.getCustomer(4).getXid(chromosome.getGene(4));

			// MainPanel.stat.setText("Случайная задача SDVRP сгенерирована" +
			// chromosome.getGene(4)+"cvbn"+chromosome +"sdfg"+ a);

			// Customer gh = chromosome.getCustToChrom(3);
			// MainPanel.stat.setText(chromosome +
			// "Случайная задача SDVRP сгенерирована" +
			// (chromosome.getSize()-1));

			for (int i = 0; i < Problem.customersNumber; ++i) {

				Customer customer = Problem.getCustomer(i);
				double maxX = Problem.getCustomer(i).findMaxPointX();
				double maxY = Problem.getCustomer(i).findMaxPointY();

				Double min = Math.min(maxX, maxY);
				double scale = Math.min(MainPanel.panelDefHeight,
						MainPanel.panelDefWidth) / min * 0.9;

				x = (int) (Problem.getCustomer(i).getX() * scale + 5);
				y = (int) (Problem.getCustomer(i).getY() * scale + 5);

				if (Problem.getCustomer(i).getId() == 0) {
					g.setColor(Color.black);
					g.fillOval((x - 4), (y - 4), 10, 10);
					g.setColor(Color.red);
					g.fillOval((x - 3), (y - 3), 9, 9);
				} else {

					g.setColor(Color.black);
					g.fillOval((x - 4), (y - 4), 10, 10);
					g.setColor(Color.blue);
					g.fillOval((x - 3), (y - 3), 7, 7);
					// g.drawString("[]", x - 40, y + 25);
					g.setColor(Color.black);
					g.drawString(Integer.toString(customer.getId()), x + 6,
							y + 5);
				}
			}

			if (MainPanel.signal == 2) {
				List<Customer> bb = MainPanel.chromosome.chTocust();
				
				
				
				Random random = new Random();
				for (int i = 0; i < bb.size(); ++i) {
					double maxX = bb.get(i).findMaxPointX();
					double maxY = bb.get(i).findMaxPointY();
					
					
					
					Double min = Math.min(maxX, maxY);
					double scale = Math.min(MainPanel.panelDefHeight,
							MainPanel.panelDefWidth) / min * 0.9;

					x = (int) (bb.get(i).getX() * scale + 5);
					y = (int) (bb.get(i).getY() * scale + 5);
					
					if (i < bb.size() - 1) {
						if (bb.get(i).getId() == 0) {
							g.setColor(colors[random.nextInt(7) % colors.length]);
							
						}
						P2D startPoint = new P2D((int) Math.round(bb.get(i)
								.getX() * scale + 5), (int) Math.round(bb
								.get(i).getY() * scale + 5));
						P2D endPoint = new P2D((int) Math.round(bb.get(i + 1)
								.getX() * scale + 5), (int) Math.round(bb.get(
								i + 1).getY()
								* scale + 5));
						g.drawLine(startPoint.x, startPoint.y, endPoint.x,
								endPoint.y);
					}
				}
			}
		}
	}
}
