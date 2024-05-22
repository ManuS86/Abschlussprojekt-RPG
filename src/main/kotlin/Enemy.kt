open class Enemy(var name: String, val maxHp: Int) {
    var hp = maxHp
    var damageModifier = 1.0
    var burning = false
}
