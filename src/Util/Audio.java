package Util;

import java.applet.Applet;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import principal.Tela;

public class Audio extends Applet{

	private static final long serialVersionUID = 1L;
	
	private String soundName;
	private Clip clip;
	private static long prxAtualizacao = 0;
	
	public Audio(String nome) {
		this.soundName = nome;
	}
	
	public void tocar() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (System.currentTimeMillis() > prxAtualizacao || !Tela.tocandoCorrendo || !Tela.tocandoEstrelinha){
            	clip.start();
            	prxAtualizacao = System.currentTimeMillis() + (clip.getMicrosecondLength()/2000);
            }
        } catch (Exception ex) {
            System.out.println("Erro ao executar SOM!");
            ex.printStackTrace();
        }
    }
	
	public void stop(){
		if (clip != null){
			clip.stop(); 
		}
	}
	
}
