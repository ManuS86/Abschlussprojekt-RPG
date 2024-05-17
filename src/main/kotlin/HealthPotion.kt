class HealthPotion : Potion() {
    fun useHealthPotion(target: Hero, inventory: Inventory) {
        val heal = target.hp * 1.5
        target.currentHp + heal
        println("Health Potion heals ${target.name} to $heal")
        if (inventory.content.contains(HealthPotion())) {
            inventory.content.remove(HealthPotion())
        } else {
            //redo action select
        }
    }
}