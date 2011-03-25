/*
ClipboardApplet.
Copy text from an HTML element into the system clipboard.
	
This is done via the Java Network Launching Protocol (JNLP),
<<<<<<< HEAD
as applets run in a sandboxed execution environment 
and this applet needs access to the system clipboard.
=======
as applets run in a sandboxed execution environment.
>>>>>>> 0e82485b1c9a8c5422d0dd69314d95ce4a6caa45
	
Copyright (c) 2011 Sam Saint-Pettersen.
Released under the MIT/X11 License.
*/
package org.stpettersens.cbapplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.jnlp.ServiceManager;
import javax.jnlp.ClipboardService;
import javax.jnlp.UnavailableServiceException;

public class ClipboardApplet extends JApplet {

	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JButton copyButton = new JButton("Copy to Clipboard");
					copyButton.addActionListener(new CopyToClipboard());
					add(copyButton);
				}
			});
		}
		catch(Exception e) {
			System.err.println("Error initializing ClipboardApplet.");
		}
	}	
	
	public class CopyToClipboard implements ActionListener {
		
		ClipboardService sysClipboard = null;
		JFrame frame = new JFrame("ClipboardApplet");
		
		public void actionPerformed(ActionEvent e) {
		
			try {
  				sysClipboard = (ClipboardService) ServiceManager.lookup
				("javax.jnlp.ClipboardService");
				writeToClipboard();
  			}
  			catch(UnavailableServiceException use) {
  				System.err.println(use);
  				sysClipboard = null;
  			}		
		}
		
		public void writeToClipboard() {
		
<<<<<<< HEAD
			Transferable copiedText = new StringSelection("I don't not hit her... Oh hai, Mark."); // :P
=======
			Transferable copiedText = new StringSelection("ClipboardApplet text.");
>>>>>>> 0e82485b1c9a8c5422d0dd69314d95ce4a6caa45
			sysClipboard.setContents(copiedText);
			JOptionPane.showMessageDialog(frame, "Copied text to clipboard.");
		}
	}
}
