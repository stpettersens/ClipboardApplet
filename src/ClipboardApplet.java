/*
	ClipboardApplet.
	Copy text from an HTML element into the system clipboard.
	
	This is done via the Java Network Launching Protocol (JNLP),
	as applets run in a sandboxed execution environment.
	
	Copyright (c) 2011 Sam Saint-Pettersen.
	Released under the MIT/X11 License.
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.jnlp.ServiceManager;
import javax.jnlp.ClipboardService;
import javax.jnlp.UnavailableServiceException;

public class ClipboardApplet extends JApplet {

	static public ClipboardService sysClipboard = null;
	
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JButton copyButton = new JButton("Copy to Clipboard");
					copyButton.addActionListener(new CopyToClipboard());
					add(copyButton);
				}
			});
  			try {
  				sysClipboard = (ClipboardService) ServiceManager.lookup
  				("javax.jlnp.ClipboardService");
  			}
  			catch(UnavailableServiceException use) {
  				System.err.println(use);
  				sysClipboard = null;
  			}
		}
		catch(Exception e) {
			System.err.println("Error initializing ClipboardApplet UI.");
		}
	}	
	
	public class CopyToClipboard implements ActionListener {
		JFrame frame = new JFrame("ClipboardApplet");
		public void actionPerformed(ActionEvent e) {
			writeToClipboard();
			JOptionPane.showMessageDialog(frame, "Copied to clipboard.");
		}
		
		public void writeToClipboard() {
			Transferable copiedText = new StringSelection("ClipboardApplet text.");
			sysClipboard.setContents(copiedText);
		}
	}
}
