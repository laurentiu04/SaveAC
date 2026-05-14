package ro.ac.castravetii.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import ro.ac.castravetii.events.GameEventQueue;

import java.util.HashMap;

public class SoundSystem extends EntitySystem {
    private final GameEventQueue queue;

    private final HashMap<String, Sound> sounds = new HashMap<>();
    private final HashMap<String, Long> loopSounds = new HashMap<>();
    public SoundSystem(GameEventQueue queue, int priority){
        super(priority);
        this.queue = queue;

        sounds.put(
            "shoot",
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/shot.wav")
            )
        );

        sounds.put(
            "run",
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/running.wav")
            )
        );

        sounds.put(
            "enemyHit",
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/hitHurt.wav")
            ));

        sounds.put(
            "enemyDead",
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/death.wav")
            )
        );

        sounds.put(
            "powerUp",
            Gdx.audio.newSound(
                Gdx.files.internal("sounds/powerUp.wav")
            )
        );

//        sounds.put(
//            "playerDead",
//            Gdx.audio.newSound(
//                Gdx.files.internal("sounds/playerDead.wav")
//            ));
    }

    public void play(String soundName){

        Sound sound = sounds.get(soundName);

        if(sound != null){
            sound.play();
        }
    }

    //functie de buclare a sunetului
    public void loop(String soundName){
        Sound sound = sounds.get(soundName);

        if(sound != null && !loopSounds.containsKey(soundName)){
            long id = sound.loop();
            loopSounds.put(soundName,id);
        }
    }

    //functie de stop pentru sunet
    public void stop(String soundName){
        Sound sound = sounds.get(soundName);

        if(sound != null && loopSounds.containsKey(soundName)){
            sound.stop(loopSounds.get(soundName));

            loopSounds.remove(soundName);
        }
    }

}
