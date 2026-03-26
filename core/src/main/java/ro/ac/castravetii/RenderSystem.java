package ro.ac.castravetii;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Sistem de randare a tuturor entitatilor de au componenta de tip TextureComponent
 * @author Laurentiu
 */
public class RenderSystem extends IteratingSystem {

    // Stochez toate componentele de care o sa am nevoie pentru a nu fi nevoit sa le caut constant.
    ComponentMapper<TextureComponent> txm = ComponentMapper.getFor(TextureComponent.class);
    ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    /**
     * Constructorul asta ia toate entitatile cu componentele mentionate in functia Family.all()
     * ca mai apoi engine-ul sa apeleze processEntity() pentru fiecare entitate gasita cu acele componente.
     */
    public RenderSystem() {
        super(Family.all(TextureComponent.class, TransformComponent.class).get());
    }

    /**
     * Aici trebuie sa apelam super.update() ca dupa sa se apeleze processEntity()
     * @param delta The time passed since last frame in seconds.
     */
    @Override
    public void update(float delta) {
        // Desenez toate entitatile ce trebuie desenate.
        super.update(delta);
    }

    /**
     * Functia asta se apeleaza pentru fiecare entitate gasita de Family.all()
     * Aici punem logica sistemului pe care vrem sa il implementam.
     *
     * @param entity The current Entity being processed
     * @param deltaTime The delta time between the last and current frame
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Iau variabila region din componenta TextureComponent a entitatii curente.
        TextureRegion region = txm.get(entity).region;
        // Iau toata componenta TransformComponent a entitatii curente.
        TransformComponent transform = tm.get(entity);

        /* Initializez o variabila pentru directie.
         Daca entitatea curenta are si componenta de movement, iau directia din acea componenta
         ca sa invart textura entitatii in acea directie.
            1 > dreapta
           -1 > stanga
        */
        int direction = 1;
        if (mm.has(entity)) {
            direction = mm.get(entity).directionX;
        }

        // Desenez entitatea cu datele din componenta TransformComponent si TextureComponent
        Services.batch.draw(
            region,                                                 // regiunea texturii
            Math.round(transform.position.x - region.getRegionWidth()/2.0f),    // pozitia pe axa X
            Math.round(transform.position.y - region.getRegionHeight()/2.0f),   // pozitia pe axa Y
            region.getRegionWidth()/2.0f,                           // originea pe axa X (am pus sa fie la mijloc)
            region.getRegionHeight()/2.0f,                          // originea pe axa Y (am pus sa fie la mijloc)
            region.getRegionWidth(),                                // latimea (o obtin din latimea texturii)
            region.getRegionHeight(),                               // inaltimea (o obtin din inaltimea texturii
            transform.scale.x * direction,                          // scalare pe axa X
            transform.scale.y,                                      // scalare pe axa Y
            transform.rotation                                      // rotatie
        );
    }
}
