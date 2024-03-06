import org.testng.annotations.Test
import kotlin.random.Random

fun main(){
    var world = generateWorld(20,60)
    println("Game of Life: ")
    println("Enter for next Generation, q an Enter to quit")
    println()
    var x = 'n'
    var counter = 0
    while (x != 'q'){
        counter ++
        println("Generation $counter")
        printWorld(world)
        world = nextState(world)
        x = try {
        readln().first() } catch (e: NoSuchElementException) {
            'n'
        }
    }
}

fun nextState(world: Array<Array<Boolean>>): Array<Array<Boolean>> {
    val nextWorldState = Array(world.size){Array(world[0].size) {false} }
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


class TestClass {
    @Test
    fun generateWorldTest() {
        val world = generateWorld(20, 40)
        assert(world.size == 20)
        assert(world[0].size == 40)
    }

    @Test
    fun isInBoundsTest() {
        val world = generateWorld(20, 40)
        assert(isInbounds(0, world, 0))
        assert(isInbounds(19, world, 39))
        assert(!isInbounds(20, world, 0))
        assert(!isInbounds(0, world, 40))
        assert(!isInbounds(-1, world, 10))
        assert(!isInbounds(10, world, -1))
    }

    @Test
    fun checkNeighboursTest(){
        val world = Array(3) {Array(3) {false} }
        world[0][0] = true
        world[0][1] = true
        world[1][0] = true
        assert(checkNeighbours(world, 0, 0) == 2)
        assert(checkNeighbours(world, 1, 1) == 3)
    }

    @Test
    fun nextStateTest(){
        val world = Array(3) {Array(3) {false} }
        world[0][0] = true
        world[0][1] = true
        world[1][0] = true
        val nextWorld = nextState(world)
        assert(nextWorld[0][0])
        assert(nextWorld[0][1])
        assert(nextWorld[1][0])
        assert(nextWorld[1][1])
    }
}