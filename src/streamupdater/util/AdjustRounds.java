package streamupdater.util;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import streamupdater.gui.components.StreamUpdaterTab;

public class AdjustRounds {

	private JSpinner numberOfPools;
	private JSpinner numberOfPeopleInPools;
	private JSpinner numberOfRounds;
	
	private JTextField bracket;
	private JTextField pools;
	private JFrame frame;
	
	public AdjustRounds() {
		
		frame = new JFrame();
		frame.setTitle("Adjust Rounds Configuration");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setBounds(100, 100, 450, 360);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 299);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pools Configuration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 394, 33);
		panel.add(lblNewLabel);
		
		pools = new JTextField();
		pools.setText(StreamUpdaterTab.displayPools);
		pools.setToolTipText(loadCommands());
		pools.setHorizontalAlignment(SwingConstants.CENTER);
		pools.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pools.setBounds(10, 55, 394, 23);
		panel.add(pools);
		pools.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Bracket Configuration");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(10, 89, 394, 33);
		panel.add(lblNewLabel_1);
		
		bracket = new JTextField();
		bracket.setText(StreamUpdaterTab.displayBracket);
		bracket.setToolTipText(loadCommands());
		bracket.setHorizontalAlignment(SwingConstants.CENTER);
		bracket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bracket.setBounds(10, 133, 394, 20);
		panel.add(bracket);
		bracket.setColumns(10);
		
		JButton update = new JButton("Update");
		update.setBounds(108, 248, 199, 40);
		panel.add(update);
		
		JLabel lblNewLabel_2 = new JLabel("Number Of Pools: ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 164, 124, 33);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Number Of People In Each Pool: ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(10, 208, 220, 33);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Number Of Rounds: ");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(225, 164, 124, 33);
		panel.add(lblNewLabel_4);
		
		numberOfPools = new JSpinner();
		numberOfPools.setValue(2);
		numberOfPools.setBounds(123, 171, 50, 20);
		panel.add(numberOfPools);
		
		numberOfPeopleInPools = new JSpinner();
		numberOfPeopleInPools.setValue(2);
		numberOfPeopleInPools.setBounds(230, 215, 50, 20);
		panel.add(numberOfPeopleInPools);
		
		numberOfRounds = new JSpinner();
		numberOfRounds.setValue(2);
		numberOfRounds.setBounds(352, 171, 52, 20);
		panel.add(numberOfRounds);
		
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StreamUpdaterTab.setRounds(spliceAndCommit(pools.getText(), bracket.getText()));
				StreamUpdaterTab.displayPools = pools.getText();
				StreamUpdaterTab.displayBracket = bracket.getText();
				frame.dispose();
			}
			
		});
		
		frame.setVisible(true);
		
	}
	
	private ArrayList<String> spliceAndCommit(String pools, String bracket) {
		ArrayList<String> setup = new ArrayList<String>();
		ArrayList<String> build = new ArrayList<String>();
		// do pools
		// add whole if check for commands, if nothing add to list
		if(pools.contains("%ad") || pools.contains("%winloss") ||
				pools.contains("%round") || pools.contains("%d") ||
				pools.contains("%a") || pools.contains("%b")) {
			
			if(pools.contains("%ad")) {
				if(build.size() == 0) {
					for(int i = 0; i < (int) numberOfPools.getValue(); i++) {			// letter
						for(int j = 1; j <= (int) numberOfPeopleInPools.getValue(); j++) { //grouping
							String tmp = pools;
							char letter = (char) (i + 65);
							tmp = tmp.replace("%ad", "" + letter + j);
							build.add(tmp);
						}
					}
				} else {
					for(int i = 0; i < (int) numberOfPools.getValue() -1; i++) {			// letter
						for(int j = 1; j <= (int) numberOfPeopleInPools.getValue(); j++) { //grouping
							for(int a = 0; a < build.size(); a++) {
								String tmp = build.get(a);
								char letter = (char) (i + 65);
								tmp = tmp.replace("%ad", "" + letter + j);
								build.remove(a);
								build.add(tmp);
							}
						}
					}
				}
			}
			
			if(pools.contains("%round")) {
				if(build.size() == 0) {
					for(int i = 0; i < (int) numberOfRounds.getValue(); i++) {
						String tmp = pools;
						if(i >= (int) numberOfRounds.getValue() - 4) {
							tmp = tmp.replace("%round", "Quarter-finals");
							build.add(tmp);
							tmp = pools;
							tmp = tmp.replace("%round", "Semi-finals");
							build.add(tmp);
							tmp = pools;
							tmp = tmp.replace("%round", "Finals");
							build.add(tmp);
							tmp = pools;
							tmp = tmp.replace("%round", "Grand Finals");
							build.add(tmp);
							break;
						}
						tmp = tmp.replace("%round", "Round " + (i + 1));
						build.add(tmp);
					}
				} else {
					// little bit more advanced
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < build.size(); i++) {
						String tmp = build.get(i);
						for(int j = 0; j < (int) numberOfRounds.getValue(); j++) {
							tmp = build.get(i);
							if(j >= (int) numberOfRounds.getValue() - 4) {
								tmp = tmp.replace("%round", "Quarter-finals");
								tmparray.add(tmp);
								tmp = build.get(i);
								tmp = tmp.replace("%round", "Semi-finals");
								tmparray.add(tmp);
								tmp = build.get(i);
								tmp = tmp.replace("%round", "Finals");
								tmparray.add(tmp);
								tmp = build.get(i);
								tmp = tmp.replace("%round", "Grand Finals");
								tmparray.add(tmp);
								break;
							}
							tmp = tmp.replace("%round", "Round " + (j + 1));
							tmparray.add(tmp);
						}
					}
					build.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						build.add(tmparray.get(i));
					}
				}
			}
			
			// fix everyone swap
			if(pools.contains("%winloss")) {
				if(build.size() == 0) {
					for(int i = 0; i < 2; i++) {
						String tmp = pools;
						String[] tmps = { "Winners", "Losers" };
						tmp = tmp.replace("%winloss", tmps[i]);
						build.add(tmp);
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < build.size(); i++) {
						String tmp = build.get(i);
						String tmp2 = build.get(i);
						String[] tmps = { "Winners", "Losers" };
						tmp = tmp.replace("%winloss", tmps[0]);
						tmp2 = tmp2.replace("%winloss", tmps[1]);
						tmparray.add(tmp);
						tmparray.add(tmp2);
					}
					build.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						build.add(tmparray.get(i));
					}
				}
			}
			
			if(pools.contains("%d")) {
				if(build.size() == 0) {
					for(int i = 0; i < (int) numberOfRounds.getValue(); i++) {
						String tmp = pools;
						tmp = tmp.replaceAll("%d", "" + (i + 1));
						build.add(tmp);
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < build.size(); i++) {
						for(int j = 0; j < (int) numberOfRounds.getValue(); j++) {
							String tmp = build.get(i);
							tmp = tmp.replaceAll("%d", "" + (j + 1));
							tmparray.add(tmp);
						}
					}
					build.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						build.add(tmparray.get(i));
					}
				}
			}
			
			if(pools.contains("%a")) {
				if(build.size() == 0) {
					for(int i = 0; i < (int) numberOfRounds.getValue(); i++) {
						String tmp = pools;
						char tmpa = (char) (65 + i);
						tmp = tmp.replaceAll("%a", "" + tmpa);
						build.add(tmp);
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < build.size(); i++) {
						for(int j = 0; j < (int) numberOfRounds.getValue(); j++) {
							String tmp = build.get(i);
							char tmpa = (char) (65 + j);
							tmp = tmp.replaceAll("%a", "" + tmpa);
							tmparray.add(tmp);
						}
					}
					build.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						build.add(tmparray.get(i));
					}
				}
			}
			
			// BINARYYYYY
			if(pools.contains("%b")) {
				if(build.size() == 0) {
					for(int i = 12; i > 0; i--) {
						String tmp = pools;
						tmp = tmp.replaceAll("%b", "" + (int) Math.pow(2, i));
						build.add(tmp);
						tmp = pools;
						if(i > 0) {
							int compute = ((int) Math.pow(2, i) + (int) Math.pow(2, (i-1))) / 2;
							tmp = tmp.replaceAll("%b", "" + compute);
							build.add(tmp);
						}
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int j = 0; j < build.size(); j++) {
						for(int i = 12; i > 0; i--) {
							String tmp = build.get(j);
							tmp = tmp.replaceAll("%b", "" + (int) Math.pow(2, i));
							tmparray.add(tmp);
							tmp = build.get(j);
							if(i > 0) {
								int compute = ((int) Math.pow(2, i) + (int) Math.pow(2, (i-1))) / 2;
								tmp = tmp.replaceAll("%b", "" + compute);
								tmparray.add(tmp);
							}
						}
					}
					build.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						build.add(tmparray.get(i));
					}
				}
			}
		
		} else {
			build.add(pools);
		}
		
		// rinse and repeat, literally copy and paste but do bracket instead.
		if(bracket.contains("%winloss") ||
				bracket.contains("%round") || bracket.contains("%d") ||
				bracket.contains("%a") || bracket.contains("%b")) {
			if(bracket.contains("%round")) {
				if(setup.size() == 0) {
					for(int i = 0; i < (int) numberOfRounds.getValue(); i++) {
						String tmp = bracket;
						if(i >= (int) numberOfRounds.getValue() - 4) {
							tmp = tmp.replace("%round", "Quarter-finals");
							setup.add(tmp);
							tmp = bracket;
							tmp = tmp.replace("%round", "Semi-finals");
							setup.add(tmp);
							tmp = bracket;
							tmp = tmp.replace("%round", "Finals");
							setup.add(tmp);
							tmp = bracket;
							tmp = tmp.replace("%round", "Grand Finals");
							setup.add(tmp);
							break;
						}
						tmp = tmp.replace("%round", "Round " + (i + 1));
						setup.add(tmp);
					}
				} else {
					// little bit more advanced
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < setup.size(); i++) {
						String tmp = setup.get(i);
						for(int j = 0; j < (int) numberOfRounds.getValue(); j++) {
							tmp = setup.get(i);
							if(j >= (int) numberOfRounds.getValue() - 4) {
								tmp = tmp.replace("%round", "Quarter-finals");
								tmparray.add(tmp);
								tmp = setup.get(i);
								tmp = tmp.replace("%round", "Semi-finals");
								tmparray.add(tmp);
								tmp = setup.get(i);
								tmp = tmp.replace("%round", "Finals");
								tmparray.add(tmp);
								tmp = setup.get(i);
								tmp = tmp.replace("%round", "Grand Finals");
								tmparray.add(tmp);
								break;
							}
							tmp = tmp.replace("%round", "Round " + (j + 1));
							tmparray.add(tmp);
						}
					}
					setup.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						setup.add(tmparray.get(i));
					}
				}
			}
			
			// fix everyone swap
			if(bracket.contains("%winloss")) {
				if(setup.size() == 0) {
					for(int i = 0; i < 2; i++) {
						String tmp = bracket;
						String[] tmps = { "Winners", "Losers" };
						tmp = tmp.replace("%winloss", tmps[i]);
						setup.add(tmp);
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < setup.size(); i++) {
						String tmp = setup.get(i);
						String tmp2 = setup.get(i);
						String[] tmps = { "Winners", "Losers" };
						tmp = tmp.replace("%winloss", tmps[0]);
						tmp2 = tmp2.replace("%winloss", tmps[1]);
						tmparray.add(tmp);
						tmparray.add(tmp2);
					}
					setup.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						System.out.println(tmparray.get(i));
						setup.add(tmparray.get(i));
					}
				}
			}
			
			if(bracket.contains("%d")) {
				if(build.size() == 0) {
					for(int i = 0; i < (int) numberOfRounds.getValue(); i++) {
						String tmp = bracket;
						tmp = tmp.replaceAll("%d", "" + (i + 1));
						setup.add(tmp);
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < setup.size(); i++) {
						for(int j = 0; j < (int) numberOfRounds.getValue(); j++) {
							String tmp = setup.get(i);
							tmp = tmp.replaceAll("%d", "" + (j + 1));
							tmparray.add(tmp);
						}
					}
					setup.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						setup.add(tmparray.get(i));
					}
				}
			}
			
			if(bracket.contains("%a")) {
				if(setup.size() == 0) {
					for(int i = 0; i < (int) numberOfRounds.getValue(); i++) {
						String tmp = bracket;
						char tmpa = (char) (65 + i);
						tmp = tmp.replaceAll("%a", "" + tmpa);
						setup.add(tmp);
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int i = 0; i < setup.size(); i++) {
						for(int j = 0; j < (int) numberOfRounds.getValue(); j++) {
							String tmp = setup.get(i);
							char tmpa = (char) (65 + j);
							tmp = tmp.replaceAll("%a", "" + tmpa);
							tmparray.add(tmp);
						}
					}
					setup.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						setup.add(tmparray.get(i));
					}
				}
			}
			
			// BINARYYYYY
			if(bracket.contains("%b")) {
				if(setup.size() == 0) {
					for(int i = 12; i > 0; i--) {
						String tmp = bracket;
						tmp = tmp.replaceAll("%b", "" + (int) Math.pow(2, i));
						setup.add(tmp);
						tmp = bracket;
						if(i > 0) {
							int compute = ((int) Math.pow(2, i) + (int) Math.pow(2, (i-1))) / 2;
							tmp = tmp.replaceAll("%b", "" + compute);
							setup.add(tmp);
						}
					}
				} else {
					ArrayList<String> tmparray = new ArrayList<String>();
					for(int j = 0; j < setup.size(); j++) {
						for(int i = 12; i > 0; i--) {
							String tmp = setup.get(j);
							tmp = tmp.replaceAll("%b", "" + (int) Math.pow(2, i));
							tmparray.add(tmp);
							tmp = setup.get(j);
							if(i > 0) {
								int compute = ((int) Math.pow(2, i) + (int) Math.pow(2, (i-1))) / 2;
								tmp = tmp.replaceAll("%b", "" + compute);
								tmparray.add(tmp);
							}
						}
					}
					setup.clear();
					for(int i = 0; i < tmparray.size(); i++) {
						setup.add(tmparray.get(i));
					}
				}
			}
		
		} else {
			setup.add(bracket);
		}
		
		for(int i = 0; i < setup.size(); i++) {
			build.add(setup.get(i));
		}
		
		return build;
	}
	
	private String loadCommands() {
		StringBuffer sb = new StringBuffer("<html>");
		sb.append("%round --> Round # till it's replaced by GF, etc<br>");
		sb.append("%winloss --> Winners/Losers is displayed<br>");
		sb.append("%a --> A,B,C,D,etc<br>");
		sb.append("%d --> 1,2,3,4,etc<br>");
		sb.append("%b --> 256,192,128,etc<br>");
		sb.append("%ad --> a1,a2,a3,b1,etc<br>");
		return sb.toString();
	}
	
	public void unhide() {
		if(frame != null) {
			frame.setVisible(true);
		}
	}

}
