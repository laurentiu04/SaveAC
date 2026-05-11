package ro.ac.castravetii.events;

import com.badlogic.ashley.core.Entity;

/**
 * Event pentru orice tip de atac.
 *
 * @param source Entitatea care a atacat.
 * @param damage Damage-ul atacului.
 * @param target Entitatea atacată.
 */
public record AttackEvent(Entity source, int damage, Entity target) implements GameEvent {
}
