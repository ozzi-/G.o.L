package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import simulation.Cell;
import simulation.Creature;
import simulation.Settings;
import simulation.Simulation;
import simulation.WolPanel;
import simulation.World;

public class GoLActionHandlers {
	private World world;
	private WolPanel worldPanel;
	private Creature pointerCreature;

	public GoLActionHandlers(World world, WolPanel worldPanel) {
		this.world = world;
		this.worldPanel = worldPanel;

	}

	public ActionListener spawn() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.bearRandom(50);
				worldPanel.repaint();
			}
		};
	}

	public ActionListener spawnCreature(final Creature creature) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pointerCreature = creature;
				worldPanel.repaint();
			}
		};
	}

	public ActionListener killAll() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.killAll();
				worldPanel.repaint();
			}
		};
	}

	public ActionListener play(final JButton btn_next, final JButton btn_togglePlay) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.setRunning(!world.isRunning());
				btn_next.setEnabled(!world.isRunning());
				btn_togglePlay.setText(world.isRunning() ? "Pause" : "  Play  ");
			}
		};
	}

	public ActionListener next() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.setLastSimTime(System.currentTimeMillis());
				world.setSleepTime(0);
				Simulation.simulate(world);
				GUI.paint();
			}
		};
	}

	public MouseListener mouseListener() {
		return new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX() / Settings.creatureScale + world.visibleWorldStartX;
				int y = e.getY() / Settings.creatureScale + world.visibleWorldStartY;

				if (SwingUtilities.isLeftMouseButton(e)) {
					if (pointerCreature == null) {
						world.bear(x, y);
					} else {
						world.bearCreature(pointerCreature, x, y);
					}
				} else if (SwingUtilities.isRightMouseButton(e)) {
					Cell dyingCell = world.getInhabitants()[x][y];
					dyingCell.kill();
				}
				GUI.paint();
			}
		};
	}

	public AdjustmentListener simSpeed(final JLabel lbl_simspeed) {
		return new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				Settings.simTime = 1000 - (arg0.getValue() * 11);
				lbl_simspeed.setText("    Simulation Speed " + Settings.simTime);
			}
		};
	}

	public ActionListener save() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Saving to: " + IO.userHomeFolder);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mmss");
				String strDate = sdf.format(cal.getTime());
				File textFile = new File(IO.userHomeFolder, "save_" + strDate + ".world");
				System.out.println("save_" + strDate + ".world");
				try {
					// TODO refactor to use json object instead of manual string building
					BufferedWriter out = new BufferedWriter(new FileWriter(textFile));
					out.write("{ \"savename\":\"save\",\"cells\":[");
					Cell[][] cells = world.getInhabitants();
					for (int x = 0; x < world.getWorldWidth(); x++) {
						for (int y = 0; y < world.getWorldHeight(); y++) {
							Cell cell = cells[x][y];
							if (cell.isAlive()) {
								out.write("[" + cell.getPosx() + "," + cell.getPosy() + "],");
							}
						}
					}
					out.write("] }");
					out.close();
				} catch (Exception e) {

				}

			}
		};
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public ActionListener load() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Saved Worlds", "world", "WORLD");
				fileChooser.setFileFilter(filter);

				fileChooser.setCurrentDirectory(new File(IO.userHomeFolder));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("Loading: " + selectedFile.getAbsolutePath());
					world.killAll();
					String json;
					try {
						json = readFile(selectedFile.getAbsolutePath(), Charset.defaultCharset());
						JsonObject job = JsonParser.parseString(json).getAsJsonObject();
						JsonArray formArr = job.get("cells").getAsJsonArray();
						// TODO not a json array ex
						for (JsonElement formCoordinates : formArr) {
							int x = (Integer) formCoordinates.getAsJsonArray().get(0).getAsInt();
							int y = (Integer) formCoordinates.getAsJsonArray().get(1).getAsInt();
							world.bear(x, y);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					GUI.paint();
				}
			}
		};
	}

	public ActionListener toggleGrid() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Settings.drawGrid = !Settings.drawGrid;
				GUI.paint();
			}
		};
	}

	public ActionListener toggleInfinite(WorldType worldType) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				world.setType(worldType);
			}
		};
	}

	public ActionListener downloadCreature() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Thread thread = new Thread(){
					public void run(){
						String creatureName = (String)JOptionPane.showInputDialog("Enter a pattern name from conwaylife.com\r\ni.E. \"b52bomber\"");
						String res;
						try {
							res = NW.doGETRequest("https://www.conwaylife.com/patterns/"+creatureName+".cells");
							Creature creature = CreatureLoader.loadFromCellsSyntax(res,creatureName);
							ArrayList<Creature> creatureList = CreatureLoader.getCreatureList();
							boolean exists = false;
							for (Creature creatureEntry : creatureList) {
								if (creatureEntry.getName().equals(creature.getName())) {
									exists=true;
								}
							}
							if(!exists) {
								pointerCreature = creature;
								GUI.addCreatureButton(creature);
								
								File file = new File(IO.userHomeFolder, "downloaded_"+creature.getName()+".creature");
								BufferedWriter out = new BufferedWriter(new FileWriter(file));
								out.write(creature.toJSON());
								out.close();
							}
						} catch (IOException e) {
							String exDesc = e.getCause()==null?e.getMessage():e.getCause().getMessage();
						    JOptionPane.showMessageDialog(new JFrame(),exDesc,e.getClass().getName(),JOptionPane.ERROR_MESSAGE);     
						} catch (NoSuchElementException e) {
						    JOptionPane.showMessageDialog(new JFrame(),"Not found","404",JOptionPane.WARNING_MESSAGE);     
						}
					}
				};
				thread.start();
			}
		};
	}
}