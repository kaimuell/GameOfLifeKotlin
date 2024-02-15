import kotlin.random.Random

fun main(){
    var world = generateWorld(20,40)
    println("Enter for next Generation, q an Enter to quit")
    var x = 'n'
    while (x != 'q'){
        printWorld(world)
        world = nextState(world)
        x = try {
        readln().first() } catch (e: NoSuchElementException) {
            'n'
        }
    }
}

fun nextState(world: Array<Array<Boolean>>): Array<Array<Boolean>> {
    var nextWorldState = Array(world.size){Array(world[0].size) {false} }
    for (i in 0..<world.size){
        for (j in 0..<world[i].size){
            val aliveNeighbours: Int = checkNeighbours(world, i, j)
            nextWorldState[i][j] = isAliveInNextState(world[i][j], aliveNeighbours)
        }
    }
    return nextWorldState
}

fun isAliveInNextState(isAlive: Boolean, aliveNeighbours: Int): Boolean {
    if (!isAlive && aliveNeighbours == 3) return true
    if (isAlive && (aliveNeighbours == 2 || aliveNeighbours == 3)) return true
    return false
}

fun checkNeighbours(world: Array<Array<Boolean>>, i: Int, j: Int): Int {
    var aliveNeighbours = 0
    if (isInbounds(i-1, world, j) && world[i-1][j]) aliveNeighbours ++
    if (isInbounds(i+1, world, j) && world[i+1][j]) aliveNeighbours ++
    if (isInbounds(i-1, world, j+1) && world[i-1][j+1]) aliveNeighbours ++
    if (isInbounds(i, world, j+1) && world[i][j+1]) aliveNeighbours ++
    if (isInbounds(i+1, world, j+1) && world[i+1][j+1]) aliveNeighbours ++
    if (isInbounds(i-1, world, j-1) && world[i-1][j-1]) aliveNeighbours ++
    if (isInbounds(i, world, j-1) && world[i][j-1]) aliveNeighbours ++
    if (isInbounds(i+1, world, j-1) && world[i+1][j-1]) aliveNeighbours ++
    return aliveNeighbours
}

private fun isInbounds(i: Int, world: Array<Array<Boolean>>, j: Int) =
    0 <= i && i < world.size && 0 <= j && j < world[i].size

fun printWorld(board: Array<Array<Boolean>>) {
    for (row in board){
        for (col in row){
            print(if (col) "X" else ".")
        }
        println()
    }
    println()
}

fun generateWorld(height: Int, width: Int): Array<Array<Boolean>> {
    return Array (height) {Array (width){
        Random.nextBoolean()
    } }
}
