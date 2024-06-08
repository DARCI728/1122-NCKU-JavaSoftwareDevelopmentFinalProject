package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/menu.wav");
        soundURL[1] = getClass().getResource("/sound/hitMob.wav");
        soundURL[2] = getClass().getResource("/sound/gameOver.wav");
        soundURL[3] = getClass().getResource("/sound/cursor.wav");
        soundURL[4] = getClass().getResource("/sound/dungeon.wav");
        soundURL[5] = getClass().getResource("/sound/speak.wav");
        soundURL[6] = getClass().getResource("/sound/teleport.wav");
        soundURL[7] = getClass().getResource("/sound/move1.wav");
        soundURL[8] = getClass().getResource("/sound/move2.wav");
        soundURL[9] = getClass().getResource("/sound/getItem.wav");
        soundURL[10] = getClass().getResource("/sound/sword.wav");
        soundURL[11] = getClass().getResource("/sound/shoot.wav");
        soundURL[12] = getClass().getResource("/sound/victory.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
        clip.flush();
    }

    public void setVolume(float value) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(value);
    }
}
