package tlob.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Sound {
	private AudioStream audios;
	
	private String defaultName;

	public Sound(){
		defaultName = null;
	}
	
	public Sound(String name){
		defaultName = name;
	}
	
	public void play() {
		if(defaultName != null)
			play(defaultName);
	}
	
	public void play(String name) {
		InputStream in;
		try{
			in = new FileInputStream(new File("res/Audio/" + name + ".wav"));
			this.audios = new AudioStream(in);
			AudioPlayer.player.start(audios);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
	}
	
	public boolean isFinished() {
		if(audios == null)
			return true;
		
		try {
			return audios.available() == 0;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return false;
		}
	}
	
	public void soundEnd() {
		if(audios == null)
			return;
		try {
			audios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}