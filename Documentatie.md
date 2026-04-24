# Documentatie 

### Ce este ECS?

`ECS` - Entity Component System.

**ECS** este un sistem bazat pe entități care dețin componente.

**Componentele** sunt simple clase care dețin strict informație, orice tip de logică fiind implementată într-un  sistem de control care manipulează datele din componente.

### Cum funcționează sistemul de tip ECS din librăria LibGDX

ECS are la bază un motor `Engine` care asigură controlul asupra entităților si al sistemelor de control.

La acest motor adăugăm entitățile  `Entity` și sistemele de control `EntitySystem`.

## Entity

Pentru a crea o entitate, putem folosi constructorul implicit
```java
Entity entity;
entity = new Entity();
```
Sau ne putem folosi de engine
```java
Engine engine = new Engine();
entity = engine.createEntity();
```

**Însă**, în acest proiect folosim o clasă derivată din `Engine`, mai exact `PooledEngine`.

`Pooled Engine` se ocupă singur de Garbage Collector, pentru a elibera automat resursele de care nu mai este nevoie.\
Astfel, în acest caz, trebuie neapărat folosit `engine.createEntity()` pentru a nu pierde acest beneficiu.

## Component

Pentru a crea noi componente ne folosim de interfața `Component` pe care o implementăm într-o nouă clasă.
```java
class PlayerComponent implements Component {
    public int damage = 10;
    public float health = 100;
}
```
**Atenție** - O componentă trebuie să conțină doar date, cum este prezentat și în exemplul de mai sus.

Pentru a adăuga o nouă componentă la o entitate, ca și în cazul creării entității, sunt 2 variante.\
Pentru că utilizăm `PooledEngine` vom folosi următoarea variantă:
```java
    PlayerComponent playerComp = Engine.createComponent(PlayerComponent.class);
    playerComp.health = 50f;
    playerComp.damage = 20;
    entity.addComponent(playerComp);
```

## EntitySystem / IteratingSystem

`EntitySystem` este clasa de bază pentru sistemele de control ale entităților. Această clasă nu prea ne ajută la acest proiect\
însă derivata ei, `IteratingSystem` este fix ce avem nevoie, pentru că trece automat prin toate entitățile care intră în familia de entități a sistemului.

Un exemplu de sistem:
```java
    class EnemySystem extends IteratingSystem {
    ComponentMapper<TransformComponent> tm = new ComponentMapper.getFor(TransformComponent.class);
    
    public EnemySystem() {
        super(Family.all(EnemyComponent.class).get());
    }
    
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransfromComponent = tm.get(entity);
        
        // logica aici...
    }
}
```

- `ComponentMapper` este folosit pentru a obține o componentă rapid și eficient.
- `Family.all().get()` gaseste toate entitățile cu componentele trecute in `all()`.\
    Se mai poate folosi și `one()` în loc de `all()`, diferența este că v-a lua toate entitățile care au cel puțin una\
    din componentele trecute.


