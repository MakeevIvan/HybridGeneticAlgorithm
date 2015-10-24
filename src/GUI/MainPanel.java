package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Collections;
import java.util.List;

import �ommon.Customer;
import main.Chromosome;
import main.HybGenAl;
import main.Population;
import main.Problem;

public class MainPanel extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Chromosome chromosome;
	public static int signal;
	public static int sig = 0;
	public static double opt;
	private JScrollPane scroll = null;
	private PanelLogic panel = null;
	public static JTextField stat = null;
	private JDialog randvrp = null;
	private JDialog runopt = null;
	private JDialog inter = null;
	private JDialog inf1 = null;
	private JDialog inf2 = null;
	private JFileChooser chooser = null;
	public static int zero = 0;
	private int epochs = -1;
	// private Timer timer = null;
	Population population = new Population();

	public static int panelDefHeight = 620;
	public static int panelDefWidth = 620;

	private JFileChooser createChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileHidingEnabled(true);
		fc.setAcceptAllFileFilterUsed(true);
		fc.setMultiSelectionEnabled(false);
		fc.setFileView(null);
		return fc;
	}

	private void genSDVRP(int customersCount) {
		signal = 1;
		sig = 1;
		zero = 0;
		double[][] data = Problem.CreatRand(customersCount);
		Problem.InitCustomers(data);
		this.repaint();
		MainPanel.stat.setText("��������� ������ SDVRP �������������");
	}

	private void loadTask(File file) {
		if (file == null) {
			if (this.chooser == null)
				this.chooser = this.createChooser();
			this.chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			int r = this.chooser.showDialog(this, null);
			if (r != JFileChooser.APPROVE_OPTION)
				return;
			file = this.chooser.getSelectedFile();
		}
		loadSDVRP(file);
	}

	private void loadSDVRP(File file) {
		signal = 1;
		int j = 0;
		File csvFile = file;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		int n = 0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				while (br.readLine() != null) {
					n++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		double[][] data = new double[n][3];
		try {

			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String[] customer = line.split(cvsSplitBy);
				for (int k = 0; k < 3; k++) {
					data[j][k] = Double.parseDouble(customer[k]);
				}
				j++;
			}
			MainPanel.stat.setText("������ SDVRP �������. ��� ���a��: "
					+ file.getName() + ". ���������� ��������: " + (n)/*
																	 * +
																	 * " ����� ��������"
																	 * +opt
																	 */);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			MainPanel.stat.setText("������ ����� �����");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Problem.InitCustomers(data);
		this.repaint();
		sig = 1;
		zero=0;

	}

	public void saveImage(File file) {
		if (file == null) {
			if (this.chooser == null)
				this.chooser = this.createChooser();
			this.chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			int r = this.chooser.showDialog(this, null);
			if (r != JFileChooser.APPROVE_OPTION)
				return;
			file = this.chooser.getSelectedFile();
		}
		try {
			FileOutputStream stream = new FileOutputStream(file);
			ImageIO.write(this.panel.makeImage(), "png", stream);
			stream.close();
		} catch (IOException e) {
			String msg = e.getMessage();
			stat.setText(msg);
			System.err.println(msg);
			JOptionPane.showMessageDialog(this, msg, "������",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void saveResult(File file) {
		if (file == null) {
			if (this.chooser == null)
				this.chooser = this.createChooser();
			this.chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			int r = this.chooser.showDialog(this, null);
			if (r != JFileChooser.APPROVE_OPTION)
				return;
			file = this.chooser.getSelectedFile();
			
		}
		try {
			FileWriter out = new FileWriter(file.getAbsoluteFile());
			out.write("���������� ��������: " + Problem.customersNumber);
			out.write("\r\n����������� ����������: " + HybGenAl.MAX_CAPACITY);
			out.write("\r\n���������� ���������: " + HybGenAl.GENERATION_SPAN);
			out.write("\r\n������ �������: [");
			for (int i = 0; i < population.get(0).getSize() - 1; i++) {
				out.write(population.get(0).getGene(i) + ",");
			}
			out.write("0");
			out.write("]");
			out.write("\r\n���������� ���������: " + (zero - 1));
			out.write("\r\n����� ��������: "
					+ Math.round(population.get(0).getTotalLength()));

			out.close();
		} catch (IOException e) {
			String msg = e.getMessage();
			stat.setText(msg);
			System.err.println(msg);
			JOptionPane.showMessageDialog(this, msg, "������",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private JDialog createRandSDVRP() {
		final JDialog dlg = new JDialog(this,
				"������� ��������� ������ SDVRP...");
		GridBagLayout g = new GridBagLayout();
		GridBagConstraints lc = new GridBagConstraints();
		GridBagConstraints rc = new GridBagConstraints();
		JPanel grid = new JPanel(g);
		JPanel bbar;
		JLabel lbl;
		JButton btn;

		grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lc.fill = rc.fill = GridBagConstraints.BOTH;
		rc.weightx = 1.0;
		lc.weightx = 0.0;
		lc.weighty = 0.0;
		rc.weighty = 0.0;
		lc.ipadx = 10;
		lc.ipady = 10;
		rc.gridwidth = GridBagConstraints.REMAINDER;

		lbl = new JLabel("���������� ��������:");
		g.setConstraints(lbl, lc);
		grid.add(lbl);
		final JSpinner customersCount = new JSpinner(new SpinnerNumberModel(25,
				1, 999999, 1));
		g.setConstraints(customersCount, rc);
		grid.add(customersCount);

		bbar = new JPanel(new GridLayout(1, 2, 5, 5));
		bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));
		btn = new JButton("������");
		bbar.add(btn);

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
				MainPanel.this.genSDVRP((Integer) customersCount.getValue());
			}
		});

		btn = new JButton("�������");
		bbar.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
			}
		});

		dlg.getContentPane().add(grid, BorderLayout.CENTER);
		dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
		dlg.setLocationRelativeTo(this);
		dlg.setLocation(664, 0);
		dlg.pack();
		return dlg;
	}

	public void runVehicles(int epochs, int capas) {
		zero=0;
		signal = 2;
		HybGenAl.GENERATION_SPAN = epochs;
		HybGenAl.MAX_CAPACITY = capas;

		// Population population = new Population();
		population.initialize();
		Collections.sort(population);
		population.selectionFiftyPC();

		for (int i = 0; i < HybGenAl.GENERATION_SPAN; i++) {
			System.out.println(" -------------------------------------" + i
					+ " -------------------------------------");
			population.futureGenerator();
			Collections.sort(population);
			population.routeConstruction();
			Collections.sort(population);
			population.show();

			chromosome = population.get(0);

			this.repaint();
		}

		List<Customer> bb = chromosome.chTocust();
		for (int i = 0; i < bb.size(); i++)
			if (bb.get(i).getId() == 0) {
				zero = zero + 1;
				MainPanel.stat.setText("����� ������� ��������: "
						+ population.get(0).getTotalLength());

			}
	}

	private JDialog createRunOpt() {
		final JDialog dlg = new JDialog(this, "������ �����������");
		GridBagLayout g = new GridBagLayout();
		GridBagConstraints lc = new GridBagConstraints();
		GridBagConstraints rc = new GridBagConstraints();
		JPanel grid = new JPanel(g);
		JPanel bbar;
		JLabel lbl;
		JButton btn;

		grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lc.fill = rc.fill = GridBagConstraints.BOTH;
		rc.weightx = 1.0;
		lc.weightx = 0.0;
		lc.weighty = 0.0;
		rc.weighty = 0.0;
		lc.ipadx = 10;
		lc.ipady = 10;
		rc.gridwidth = GridBagConstraints.REMAINDER;

		lbl = new JLabel("���������� ���������:");
		g.setConstraints(lbl, lc);
		grid.add(lbl);
		final JSpinner epochs = new JSpinner(new SpinnerNumberModel(100, 1,
				999999, 1));
		g.setConstraints(epochs, rc);
		grid.add(epochs);

		lbl = new JLabel("���������������� ����������:");
		g.setConstraints(lbl, lc);
		grid.add(lbl);
		final JSpinner capas = new JSpinner(new SpinnerNumberModel(200, 0,
				999999, 5));
		g.setConstraints(capas, rc);
		grid.add(capas);

		bbar = new JPanel(new GridLayout(1, 2, 5, 5));
		bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));
		btn = new JButton("Ok");
		bbar.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
				MainPanel.this.runVehicles((Integer) epochs.getValue(),
						(Integer) capas.getValue());
			}
		});

		btn = new JButton("�������");
		bbar.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
			}
		});

		dlg.getContentPane().add(grid, BorderLayout.CENTER);
		dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
		dlg.setLocationRelativeTo(this);
		dlg.setLocation(664, 465);
		dlg.pack();
		return dlg;
	}

	private JDialog information1() {
		final JDialog dlg = new JDialog(this, "����������");
		GridBagLayout g = new GridBagLayout();
		GridBagConstraints lc = new GridBagConstraints();
		GridBagConstraints rc = new GridBagConstraints();
		JPanel grid = new JPanel(g);
		JPanel bbar;
		JLabel lbl;
		JButton btn;

		grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lc.fill = rc.fill = GridBagConstraints.BOTH;
		rc.weightx = 1.0;
		lc.weightx = 0.0;
		lc.weighty = 0.0;
		rc.weighty = 0.0;
		lc.ipadx = 10;
		lc.ipady = 10;
		rc.gridwidth = GridBagConstraints.REMAINDER;

		lbl = new JLabel(
				"������ �� ���� ������������ ��� �� ��� ������� �������� �������");
		g.setConstraints(lbl, lc);
		grid.add(lbl);

		bbar = new JPanel(new GridLayout(1, 2, 5, 5));
		bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));

		btn = new JButton("�������");
		bbar.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
			}
		});

		dlg.getContentPane().add(grid, BorderLayout.CENTER);
		dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
		dlg.setLocationRelativeTo(this);
		dlg.setLocation(664, 465);
		dlg.pack();
		return dlg;
	}

	private JDialog information2() {
		final JDialog dlg = new JDialog(this, "����������");
		GridBagLayout g = new GridBagLayout();
		GridBagConstraints lc = new GridBagConstraints();
		GridBagConstraints rc = new GridBagConstraints();
		JPanel grid = new JPanel(g);
		JPanel bbar;
		JLabel lbl;
		JButton btn;

		grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lc.fill = rc.fill = GridBagConstraints.BOTH;
		rc.weightx = 1.0;
		lc.weightx = 0.0;
		lc.weighty = 0.0;
		rc.weighty = 0.0;
		lc.ipadx = 10;
		lc.ipady = 10;
		rc.gridwidth = GridBagConstraints.REMAINDER;

		lbl = new JLabel("������ �� ���� ������������");
		g.setConstraints(lbl, lc);
		grid.add(lbl);

		bbar = new JPanel(new GridLayout(1, 2, 5, 5));
		bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));

		btn = new JButton("�������");
		bbar.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
			}
		});

		dlg.getContentPane().add(grid, BorderLayout.CENTER);
		dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
		dlg.setLocationRelativeTo(this);
		dlg.setLocation(664, 465);
		dlg.pack();
		return dlg;
	}

	private JDialog interpretation() {
		final JDialog dlg = new JDialog(this, "������������� ������");
		GridBagLayout g = new GridBagLayout();
		GridBagConstraints lc = new GridBagConstraints();
		GridBagConstraints rc = new GridBagConstraints();
		JPanel grid = new JPanel(g);
		JPanel bbar;
		JLabel lbl;
		JButton btn;

		grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		lc.fill = rc.fill = GridBagConstraints.BOTH;
		rc.weightx = 1.0;
		lc.weightx = 0.0;
		lc.weighty = 0.0;
		rc.weighty = 0.0;
		lc.ipadx = 10;
		lc.ipady = 10;
		rc.gridwidth = GridBagConstraints.REMAINDER;

		GridLayout layout = new GridLayout(5, 1);
		layout.setVgap(10);

		lbl = new JLabel("���������� ���������: " + HybGenAl.GENERATION_SPAN);
		g.setConstraints(lbl, lc);
		grid.add(lbl);
		grid.setLayout(layout);

		lbl = new JLabel("���������� ��������: " + Problem.customersNumber);
		g.setConstraints(lbl, lc);
		grid.add(lbl);
		grid.setLayout(layout);

		lbl = new JLabel("���������������� ����������: " + HybGenAl.MAX_CAPACITY);
		g.setConstraints(lbl, lc);
		grid.add(lbl);
		grid.setLayout(layout);

		lbl = new JLabel("���������� ���������: " + (zero - 1));
		g.setConstraints(lbl, lc);
		grid.add(lbl);
		grid.setLayout(layout);

		lbl = new JLabel("����� ��������: "
				+ Math.round(population.get(0).getTotalLength()));
		g.setConstraints(lbl, lc);
		grid.add(lbl);

		bbar = new JPanel(new GridLayout(1, 2, 5, 5));
		bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));
		btn = new JButton("�������");
		bbar.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
			}
		});

		dlg.getContentPane().add(grid, BorderLayout.CENTER);
		dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
		dlg.setLocationRelativeTo(this);
		dlg.setLocation(664, 465);
		dlg.pack();
		zero=0;
		return dlg;
	}

	@Override
	public void run() {

		JMenuBar mbar;
		JMenu menu;
		JMenuItem item;

		this.getContentPane().setLayout(new BorderLayout());

		mbar = new JMenuBar();
		this.getContentPane().add(mbar, BorderLayout.NORTH);

		menu = mbar.add(new JMenu("����"));
		menu.setMnemonic('f');

		item = menu.add(new JMenuItem("��������� ������"));
		item.setMnemonic('l');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.this.loadTask(null);
			}
		});
		item = menu.add(new JMenuItem("��������� ��� PNG"));
		item.setMnemonic('i');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.this.saveImage(null);
			}
		});
		item = menu.add(new JMenuItem("��������� ��� TXT"));
		item.setMnemonic('i');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.this.saveResult(null);
			}
		});
		menu.addSeparator();
		item = menu.add(new JMenuItem("�����"));
		item.setMnemonic('q');

		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		menu = mbar.add(new JMenu("��������"));
		menu.setMnemonic('a');
		item = menu.add(new JMenuItem("������� ��������� ������ SDVRP"));
		item.setMnemonic('g');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainPanel.this.randvrp == null)
					MainPanel.this.randvrp = createRandSDVRP();
				MainPanel.this.randvrp.setVisible(true);
			}
		});

		item = menu.add(new JMenuItem("������"));
		item.setMnemonic('o');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainPanel.this.runopt == null)
					if (sig == 0) {
						MainPanel.this.inf2 = information2();
						MainPanel.this.inf2.setVisible(true);
					} else {
						MainPanel.this.runopt = createRunOpt();
					}
				MainPanel.this.runopt.setVisible(true);
			}
		});

		item = menu.add(new JMenuItem("������������� �������"));
		item.setMnemonic('m');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainPanel.this.inter == null)
					if (zero == 0) {
						MainPanel.this.inf1 = information1();
						MainPanel.this.inf1.setVisible(true);
					} else {
						MainPanel.this.inter = interpretation();
					}
				MainPanel.this.inter.setVisible(true);
			}
		});

		/*
		 * item = menu.add(new JMenuItem("����")); item.setMnemonic('s');
		 * item.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { if (MainPanel.this.timer == null)
		 * return; MainPanel.this.timer.stop(); } });
		 */

		this.panel = new PanelLogic();
		this.panel.setLayout(new BorderLayout());
		this.panel
				.setPreferredSize(new Dimension(panelDefWidth, panelDefHeight));
		this.scroll = new JScrollPane(this.panel);
		this.getContentPane().add(this.scroll, BorderLayout.CENTER);

		MainPanel.stat = new JTextField("");
		MainPanel.stat.setEditable(false);
		this.getContentPane().add(MainPanel.stat, BorderLayout.SOUTH);

		this.setTitle("������� ������ SDVRP ��������� ������������ ����������");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
		MainPanel.stat.setText("�������� ��������");
	}

	public MainPanel() {
		try {
			EventQueue.invokeAndWait(this);
		} catch (Exception e) {
		}
	}

	public static void main(String args[]) {
		new MainPanel();
	}
}
