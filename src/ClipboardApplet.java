/*
ClipboardApplet.
Copy text from an HTML element into the system clipboard.
	
This is done via the Java Network Launching Protocol (JNLP),
as applets run in a sandboxed execution environment 
and this applet needs access to the system clipboard.
	
Copyright (c) 2011 Sam Saint-Pettersen.
Released under the MIT/X11 License.
*/
package org.stpettersens.cbapplet;

import netscape.javascript.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.jnlp.ServiceManager;
import javax.jnlp.ClipboardService;
import javax.jnlp.UnavailableServiceException;

public class ClipboardApplet extends JApplet {

	public JSObject win;
	public String toCopy;
	
	public void init() {
	
		win = JSObject.getWindow(this);
		toCopy = this.getParameter("tocopy");
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
		
		private void writeToClipboard() {
		
			Transferable copiedText = new StringSelection(getText());
			sysClipboard.setContents(copiedText);
			JOptionPane.showMessageDialog(frame, "Copied text to clipboard.");
		}
		
		private String getText() {
	
			return (String) win.eval(
			String.format("document.getElementById('%s').innerHTML;", toCopy));
		}
	}
}
