package ro.ac.castravetii.systems;

import ro.ac.castravetii.components.TransformComponent;

public class EnemyAISystem {

    private TransformComponent enemy;
    private TransformComponent player;

    private int[][] grid;
    private int tileSize;

    public EnemyAISystem(TransformComponent enemy, TransformComponent player, int[][] grid, int tileSize){
        this.enemy = enemy;
        this.player = player;
        this.grid = grid;
        this.tileSize = tileSize;
    }

    public void update(float deltaTime){
        //directia catre player
        float dx = player.position.x - enemy.position.x;
        float dy = player.position.y - enemy.position.y;

        float dist = (float)Math.sqrt(dx*dx + dy*dy);

        float speed = 80f;

        if(dist > 1f){
            enemy.position.x += (dx / dist) * speed * deltaTime;
            enemy.position.y += (dy / dist) * speed * deltaTime;
        }
    }
}
