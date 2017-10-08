package core.awt.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.FlavorEvent;

class FlavorListening {

	public static void main(String args[]) {
		System.out.println("Start Clipboard Listening");
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.addFlavorListener(new CbListener());
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true);
			}
		}).start();
	}
}

class CbListener implements FlavorListener {
	
	@Override
	public void flavorsChanged(FlavorEvent event) {
		System.out.println("EVENT: " + event);
	}

}

