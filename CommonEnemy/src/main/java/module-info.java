module CommonEnemy {
    requires Common;
    requires Bullet;
    exports dk.sdu.se4.groupX.commonenemy;
    provides CommonEnemy with dk.sdu.se4.groupX.commonenemy.Enemy;
    provides EnemySPI with dk.sdu.se4.groupX.commonenemy.EnemySPI;
}
