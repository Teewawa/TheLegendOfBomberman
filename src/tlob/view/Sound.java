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


	public Sound(){
		
	}
	public void playSound(String name){
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
	
	public boolean isFinished (AudioStream audios){
		try{
		if (audios.available() == 0)
			return true;
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}
	public void soundEnd(AudioStream audios){
		try {
			audios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public AudioStream getAudioStream(){
		return this.audios;
	}
}