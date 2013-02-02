package gdp.racetrack;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public final class Log {

	public static final Logger logger;

	public static void addDebugWindow() {
		logger.addHandler(new DebugWindow());
	}

	private static class DebugWindow extends Handler {
		private final JFrame window;
		private final JTextArea textArea;

		private DebugWindow() {
			this.setLevel(Level.ALL);
			
			window = new JFrame();
			window.setLayout(new GridLayout(1, 1));
			
			textArea = new JTextArea();
			textArea.setSize(window.getSize());
			window.getContentPane().add(textArea);
			textArea.setForeground(Color.GRAY);
			textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
			
			window.setSize(800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			window.setVisible(true);
		}

		@Override
		public void close() throws SecurityException { }

		@Override
		public void flush() { }

		@Override
		public void publish(LogRecord record) {
			textArea.append("["+record.getLevel()+"] "+record.getMessage()+"\n");
			if (record.getThrown() != null) {
				StringWriter stringWriter = new StringWriter();
				record.getThrown().printStackTrace(new PrintWriter(stringWriter));
				textArea.append(stringWriter.toString());
			}
		}

	}

	private Log() {} // no instance can be created

	static {
		logger = Logger.getLogger("vectorAce");
		logger.setLevel(Level.ALL);
	}

}
