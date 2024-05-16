class HealthPotion : Potion() {
    fun useHealthPotion(target: Hero) {
        val heal = target.hp * 1.5
        target.hp
        println("Health Potion heals ${target.name} to $heal")
        if (Inventory.content.contains(HealthPotion())) {
            Inventory.content.remove[HealthPotion()]
        } else {
            //redo action select
        }
    }
}