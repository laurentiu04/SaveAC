package ro.ac.castravetii.events;

/**
 * Event pentru orice tip de atac.
 *
 * @param source Entitatea care a atacat.
 * @param damage Damage-ul atacului.
 * @param target Entitatea atacată.
 */
public record AttackEvent(Object source, int damage, Object target) implements GameEvent {
}
