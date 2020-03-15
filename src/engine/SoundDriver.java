package engine;

import java.io.*;
import javax.sound.sampled.*; 


public class SoundDriver {
	static AudioInputStream[] audioInputStream;
	static Clip clip;
	public SoundDriver (File sounds[]) {
		audioInputStream = new AudioInputStream[sounds.length];
		// = AudioSystem.getClip();
		int c = 0; 
		for(File sound : sounds) {
			try {
				audioInputStream[c] = AudioSystem.getAudioInputStream(sound.getAbsoluteFile());
			} catch(Exception e) {
				e.printStackTrace();
			}
			c++;
		}
		try {
			clip = AudioSystem.getClip();
		} catch(Exception e) {
			
		}
	}

	public static void playShoot() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/laser.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playThrust() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/thrusters.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	
	public static void playMissile() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/missile.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playBreak() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/break.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playExplosion() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/explosion.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playReset() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/beep2.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playGameOver() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/GameOver.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playPowerUp() {
		try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/powerup.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		} catch(Exception e) {}
	}
	
	public static void playBackground() {
		try {
		
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/miiSound.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        
	        clip.start();
	        //clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) {}
	}
}
