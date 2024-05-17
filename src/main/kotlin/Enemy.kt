open class Enemy(var name: String, val hp: Int) {
    var currentHp = hp
    var damageModifier = 1.0
    var burning = false
}
